package com.secondW4ve.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.mail.Message;
import java.util.Date;

public class EmailMessage {

    private SimpleStringProperty subject;
    private SimpleStringProperty sender;
    private SimpleStringProperty recipient;
    private SimpleObjectProperty<SizeInteger> size;
    private SimpleObjectProperty<Date> date;
    private boolean isRead;
    private Message message;

    public EmailMessage(String subject, String sender, String recipient, SizeInteger size, Date date,
                        boolean isRead, Message message){
        this.subject = new SimpleStringProperty(subject);
        this.sender = new SimpleStringProperty(sender);
        this.recipient = new SimpleStringProperty(recipient);
        this.size = new SimpleObjectProperty<SizeInteger>(size);
        this.date = new SimpleObjectProperty<Date>(date);
        this.isRead = isRead;
        this.message = message;
    }

    public String getSubject(){
        return this.subject.get();
    }

    public String getSender(){
        return this.sender.get();
    }

    public String getRecipient(){
        return this.recipient.get();
    }

    public SizeInteger getSize(){
        return this.size.get();
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read){
        this.isRead = read;
    }

    public Date getDate(){
        return this.date.get();
    }

    public Message getMessage(){
        return this.message;
    }
}
