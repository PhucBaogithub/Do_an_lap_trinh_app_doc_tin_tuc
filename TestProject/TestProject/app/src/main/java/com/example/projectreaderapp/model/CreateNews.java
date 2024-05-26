package com.example.projectreaderapp.model;

import android.graphics.Bitmap;

public class CreateNews {

    public CreateNews(String nameNews, Integer categoryID, Integer sourceNewsID, Bitmap imageNews) {
        NameNews = nameNews;
        CategoryID = categoryID;
        SourceNewsID = sourceNewsID;
        ImageNews = imageNews;
    }

    public String getNameNews() {
        return NameNews;
    }

    public void setNameNews(String nameNews) {
        NameNews = nameNews;
    }

    public Integer getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(Integer categoryID) {
        CategoryID = categoryID;
    }

    public Integer getSourceNewsID() {
        return SourceNewsID;
    }

    public void setSourceNewsID(Integer sourceNewsID) {
        SourceNewsID = sourceNewsID;
    }

    public Bitmap getImageNews() {
        return ImageNews;
    }

    public void setImageNews(Bitmap imageNews) {
        ImageNews = imageNews;
    }

    private String NameNews;
    private Integer CategoryID, SourceNewsID;
    private Bitmap ImageNews;


}
