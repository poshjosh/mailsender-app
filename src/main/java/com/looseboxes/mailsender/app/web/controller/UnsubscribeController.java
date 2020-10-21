package com.looseboxes.mailsender.app.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;
import com.looseboxes.mailsender.app.web.service.UnsubscribeService;
import com.looseboxes.mailsender.app.web.ModelAttributes;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hp
 */
@Controller
public class UnsubscribeController {
    
    @Autowired private UnsubscribeService unsubscribeService;

    @RequestMapping(UnsubscribeService.ENDPOINT) 
    public String unsubscribe(
            ModelMap model,
            @RequestParam(name = "email", required = true) String emailBase64Encoded,
            @RequestParam(name = "key", required = true) String key) {
        
        final String message = unsubscribeService.unsubscribe(emailBase64Encoded, key);
        
        model.addAttribute(ModelAttributes.MESSAGES, message);
        
        return "home";
    }
}
