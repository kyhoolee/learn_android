package com.example.infinitbank;

import android.app.Application;
import android.util.Log;

import com.anupcowkur.reservoir.Reservoir;
import com.example.infinitbank.util.CacheUtil;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.example.infinitbank.model.UserProfile;
import com.example.infinitbank.service.ServiceEndpoint;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by kylee on 29/10/2016.
 */

public class MyApplication extends Application{

    private static final String PROPERTY_ID = "UA-75669591-5";

    public enum TrackerName {
        APP_TRACKER, // Tracker used only in this app.
        GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
        ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
    }

    HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

    public Tracker getTracker(TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {

            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics.newTracker(R.xml.app_tracker)
                    : (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(PROPERTY_ID)
                    : null;
            mTrackers.put(trackerId, t);

        }
        return mTrackers.get(trackerId);
    }

    private UserProfile mMyProfile;

    @Override
    public void onCreate() {
        super.onCreate();

//        FlurryAgent.init(this, "DZVT2ZBHXPQT74J9WR56");
//        FlurryAgent.setCaptureUncaughtExceptions(true);
//
//        FacebookSdk.sdkInitialize(getApplicationContext());
//
        ImageLoaderConfiguration config = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(config);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        Log.e("kyhoolee", "init application");
        ServiceEndpoint.init();

        try {
            Reservoir.init(this, 5 * 2048 * 1024); //in bytes
        } catch (Exception e) {
            //failure
        }

        mMyProfile = CacheUtil.getUserProfile();
    }

    public UserProfile getMyProfile() {
        return mMyProfile;
    }
    public String getProfileId() {
        if (mMyProfile == null)
            return "-69";
        else
            return mMyProfile.getId();
    }

    public void setMyProfile(UserProfile myProfile) {
        this.mMyProfile = myProfile;
    }

    public void logout() {
        mMyProfile = null;
    }

//    private List<Topic> mTopicList;
//    private List<TipTopic> mTipTopicList;
//
//    public List<Topic> getTopicList() {
//        if (mTopicList == null)
//            return new ArrayList<>();
//        return mTopicList;
//    }
//
//    public List<TipTopic> getTipTopicList() {
//        if (mTipTopicList == null)
//            return new ArrayList<>();
//        return mTipTopicList;
//    }
//
//    public void setTipTopicList(List<TipTopic> mTipTopicList) {
//        this.mTipTopicList = mTipTopicList;
//    }
//
//    public void setTopicList(List<Topic> topicList) {
//        this.mTopicList = topicList;
//    }

    public String getProfileToken() {
        if  (mMyProfile == null)
            return "null";
        return mMyProfile.getAccessToken();
    }

    public boolean isUserLoggedIn() {
        if (mMyProfile == null)
            return false;
        return true;
    }

//    public Topic getTopicById(String id) {
//        if (mTopicList == null)
//            return new Topic();
//        for (Topic topic : mTopicList) {
//            if (topic.getId().equals(id))
//                return topic;
//        }
//        return new Topic();
//    }
//
//    public TipTopic getTipTopicById(String id) {
//        if (mTipTopicList == null)
//            return new TipTopic();
//        for (TipTopic topic : mTipTopicList) {
//            if (topic.getId().equals(id))
//                return topic;
//        }
//        return new TipTopic();
//    }
}
