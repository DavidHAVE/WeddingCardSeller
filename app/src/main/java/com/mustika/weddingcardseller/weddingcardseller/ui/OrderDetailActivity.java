package com.mustika.weddingcardseller.weddingcardseller.ui;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.mustika.weddingcardseller.weddingcardseller.helper.SharedPrefManager;
import com.mustika.weddingcardseller.weddingcardseller.helper.TouchImageView;
import com.mustika.weddingcardseller.weddingcardseller.helper.VolleySingleton;
import com.mustika.weddingcardseller.weddingcardseller.model.AddOn;
import com.mustika.weddingcardseller.weddingcardseller.model.Discount;
import com.mustika.weddingcardseller.weddingcardseller.model.Item;
import com.mustika.weddingcardseller.weddingcardseller.model.Order;
import com.mustika.weddingcardseller.weddingcardseller.model.Report;
import com.mustika.weddingcardseller.weddingcardseller.model.Souvenir;
import com.mustika.weddingcardseller.weddingcardseller.model.Store;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDetailActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

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
    private Switch mPaymentCompleteSwitch;
    private CheckBox mWeddingWebCheckBox;
    private Button mOrderCompleteButton;

    public static String itemName, itemDescription, itemImageUrl, addon;
    public static String addonName = "Website Pernikahan";
    public static int itemPrice, addonId, addonPrice, itemPriceQuantity, souvenirQuantity,
            souvenirPriceQuantity, addonPriceQuantity, totalPriceQuantity, subTotalPrice, taxPrice, finalPrice;
    private int buyerAmount;
    public static boolean isAddOn, isCustomDesign;
    public static List<Discount> discountList;

    private MapFragment mapFragment;

    private EditText mLokasiEditText;

    //ImageView to display image selected
    ImageView imageView;

    //edittext for getting the tags input
//    EditText editTextTags;

    private int orderId, orderPrice;

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

    List<Order> orders;
    //Volley Request Queue
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

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
        mOrderCompleteButton = (Button) findViewById(R.id.order_complete_button);

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //initializing views
        imageView = (ImageView) findViewById(R.id.imageView);
//        editTextTags = (EditText) findViewById(R.id.editTextTags);

        if (this.getIntent() != null){
            orderId = this.getIntent().getExtras().getInt(Constant.TAG_ORDER_ID);

            Log.e("OrderDetailActivity", "orderId : "+orderId);
        }

        //Initializing our superheroes list
        orders = new ArrayList<>();

        mPaymentCompleteSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    changeCompletePayment(orderId, "true");
                    Log.e("OrderDetailActivity", "statusSwitch : true");

                }else {
                    changeCompletePayment(orderId, "false");
                    Log.e("OrderDetailActivity", "statusSwitch : false");
                }
            }
        });

        mOrderCompleteButton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUploadDesignTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.order_complete_button){
            showOrderCompleteConfirmationDialog(orderId);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        requestQueue = Volley.newRequestQueue(this);
        getData();
    }


    private void changeCompletePayment(final int orderId, final String status) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.CHANGE_COMPLETE_PAYMENT_URL + orderId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        progressBar.setVisibility(View.GONE);

                        Log.e("OrderDetailActivity", "ResponsePayment: "+response);

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject("status");

                                //creating a new user object
//                                User user = new User(
//                                        userJson.getInt("id"),
//                                        userJson.getString("username"),
//                                        userJson.getString("email"),
//                                        userJson.getString("gender")
//                                );

                                statusPayment = userJson.getString("payment_completed");
//                                if (status.equals("true")){
//                                    mPaymentCompleteSwitch.setChecked(true);
//                                }else {
//                                    mPaymentCompleteSwitch.setChecked(false);
//                                }
                                Log.e("OrderDetailActivity", "changeComplete, statusPayment : "+statusPayment);

                                getData();

//                                Log.e("OrderDetailActivity", "GetData, status : "+status);


                                //storing the user in shared preferences
//                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                //starting the profile activity
//                                finish();
//                                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.e("OrderDetailActivity", "changeComplete, statusPayment : "+statusPayment);

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
//                params.put("username", username);
//                params.put("email", email);
//                params.put("password", password);
//                params.put("gender", gender);

//                params.put("orderId", String.valueOf(orderId));
                params.put("paymentCompleted", status);

                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    private void showOrderCompleteConfirmationDialog(final int orderId) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Order sudah selesai ?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
//                orderComplete(orderId);
                readReport();
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void  orderComplete(int orderId) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.CHANGE_COMPLETE_ORDER_URL + orderId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        progressBar.setVisibility(View.GONE);
                        Log.e("OrderDetailActivity", "ResponseComplete: "+response);

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
//                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();


                                finish();
                                //getting the user from the response
//                                JSONObject userJson = obj.getJSONObject("status");

                                //creating a new user object
//                                User user = new User(
//                                        userJson.getInt("id"),
//                                        userJson.getString("username"),
//                                        userJson.getString("email"),
//                                        userJson.getString("gender")
//                                );

//                                statusPayment = userJson.getString("payment_completed");
//                                if (status.equals("true")){
//                                    mPaymentCompleteSwitch.setChecked(true);
//                                }else {
//                                    mPaymentCompleteSwitch.setChecked(false);
//                                }

//                                getData();

//                                updateReport();
//                                Log.e("OrderDetailActivity", "GetData, status : "+status);


                                //storing the user in shared preferences
