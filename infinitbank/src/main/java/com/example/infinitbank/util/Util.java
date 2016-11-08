package com.example.infinitbank.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.infinitbank.BaseActivity;
import com.example.infinitbank.MyApplication;
import com.example.infinitbank.R;
import com.example.infinitbank.service.ServiceEndpoint;
import com.example.infinitbank.service.ServiceUtil;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * Created by kylee on 29/10/2016.
 */

public class Util {
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public static File saveBitmapToFile(File file){
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE=1024;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 80 , outputStream);

            return file;
        } catch (Exception e) {
            return null;
        }
    }

    public static String diffTime(Date date) {
        long time = date.getTime();
        long now = new Date().getTime();
        long diff = (now - time) / 1000;
        int minutes = (int) diff / (60);
        String des = "";
        if (minutes <= 0) {
            des += " vừa xong";
        } else if (minutes < 60) {
            des += minutes + " phút trước";
        } else if (minutes < 1440) {
            des += (minutes / 60) + " giờ trước";
        } else
//        else {
//            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
//            des = format.format(date);
//        }
            if (minutes < 43200) {
                des += (minutes / 1440) + " ngày trước";
            } else if (minutes < 518400) {
                des += (minutes / 43200) + " tháng trước";
            } else {
                des += (minutes / 518400) + " năm trước";
            }

        return des;
    }


    public static String getUserAvatarUrl(String userId) {
        return ServiceEndpoint.BASE_IMAGE_ENDPOINT +  "/user/" + userId + "/avatar";
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    static Handler handler = new Handler();
    public static void temporaryDisableButton(final View button) {
        button.setEnabled(false);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                button.setEnabled(true);
            }
        }, 5000);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static void dimPopupMenu(Activity activity, PopupWindow mPopWindow) {
        View container;
        if (Build.VERSION.SDK_INT >= 23) {
            container = (View) mPopWindow.getContentView().getParent().getParent();
        } else {
            container = (View) mPopWindow.getContentView().getParent();
        }
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.7f;
        wm.updateViewLayout(container, p);
    }



    public static boolean isInternetAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static String refineHtml(String content, String title, String topic) {
        String output = "";

        content = content.replace("<h1>", "<b>");
        content = content.replace("<h2>", "<b>");
        content = content.replace("<h3>", "<b>");
        content = content.replace("</h1>", "</b>");
        content = content.replace("</h2>", "</b>");
        content = content.replace("</h3>", "</b>");
        content = content.replace("onclick", "id");
        content = content.replace("<iframe", "<iframe height='600'");
        content = content.replace("<video", "<video width='100%'");

        output = "<html style='background:#F7F7F7;text-align:left;margin:0 4px 0 4px;'>"
                + "<head><style>@font-face { font-family: 'RobotoCondensed-Regular';src: url('file:///android_asset/fonts/Roboto-Light_2.ttf');}"
                + "@font-face { font-family: 'RobotoCondensed-Bold';src: url('file:///android_asset/fonts/RobotoCondensed-Bold.ttf');}"
                + "h2 {font-family: 'RobotoCondensed-Bold' !important;  color: #141414;line-height:28px;text-align:left;}"
                + "blockquote {margin:0px;}"
                + "body {font-family: 'RobotoCondensed-Regular';color: #141414;line-height:28px}"
                + "img {width: 100% !important;}"
                + "a {font-family: 'RobotoCondensed-Regular' !important;color: #141414;line-height:28px, font-size:14px !important;text-decoration:none !important;}"
                + "tbody {font-family: 'RobotoCondensed-Regular' !important;color: #141414;line-height:28px}b:not(.mytitle) "
                + "{ font-family: 'RobotoCondensed-Bold' !important; line-height:28px}strong"
                + "{ font-family: 'RobotoCondensed-Bold' !important; line-height:28px}i "
                + "{ font-family: 'RobotoCondensed-Regular' !important;text-align:left;color: #141414; line-height:28px}</style>"
                + "</head><style></style><body>"
                + "<div class='mytitle' style='color:#333333;font-family:RobotoCondensed-Bold;font-size:26px;line-height:36px;text-align:left;margin: 4px 6px 8px 0px;'>" + title + "</div>"
                + "<div style='color:#999999;font-family:RobotoCondensed-Regular;font-size:14px;text-align:left;'>"
                + "<span style='color:#00a85a;'>" + topic + "</span></div>"
                + "<div style='text-align:left;"
                + "font-family:RobotoCondensed-Regular !important;line-height:28px' >" + content
                + "<p id=\"demo\"></p>\n" +
                "                \n" +
                "<script type=\"text/javascript\">\n" +
                "    var imgs = document.getElementsByTagName(\"img\");\n" +
                "    var i=0;\n" +
                "    for(i=0;i<imgs.length;i++){\n" +
                "        var x = imgs.item(i);\n" +
                "        x.addEventListener(\"click\", function showImageArticle(e){\n" +
                "            //document.getElementById(\"demo\").innerHTML = this.src;\n" +
                "            JSInterface.showImageArticle(this.src);\n" +
                "        });\n" +
                "    }\n" +
                "</script>\n"
                + "</body></html>";

        return output;
    }
}
