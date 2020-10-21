package com.looseboxes.mailsender.app.web.controller;

import com.looseboxes.mailsender.app.config.MailSenderProperties;
import com.looseboxes.mailsender.app.web.service.MailService;
import com.looseboxes.mailsender.app.task.EmailSendingState;
import com.looseboxes.mailsender.app.task.EmailSendingConfig;
import com.looseboxes.mailsender.app.task.Task;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hp
 */
@RestController
public class MailSenderController {
    
    private final Logger log = LoggerFactory.getLogger(MailSenderController.class);
    
    @Autowired private MailSenderProperties appProperties;
    @Autowired private Task<EmailSendingConfig, EmailSendingState> task;
    @Autowired private MailService mailService;
    
    @RequestMapping("/send")
    public ResponseEntity<Map<String, String>> send(
            @RequestParam(name = "template", required = false, defaultValue = "mail/intro") String template,
            @RequestParam(name = "subject", required = true) String subject,
            @RequestParam(name = "to", required = true) List<String> to,
            @RequestParam(name = "username", required = true) String username,
            @RequestParam(name = "password", required = true) String password) {
    
        Map<String, String> result = new HashMap<>(to.size(), 1.0f);
        
        for(String recipient : to) {
            final boolean success = mailService.sendEmail(recipient, subject, template);
            result.put(recipient, success ? "Success" : "Failure");
        }
        
        return ResponseEntity.ok(result);
    }

    @RequestMapping("/start")
    public ResponseEntity<Map<String, String>> start(@Valid EmailSendingConfig config, 
            @RequestParam(name = "username", required = true) String username,
            @RequestParam(name = "password", required = true) String password) {
        
        log.debug("REST request to start a new task if none, username: {}, config: {}", username, config);
        
        ResponseEntity<Map<String, String>> response = this.check(username, password);
        
        if(response.getStatusCode().equals(HttpStatus.OK)) {
    
            final HttpStatus status;
            final String message;
            if(task.isRunning()) {
                status = HttpStatus.CONFLICT;
                message = "Existing mail sending task is still running.";
            }else{
                task.start(config);
                status = HttpStatus.OK;
                message = "Started mail sending task.";
            }
            
            response = ResponseEntity
                    .status(status)
                    .body(Collections.singletonMap("message", message + " Use the /status endpoint to check status."));
        }
        
        return response;
    }
    
    @RequestMapping("/stop")
    public ResponseEntity<Map<String, String>> stop(
            @RequestParam(name = "username", required = true) String username,
            @RequestParam(name = "password", required = true) String password) {

        log.debug("REST request to stop current task if existing, username: {}", username);
        
        ResponseEntity<Map<String, String>> response = this.check(username, password);
        
        if(response.getStatusCode().equals(HttpStatus.OK)) {
            
            final HttpStatus status;
            final String message;
            if(task.isRunning()) {
                task.stop();
                status = HttpStatus.OK;
                message = "Stopping mail sending task.";
            }else{
                status = HttpStatus.CONFLICT;
                message = "There is no running mail sending task to stop.";
            }

            response = ResponseEntity
                    .status(status)
                    .body(Collections.singletonMap("message", message + " Use the /status endpoint to check status."));
        }
        
        return response;
    }
    
    @RequestMapping("/status")
    public ResponseEntity<EmailSendingState> status() {
        return ResponseEntity.ok(task.getSnapshot());
    }

    private ResponseEntity<Map<String, String>> check(String username, String password) {
        final HttpStatus status;
        final String message;
        if( ! appProperties.getUsername().equals(username)) {
            status = HttpStatus.UNAUTHORIZED;
            message = "Incorrect username";
        }else if( ! appProperties.getPassword().equals(password)) {
            status = HttpStatus.UNAUTHORIZED;
            message = "Incorrect password";
        }else{
            status = HttpStatus.OK;
            message = "OK";
        }
        return ResponseEntity
                .status(status)
                .body(Collections.singletonMap("message", message + " Use the /status endpoint to check status."));
    }
}
