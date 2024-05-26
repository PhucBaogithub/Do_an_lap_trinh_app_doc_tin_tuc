package com.example.projectreaderapp.model;

import java.util.List;

public class Category {

    private String nameCategory;
    private List<News> news;

    public Category(String nameCategory, List<News> news) {
        this.nameCategory = nameCategory;
        this.news = news;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }
}
