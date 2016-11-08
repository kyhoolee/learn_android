package com.example.infinitbank.util;

import android.app.Activity;
import android.content.Intent;

import com.example.infinitbank.MyApplication;
import com.example.infinitbank.PreLoginActivity;
import com.example.infinitbank.R;
import com.example.infinitbank.TransactionActivity;
import com.example.infinitbank.model.HistoryItem;
import com.example.infinitbank.model.NotificationMessage;

/**
 * Created by kylee on 29/10/2016.
 */

public class AppNavigation {

//    public static void askDoctor(Activity context, String bsId, String bsName, String topicId) {
//        Intent intent = new Intent(context, CreatePostActivity.class);
//        intent.putExtra("bac_si_id", bsId);
//        intent.putExtra("bac_si", bsName);
//        intent.putExtra("topic_id", topicId);
//        context.startActivity(intent);
//    }
//
//    public static void viewPostDetail(Activity context, Post post) {
//        MyApplication app = (MyApplication) context.getApplication();
//        Intent intent;
//        if (!app.getProfileId().equals(post.getFrom().getId())) {
//            intent = new Intent(context, PostDetail2Activity.class);
//        } else
//            intent = new Intent(context, PostDetailActivity.class);
//        intent.putExtra("post", post);
//        context.startActivity(intent);
//    }
//
//    public static void viewPostDetailFromNotification(Activity context, Post post) {
//        MyApplication app = (MyApplication) context.getApplication();
//        Intent intent;
//        if (!app.getProfileId().equals(post.getFrom().getId())) {
//            intent = new Intent(context, PostDetail2Activity.class);
//        } else
//            intent = new Intent(context, PostDetailActivity.class);
//        intent.putExtra("post", post);
//        intent.putExtra("from_notification", true);
//        context.startActivity(intent);
//    }
//
//    public static void viewPostDetail(Activity context, Post post, boolean focusComment) {
//        MyApplication app = (MyApplication) context.getApplication();
//        Intent intent;
//        if (!app.getProfileId().equals(post.getFrom().getId())) {
//            intent = new Intent(context, PostDetail2Activity.class);
//        } else
//            intent = new Intent(context, PostDetailActivity.class);
//        intent.putExtra("post", post);
//        intent.putExtra("focus_comment", focusComment);
//        context.startActivity(intent);
//    }
//
//    public static void viewImage(Activity context, String url) {
//        Intent intent = new Intent(context, ViewImageActivity.class);
//        intent.putExtra("url", url);
//        context.startActivity(intent);
//    }
//
//    public static void viewTopicDetail(Activity context, Topic topic) {
//        Intent intent = new Intent(context, TopicDetailActivity.class);
//        intent.putExtra("topic", topic);
//        context.startActivity(intent);
//    }
//
    public static void viewHistoryDetail(Activity context, HistoryItem item) {
        Intent intent = new Intent(context, TransactionActivity.class);
        intent.putExtra("history_id", item.id);
        intent.putExtra("history_type", item.type);
        context.startActivity(intent);
    }
//
//    public static void viewGuiderDetail(Activity context, TopGuider guider, String topicId) {
//        Intent intent = new Intent(context, GuiderDetailActivity.class);
//        intent.putExtra("guider", guider);
//        intent.putExtra("topic_id", topicId);
//        context.startActivity(intent);
//    }
//
//    public static void processNotificationMessage(Activity activity, NotificationMessage message) {
//        String objectType = message.getObject().getType();
//        switch (objectType) {
//            case "post":
//                Post post = new Post();
//                post.setId(message.getObject().getId());
//                From from = new From();
//                from.setId(message.getObject().getOwner());
//                post.setFrom(from);
//                viewPostDetail(activity, post);
//                break;
//            case "reply":
//            case "comment":
//                String commentId = message.getObject().getId();
//                Intent intent = new Intent(activity, ReplyCommentActivity.class);
//                intent.putExtra("comment_id", commentId);
//                intent.putExtra("enable_back_to_post", true);
//                activity.startActivity(intent);
//                break;
//        }
//    }

    public static void showLoginActivity(Activity activity) {
        activity.startActivityForResult(new Intent(activity, PreLoginActivity.class), 6);
        activity.overridePendingTransition(R.anim.slide_in_from_bottom, android.R.anim.fade_out);
    }

    public static void processNotificationMessage(Activity activity, NotificationMessage message) {
        String objectType = message.getObject().getType();
        switch (objectType) {
            case "post":
//                Post post = new Post();
//                post.setId(message.getObject().getId());
//                From from = new From();
//                from.setId(message.getObject().getOwner());
//                post.setFrom(from);
//                viewPostDetail(activity, post);
                break;
            case "reply":
            case "comment":
//                String commentId = message.getObject().getId();
//                Intent intent = new Intent(activity, ReplyCommentActivity.class);
//                intent.putExtra("comment_id", commentId);
//                intent.putExtra("enable_back_to_post", true);
//                activity.startActivity(intent);
                break;
        }
    }
}
