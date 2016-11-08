package com.example.infinitbank.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.infinitbank.BaseActivity;
import com.example.infinitbank.MyApplication;
import com.example.infinitbank.model.NotificationMessage;
import com.example.infinitbank.util.CanScrollVerticallyCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by kylee on 01/11/2016.
 */

public class BaseFragment extends Fragment implements CanScrollVerticallyCallback {
    protected View mRootView;
    protected BaseActivity mActivity;
    protected MyApplication mApp;

    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = (BaseActivity) getActivity();
        if(mActivity == null)
            return;
        mApp = (MyApplication) mActivity.getApplication();
    }

    public void showProgressDialog(String message) {
        if(mProgressDialog == null)
            mProgressDialog = new ProgressDialog(mActivity);

        mProgressDialog.setMessage(message);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if(mProgressDialog != null)
            mProgressDialog.hide();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);

    }
    public void scrollToTop() {

    }

    @Subscribe
    public void onEvent(NotificationMessage message) {
//        Log.e("huunc", "aaaaaaaaaaaaaaaaaa " + message.getContent());
    }


    @Override
    public boolean canScrollVertically(int direction) {
        return false;
    }
}
