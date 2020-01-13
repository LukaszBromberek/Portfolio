package accessor.model.departments;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="Departments")
@Table(name="departments")
public class Department {
	//---------------------------------------------------------------------------------------------------------------------
	//											FIELDS DECLARATION
	//---------------------------------------------------------------------------------------------------------------------
	@Id
	@Column(name="department_id")
	private int departmentId;
	@Column(name="prefix")
	private String prefix;
	@Column(name="name")
	private String name;
	
	//--------------------------------------------------------------------------------------------------------------------
	//										#END OF FIELDS DECLARATION
	//---------------------------------------------------------------------------------------------------------------------
	
	//---------------------------------------------------------------------------------------------------------------------
	//											CONSTRUCTOR
	//---------------------------------------------------------------------------------------------------------------------	
	
	
	public Department(String prefix, String name) {
		this.prefix = prefix;
		this.name = name;
	}
	
	public Department(int departmentId, String prefix, String name) {
		this.departmentId = departmentId;
		this.prefix = prefix;
		this.name = name;
	}

	public Department() {
	}

	//---------------------------------------------------------------------------------------------------------------------
	//										#END OF CONSTRUCTOR
	//---------------------------------------------------------------------------------------------------------------------	
	
	//---------------------------------------------------------------------------------------------------------------------
	//										GETTERS AND SETTERS
	//---------------------------------------------------------------------------------------------------------------------
	
	
	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getPrefix() {
		return prefix;
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	
	//---------------------------------------------------------------------------------------------------------------------
	//									#END OF	GETTERS AND SETTERS
	//---------------------------------------------------------------------------------------------------------------------
	
	//---------------------------------------------------------------------------------------------------------------------
	//											STATIC FUNCTIONS
	//---------------------------------------------------------------------------------------------------------------------
	
	//---------------------------------------------------------------------------------------------------------------------
	//								#END OF		STATIC FUNCTIONS
	//---------------------------------------------------------------------------------------------------------------------
	
	@Override
	public String toString() {
		return "Department [prefix=" + prefix + ", name=" + name + "]";
	}	
	
}
