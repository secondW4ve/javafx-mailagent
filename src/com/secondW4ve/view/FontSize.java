package com.secondW4ve.view;

public enum FontSize {
    SMALL,
    MEDIUM,
    BIG;

    public static String getCssPath(FontSize size){
        switch(size){
            case SMALL:
                return "css/fontSmall.css";
            case MEDIUM:
                return "css/fontMedium.css";
            case BIG:
                return "css/fontBig.css";
            default:
                return null;
        }
    }
}
