package com.example.medisoft.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Arrays;

//SHARED PREFERENCE MANAGER FOR INBUILT APP SESSION
public class AppPreference {

    private static Context mContext;
    public static final String APP_PREF_NAME = "medisoft_digital";
    public final static String REG_INITIAL = "reg_initial";
    public final static String LOGIN_INITIAL = "login_initial";
    public final static String USERNAME = "username";
    public final static String IS_ADMIN = "is_admin";

    public final static String IS_SHOP = "is_shop";
    public final static String IS_USER = "is_user";
    public final static String MOBILE = "mobile";
    public final static String NAME = "name";

    public final static String CLIENTID = "clientid";
    public final static String CLIENTNAME = "clientname";
    private static AppPreference appPreference = null;

    private SharedPreferences sharedPreferences, settingsPreferences;
    private SharedPreferences.Editor editor;

    public static AppPreference getInstance(Context context) {
        if(appPreference == null) {
            mContext = context;
            appPreference = new AppPreference();
        }
        return appPreference;
    }
    private AppPreference() {
        sharedPreferences = mContext.getSharedPreferences(APP_PREF_NAME, Context.MODE_PRIVATE);
        settingsPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        editor = sharedPreferences.edit();

    }

    //SETTER AND GETTER FOR SVAING AND ACCESSING THE STRING CONSTANTS
    public void setString(String key, String value) {
        editor.putString(key , value);
        editor.commit();
    }
    public String getString(String key) {
        String string = sharedPreferences.getString(key, "0");
        return string;
    }

    public void setBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }
    public Boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public void setInteger(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInteger(String key) {
        return sharedPreferences.getInt(key, -1);
    }

    public void setStringArray(String key, ArrayList<String> values) {
        if (values != null && !values.isEmpty()) {
            String value = "";
            for (String str : values) {
                if(value.isEmpty()) {
                    value = str;
                } else {
                    value = value + "," + str;
                }
            }
            setString(key, value);
        }
    }

    public ArrayList<String> getStringArray(String key) {
        ArrayList<String> arrayList = new ArrayList<>();
        String value = getString(key);
        if (value != null) {
            arrayList = new ArrayList<>(Arrays.asList(value.split(",")));
        }
        return arrayList;
    }


}
