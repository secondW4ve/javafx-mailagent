module MailAgentProject {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.web;
    requires javafx.graphics;
    requires activation;
    requires java.mail;

    opens com.secondW4ve;
    opens com.secondW4ve.view;
    opens com.secondW4ve.controller;
    opens com.secondW4ve.model;
}