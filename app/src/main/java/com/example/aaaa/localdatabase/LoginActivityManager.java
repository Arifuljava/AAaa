package com.example.aaaa.localdatabase;

import android.app.Activity;
import android.content.Intent;
import java.util.List;

public class LoginActivityManager {
    private Activity activity;
    private List<UserModel> retrievedMoldList;
    private String username;
    private String password;

    public LoginActivityManager(Activity activity, List<UserModel> retrievedMoldList, String username, String password) {
        this.activity = activity;
        this.retrievedMoldList = retrievedMoldList;
        this.username = username;
        this.password = password;
    }

    public void authenticateUser() {
        boolean usernameFound = false;
        for (UserModel userModel : retrievedMoldList) {
            if (userModel.getUsername().equals(username)) {
                usernameFound = true;
                if (userModel.getPassword().equals(password)) {
                    navigateToHomeActivity();
                } else {
                    System.out.println("Incorrect password.");
                }
                break;
            }
        }
        if (!usernameFound) {
            System.out.println("Username not found.");
        }
    }

    private void navigateToHomeActivity() {
        Intent intent = new Intent(activity, HomeActivity.class);
        activity.startActivity(intent);
        activity.finish(); // Optional: Finish the current activity
    }
}
//for login
/*
 private LoginActivityManager authenticationHelper;
  String preferencesName = "UserDetails";
        List<UserModel> retrievedMoldList = SharedPreferencesManager.retrieveListFromSharedPreferences(this, preferencesName, "userdetailsKey", UserModel.class);

        if (retrievedMoldList != null) {
            for (UserModel mold : retrievedMoldList) {
                System.out.println("Name: " + mold.getUsername() + ", Class: " + mold.getPassword());
            }
        }

        // Assuming you have the username and password values here
        String username = "john_doe";
        String password = "mypassword";

        authenticationHelper = new LoginActivityManager(this, retrievedMoldList, username, password);
        authenticationHelper.authenticateUser();
 */