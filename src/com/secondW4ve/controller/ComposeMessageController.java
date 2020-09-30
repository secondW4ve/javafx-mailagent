package com.secondW4ve.controller;

import com.secondW4ve.EmailManager;
import com.secondW4ve.controller.services.EmailSendingService;
import com.secondW4ve.model.EmailAccount;
import com.secondW4ve.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ComposeMessageController extends BaseController implements Initializable {

    @FXML
    private TextField recipientTextField;

    @FXML
    private TextField subjectTextField;

    @FXML
    private Label errorLabel;

    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    private Button sendButton;

    @FXML
    private Button cancelButton;

    @FXML
    private ChoiceBox<EmailAccount> emailAccountChoice;

    public ComposeMessageController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        emailAccountChoice.setItems(emailManager.getEmailAccounts());
        emailAccountChoice.setValue(emailManager.getEmailAccounts().get(0));
    }

    @FXML
    void cancelButtonAction() {
        Stage stage = (Stage)recipientTextField.getScene().getWindow();
        viewFactory.closeStage(stage);
    }

    @FXML
    void sendButtonAction() {
        EmailSendingService emailSendingService = new EmailSendingService(
                emailAccountChoice.getValue(),
                subjectTextField.getText(),
                recipientTextField.getText(),
                htmlEditor.getHtmlText()
        );
        emailSendingService.start();
        emailSendingService.setOnSucceeded(e-> {
            EmailSendingResult emailSendingResult = (EmailSendingResult) emailSendingService.getValue();
            System.out.println("text");
            switch (emailSendingResult) {
                case SUCCESS:
                    Stage stage = (Stage)recipientTextField.getScene().getWindow();
                    viewFactory.closeStage(stage);
                    break;
                case FAILD_BY_PROVIDER:
                    errorLabel.setText("Provider error");
                    break;
                case FAILD_BY_UNKNOWN_ERROR:
                    errorLabel.setText("Unknown error");
                    break;
            }

        });
    }
}
