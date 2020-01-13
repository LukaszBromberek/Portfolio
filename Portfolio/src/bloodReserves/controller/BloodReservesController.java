package bloodReserves.controller;

import java.lang.StringBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import bloodReserves.model.BloodReservesModel;

/**
 * Controller of BloodReserves Project
 * Gets HTML from krew.info/zapasy , sends to model to update DB
 * Prepares view page
 */
@WebServlet("/BloodReservesController")
public class BloodReservesController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int ACTUALISATION_INTERVAL = 12;
	private static final TimeUnit ACTUALIZATION_TIME_UNIT = TimeUnit.HOURS;
	private static final Logger logger = LogManager.getLogger(BloodReservesController.class);
	
	ScheduledExecutorService actualizationChecker;
	
	@Resource(name = "jdbc/project") DataSource dataSource;			//Server on mamp localhost
//	@Resource(name = "jdbc/serwer1910176db") DataSource dataSource;	//Web test server
	
    public BloodReservesController() {
    }

    //Runs actualization process
	public void init(ServletConfig config) throws ServletException {
		// Set timer for checking if new blood level is available
		actualizationChecker = Executors.newScheduledThreadPool(1);		
		logger.info("Initialize actualization checker for BloodReserves. ACTUALISATION_INTERVAL="+ACTUALISATION_INTERVAL+" ACTUALIZATION_TIME_UNIT:"+ACTUALIZATION_TIME_UNIT.toString() );
		final Runnable actualizeBloodReserves = new Runnable() {
		
			@Override
			public void run() {
				logger.info("Blood leveles actualization - scheduled task");
				
				BloodReservesModel bloodModel = new BloodReservesModel(dataSource);
				String html = getHtml();
				
				//Will insert data to database, if data is new
				bloodModel.tryToActualize(html);
			}
		};
		
		actualizationChecker.scheduleAtFixedRate(actualizeBloodReserves, 0, ACTUALISATION_INTERVAL, ACTUALIZATION_TIME_UNIT);	
	}

	//Terminate scheduled task
	@Override
	public void destroy() {
		actualizationChecker.shutdownNow();
		Executors.newScheduledThreadPool(0);
		super.destroy();
		logger.info("Terminate actualizationChecker");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String page=request.getParameter("page");
		if(page!=null) {page.toLowerCase();}
		else{page="";}
		
		BloodReservesModel bloodModel = new BloodReservesModel(dataSource);
		List<String> types;
		List<String> escapedTypes;
		
		try {
			switch(page) {
			//Try to get actualization from krew.info/zapasy
			case "gethtml":
				logger.info("Blood leveles actualization - manual task");
				String html = getHtml();
				bloodModel.tryToActualize(html);
				
				request.setAttribute("html", html);
				
				request.getRequestDispatcher("view/manualCall.jsp").forward(request, response);
				break;
			case "select":
				//Get data from form
				String date=request.getParameter("date");
				String city=request.getParameter("city");
				String bloodType=request.getParameter("bloodType").replaceAll("x", " ").replaceAll("p", "+").replaceAll("n", "-");

				
				//Get results to show in view
				request.setAttribute("results", bloodModel.getResultsFromDB(date, city, bloodType));
				
				//Get data to select options
				request.setAttribute("dates", bloodModel.getDatesFromDB());
				request.setAttribute("cities", bloodModel.getCitiesFromDB());
				
				types = bloodModel.getTypesFromDB();
				request.setAttribute("types", types);
				
				//Escape forbidden characters in URL code
				escapedTypes = new ArrayList<String>();
				for (String string : types) {
					escapedTypes.add(string.replaceAll("\\s","x").replaceAll("\\+", "p").replaceAll("\\-", "n"));
				}
				request.setAttribute("escapedTypes", escapedTypes);
				
				request.getRequestDispatcher("view/bloodReserves.jsp").forward(request, response);
				break;
				
			default: 
				//Get results to show in view
				request.setAttribute("results", bloodModel.getResultsFromDB("Wszystkie", "Wszystkie", "Wszystkie"));

				//Get data to select options
				request.setAttribute("dates", bloodModel.getDatesFromDB());
				request.setAttribute("cities", bloodModel.getCitiesFromDB());

				types = bloodModel.getTypesFromDB();				
				request.setAttribute("types", types);
				
				//Escape forbidden characters in URL code
				escapedTypes = new ArrayList<String>();
				for (String string : types) {
					escapedTypes.add(string.replaceAll("\\s","s").replaceAll("\\+", "p").replaceAll("\\-", "n"));
				}
				request.setAttribute("escapedTypes", escapedTypes);

				request.getRequestDispatcher("view/bloodReserves.jsp").forward(request, response);
			}
		} catch (Exception e) {
			logger.error("Error at GET main switch : " + e.getMessage());
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	//Get HTML Code from krew.info/zapasy
	public String getHtml() {
		String sURL = "https://krew.info/zapasy/";
		logger.info("Trying to get URL : " + sURL);
		
		
		URL url=null;
		HttpsURLConnection httpCon;		
		Proxy proxy=null;
		
		//Parse URL to URL object
		try {
			
			// @Kaczka - ta linia kodu ulegnie zmianie przy przeniesieniu na właściwy serwer.
			proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.252.76.110",8080 ));
			// ---------------------------------------------------------------------------------
			
			url = new URL(sURL);

		} catch (MalformedURLException e) {
			logger.error("Error : Getting URL unsuccessful! : " + e.getMessage());
		}
		
		// Try to connect with website and parse HTML code to String
		try {
			
			httpCon = (HttpsURLConnection) url.openConnection(proxy);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
			String inputLine;
			StringBuilder builder = new StringBuilder();
			
			while((inputLine = in.readLine())!= null) {
				
				builder.append(inputLine);
			}
			in.close();
			
			httpCon.disconnect();
			
			return builder.toString();
		} catch (IOException e) {
			logger.error("Error : Connecting with webpage unsuccessful! : " +e.getMessage());
		}
		
		return null;
	}

}
