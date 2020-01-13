package bloodReserves.process;

import java.util.ArrayList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import bloodReserves.enums.BloodType;

//Class containting single row with blood type as header,
//And blood leveles data in the number of cities
public class TableRow {

	private BloodType bloodType;
	public ArrayList<Integer> values;
	private static final Logger logger = LogManager.getLogger(TableRow.class);
	
	//Constructor
	public TableRow(String bloodType, ArrayList<Integer> values) {
		this.bloodType=bloodTypeGenerator(bloodType);
		
		this.values = values;
	}
	
	//Gives BloodType enum using string
	private BloodType bloodTypeGenerator (String bloodType) {
		switch (bloodType) {
		case "0 Rh-" :  return BloodType.ZERO_RH_N; 
		case "0 Rh+" :  return BloodType.ZERO_RH_P; 
		case "A Rh-" :  return BloodType.A_RH_N; 
		case "A Rh+" :  return BloodType.A_RH_P; 
		case "B Rh-" :  return BloodType.B_RH_N; 
		case "B Rh+" :  return BloodType.B_RH_P; 
		case "AB Rh-" :  return BloodType.AB_RH_N; 
		case "AB Rh+" :  return BloodType.AB_RH_P; 
		default: throw new IllegalArgumentException("There's no such blood type");
		}
	}
	
	//Generate table row object from raw table row
	public TableRow (String tableRow) {
		
		try {
			String row = tableRow;
			
			//Get string containing blood type for example. "A Rh+"
			//Problem with HTML code - one tag <h3> was not closed with </h3> but with <h3>
			int h3ClosingTag=0;
			if(row.indexOf("</h3>")==-1) {
				h3ClosingTag=row.lastIndexOf("<h3>");
			}
			else {
				h3ClosingTag=row.indexOf("</h3>");
			}
			
			this.bloodType=bloodTypeGenerator(row.substring(row.indexOf("<h3>")+4, h3ClosingTag));
			
			this.values = new ArrayList<Integer>();
			
			while(true) {
				int beginIndex = row.indexOf("src=")+5;
				int endIndex=row.indexOf(" alt=")-1;
				
				//End condition for loop
				if(endIndex==-2) {
					break;
				}
				
				String value = row.substring(beginIndex, endIndex);
				
				//Cut data
				row = row.substring(endIndex+6);
				
				//Give actual value
				switch (value) {
					case "img/krew0.png": values.add(Integer.valueOf(4)); break;
					case "img/krew1.png": values.add(Integer.valueOf(3)); break;
					case "img/krew2.png": values.add(Integer.valueOf(2)); break;
					case "img/krew3.png": values.add(Integer.valueOf(1)); break;
				}
			}
		} catch (Exception e) {
			logger.error("Error at TableRow : " +e.getMessage());
		}
		
	}
	
	//Print values to console
	public void printValues () {
		for (Integer value : values) {
			System.out.print(value.intValue() + " ");
		}
	}

	@Override
	public String toString() {
		String ret= "["+ ((BloodType) this.bloodType).getBloodTypeName() + ":";
		for (Integer integer : values) {
			ret=ret + " " + integer;
		}
		ret+="]";
		return ret;
	}

	public BloodType getBloodType() {
		return bloodType;
	}
	
	
}
