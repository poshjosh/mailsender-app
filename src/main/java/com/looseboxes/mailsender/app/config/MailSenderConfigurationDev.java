package com.looseboxes.mailsender.app.config;

import com.looseboxes.gmailapi.DataStoreProvider;
import com.looseboxes.gmailapi.GmailConfig;
import com.looseboxes.gmailapi.GmailFactory;
import com.looseboxes.mailsender.google.GmailSenderNoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author hp
 */
@Profile("!prod")
@Configuration
public class MailSenderConfigurationDev extends MailSenderConfigurationBase{
    
    @Bean public GmailFactory gmailFactory(GmailConfig gmailConfig, DataStoreProvider dataStoreProvider) {
        return () -> new GmailSenderNoOp();
    }
}
