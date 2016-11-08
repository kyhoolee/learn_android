package com.example.infinitbank.util;

import android.app.Activity;

import com.anupcowkur.reservoir.Reservoir;
import com.example.infinitbank.MyApplication;
import com.example.infinitbank.model.UserProfile;

/**
 * Created by kylee on 29/10/2016.
 */

public class CacheUtil {
    private static final String KEY_CACHE_USER_PROFILE = "cache_user_profile";
    public static void cacheUserProfile(Activity activity) {
        MyApplication app = (MyApplication) activity.getApplication();
        UserProfile myProfile = app.getMyProfile();
        if (myProfile != null)
            try {
                Reservoir.put(KEY_CACHE_USER_PROFILE, myProfile);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public static void removeCacheUserProfile() {
        try {
            Reservoir.delete(KEY_CACHE_USER_PROFILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static UserProfile getUserProfile() {
        try {
            return Reservoir.get(KEY_CACHE_USER_PROFILE, UserProfile.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
