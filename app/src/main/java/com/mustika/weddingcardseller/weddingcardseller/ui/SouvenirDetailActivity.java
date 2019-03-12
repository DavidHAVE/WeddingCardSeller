package com.mustika.weddingcardseller.weddingcardseller.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.mustika.weddingcardseller.weddingcardseller.R;
import com.mustika.weddingcardseller.weddingcardseller.helper.Constant;
import com.mustika.weddingcardseller.weddingcardseller.helper.SharedPrefManager;
import com.mustika.weddingcardseller.weddingcardseller.helper.TouchImageView;
import com.mustika.weddingcardseller.weddingcardseller.model.Store;

public class SouvenirDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TouchImageView mDesignTouchImageView;
    private ProgressBar mProgressBar;
    private FloatingActionButton mNextFab;
    private TextView mNameTextView, mPriceTextView, mDescriptionTextView;
    private ElegantNumberButton mAmountButton;
    private EditText mAmountEditText;
    private String name, description, imageUrl;
    private int souvenirId, price, total;
    private int amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_souvenir_detail);

        mDesignTouchImageView = (TouchImageView) findViewById(R.id.design_touch_image_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
//        mCardImageView = (ImageView) findViewById(R.id.card_image_view);
        mNameTextView = (TextView) findViewById(R.id.name_text_view);
        mPriceTextView = (TextView) findViewById(R.id.price_text_view);
        mDescriptionTextView = (TextView) findViewById(R.id.description_text_view);
        mAmountEditText = (EditText) findViewById(R.id.amount_edit_text);
//        mAmountButton = (ElegantNumberButton) findViewById(R.id.amount_button);
        mNextFab = (FloatingActionButton) findViewById(R.id.next_fab);

        if (getIntent() != null){
            souvenirId = getIntent().getExtras().getInt(Constant.TAG_SOUVENIR_ID);
            name = getIntent().getExtras().getString(Constant.TAG_NAME);
            price = getIntent().getExtras().getInt(Constant.TAG_PRICE);
            imageUrl = getIntent().getExtras().getString(Constant.TAG_IMAGE_URL);

            //loading the image
//            Glide.with(this)
//                    .load(Constant.IMAGE_URL+imageUrl)
//                    .into(mCardImageView);
            Log.e("SouvenirDetailActivity", "image :"+ Constant.SOUVENIR_IMAGE_URL+imageUrl);

            mNameTextView.setText(name);
            mPriceTextView.setText(String.valueOf(price));
            mDescriptionTextView.setText(description);
        }

//        mAmountButton.setOnClickListener(new ElegantNumberButton.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                amount = Integer.parseInt(mAmountButton.getNumber());
//
//                if (amount == 0){
//                    mAmountEditText.setEnabled(true);
//                }else {
//                    mAmountEditText.setEnabled(false);
//                    total = Integer.parseInt(mAmountButton.getNumber());
//
//                }
//                Toast.makeText(SouvenirDetailActivity.this, String.valueOf(total), Toast.LENGTH_SHORT).show();
//            }
//        });

        mNextFab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.next_fab){

//            if (amount == 0) {


            if (!mAmountEditText.getText().toString().trim().equals("")) {
                total = Integer.parseInt(mAmountEditText.getText().toString().trim());
                Store store = new Store(souvenirId, name, price, total);
                SharedPrefManager.getInstance(getApplicationContext()).souvenirSave(store);
                startActivity(new Intent(this, OrderStepActivity.class));
                finish();
            }else {
                Toast.makeText(this, "Masukkan jumlah pembelian", Toast.LENGTH_SHORT).show();
            }


//            Intent intent = new Intent(this, OrderStepActivity.class);
//                intent.putExtra(Constant.TAG_SOUVENIR_ID, souvenirId);
//                intent.putExtra(Constant.TAG_NAME, name);
////            intent.putExtra(Constant.TAG_DESCRIPTION, description);
//                intent.putExtra(Constant.TAG_PRICE, price);
////            intent.putExtra(Constant.TAG_IMAGE_URL, imageUrl);
//                intent.putExtra("weddingcardquantity", total);
//                startActivity(intent);
//                finish();
//            }else {
//                Toast.makeText(this, "Terdapat dua masukan", Toast.LENGTH_SHORT).show();
//            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showImageProfile();
    }

    private void showImageProfile() {
//        Glide.with(this).load(imageUrl)
//                .crossFade()
////                .thumbnail(0.5f)
////                .bitmapTransform(new CircleTransform(this))
//
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .override(800, 600)
//                .into(mDesignImageView);
        mProgressBar.setVisibility(View.VISIBLE);

        Glide.with(this)
                .load(Constant.SOUVENIR_IMAGE_URL+imageUrl)
                .asBitmap()
                //.placeholder(R.drawable.default_placeholder)
                .override(400, 200) // Can be 2000, 2000
                .into(new BitmapImageViewTarget(mDesignTouchImageView) {
                    @Override
                    public void onResourceReady(Bitmap drawable, GlideAnimation anim) {
                        super.onResourceReady(drawable, anim);
                        mProgressBar.setVisibility(View.GONE);
                        mDesignTouchImageView.setImageBitmap(drawable);
                        mDesignTouchImageView.setVisibility(View.VISIBLE);
                    }
                });
    }
}
