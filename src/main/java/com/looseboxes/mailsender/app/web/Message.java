package com.looseboxes.mailsender.app.web;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author hp
 */
public class Message implements Serializable{
    
    public static Message error(String message) {
        return new Message().type("error").text(message);
    }

    public static Message info(String message) {
        return new Message().type("info").text(message);
    }
    
    private String text;
    private String type;
    
    public Message() {}
    
    public Message text(String text) {
        this.setText(text);
        return this;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    public Message type(String type) {
        this.setType(type);
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.text);
        hash = 53 * hash + Objects.hashCode(this.type);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Message other = (Message) obj;
        if (!Objects.equals(this.text, other.text)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Message{" + "text=" + text + '}';
    }
}
