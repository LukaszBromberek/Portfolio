package bloodReserves.model;

//Class for sending data to view file
public class BloodReservesResult {
	private String date;
	private String city;
	private String type;
	private String level;
	
	public BloodReservesResult(String date, String city, String type, String level) {
		this.city = city;
		this.date = date;
		this.type = type;
		this.level = level;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}

	public BloodReservesResult() {
	}

	//Get query from blood reserves model, or something like that
}
