package com.example.infinitbank.util;

import android.content.Context;
import android.widget.Toast;

import com.example.infinitbank.R;

/**
 * Created by kylee on 29/10/2016.
 */

public class ToastUtil {
    public static void showError(Context context) {
        Toast.makeText(context, context.getResources().getString(R.string.toast_error), Toast.LENGTH_SHORT).show();
    }

    public static void showMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showNoInternetConnection(Context context) {
        showMessage(context, context.getResources().getString(R.string.no_internet_connection));
    }
}
