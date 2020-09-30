package com.secondW4ve;

import com.secondW4ve.controller.services.FetchFolderService;
import com.secondW4ve.controller.services.FolderUpdaterService;
import com.secondW4ve.model.EmailAccount;
import com.secondW4ve.model.EmailMessage;
import com.secondW4ve.model.EmailTreeItem;
import com.secondW4ve.view.IconResolver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import javax.mail.Flags;
import javax.mail.Folder;
import java.util.ArrayList;
import java.util.List;

public class EmailManager {

    //Folders handling:
    private EmailTreeItem<String> foldersRoot = new EmailTreeItem<String>("");

    private FolderUpdaterService folderUpdaterService;

    private EmailMessage selectedMessage;
    private EmailTreeItem<String> selectedFolder;

    private List<Folder> folderList = new ArrayList<>();
    private ObservableList<EmailAccount> emailAccounts = FXCollections.observableArrayList();

    private IconResolver iconResolver = new IconResolver();


    public EmailManager(){
        folderUpdaterService = new FolderUpdaterService(folderList);
        folderUpdaterService.start();
    }

    public ObservableList<EmailAccount> getEmailAccounts(){ return this.emailAccounts; }

    public EmailMessage getSelectedMessage() {
        return selectedMessage;
    }

    public void setSelectedMessage(EmailMessage selectedMessage) {
        this.selectedMessage = selectedMessage;
    }

    public EmailTreeItem<String> getSelectedFolder() {
        return selectedFolder;
    }

    public void setSelectedFolder(EmailTreeItem<String> selectedFolder) {
        this.selectedFolder = selectedFolder;
    }

    public List<Folder> getFolderList() {
        return folderList;
    }

    public TreeItem<String> getFoldersRoot() {
        return foldersRoot;
    }

    public void setFoldersRoot(EmailTreeItem<String> foldersRoot) {
        this.foldersRoot = foldersRoot;
    }

    public void addEmailAccount(EmailAccount emailAccount){
        emailAccounts.add(emailAccount);
        EmailTreeItem<String> treeItem = new EmailTreeItem<>(emailAccount.getAddress());
        treeItem.setGraphic(iconResolver.getIconFromFolder(emailAccount.getAddress()));
        FetchFolderService fetchFolderService = new FetchFolderService(emailAccount.getStore(), treeItem, folderList);
        fetchFolderService.start();
        foldersRoot.getChildren().add(treeItem);
    }


    public void setMessageToRead() {
        try{
            selectedMessage.setRead(true);
            selectedMessage.getMessage().setFlag(Flags.Flag.SEEN,true);
            selectedFolder.decrementUnreadMessagesCount();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setMessageToUnRead() {
        try{
            selectedMessage.setRead(false);
            selectedMessage.getMessage().setFlag(Flags.Flag.SEEN,false);
            selectedFolder.incrementUnreadMessagesCount();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteSelectedMessage() {
        try{
            selectedMessage.getMessage().setFlag(Flags.Flag.DELETED,true);
            selectedFolder.getEmailMessages().remove(selectedMessage);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
