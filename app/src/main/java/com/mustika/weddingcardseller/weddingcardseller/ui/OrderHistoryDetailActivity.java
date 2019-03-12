package com.mustika.weddingcardseller.weddingcardseller.ui;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.mustika.weddingcardseller.weddingcardseller.R;
import com.mustika.weddingcardseller.weddingcardseller.helper.Constant;
import com.mustika.weddingcardseller.weddingcardseller.helper.TouchImageView;
import com.mustika.weddingcardseller.weddingcardseller.helper.VolleySingleton;
import com.mustika.weddingcardseller.weddingcardseller.model.AddOn;
import com.mustika.weddingcardseller.weddingcardseller.model.Item;
import com.mustika.weddingcardseller.weddingcardseller.model.Order;
import com.mustika.weddingcardseller.weddingcardseller.model.OrderHistory;
import com.mustika.weddingcardseller.weddingcardseller.model.Souvenir;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderHistoryDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TouchImageView mCustomTouchImageView, mDesignTouchImageView, mFinalDesignTouchImageView, mSouvenirTouchImageView;
    private LinearLayout mSouvenirLayout;
    private ProgressBar mProgressBar;
    private TextView mNameTextView, mPriceTextView, mDescriptionTextView, mSouvenirNameTextView, mSouvenirPriceTextView,
            mWebPriceTextView, mUploadDesignTextView, mCustomDesignTextView;

    private EditText mFullNameMEditText, mNickNameMEditText, mFatherFullNameMEditText, mMotherFullNameMEditText,
            mParentAddressMEditText, mFullNameWEditText, mNickNameWEditText, mFatherFullNameWEditText, mMotherFullNameWEditText,
            mParentAddressWEditText, mDayAndDateEditText, mTimeEditText, mPlaceEditText,
            mFullNameOEditText, mNickNameOEditText, mEmailOEditText, mTelephoneOEditText, mOrderTimeEditText, mOrderPriceEditText,
            mMessageEditText;
    private CheckBox mWeddingWebCheckBox;
    private Switch mPaymentCompleteSwitch;

    public static String itemName, itemDescription, itemImageUrl, addon;
    public static String addonName = "Website Pernikahan";
    public static int itemPrice, addonId, addonPrice, itemPriceQuantity, souvenirQuantity,
            souvenirPriceQuantity, addonPriceQuantity, totalPriceQuantity, subTotalPrice, taxPrice, finalPrice;
    private int buyerAmount;
    public static boolean isAddOn, isCustomDesign;
//    public static List<Discount> discountList;

    private MapFragment mapFragment;

    private EditText mLokasiEditText;

    //ImageView to display image selected
//    ImageView imageView;

    //edittext for getting the tags input
