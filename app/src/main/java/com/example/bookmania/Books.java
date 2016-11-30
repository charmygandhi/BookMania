package com.example.bookmania;

/**
 * Created by Charmy PC on 2016-11-11.
 */

public class Books {

    public String title;
    public String price;
    public String description;
    public String image;
    public String category;
    public String address;
    public String user;

    public Books()
    {
        super();
    }

    public Books(String title,String price,String description,String image,String category,String address)
    {
        this.title = title;
        this.price = price;
        this.description = description;
        this.image = image;
        this.category = category;
        this.address = address;
    }


    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getPrice(){
        return price;
    }

    public void setPrice(String price){
        this.price = price;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getImage(){
        return image;
    }

    public void setImage(String image){
        this.image = image;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getUser(){
        return user;
    }

    public void setUser(String user){
        this.user = user;
    }
}
