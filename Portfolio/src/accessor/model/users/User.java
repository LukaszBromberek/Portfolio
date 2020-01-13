package accessor.model.users;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import accessor.supportClasses.rights.Rights;

@Entity(name="UserImpl")
@Table(name="users")
public class User {
	private static final Logger logger = LogManager.getLogger(User.class);
	//---------------------------------------------------------------------------------------------------------------------
	//											FIELDS DECLARATION
	//---------------------------------------------------------------------------------------------------------------------
	
	@Id
	@Column(name="user_id")
	private int id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column (name="last_name")
	private String lastName;
	
	@Column(name="login")
	private String login;
	
	@Column(name="password")
	private String password;
	
	@Column(name="access_rights")
	private String accessRightsCoded;
	
	@Column(name="admin_rights")
	private String adminRightsCoded;
	
	@Transient
	private List<Rights> accessRights;
	@Transient
	private List<Rights> adminRights;
	
	@Column(name="class")
	private String userClass;

	//---------------------------------------------------------------------------------------------------------------------
	//										#END OF FIELDS DECLARATION
	//---------------------------------------------------------------------------------------------------------------------
	
	//---------------------------------------------------------------------------------------------------------------------
	//											CONSTRUCTOR
	//---------------------------------------------------------------------------------------------------------------------	
	
	public User(int id, String firstName, String lastName, String login, String password) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.login = login;
		this.password = password;
	}
	
	
	public User(String firstName, String lastName, String login, String password, String accessRightsCoded,
			String adminRightsCoded, String userClass) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.login = login;
		this.password = password;
		this.accessRightsCoded = accessRightsCoded;
		this.adminRightsCoded = adminRightsCoded;
		this.userClass = userClass;
	}



	public User(String firstName, String lastName, String login, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.login = login;
		this.password = password;
	}
	
	public User() {
	}



	//---------------------------------------------------------------------------------------------------------------------
	//										#END OF CONSTRUCTOR
	//---------------------------------------------------------------------------------------------------------------------	
	
	//---------------------------------------------------------------------------------------------------------------------
	//										GETTERS AND SETTERS
	//---------------------------------------------------------------------------------------------------------------------
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAccessRightsCoded() {
		return accessRightsCoded;
	}
	public void setAccessRightsCoded(String accessRightsCoded) {
		this.accessRightsCoded = accessRightsCoded;
	}
	public String getAdminRightsCoded() {
		return adminRightsCoded;
	}
	public void setAdminRightsCoded(String adminRightsCoded) {
		this.adminRightsCoded = adminRightsCoded;
	}
	public List<Rights> getAccessRights() {
		return accessRights;
	}
	public void setAccessRights(List<Rights> accessRights) {
		this.accessRights = accessRights;
	}
	public List<Rights> getAdminRights() {
		return adminRights;
	}
	public void setAdminRights(List<Rights> adminRights) {
		this.adminRights = adminRights;
	}
	public String getUserClass() {
		return userClass;
	}
	public void setUserClass(String userClass) {
		this.userClass = userClass;
	}
	
	//---------------------------------------------------------------------------------------------------------------------
	//									#END OF	GETTERS AND SETTERS
	//---------------------------------------------------------------------------------------------------------------------
	


	@Override
	public String toString() {
		String ret = "UserImpl [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", login=" + login
				+ ", password=" + password + ", accessRightsCoded=" + accessRightsCoded + ", adminRightsCoded="
				+ adminRightsCoded + ", accessRights=";
		if(this.accessRights!=null) {
		for (Rights right : accessRights) {
			ret=ret+right.toString();
		}}
		ret = ret+ accessRights + ", adminRights=";
		if(this.adminRights!=null) {
		for (Rights right : adminRights) {
			ret=ret+right.toString();
		}}
		ret=ret+ ", userClass="	+ userClass + "]";
		return ret;
	}
	
	//Change accessRight in User instance
	public void changeAccessRight (Rights right) {
		logger.debug("right " + right + " accessRightsBeforeChange " + accessRights);
		boolean departmentAlreadyInList = false;
		
		//If user have access rights, check them for right for given department
		if(accessRights!=null) {
			for (int i=0;i<accessRights.size();i++){
				//Check if right is of same department
				if (accessRights.get(i).getDepartment().getPrefix().equals(right.getDepartment().getPrefix())) {
					departmentAlreadyInList=true;
					//If right level is bigger than zero, update
					if(right.getLevel()>0) {
						logger.debug("rightPrefix " + right.getDepartment().getPrefix()+ " accessrightPref " + accessRights.get(i).getDepartment().getPrefix());
						accessRights.get(i).setLevel(right.getLevel());
					}
					//If level equals than 0, delete right
					else {
						this.accessRights.remove(i);
					}
				}
			}
		}
		//If user don't have right of given department - add right to list
		if(departmentAlreadyInList==false & right.getLevel()>0) {
			this.accessRights.add(right);
		}
	}
	
	//Change adminRight in User instance
	public void changeAdminRight (Rights right) {
		boolean departmentAlreadyInList = false;
		
		//If user have access rights, check them for right for given department
		if(adminRights!=null) {
			for (int i=0;i<adminRights.size();i++){
				//Check if right is of same department
				if (adminRights.get(i).getDepartment().getPrefix().equals(right.getDepartment().getPrefix())) {
					departmentAlreadyInList=true;
					//If right level is bigger than zero, update
					if(right.getLevel()>0) {
						adminRights.get(i).setLevel(right.getLevel());
					}
					//If level equals than 0, delete right
					else {
						this.accessRights.remove(i);
					}
				}
			}
		}
		//If user don't have right of given department - add right to list
		if(departmentAlreadyInList==false & right.getLevel()>0) {
			this.adminRights.add(right);
		}
	}
}