package accessor.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import accessor.DAO.AccessorDAOImpl;
import accessor.model.departments.Department;
import accessor.model.users.User;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Configuration
@EnableScheduling
public class AccessorConfig implements WebMvcConfigurer,SchedulingConfigurer   {
	
	private static final Logger logger = LogManager.getLogger(AccessorConfig.class);
	
	//set DB connection parameters for Accessor project
	@Bean
	@Singleton
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		try {
			dataSource.setUrl("https://localhost:3306/accessor/");
			dataSource.setUsername("root");
			dataSource.setPassword("root");
			dataSource.setDriverClassName("com.mysql.jdbc.Driver");
			
			logger.info("Accessor data source set to : " + dataSource.getUrl());
			
			return dataSource;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error "+ e.getMessage() +" at AccessorConfig.getDataSource. URL : " + dataSource.getUrl() + " USERNAME : " +dataSource.getUsername() + " PASSWORD : " + dataSource.getPassword());
			return null;
		}
	}
	
	//Create bean of DAOImpl
	@Bean (name="DAOBean")
	@Singleton
	public AccessorDAOImpl AccessorDAO() {

		return new AccessorDAOImpl(getDataSource());
	}
	
	//Create bean for user login attempt
	@Bean (name="loginAttempt")
	public User User() {
		User user = new User();
		user.setLogin("");
		user.setPassword("");
		return user;
	}
	
	
	//Create beans for predefined departments
	@Bean (name="finDep")
	public Department finDep() {
		return new Department("FIN", "Financial");
	}
	
	@Bean (name="pDep")
	public Department pDep() {
		return new Department("P", "Personal");
	}
	
	@Bean (name="proDep")
	public Department proDep() {
		return new Department("PRO", "Production");
	}
	
	@Bean (name="itDep")
	public Department itDep() {
		return new Department("IT", "Information Technology");
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(taskExecutor());
	}
	
    @Bean(destroyMethod="shutdown")
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(0);
    }
}
