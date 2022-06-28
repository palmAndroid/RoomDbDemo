package com.roomdbdemo.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preference {
    private static Preference instance = null;
    private SharedPreferences sharedPreferences;
    private Context context;
    private static final String KEY_EMPLOYEE_ID = "employee_id";


    private Preference(Context context){

        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static Preference getInstance(Context context){

        if (instance == null){
            instance = new Preference(context);
        }
        return instance;
    }

    public void setEmployeeId(int empId){
        sharedPreferences.edit().putInt(KEY_EMPLOYEE_ID,empId).apply();
    }
    public int getEmployeeId(){
        return sharedPreferences.getInt(KEY_EMPLOYEE_ID,0);
    }
}
