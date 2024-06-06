package com.example.aaaa.localdatabase;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class ProfileUpgradeManager {
    private static final String PROFILE_UPGRADE_KEY = "profileupgradekey";
    private static final String PROFILE_UPGRADED = "upgraded";

    public static void checkAndHandleProfileUpgrade(Context context, Class<?> targetActivityClass) {
        String profileUpgrade = SharedPreferencesManager.retrieveTextFromSharedPreferences(context, "usertracking", PROFILE_UPGRADE_KEY);

        if (profileUpgrade != null && profileUpgrade.equals(PROFILE_UPGRADED)) {
            // Profile is upgraded, navigate to the target activity
            Intent intent = new Intent(context, targetActivityClass);
            context.startActivity(intent);
        } else {
            // Profile is not upgraded, show an alert dialog
            showUpgradeProfileDialog(context,targetActivityClass);
        }
    }

    private static void showUpgradeProfileDialog(Context context,Class<?> targetActivityClass) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Upgrade Profile")
                .setMessage("Please upgrade your profile to continue.")
                .setPositiveButton("Upgrade", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        String username = Homeactivitymanager.CollectData(context).username;
                        String password = Homeactivitymanager.CollectData(context).password;
                        String email = Homeactivitymanager.CollectData(context).email;
                        String time = Homeactivitymanager.CollectData(context).username;

                        String currentbalance = Homeactivitymanager.CollectData(context).currentbalance;
                        String depositbalance = Homeactivitymanager.CollectData(context).depositbalance;
                        String uuid = Homeactivitymanager.CollectData(context).uuid;


                    }
                }) // Add your upgrade logic here
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
//How to call
/*
ProfileUpgradeManager.checkAndHandleProfileUpgrade(this, HomeActivity.class);
 */