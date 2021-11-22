package com.example.gelgel;

public class ProductModel {

    String img, title, description;
    double price;
    int discount;

    public ProductModel(String img, String title, String description, double price, int discount) {
        this.img = img;
        this.title = title;
        this.description = description;
        this.price = price;
        this.discount = discount;
    }
    public ProductModel(){}

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
