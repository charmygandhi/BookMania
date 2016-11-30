package com.example.bookmania;

/**
 * Created by Charmy PC on 2016-11-27.
 */

public class Users {
    public String userId;
    public String email;

    public Users(){
        super();
    }

    public Users(String userId,String email){
        this.userId = userId;
        this.email = email;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getUserId(){
        return userId;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }
}
