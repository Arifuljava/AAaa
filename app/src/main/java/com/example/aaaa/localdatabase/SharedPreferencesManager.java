package com.example.aaaa.localdatabase;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class SharedPreferencesManager {

    // Save text to SharedPreferences
    public static void saveTextToSharedPreferences(Context context, String preferencesName, String text, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, text);
        editor.apply(); // Asynchronously save the preferences without blocking the UI
    }

    // Retrieve text from SharedPreferences
    public static String retrieveTextFromSharedPreferences(Context context, String preferencesName, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

    // Save list to SharedPreferences
    public static <T> void saveListToSharedPreferences(Context context, String preferencesName, List<T> list, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
    }

    // Retrieve list from SharedPreferences
    public static <T> List<T> retrieveListFromSharedPreferences(Context context, String preferencesName, String key, Class<T> clazz) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(key, null);
        if (json == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = TypeToken.getParameterized(List.class, clazz).getType();
        return gson.fromJson(json, type);
    }
    public static void clearSharedPreferences(Context context,
                                              String preferencesName,
                                              String key) {
        SharedPreferences preferences = context.getSharedPreferences(preferencesName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.apply();
    }
}
