package com.jinshui.superapp.providers;

/**
 * Author : J-Shui[YJS]
 * DATE : 2015/10/31
 * email : jshui_920124@163.com
 * package_name : com.jinshui.superapp.database
 * project : SuperApp
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库工具
 */
public class DBHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "app.db";
    private static final int DB_VERSION = 1;
    private static final String CREATE_TABLE_HOSTORY =
            "create table " +
                    DataContract.TABLE_HISTORY +
                    "(" +
                    DataContract.History._ID + " integer primary key autoincrement," +
                    DataContract.History.URL + " text not null" +
                    ")";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_HOSTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
