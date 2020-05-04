package com.ama.lax.repositories;

import android.app.Activity;
import android.content.SharedPreferences;

import com.ama.lax.models.User;

public class LoginData {

    public static void saveData(Activity context, User user){
        SharedPreferences pref = context.getSharedPreferences("auth", 0);
        SharedPreferences.Editor editor = pref.edit();
        if (user != null){
            editor.putString("id", user.getId());
            editor.putString("user", user.getUser());
            editor.putString("email", user.getEmail());
            editor.putString("phone", user.getPhone());
            editor.putString("img", user.getImg());
            editor.putString("id_num", user.getId_num());
            editor.putString("birth_date", user.getBirth_date());
            editor.putString("gender", user.getGender());
            editor.putString("height", user.getHeight());
            editor.putString("weight", user.getWeight());
            editor.putString("disease", user.getDisease());
            editor.putString("score", user.getScore());
            editor.commit();
        }else {
            editor.clear();
            editor.commit();
        }

    }


    public static User getData(Activity context){
        SharedPreferences pref = context.getSharedPreferences("auth", 0);
        User user = null;
        if (pref.getString("user", null) != null){
            user = new User(
                    pref.getString("id", null),
                    pref.getString("user", null),
                    pref.getString("email", null),
                    pref.getString("phone", null),
                    pref.getString("img", null),
                    pref.getString("id_num", null),
                    pref.getString("birth_date", null),
                    pref.getString("gender", null),
                    pref.getString("height", null),
                    pref.getString("weight", null),
                    pref.getString("disease", null),
                    pref.getString("score", null)
            );
        }
        return user;
    }

    public static void saveSkipLogin(Activity context, boolean b){
        SharedPreferences pref = context.getSharedPreferences("auth", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isSkipped", b);
        editor.commit();
    }

    public static boolean getIsSkippedLogin(Activity context){
        SharedPreferences pref = context.getSharedPreferences("auth", 0);
        return pref.getBoolean("isSkipped", false);
    }

}