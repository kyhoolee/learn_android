package com.example.infinitbank;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;

import com.example.infinitbank.model.HistoryItem;
import com.example.infinitbank.service.OnServiceResponseHandler;
import com.example.infinitbank.service.ServiceEndpoint;
import com.example.infinitbank.service.ServiceUtil;
import com.example.infinitbank.util.Util;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kylee on 31/10/2016.
 */

public class TransactionActivity extends BaseActivity {
    @Bind(R.id.webview)
    WebView mWebView;
    @Bind(R.id.button_share)
    ImageButton mShareButton;

    private String itemId;
    private HistoryItem itemDetail;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);
        ButterKnife.bind(this);

        itemId = getIntent().getStringExtra("history_id");
        if (itemId == null)
            finish();

        setupToolbar(getIntent().getStringExtra("history_type"), true);

//        mShareButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.putExtra(Intent.EXTRA_TEXT, "http://abacsi.com.vn/tips/detail/" + itemId);
//                sendIntent.setType("text/plain");
//                startActivity(Intent.createChooser(sendIntent, "Chọn ứng dụng chia sẻ"));
//            }
//        });

        ServiceUtil.get(this, ServiceEndpoint.GET_HEALTH_TIP_DETAIL_ENDPOINT.replace("{tip_id}", itemId)
                .replace("{token}", mApp.getProfileToken()), new OnServiceResponseHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    itemDetail = new Gson().fromJson(response.getJSONObject("data").getJSONObject("transaction").toString(), HistoryItem.class);

                    showTipDetails();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode) {

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    private void showTipDetails() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        mWebView.getSettings().setSupportZoom(false);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setAppCachePath("");
        mWebView.getSettings().setAppCacheMaxSize(5 * 1024 * 1024);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);

//        try {
//            Method m = Class.forName("android.webkit.CacheManager").getDeclaredMethod("setCacheDisabled‌​", boolean.class);
//            m.setAccessible(true);
//            m.invoke(null, true);
//        } catch (Throwable e) {
//            Log.i("huunc", "Reflection failed", e);
//        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebView.setWebChromeClient(new WebChromeClient());

        //String contentHtml = Util.refineHtml(itemDetail.getContent(), itemDetail.getTitle(),itemDetail.getTopicName());

        //mWebView.loadDataWithBaseURL(null, contentHtml, "text/html", "utf-8", null);
    }
}
