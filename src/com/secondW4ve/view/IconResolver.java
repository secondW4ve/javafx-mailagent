package com.secondW4ve.view;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class IconResolver  {

    public Node getIconFromFolder(String folderName){
        String lowerCaseFolderName = folderName.toLowerCase();
        ImageView imageView;
        try{
            if (lowerCaseFolderName.contains("@")){
                imageView = new ImageView(new Image(getClass().getResourceAsStream("icons/email.png")));

            }else if(lowerCaseFolderName.contains("inbox")){
                imageView = new ImageView(new Image(getClass().getResourceAsStream("icons/inbox.png")));
            }else if (lowerCaseFolderName.contains("надіслані")){
                imageView = new ImageView(new Image(getClass().getResourceAsStream("icons/sent2.png")));
            }else if (lowerCaseFolderName.contains("спам")){
                imageView = new ImageView(new Image(getClass().getResourceAsStream("icons/spam.png")));
            } else{
                imageView = new ImageView(new Image(getClass().getResourceAsStream("icons/folder.png")));
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        return imageView;
    }
}
