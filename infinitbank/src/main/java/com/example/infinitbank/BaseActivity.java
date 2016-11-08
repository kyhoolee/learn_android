package com.example.infinitbank;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anupcowkur.reservoir.Reservoir;
import com.example.infinitbank.model.NotificationMessage;
import com.example.infinitbank.model.UserProfile;
import com.example.infinitbank.service.OnServiceResponseHandler;
import com.example.infinitbank.service.ServiceEndpoint;
import com.example.infinitbank.service.ServiceUtil;
import com.example.infinitbank.util.AppNavigation;
import com.example.infinitbank.util.CacheUtil;
import com.example.infinitbank.util.ImageUtil;
import com.example.infinitbank.util.PreferenceUtil;
import com.example.infinitbank.util.ToastUtil;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.example.infinitbank.util.Util;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kylee on 29/10/2016.
 */

public class BaseActivity extends AppCompatActivity {
    private static final int HANDLE_SCHEDULE_HIDE_NOTIFICATION_LAYOUT = 2;
    public static final String KEY_CACHE_TOPIC_LIST = "key_cache_topic_list";
    public static final String KEY_CACHE_TIP_TOPIC_LIST = "key_cache_topic_list";
    protected MyApplication mApp;
    private UserProfile mMyProfile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("kyhoolee", "init base activity");
        mApp = (MyApplication) getApplication();

        Tracker tracker = mApp.getTracker(MyApplication.TrackerName.APP_TRACKER);
        tracker.enableAdvertisingIdCollection(true);
        //mCallbackManager = CallbackManager.Factory.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        mCallbackManager.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 6) {
//            if (resultCode == RESULT_OK) {
//                ToastUtil.showMessage(BaseActivity.this, "Chào mừng bạn quay trở lại, "
//                        + mApp.getMyProfile().getName());
//                registerPushNotification(BaseActivity.this);
//            }
//        }
    }

//    private void configFacebookLoginButton(ImageButton button) {
//        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Log.e("huunc", "on success");
//                getMyProfileAndLogin(loginResult.getAccessToken());
//
//            }
//
//            @Override
//            public void onCancel() {
//                hideLoadingDialog();
//                Log.e("huunc", "on cancel");
//            }
//
//            @Override
//            public void onError(FacebookException exception) {
//                hideLoadingDialog();
//                Log.e("huunc", "on error");
//            }
//        });
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showLoadingDialog("Logging in...");
//                LoginManager.getInstance().logInWithReadPermissions(BaseActivity.this,
//                        Arrays.asList("public_profile", "email", "user_friends"));
//            }
//        });
//    }

//    private void getMyProfileAndLogin(final AccessToken accessToken) {
//        GraphRequest.newMeRequest(
//                accessToken,
//                new GraphRequest.GraphJSONObjectCallback() {
//                    @Override
//                    public void onCompleted(
//                            JSONObject object,
//                            GraphResponse response) {
//                        try {
//                            String username = object.getString("id");
//
//                            String bodyReq = "grant_type=password&username=" + username +
//                                    "&password=" + accessToken.getToken() + "&scope=&format=json&client_id=" + ServiceEndpoint.APP_ID +
//                                    "&client_secret=" + ServiceEndpoint.APP_SECRET + "&action=signup&provider=facebook&device_id=" +
//                                    Util.getDeviceId(BaseActivity.this)
//                                    + "&device_os=android";
//                            ServiceUtil.sendPostRequestToServer(BaseActivity.this, ServiceEndpoint.AUTH_ACCOUNT_ENDPOINT,
//                                    bodyReq, "application/x-www-form-urlencoded", new OnServiceResponseHandler() {
//                                        @Override
//                                        public void onSuccess(JSONObject response) {
//                                            Log.e("huunc", "login response " + response.toString());
//                                            try {
//                                                String userId = response.getString("user_id");
//                                                String accessToken = response.getString("access_token");
//                                                String refreshToken = response.getString("refresh_token");
//
//                                                UserProfile myProfile = new UserProfile(userId, accessToken, refreshToken);
//                                                mApp.setMyProfile(myProfile);
//                                                CacheUtil.cacheUserProfile(BaseActivity.this);
//                                                fetchUserProfileAndUpdateInfo();
//                                            } catch (JSONException e) {
//                                                e.printStackTrace();
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onFailure(int statusCode) {
//                                            hideLoadingDialog();
//                                            ToastUtil.showError(BaseActivity.this);
//                                        }
//                                    });
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }).executeAsync();
//    }

    private void fetchUserProfileAndUpdateInfo() {
        mMyProfile = mApp.getMyProfile();
        if (mMyProfile != null) {
            ServiceUtil.get(BaseActivity.this, ServiceEndpoint.GET_ACCOUNT_PROFILE.replace("{user_id}", mMyProfile.getId())
                    .replace("{access_token}", mMyProfile.getAccessToken()), new OnServiceResponseHandler() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        mApp.getMyProfile().setName(response.getString("name"));
                        mApp.getMyProfile().setAvatar(response.getString("avatar"));
                        mApp.getMyProfile().setEmail(response.getString("email"));
                        mApp.getMyProfile().setPhone(response.getString("phone"));
                        mApp.getMyProfile().setGender(response.getString("gender"));
                        mApp.getMyProfile().setIdentity(response.getString("identity"));
                        mApp.getMyProfile().setDob(response.getString("dob"));

                        CacheUtil.cacheUserProfile(BaseActivity.this);
                        hideLoadingDialog();
//                        if (mLoginDialog != null)
//                            mLoginDialog.dismiss();
                        ToastUtil.showMessage(BaseActivity.this, "Chào mừng bạn quay trở lại, "
                                + mApp.getMyProfile().getName());
                        registerPushNotification(BaseActivity.this);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode) {

                }
            });
        } else {
            hideLoadingDialog();
            ToastUtil.showError(BaseActivity.this);
        }

    }


