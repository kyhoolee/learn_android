package com.example.infinitbank.service;

/**
 * Created by kylee on 29/10/2016.
 */

public class ServiceEndpoint {
    private static boolean IS_PRODUCTION = true;
    public static String APP_ID = "quiz_platform";
    public static String APP_SECRET = "3cab0d7fde3ad207f7701739a4e901bf";

    public static String BASE_ACCOUNT_ENDPOINT = "https://id.anyquiz.info";
    public static String BASE_IMAGE_ENDPOINT = "http://id.anyquiz.info";

    public static String AUTH_ACCOUNT_ENDPOINT = BASE_ACCOUNT_ENDPOINT + "/oauth/token";
    public static String GET_ACCOUNT_PROFILE = BASE_ACCOUNT_ENDPOINT + "/user/{user_id}?access_token={access_token}";
    public static String SIGN_UP_PHONE_ENDPOINT = BASE_ACCOUNT_ENDPOINT + "/signup/phone";
    public static String VERIFY_PHONE_ENDPOINT = BASE_ACCOUNT_ENDPOINT + "/signup/phone/verify";
    public static String RESET_PASSWORD_ENDPOINT = BASE_ACCOUNT_ENDPOINT + "/reset_password/phone";
    public static String VERIFY_RESET_PASSWORD_ENDPOINT = BASE_ACCOUNT_ENDPOINT + "/reset_password/phone/verify";
    public static String INIT_PROFILE_ENDPOINT = BASE_ACCOUNT_ENDPOINT + "/user/setup?access_token={token}";
    public static String LOGIN_ENDPOINT = BASE_ACCOUNT_ENDPOINT + "/signin";
    public static String UPDATE_PROFILE_ENDPOINT = BASE_ACCOUNT_ENDPOINT + "/user/me?access_token={token}";

    private static String BASE_PUSH_ENDPOINT = "http://api.anyquiz.info/push/v0";
    public static String REGISTER_PUSH_ENDPOINT = BASE_PUSH_ENDPOINT + "/app/" + APP_ID + "/os/android/device/{device_id}?access_token={token}";
    public static String UNREGISTER_PUSH_ENDPOINT = BASE_PUSH_ENDPOINT + "/app/" + APP_ID + "/os/android/device/{device_id}";

    private static String BASE_SERVICE_ENDPOINT = "http://api.anyquiz.info/v0";
    private static String BASE_SOCIAL_ENDPOINT = "http://api.anyquiz.info/social/v0";
    public static String GET_NOTIFICATION_HISTORY = BASE_SERVICE_ENDPOINT + "/notify/history/get?token={token}&app=abacsi&dir=old&last_ts={last_ts}";
    public static String MARK_READ_NOTIFICATION = BASE_SERVICE_ENDPOINT + "/notify/history/mark?token={token}&app=abacsi";
    public static String GET_TOPIC_ENDPOINT = BASE_SERVICE_ENDPOINT + "/topic/asker/top?token={token}&category=ichnhan";
    public static String REQUEST_UPLOAD_ENDPOINT = BASE_SERVICE_ENDPOINT + "/request/upload?token={token}";
    public static String CREATE_POST_ENDPOINT = BASE_SOCIAL_ENDPOINT + "/user/{user_id}/feeds?access_token={token}";
    public static String GET_USER_FEEDS_ENDPOINT = CREATE_POST_ENDPOINT + "&page={page}&limit=20";
    public static String GET_FOLLOW_FEEDS_ENDPOINT = BASE_SOCIAL_ENDPOINT + "/follow_feeds?access_token={token}" + "&page={page}&limit=20";
    public static String GET_LIVE_FEEDS_ENDPOINT = BASE_SOCIAL_ENDPOINT + "/feeds?access_token={token}&page={page}&limit=20";
    public static String VOTE_POST_ENDPOINT = BASE_SOCIAL_ENDPOINT + "/post/{post_id}/vote?access_token={token}";
    public static String FOLLOW_POST_ENDPOINT = BASE_SOCIAL_ENDPOINT + "/post/{post_id}/follow?access_token={token}";
    public static String COMMENT_POST_ENDPOINT = BASE_SOCIAL_ENDPOINT + "/post/{post_id}/comments?access_token={token}";
    public static String GET_COMMEND_ENDPOINT = BASE_SOCIAL_ENDPOINT + "/comment/{comment_id}?access_token={token}";
    public static String EDIT_COMMENT_ENDPOINT = BASE_SOCIAL_ENDPOINT + "/post/{post_id}/comment/{comment_id}?access_token={token}";
    public static String REPLY_COMMENT_ENDPOINT = BASE_SOCIAL_ENDPOINT + "/post/{post_id}/comment/{comment_id}/replies?access_token={token}";
    public static String VOTE_COMMENT_ENDPOINT = BASE_SOCIAL_ENDPOINT + "/post/{post_id}/comment/{comment_id}/vote?access_token={token}";
    public static String GET_TOPIC_FEEDS_ENDPOINT = BASE_SOCIAL_ENDPOINT + "/topic/{topic_id}/feeds?access_token={token}&page={page}&limit=20";
    public static String GET_GUIDER_BY_TOPIC_ENDPOINT = BASE_SERVICE_ENDPOINT + "/topic/asker/list?token={token}&id={topic_id}";
    public static String GET_GUIDER_INFO_ENDPOINT = BASE_SOCIAL_ENDPOINT + "/expert/{user_id}?access_token={token}";
    public static String SEARCH_ENDPOINT = BASE_SOCIAL_ENDPOINT + "/search/posts?q.text=";
    public static String GET_POST_INFO_ENDPOINT = BASE_SOCIAL_ENDPOINT + "/post/{post_id}?access_token={token}";
    public static String GET_GUIDER_ACTIVITY_ENDPOINT = BASE_SOCIAL_ENDPOINT + "/expert/{expert_id}/activity?access_token={token}&page={page}&limit=20";

