package accessor.controller;

import java.text.Normalizer;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import accessor.DAO.AccessorDAO;
import accessor.DAO.AccessorDAOImpl;
import accessor.Document.Document;
import accessor.config.AccessorConfig;
import accessor.model.departments.Department;
import accessor.model.users.User;
import accessor.model.users.UserValidator;
import accessor.supportClasses.rights.Rights;
import accessor.supportClasses.rights.RightsListWrapper;

/* Main controller of Accessor project
 * Manages user logins, user creation, right setting and accessing
 * Enables viewing documents 
 * */
@Component
@SessionAttributes({ "currentUser", "isLogged" })
@Controller
@ControllerAdvice
public class SpringAccessorController {

	//initialize logger
	private static final Logger logger = LogManager.getLogger(SpringAccessorController.class);
	
	// Wire validator for user creation at createUser
	@Autowired
	UserValidator userImplValidator;
	
	//Get context and DAO for entire controller
	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AccessorConfig.class);
	AccessorDAO DAO = context.getBean("DAOBean", AccessorDAOImpl.class);
	
	@PreDestroy
	//Close DAO connection, and context for entire controller
	protected void closeContext() {
		context.close();
		DAO.closeFactory();
		logger.info("Accessor properly terminated");
	}
	
	//Consumes list of strings from setRights to create list of Rights
	@InitBinder
	protected void initBinder(WebDataBinder binder) throws Exception {
		binder.registerCustomEditor(Set.class, "list", new CustomCollectionEditor(Set.class) {
			protected Object convertElement(Object element) {
				if (element instanceof Rights) {
					logger.trace("Converting from Rights to Rights: " + element);
					return element;
				}
				if (element instanceof String) {
					Rights right = new Rights((String)element);
					logger.trace("Converting " + element + " to : " + right);
					return right;
				}
				logger.trace("Data unconvertable" + element);
				return null;
			}
		});
	}

	//Reset DB every hour
	private TaskScheduler scheduler = new ConcurrentTaskScheduler();
	@PostConstruct
	private void executeJob() {
		logger.info("Initialize DB reseting for Accessor every hour");
	    scheduler.scheduleAtFixedRate(new Runnable() {
	        @Override
	        public void run() {


	    		try {
					// Reset DB, set default user values
					resetDB(DAO);
					logger.info("Accessor DB reseted");
				} catch (Exception e) {
					logger.error("Error - reseting DAO " + e.getStackTrace().toString());
				}

	        }
	    }, Duration.ofHours(1));
	}
	
//-------------------------------------------------------------------------
//							Initialize session variables
//-------------------------------------------------------------------------
	// Initialize session values - logged user data
	@ModelAttribute("currentUser")
	public User currentUser() {
		return new User();
	}

	@ModelAttribute("isLogged")
	public Boolean isLogged() {
		return false;
	}

//-------------------------------------------------------------------------
//						#End of	Initialize session variables
//-------------------------------------------------------------------------	

//-------------------------------------------------------------------------
//						Index page - Portfolio
//-------------------------------------------------------------------------	  
	@GetMapping(value = "/")
	public ModelAndView homepage() {
		
		return new ModelAndView("index");
	}

//-------------------------------------------------------------------------
//						Main page of accessor project
//-------------------------------------------------------------------------	 
	@GetMapping(value = "/Accessor/")
	@ResponseBody
	public ModelAndView accessorHomepage(@ModelAttribute("currentUser") User currentUser,
			@ModelAttribute("isLogged") Boolean isLogged) {

		ModelAndView model = new ModelAndView("main");
		
		logger.info("Accessor called");

		// Bugfix - reset sessionAtributes if they are not correct
		if (currentUser.getId() != 0 != isLogged) {
			isLogged = isLogged();
			currentUser = currentUser();
		}

		// Add objects to ModelAndView
		model.addObject("currentUser", currentUser);
		model.addObject("isLogged", isLogged);

		return model;
	}

