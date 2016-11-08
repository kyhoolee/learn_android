package com.example.infinitbank.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kylee on 29/10/2016.
 */

public class PreferenceUtil {
    public static final String KEY_PREFS_NOTIFICATION = "key_pref_notification";
    public static final String KEY_GCM_REG_ID = "key_pref_gcm_reg_id";

    public static void storeGCMRegistrationId(Activity context, String regid) {
        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_GCM_REG_ID, regid).commit();
    }
}
