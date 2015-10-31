package com.jinshui.superapp;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.jinshui.superapp.fragment.HistoryFragment;
import com.jinshui.superapp.providers.DataContract;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("Config", "orientation = " + newConfig.orientation);

    }

    /**
     * 添加历史
     *
     * @param view
     */
    public void btnAddHistory(View view) {
        ContentResolver resolver = getContentResolver();

        ContentValues values = new ContentValues();

        values.put(DataContract.History.URL,"http://www.baidu.com");

        //添加记录，并且返回记录的访问Uri
        Uri uri = resolver.insert(DataContract.History.CONTENT_URI, values);

        if (uri != null) {
            Toast.makeText(this,uri.toString(),Toast.LENGTH_LONG).show();

            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_history);

            if (fragment != null) {
                if (fragment instanceof HistoryFragment){
                    HistoryFragment historyFragment = (HistoryFragment) fragment;

                    historyFragment.refreshHistory();
                }
            }
        }
    }

    /**
     * 切换横屏
     * @param view
     */
    public void btnSwitchLandscape(View view) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public void btnJumpAndroid5(View view) {
        startActivity(new Intent(this,Android5Activity.class));
    }

    public void btnJumpPalette(View view) {
        startActivity(new Intent(this,PaletteActivity.class));
    }
}
