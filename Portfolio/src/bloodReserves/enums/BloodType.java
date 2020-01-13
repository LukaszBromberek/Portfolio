package bloodReserves.enums;

//Enum for types of blood
public enum BloodType {
	ZERO_RH_N ("0 Rh-",1),
	ZERO_RH_P ("0 Rh+",2),
	A_RH_N ("A Rh-",3),
	A_RH_P ("A Rh+",4),
	B_RH_N ("B Rh-",5),
	B_RH_P ("B Rh+",6),
	AB_RH_N ("AB Rh-",7),
	AB_RH_P ("AB Rh+",8);
	
	private String bloodTypeName;
	private int id;
	
	BloodType(String name, int dbId) {
		this.bloodTypeName=name;
		this.id = dbId;
	}

	public String getBloodTypeName() {
		return bloodTypeName;
	}
	public int getId() {
		return this.id;
	}
}
