package com.example.aaaa.localdatabase;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;

public class WorkManager {
    public static void checkAndManageWork(Context context, Class<?> targetActivityClass, String fullDate, String lastworkdate) {
        if (TextUtils.isEmpty(lastworkdate)) {
            // lastworkdate is null or empty, navigate to the target activity
            navigateToTargetActivity(context, targetActivityClass);
        } else if (fullDate.equals(lastworkdate)) {
            // Dates are equal, show "Can not work" dialog
            showCannotWorkDialog(context);
        } else {
            // Dates are not equal, navigate to the target activity
            navigateToTargetActivity(context, targetActivityClass);
        }
    }

    private static void navigateToTargetActivity(Context context, Class<?> targetActivityClass) {
        Intent intent = new Intent(context, targetActivityClass);
        context.startActivity(intent);
    }

    private static void showCannotWorkDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Work Restriction")
                .setMessage("Can not work")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
// How to call
/*
AsiaDateTimeHelper helper = new AsiaDateTimeHelper("Asia/Kolkata");
        String fullDate = helper.getFullDate();

        String lastworkdate = SharedPreferencesManager.retrieveTextFromSharedPreferences(this, "usertracking", "lastworkdate");

        WorkManager.checkAndManageWork(this, HomeActivity.class, fullDate, lastworkdate);
 */