package com.secondW4ve.controller.services;

import com.secondW4ve.model.EmailMessage;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.web.WebEngine;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import java.io.IOException;

public class MessageRendererService extends Service {

    private EmailMessage emailMessage;
    private WebEngine webEngine;
    private StringBuffer buffer;

    public MessageRendererService(WebEngine webEngine) {
        this.webEngine = webEngine;
        buffer = new StringBuffer();
        this.setOnSucceeded(e->{
            displayMessage();
        });
    }

    public void setEmailMessage(EmailMessage emailMessage){
        this.emailMessage = emailMessage;
    }

    private void displayMessage(){
        webEngine.loadContent(buffer.toString());
    }

    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                try{
                    loadMessage();
                }catch(IOException e){

                }
                return null;
            }
        };
    }

    private void loadMessage() throws MessagingException, IOException {
        buffer.setLength(0); //clear buffer
        Message message = emailMessage.getMessage();
        String contentType = message.getContentType();
        if(isSimpleType(contentType)){
            buffer.append(message.getContent().toString());
        }else if(isMultipartType(contentType)){
            Multipart multipart = (Multipart) message.getContent();
            for (int i = multipart.getCount() - 1; i >= 0; i--) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                String bodyPartContentType = bodyPart.getContentType();
                if (isSimpleType(bodyPartContentType)){
                    buffer.append(bodyPart.getContent().toString());
                }
            }
        }
    }

    private boolean isSimpleType(String contentType){
        if (contentType.contains("TEXT/HTML") ||
        contentType.contains("mixed")||
        contentType.contains("text")){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isMultipartType(String contentType){
        if (contentType.contains("multipart")||
            contentType.contains("multipart/mixed")){
            return true;
        }else{
            return false;
        }
    }
}
