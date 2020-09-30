package com.secondW4ve.view;

import com.secondW4ve.EmailManager;
import com.secondW4ve.controller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ViewFactory {

    private EmailManager emailManager;
    private ArrayList<Stage> activeStages;

    //View options handling
    private ColorTheme colorTheme = ColorTheme.DEFAULT;
    private FontSize fontSize = FontSize.MEDIUM;

    private boolean mainViewInitialized = false;

    public ViewFactory(EmailManager emailManager) {
        this.emailManager = emailManager;
        activeStages = new ArrayList<>();
    }


    public boolean isMainViewInitialized(){
        return mainViewInitialized;
    }

    public void showLoginWindow(){
        BaseController controller = new LoginWindowController(emailManager,this,
                    "LoginWindow.fxml");
        initialiseStage(controller);
    }

    public void showMainWindow(){
        mainViewInitialized = true;
        BaseController controller = new MainWindowController(emailManager,this,
                "MainWindow.fxml");
        initialiseStage(controller);
    }

    public void showOptionsWindow(){
        BaseController controller = new OptionWindowController(emailManager,this,
                "OptionsWindow.fxml");
        initialiseStage(controller);
    }

    public void showComposeMessageWindow(){
        BaseController controller = new ComposeMessageController(emailManager,this,
                "ComposeMessageWindow.fxml");
        initialiseStage(controller);
    }

    public ColorTheme getColorTheme() {
        return colorTheme;
    }

    public void setColorTheme(ColorTheme colorTheme) {
        this.colorTheme = colorTheme;
    }

    public FontSize getFontSize() {
        return fontSize;
    }

    public void setFontSize(FontSize fontSize) {
        this.fontSize = fontSize;
    }

    private void initialiseStage(BaseController BaseController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(BaseController.getFXMLName()));
        fxmlLoader.setController(BaseController);
        Parent parent;
        try{
            parent = fxmlLoader.load();
        }catch (IOException e){
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        activeStages.add(stage);
    }

    public void closeStage(Stage stageToClose){
        stageToClose.close();
        activeStages.remove(stageToClose);
    }

    public void updateStyles() {
        for(Stage stage: activeStages){
            Scene scene = stage.getScene();
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource(ColorTheme.getCssPath(colorTheme)).toExternalForm());
            scene.getStylesheets().add(getClass().getResource(FontSize.getCssPath(fontSize)).toExternalForm());
        }
    }
}
