package com.example.aaaa.localdatabase;

import android.app.Activity;
import android.content.Intent;

public class MainAtivityManager {
    private Activity activity;
    private String getuser;

    public MainAtivityManager(Activity activity, String getuser) {
        this.activity = activity;
        this.getuser = getuser;
    }

    public void navigateToAppropriateActivity() {
        if (getuser == null || getuser.isEmpty()) {
            navigateToLoginActivity();
        } else {
            navigateToHomeActivity();
        }
    }

    private void navigateToLoginActivity() {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.finish(); // Optional: Finish the current activity
    }

    private void navigateToHomeActivity() {
        Intent intent = new Intent(activity, HomeActivity.class);
        activity.startActivity(intent);
        activity.finish(); // Optional: Finish the current activity
    }
}
//User tracking first page
//how to use
/*
        String getuser = SharedPreferencesManager.retrieveTextFromSharedPreferences(this, "usertracking","userKey");
        Log.d("Usssss",""+getuser);
         MainAtivityManager navigationHelper;
        navigationHelper = new MainAtivityManager(this, getuser);
        navigationHelper.navigateToAppropriateActivity();
 */