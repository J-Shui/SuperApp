package com.jinshui.superapp.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * 浏览器相关的数据提供者
 */
public class BrowserProvider extends ContentProvider {
    private DBHelper dbHelper;

    private static final UriMatcher match;
    private static final int CODE_HISTORY = 1;

    static {
        match = new UriMatcher(UriMatcher.NO_MATCH);

        //添加匹配规则
        //1.参数1，代表匹配的authority,指定的authority，如果有多个authority，也只能够匹配指定的那一个

        match.addURI("*","history",CODE_HISTORY);//content://authority//histroy
    }

    public BrowserProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int ret = 0;

        int code = BrowserProvider.match.match(uri);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (code){
            case CODE_HISTORY:
                ret = db.delete(DataContract.TABLE_HISTORY,selection,selectionArgs);
        }
        return ret;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    /**
     * 初始化ContentProvider,创建DBHelper
     * @return true 创建成功，false 创建失败，不允许使用
     */
    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        dbHelper = new DBHelper(getContext());
        return true;
    }

    /**
     * 根据Uri来添加不同的数据，ContentValues包含数据内容
     * @param uri
     * @param values
     * @return Uri 代表新添加的那一条记录的访问Uri
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri ret = null;

        if (uri != null) {
            if (values == null) {
                values = new ContentValues();
            }
            //TODO 匹配Uri，到底是处理哪一张表
            int code = BrowserProvider.match.match(uri);

            SQLiteDatabase db = dbHelper.getWritableDatabase();

            switch (code){
                case CODE_HISTORY://代表添加历史记录
                    long rid = db.insert(DataContract.TABLE_HISTORY, "url", values);

                    if (rid > -1){
                        //TODO 添加成功
                        //定义：content://xxx//history/id代表新添加的记录
//                        ret = Uri.withAppendedPath(uri,Long.toString(rid));
                        ret = ContentUris.withAppendedId(uri,rid);
                    }

                    break;
            }
            db.close();
        }

        return  ret;
    }

    /**
     * 查询
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor ret = null;

        if (uri != null) {

            int code = BrowserProvider.match.match(uri);

            SQLiteDatabase db = dbHelper.getReadableDatabase();

            switch (code){
                case CODE_HISTORY:
                    ret = db.query(DataContract.TABLE_HISTORY,
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder
                            );
                    break;
            }
        }

        return  ret;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int ret = 0;

        int code = BrowserProvider.match.match(uri);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (code){
            case CODE_HISTORY:
                ret = db.update(DataContract.TABLE_HISTORY, values, selection, selectionArgs);
                break;
        }
        return ret;

    }
}
