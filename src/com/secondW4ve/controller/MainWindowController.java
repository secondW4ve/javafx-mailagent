package com.secondW4ve.controller;

import com.secondW4ve.EmailManager;
import com.secondW4ve.controller.services.MessageRendererService;
import com.secondW4ve.model.EmailMessage;
import com.secondW4ve.model.EmailTreeItem;
import com.secondW4ve.model.SizeInteger;
import com.secondW4ve.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.util.Callback;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {

    @FXML
    private TreeView<String> emailsTreeView;

    @FXML
    private TableView<EmailMessage> emailsTableView;

    @FXML
    private WebView emailWebView;

    @FXML
    private TableColumn<EmailMessage, String> senderCol;

    @FXML
    private TableColumn<EmailMessage, String> subjectCol;

    @FXML
    private TableColumn<EmailMessage, String> recipientCol;

    @FXML
    private TableColumn<EmailMessage, Date> dateCol;

    @FXML
    private TableColumn<EmailMessage, SizeInteger> sizeCol;

    private MessageRendererService messageRendererService;

    private MenuItem markUnreadMenuItem = new MenuItem("Mark as unread");
    private MenuItem deleteMessageMenuItem = new MenuItem("Delete this message");

    public MainWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    void composeMessageAction() {
        viewFactory.showComposeMessageWindow();

    }

    @FXML
    void optionsAction() {
        System.out.println("Options called");
        viewFactory.showOptionsWindow();
    }

    @FXML
    void addAcountAction() {
        viewFactory.showLoginWindow();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpEmailsTreeView();
        setUpEmailsTableView();
        setUpFolderSelection();
        setUpBoldRows();
        setUpMessageRenderService();
        setUpMessageSelection();
        setUpContextMenus();
    }

    private void setUpContextMenus() {
        markUnreadMenuItem.setOnAction(e->{
            emailManager.setMessageToUnRead();
        });
        deleteMessageMenuItem.setOnAction(e->{
            emailManager.deleteSelectedMessage();
            emailWebView.getEngine().loadContent("");
        });
    }

    private void setUpMessageSelection() {
        emailsTableView.setOnMouseClicked(e->{
            EmailMessage emailMessage = emailsTableView.getSelectionModel().getSelectedItem();
            if(emailMessage != null){
                emailManager.setSelectedMessage(emailMessage);
                if (!emailMessage.isRead()){
                    emailManager.setMessageToRead();
                }
                messageRendererService.setEmailMessage(emailMessage);
                messageRendererService.restart();
            }
        });
    }

    private void setUpMessageRenderService() {
        messageRendererService = new MessageRendererService(emailWebView.getEngine());
    }

    private void setUpBoldRows() {
        emailsTableView.setRowFactory(new Callback<TableView<EmailMessage>, TableRow<EmailMessage>>() {
            @Override
            public TableRow<EmailMessage> call(TableView<EmailMessage> param) {
                return new TableRow<EmailMessage>(){
                    @Override
                    protected void updateItem(EmailMessage item, boolean empty){
                        super.updateItem(item,empty);
                        if(item != null){
                            if (item.isRead()){
                                setStyle("");
                            }else{
                                setStyle("-fx-font-weight: bold");
                            }
                        }
                    }
                };
            }
        });
    }

    private void setUpFolderSelection() {
        emailsTreeView.setOnMouseClicked(e->{
            EmailTreeItem<String> item = (EmailTreeItem<String>) emailsTreeView.getSelectionModel().getSelectedItem();
            if (item != null){
                emailManager.setSelectedFolder(item);
                emailsTableView.setItems(item.getEmailMessages());
            }
        });
    }

    private void setUpEmailsTableView() {
        senderCol.setCellValueFactory(new PropertyValueFactory<EmailMessage,String>("sender"));
        subjectCol.setCellValueFactory(new PropertyValueFactory<EmailMessage,String>("subject"));
        recipientCol.setCellValueFactory(new PropertyValueFactory<EmailMessage,String>("recipient"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<EmailMessage,SizeInteger>("size"));
        dateCol.setCellValueFactory(new PropertyValueFactory<EmailMessage,Date>("date"));

        emailsTableView.setContextMenu(new ContextMenu(markUnreadMenuItem,deleteMessageMenuItem));
    }

    private void setUpEmailsTreeView() {
        emailsTreeView.setRoot(emailManager.getFoldersRoot());
        emailsTreeView.setShowRoot(false);
    }



}
