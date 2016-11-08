package com.example.infinitbank.service;

import org.json.JSONObject;

/**
 * Created by kylee on 29/10/2016.
 */

public interface OnServiceResponseHandler {
    void onSuccess(JSONObject response);
    void onFailure(int statusCode);
}
