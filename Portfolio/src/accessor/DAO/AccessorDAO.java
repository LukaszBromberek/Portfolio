package accessor.DAO;

import java.util.List;

import accessor.Document.Document;
import accessor.model.departments.Department;
import accessor.model.users.User;
import accessor.supportClasses.rights.Rights;

public interface AccessorDAO {
	
	
	//------------------------------------------------------------
	//							USER
	//------------------------------------------------------------
	// List users classes
	public List<User> listUsers();	
	
	//loginAttempt
	public boolean loginAttempt(User user);
	public boolean isLoginInDB(User user);
	public boolean isLoginInDB(String login);
	
	//CRUD
	//Create
	public void saveUser (User user);
	
	//Read 
	public User getUser (String login);
	public User getUser (int id);
	
	//Update
	public void updateUser (User user);
	
	//Delete
	public void deleteUser (User user);
	
	//------------------------------------------------------------
	//						#END OF	USER
	//------------------------------------------------------------
	
	//------------------------------------------------------------
	//						DEPARTMENT
	//------------------------------------------------------------
	
	// List all departments
	public List<Department> listDepartments();
	
	//CRUD
	//Create
	public void saveDepartment (Department department);
	
	//Delete
	public void deleteDepartment(Department department);
	
	//------------------------------------------------------------
	//					#END OF	DEPARTMENT
	//------------------------------------------------------------
	
	public void closeFactory();
	List<Document> listDocuments();
	public List<Document> listAccessableDocuments(List<Rights> rightsList);
}
