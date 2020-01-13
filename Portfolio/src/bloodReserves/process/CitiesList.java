package bloodReserves.process;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

//List of cities which have blood donation center
public class CitiesList {
	private ArrayList<String> names;
	private ArrayList<Integer> id;
	private static final Logger logger = LogManager.getLogger(CitiesList.class);
	
	//Get cities table from db
	public CitiesList (DataSource datasource) {
		
		//Prepare query to db to get cities table
		Connection connect = null;
		Statement stmt = null;
		ResultSet rs = null;
		String query = "SELECT * FROM `cities` ORDER BY `city_name` ASC";
		
		names = new ArrayList<String>();
		id = new ArrayList<Integer>();
		
		try {
			connect=datasource.getConnection();
			stmt=connect.createStatement();
			rs=stmt.executeQuery(query);
			
			//Add id and name to corresponding list
			while (rs.next()) {
				id.add(rs.getInt(1));
				names.add(rs.getString(2));
			}
			connect.close();
		} catch (SQLException e) {
			logger.error("Error at retrieving cities list from DB : " +e.getMessage());
		}
	}

	public ArrayList<String> getNames() {
		return names;
	}

	public ArrayList<Integer> getId() {
		return id;
	}
	
	public String getNames(int number) {
		return this.names.get(number);
	}
	
	public int getId(int number) {
		return this.id.get(number);
		
	}
}