//-------------------------------------------------------------------------
//								Login page
//-------------------------------------------------------------------------	
	@RequestMapping(value = "/Accessor/login/")
	public ModelAndView loginPage(@ModelAttribute("currentUser") User currentUser,
			@ModelAttribute("isLogged") Boolean isLogged, User user, BindingResult result) {

		ModelAndView model = null;
		// Bugfix - reset sessionAtributes if they are not corresponding
		if (currentUser.getId() != 0 != isLogged) {
			isLogged = isLogged();
			currentUser = currentUser();
		}

		// If user is logged - redirect to main
		if (isLogged) {

			model = new ModelAndView("main");
			model.addObject("currentUser", currentUser);
			model.addObject("isLogged", isLogged);
			return model;
		}

		// Check if page is called for the first time, or is a login attempt
		try {
			if (user.getLogin() == null || user.getPassword() == null) {
				user = context.getBean("loginAttempt", User.class);
				model = new ModelAndView("login");
				model.addObject("user", user);
			} else {
				// Check if user exists in DB, if true, then try to login
				if (DAO.isLoginInDB(user)) {
					if (DAO.loginAttempt(user)) {
						// Login successful

						currentUser = DAO.getUser(user.getLogin());
						isLogged = true;

						model = new ModelAndView("main");
						model.addObject("currentUser", currentUser);
						model.addObject("isLogged", isLogged);
						logger.trace("Login attempt succesfull. User class : "+ currentUser.getUserClass() +" Username : " + currentUser.getFirstName() + " " + currentUser.getLastName());
					}
					// Password incorrect
					else {
						model = new ModelAndView("login");
						model.addObject("user", user);
						logger.trace("Login attempt unsuccesfull. Wrong password");
					}
				}
				// User login don't exist in DB
				else {
					model = new ModelAndView("login");
					model.addObject("user", user);
					logger.trace("Login attempt unsuccesfull. No such login");
				}
			}
		} catch (Exception e) {
			logger.error("Error at login attempt : "+e.getMessage()+" User data : " + user.toString());
		}
		return model;
	}

//-------------------------------------------------------------------------
//								Logout
//-------------------------------------------------------------------------	
	@RequestMapping(value = "/Accessor/logout/")
	public ModelAndView logout(@ModelAttribute("currentUser") User currentUser,
			@ModelAttribute("isLogged") Boolean isLogged) {

		ModelAndView model = new ModelAndView("main");
		if (isLogged) {
			currentUser = currentUser();
			isLogged = isLogged();
			logger.trace("User logged out");
		}
		model.addObject("currentUser", currentUser);
		model.addObject("isLogged", isLogged);
		return model;
	}

//-------------------------------------------------------------------------
//									Create User - Validation
//-------------------------------------------------------------------------	
	@RequestMapping(value = "/Accessor/createUser/")
	public ModelAndView createUser(@ModelAttribute(binding = false, name = "currentUser") User currentUser,
			@ModelAttribute(binding = false, name = "isLogged") Boolean isLogged,
			@ModelAttribute(binding = true, value = "createdUser") User createdUser, BindingResult result) {

		ModelAndView model = null;
		// Bugfix - reset sessionAtributes if they are not corresponding
		if (currentUser.getId() != 0 != isLogged) {
			isLogged = isLogged();
			currentUser = currentUser();
		}

		// Check if user is logged as an admin
		//Only admin can create user
		if (currentUser.getUserClass().equals("Admin") & isLogged) {

			//First calling - creating empty user to fill in form
			if (createdUser.getFirstName() == null & createdUser.getLastName() == null) {
				logger.trace("Debug : page called for the first time, first and last name empty");
				model = new ModelAndView("createUser");
				model.addObject("createdUser", createdUser);
				return model;
			} else {
				//Call validator with data from form
				logger.trace("Validating user creation");
				try {
					userImplValidator.validate(createdUser, result);
				} catch (Exception e) {
					logger.error("Error at user creation validation : " +e.getMessage() + " Data from form : " + createdUser.toString());
				}
			}

				//Repopulate form after failed user creation try
			if (result.hasErrors()) {
				logger.trace("User creation validation result : incorrect");
				model = new ModelAndView("createUser");
				model.addObject("createdUser", createdUser);
			} else {
				logger.trace("User creation validation result : correct");
				model = new ModelAndView("userAdded");

				try {
					// Finish creating user - convert names to login, initialize missing variables
					createdUser.setAccessRights(new ArrayList<Rights>());
					createdUser.setAdminRights(new ArrayList<Rights>());
					createdUser.setAccessRightsCoded("");
					createdUser.setAdminRightsCoded("");

					logger.debug("Created user data : " + createdUser.toString());
					
					// Send uncoded password to a view file
					model.addObject("password", createdUser.getPassword());

					// Create login base
					String loginBase = createLoginBase(createdUser);

					// Check if login is already in db, if it is, add number;
					if (DAO.isLoginInDB(loginBase)) {
						for (int i = 1;; i++) {
							String checkedLogin = loginBase + i;
							if (DAO.isLoginInDB(checkedLogin) == false) {
								createdUser.setLogin(checkedLogin);
								break;
							}
						}
					} else {
						createdUser.setLogin(loginBase);
					}

					// Send added user to DB
					DAO.saveUser(createdUser);
				} catch (Exception e) {
					logger.error("Creating user form validated data unsuccessful : " +e.getMessage() + " user data : " + createdUser.toString());
				}

				model.addObject("createdUser", createdUser);
			}

		}
		// User is not an logged admin - redirect to main
		else {
			model = new ModelAndView("main");
		}

		model.addObject("currentUser", currentUser);
		model.addObject("isLogged", isLogged);
		return model;
	}

