package com.mustika.weddingcardseller.weddingcardseller.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.mustika.weddingcardseller.weddingcardseller.R;
import com.mustika.weddingcardseller.weddingcardseller.autentikasi.LoginActivity;
import com.mustika.weddingcardseller.weddingcardseller.helper.Constant;
import com.mustika.weddingcardseller.weddingcardseller.helper.SharedPrefManager;

public class SplashScreenActivity extends AppCompatActivity {

    private ImageView mBannerImageView;

    private String bannerUrl;


    private ProgressDialog loadingDialog;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            loadingDialog.dismiss();
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mBannerImageView = (ImageView) findViewById(R.id.banner_image_view);

        bannerUrl = SharedPrefManager.getInstance(this).getSeller().getBannerUrl();

//        http://10.0.2.2/web/module/mod_seller/files/110320181032242.jpg

        Log.e("SplashScreenActivity", "bannerUrl :" + bannerUrl);

//        Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
//                    startActivity(intent);

    }

    public void startTread() {
//        loadingDialog = new ProgressDialog(this);
//        loadingDialog.setCanceledOnTouchOutside(false);
//        loadingDialog.setCancelable(false);
//        loadingDialog.setMessage("Please Wait...");
//        loadingDialog.show();
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                try {
////                    attachOrderReadListener();
//                    viewBanner();
//                    sleep(4000);
//                    handler.sendEmptyMessage(0);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
    }


    @Override
    protected void onResume() {
        super.onResume();
        viewBanner();
//        startTread();
//        Thread timerThread = new Thread(){
//            public void run(){
//                try{
//                    sleep(10000);
//                }catch(InterruptedException e){
//                    e.printStackTrace();
//                }finally{
//                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
//                    startActivity(intent);
//                }
//            }
//        };
//        timerThread.start();
    }

    private void viewBanner() {
        Glide.with(this)
                .load(Constant.BANNER_URL + bannerUrl)
                .asBitmap()
                //.placeholder(R.drawable.default_placeholder)
                .override(400, 200) // Can be 2000, 2000
                .into(new BitmapImageViewTarget(mBannerImageView)
                {
                    @Override
                    public void onResourceReady(Bitmap drawable, GlideAnimation anim) {
                        super.onResourceReady(drawable, anim);
                        mBannerImageView.setImageBitmap(drawable);
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                try {
                                    sleep(2000);
                                    handler.sendEmptyMessage(0);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                    }
                });
    }

}
