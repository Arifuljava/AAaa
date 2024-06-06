package com.example.aaaa.localdatabase;

import android.app.Application;
import android.content.Context;

import java.util.List;

public class PackageManager extends Application {
    public static PackageManager instance;
    public static String name = null;
    public static  String price = null;
    public static  String dailypay = null;

    public static PackageManager CollectData(Context context) {
        loadUserInfoFromSharedPreferences(context);
        return instance;
    }
    private static void loadUserInfoFromSharedPreferences(Context context) {
        String preferencesName = "usertracking";
        List<PackageModel> retrievedMoldList = SharedPreferencesManager.retrieveListFromSharedPreferences(context, preferencesName, "packagedetailskey", PackageModel.class);
        if (retrievedMoldList != null) {
            for (PackageModel mold : retrievedMoldList) {
                name = mold.name;
                price = mold.price;
                dailypay = mold.dailypay;
            }
        }
    }
}