//-------------------------------------------------------------------------
//	view Rights - Dynamic view
//-------------------------------------------------------------------------	
	//Dynamic view is realised in jsp with <c:foreach> JSTL tags
	@RequestMapping(value = "/Accessor/rights/")
	public ModelAndView viewRights(@ModelAttribute("currentUser") User currentUser,
			@ModelAttribute("isLogged") Boolean isLogged) {

		if (currentUser.getId() != 0 != isLogged) {
			isLogged = isLogged();
			currentUser = currentUser();
		}
		ModelAndView model = null;
		if (isLogged) {
			model = new ModelAndView("viewRights");
		} else {
			model = new ModelAndView("main");
		}
		model.addObject("currentUser", currentUser);
		model.addObject("isLogged", isLogged);
		return model;
	}

//-------------------------------------------------------------------------
//								set Rights - Dynamic forms
//-------------------------------------------------------------------------	
	//Set and update user rights
	//Admins and bosses can set admin rights
	//Simple users can only set access rights
	@RequestMapping(value = "/Accessor/setRights/")
	public ModelAndView setRights(@ModelAttribute("currentUser") User currentUser,
			@ModelAttribute("isLogged") Boolean isLogged, @ModelAttribute("choosenUser") User choosenUser) {

		if (currentUser.getId() != 0 != isLogged) {
			isLogged = isLogged();
			currentUser = currentUser();
		}

		ModelAndView model = null;
		if (isLogged) {
			// Page is called for the first time - create modelAttribute and send it to view file
			if (choosenUser.getId() == 0) {

				model = new ModelAndView("chooseUser");

				// Send users list do model file as map
				// Key is user ID, value is first and last name of user
				List<User> usersList = DAO.listUsers();
				Map<Integer, String> usersMap = new LinkedHashMap<Integer, String>();
				for (User user : usersList) {
					usersMap.put(user.getId(), user.getFirstName() + " " + user.getLastName());
				}
				model.addObject("usersMap", usersMap);

			}
			// Page is called for the next time - display changeable rights of chosen user
			else {

				try {
					choosenUser = (User) DAO.getUser(choosenUser.getId());

					model = new ModelAndView("setRights");
					model.addObject("choosenUser", choosenUser);

					// Prepare lists with all rights, which user/admin can set (options in view
					// select tag)
					List<Rights> accessRightsToChangeOption = new ArrayList<Rights>();
					List<Rights> adminRightsToChangeOption = new ArrayList<Rights>();
					// Prepare list for select tag in view
					List<Rights> rightsList = new ArrayList<Rights>();
					Integer numberOfAccessRights = 0;
					Integer numberOfAdminRights = 0;

					// Write all accessRights giveable by user to list
					// For user with admin right FIN.2 it would be:
					// FIN.0 FIN.1 FIN.2
					for (Rights rights : currentUser.getAdminRights()) {
						rightsList.add(rights);
						logger.trace("added " + rights.getDepartment().getPrefix() + rights.getLevel()
								+ " to rightsList for setRights");
						numberOfAccessRights += 1;
						// Check if currentUser can change right of person (is admin, or have higher
						// level-access
						if (isAccessRightChangeable(rights, choosenUser.getAccessRights())
								|| currentUser.getUserClass().equals("Admin")) {
							for (int i = 0; i <= rights.getLevel(); i++) {
								accessRightsToChangeOption.add(new Rights(rights.getDepartment(), i));
								logger.trace("added " + rights.getDepartment().getPrefix() + i
										+ " to accessRightsToChangeOption for setRights");
							}
						}
					}
					// Write all adminRights giveable by user to list
					// For admin/boss with admin right FIN.2 it would be:
					// FIN.0 FIN.1 FIN.2
					// Only admin/boss can set those rights
					if (currentUser.getUserClass().equals("User") == false) {
						for (Rights rights : currentUser.getAdminRights()) {
							rightsList.add(rights);
							logger.trace("added " + rights.getDepartment().getPrefix() + rights.getLevel()
									+ " to rightsList for setRights");
							numberOfAdminRights += 1;
							if (isAdminRightChangeable(rights, choosenUser.getAdminRights())
									|| currentUser.getUserClass().equals("Admin")) {
								for (int i = 0; i <= rights.getLevel(); i++) {
									adminRightsToChangeOption.add(new Rights(rights.getDepartment(), i));
									logger.trace("added " + rights.getDepartment().getPrefix() + i
											+ " to adminRightsToChangeOption for setRights");
								}
							}
						}
					}
					
					
					// Add number of access rights to number of admin rights
					// Right list is common for both rights
					logger.trace("numberOfAccessRights" + numberOfAccessRights);
					logger.trace("numberOfAdminRights" + numberOfAdminRights);
					numberOfAdminRights += numberOfAccessRights;

					// accessRightsToChangeOption.get(i);
					model.addObject("accessRightsToChangeOption", accessRightsToChangeOption);
					model.addObject("adminRightsToChangeOption", adminRightsToChangeOption);
					model.addObject("numberOfAccessRights", numberOfAccessRights);
					model.addObject("numberOfAdminRights", numberOfAdminRights);
					model.addObject("rightsList", rightsList);
					// Plain object for returning result
					RightsListWrapper changedRightsList = new RightsListWrapper(new ArrayList<Rights>());

					model.addObject("changedRightsList", changedRightsList);
				} catch (Exception e) {
					logger.error("Error while listing setable rights : " +e.getMessage()+" of user :" + choosenUser.toString());
				}

			}
		} else {
			model = new ModelAndView("main");
		}

		model.addObject("currentUser", currentUser);
		model.addObject("isLogged", isLogged);
		return model;
	}

	//Display data received from setRights form, and update user in DB
	@RequestMapping(value = "/Accessor/changeRights/")
	public ModelAndView changeRights(@ModelAttribute("currentUser") User currentUser,
			@ModelAttribute("isLogged") Boolean isLogged,
			@ModelAttribute(name = "changedRightsList") RightsListWrapper changedRightsList) {

		if (currentUser.getId() != 0 != isLogged) {
			isLogged = isLogged();
			currentUser = currentUser();
		}

		ModelAndView model = null;
		if (isLogged) {
			
			try {
				logger.debug("received right : " + changedRightsList);

				logger.debug("numberOfAccessRights : " + changedRightsList.getNumberOfAccessRights());
				logger.debug("numberOfAdminRights : " + changedRightsList.getNumberOfAdminRights());

				User choosenUser = DAO.getUser(changedRightsList.getChosenUserId());
				
				logger.debug("rights changed to " + choosenUser.toString());
				model = new ModelAndView("rightsChanged");
				
				logger.debug("changedRightsList : " + changedRightsList.getList().toString());
				
				//Change user
				for (int i = 0; i<changedRightsList.getNumberOfAccessRights(); i++) {
					choosenUser.changeAccessRight(changedRightsList.getList().get(i));
				}
				for (int i = changedRightsList.getNumberOfAccessRights(); i<changedRightsList.getNumberOfAdminRights(); i++) {
					choosenUser.changeAdminRight(changedRightsList.getList().get(i));
				}
				
				logger.debug("choosenUser.accessRights : " +choosenUser.getAccessRights().toString());
				logger.debug("choosenUser.AdminRights : " +choosenUser.getAdminRights().toString());
				
				//Update user in hibernate
				DAO.updateUser(choosenUser);
				
				//Send user to view
				model.addObject("choosenUser",choosenUser);
			} catch (Exception e) {
				logger.error("Error at updating user rights : "+e.getMessage()+"More data accessable at DEBUG logs level");
			}
		} else {
			model = new ModelAndView("main");
		}

		model.addObject("currentUser", currentUser);
		model.addObject("isLogged", isLogged);
		return model;
	}