    public static String GET_PROMOTION_CAMPAIGN = BASE_SERVICE_ENDPOINT + "/promotion/campaign?token={token}";
    public static String INPUT_REF_CODE_ENDPOINT = BASE_SERVICE_ENDPOINT + "/promotion/set?token={token}&code={code}";

    public static String GET_HEALTH_TIPS_ENDPOINT = BASE_SERVICE_ENDPOINT + "/tip/list?token={token}&start={start}&limit=20";
    public static String GET_HEALTH_TIPS_BY_TOPIC_ENDPOINT = BASE_SERVICE_ENDPOINT + "/tip/list?token={token}&topic={topic_id}&start={start}&limit=20";
    public static String GET_HEALTH_TIPS_TOPIC_ENDPOINT = BASE_SERVICE_ENDPOINT + "/tip/get_topic?token={token}&category=health_tip";
    public static String GET_HEALTH_TIP_DETAIL_ENDPOINT = BASE_SERVICE_ENDPOINT + "/tip/get?token={token}&tip_id={tip_id}";
    public static String SUBSCRIBE_TIP_TOPIC_ENDPOINT = BASE_SERVICE_ENDPOINT + "/tip/subscribe?token={token}";
    public static String GET_APP_CONFIG = BASE_ACCOUNT_ENDPOINT + "/app/" + APP_ID + "/v{version}_android/config";
    public static String GET_MEDICAL_RECORDS = BASE_SOCIAL_ENDPOINT + "/medical_records?access_token={token}";
    public static String UPDATE_MEDICAL_RECORDS = BASE_SOCIAL_ENDPOINT + "/medical_record/{record_id}?access_token={token}";
    public static String DELETE_MEDICAL_RECORDS = BASE_SOCIAL_ENDPOINT + "/medical_record/{record_id}?access_token={token}";

