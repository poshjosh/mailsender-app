package com.looseboxes.mailsender.app.web.controller;

import com.looseboxes.mailsender.app.web.Message;
import com.looseboxes.mailsender.app.web.ModelAttributes;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author hp
 */
@Controller
public class AppController implements ErrorController{
    
    private final Logger log = LoggerFactory.getLogger(AppController.class);

    /**
     * Forwards any unmapped paths (except those containing a period) to the client {@code home.html}.
     * @return forward to client {@code home.html}.
     */
    @GetMapping(value = "/**/{path:[^\\.]*}")
    public String unmappedRequest() {
        return "home";
    }
    
    @RequestMapping("/") 
    public String home(){
        return "home";
    }

    @RequestMapping("/error") 
    public String error(){
        return "error";
    }

    /**
     * Returns the path of the error page.
     * 
     * Primarily used to know the error paths that will not need to be secured.
     * 
     * @return the error path
     */
    @Override
    public String getErrorPath() {
        return "/error";
    }
    
    //////////////////////////////////////////////////////////////////////////
    // Exception handling
    // @see https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
    //////////////////////////////////////////////////////////////////////////

    @ExceptionHandler(FileNotFoundException.class)
    public ModelAndView handleResourceNotFound(
        FileNotFoundException exception, HttpServletRequest request) {
  
        final List<String> errors = Arrays.asList(
                "The page you request was not found",
                "It may have been moved, or it is no longer available",
                "Also, check that you entered the correct address in the browser.",
                "",
                request.getRequestURI(),
                "",
                "Meanwhile, keep calm, keep browsing");
        
        return this.handleException(exception, HttpStatus.NOT_FOUND, errors);
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception exception, HttpServletRequest request) {
    
        final List<String> errors = Arrays.asList(
                "We are unable to fullfill your request at this time.",
                "",
                request.getRequestURI(),
                "",
                "Meanwhile, keep calm, keep browsing");
        
        return this.handleException(exception, HttpStatus.INTERNAL_SERVER_ERROR, errors);
    }
            
    protected ModelAndView handleException(
            Exception exception, HttpStatus status, List<String> errors) {
    
        final ModelAndView modelAndView = new ModelAndView(this.getErrorPath());
        
        modelAndView.getModelMap().addAttribute(ModelAttributes.MESSAGES, 
                errors.stream().map(Message::error).collect(Collectors.toList()));

        modelAndView.setStatus(status);
        
        log.warn(errors.toString(), exception);

        return modelAndView;
    }
}