//    private CallbackManager mCallbackManager;
//    private Dialog mLoginDialog;

    public void showLoginDialog() {
//        mLoginDialog = new Dialog(this);
//        mLoginDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        mLoginDialog.setContentView(R.layout.dialog_login);
//        mLoginDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        mLoginDialog.setCancelable(true);
//        mLoginDialog.getWindow().getAttributes().windowAnimations = R.style.PopUpAnimation;
//
//        WindowManager.LayoutParams lp = mLoginDialog.getWindow().getAttributes();
//        lp.dimAmount = 0.5f;
//        mLoginDialog.getWindow().setAttributes(lp);
//
//        ImageButton loginButton = (ImageButton) mLoginDialog.findViewById(R.id.button_fb_login);
//        configFacebookLoginButton(loginButton);
//        ImageButton buttonClose = (ImageButton) mLoginDialog.findViewById(R.id.button_close_dialog);
//        buttonClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mLoginDialog.dismiss();
//            }
//        });

//        mLoginDialog.show();
        AppNavigation.showLoginActivity(this);
    }

    protected void setActionbarTitle(String title) {
//        RelativeLayout toolbar = (RelativeLayout) findViewById(R.id.toolbar);
//        if (toolbar != null) {
//            TextView titleText = (TextView) toolbar.findViewById(R.id.text_title);
//            titleText.setText(title);
//        }
    }

    protected void setupToolbar(String title, boolean backEnabled) {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        if (toolbar != null) {
//            setSupportActionBar(toolbar);
//        }
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setTitle(title);
//            if (backEnabled)
//                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }

//        RelativeLayout toolbar = (RelativeLayout) findViewById(R.id.toolbar);
//        if (toolbar != null) {
//            ImageButton backButton = (ImageButton) toolbar.findViewById(R.id.button_back);
//            TextView titleText = (TextView) toolbar.findViewById(R.id.text_title);
//            titleText.setText(title);
//            if (backEnabled) {
//                backButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        onBackPressed();
//                    }
//                });
//            } else {
//                backButton.setVisibility(View.GONE);
//            }
//        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_1, R.anim.anim_2);
    }

//    public void getTopicList(final Runnable actionAfter) {
//        if (Util.isInternetAvailable(this)) {
//            String endpoint = ServiceEndpoint.GET_TOPIC_ENDPOINT.replace("{token}", mApp.getProfileToken());
//            Log.e("huunc", "get topic endpoint " + endpoint);
//            ServiceUtil.get(this, endpoint, new OnServiceResponseHandler() {
//                @Override
//                public void onSuccess(JSONObject response) {
//                    processTopicJson(response, actionAfter);
//                }
//
//                @Override
//                public void onFailure(int statusCode) {
//                    loadTopicFromCache(actionAfter);
//                }
//            });
//        } else {
//            loadTopicFromCache(actionAfter);
//        }
//    }

//    public void getTipTopicList(final Runnable actionAfter) {
//        if (Util.isInternetAvailable(BaseActivity.this.getApplicationContext())) {
//            String endpoint = ServiceEndpoint.GET_HEALTH_TIPS_TOPIC_ENDPOINT.replace("{token}", mApp.getProfileToken());
//            ServiceUtil.get(this, endpoint, new OnServiceResponseHandler() {
//                @Override
//                public void onSuccess(JSONObject response) {
//                    processTipTopicJson(response, actionAfter);
//                }
//
//                @Override
//                public void onFailure(int statusCode) {
//                    loadTipTopicFromCache(actionAfter);
//                }
//            });
//        } else {
//            loadTipTopicFromCache(actionAfter);
//        }
//    }

//    private void processTopicJson(JSONObject response, Runnable actionAfter) {
//        Log.e("huunc", response.toString());
//        List<Topic> topicList = JsonParser.convertToListTopic(response);
//        mApp.setTopicList(topicList);
//        try {
//            Reservoir.put(KEY_CACHE_TOPIC_LIST, response.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (actionAfter != null)
//            actionAfter.run();
//    }
//
//    private void processTipTopicJson(JSONObject response, Runnable actionAfter) {
//        Log.e("huunc", response.toString());
//        Type listType = new TypeToken<List<TipTopic>>(){}.getType();
//        try {
//            List<TipTopic> topicList = new Gson()
//                    .fromJson(response.getJSONObject("data").getJSONArray("topics").toString(), listType);
//            mApp.setTipTopicList(topicList);
//
//            Reservoir.put(KEY_CACHE_TIP_TOPIC_LIST, response.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (actionAfter != null)
//            actionAfter.run();
//    }

//    private void loadTopicFromCache(Runnable actionAfter) {
//        String topicJson = null;
//        try {
//            topicJson = Reservoir.get(KEY_CACHE_TOPIC_LIST, String.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (topicJson == null) {
//            new AlertDialog.Builder(BaseActivity.this)
//                    .setTitle("Không thể tải dữ liệu")
//                    .setMessage("Vui lòng kiểm tra lại kết nối mạng!")
//                    .setPositiveButton("Thử lại", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            recreate();
//                        }
//                    })
//                    .setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            finish();
//                        }
//                    })
//                    .show();
//        } else {
//            try {
//                processTopicJson(new JSONObject(topicJson), actionAfter);
//            } catch (JSONException e) {
//                e.printStackTrace();
//                ToastUtil.showMessage(BaseActivity.this, getResources().getString(R.string.no_internet));
//                finish();
//            }
//        }
//    }

//    private void loadTipTopicFromCache(Runnable actionAfter) {
//        String topicJson = null;
//        try {
//            topicJson = Reservoir.get(KEY_CACHE_TIP_TOPIC_LIST, String.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (topicJson == null) {
//            ToastUtil.showMessage(BaseActivity.this, getResources().getString(R.string.no_internet));
//            finish();
//        } else {
//            try {
//                processTopicJson(new JSONObject(topicJson), actionAfter);
//            } catch (JSONException e) {
//                e.printStackTrace();
//                ToastUtil.showMessage(BaseActivity.this, getResources().getString(R.string.no_internet));
//                finish();
//            }
//        }
//    }

    private ProgressDialog mLoadingDialog;

    public void showLoadingDialog(String message) {
        if (mLoadingDialog == null)
            mLoadingDialog = new ProgressDialog(this, R.style.DialogTheme);
        mLoadingDialog.setMessage(message);
        mLoadingDialog.setIndeterminate(true);
        mLoadingDialog.setCancelable(false);
        if (!isFinishing())
            mLoadingDialog.show();
    }

    public void hideLoadingDialog() {
        if (mLoadingDialog != null)
            mLoadingDialog.dismiss();
    }

    public void requestLoginAgain() {
        // TODO request login again
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        //AppEventsLogger.activateApp(this);
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
        //AppEventsLogger.deactivateApp(this);
    }

//    protected static ExecutorService threadPool = Executors.newFixedThreadPool(2);

    @Subscribe
    public void onEvent(NotificationMessage message) {
        if (!mApp.isUserLoggedIn())
            return;
        try {
            Log.e("huunc", "receive notification message " + message.getActor().getName() + " " + message.getAction().getContent());
//            final List<NotificationMessage> list = getNotificationMessageFromCache();
//            list.add(0, message);
//            if (list.size() > 50)
//                list.remove(list.size() - 1);
            showNotificationInApp(message);
//            Reservoir.put(NotificationFragment.KEY_CACHE_NOTIFICATION_MESSAGES, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private RelativeLayout notificationLayout;
    private static Animation slideInFromTop;
    private static Animation slideOutToTop;

    private void showNotificationInApp(final NotificationMessage message) {
        if (slideInFromTop == null)
            slideInFromTop = AnimationUtils.loadAnimation(this, R.anim.slide_in_from_top);
        if (slideOutToTop == null)
            slideOutToTop = AnimationUtils.loadAnimation(this, R.anim.slide_out_to_top);
        notificationLayout = (RelativeLayout) findViewById(R.id.layout_notification);
        if (notificationLayout != null) {
            if (notificationLayout.getVisibility() == View.VISIBLE) {
                notificationLayout.setVisibility(View.INVISIBLE);
                mHandler.removeMessages(HANDLE_SCHEDULE_HIDE_NOTIFICATION_LAYOUT);
            }
            ImageView avatarImage = (ImageView) notificationLayout.findViewById(R.id.image_avatar);
            TextView descText = (TextView) notificationLayout.findViewById(R.id.text_desc);
            ImageButton closeButton = (ImageButton) notificationLayout.findViewById(R.id.button_close);

            ImageUtil.loadImage(Util.getUserAvatarUrl(message.getActor().getUid()), avatarImage);
//            holder.mNameText.setText(item.getFrom().getName());
            String desc = message.getAction().getContent();
            String name = message.getActor().getName();

            slideInFromTop.setInterpolator(new AccelerateInterpolator());
            slideInFromTop.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    notificationLayout.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            slideOutToTop.setInterpolator(new AccelerateInterpolator());
            slideOutToTop.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    notificationLayout.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            descText.setText(Html.fromHtml("<b>" + name + "</b> " + desc));
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notificationLayout.startAnimation(slideOutToTop);
                }
            });
            notificationLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notificationLayout.setVisibility(View.INVISIBLE);
                    AppNavigation.processNotificationMessage(BaseActivity.this, message);
                }
            });
            notificationLayout.startAnimation(slideInFromTop);
            mHandler.sendEmptyMessageDelayed(HANDLE_SCHEDULE_HIDE_NOTIFICATION_LAYOUT, 5000);
        }

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLE_SCHEDULE_HIDE_NOTIFICATION_LAYOUT:
                    if (notificationLayout != null && slideOutToTop != null && notificationLayout.getVisibility() == View.VISIBLE)
                        notificationLayout.startAnimation(slideOutToTop);
            }
        }
    };

