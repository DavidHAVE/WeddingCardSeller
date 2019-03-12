package com.mustika.weddingcardseller.weddingcardseller.helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.mustika.weddingcardseller.weddingcardseller.model.AddOn;
import com.mustika.weddingcardseller.weddingcardseller.model.Item;
import com.mustika.weddingcardseller.weddingcardseller.model.Seller;
import com.mustika.weddingcardseller.weddingcardseller.autentikasi.LoginActivity;
import com.mustika.weddingcardseller.weddingcardseller.model.Souvenir;
import com.mustika.weddingcardseller.weddingcardseller.model.Store;

/**
 * Created by Belal on 9/5/2017.
 */

//here for this class we are using a singleton pattern

public class SharedPrefManager {

    //the constants
    private static final String SHARED_PREF_NAME = "weddingcard";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_TELEPHONE = "keytelephone";
    private static final String KEY_CITY = "keycity";
    private static final String KEY_BUYER_AMOUNT = "buyeramount";
    private static final String KEY_BANNER_URL = "bannerurl";
    private static final String KEY_ID = "keyid";


//    private static final String SHARED_ITEM_PREF_NAME = "itemweddingcard";
    private static final String KEY_STORE_NAME = "keystorename";
    private static final String KEY_STORE_DESCRIPTION = "keystoredescription";
    private static final String KEY_STORE_PRICE = "keystoreprice";
    private static final String KEY_STORE_TOTAL = "keystoretotal";
    private static final String KEY_STORE_IMAGE_URL = "keystoreimageurl";

    private static final String SHARED_ITEM_PREF_NAME = "itemweddingcard";
    private static final String KEY_ITEM_NAME = "keyitemname";
    private static final String KEY_ITEM_DESCRIPTION = "keyitemdescription";
    private static final String KEY_ITEM_PRICE = "keyitemprice";
    private static final String KEY_ITEM_TOTAL = "keyitemtotal";
    private static final String KEY_ITEM_IMAGE_URL = "keyitemimageurl";
//    private static final String KEY_ID = "keyid";

    private static final String SHARED_SOUVENIR_PREF_NAME = "souvenirweddingcard";
    private static final String KEY_SOUVENIR_NAME = "keysouvenirname";
    private static final String KEY_SOUVENIR_PRICE = "keysouvenirprice";
    private static final String KEY_SOUVENIR_TOTAL = "keysouvenirtotal";
    private static final String KEY_SOUVENIR_IMAGE_URL = "keysouvenirimageurl";

    private static final String SHARED_ADDON_PREF_NAME = "addonweddingcard";
    private static final String KEY_ADDON_NAME = "keyaddonname";
    private static final String KEY_ADDON_PRICE = "keyaddonprice";


    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void sellerLogin(Seller seller) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, seller.getSellerId());
        editor.putString(KEY_USERNAME, seller.getUsername());
        editor.putString(KEY_EMAIL, seller.getEmail());
        editor.putString(KEY_TELEPHONE, seller.getTelephone());
        editor.putString(KEY_CITY, seller.getCity());
        editor.putInt(KEY_BUYER_AMOUNT, seller.getBuyerAmount());
        editor.putString(KEY_BANNER_URL, seller.getBannerUrl());
        editor.apply();
    }

    public void itemSave(Store store) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_ITEM_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, store.getStoreId());
        editor.putString(KEY_ITEM_NAME, store.getStoreName());
//        editor.putString(KEY_ITEM_DESCRIPTION, item.getDescription());
        editor.putInt(KEY_ITEM_PRICE, store.getStorePrice());
        editor.putInt(KEY_ITEM_TOTAL, store.getStoreTotal());
//        editor.putString(KEY_ITEM_IMAGE_URL, item.getImageUrl());
        editor.apply();
    }

    public void souvenirSave(Store store) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_SOUVENIR_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, store.getStoreId());
        editor.putString(KEY_SOUVENIR_NAME, store.getStoreName());
        editor.putInt(KEY_SOUVENIR_PRICE, store.getStorePrice());
        editor.putInt(KEY_SOUVENIR_TOTAL, store.getStoreTotal());
        editor.apply();
    }

    public void addOnSave(AddOn addOn) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_ADDON_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, addOn.getAddonId());
        editor.putString(KEY_ADDON_NAME, addOn.getName());
        editor.putInt(KEY_ADDON_PRICE, addOn.getPrice());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    public boolean isItem() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_ITEM_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ITEM_NAME, null) != null;
    }

    public boolean isSouvenir() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_SOUVENIR_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_SOUVENIR_NAME, null) != null;
    }

    public boolean isAddOn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_ADDON_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ADDON_NAME, null) != null;
    }



    //this method will give the logged in user
    public Seller getSeller() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Seller(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_TELEPHONE, null),
                sharedPreferences.getString(KEY_CITY, null),
                sharedPreferences.getInt(KEY_BUYER_AMOUNT, 0),
                sharedPreferences.getString(KEY_BANNER_URL, null)
        );
    }

    public Store getItem() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_ITEM_PREF_NAME, Context.MODE_PRIVATE);
        return new Store(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_ITEM_NAME, null),
                sharedPreferences.getInt(KEY_ITEM_PRICE, 0),
                sharedPreferences.getInt(KEY_ITEM_TOTAL, 0)
        );
    }

    public Store getSouvenir() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_SOUVENIR_PREF_NAME, Context.MODE_PRIVATE);
        return new Store(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_SOUVENIR_NAME, null),
                sharedPreferences.getInt(KEY_SOUVENIR_PRICE, 0),
                sharedPreferences.getInt(KEY_SOUVENIR_TOTAL, 0)
                );
    }

    public AddOn getAddOn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_ADDON_PREF_NAME, Context.MODE_PRIVATE);
        return new AddOn(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_ADDON_NAME, null),
                sharedPreferences.getInt(KEY_ADDON_PRICE, 0)
        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }

    public void deleteItem() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_ITEM_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public void deleteSouvenir() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_SOUVENIR_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public void deleteAddOn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_ADDON_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}