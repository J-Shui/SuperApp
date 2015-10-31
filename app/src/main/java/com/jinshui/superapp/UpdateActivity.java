package com.jinshui.superapp;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.jinshui.superapp.providers.DataContract;

public class UpdateActivity extends AppCompatActivity {

    private EditText editText;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        editText = (EditText) findViewById(R.id.edit_text);

        Intent intent = getIntent();

        String url = intent.getStringExtra("url");
        id = intent.getStringExtra("id");

        editText.setText(url);
    }

    public void btnSubmit(View view) {
        String string = editText.getText().toString();

        ContentValues values = new ContentValues();
        values.put(DataContract.History.URL,string);
        getContentResolver().update(DataContract.History.CONTENT_URI, values, "_id=" + id, null);

        finish();
    }
}
