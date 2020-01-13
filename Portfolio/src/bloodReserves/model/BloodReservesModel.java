package bloodReserves.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import bloodReserves.enums.BloodType;
import bloodReserves.process.BloodDataProcessor;
import bloodReserves.process.CitiesList;
import bloodReserves.process.ProcessedBloodData;
import bloodReserves.process.TableRow;

//Model merged with DAO - process HTML from Controller, connects with DB
public class BloodReservesModel {
	private ProcessedBloodData data;
	private DataSource dataSource;
	private BloodDataProcessor processor;
	public Tester tester;
	
	private static final Logger logger = LogManager.getLogger(BloodReservesModel.class);
	
	//subclass for JUnit Tests
	public class Tester{
		public boolean testIsDateInBase(GregorianCalendar date) {
			boolean result = isDateInBase(date);
			logger.debug("IsDateInBase result : " + result);
			return result;
		}
	}
	
	//Constructor
	public BloodReservesModel(DataSource dataSource) {		
		this.dataSource = dataSource;
		this.tester=new Tester();
		
	}

//Main function called by servlet timer
	public void tryToActualize(String html) {
		logger.debug("inside tryToActualize");
		logger.debug("init BloodDataProcessor");
		
		this.processor = new BloodDataProcessor (html);
		this.data = new ProcessedBloodData();
		
		logger.debug("getActualizationDate called");
		this.data.setActualizationDate(processor.getDate());
		
		//Connect with DB
		if(this.isDateInBase(data.getActualizationDate())==false)
		{			
			
			  try {
				logger.info("Date not found in DB, actualizing blood levels");
				  this.processor.extractTable();
				  
				  logger.debug("calling insertDate"); //Send date to DB
				  this.insertDate();
				  
				  logger.debug("calling data.setCityNames"); //Extract cities
				  this.data.setCityNames(processor.extractCityName());
				  
				  logger.debug("Debug: calling data.setValueRows"); //Extract bloodLevels
				  this.data.setValuesRows(processor.extractBloodLevels());
				  
				  logger.debug("Debug: calling areAllCitiesInDBAndAddIfNotThenAdd");
				  //Check cities list, add some to DB if that's needed
				  this.areAllCitiesInDBAndAddIfNotThenAdd();
				  
				  logger.debug("calling insertBloodLevels"); //Add blood levels
				  this.insertBloodLevels();
				  logger.info("Blood levels successfuly actualized");
			} catch (Exception e) {
				logger.error("Error while actualizng blood levels :" + e.getMessage());
			}
		}
		else {
			logger.info("Date already in DB, blood levels are actual");
		}


		
		
		
	}
	
	
	//Insert date to DB
	private void insertDate() {
		GregorianCalendar date = this.data.getActualizationDate();
		
		//Parse date to string
		String dateString = date.get(Calendar.YEAR)+"-"
							+(date.get(Calendar.MONTH)+1)+"-"  //Bugfix: set january as 1, december as 12
							+date.get(Calendar.DAY_OF_MONTH)+" " 
							+date.get(Calendar.HOUR_OF_DAY)+":"
							+date.get(Calendar.MINUTE)+":00";	
		try {
			//Prepare query with date of actualization, then send it to DB
			String query = "INSERT INTO `actualization_date`(`date`) VALUES (?)";
			Connection connection = this.dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, dateString);
			statement.execute();
			connection.close();
		} catch (SQLException e) {
			logger.error("Error at sending date to DB: " + e.getMessage());
		}
		logger.debug("Date successfully added to DB");
		
	}
	
	//This function connects with database and check if date from HTML is already in base
	private boolean isDateInBase(GregorianCalendar date) {
		Connection connect=null;
		Statement stmt = null;
		ResultSet rs = null;
		
		//Prepare query with date parsed to String
		//SELECT * FROM `test1` WHERE test_date = '2018-4-3 2:14:00' 
		String query="Select * from actualization_date where date = '" + 
				date.get(Calendar.YEAR) + "-" + 
				(date.get(Calendar.MONTH)+1) + "-" + //Bugfix: set january as 1, december as 12
				date.get(Calendar.DAY_OF_MONTH) + " " +
				date.get(Calendar.HOUR_OF_DAY) + ":" +
				date.get(Calendar.MINUTE) + ":" +
				date.get(Calendar.SECOND) + "'";
		
		//Connect with db
		try {
			connect = dataSource.getConnection();
			stmt = connect.createStatement();
			rs = stmt.executeQuery(query);
			
			//Check if query result is empty
			if (rs.next()==false) {
				logger.debug("Date not found in DB");
				return false;
			}			
			
			connect.close();
		} catch (SQLException e) {
			logger.error("Error while calling isDateInBase : "+e.getMessage());
		}	
		logger.debug("Date found in DB");
		return true;
	}

	//Check cities list, add some to DB if that's needed
	private void areAllCitiesInDBAndAddIfNotThenAdd()
	{
		this.data.setCityList(new CitiesList(this.dataSource));
		ArrayList<String> citiesNames = this.data.getCityList().getNames();	
		for (String city : this.data.getCityNames()) {
			if(!citiesNames.contains(city)) {
				try {
					String query = "INSERT INTO `cities`(`city_name`) VALUES (?)";
					Connection connect = this.dataSource.getConnection();
					PreparedStatement statement = connect.prepareStatement(query);
					statement.setString(1, city);
					statement.execute();
					connect.close();
					logger.debug("City : " + city +" added to DB");
				} catch (SQLException e) {
					logger.error("Error at sending cities to DB : " + e.getMessage());
				}
				
			}
		}
	}

	//Transfers blood.... levels :) Add records to DB
	private void insertBloodLevels() {
		
		Connection connect=null;
		Statement stmt = null;
		ResultSet rs = null;
		
		String query = null;
	
		//Prepare table with bloodTypes
		ArrayList<BloodType> types = new ArrayList<BloodType>();
		types.add(BloodType.ZERO_RH_N);
		types.add(BloodType.ZERO_RH_P);
		types.add(BloodType.A_RH_N);
		types.add(BloodType.A_RH_P);
		types.add(BloodType.B_RH_N);
		types.add(BloodType.B_RH_P);
		types.add(BloodType.AB_RH_N);
		types.add(BloodType.AB_RH_P);
		
		try {
			connect=dataSource.getConnection();
			
			//Get actualisation ID
			query = "SELECT * FROM `actualization_date` ORDER BY `actualization_id` DESC LIMIT 1";
			stmt = connect.createStatement();
			rs=stmt.executeQuery(query);
			rs.next();
			int actualization_id = rs.getInt(1);
			
			//Iterate over table with data rows
			for(TableRow row : this.data.getValuesRows()) {
				
				//Get type_id from blood_type table
				String bloodTyp = row.getBloodType().getBloodTypeName();
				query="SELECT * FROM blood_type WHERE type_name = '"+bloodTyp+"' LIMIT 1";
				stmt=connect.createStatement();
				rs=stmt.executeQuery(query);
				rs.next();
				int bloodTypId = rs.getInt(1);
				
				//Check if number of cities equals number of blood levels
				if(this.data.getCityNames().size()!=row.values.size())
				{
					throw new Exception ("Number of cities don't match number of blood levels");	
				}
				
				//Insert blood levels into db
				for (int i=1;i<=row.values.size();i++) {
					query = "INSERT INTO `blood_levels`(`city_id`, `type_id`, `actualization_id`, `blood_level`) VALUES (?,?,?,?)";
					PreparedStatement statement = connect.prepareStatement(query);
					
					//GetCityNumber
					int cityId = this.data.getCityList().getId(i-1);
					
					statement.setInt(1, cityId);
					statement.setInt(2, bloodTypId);
					statement.setInt(3, actualization_id);
					statement.setInt(4, row.values.get(i-1));
					
					statement.execute();
				}
				
			}
			connect.close();
		}
		catch (SQLException e) {
			logger.error("SQL Error at inserting blood levels into DB : " + e.getMessage());
		}
		catch (Exception e) {
			logger.error("Error at inserting blood levels into DB : " + e.getMessage());
		}
		
		logger.debug("Blood levels actualized");
	}
	
	//Send query to DB to retrieve stored data, to show in view
	public List<BloodReservesResult>  getResultsFromDB (String date, String city, String bloodType) {
		List<BloodReservesResult> list = new ArrayList<BloodReservesResult>();
		
		ResultSet rs = null;
		PreparedStatement statement=null;
		
		try {
			//Prepare query with date of actualization, then send it to DB
			String query = "SELECT `date`,`city_name`,`type_name`,`blood_level` FROM blood_levels " + 
							"RIGHT JOIN blood_type ON blood_levels.type_id = blood_type.type_id " + 
							"RIGHT JOIN cities ON blood_levels.city_id = cities.city_id " + 
							"RIGHT JOIN actualization_date ON blood_levels.actualization_id=actualization_date.actualization_id ";
//							"WHERE date = ? AND city_name = ? AND type_name = ? ORDER BY date DESC LIMIT 50";
			Connection connection = this.dataSource.getConnection();

			//Prepare last line of query 
			boolean andNeeded = false;
			boolean allDates = date.equals("Wszystkie");
			boolean allCities = city.equals("Wszystkie");
			boolean allTypes = bloodType.equals("Wszystkie");
			
			//Where is needed only when any of query part must be precised
			if(!allDates | !allCities | !allTypes) {
				query = query + "WHERE ";
			}
			int iter=1;

			//Add WHERE column_name = and AND if needed
			if(allDates==false) {
				query = query + "date = ? ";
				andNeeded=true;
			}
			if(allCities==false) {
				if (andNeeded) {
					query = query + "AND ";
				}
				query = query + "city_name = ? ";
				andNeeded=true;
			}
			if(allTypes==false) {
				if (andNeeded) {
					query = query + "AND ";
				}
				query = query + "type_name = ? ";
			}		
			query = query + "ORDER BY date DESC LIMIT 50";
			statement = connection.prepareStatement(query);
			//Fill ? with values
			if(allDates==false) {
				statement.setString(iter, date);
				iter++;
			}
			if(allCities==false) {
				statement.setString(iter, city);
				iter++;
			}
			if(allTypes==false) {
				statement.setString(iter, bloodType);
			}
			rs = statement.executeQuery();
			logger.debug("Query sent to DB : " + statement.toString());
			
			//List all results
			while(rs.next()==true) {
				list.add(new BloodReservesResult(rs.getString("date"),rs.getString("city_name"), rs.getString("type_name"), rs.getString("blood_level")));
			}			
			connection.close();
			return list;
		} catch (SQLException e) {
			logger.error("Error at retrieving data from DB: " + e.getMessage());
			logger.error("Query : " + statement.toString());
			return null;
		}
	}

	//Get last up to 20 last dates from DB
	public List<String> getDatesFromDB (){
		List<String> dates = new  ArrayList<String>();
		dates.add("Wszystkie");
		
		PreparedStatement statement=null;
		
		try {
			//Prepare query with date of actualization, then send it to DB
			String query = "SELECT `date` FROM actualization_date ORDER BY date DESC LIMIT 19";
			Connection connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			logger.debug("Query sent to DB : " + statement.toString());
			
			//List all results
			while(rs.next()==true) {
				dates.add(rs.getString("date"));
			}			
			connection.close();
			return dates;
		} catch (SQLException e) {
			logger.error("Error at retrieving dates from DB: " + e.getMessage());
			logger.error("Query : " + statement.toString());
			return null;
		}
	}
	
	//Get cities list from DB
	public List<String> getCitiesFromDB (){
		List<String> cities = new  ArrayList<String>();
		cities.add("Wszystkie");
		PreparedStatement statement = null;
		
		try {
			//Prepare query with date of actualization, then send it to DB
			String query = "SELECT `city_name` FROM cities";
			Connection connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			logger.debug("Query sent to DB : " + statement.toString());

			//List all results
			while(rs.next()==true) {
				cities.add(rs.getString("city_name"));
			}			
			connection.close();
			return cities;
		} catch (SQLException e) {
			logger.error("Error at retrieving cities from DB: " + e.getMessage());
			logger.error("Query : " + statement.toString());
			return null;
		}
	}
		
	//Get blood types list from DB
	public List<String> getTypesFromDB (){
		List<String> types = new  ArrayList<String>();
		types.add("Wszystkie");
		PreparedStatement statement =null;
		
		try {
			//Prepare query with date of actualization, then send it to DB
			String query = "SELECT `type_name` FROM blood_type";
			Connection connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			logger.debug("Query sent to DB : " + statement.toString());

			//List all results
			while(rs.next()==true) {
				types.add(rs.getString("type_name"));
			}			
			connection.close();
			return types;
		} catch (SQLException e) {
			logger.error("Error at retrieving blood types from DB: " + e.getMessage());
			logger.error("Query : " + statement.toString());
			return null;
		}
	}
}
