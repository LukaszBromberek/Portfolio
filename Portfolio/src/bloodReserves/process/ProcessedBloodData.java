package bloodReserves.process;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import bloodReserves.process.TableRow;

//Data container for processed blood levels data
public class ProcessedBloodData {
	private GregorianCalendar actualizationDate;
	private ArrayList<String> cityNames;
	private ArrayList<TableRow> valuesRows;
	private CitiesList cityList;

	
	public GregorianCalendar getActualizationDate() {
		return actualizationDate;
	}
	public void setActualizationDate(GregorianCalendar actualizationDate) {
		this.actualizationDate = actualizationDate;
	}
	public ArrayList<String> getCityNames() {
		return cityNames;
	}
	public void setCityNames(ArrayList<String> cityNames) {
		this.cityNames = cityNames;
	}
	public ArrayList<TableRow> getValuesRows() {
		return valuesRows;
	}
	public void setValuesRows(ArrayList<TableRow> valuesRows) {
		this.valuesRows = valuesRows;
	}
	public CitiesList getCityList() {
		return cityList;
	}
	public void setCityList(CitiesList cityList) {
		this.cityList = cityList;
	}
	
}