    public static void init() {
        if (IS_PRODUCTION) {
            BASE_ACCOUNT_ENDPOINT = "http://api.abacsi.com.vn/auth/v0";
            BASE_IMAGE_ENDPOINT = BASE_ACCOUNT_ENDPOINT;
            BASE_PUSH_ENDPOINT = "http://api.abacsi.com.vn/push/v0";
            BASE_SERVICE_ENDPOINT = "http://api.abacsi.com.vn/v0";
            BASE_SOCIAL_ENDPOINT = "http://api.abacsi.com.vn/social/v0";

            AUTH_ACCOUNT_ENDPOINT = BASE_ACCOUNT_ENDPOINT + "/oauth/token";
            GET_ACCOUNT_PROFILE = BASE_ACCOUNT_ENDPOINT + "/user/{user_id}?access_token={access_token}";

            SIGN_UP_PHONE_ENDPOINT = BASE_ACCOUNT_ENDPOINT + "/signup/phone";
            VERIFY_PHONE_ENDPOINT = BASE_ACCOUNT_ENDPOINT + "/signup/phone/verify";
            RESET_PASSWORD_ENDPOINT = BASE_ACCOUNT_ENDPOINT + "/reset_password/phone";
            VERIFY_RESET_PASSWORD_ENDPOINT = BASE_ACCOUNT_ENDPOINT + "/reset_password/phone/verify";
            INIT_PROFILE_ENDPOINT = BASE_ACCOUNT_ENDPOINT + "/user/setup?access_token={token}";
            LOGIN_ENDPOINT = BASE_ACCOUNT_ENDPOINT + "/signin";
            UPDATE_PROFILE_ENDPOINT = BASE_ACCOUNT_ENDPOINT + "/user/me?access_token={token}";

            REGISTER_PUSH_ENDPOINT = BASE_PUSH_ENDPOINT + "/app/" + APP_ID + "/os/android/device/{device_id}?access_token={token}";
            UNREGISTER_PUSH_ENDPOINT = BASE_PUSH_ENDPOINT + "/app/" + APP_ID + "/os/android/device/{device_id}";

            GET_NOTIFICATION_HISTORY = BASE_SERVICE_ENDPOINT + "/notify/history/get?token={token}&app=abacsi&dir=old&last_ts={last_ts}";
            MARK_READ_NOTIFICATION = BASE_SERVICE_ENDPOINT + "/notify/history/mark?token={token}&app=abacsi";
            GET_TOPIC_ENDPOINT = BASE_SERVICE_ENDPOINT + "/topic/asker/top?token={token}&category=ichnhan";
            REQUEST_UPLOAD_ENDPOINT = BASE_SERVICE_ENDPOINT + "/request/upload?token={token}";
            CREATE_POST_ENDPOINT = BASE_SOCIAL_ENDPOINT + "/user/{user_id}/feeds?access_token={token}";
            GET_USER_FEEDS_ENDPOINT = CREATE_POST_ENDPOINT + "&page={page}&limit=20";
            GET_FOLLOW_FEEDS_ENDPOINT = BASE_SOCIAL_ENDPOINT + "/follow_feeds?access_token={token}" + "&page={page}&limit=20";
            GET_LIVE_FEEDS_ENDPOINT = BASE_SOCIAL_ENDPOINT + "/feeds?access_token={token}&page={page}&limit=20";
            VOTE_POST_ENDPOINT = BASE_SOCIAL_ENDPOINT + "/post/{post_id}/vote?access_token={token}";
            FOLLOW_POST_ENDPOINT = BASE_SOCIAL_ENDPOINT + "/post/{post_id}/follow?access_token={token}";
            COMMENT_POST_ENDPOINT = BASE_SOCIAL_ENDPOINT + "/post/{post_id}/comments?access_token={token}";
            GET_COMMEND_ENDPOINT = BASE_SOCIAL_ENDPOINT + "/comment/{comment_id}?access_token={token}";
            EDIT_COMMENT_ENDPOINT = BASE_SOCIAL_ENDPOINT + "/post/{post_id}/comment/{comment_id}?access_token={token}";
            REPLY_COMMENT_ENDPOINT = BASE_SOCIAL_ENDPOINT + "/post/{post_id}/comment/{comment_id}/replies?access_token={token}";
            VOTE_COMMENT_ENDPOINT = BASE_SOCIAL_ENDPOINT + "/post/{post_id}/comment/{comment_id}/vote?access_token={token}";
            GET_TOPIC_FEEDS_ENDPOINT = BASE_SOCIAL_ENDPOINT + "/topic/{topic_id}/feeds?access_token={token}&page={page}&limit=20";
            GET_GUIDER_BY_TOPIC_ENDPOINT = BASE_SERVICE_ENDPOINT + "/topic/asker/list?token={token}&id={topic_id}";
            GET_GUIDER_INFO_ENDPOINT = BASE_SOCIAL_ENDPOINT + "/expert/{user_id}?access_token={token}";
            SEARCH_ENDPOINT = BASE_SOCIAL_ENDPOINT + "/search/posts?q.text=";
            GET_POST_INFO_ENDPOINT = BASE_SOCIAL_ENDPOINT + "/post/{post_id}?access_token={token}";
            GET_GUIDER_ACTIVITY_ENDPOINT = BASE_SOCIAL_ENDPOINT + "/expert/{expert_id}/activity?access_token={token}&page={page}&limit=20";

            GET_PROMOTION_CAMPAIGN = BASE_SERVICE_ENDPOINT + "/promotion/campaign?token={token}";
            INPUT_REF_CODE_ENDPOINT = BASE_SERVICE_ENDPOINT + "/promotion/set?token={token}&code={code}";
            GET_HEALTH_TIPS_ENDPOINT = BASE_SERVICE_ENDPOINT + "/tip/list?token={token}&start={start}&limit=20";
            GET_HEALTH_TIPS_TOPIC_ENDPOINT = BASE_SERVICE_ENDPOINT + "/tip/get_topic?token={token}&category=health_tip";
            GET_HEALTH_TIP_DETAIL_ENDPOINT = BASE_SERVICE_ENDPOINT + "/tip/get?token={token}&tip_id={tip_id}";
            SUBSCRIBE_TIP_TOPIC_ENDPOINT = BASE_SERVICE_ENDPOINT + "/tip/subscribe?token={token}";
            GET_HEALTH_TIPS_BY_TOPIC_ENDPOINT = BASE_SERVICE_ENDPOINT + "/tip/list?token={token}&topic={topic_id}&start={start}&limit=20";
            GET_APP_CONFIG = BASE_ACCOUNT_ENDPOINT + "/app/" + APP_ID + "/v{version}_android/config";
            GET_MEDICAL_RECORDS = BASE_SOCIAL_ENDPOINT + "/medical_records?access_token={token}";
            UPDATE_MEDICAL_RECORDS = BASE_SOCIAL_ENDPOINT + "/medical_record/{record_id}?access_token={token}";
            DELETE_MEDICAL_RECORDS = BASE_SOCIAL_ENDPOINT + "/medical_record/{record_id}?access_token={token}";
        }
    }
}