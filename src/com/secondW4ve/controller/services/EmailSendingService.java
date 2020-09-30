package com.secondW4ve.controller.services;

import com.secondW4ve.controller.EmailSendingResult;
import com.secondW4ve.model.EmailAccount;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailSendingService extends Service {

    private EmailAccount emailAccount;
    private String subject;
    private String recipient;
    private String content;

    public EmailSendingService(EmailAccount emailAccount, String subject, String recipient, String content) {
        this.emailAccount = emailAccount;
        this.subject = subject;
        this.recipient = recipient;
        this.content = content;
    }

    @Override
    protected Task<EmailSendingResult> createTask() {
        return new Task<EmailSendingResult>() {
            @Override
            protected EmailSendingResult call(){
                try {
                    //create the message
                    MimeMessage mimeMessage = new MimeMessage(emailAccount.getSession());
                    mimeMessage.setFrom(emailAccount.getAddress());
                    mimeMessage.addRecipients(Message.RecipientType.TO, recipient);
                    mimeMessage.setSubject(subject);
                    //set the content
                    Multipart mimeMultipart = new MimeMultipart();
                    BodyPart messageBodyPart = new MimeBodyPart();
                    messageBodyPart.setContent(content,"text/html");
                    mimeMultipart.addBodyPart(messageBodyPart);
                    mimeMessage.setContent(mimeMultipart);
                    //sending the message
                    Transport transport = emailAccount.getSession().getTransport();
                    transport.connect(
                            emailAccount.getProperties().getProperty("outgoingHost"),
                            emailAccount.getAddress(),
                            emailAccount.getPassword()
                    );
                    transport.sendMessage(mimeMessage,mimeMessage.getAllRecipients());
                    transport.close();
                    return EmailSendingResult.SUCCESS;
                }catch (MessagingException e) {
                    e.printStackTrace();
                    return EmailSendingResult.FAILD_BY_PROVIDER;
                }catch(Exception e) {
                    e.printStackTrace();
                    return EmailSendingResult.FAILD_BY_UNKNOWN_ERROR;
                }

            }
        };
    }
}
