package accessor.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.sql.DataSource;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.query.Query;

import accessor.Document.Document;
import accessor.model.departments.Department;
import accessor.model.users.User;
import accessor.supportClasses.rights.Rights;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class AccessorDAOImpl implements AccessorDAO {
	//------------------------------------------------------------
	//						FIELD DECLARATION
	//------------------------------------------------------------
	private SessionFactory hibernateFactory;
	private static final Logger logger = LogManager.getLogger(AccessorDAOImpl.class);
	
	//------------------------------------------------------------
	//				#END OF FIELD DECLARATION
	//------------------------------------------------------------
	
	//------------------------------------------------------------
	//						CONSTRUCTOR/CLOSE
	//------------------------------------------------------------
	
//Initialize hibernate
public AccessorDAOImpl(DataSource dataSource) {
	
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
	
		try {
			hibernateFactory = new Configuration()
					.configure("accessor/DAO/hibernate.cfg.xml")
					.addAnnotatedClass(User.class)
					.addAnnotatedClass(Department.class)
					.addAnnotatedClass(Document.class)
					.buildSessionFactory();
		} catch (HibernateException e) {
			logger.fatal("Error at hibernate initialization : " + e.getMessage());
		}
	}

    //Close session factory
	public void closeFactory() {
		logger.info("DAO.closeFactory called");
		hibernateFactory.close();
	}
	
	//------------------------------------------------------------
	//				#END OF CONSTRUCTOR/CLOSE
	//------------------------------------------------------------

	//------------------------------------------------------------
	//						USER
	//------------------------------------------------------------
	//List all users in DB
	@SuppressWarnings("unchecked")
	//------------------------LIST USERS-------------------------
	@Override
	public List<User> listUsers() {
		List<User> users = null;
		Session session = hibernateFactory.getCurrentSession();
		
		try {
			session.beginTransaction();
			users = session.createQuery("from UserImpl").list();
			session.getTransaction().commit();
			
			List<Department> departmentsList = this.listDepartments();
			
			for (User user : users) {
				user.setAccessRights(AccessorDAOImpl.decodeRights(user.getAccessRightsCoded(),departmentsList));
				user.setAdminRights(AccessorDAOImpl.decodeRights(user.getAdminRightsCoded(),departmentsList));
			}
		} catch (Exception e) {
			logger.error("Error at listing users : " + e.getMessage());
		}
		finally {
			if(session.isOpen()) {session.close();}
		}
		
		return users;
	}


	//------------------------CRUD-------------------------
	//------------------------CREATE-------------------------
	@Override
	public void saveUser(User user) {
		Session session = hibernateFactory.getCurrentSession();
		session.beginTransaction();
		
		String md5hash = DigestUtils.md5Hex(user.getPassword().getBytes());
		user.setPassword(md5hash);
		
		session.save(user);
		session.getTransaction().commit();
		session.close();
	}

	//-----------------------READ-------------------------------
	@SuppressWarnings("unchecked")
	@Override
	public User getUser(String login) {
		Session session = hibernateFactory.getCurrentSession();
		
		session.beginTransaction();
		
		try {
			//Search DB for user with given login
			//HQL injection save
			Query <User> query =  session.createQuery("from UserImpl where login = :login").setParameter("login", login);
			List <User> resultList = query.list();
			
			//If list is empty, return false, if there's a record, return true (logins are unique)
			if(!resultList.isEmpty()) {
				session.close();
				User retUser = resultList.get(0);
				//Decode access/admin rights individually
				retUser.setAccessRights(this.decodeRights(retUser.getAccessRightsCoded()));
				retUser.setAdminRights(this.decodeRights(retUser.getAdminRightsCoded()));
				return retUser;
			}
		} catch (Exception e) {
			logger.error("Error at reading user from DB : " + e.getMessage());
		}finally {
			if(session.isOpen()) {session.close();}
		}
		return null;
	}
	
	public User getUser (int id)
	{
		User user = null;
		Session session = hibernateFactory.getCurrentSession();
		
		try {
			session.beginTransaction();
			
			user = session.get(User.class, id);
			session.close();
			//Decode access/admin rights individually
			user.setAccessRights(this.decodeRights(user.getAccessRightsCoded()));
			user.setAdminRights(this.decodeRights(user.getAdminRightsCoded()));
		} catch (Exception e) {
			logger.error("Error at geting user : " + e.getMessage());
		}finally {
			if(session.isOpen()) {session.close();}
		}
		return user;
	}

	//------------------------UPDATE-------------------------
	@Override
	public void updateUser(User user) {
	
		Session session = null;
		try {
			user.setAccessRightsCoded(this.codeRights(user.getAccessRights()));
			user.setAdminRightsCoded(this.codeRights(user.getAdminRights()));
			
			session = hibernateFactory.getCurrentSession();
			session.beginTransaction();
			
			session.update(user);
			session.getTransaction().commit();
		} catch (Exception e) {
			logger.error("Error while updating user in DB : " + e.getMessage());
		}finally {
			if(session.isOpen()) {session.close();}
		}
	}

	//------------------------DELETE-------------------------
	@Override
	public void deleteUser(User user) {
		Session session = hibernateFactory.getCurrentSession();
		session.beginTransaction();
		
		try {
			session.delete(user);
			session.getTransaction().commit();
		} catch (Exception e) {
			logger.error("Error at deleting user : " + e.getMessage());
		}finally {
		session.close();
		}
	}
	//----------------------#END OF CRUD-----------------------
	
	//------------------------FUNCTIONS------------------------
	
	//Login attempt, true if credentials are correct with md5
	@Override
	public boolean loginAttempt(User user) {
		User dbUser = this.getUser(user.getLogin());
		
		String md5hash = DigestUtils.md5Hex(user.getPassword().getBytes());
		
		//If login and password are the same, then return true;
		if (user.getLogin().equals(dbUser.getLogin())&md5hash.equals(dbUser.getPassword()))
		{
			return true;
		}
		
		return false;
	}
	
	//Check if user is in DB
	@Override
	public boolean isLoginInDB(User user) {
		
		//Check if given user is in DB
		if(this.getUser(user.getLogin())!=null)
		{
			return true;
		}
		
		return false;
	}
	
	//Check if login is in
	@Override
	public boolean isLoginInDB(String login) {
		
		//Check if given user is in DB
		if(this.getUser(login)!=null)
		{
			return true;
		}
		
		return false;
	}
	
	//-----------------------------CODE AND DECODE FUNCTIONS
	//Decode rights code from DB using departments list from DB
	public List<Rights> decodeRights (String codedRights){
		// Code from DB example P.3_PRO.2_FIN.1_IT.1
		return AccessorDAOImpl.decodeRights(codedRights, this.listDepartments());
	}

	//Decode rights code from DB using downloaded departments list
	public static List<Rights> decodeRights (String codedRights, List<Department> departmentList){
		// Code from DB example P.3_PRO.2_FIN.1_IT.1
		//Check department list
		if(departmentList==null || departmentList.isEmpty()) {return null;}
		
		//Declare returned arrayList
		List<Rights> retList = new ArrayList<Rights>();
		
		//If input is empty, send empty list
		if(codedRights==null || codedRights.equals("")) {
			return retList;
		}
		
		try {
			//Split codedRights
			String[] splitedCodedRights = codedRights.split("_");
			
			for (String string : splitedCodedRights) {
				//Split single right code to department and level of rights
				String[] splitCode = string.split("\\.");
				String depPrefix = splitCode[0];
				int level = Integer.valueOf(splitCode[1]);
				
				//Look for department in department list
				Department codeDepartment = null;
				
				for (Department dep : departmentList) {
					if(depPrefix.equals(dep.getPrefix())) {
						codeDepartment = dep;
					}
				}
				
				//Add decoded Right to list
				retList.add(new Rights(codeDepartment,level));
			}
		} catch (Exception e) {
			logger.error("Error at decodeRights : " +e.getMessage());
			return null;
		}
		return retList;
	}

	//Code rights and check if their departments are in DB
	public String codeRights (List<Rights> rights) {
		return AccessorDAOImpl.codeRights(rights, this.listDepartments());
	}
	
	//Code rights and check if their departments are in given list
	public static String codeRights (List<Rights> rights, List<Department> departmentList) {
		//Check input
		if(rights==null || rights.isEmpty() || departmentList==null || departmentList.isEmpty()) {return null;}
		String ret="";
		
		try {
			//Loop over all rights, then code them
			for (Rights right : rights) {
				//Check if department in right is in DB
				for (Department department : departmentList) {
					if(department.getPrefix().equals(right.getDepartment().getPrefix())){
						ret= ret + right.getDepartment().getPrefix() + "." + right.getLevel() + "_";
					}
				}
			}
			//Remove last '_' from string
			System.out.println("debug : ret from codeRights" + ret);
			ret = ret.substring(0, ret.length()-1);
		} catch (Exception e) {
			logger.error("Error at codeRights : " + e.getMessage());
			return null;
		}
		
		return ret;
	}
	
	//Decode single right from Document using data from DB
	public Rights decodeDocumentRight (Document document) {
		return this.decodeDocumentRight(document, this.listDepartments());
	}
	
	//Decode single right from Document using given department list
	public Rights decodeDocumentRight (Document document, List<Department> departmentList) {
		try {
			// Code from DB example P.3_PRO.2_FIN.1_IT.1
			//Check department list
			if(departmentList==null || departmentList.isEmpty()) {return null;}
			
			//If input is empty, send empty list
					if(document.getAccessRightCoded()==null || document.getAccessRightCoded().equals("")) {
						return null;
					}
					
					//Split codedRights
					String[] splitedCodedRights = document.getAccessRightCoded().split("\\.");
					
					//Look for department in department list
					Department codeDepartment = null;
					
					for (Department dep : departmentList) {
						if(splitedCodedRights[1].equals(dep.getPrefix())) {
							codeDepartment = dep;
						}
					}
					
			return new Rights(codeDepartment, Integer.valueOf(splitedCodedRights[1]));
		} catch (NumberFormatException e) {
			logger.error("Error at decodeDocumentRight : " + e.getMessage());
			return null;
		}
	}
	//-----------------------#END OF FUNCTIONS-------------------
	//------------------------------------------------------------
	//				#  		END OF USER
	//------------------------------------------------------------

	//------------------------------------------------------------
	//				  		DEPARTMENT
	//------------------------------------------------------------
	
	//------------------------LIST DEPARTMENTS--------------------
	
	//List all departments from DB
	@Override
	public List<Department> listDepartments() {
		
		Session session = hibernateFactory.getCurrentSession();
		session.beginTransaction();
		
		@SuppressWarnings("unchecked")
		List<Department> departments = session.createQuery("from Departments").list();
		
		session.getTransaction().commit();
		session.close();
		return departments;
	}

	//------------------------CRUD-------------------------
	//------------------------CREATE-------------------------
	@Override
	public void saveDepartment(Department department) {
		Session session = hibernateFactory.getCurrentSession();
		try {
			session.beginTransaction();
			
			session.save(department);
			
			session.getTransaction().commit();
		} catch (Exception e) {
			logger.error("Error at saveDepartment : " + e.getMessage());
		}finally {
		session.close();
		}
	}


	//------------------------DELETE-------------------------
	@Override
	public void deleteDepartment(Department department) {
		Session session = hibernateFactory.getCurrentSession();
		try {
			session.beginTransaction();
			
			session.delete(department);
			
			session.getTransaction().commit();
		} catch (Exception e) {
			logger.error("Error at delete department : " + e.getMessage());
		}finally {
			session.close();
		}		
	}
	//------------------------------------------------------------
	//				  #END OF DEPARTMENT
	//------------------------------------------------------------
	
	//------------------------------------------------------------
	//				  	DOCUMENT
	//------------------------------------------------------------

	//------------------------LIST DOCUMENTS--------------------

	//List all departments from DB
	@Override
	public List<Document> listDocuments() {
		
		Session session = hibernateFactory.getCurrentSession();
		session.beginTransaction();
		
		@SuppressWarnings("unchecked")
		List<Document> documents = session.createQuery("from Document").list();
		
		session.getTransaction().commit();
		session.close();
		return documents;
	}
	
	@SuppressWarnings("unchecked")
	public List<Document> listAccessableDocuments(List<Rights> rightsList){
		List<Document> documents = null;
		Session session = hibernateFactory.getCurrentSession();
		try {
			session.beginTransaction();
			
			//Query creation
			String query = "from Document WHERE (";//access_right LIKE 'prefix' OR)";
			
			//Add like from every accessable right
			for (Rights rights : rightsList) {
				query = query + " access_right LIKE '" +rights.getDepartment().getPrefix()+"."+rights.getLevel() +"' OR";
			}
			
			//Cut last 'OR' and add ')'
			query = query.substring(0, query.length()-2) + ")";
			
			System.out.println("Debug : Document query : " + query);
			documents = session.createQuery(query).list();
			
			session.getTransaction().commit();
		} catch (Exception e) {
			logger.error("Error at listAccessableDocuments : " + e.getMessage());
		}finally {
		session.close();}
		return documents;
	}
	
	//------------------------CRUD-------------------------
	//------------------------READ-------------------------
	
	//------------------------------------------------------------
	//				  	#DOCUMENT
	//------------------------------------------------------------
}