//-------------------------------------------------------------------------
//								view Documents
//-------------------------------------------------------------------------	
	//List accessable documents from DB and send to view
	@RequestMapping(value = "/Accessor/documents/")
	public ModelAndView documents(@ModelAttribute("currentUser") User currentUser,
										@ModelAttribute("isLogged") Boolean isLogged) {

		if (currentUser.getId() != 0 != isLogged) {
			isLogged = isLogged();
			currentUser = currentUser();
		}

		ModelAndView model = null;
		if (isLogged) {
			model = new ModelAndView("viewDocuments");
			
			//List all accessable rights
			List<Rights>accessableRights = new ArrayList<Rights>();
			
			for (Rights rights : currentUser.getAccessRights()) {
				//Add lower level accesses to list
				for (int i=1; i<=rights.getLevel();i++) {
					accessableRights.add(new Rights(rights.getDepartment(),i));
				}
			}
			
			List<Document> accessableDocuments = null;
			try {
				accessableDocuments = DAO.listAccessableDocuments(accessableRights);
			} catch (Exception e) {
				logger.error("Error at listingAccessableDocuments : " + e.getMessage() + " accessableRights : " + accessableRights.toString());
			}
			
			model.addObject("accessableDocuments", accessableDocuments);
			//Send empty bean to view
			model.addObject("requestedDocument", new Document("",null));
			
		} else {
			model = new ModelAndView("main");
		}
		model.addObject("currentUser", currentUser);
		model.addObject("isLogged", isLogged);
		return model;
	}
	
	//Read choosen document
	@RequestMapping(value = "/Accessor/readDocument/")
	public ModelAndView readDocument(@ModelAttribute("currentUser") User currentUser,
										@ModelAttribute("isLogged") Boolean isLogged,
										@ModelAttribute("requestedDocument") Document requestedDocument) {
		if (currentUser.getId() != 0 != isLogged) {
			isLogged = isLogged();
			currentUser = currentUser();
		}

		ModelAndView model = null;
		if (isLogged & requestedDocument!=null) {
			model = new ModelAndView("document");
			
			model.addObject("requestedDocument",requestedDocument);
			
		} else {
			model = new ModelAndView("main");
		}
		model.addObject("currentUser", currentUser);
		model.addObject("isLogged", isLogged);
		return model;
	}
	
