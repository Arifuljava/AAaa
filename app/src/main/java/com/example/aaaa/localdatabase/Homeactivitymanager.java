package com.example.aaaa.localdatabase;

//Homeactivitymanager
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.app.Application;
import android.util.Log;

import java.util.List;

public class Homeactivitymanager extends Application {
    public static Homeactivitymanager instance;
    public static String username = null;
    public static  String password = null;
    public static  String email = null;
    public static  String time = null;
    public static  String currentbalance = null;
    public static  String depositbalance = null;
    public static  String uuid = null;


    public static Homeactivitymanager getInstance() {

        return instance;
    }
    public static Homeactivitymanager CollectData(Context context) {
        loadUserInfoFromSharedPreferences(context);
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }

    private static void loadUserInfoFromSharedPreferences(Context context) {
        String preferencesName = "UserDetails";
        List<UserModel> retrievedMoldList = SharedPreferencesManager.retrieveListFromSharedPreferences(context, preferencesName, "userdetailsKey", UserModel.class);
        if (retrievedMoldList != null) {
            for (UserModel mold : retrievedMoldList) {
               username = mold.username;
                password = mold.password;
                email = mold.email;
                time = mold.time;
                currentbalance = mold.currentbalance;
                depositbalance = mold.depositbalance;
                uuid = mold.uuid;


            }
        }
    }

}
//How to call
/*
   String username = Homeactivitymanager.CollectData(MainActivity2.this).username;
        Log.d("KKKKKKKKKKKK", ""+username);
 */