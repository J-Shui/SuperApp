package com.jinshui.superapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.widget.ImageView;
import android.widget.TextView;

public class PaletteActivity extends AppCompatActivity implements Palette.PaletteAsyncListener {

    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette);

        imageView = (ImageView) findViewById(R.id.imgiew);
        textView = (TextView) findViewById(R.id.txt_view);
        textView.setText("杨金水");
//        textView.setTextColor(Color.WHITE);

        //通常使用异步任务加载图片
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.image2);

        imageView.setImageBitmap(bitmap);
        //无参代表同步，阻塞主线程
        // 有参代表异步
        Palette.from(bitmap).generate(this);
    }

    /**
     * 调色板处理完成的回调，在主线程中
     * @param palette
     */
    @Override
    public void onGenerated(Palette palette) {

        //获取用于覆盖在图片上的主体颜色，黑色的，柔和的颜色
        int color = palette.getDarkVibrantColor(Color.WHITE);

        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        color = Color.argb(0x99,red,green,blue);

        textView.setBackgroundColor(color);

        Palette.Swatch swatch = palette.getDarkMutedSwatch();

        if (swatch != null) {
            int titleTextColor = swatch.getTitleTextColor();

            textView.setTextColor(titleTextColor);
        }
    }
}
