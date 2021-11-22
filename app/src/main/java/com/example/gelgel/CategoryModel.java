package com.example.gelgel;

public class CategoryModel {
    String img, title;

    public CategoryModel(String img, String title) {
        this.img = img;
        this.title = title;
    }
    public CategoryModel(){

    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
