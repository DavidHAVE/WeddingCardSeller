package com.mustika.weddingcardseller.weddingcardseller.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.mustika.weddingcardseller.weddingcardseller.model.Item;
import com.mustika.weddingcardseller.weddingcardseller.model.Store;

public class CardDetailActivity extends AppCompatActivity implements View.OnClickListener {


    private TouchImageView mDesignTouchImageView;
    private ProgressBar mProgressBar;
    private FloatingActionButton mNextFab;
    private TextView mNameTextView, mPriceTextView, mDescriptionTextView;
//    private ElegantNumberButton mAmountButton;
    private EditText mAmountEditText;
    private String name, description, imageUrl;
    private int itemId, price, total;
    private int amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_detail);

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
            itemId = getIntent().getExtras().getInt(Constant.TAG_ITEM_ID);
            name = getIntent().getExtras().getString(Constant.TAG_NAME);
            description = getIntent().getExtras().getString(Constant.TAG_DESCRIPTION);
            price = getIntent().getExtras().getInt(Constant.TAG_PRICE);
            imageUrl = getIntent().getExtras().getString(Constant.TAG_IMAGE_URL);

            //loading the image
//            Glide.with(this)
//                    .load(Constant.IMAGE_URL+imageUrl)
//                    .into(mCardImageView);
            Log.e("CardDetailActivity", "image :"+ Constant.IMAGE_URL+imageUrl);

            mNameTextView.setText(name);
            mPriceTextView.setText(String.valueOf(price));
            mDescriptionTextView.setText(description);
        }

//       mAmountButton.setOnClickListener(new ElegantNumberButton.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//               amount = Integer.parseInt(mAmountButton.getNumber());
//
////               int amountEditText = Integer.parseInt(mAmountEditText.getText().toString());
//
//               if (amount == 0){
//                   mAmountEditText.setEnabled(true);
//               }else {
//                   mAmountEditText.setEnabled(false);
//                   total = Integer.parseInt(mAmountButton.getNumber());
//
//               }
//               Toast.makeText(CardDetailActivity.this, String.valueOf(total), Toast.LENGTH_SHORT).show();
//           }
//       });

        mNextFab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.next_fab){


//            if (amount == 0) {
            if (!mAmountEditText.getText().toString().trim().equals("")) {
                SharedPrefManager.getInstance(getApplicationContext()).deleteItem();
                SharedPrefManager.getInstance(getApplicationContext()).deleteSouvenir();
                total = Integer.parseInt(mAmountEditText.getText().toString().trim());
                showSouvenirConfirmationDialog();
            }else {
                Toast.makeText(this, "Masukkan jumlah pembelian", Toast.LENGTH_SHORT).show();
            }

//                total = Integer.parseInt(mAmountEditText.getText().toString());
//                Intent intent = new Intent(this, OrderStepActivity.class);
//                intent.putExtra(Constant.TAG_ITEM_ID, itemId);
//                intent.putExtra(Constant.TAG_NAME, name);
//                intent.putExtra(Constant.TAG_PRICE, price);
//                intent.putExtra("weddingcardquantity", total);
//                startActivity(intent);
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
                .load(Constant.IMAGE_URL+imageUrl)
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

    private void showSouvenirConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Ingin menambahkan souvenir ?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {


                Store store = new Store(itemId, name, price, total);

                SharedPrefManager.getInstance(getApplicationContext()).itemSave(store);


                Intent intent = new Intent(CardDetailActivity.this, SouvenirGalleryActivity.class);
//                intent.putExtra(Constant.TAG_ITEM_ID, itemId);
//                intent.putExtra(Constant.TAG_NAME, name);
//                intent.putExtra(Constant.TAG_PRICE, price);
//                intent.putExtra("weddingcardquantity", total);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {


                    Store store = new Store(itemId, name, price, total);

                    SharedPrefManager.getInstance(getApplicationContext()).itemSave(store);

                    SharedPrefManager.getInstance(getApplicationContext()).deleteSouvenir();

                    Intent intent = new Intent(CardDetailActivity.this, OrderStepActivity.class);
//                    intent.putExtra(Constant.TAG_ITEM_ID, itemId);
//                    intent.putExtra(Constant.TAG_NAME, name);
//                    intent.putExtra(Constant.TAG_PRICE, price);
//                    intent.putExtra("weddingcardquantity", total);
                    startActivity(intent);
                    finish();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
