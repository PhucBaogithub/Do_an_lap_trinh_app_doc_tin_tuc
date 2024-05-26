package com.example.projectreaderapp.model;

import android.graphics.Bitmap;

public class SourceNews {

    public SourceNews(String sourceNewsName, Bitmap btnimgv_logo_news) {
        SourceNewsName = sourceNewsName;
        this.btnimgv_logo_news = btnimgv_logo_news;
    }

    public String getSourceNewsName() {
        return SourceNewsName;
    }

    public void setSourceNewsName(String sourceNewsName) {
        SourceNewsName = sourceNewsName;
    }

    public Bitmap getBtnimgv_logo_news() {
        return btnimgv_logo_news;
    }

    public void setBtnimgv_logo_news(Bitmap btnimgv_logo_news) {
        this.btnimgv_logo_news = btnimgv_logo_news;
    }

    private String SourceNewsName;
    private Bitmap btnimgv_logo_news;
}
