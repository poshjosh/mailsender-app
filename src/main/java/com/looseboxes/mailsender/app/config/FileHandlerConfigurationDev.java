package com.looseboxes.mailsender.app.config;

import com.looseboxes.fileclient.config.FileHandlerConfigurationSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author hp
 */
@Profile("!prod")
@Configuration
public class FileHandlerConfigurationDev extends FileHandlerConfigurationSource{
    
}