//                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                //starting the profile activity
//                                finish();
//                                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            } else {
//                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
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
//                params.put("orderId", String.valueOf(orderId));
                params.put("orderCompleted", statusPayment);

                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        Log.e("OrderDetailActivity", "UpdateOrder, statusPayment : "+statusPayment);
    }





    private void readReport(){

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        final String currentDate = sdf.format(new Date());

        Log.e("OrderDetailActivity", "ResponseReadReportOrderCompleted: "+statusPayment);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.READ_REPORT_URL + sellerId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        progressBar.setVisibility(View.GONE);
                        Log.e("OrderDetailActivity", "ResponseReadReport: "+response);

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                if (obj.getInt("status") == 0) {
                                    //getting the user from the response
                                    JSONObject reportJson = obj.getJSONObject("report");

                                    Report report = new Report(
                                            reportJson.getInt("report_id"),
                                            reportJson.getInt("income_total"),
                                            reportJson.getString("month"),
                                            reportJson.getInt("month_buyer_amount"),
                                            reportJson.getInt("month_income"),
                                            reportJson.getInt("month_salary")
                                    );

                                    reportId = report.getReportId();
                                    incomeTotal = report.getIncomeTotal();
                                    month = report.getMonth();
                                    monthBuyerAmount = report.getMonthBuyerAmount();
                                    monthIncome = report.getMonthIncome();
                                    monthSalary = report.getMonthSalary();

                                    status = obj.getInt("status");

                                    addValue(status);

                                }else {
                                    reportId = obj.getInt("report_id");
                                    status = obj.getInt("status");

                                    addValue(status);
                                }
                                //creating a new user object
//                                User user = new User(
//                                        userJson.getInt("id"),
//                                        userJson.getString("username"),
//                                        userJson.getString("email"),
//                                        userJson.getString("gender")
//                                );

//                                statusPayment = userJson.getString("payment_completed");
//                                if (status.equals("true")){
//                                    mPaymentCompleteSwitch.setChecked(true);
//                                }else {
//                                    mPaymentCompleteSwitch.setChecked(false);
//                                }

//                                getData();

//                                saveReport();
//                                Log.e("OrderDetailActivity", "GetData, status : "+status);


                                //storing the user in shared preferences
//                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                //starting the profile activity
//                                finish();
//                                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
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
                params.put("sellerIdFk", String.valueOf(sellerId));
                params.put("month", currentDate);
                params.put("orderCompleted", statusPayment);

//                params.put("income_total", "2000");
//                params.put("month", "Maret");
//                params.put("month_buyer_amount", "100");
//                params.put("month_income", "200");
//                params.put("month_salary", "150");

//                params.put("orderId", String.valueOf(orderId));
//                params.put("paymentCompleted", paymentCompleted);

                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void addValue(int status){
        if (status == 0) {
            incomeTotal = incomeTotal + orderPrice;
            monthBuyerAmount = monthBuyerAmount + 1;
            monthIncome = monthIncome + orderPrice;
            monthSalary = 0;

            updateReport();
        }else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            final String currentDate = sdf.format(new Date());

            incomeTotal = orderPrice;
            month = currentDate;
            monthBuyerAmount = 1;
            monthIncome = orderPrice;
            monthSalary = 0;

            updateReport();
        }
    }

    private void updateReport(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.UPDATE_REPORT_URL + reportId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        progressBar.setVisibility(View.GONE);
                        Log.e("OrderDetailActivity", "ResponseUpdateReport: "+response);

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
//                                JSONObject userJson = obj.getJSONObject("status");

                                orderComplete(orderId);

                                Toast.makeText(OrderDetailActivity.this, "Order Selesai", Toast.LENGTH_SHORT).show();

//                                if (obj.getInt("status") == )
                                //creating a new user object
//                                User user = new User(
//                                        userJson.getInt("id"),
//                                        userJson.getString("username"),
//                                        userJson.getString("email"),
//                                        userJson.getString("gender")
//                                );



//                                statusPayment = userJson.getString("payment_completed");
//                                if (status.equals("true")){
//                                    mPaymentCompleteSwitch.setChecked(true);
//                                }else {
//                                    mPaymentCompleteSwitch.setChecked(false);
//                                }

//                                getData();

//                                saveReport();
//                                Log.e("OrderDetailActivity", "GetData, status : "+status);


                                //storing the user in shared preferences
//                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                //starting the profile activity
//                                finish();
//                                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
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
//                params.put("username", username);
//                params.put("email", email);
//                params.put("password", password);
//                params.put("gender", gender);

                params.put("reportId", String.valueOf(reportId));
                params.put("incomeTotal", String.valueOf(incomeTotal));
                params.put("month", month);
                params.put("monthBuyerAmount", String.valueOf(monthBuyerAmount));
                params.put("monthIncome", String.valueOf(monthIncome));
                params.put("monthSalary", String.valueOf(monthSalary));
                params.put("orderCompleted", statusPayment);

//                params.put("orderId", String.valueOf(orderId));
//                params.put("paymentCompleted", paymentCompleted);

                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    private JsonArrayRequest getOrderDataFromServer(int orderId) {
        //Initializing ProgressBar
        //Displaying Progressbar
//        mDiscountProgressBar.setVisibility(View.VISIBLE);
//        setProgressBarIndeterminateVisibility(true);

        //JsonArrayRequest of volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Constant.READ_ORDER_URL_ALL + orderId,
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
        requestQueue.add(getOrderDataFromServer(orderId));
    }

    //This method will parse json data
    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            //Creating the superhero object
            Order order = new Order();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);

                //Adding data to the superhero object
//                discount.setDiscountId(json.getInt(Constant.TAG_DISCOUNT_ID));
//                discount.setTotal(json.getInt(Constant.TAG_TOTAL));
//                discount.setDiscount(json.getInt(Constant.TAG_DISCOUNT));

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
            orders.add(order);

            if (imageId != -1) {
                imageRead();
            }else {
                itemRead();
            }
        }
//        viewOrder();
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
                        Log.e("OrderDetailActivity", "response : "+response);

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
