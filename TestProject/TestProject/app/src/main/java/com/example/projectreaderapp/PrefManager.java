package com.example.projectreaderapp;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    public String getUsername;
    Context context;
    public PrefManager(Context context) {
        this.context = context;
    }
    public void saveLoginDetails(String userName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userName", userName);
        editor.commit();
    }
    public String getUsername() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("userName", "");
    }
}
