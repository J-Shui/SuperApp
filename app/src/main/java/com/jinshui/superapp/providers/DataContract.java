package com.jinshui.superapp.providers;

/**
 * Author : J-Shui[YJS]
 * DATE : 2015/10/31
 * email : jshui_920124@163.com
 * package_name : com.jinshui.superapp.providers
 * project : SuperApp
 */

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * 数据库以及ContentProvider中，约定的字段名，表名，以及类的定义
 */
public final class DataContract {
    private DataContract(){

    }

    /**
     * 历史表的名称
     */
    public static final String TABLE_HISTORY = "histories";

    /**
     * 使用静态类的形式来代表一张表，以及表的字段，Uri
     * 实现BaseColumns目的是 使用_id的字段
     */
    public static class History implements BaseColumns{

        public static final String AUTHORITY = "com.jinshui.superapp.providers.BrowserProvider";

        /**
         * 当以特定的字段名称
         */
        public static final String URL = "url";

        /**
         * History这张表的CONTENT_URI
         * content://com.../history/history
         */
        public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY)
                .buildUpon().appendPath("history").build();


    }
}
