package com.lx.neusoftdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixiang on 2017/2/27.
 */

public class SqlUtils {
    private MySQLite mySQLite;
    private Context context;

    public SqlUtils(Context context) {
        this.context = context;
        mySQLite = new MySQLite(context);
    }
   //添加一条用户信息
    public void addUser(User user){
        SQLiteDatabase db = mySQLite.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",user.getName());
        values.put("password",user.getPassword());
        db.insert("user",null,values);
        db.close();
        Log.i("AAA","addUser");
    }
    //更新用户信息
    public void updateUser(User user){
        SQLiteDatabase db = mySQLite.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",user.getName());
        values.put("password",user.getPassword());
        db.update("user",values,"name=?",new String[]{user.getName()+""});
        db.close();
        Log.i("AAA","updateUser");
    }
    public boolean login(User user){

        SQLiteDatabase db =mySQLite.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user where name=?",new String[]{user.getName()+""});
        if (cursor.moveToFirst()) {
            int name = cursor.getInt(cursor.getColumnIndex("name"));
            String pd = cursor.getString(cursor.getColumnIndex("password"));
            if(name==user.getName()&&pd.equals(user.getPassword())){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }
    //查询所有用户信息
    public List<User> queryAll(){

        SQLiteDatabase db =mySQLite.getWritableDatabase();
        Cursor cursor = db.query("user",null,null,null,null,null,"id ASC");//DESC逆序
        List<User> list = new ArrayList<User>();
        while (cursor.moveToNext()){
            int name = cursor.getInt(cursor.getColumnIndex("name"));
            String password= cursor.getString(cursor.getColumnIndex("password"));
            list.add(new User(name,password));
        }
        cursor.close();
        db.close();
        Log.i("AAA","queryUser");
        return list;
    }


}
