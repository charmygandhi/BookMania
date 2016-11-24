package com.example.bookmania;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by Charmy PC on 2016-11-23.
 */

public class UserSessionManager {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context _context;

    public UserSessionManager(Context context){
        this._context = context;
        sharedPreferences = _context.getSharedPreferences("My_Preferences",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void createUserLoginSession(String user){
        editor.putBoolean("IsUserLoggedIn",true);

        editor.putString("user",user);
        editor.commit();
    }

    public boolean checkLogin()
    {
        //if user is not logged in redirect them to main login page
        if(!this.isUserLoggedIn()){


            /*Intent i = new Intent(_context,LoginActivity.class);
            //remove's all the activities on the top
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);*/
            return true;
        }
        return false;
    }

    public void logoutUser(){

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        Intent i = new Intent(_context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }

    public boolean isUserLoggedIn(){
        return sharedPreferences.getBoolean("IsUserLoggedIn", false);
    }
}
