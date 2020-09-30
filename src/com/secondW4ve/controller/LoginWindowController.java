package com.secondW4ve.controller;

import com.secondW4ve.EmailManager;
import com.secondW4ve.controller.services.LoginService;
import com.secondW4ve.model.EmailAccount;
import com.secondW4ve.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginWindowController extends BaseController implements Initializable {

    @FXML
    private TextField emailAddressField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    public LoginWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    void loginButtonAction() {
        if(fieldsValidation()){
            EmailAccount emailAccount = new EmailAccount(emailAddressField.getText(),passwordField.getText());
            LoginService loginService = new LoginService(emailAccount, emailManager);
            loginService.start();
            loginService.setOnSucceeded(event -> {
                EmailLoginResult emailLoginResult = loginService.getValue();
                switch (emailLoginResult){
                    case SUCCESS:
                        System.out.println("Logged acount" + emailAccount);
                        if(!viewFactory.isMainViewInitialized())
                            viewFactory.showMainWindow();
                        Stage stage = (Stage)errorLabel.getScene().getWindow();
                        viewFactory.closeStage(stage);
                        return;
                    case FAILED_BY_CREDENTIALS:
                        errorLabel.setText("Invalid credentials");
                        return;
                    case FAILED_BY_UNEXPECTED_ERROR:
                        errorLabel.setText("Unexpected error");
                        return;
                    default:
                        return;

                }
            });
        }
    }

    private boolean fieldsValidation(){
        if(!checkEmailOnCorrectForm()){
            errorLabel.setText("Please, write email in correct form");
            return false;
        }
        if(emailAddressField.getText().isEmpty()){
            errorLabel.setText("Please fill email field");
            return false;
        }
        if(passwordField.getText().isEmpty()){
            errorLabel.setText("Please fill password field");
        }
        return true;
    }

    private boolean checkEmailOnCorrectForm(){
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailAddressField.getText().toString());
        if(matcher.find()){
            return true;
        }
        else {
            errorLabel.setText("Write your email in correct format");
            return false;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        emailAddressField.setText("iamsecondW4ve@gmail.com");
        passwordField.setText("Cezar_159753");
    }
}
