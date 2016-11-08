package com.example.infinitbank.service;

import android.util.Log;

import com.example.infinitbank.BaseActivity;
import com.example.infinitbank.MyApplication;
import com.example.infinitbank.model.UserProfile;
import com.example.infinitbank.util.CacheUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by kylee on 29/10/2016.
 */

public class ServiceUtil {
    static AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);

    public static void get(final BaseActivity context, final String endpoint, final OnServiceResponseHandler handler) {
        client.get(context, endpoint, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    int code = 200;
                    if (response.has("code")) {
                        code = response.getInt("code");
                    }
                    if (code == 200)
                        handler.onSuccess(response);
                    else {
                        if (code == 5) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            refreshToken(context, new Runnable() {
                                @Override
                                public void run() {
                                    get(context, endpoint, handler);
                                }
                            });

                        } else {
                            handler.onFailure(statusCode);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                handler.onFailure(statusCode);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                handler.onFailure(statusCode);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                if (statusCode == 401) {
                    refreshToken(context, new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            get(context, endpoint, handler);
                        }
                    });
                } else if (statusCode == 400) {
                    int code = -1;
                    try {
                        if (errorResponse.getJSONObject("error").has("code")) {
                            code = errorResponse.getJSONObject("error").getInt("code");
                            if (code == 4) {
                                context.requestLoginAgain();
                            }
                        } else {
                            handler.onFailure(statusCode);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    handler.onFailure(statusCode);
                }
            }
        });
    }

    public static void sendPostRequestToServer(final BaseActivity context, final String endpoint, final String requestBody,
                                               final String contentType, final OnServiceResponseHandler handler) {
        try {
            StringEntity entity = new StringEntity(requestBody);
            client.post(context, endpoint, entity, contentType, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        int code = 200;
                        if (response.has("code")) {
                            code = response.getInt("code");
                        }
                        if (code == 200)
                            handler.onSuccess(response);
                        else {
                            if (code == 5) {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                refreshToken(context, new Runnable() {
                                    @Override
                                    public void run() {
                                        sendPostRequestToServer(context, endpoint, requestBody, contentType, handler);
                                    }
                                });

                            } else {
                                handler.onFailure(statusCode);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

//                @Override
//                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                    super.onFailure(statusCode, headers, responseString, throwable);
//                    handler.onFailure(statusCode);
//                }


                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    handler.onFailure(statusCode);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    handler.onFailure(statusCode);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    if (statusCode == 401) {
                        int code = 1000;
                        try {
                            if (errorResponse.getJSONObject("error").has("code")) {
                                code = errorResponse.getJSONObject("error").getInt("code");
                                if (code == 1001) {
                                    handler.onFailure(statusCode);
                                    return;
                                }
                            }
                            refreshToken(context, new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    sendPostRequestToServer(context, endpoint, requestBody, contentType, handler);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else if (statusCode == 400) {
                        int code = -1;
                        try {
                            if (errorResponse.getJSONObject("error").has("code")) {
                                code = errorResponse.getJSONObject("error").getInt("code");
                                if (code == 4) {
                                    context.requestLoginAgain();
                                }
                            } else {
                                handler.onFailure(statusCode);
                            }
                        } catch (JSONException e) {
                            handler.onFailure(statusCode);
                            e.printStackTrace();
                        }
                    } else if (statusCode == 409) {
                        handler.onSuccess(errorResponse);
                    } else {
                        handler.onFailure(statusCode);
                    }
                }

//                @Override
//                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
//                    super.onFailure(statusCode, headers, throwable, errorResponse);
//                    handler.onFailure(statusCode);
//                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void sendDeleteRequestToServer(final BaseActivity context, final String endpoint, final String requestBody,
                                                 final String contentType, final OnServiceResponseHandler handler) {
        try {
            StringEntity entity = new StringEntity(requestBody);
            client.delete(context, endpoint, entity, contentType, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        int code = 200;
                        if (response.has("code")) {
                            code = response.getInt("code");
                        }
                        if (code == 200)
                            handler.onSuccess(response);
                        else {
                            if (code == 5) {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                refreshToken(context, new Runnable() {
                                    @Override
                                    public void run() {
                                        sendDeleteRequestToServer(context, endpoint, requestBody, contentType, handler);
                                    }
                                });

                            } else {
                                handler.onFailure(statusCode);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    handler.onFailure(statusCode);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    handler.onFailure(statusCode);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    if (statusCode == 401) {
                        refreshToken(context, new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                sendDeleteRequestToServer(context, endpoint, requestBody, contentType, handler);
                            }
                        });
                    } else if (statusCode == 400) {
                        int code = -1;
                        try {
                            if (errorResponse.getJSONObject("error").has("code")) {

                                code = errorResponse.getJSONObject("error").getInt("code");
                                if (code == 4) {
                                    context.requestLoginAgain();
                                }

                            } else {
                                handler.onFailure(statusCode);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        handler.onFailure(statusCode);
                    }
                }

            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void sendDeleteRequestToServer(BaseActivity context, String endpoint, String requestBody, OnServiceResponseHandler handler) {
        sendDeleteRequestToServer(context, endpoint, requestBody, "application/json", handler);
    }

    public static void sendPostRequestToServer(BaseActivity context, String endpoint, String requestBody, OnServiceResponseHandler handler) {
        sendPostRequestToServer(context, endpoint, requestBody, "application/json", handler);
    }

    private static void refreshToken(final BaseActivity context, final Runnable runAfter) {
        if (((MyApplication) context.getApplication()).getMyProfile() == null)
            return;
        String token = ((MyApplication) context.getApplication()).getMyProfile().getRefreshToken();
        String requestBody = "grant_type=refresh_token&client_id=" + ServiceEndpoint.APP_ID
                + "&client_secret=" + ServiceEndpoint.APP_SECRET + "&refresh_token=" + token;
        Log.e("huunc", requestBody);
        try {
            StringEntity entity = new StringEntity(requestBody);
            client.post(context, ServiceEndpoint.AUTH_ACCOUNT_ENDPOINT,
                    entity, "application/x-www-form-urlencoded", new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            try {
                                String userId = response.getString("user_id");
                                String accessToken = response.getString("access_token");
                                String refreshToken = response.getString("refresh_token");

                                UserProfile myProfile = new UserProfile(userId, accessToken, refreshToken);
                                ((MyApplication) context.getApplication()).setMyProfile(myProfile);
                                CacheUtil.cacheUserProfile(context);

                                runAfter.run();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }


                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                        }
                    });

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