//    EditText editTextTags;

    private int orderHistoryId, orderPrice;

    public static String fullNameM, nickNameM, fatherNameM, motherNameM, parentAddressM,
            fullNameW, nickNameW, fatherNameW, motherNameW, parentAddressW, dayAndDate, time, place,
            latitude, longtitude, fullNameO, nickNameO, emailO, telephoneO, username, paymentCompleted, web, message;
    public static int souvenirId, imageId, itemId, discountId, sellerId, weddingCardQuantity, discount;
    private String statusPayment, orderTime;
    private String name, description, imageUrl, designUrl, souvenirName, souvenirImageUrl, customImageUrl, tags;
    private int price, souvenirPrice;
    private boolean isWeb;

    private int reportId, incomeTotal, monthBuyerAmount, monthIncome, monthSalary, status;
    private String month;

    private GoogleMap mGoogleMap;
    public static final CameraPosition LOCATION = CameraPosition.builder()
            .target(new LatLng(40.784,-73.9857))
            .zoom(21)
            .bearing(0)
            .tilt(45)
            .build();

    List<OrderHistory> orderHistories;
    //Volley Request Queue
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_detail);

        mCustomTouchImageView = (TouchImageView) findViewById(R.id.custom_touch_image_view);
        mDesignTouchImageView = (TouchImageView) findViewById(R.id.design_touch_image_view);
        mFinalDesignTouchImageView = (TouchImageView) findViewById(R.id.final_design_touch_image_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mNameTextView = (TextView) findViewById(R.id.name_text_view);
        mPriceTextView = (TextView) findViewById(R.id.price_text_view);
        mDescriptionTextView = (TextView) findViewById(R.id.description_text_view);
        mUploadDesignTextView = (TextView) findViewById(R.id.upload_design_text_view);
        mPaymentCompleteSwitch = (Switch) findViewById(R.id.payment_complete_switch);
        mCustomDesignTextView = (TextView) findViewById(R.id.custom_design_text_view);
        mSouvenirTouchImageView = (TouchImageView) findViewById(R.id.souvenir_touch_image_view);
        mSouvenirLayout = (LinearLayout) findViewById(R.id.souvenir_layout);

        mFullNameMEditText = (EditText) findViewById(R.id.full_name_m_edit_text);
        mNickNameMEditText = (EditText) findViewById(R.id.nick_name_m_edit_text);
        mFatherFullNameMEditText = (EditText) findViewById(R.id.father_full_name_m_edit_text);
        mMotherFullNameMEditText = (EditText) findViewById(R.id.mother_full_name_m_edit_text);
        mParentAddressMEditText = (EditText) findViewById(R.id.parent_address_m_edit_text);
        mFullNameWEditText = (EditText) findViewById(R.id.full_name_w_edit_text);
        mNickNameWEditText = (EditText) findViewById(R.id.nick_name_w_edit_text);
        mFatherFullNameWEditText = (EditText) findViewById(R.id.father_full_name_w_edit_text);
        mMotherFullNameWEditText = (EditText) findViewById(R.id.mother_full_name_w_edit_text);
        mParentAddressWEditText = (EditText) findViewById(R.id.parent_address_w_edit_text);
        mDayAndDateEditText = (EditText) findViewById(R.id.day_and_date_edit_text);
        mTimeEditText = (EditText) findViewById(R.id.time_edit_text);
        mPlaceEditText = (EditText) findViewById(R.id.place_edit_text);
        mFullNameOEditText = (EditText) findViewById(R.id.full_name_o_edit_text);
        mNickNameOEditText = (EditText) findViewById(R.id.nick_name_o_edit_text);
        mEmailOEditText = (EditText) findViewById(R.id.email_o_edit_text);
        mTelephoneOEditText = (EditText) findViewById(R.id.telephone_o_edit_text);
        mMessageEditText = (EditText) findViewById(R.id.message_edit_text);
        mOrderTimeEditText = (EditText) findViewById(R.id.order_time_edit_text);
        mOrderPriceEditText = (EditText) findViewById(R.id.order_price_edit_text);
        mSouvenirNameTextView = (TextView) findViewById(R.id.souvenir_name_text_view);
        mSouvenirPriceTextView = (TextView) findViewById(R.id.souvenir_price_text_view);
        mMessageEditText = (EditText) findViewById(R.id.message_edit_text);
        mWebPriceTextView = (TextView) findViewById(R.id.web_price_text_view);
        mWeddingWebCheckBox = (CheckBox) findViewById(R.id.wedding_web_check_box);

        mPaymentCompleteSwitch.setEnabled(false);

        if (this.getIntent() != null){
            orderHistoryId = this.getIntent().getExtras().getInt(Constant.TAG_ORDER_HISTORY_ID);

            Log.e("OrderDetailActivity", "orderId : "+orderHistories);
        }

        //Initializing our superheroes list
        orderHistories = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUploadDesignTextView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        requestQueue = Volley.newRequestQueue(this);
        getData();
    }

    private JsonArrayRequest getDataFromServer(int orderId) {
        //Initializing ProgressBar
        //Displaying Progressbar
//        mDiscountProgressBar.setVisibility(View.VISIBLE);
//        setProgressBarIndeterminateVisibility(true);

        //JsonArrayRequest of volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Constant.READ_ORDER_HISTORY_URL_ALL + orderId,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Calling method parseData to parse the json response
                        parseData(response);
                        //Hiding the progressbar
//                        mDiscountProgressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        mDiscountProgressBar.setVisibility(View.GONE);
                        //If an error occurs that means end of the list has reached
//                        Toast.makeText(getActivity(), "No More Items Available", Toast.LENGTH_SHORT).show();
                    }
                });

        //Returning the request
        return jsonArrayRequest;
    }

    //This method will get data from the web api
    private void getData() {
        //Adding the method to the queue by calling the method getDataFromServer
        requestQueue.add(getDataFromServer(orderHistoryId));
    }

    //This method will parse json data
    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            //Creating the superhero object
            OrderHistory orderHistory = new OrderHistory();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);

                fullNameM = json.getString("full_name_m");
                nickNameM = json.getString("nick_name_m");
                fatherNameM = json.getString("father_full_name_m");
                motherNameM = json.getString("mother_name_m");
                parentAddressM = json.getString("parent_address_m");
                fullNameW = json.getString("full_name_w");
                nickNameW = json.getString("nick_name_w");
                fatherNameW = json.getString("father_full_name_w");
                motherNameW = json.getString("mother_name_w");
                parentAddressW = json.getString("parent_address_w");

                dayAndDate = json.getString("day_and_date");
                time = json.getString("wedding_time");
                place = json.getString("place");
                latitude = json.getString("latitude");
                longtitude = json.getString("longtitude");
                orderTime = json.getString("order_time");
                orderPrice = json.getInt("order_price");
                fullNameO = json.getString("full_name_o");
                nickNameO = json.getString("nick_name_o");
                emailO = json.getString("email_o");
                telephoneO = json.getString("telephone_o");
                username = json.getString("username");
                statusPayment = json.getString("payment_completed");
                designUrl = json.getString("design_url");

                message = json.getString("message");

                souvenirId = json.getInt("souvenir_id_fk");
                addonId = json.getInt("addon_id_fk");
                imageId = json.getInt("image_id_fk");
                itemId = json.getInt("item_id_fk");
                discountId = json.getInt("discount_id_fk");
                sellerId = json.getInt("seller_id_fk");


                Log.e("DiscountFragment", "total :"+json.getInt(Constant.TAG_TOTAL)+
                        ", discount :"+json.getInt(Constant.TAG_DISCOUNT));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the superhero object to the list
            orderHistories.add(orderHistory);

            if (imageId != -1) {
                imageRead();
            }else {
                itemRead();
            }
        }

