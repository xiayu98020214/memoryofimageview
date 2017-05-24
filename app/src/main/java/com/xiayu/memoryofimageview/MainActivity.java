package com.xiayu.memoryofimageview;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageView)findViewById(R.id.image);
        Log.e(TAG, "im"+ img);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        printBitmapSize(img);
    }

    private void printBitmapSize(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        if (drawable != null) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            Log.d(TAG, " width = " + bitmap.getWidth() + " height = " + bitmap.getHeight());

            DisplayMetrics d = getResources().getDisplayMetrics();
            Log.d(TAG,"屏幕密度："+ d.density + "   "+d.densityDpi);
            //image图片放在了xxhdpi,所以为480.
            float scale =  (float)d.densityDpi / 480 ;
           // float x = 469*scale + 0.5f;
            int x = (int)(469*scale + 0.5f);
            int y = (int)(215*scale + 0.5f);
            int total = x*y*4;
            Log.d(TAG,"占用内存："+bitmap.getByteCount()+"   total:"+total + "  scale:"+scale);
            //预测bitmap.getByteCount 和 total的值应该相等的。
            //占用内存大小和手机屏幕密度，以及图片所存放文件有关。
        } else {
            Log.d(TAG, "Drawable is null !");
        }
    }

}
