package com.looseboxes.mailsender.app.config;

import com.looseboxes.fileclient.config.aws.FileHandlerConfigurationSourceAws;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author hp
 */
@Profile("prod")
@Configuration
public class FileHandlerConfigurationProd extends FileHandlerConfigurationSourceAws{

    public FileHandlerConfigurationProd(MailSenderProperties appProperties) {
        super(appProperties.getAws());
    }
}
