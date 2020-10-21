package com.looseboxes.mailsender.app.config;

import com.looseboxes.gmailapi.DataStoreProvider;
import com.looseboxes.gmailapi.GmailConfig;
import com.looseboxes.gmailapi.GmailFactory;
import com.looseboxes.gmailapi.GmailFactoryImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author hp
 */
@Profile("prod")
@Configuration
public class MailSenderConfigurationProd extends MailSenderConfigurationBase{
    
    @Value("${spring.application.name}")
    private String applicationName;
    
    @Bean public GmailFactory gmailFactory(GmailConfig gmailConfig, DataStoreProvider dataStoreProvider) {
        return new GmailFactoryImpl(applicationName, gmailConfig, dataStoreProvider);
    }
}