//    protected List<NotificationMessage> getNotificationMessageFromCache() {
//        Type resultType = new TypeToken<List<NotificationMessage>>() {
//        }.getType();
//        try {
//            List<NotificationMessage> list
//                    = Reservoir.get(NotificationFragment.KEY_CACHE_NOTIFICATION_MESSAGES, resultType);
//            if (list == null)
//                list = new ArrayList<>();
//            return list;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new ArrayList<>();
//    }

    public static final String SENDER_ID = "54131865871";
    private static GoogleCloudMessaging gcm;

    public void registerPushNotification(final BaseActivity context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    final String regid = gcm.register(SENDER_ID);
//                    msg = "Device registered, registration ID=" + regid;
                    PreferenceUtil.storeGCMRegistrationId(context, regid);
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            registerPush(context, regid);
                        }
                    });

                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    private void registerPush(BaseActivity context, String regid) {
        Log.e("huunc", regid);
        String deviceId = Util.getDeviceId(context);
        String bodyReg = "device_x_token=" + regid;
        Log.e("huunc", ServiceEndpoint.REGISTER_PUSH_ENDPOINT
                .replace("{device_id}", deviceId)
                .replace("{token}", mApp.getProfileToken()));
        ServiceUtil.sendPostRequestToServer(context,
                ServiceEndpoint.REGISTER_PUSH_ENDPOINT
                        .replace("{device_id}", deviceId)
                        .replace("{token}", mApp.getProfileToken()), bodyReg,
                "application/x-www-form-urlencoded", new OnServiceResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        Log.e("huunc", "register push success");
                    }

                    @Override
                    public void onFailure(int statusCode) {
                        Log.e("huunc", "register push fail");
                    }
                });
    }
}
