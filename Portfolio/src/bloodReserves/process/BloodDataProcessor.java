package bloodReserves.process;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

//Class for processing HTML code from krew.info/zapasy to usable date
public class BloodDataProcessor {
	String originalHtml;
	String reducedHtml;
	boolean isHtmlReduced;
	private static final Logger logger = LogManager.getLogger(BloodDataProcessor.class);

	public BloodDataProcessor(String originalHtml) {
		this.originalHtml = originalHtml;
		this.isHtmlReduced = false;
	}
	
	//Get date of actualization blood levels on website
	public GregorianCalendar getDate() {
		
		try {
			//Temporary variable used to simplify formulas
			int startIndex = originalHtml.indexOf("Aktualizacja stanu");
			
			// Cuts fragment with day/month/year data, than separates it using .
			// tempStrings operation fixes problem with days/month one-digit number
			String tempString = originalHtml.substring(startIndex+20, startIndex + 30);
			tempString = tempString.substring(0,tempString.lastIndexOf(".")+5);
			logger.debug("Date in HTML " + tempString);
			String[] date = tempString.split("\\.");
			
			
			startIndex = originalHtml.indexOf("godz.");
			tempString = originalHtml.substring(startIndex+5, startIndex + 12);
			tempString = tempString.substring(0,tempString.indexOf("<"));
			String[] time = tempString.split(":");
			
			int year = Integer.valueOf(date[2]);
			int month = Integer.valueOf(date[1])-1;// Bugfix - january is 0, december is 11 - needs to be corrected before sending to db
			int day = Integer.valueOf(date[0]);
			int hour = Integer.valueOf(time[0]);
			//Substring to make sure, that there no issue when hour is less than 10;
			int minute = Integer.valueOf(time[1]);

			return new GregorianCalendar(year,month,day,hour,minute);
		} catch (Exception e) {
			logger.error("Error at getDate : " + e.getMessage());
		} 
		return null;
	}
	
	//Reduce html to only useful data
	public void extractTable () {
		try {
			int beginIndex = originalHtml.indexOf("<table>");
			int endIndex = originalHtml.indexOf("</table>");
			this.isHtmlReduced = true;
			this.reducedHtml = originalHtml.substring(beginIndex, endIndex);
		} catch (Exception e) {
			logger.error("Erorr at extractTable : " + e.getMessage());
		} 
	}
	
	public String getOriginalHtml() {
		return originalHtml;
	}

	public String getReducedHtml() {
		return reducedHtml;
	}

	public boolean isHtmlReduced() {
		return isHtmlReduced;
	}

	
	//Extract Names of all cities from reducedHtml
	public ArrayList<String> extractCityName(){
		try {
			ArrayList<String> citiesNames = new ArrayList<String>();
			
			if(!isHtmlReduced()) {
				this.extractTable();
			}
			
			//Extract header from table
			String extractor = reducedHtml.substring(reducedHtml.indexOf("<thead>"),reducedHtml.indexOf("</thead>"));
			
			//Remove first <span> tag with "Grupa krwi"
			extractor = extractor.substring(extractor.indexOf("</span>")+7);
			
			//This loop extracts cities, until there's none
			while(true) {
				int beginIndex = extractor.indexOf("<span>")+6;
				int endIndex = extractor.indexOf("</span>");
				
				//End condition for loop
				if(endIndex==-1) { break; }
				
				//Add city name to ArrayList
				citiesNames.add(extractor.substring(beginIndex,endIndex));
				//Remove added city name
				extractor=extractor.substring(endIndex+7);
			}
			Locale polish = new Locale("pl","PL");
			Collator polishCollator = Collator.getInstance(polish);
			Collections.sort( citiesNames, polishCollator);
			
			return citiesNames;
		} catch (Exception e) {
			logger.error("Error at extractCityName : " + e.getMessage());
		}
		return null;
	}
	
	//Extract actual blood levels data from reducedHTML to rows with blood type name
	public  ArrayList<TableRow> extractBloodLevels(){
		try {
			if(!isHtmlReduced()) {
				this.extractTable();
			} 
			ArrayList<TableRow> bloodLevels = new ArrayList<TableRow>();
			 
			//Extract from table rows with data
			String extractor = reducedHtml.substring(reducedHtml.indexOf("<tbody>"), reducedHtml.indexOf("</tbody>"));
			
			//This loop extracts rows with data, until there's none
			while(true) {
				int rowEnd = extractor.indexOf("</tr>");
				
				//End condition for loop
				if(rowEnd==-1) { break; }
				
				String row = extractor.substring(extractor.indexOf("<tr>")+4,extractor.indexOf("</tr>"));
				
				bloodLevels.add(new TableRow(row));
				
				//Cut extracted data from extractor
				extractor=extractor.substring(rowEnd+5);
			}		
			return bloodLevels;
		} catch (Exception e) {
			logger.error("Error at extractBloodLevels : " + e.getMessage());
		}
		return null;
	}
}
