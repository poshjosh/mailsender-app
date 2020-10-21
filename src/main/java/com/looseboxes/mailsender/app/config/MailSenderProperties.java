package com.looseboxes.mailsender.app.config;

import com.looseboxes.fileclient.config.aws.AwsProperties;
import com.looseboxes.mailsender.config.GmailProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author hp
 */
@ConfigurationProperties(prefix = "mailsender", ignoreUnknownFields = false)
public class MailSenderProperties {
    
    private String username;
    
    private String password;
    
    private String baseUrl;
    
    private String clientBaseUrl;
    
    private String senderEmail;
    
    private String dirName;
    
    private String dir;
    
    private AwsProperties aws;
    
    private GmailProperties gmail;
    
    public MailSenderProperties() { }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getClientBaseUrl() {
        return clientBaseUrl;
    }

    public void setClientBaseUrl(String clientBaseUrl) {
        this.clientBaseUrl = clientBaseUrl;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public AwsProperties getAws() {
        return aws;
    }

    public void setAws(AwsProperties aws) {
        this.aws = aws;
    }

    public GmailProperties getGmail() {
        return gmail;
    }

    public void setGmail(GmailProperties gmail) {
        this.gmail = gmail;
    }

    @Override
    public String toString() {
        return "MailSenderProperties{" + "username=" + username + ", password=*******" +
                ", baseUrl=" + baseUrl + ", clientBaseUrl=" + clientBaseUrl +
                ", senderEmail=" + senderEmail +
                ", dirName=" + dirName + ", dir=" + dir + ", aws=" + aws + ", gmail=" + gmail + '}';
    }
}
