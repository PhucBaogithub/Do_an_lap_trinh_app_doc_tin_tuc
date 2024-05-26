package com.example.projectreaderapp.model;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class News {
    public News(Bitmap resourceId, Bitmap resourceId2, String name, String idnews, String nameauthor) {
        this.resourceId = resourceId;
        this.resourceId2 = resourceId2;
        this.name = name;
        this.idnews = idnews;
        this.nameauthor = nameauthor;
    }

    public Bitmap getResourceId() {
        return resourceId;
    }

    public void setResourceId(Bitmap resourceId) {
        this.resourceId = resourceId;
    }

    public Bitmap getResourceId2() {
        return resourceId2;
    }

    public void setResourceId2(Bitmap resourceId2) {
        this.resourceId2 = resourceId2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdnews() {
        return idnews;
    }

    public void setIdnews(String idnews) {
        this.idnews = idnews;
    }

    public String getNameauthor() {
        return nameauthor;
    }

    public void setNameauthor(String nameauthor) {
        this.nameauthor = nameauthor;
    }

    private Bitmap resourceId, resourceId2;
    private String name, idnews, nameauthor;
}