//-------------------------------------------------------------------------
//							Functions
//-------------------------------------------------------------------------	

	//Create login like "GRZSLO" using first and last name
	private String createLoginBase(User user) {
		try {
			String loginBase = user.getFirstName().substring(0, 3) + user.getLastName().subSequence(0, 3);
			loginBase = Normalizer.normalize(loginBase, Normalizer.Form.NFD);
			Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
			loginBase = pattern.matcher(loginBase).replaceAll("").toUpperCase();
			return loginBase.replaceAll("[Ł]", "L"); // ' Ł ' needs special treatment
														// does Java want to say me something? :) (code's author name is
														// Łukasz)
		} catch (Exception e) {
			logger.error("Error at createLoginBase " + e.getMessage() + " user names : " + user.getFirstName()+ user.getLastName());
			return null;
		}
	}

//---------------------Reset DB with default data
	public void resetDB(AccessorDAO dao) {
		try {
			// List all users
			List<User> users = dao.listUsers();

			// Then delete them one by one
			for (User user : users) {
				dao.deleteUser(user);
			}

			// Next send default users to DB
			dao.saveUser(new User("Grzegorz", "Słodowy", "GRZSLO", "kozak123!", "IT.2", "P.3_FIN.3_IT.3_PRO.3", "Admin"));
			dao.saveUser(new User("Kaja", "Kozłowska", "KAJKOZ", "asdf5%56", "P.3_FIN.2_IT.1", "P.3", "Boss"));
			dao.saveUser(new User("Grzegorz", "Przykładek", "GRZPRZ", "porszak_42", "IT.3_FIN.2_P.2", "IT.3", "Boss"));
			dao.saveUser(new User("Tomasz", "Psikuta", "TOMPSI", "bezEs_12", "FIN.3_P.2_IT.1", "FIN.3", "Boss"));
			dao.saveUser(new User("Henryk", "Sienkiewicz", "HENSIE", "Kmitzitz_8F", "PRO.3_FIN.2_P.2", "PRO.3", "Boss"));
			dao.saveUser(new User("Eliza", "Orzeszkowa", "ELIORZ", "Niemno_%$#", "FIN.2", "FIN.2", "User"));
			dao.saveUser(new User("Aleksander", "Hamilton", "ALEHAM", "Ju5T_yOu_Wa1T", "PRO.2_FIN.1", "PRO.2", "User"));
			dao.saveUser(new User("Aaron", "Burr", "AARBUR", "Wait_for_1T", "PRO.2", "PRO.1", "User"));
			dao.saveUser(new User("Andżelika", "Schyler", "ANDSCH", "Hell_ple55", "FIN.1_P.2", "P.1", "User"));
			dao.saveUser(new User("Conrad", "Duma", "CONDUM", "darkne55_S", "PRO.1", "", "User"));
			dao.saveUser(new User("Paweł", "Komarewicz", "PAWKOM", "Mar_but$4", "FIN.1", "", "User"));

			// List all departments
			List<Department> departments = dao.listDepartments();

			// Then delete them one by one
			for (Department department : departments) {
				dao.deleteDepartment(department);
			}
			dao.saveDepartment(context.getBean("finDep", Department.class));
			dao.saveDepartment(context.getBean("pDep", Department.class));
			dao.saveDepartment(context.getBean("proDep", Department.class));
			dao.saveDepartment(context.getBean("itDep", Department.class));
		} catch (Exception e) {
			logger.fatal("Fatal exception at reseting DB : " + e.getMessage());
		}
	}

	// Check if the right is changeable by non-admin user
	boolean isAccessRightChangeable(Rights adminRight, List<Rights> choosenUserRights) {
		boolean choosenUserHaveThoseRights = false;
		
		try {
			if (choosenUserRights != null) {
				logger.debug("chosenUserRight !=null");
				for (Rights right : choosenUserRights) {
					logger.debug(adminRight.getDepartment().getPrefix() + "---" + right.getDepartment().getPrefix());
					if (adminRight.getDepartment().getPrefix().equals(right.getDepartment().getPrefix())) {
						choosenUserHaveThoseRights=true;
						logger.debug(adminRight.getLevel() + "---" + right.getLevel());
						if (adminRight.getLevel() >= right.getLevel()) {
							// Return true if for the same department the user who want to change others
							// right
							// Have rights of higher level
							return true;
						}
					}
				}
			}
			else {
				return true;
			}
			
			if (choosenUserHaveThoseRights==false) {
				return true;
			}
		} catch (Exception e) {
			logger.error("isAccessRightChangeable error : " +e.getMessage() + " adminRight : " +adminRight + " choosenUserRights : " + choosenUserRights.toString());
		}
		return false;
	}
	
	// Check if the right is changeable by non-admin user
		boolean isAdminRightChangeable(Rights adminRight, List<Rights> choosenUserRights) {
			boolean choosenUserHaveThoseRights = false;
			
			try {
				if (choosenUserRights != null) {
					logger.debug("chosenUserRight !=null");
					for (Rights right : choosenUserRights) {
						logger.debug(adminRight.getDepartment().getPrefix() + "---" + right.getDepartment().getPrefix());
						if (adminRight.getDepartment().getPrefix().equals(right.getDepartment().getPrefix())) {
							logger.debug(adminRight.getLevel() + "---" + right.getLevel());
							choosenUserHaveThoseRights=true;
							if (adminRight.getLevel() > right.getLevel()) {
								// Return true if for the same department the user who want to change others
								// right
								// Have rights of higher level
								return true;
							}
						}
					}
				}
				else {
					return true;
				}
				
				if (choosenUserHaveThoseRights==false) {
					return true;
				}
			} catch (Exception e) {
				logger.error("isAdminRightChangeable error : " +e.getMessage() + " adminRight : " +adminRight + " choosenUserRights : " + choosenUserRights.toString());
			}
			return false;
		}

//-------------------------------------------------------------------------
//						#End of Functions
//-------------------------------------------------------------------------	
}