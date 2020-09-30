package com.secondW4ve.controller;

import com.secondW4ve.EmailManager;
import com.secondW4ve.view.ColorTheme;
import com.secondW4ve.view.FontSize;
import com.secondW4ve.view.ViewFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class OptionWindowController extends BaseController implements Initializable {
    public OptionWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);

    }

    @FXML
    private Slider sizeFontPicker;

    @FXML
    private ChoiceBox<ColorTheme> themePicker;

    @FXML
    void applyButtonAction() {
        viewFactory.setColorTheme(themePicker.getValue());
        viewFactory.setFontSize(FontSize.values()[(int)sizeFontPicker.getValue()]);
        viewFactory.updateStyles();
    }

    @FXML
    void cancelButtonAction() {
        Stage stage = (Stage)themePicker.getScene().getWindow();
        viewFactory.closeStage(stage);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpPicker();
        setUpSizePicker();
    }

    private void setUpSizePicker() {
        sizeFontPicker.setMin(0);
        sizeFontPicker.setMax(FontSize.values().length-1);
        sizeFontPicker.setValue(viewFactory.getFontSize().ordinal());
        sizeFontPicker.setMinorTickCount(0);
        sizeFontPicker.setMajorTickUnit(1);
        sizeFontPicker.setBlockIncrement(1);
        sizeFontPicker.setSnapToTicks(true);
        sizeFontPicker.setShowTickMarks(true);
        sizeFontPicker.setShowTickLabels(true);
        sizeFontPicker.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                int i = object.intValue();
                return FontSize.values()[i].toString();
            }

            @Override
            public Double fromString(String string) {
                return null;
            }
        });
        sizeFontPicker.valueProperty().addListener((obs,oldVal,newVal)->{
            sizeFontPicker.setValue(newVal.intValue());
        });
    }

    private void setUpPicker() {
        themePicker.setItems(FXCollections.observableArrayList(ColorTheme.values()));
        themePicker.setValue(viewFactory.getColorTheme());
    }
}
