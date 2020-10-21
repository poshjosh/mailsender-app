package com.looseboxes.mailsender.app.config;

import com.looseboxes.mailsender.google.GmailConfigImpl;
import com.looseboxes.fileclient.FileHandler;
import com.looseboxes.gmailapi.DataStoreProvider;
import com.looseboxes.gmailapi.GmailConfig;
import com.looseboxes.fileclient.FileHandlerFactory;
import com.looseboxes.mailsender.google.GmailDataStoreFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

/**
 * @author hp
 */
public class MailSenderConfigurationBase {
    
    @Bean public DataStoreProvider dataStoreProvider(
            @Qualifier(FileHandlerFactory.BASE) FileHandler fileHandler, GmailConfig gmailConfig) {
        return () -> new GmailDataStoreFactory(fileHandler, gmailConfig.getTokensDirectory().toFile());
    }
    
    @Bean public GmailConfig gmailConfig(
            @Qualifier(FileHandlerFactory.BASE) FileHandler fileHandler, MailSenderProperties props) {
        return new GmailConfigImpl(fileHandler, props.getGmail());
    }
}
