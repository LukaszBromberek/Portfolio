package accessor.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

//Spring error controller for Accessor project
@Controller
public class ErrorController {
	private static final Logger logger = LogManager.getLogger(ErrorController.class);
	
    @RequestMapping(value = "errors", method = RequestMethod.GET)
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

    	//Prepare message for error page
    	 ModelAndView errorPage = new ModelAndView("error");
         String errorMsg = "";
         int httpErrorCode = getErrorCode(httpRequest);
         switch (httpErrorCode) {
             case 400: {
                 errorMsg = "400 Nieprawidłowe żądanie";
                 break;
             }
             case 401: {
                 errorMsg = "401 Nieautoryzowany dostęp";
                 break;
             }
             case 404: {
                 errorMsg = "404 Nie odnaleziono strony";
                 break;
             }
             case 500: {
                 errorMsg = "500 Wewnętrzny błąd serwera";
                 break;
             }
             default : {
            	 errorMsg = "Inny rodzaj błędu";
            	 break;
             }
         }
         errorPage.addObject("errorMsg", errorMsg);
         
         logger.error(errorMsg);
         
         return errorPage;
     }
     
    //Get error code form HTML request
     private int getErrorCode(HttpServletRequest httpRequest) {
         return (Integer) httpRequest
           .getAttribute("javax.servlet.error.status_code");
     }
}
