package context;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.PropertyConfigurator;
 
//Context listener which runs log4j configuration
@WebListener("application context listener")
public class ContextListener implements ServletContextListener {

		@Override
		public void contextDestroyed(ServletContextEvent sce) {
		/*
		 * AnnotationConfigApplicationContext appContext = new
		 * AnnotationConfigApplicationContext(AccessorConfig.class); AccessorDAO DAO =
		 * appContext.getBean("DAOBean", AccessorDAOImpl.class); DAO.closeFactory();
		 * appContext.close();
		 */
		}
	
	    /**
	     * Initialize log4j when the application is being started
	     */
		@Override
		public void contextInitialized(ServletContextEvent event) {
	        // initialize log4j here
	        ServletContext context = event.getServletContext();
	        String log4jConfigFile = context.getInitParameter("log4j-config-location");
	        String fullPath = context.getRealPath("/Portfolio/resources/log4j.properties") + File.separator + log4jConfigFile;
	        
	        PropertyConfigurator.configure(fullPath);
		}
		

		
}
