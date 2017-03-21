package com.lx.neusoftdemo;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lixiang on 2017/2/27.
 */

public class LocalUser {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private Context context;
    public LocalUser(Context context) {
        this.context = context;
        sp = context.getSharedPreferences("user",Context.MODE_PRIVATE);
    }

    public  void saveUser(User user){
        if (sp==null){
            sp = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        }
        editor = sp.edit();
        editor.putInt("name",user.getName());
        editor.putString("password",user.getPassword());
        editor.commit();
    }
    public  User getUser(){
        if (sp==null){
            sp = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        }
        int name = sp.getInt("name",0);
        String password = sp.getString("password","123456");
        return new User(name,password);
    }

}
