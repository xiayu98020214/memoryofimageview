package com.xiayu.memoryofimageview;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
/*http://blog.csdn.net/lsyz0021/article/details/51356670*/
/*https://developer.android.com/topic/performance/graphics/load-bitmap.html#read-bitmap*/
/*在xml中imageview设置宽高，并不能改变占用内存大小*/
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageView)findViewById(R.id.image);
        Log.e(TAG, "im"+ img);
        img.setImageBitmap(
                decodeSampledBitmapFromResource(getResources(), R.id.image, 200, 300));
      //  img.setImageResource(R.drawable.dplan);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
       // printBitmapSize(img);

   /*     img.setImageBitmap(
                decodeSampledBitmapFromResource(getResources(), R.id.image, 100, 100));*/

        //printBitmapSize(img);
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

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap tmp = BitmapFactory.decodeResource(res, resId, options);
        Log.e(TAG, "TMP:"+tmp );
        return tmp;
    }

}
