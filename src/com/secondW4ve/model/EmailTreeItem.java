package com.secondW4ve.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class EmailTreeItem<String> extends TreeItem<String> {

    private String name;
    private ObservableList<EmailMessage> emailMessages;
    private int unreadMessagesCount;

    public EmailTreeItem(String name) {
        super(name);
        this.name = name;
        this.emailMessages = FXCollections.observableArrayList();
    }

    public ObservableList<EmailMessage> getEmailMessages(){
        return this.emailMessages;
    }
    public void addEmail(Message message) throws MessagingException {
        EmailMessage emailMessage = fetchMessage(message);
        emailMessages.add(emailMessage);
        System.out.println("Working" + name + message.getSubject());
    }

    private EmailMessage fetchMessage(Message message) throws MessagingException {
        boolean isMessageRead = message.getFlags().contains(Flags.Flag.SEEN);
        EmailMessage emailMessage = new EmailMessage(
                message.getSubject(),
                message.getFrom()[0].toString(),
                message.getRecipients(MimeMessage.RecipientType.TO)[0].toString(),
                new SizeInteger(message.getSize()),
                message.getSentDate(),
                isMessageRead,
                message
        );
        if (!isMessageRead){
            incrementUnreadMessagesCount();
        }
        return emailMessage;
    }

    public void incrementUnreadMessagesCount(){
        unreadMessagesCount++;
        updateName();
    }

    public void decrementUnreadMessagesCount(){
        unreadMessagesCount--;
        updateName();
    }

    private void updateName(){
        if (unreadMessagesCount > 0){
            this.setValue((String)(name + "(" + unreadMessagesCount + ")"));
        }
        else{
            this.setValue(name);
        }
    }

    public void addEmailToTop(Message message) throws MessagingException {
        EmailMessage emailMessage = fetchMessage(message);
        emailMessages.add(0,emailMessage);
    }
}
