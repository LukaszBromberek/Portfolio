package accessor.supportClasses.rights;

import accessor.model.departments.Department;

public class Rights {
	
	//---------------------------------------------------------------------------------------------------------------------
	//											FIELDS DECLARATION
	//---------------------------------------------------------------------------------------------------------------------
	
	private Department department;
	private int level;
	
	//---------------------------------------------------------------------------------------------------------------------
	//										#END OF FIELDS DECLARATION
	//---------------------------------------------------------------------------------------------------------------------
	
	//---------------------------------------------------------------------------------------------------------------------
	//											CONSTRUCTOR
	//---------------------------------------------------------------------------------------------------------------------	
	
	public Rights(Department department, int level) {
		this.department = department;
		this.level = level;
	}
	
	public Rights (String from) {
	      String[] data = from.substring(0, from.length()-1).split("\\.");
	      this.department=new Department(data[0], data[1]);
	      this.level=Integer.valueOf(data[2]);
	  }
	
	public Rights() {
	}


	//---------------------------------------------------------------------------------------------------------------------
	//										#END OF CONSTRUCTOR
	//---------------------------------------------------------------------------------------------------------------------	
	
	//---------------------------------------------------------------------------------------------------------------------
	//										GETTERS AND SETTERS
	//---------------------------------------------------------------------------------------------------------------------
	
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	//---------------------------------------------------------------------------------------------------------------------
	//									#END OF	GETTERS AND SETTERS
	//---------------------------------------------------------------------------------------------------------------------
	@Override
	public String toString() {
		return "Rights [department=" + department + ", level=" + level + "]";
	}
	
	//Return in ex. FIN.3_
	public String simplyCode() {
		return this.getDepartment().getPrefix() + "." + this.getDepartment().getName() + "." + this.getLevel() + "_";
	}	
}
