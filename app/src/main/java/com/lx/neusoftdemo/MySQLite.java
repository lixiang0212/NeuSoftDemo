package com.lx.neusoftdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lixiang on 2017/2/27.
 */

public class MySQLite extends SQLiteOpenHelper {

    public MySQLite(Context context) {
        super(context, "user.db", null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user(id Integer primary key autoincrement,name Integer,password varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("alter table user add account varchar(20)");
    }
}