//        viewOrderHistory();
    }

    private void imageRead(){

//        final int sellerId = SharedPrefManager.getInstance(this).getSeller().getSellerId();
//        int buyerAmount = SharedPrefManager.getInstance(this).getSeller().getBuyerAmount();

//        Log.e("OrderStepACtivity", "sellerId(sellerRead) : "+sellerId);

        Log.e("OrderDetailActivity", "souvenirId :"+souvenirId);

//        buyerAmount += 1;

//        final int finalBuyerAmount = buyerAmount;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.GET_PICS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        progressBar.setVisibility(View.GONE);
                        Log.e("OrderDetailActivity", "responsePic : "+response);

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject imageJson = obj.getJSONObject("image");

//                                Souvenir souvenir = new Souvenir(
//                                        souvenirJson.getInt("souvenir_id"),
//                                        souvenirJson.getString("name"),
//                                        souvenirJson.getInt("price"),
//                                        souvenirJson.getString("image_url")
//                                );

                                customImageUrl = imageJson.getString("image");
//                                tags = imageJson.getString("tags");

//                                souvenirName = souvenir.getName();
//                                souvenirPrice = souvenir.getPrice();
//                                souvenirImageUrl = souvenir.getImageUrl();

//                                if (adxdonId != -1){
//                                    addonRead();
//                                }else {
//                                    itemRead();
//                                }

//                                itemRead();
//                                webRead();
//                                viewOrder();
//                                addonRead();

                                Log.e("OrderDetailActivity", "customImageUrl : "+customImageUrl);

                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        addonRead();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("imageId", String.valueOf(imageId));
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    private void itemRead(){

//        final int sellerId = SharedPrefManager.getInstance(this).getSeller().getSellerId();
//        int buyerAmount = SharedPrefManager.getInstance(this).getSeller().getBuyerAmount();

//        Log.e("OrderStepACtivity", "sellerId(sellerRead) : "+sellerId);

        Log.e("OrderDetailActivity", "itemId :"+itemId);

//        buyerAmount += 1;

//        final int finalBuyerAmount = buyerAmount;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.READ_ITEM_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        progressBar.setVisibility(View.GONE);
                        Log.e("OrderDetailActivity", "responseItemRead : "+response);

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject itemJson = obj.getJSONObject("item");

                                Item item = new Item(
                                        itemJson.getInt("item_id"),
                                        itemJson.getString("name"),
                                        itemJson.getString("description"),
                                        itemJson.getInt("price"),
                                        itemJson.getString("image_url")
                                );

                                name = item.getName();
                                description = item.getDescription();
                                price = item.getPrice();
                                imageUrl = item.getImageUrl();

                                souvenirRead();
//                                viewOrder();

                                Log.e("OrderDetailActivity", "name : "+item.getName());

                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("itemId", String.valueOf(itemId));
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void souvenirRead(){

//        final int sellerId = SharedPrefManager.getInstance(this).getSeller().getSellerId();
//        int buyerAmount = SharedPrefManager.getInstance(this).getSeller().getBuyerAmount();

//        Log.e("OrderStepACtivity", "sellerId(sellerRead) : "+sellerId);

        Log.e("OrderDetailActivity", "souvenirId :"+souvenirId);

//        buyerAmount += 1;

//        final int finalBuyerAmount = buyerAmount;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.READ_SOUVENIR_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        progressBar.setVisibility(View.GONE);
                        Log.e("OrderDetailActivity", "responseSouvenir : "+response);

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject souvenirJson = obj.getJSONObject("souvenir");

                                Souvenir souvenir = new Souvenir(
                                        souvenirJson.getInt("souvenir_id"),
                                        souvenirJson.getString("name"),
                                        souvenirJson.getInt("price"),
                                        souvenirJson.getString("image_url")
                                );

                                souvenirName = souvenir.getName();
                                souvenirPrice = souvenir.getPrice();
                                souvenirImageUrl = souvenir.getImageUrl();


//                                webRead();
//                                viewOrder();
//                                addonRead();

                                Log.e("OrderDetailActivity", "name : "+souvenir.getName());

                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        viewOrder();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("souvenirId", String.valueOf(souvenirId));
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    private void addonRead(){

//        final int sellerId = SharedPrefManager.getInstance(this).getSeller().getSellerId();
//        int buyerAmount = SharedPrefManager.getInstance(this).getSeller().getBuyerAmount();

//        Log.e("OrderStepACtivity", "sellerId(sellerRead) : "+sellerId);

        Log.e("OrderDetailActivity", "souvenirId :"+souvenirId);

//        buyerAmount += 1;

//        final int finalBuyerAmount = buyerAmount;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.READ_ADDON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        progressBar.setVisibility(View.GONE);
                        Log.e("OrderDetailActivity", "responseAddOn : "+response);

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject souvenirJson = obj.getJSONObject("addon");

                                AddOn addOn = new AddOn(
                                        souvenirJson.getInt("addon_id"),
                                        souvenirJson.getString("name"),
                                        souvenirJson.getInt("price")
                                );

                                addonName = addOn.getName();
                                addonPrice = addOn.getPrice();

//                                if (imageId != -1) {
//                                    imageRead();
//                                }else {
//                                    itemRead();
//                                }
//                                webRead();
//                                viewOrder();

                                Log.e("OrderDetailActivity", "name : "+addOn.getName());

                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        itemRead();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("addonId", String.valueOf(addonId));
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        flyTo(LOCATION);
    }

    private void flyTo(CameraPosition target)
    {
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(target), 10000, null);
    }

    private void viewOrder() {

        mProgressBar.setVisibility(View.VISIBLE);

        if (imageId != -1) {
            mCustomDesignTextView.setText("Kustom Desain");
            Glide.with(this)
                    .load(Constant.CUSTOM_URL + customImageUrl)
                    .asBitmap()
                    //.placeholder(R.drawable.default_placeholder)
                    .override(400, 200) // Can be 2000, 2000
                    .into(new BitmapImageViewTarget(mCustomTouchImageView) {
                        @Override
                        public void onResourceReady(Bitmap drawable, GlideAnimation anim) {
                            super.onResourceReady(drawable, anim);
                            mProgressBar.setVisibility(View.GONE);
                            mCustomTouchImageView.setImageBitmap(drawable);
                            mCustomTouchImageView.setVisibility(View.VISIBLE);
                            mCustomTouchImageView.setVisibility(View.VISIBLE);
                        }
                    });
        }else {
            mCustomDesignTextView.setText("Bukan kustom desain");
            mCustomTouchImageView.setVisibility(View.GONE);
        }

        if (souvenirId != -1) {
            Glide.with(this)
                    .load(Constant.CUSTOM_URL + customImageUrl)
                    .asBitmap()
                    //.placeholder(R.drawable.default_placeholder)
                    .override(400, 200) // Can be 2000, 2000
                    .into(new BitmapImageViewTarget(mSouvenirTouchImageView) {
                        @Override
                        public void onResourceReady(Bitmap drawable, GlideAnimation anim) {
                            super.onResourceReady(drawable, anim);
                            mProgressBar.setVisibility(View.GONE);
                            mSouvenirTouchImageView.setImageBitmap(drawable);
                            mSouvenirLayout.setVisibility(View.VISIBLE);
                            mSouvenirLayout.setVisibility(View.VISIBLE);
                        }
                    });
        }else {
            mSouvenirTouchImageView.setVisibility(View.GONE);
            mSouvenirLayout.setVisibility(View.GONE);
        }

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

        Glide.with(this)
                .load(Constant.DESIGN_URL+designUrl)
                .asBitmap()
                //.placeholder(R.drawable.default_placeholder)
                .override(400, 200) // Can be 2000, 2000
                .into(new BitmapImageViewTarget(mFinalDesignTouchImageView) {
                    @Override
                    public void onResourceReady(Bitmap drawable, GlideAnimation anim) {
                        super.onResourceReady(drawable, anim);
                        mProgressBar.setVisibility(View.GONE);
                        mUploadDesignTextView.setVisibility(View.GONE);
                        mFinalDesignTouchImageView.setImageBitmap(drawable);
                        mFinalDesignTouchImageView.setVisibility(View.VISIBLE);
                    }
                });

        mNameTextView.setText(name);
        mPriceTextView.setText(String.valueOf(price));
        mDescriptionTextView.setText(description);


        Log.e("OrderDetailActivity", "View Data, statusPayment :"+statusPayment);
        if (statusPayment.equals("true")){
            mPaymentCompleteSwitch.setChecked(true);
        }else {
            mPaymentCompleteSwitch.setChecked(false);
        }

        mFullNameMEditText.setText(fullNameM);
        mNickNameMEditText.setText(nickNameM);
        mFatherFullNameMEditText.setText(fatherNameM);
        mMotherFullNameMEditText.setText(motherNameM);
        mParentAddressMEditText.setText(parentAddressM);
        mFullNameWEditText.setText(fullNameW);
        mNickNameWEditText.setText(nickNameW);
        mFatherFullNameWEditText.setText(fatherNameW);
        mMotherFullNameWEditText.setText(motherNameW);
        mParentAddressWEditText.setText(parentAddressW);

        mDayAndDateEditText.setText(dayAndDate);
        mPlaceEditText.setText(place);
        mTimeEditText.setText(time);
        mFullNameOEditText.setText(fullNameO);
        mNickNameOEditText.setText(nickNameO);
        mEmailOEditText.setText(emailO);
        mTelephoneOEditText.setText(telephoneO);
        mOrderTimeEditText.setText(orderTime);
        mOrderPriceEditText.setText(String.valueOf(orderPrice));

        mSouvenirNameTextView.setText(souvenirName);
        mSouvenirPriceTextView.setText(String.valueOf(souvenirPrice));
        mMessageEditText.setText(message);

        if (addonId != -1) {
            mWeddingWebCheckBox.setChecked(true);
            mWebPriceTextView.setText(String.valueOf(addonPrice));
        }else {
            mWeddingWebCheckBox.setChecked(false);
            mWebPriceTextView.setText("-");
        }
    }
}
