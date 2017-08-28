package com.xiongyuan.lottery;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.xiongyuan.lottery.mypage.bean.User;

/**
 * 对用户信息本地保存
 */
public class CachePreferences {

    private static final String NAME = CachePreferences.class.getSimpleName();
    private static final String KEY_USER_ID = "userID";
    private static final String KEY_USER_URL = "userURL";
    private static final String KEY_USER_NAME = "userNAME";
    private static final String KEY_USER_PWD = "userPWD";
    private static final String KEY_USER_SF = "userSF";

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    private CachePreferences() {
    }

    @SuppressLint("CommitPrefEdits")
    public static void init(Context context) {
        preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public static void clearAllData() {
        editor.clear();
        editor.apply();
    }

    public static void setUser(User user) {
        editor.putString(KEY_USER_ID, user.getUser_id());
        editor.putString(KEY_USER_URL,user.getUser_url());
        editor.putString(KEY_USER_NAME,user.getUser_name());
        editor.putString(KEY_USER_PWD,user.getUser_password());
        editor.putString(KEY_USER_SF,user.isIfji());
        editor.apply();
    }

    public static User getUser() {
        User user = new User();
        user.setUser_id(preferences.getString(KEY_USER_ID, null));
        user.setUser_url(preferences.getString(KEY_USER_URL,null));
        user.setUser_name(preferences.getString(KEY_USER_NAME,null));
        user.setUser_password(preferences.getString(KEY_USER_PWD,null));
        user.setIfji(preferences.getString(KEY_USER_SF,null));
        return user;
    }

}
