package accessor.model.users;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
	private static final Logger logger = LogManager.getLogger(UserValidator.class);
	
	@Override
	public boolean supports(Class<?> arg0) {
		return User.class.equals(arg0);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.userImpl.firstName","Imię jest wymagane");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty.userImpl.lastName","Nazwisko jest wymagane");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.userImpl.password","Hasło jest wymagane");

		if(user.getFirstName().length()<3) {
			errors.rejectValue("firstName", "short.userImpl.firstName","Imię jest za krótkie");
			logger.debug("Validator: First name to short");
		}
		
		if(user.getLastName().length()<3) {
			errors.rejectValue("lastName", "short.userImpl.lastName","Nazwisko jest za krótkie");
			logger.debug("Validator: Last name to short");
		}
		
		 
		if(!user.getFirstName().matches("\\p{IsLatin}+")) {
			errors.rejectValue("firstName", "signs.userImpl.firstName", "Imię musi składać się wyłącznie z liter");
			logger.debug("Validator: First name should consist only from letters");
		}
		
		if(!user.getLastName().matches("\\p{IsLatin}+")) {
			errors.rejectValue("lastName", "signs.userImpl.lastName", "Nazwisko musi składać się wyłącznie z liter");
			logger.debug("Validator: Last name should consist only from letters");
		}
		
		if(!user.getPassword().matches(".*[^a-z0-9]+.*")) {
			errors.rejectValue("password", "containsSpecialCharacters.userImpl.password", "Hasło musi zawierać co najmniej jeden znak specjalny");
			logger.debug("Validator: Password should contain special character");
		}
		
		if(!user.getPassword().matches(".*[0-9]+.*")) {
			errors.rejectValue("password", "containsNumber.userImpl.password", "Hasło musi zawierać co najmniej jedną cyfrę");
			logger.debug("Validator: Password should contain a number");
		}
	}
	
	

}
