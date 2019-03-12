package com.mustika.weddingcardseller.weddingcardseller.ui;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.anton46.stepsview.StepsView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mustika.weddingcardseller.weddingcardseller.R;

import com.mustika.weddingcardseller.weddingcardseller.adapter.CheckoutAdapter;
import com.mustika.weddingcardseller.weddingcardseller.adapter.GalleryAdapter;
import com.mustika.weddingcardseller.weddingcardseller.autentikasi.LoginActivity;
import com.mustika.weddingcardseller.weddingcardseller.helper.Constant;
import com.mustika.weddingcardseller.weddingcardseller.helper.PDFInvoiceGenerate;
import com.mustika.weddingcardseller.weddingcardseller.helper.SharedPrefManager;
import com.mustika.weddingcardseller.weddingcardseller.helper.VolleyMultipartRequest;
import com.mustika.weddingcardseller.weddingcardseller.helper.VolleySingleton;
import com.mustika.weddingcardseller.weddingcardseller.model.AddOn;
import com.mustika.weddingcardseller.weddingcardseller.model.Discount;
import com.mustika.weddingcardseller.weddingcardseller.model.Item;
import com.mustika.weddingcardseller.weddingcardseller.model.Order;
import com.mustika.weddingcardseller.weddingcardseller.model.Store;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static android.app.Activity.RESULT_OK;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.addon;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.addonId;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.addonName;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.addonPrice;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.addonPriceQuantity;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.bitmap;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.dayAndDate;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.discount;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.discountId;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.discountList;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.emailO;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.extension;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.fatherNameM;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.fatherNameW;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.finalPrice;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.fullNameM;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.fullNameO;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.fullNameW;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.isAddOn;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.isCustomDesign;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.itemId;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.itemName;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.itemPrice;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.itemPriceQuantity;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.message;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.motherNameM;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.motherNameW;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.nickNameM;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.nickNameO;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.nickNameW;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.parentAddressM;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.parentAddressW;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.place;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.souvenirId;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.souvenirName;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.souvenirPrice;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.souvenirPriceQuantity;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.souvenirQuantity;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.subTotalPrice;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.taxPrice;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.telephoneO;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.time;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.totalPriceQuantity;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.weddingCardQuantity;
import static com.mustika.weddingcardseller.weddingcardseller.ui.TotalFragment.storeList;
import static com.mustika.weddingcardseller.weddingcardseller.ui.TotalFragment.totalView;


public class OrderStepActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Button mNextBtn,mFinishBtn,mBackBtn;
    private View[] indicators;
    private int page;
    private ImageView zero,one,two, three;

    public static Bitmap bitmap;
    public static String extension;

    StepsView mStepsView;
    private final String[] views = {"Formulir", "Diskon", "Add On", "Total"};
    Order order;
    public static String fullNameM, nickNameM, fatherNameM, motherNameM, parentAddressM,
    fullNameW, nickNameW, fatherNameW, motherNameW, parentAddressW, dayAndDate, time, place,
    latitude, longtitude, fullNameO, nickNameO, emailO, telephoneO, message;
    public static int itemId, souvenirId, addonId, imageId, discountId, weddingCardQuantity, souvenirQuantity, discount;
    public static String itemName, itemDescription, itemImageUrl, souvenirName, addon;
    public static String addonName = "Website Pernikahan";
    public static int itemPrice, souvenirPrice, addonPrice, itemPriceQuantity, souvenirPriceQuantity,
            addonPriceQuantity, totalPriceQuantity, subTotalPrice, taxPrice, finalPrice;
    private int buyerAmount;
    public static boolean isAddOn, isCustomDesign;
    public static List<Discount> discountList;

    private PDFInvoiceGenerate pdfInvoiceGenerate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_step);

        order = new Order();
        discountList = new ArrayList<>();

        pdfInvoiceGenerate = new PDFInvoiceGenerate();

        mStepsView = (StepsView) findViewById(R.id.stepsView);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        mNextBtn = (Button) findViewById(R.id.intro_btn_next);
        mFinishBtn = (Button) findViewById(R.id.intro_btn_finish);
        mBackBtn = (Button) findViewById(R.id.intro_btn_back);
        zero = (ImageView) findViewById(R.id.intro_indicator_0);
        one = (ImageView) findViewById(R.id.intro_indicator_1);
        two = (ImageView) findViewById(R.id.intro_indicator_2);
        three = (ImageView) findViewById(R.id.intro_indicator_3);

        indicators = new ImageView[]{zero, one, two, three};
        updateIndicators(page);
        final int color1 = ContextCompat.getColor(this, R.color.white);
        final int color2 = ContextCompat.getColor(this, R.color.white);
        final int color3 = ContextCompat.getColor(this, R.color.white);
        final int color4 = ContextCompat.getColor(this, R.color.white);
        final int[] colorList = new int[]{color1, color2, color3, color4};
        final ArgbEvaluator evaluator = new ArgbEvaluator();;
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int colorUpdate = (Integer) evaluator.evaluate(positionOffset, colorList[position], colorList[position == 3 ? position : position + 1]);
                viewPager.setBackgroundColor(colorUpdate);
            }

            @Override
            public void onPageSelected(int position) {
                page = position;
                updateIndicators(page);
                switch (position) {
                    case 0:
                        viewPager.setBackgroundColor(color1);
//                        String fullNameM = fullName;
                        Log.e("OrderStepActivity", "fullNameM : "+fullNameM+" : 0");
                        break;
                    case 1:
                        viewPager.setBackgroundColor(color2);
//                        String fullNameM2 = fullName;
                        Log.e("OrderStepActivity", "fullNameM2 : "+fullNameM+" : 1, discountSize : "+discountList.size());
                        break;
                    case 2:
                        viewPager.setBackgroundColor(color3);
//                        String fullNameM3 = fullName;
                        Log.e("OrderStepActivity", "fullNameM3 : "+fullNameM+", fullNameO :"+fullNameO+" " +
                                ": 2, discountSize : "+discountList.size());
                        break;
                    case 3:
                        viewPager.setBackgroundColor(color4);
//                        String fullNameM3 = fullName;
                        Log.e("OrderStepActivity", "fullNameM3 : "+fullNameM+", fullNameO :"+fullNameO+" " +
                                ": 2, discountSize : "+discountList.size());
                        break;
                }
                mNextBtn.setVisibility(position == 3 ? View.GONE : View.VISIBLE);
                mBackBtn.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
                mFinishBtn.setVisibility(position == 3 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void finishOnClick(View v){
        //  settingHelper.setOnboard(false);
//        sellerRead();

        Log.e("OrderStepActivity", "bitmap :"+bitmap);

        if (isCustomDesign) {
            if (bitmap != null) {
                uploadBitmap(bitmap);
            }else {
                Toast.makeText(this, "Kustom Desain belum di pilih", Toast.LENGTH_SHORT).show();
                return;
            }

        }else {
            imageId = -1;
            sellerRead();
        }

        Toast.makeText(this, "finish", Toast.LENGTH_SHORT).show();
        Log.e("OrderStepActivity", "Finish");
    }

    private void createInvoice() {
        String username = SharedPrefManager.getInstance(this).getSeller().getUsername();
        String emailSeller = SharedPrefManager.getInstance(this).getSeller().getEmail();
        String telephoneSeller = SharedPrefManager.getInstance(this).getSeller().getTelephone();
        String citySeller = SharedPrefManager.getInstance(this).getSeller().getCity();


        String sellerAddress = citySeller;
//
        String orderName = fullNameO;
        String orderEmail = emailO;
        String orderPhone = telephoneO;

//
        String uidSubstr = username.substring(2);
        Random rand = new Random();
        int n = rand.nextInt(500) + 1;
        String orderNumber = uidSubstr + "N" + buyerAmount + "R" + n;
        Log.e("OrderStepActivity", "orderNumber : "+orderNumber);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = sdf.format(date);

        String sellerName = username;
        String sellerPhone = telephoneSeller;
        String paymentTerms = "CASH";

//
        String productName = itemName;
        String quantity = String.valueOf(weddingCardQuantity);
        String rate = String.valueOf(itemPrice);
        String amount = String.valueOf(itemPriceQuantity);
        String discount = String.valueOf(OrderStepActivity.discount);
        String subTotal = String.valueOf(subTotalPrice);
        String tax = String.valueOf(taxPrice);
        String grandTotal = String.valueOf(finalPrice);

        if (isCustomDesign){
            productName = productName+" - Custom Design";
        }

        if (souvenirId != -1){
            productName = productName+"\n"+souvenirName;
            quantity = quantity+"\n"+souvenirQuantity;
            rate = rate+"\n"+souvenirPrice;
            amount = amount+"\n"+souvenirPriceQuantity;
        }

        if (addonId != -1){
            productName = productName+"\n"+addonName;
            quantity = quantity+"\n"+"1";
            rate = rate+"\n"+addonPrice;
            amount = amount+"\n"+addonPriceQuantity;
        }

        pdfInvoiceGenerate.createPDF(this, sellerAddress, orderName, orderEmail, orderPhone,
                orderNumber, currentDate, sellerName, sellerPhone, paymentTerms,
                productName, quantity, rate, amount, discount, subTotal, tax, grandTotal);

//        Log.e("BUYERDetail", "totaPrice:" + totalPrice);
//        pdfInvoiceGenerate.createPDF(this, fullNameO, emailO, telephoneO, username, emailSeller,
//                telephoneSeller, citySeller, itemName, String.valueOf(itemPrice), String.valueOf(weddingCardQuantity),
//                String.valueOf(itemPriceQuantity), String.valueOf(subTotalPrice), String.valueOf(discount), String.valueOf(finalPrice), orderNumber);

        String mPath2 = Environment.getExternalStorageDirectory().toString()
                + Constant.PATH_ORDER_INVOICE //PATH_PRODUCT_REPORT="/SIAS/REPORT_PRODUCT/"
                + orderNumber + ".pdf";

//            String email = "firebase.david@gmail.com";
        File pdfFile = new File(mPath2);

//        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
//        emailIntent.setType("application/pdf");
//        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{emailO});
//        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "INVOICE COUNTAINER ORDER ");
//        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Berikut");
//        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(pdfFile));
//        startActivity(Intent.createChooser(emailIntent, "Send mail..."));

    }




    /*
    * The method is taking Bitmap as an argument
    * then it will return the byte[] array for the given bitmap
    * and we will send this array to the server
    * here we are using PNG Compression with 80% quality
    * you can give quality between 0 to 100
    * 0 means worse quality
    * 100 means best quality
    * */
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void uploadBitmap(final Bitmap bitmap) {

        //getting the tag from the edittext
//        final String tags = editTextTags.getText().toString().trim();
        final String tags = "tags2";

        final String[] message = new String[1];
        final int[] imageId = new int[1];

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Constant.UPLOAD_URL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            Log.e("MainActivity", "message :"+obj.getString("message")+", image_id :"+obj.getInt("image_id"));
                            message[0] = obj.getString("message");
                            imageId[0] = obj.getInt("image_id");
                            viewResponse(message[0], imageId[0]);
                            sellerRead();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//                        Log.e("MainActivity", "message :"+message[0]+", image_id :"+imageId[0]);


//                        try {
//                            JSONObject obj = new JSONObject(new String(response.data));
//
//
//                            //converting response to json object
////                            JSONObject obj = new JSONObject(response);
//
//                            //if no error in response
//                            if (!obj.getBoolean("error")) {
//
//
//                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
//
//                                //getting the user from the response
//                                JSONObject imageJson = obj.getJSONObject("images");
//
//                                imageId = imageJson.getInt("image_id");
//
//                                Log.e("OrderStepActivity", "imageId : "+imageId);
//                            }
//
//
//                            Toast.makeText(OrderStepActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OrderStepActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
            * If you want to add more parameters with the image
            * you can do it here
            * here we have only one parameter with the image
            * which is tags
            * */
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("tags", tags);
//                return params;
//            }

            /*
            * Here we are passing image by renaming it with a unique name
            * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(imagename + extension, getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    private void viewResponse(String message, int id){
        imageId = id;
//        sellerRead();
        Log.e("MainActivity", "message :"+message+", image_id :"+id);
    }


    private void order() {

        Log.e("OrderStepActivity", "order");
//        final String username = editTextUsername.getText().toString().trim();
//        final String email = editTextEmail.getText().toString().trim();
//        final String password = editTextPassword.getText().toString().trim();
//
//        final String gender = ((RadioButton) findViewById(radioGroupGender.getCheckedRadioButtonId())).getText().toString();
//
//        //first we will do the validations
//
//        if (TextUtils.isEmpty(username)) {
//            editTextUsername.setError("Please enter username");
//            editTextUsername.requestFocus();
//            return;
//        }
//
//        if (TextUtils.isEmpty(email)) {
//            editTextEmail.setError("Please enter your email");
//            editTextEmail.requestFocus();
//            return;
//        }

//        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            editTextEmail.setError("Enter a valid email");
//            editTextEmail.requestFocus();
//            return;
//        }
//
//        if (TextUtils.isEmpty(password)) {
//            editTextPassword.setError("Enter a password");
//            editTextPassword.requestFocus();
//            return;
//        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        final String currentDate = sdf.format(new Date());

        final int sellerId = SharedPrefManager.getInstance(this).getSeller().getSellerId();
//        final int buyerAmount = SharedPrefManager.getInstance(this).getSeller().getBuyerAmount();

//        imageId = 10;

//        coordinate = "1100222, 10009928";

        latitude = "11022111";
        longtitude = "011129922";

        if(!isAddOn) {
            addonId = -1;
        }

//        final int sellerId = 2;

        Log.e("OrderStepActivity", "sellerId : "+sellerId);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        progressBar.setVisibility(View.GONE);
                        Log.e("OrderStepACtivity", "responseOrder : "+response);

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

//                                createInvoice();

                                SharedPrefManager.getInstance(getApplicationContext()).deleteItem();
                                SharedPrefManager.getInstance(getApplicationContext()).deleteSouvenir();

                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();

                                //getting the user from the response
//                                JSONObject userJson = obj.getJSONObject("user");

                                //creating a new user object
//                                User user = new User(
//                                        userJson.getInt("id"),
//                                        userJson.getString("username"),
//                                        userJson.getString("email"),
//                                        userJson.getString("gender")
//                                );


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
//
                params.put("fullNameM", fullNameM);
                params.put("nickNameM", nickNameM);
                params.put("fatherNameM", fatherNameM);
                params.put("motherNameM", motherNameM);
                params.put("parentAddressM", parentAddressM);

                params.put("fullNameW", fullNameW);
                params.put("nickNameW", nickNameW);
                params.put("fatherNameW", fatherNameW);
                params.put("motherNameW", motherNameW);
                params.put("parentAddressW", parentAddressW);

                params.put("dayAndDate", dayAndDate);
                params.put("weddingTime", time);
                params.put("place", place);
                params.put("latitude", latitude);
                params.put("longtitude", longtitude);
                params.put("weddingCardQuantity", String.valueOf(weddingCardQuantity));
                params.put("paymentCompleted", "false");
                params.put("orderTime", currentDate);
                params.put("orderPrice", String.valueOf(finalPrice));

                params.put("fullNameO", fullNameO);
                params.put("nickNameO", nickNameO);
                params.put("emailO", emailO);
                params.put("telephoneO", telephoneO);
                params.put("message", message);

                params.put("souvenir_quantity", String.valueOf(souvenirQuantity));
                params.put("souvenirIdFk", String.valueOf(souvenirId));
                params.put("addonIdFk", String.valueOf(addonId));
                params.put("imageIdFk", String.valueOf(imageId));
                params.put("itemIdFk", String.valueOf(itemId));
                params.put("discountIdFk", String.valueOf(discountId));
                params.put("sellerIdFk", String.valueOf(sellerId));

//                params.put("fullNameM", "David Naaa");
//                params.put("nickNameM", "David");
//                params.put("fatherNameM", "aaaaaa");
//                params.put("motherNameM", "bbbbbbbbb");
//                params.put("parentAddressM", "asassaasassasss");
//
//                params.put("fullNameW", "Rita Kaaa");
//                params.put("nickNameW", "Rita");
//                params.put("fatherNameW", "sssaaaaa");
//                params.put("motherNameW", "ddddddssss");
//                params.put("parentAddressW", "csacassssssasdsad");
//
//                params.put("dayAndDate", "Rabu, 09-04-2017");
//                params.put("time", "08.30");
//                params.put("place", "Yogyakarta");
//                params.put("weddingCardQuantity", String.valueOf(200));
//                params.put("paymentCompleted", "false");
//                params.put("orderTime", currentDate);
//
//                params.put("fullNameO", "Ruth Kaaaa");
//                params.put("nickNameO", "Ruth");
//                params.put("emailO", "ruth@gmail.com");
//                params.put("telephoneO", "08566111221");
//                params.put("itemIdFk", "1");
//                params.put("discountIdFk", "1");
//                params.put("sellerIdFk", "1");
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void sellerRead(){

        final int sellerId = SharedPrefManager.getInstance(this).getSeller().getSellerId();
////        int buyerAmount = SharedPrefManager.getInstance(this).getSeller().getBuyerAmount();
//
        Log.e("OrderStepACtivity", "sellerId(sellerRead) : "+sellerId);
//
//
////        buyerAmount += 1;
//
////        final int finalBuyerAmount = buyerAmount;
//
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.READ_SELLER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        progressBar.setVisibility(View.GONE);
                        Log.e("OrderStepACtivity", "responseSellerRead : "+response);

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject("seller");

                                buyerAmount = userJson.getInt("buyer_amount");

                                Log.e("OrderStepActivity", "buyerAmount : "+buyerAmount);


                                updateBuyerAmount();

                                //creating a new user object
//                                User user = new User(
//                                        userJson.getInt("id"),
//                                        userJson.getString("username"),
//                                        userJson.getString("email"),
//                                        userJson.getString("gender")
//                                );

//                                order();


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
                params.put("sellerId", String.valueOf(sellerId));
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    private void updateBuyerAmount(){

        final int sellerId = SharedPrefManager.getInstance(this).getSeller().getSellerId();
//        int buyerAmount = SharedPrefManager.getInstance(this).getSeller().getBuyerAmount();

        buyerAmount = buyerAmount + 1;

        final int finalBuyerAmount = buyerAmount;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.URL_BUYER_AMOUNT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        progressBar.setVisibility(View.GONE);
                        Log.e("OrderStepACtivity", "responseUpdateBuyerAmount : "+response);

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
//                                JSONObject userJson = obj.getJSONObject("user");

                                //creating a new user object
//                                User user = new User(
//                                        userJson.getInt("id"),
//                                        userJson.getString("username"),
//                                        userJson.getString("email"),
//                                        userJson.getString("gender")
//                                );

                                order();


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

                params.put("sellerId", String.valueOf(sellerId));
                params.put("buyerAmount", String.valueOf(finalBuyerAmount));

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }



    public void nextPage(View view){
        viewPager.setCurrentItem(page+1,true);
    }
    public void backPage(View view){
        viewPager.setCurrentItem(page-1,true);
    }

    private void updateIndicators(int position) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(
                    i == position ? R.drawable.indicator_selected : R.drawable.indicator_unselected
            );

            if (position == 1){
//                String fullNameM = fullName;
//                Log.e("OrderStepActivity", "fullNameM : "+fullNameM+" : 1");
            }else if (position == 2){
//                String fullNameM = fullName;
//                Log.e("OrderStepActivity", "fullNameM : "+fullNameM+" : 2");
            }
        }
        mStepsView.setCompletedPosition(page)
                .setLabels(views)
                .setBarColorIndicator(getResources().getColor(R.color.colorAccent))
                .setProgressColorIndicator(getResources().getColor(R.color.authui_colorPrimary))
                .setLabelColorIndicator(getResources().getColor(R.color.colorAccent))
                .drawView();
        Log.e("CheckActivity", "position : " + page);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FormFragment(), "ONE");
        adapter.addFragment(new DiscountFragment(), "TWO");
        adapter.addFragment(new AddOnFragment(), "THREE");
        adapter.addFragment(new TotalFragment(), "FOUR");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}




//FORM FRAGMENT


@SuppressLint("ValidFragment")
class FormFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener, View.OnClickListener {


    private EditText mFullNameMEditText, mNickNameMEditText, mFatherFullNameMEditText, mMotherFullNameMEditText,
            mParentAddressMEditText, mFullNameWEditText, mNickNameWEditText, mFatherFullNameWEditText, mMotherFullNameWEditText,
            mParentAddressWEditText, mPlaceEditText,
            mFullNameOEditText, mNickNameOEditText, mEmailOEditText, mTelephoneOEditText, mMessageEditText;
    public static EditText mDayAndDateEditText, mTimeEditText;

    private Button mImageUploadButton;
    private ImageView mDayAndDateImageView, mTimeImageView;

    private CheckBox mCustomDesignCheckbox;
    private LinearLayout mCustomDesignLayout, mSouvenirLayout;
    private TextView mFormTextView;
//    String name, description, imageUrl;
//    int price, total;
//    public String fullNameM;

    private MapFragment mapFragment;

    private EditText mLokasiEditText;

    //ImageView to display image selected
    ImageView imageView;

    //edittext for getting the tags input
    EditText editTextTags;

//    private String extension;

    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    protected LocationRequest mLocationRequest;
    protected double latitude, longtitude;
    protected CameraPosition PLACE = null;
    public static LatLng latLng;
    LocationManager mLocationManager;
    GoogleMap gMap;
    Marker currLocationMarker;

    boolean mapReady=false;
    private static final int REQUEST_FINE_RESULT = 202;

    public static String LATITUDE = "";

    public static String LONGTITUDE = "";

    private static final int RC_PHOTO_PICKER = 100;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_form, container, false);

        mFullNameMEditText = (EditText) rootView.findViewById(R.id.full_name_m_edit_text);
        mNickNameMEditText = (EditText) rootView.findViewById(R.id.nick_name_m_edit_text);
        mFatherFullNameMEditText = (EditText) rootView.findViewById(R.id.father_full_name_m_edit_text);
        mMotherFullNameMEditText = (EditText) rootView.findViewById(R.id.mother_full_name_m_edit_text);
        mParentAddressMEditText = (EditText) rootView.findViewById(R.id.parent_address_m_edit_text);
        mFullNameWEditText = (EditText) rootView.findViewById(R.id.full_name_w_edit_text);
        mNickNameWEditText = (EditText) rootView.findViewById(R.id.nick_name_w_edit_text);
        mFatherFullNameWEditText = (EditText) rootView.findViewById(R.id.father_full_name_w_edit_text);
        mMotherFullNameWEditText = (EditText) rootView.findViewById(R.id.mother_full_name_w_edit_text);
        mParentAddressWEditText = (EditText) rootView.findViewById(R.id.parent_address_w_edit_text);
        mDayAndDateEditText = (EditText) rootView.findViewById(R.id.day_and_date_edit_text);
        mTimeEditText = (EditText) rootView.findViewById(R.id.time_edit_text);
        mPlaceEditText = (EditText) rootView.findViewById(R.id.place_edit_text);
        mFullNameOEditText = (EditText) rootView.findViewById(R.id.full_name_o_edit_text);
        mNickNameOEditText = (EditText) rootView.findViewById(R.id.nick_name_o_edit_text);
        mEmailOEditText = (EditText) rootView.findViewById(R.id.email_o_edit_text);
        mTelephoneOEditText = (EditText) rootView.findViewById(R.id.telephone_o_edit_text);
        mMessageEditText = (EditText) rootView.findViewById(R.id.message_edit_text);
        mLokasiEditText = (EditText) rootView.findViewById(R.id.edit_schedule_location);
        mCustomDesignCheckbox = (CheckBox) rootView.findViewById(R.id.custom_design_checkbox);
        mCustomDesignLayout = (LinearLayout) rootView.findViewById(R.id.custom_design_layout);
        mSouvenirLayout = (LinearLayout) rootView.findViewById(R.id.souvenir_layout);
        mImageUploadButton = (Button) rootView.findViewById(R.id.buttonUploadImage);
        mDayAndDateImageView = (ImageView) rootView.findViewById(R.id.day_and_date_image_view);
        mTimeImageView = (ImageView) rootView.findViewById(R.id.time_image_view);

        mapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map);
//        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        mFormTextView = (TextView) rootView.findViewById(R.id.form_text_view);

        //initializing views
        imageView = (ImageView) rootView.findViewById(R.id.imageView);
//        editTextTags = (EditText) rootView.findViewById(R.id.editTextTags);

        //checking the permission
        //if the permission is not given we will open setting to add permission
        //else app will not open
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getActivity().getPackageName()));
            getActivity().finish();
            startActivity(intent);
        }


        itemId = SharedPrefManager.getInstance(getActivity()).getItem().getStoreId();
        itemName = SharedPrefManager.getInstance(getActivity()).getItem().getStoreName();
        itemPrice = SharedPrefManager.getInstance(getActivity()).getItem().getStorePrice();
        weddingCardQuantity = SharedPrefManager.getInstance(getActivity()).getItem().getStoreTotal();

        if (!SharedPrefManager.getInstance(getActivity()).isSouvenir()) {
            souvenirId = -1;
            souvenirQuantity = 0;
            mSouvenirLayout.setVisibility(View.GONE);
        }else {
            souvenirId = SharedPrefManager.getInstance(getActivity()).getSouvenir().getStoreId();
            souvenirName = SharedPrefManager.getInstance(getActivity()).getSouvenir().getStoreName();
            souvenirPrice = SharedPrefManager.getInstance(getActivity()).getSouvenir().getStorePrice();
            souvenirQuantity = SharedPrefManager.getInstance(getActivity()).getSouvenir().getStoreTotal();
            mSouvenirLayout.setVisibility(View.VISIBLE);
        }

        Log.e("OrderStepActivity", "itemId:"+itemId+", itemName:"+itemName+", itemPrice:"+itemPrice+
        ", itemQuanity:"+weddingCardQuantity+", souvenirId:"+souvenirId+", souvenirName:"+souvenirName+
        ", souvenirPrice:"+souvenirPrice+", souvenirQuantity:"+souvenirQuantity);


//        if (getActivity().getIntent() != null){
//            itemId = getActivity().getIntent().getExtras().getInt(Constant.TAG_ITEM_ID);
//            name = getActivity().getIntent().getExtras().getString(Constant.TAG_NAME);
////            description = getActivity().getIntent().getExtras().getString(Constant.TAG_DESCRIPTION);
//            price = getActivity().getIntent().getExtras().getInt(Constant.TAG_PRICE);
////            imageUrl = getActivity().getIntent().getExtras().getString(Constant.TAG_IMAGE_URL);
//            weddingCardQuantity = getActivity().getIntent().getExtras().getInt("weddingcardquantity");
//
////            mFormTextView.setText(name +"\n"+ description+"\n"+ String.valueOf(price)+"\n"+ imageUrl);
//        }

        mImageUploadButton.setOnClickListener(this);
        mCustomDesignCheckbox.setOnClickListener(this);
        mDayAndDateImageView.setOnClickListener(this);
        mTimeImageView.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.buttonUploadImage){

            //if the tags edittext is empty
            //we will throw input error
//                if (editTextTags.getText().toString().trim().isEmpty()) {
//                    editTextTags.setError("Enter tags first");
//                    editTextTags.requestFocus();
//                    return;
//                }
            //if everything is ok we will open image chooser
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, RC_PHOTO_PICKER);
//                Intent i = new Intent(Intent.ACTION_PICK);
//                i.setType("image/*");
//                i.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
//                startActivityForResult(Intent.createChooser(i, "Complete action"), RC_PHOTO_PICKER);
        }else if (id ==  R.id.custom_design_checkbox){
            if (mCustomDesignCheckbox.isChecked()){
                isCustomDesign = true;
                mCustomDesignLayout.setVisibility(View.VISIBLE);
            }else {
                isCustomDesign = false;
                mCustomDesignLayout.setVisibility(View.GONE);
            }
        }else if (id == R.id.day_and_date_image_view){
            DatePickerFragment mDatePicker = new DatePickerFragment();
            mDatePicker.show(getActivity().getFragmentManager(), "Select date");
        }else if (id == R.id.time_image_view){
            TimePicker mTimePicker = new TimePicker();
            mTimePicker.show(getActivity().getFragmentManager(), "Select time");
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        fullNameM = mFullNameMEditText.getText().toString().trim();
        nickNameM = mNickNameMEditText.getText().toString().trim();
        fatherNameM = mFatherFullNameMEditText.getText().toString().trim();
        motherNameM = mMotherFullNameMEditText.getText().toString().trim();
        parentAddressM = mParentAddressMEditText.getText().toString().trim();

        fullNameW = mFullNameWEditText.getText().toString().trim();
        nickNameW = mNickNameWEditText.getText().toString().trim();
        fatherNameW = mFatherFullNameWEditText.getText().toString().trim();
        motherNameW = mMotherFullNameWEditText.getText().toString().trim();
        parentAddressW = mParentAddressWEditText.getText().toString().trim();

        dayAndDate = mDayAndDateEditText.getText().toString().trim();
        time = mTimeEditText.getText().toString().trim();
        place = mPlaceEditText.getText().toString().trim();

        if (TextUtils.isEmpty(mLokasiEditText.getText().toString().trim())){
            OrderStepActivity.latitude = "0";
            OrderStepActivity.longtitude = "0";
        }

//        weddingCardQuantity = Integer.parseInt(mWeddingCardQuantityEditText.getText().toString());

        fullNameO = mFullNameOEditText.getText().toString().trim();
        nickNameO = mNickNameOEditText.getText().toString().trim();
        emailO = mEmailOEditText.getText().toString().trim();
        telephoneO = mTelephoneOEditText.getText().toString().trim();

        if (souvenirId != -1) {
            message = mMessageEditText.getText().toString().trim();
        }else {
            message = "-";
        }

        Log.e("FormFragmentOnPause", "fullNameM : "+fullNameM+" : 1");
        Order order = new Order();
        order.setFullNameM(fullNameM);

        if (mGoogleApiClient != null) {
            if (mGoogleApiClient.isConnected()) {
                stopLocationUpdates();
                mGoogleApiClient.disconnect();
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK && data != null) {

            //getting the image Uri
            Uri imageUri = data.getData();
//            imageUri.getLastPathSegment();


            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                extension = filePath.substring(filePath.lastIndexOf(".")); // Extension with dot .jpg, .png
                Log.e("MainActivity", "extension :"+extension);//", //scheme"+imageUri.getScheme());

            }
            cursor.close();


//            String extension = imageUri.getPath().replaceFirst("^.*/[^/]*(\\.[^\\./]*|)$", "$1");

//            getContentResolver().getType(imageUri);

//            String type = getContentResolver().getType(imageUri);
//            type.substring(type.lastIndexOf(".")); // Extension with dot .jpg, .png

//            Log.e("MainActivity", "extension :"+ type);//", //scheme"+imageUri.getScheme());
            try {
                //getting bitmap object from uri
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);

                //displaying selected image to imageview
                imageView.setImageBitmap(bitmap);

                //calling the method uploadBitmap to upload image
//                uploadBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    /*
//    * The method is taking Bitmap as an argument
//    * then it will return the byte[] array for the given bitmap
//    * and we will send this array to the server
//    * here we are using PNG Compression with 80% quality
//    * you can give quality between 0 to 100
//    * 0 means worse quality
//    * 100 means best quality
//    * */
//    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
//        return byteArrayOutputStream.toByteArray();
//    }
//
//    private void uploadBitmap(final Bitmap bitmap) {
//
//        //getting the tag from the edittext
//        final String tags = editTextTags.getText().toString().trim();
//
//        //our custom volley request
//        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Constant.UPLOAD_URL,
//                new Response.Listener<NetworkResponse>() {
//                    @Override
//                    public void onResponse(NetworkResponse response) {
//                        try {
//                            JSONObject obj = new JSONObject(new String(response.data));
//                            Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }) {
//
//            /*
//            * If you want to add more parameters with the image
//            * you can do it here
//            * here we have only one parameter with the image
//            * which is tags
//            * */
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("tags", tags);
//                return params;
//            }
//
//            /*
//            * Here we are passing image by renaming it with a unique name
//            * */
//            @Override
//            protected Map<String, DataPart> getByteData() {
//                Map<String, DataPart> params = new HashMap<>();
//                long imagename = System.currentTimeMillis();
//                params.put("pic", new DataPart(imagename + extension, getFileDataFromDrawable(bitmap)));
//                return params;
//            }
//        };
//
//        //adding the request to volley
//        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //MapsInitializer.initialize(getApplicationContext());
        mapReady=true;
        gMap = googleMap;

        Log.d("MapActivity", "onMapReady");


        if (gMap != null && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            // return;
            accessSetup();
        }else if (gMap != null){
            setup();
        }
//        else {
//
//            flyTo(YOGYAKARTA);
//
//            gMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
//                @Override
//                public void onMapLongClick(LatLng latLng) {
//                    gMap.clear();
//                    gMap.addMarker(new MarkerOptions().position(latLng).title("Custom location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
//                    Toast.makeText(MapActivity.this, latLng.latitude + " : " + latLng.longitude, Toast.LENGTH_SHORT).show();
//                    LATITUDE = "" + latLng.latitude;
//                    LONGTITUDE = "" + latLng.longitude;
//                    mLokasiEditText.setText(LATITUDE + "," + LONGTITUDE);
//                }
//            });
//        }
    }

    private void accessSetup() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
                setup();
                Log.d("MapActivity", "Not Granted");
                return;
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Toast.makeText(getActivity(), "Butuh Akses GPS", Toast.LENGTH_SHORT).show();
                }
                Log.d("Map Activity", "Granted");
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_RESULT);
            }
        } else {
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_FINE_RESULT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setup();
                return;
            } else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Log.d("MapActivity", "finishh");
                getActivity().finish();
            }
//        }else if
// (requestCode == REQUEST_COARSE_RESULT){
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                return;
//            }else {
//                finish();
//            }
        }
    }

    @SuppressLint("MissingPermission")
    private void setup() {
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        mLastLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //return;
            Log.d("MapActivity", "lastLocation");
            buildGoogleApiClient();
            mGoogleApiClient.connect();
            gMap.setMyLocationEnabled(true);
            //   buildGoogleApiClient();
//            gMap = googleMap;
//            Log.e(TAG, "onMapReady");
//            googleMap.setMyLocationEnabled(true);
//            gMap.clear();  //clears all Markers and Polylines

        }
//        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Hidupkan GPS")
                    .setCancelable(false)
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            getActivity().finish();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

//        mGoogleApiClient.connect();

        Log.d("MapActivity", "buidGoogleApiClient");
    }

    private void flyTo(CameraPosition target)
    {
        gMap.moveCamera(CameraUpdateFactory.newCameraPosition(target));
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(800);
        //   mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setSmallestDisplacement(0.45F);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onReq uestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("MapActivity", "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("MapActivity", "Connection failed: ConnectionRewsult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    @Override
    public void onLocationChanged(Location location) {
        latLng = new LatLng(location.getLatitude(), location.getLongitude()); //you already have this

        Log.d("MapActivity", "latitude :"+location.getLatitude()+", longtitude :"+location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Posisition");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        currLocationMarker = gMap.addMarker(markerOptions);

//        flyTo(YOGYAKARTA);

        if(gMap!=null){
            LatLng SYDNEY = latLng;
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16);
            gMap.animateCamera(cameraUpdate);
        }

        gMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                gMap.clear();
                gMap.addMarker(new MarkerOptions().position(latLng).title("Custom location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                Toast.makeText(getActivity(), latLng.latitude + " : " + latLng.longitude, Toast.LENGTH_SHORT).show();
                LATITUDE = "" + latLng.latitude;
                LONGTITUDE = "" + latLng.longitude;
                latitude = latLng.latitude;
                longtitude = latLng.longitude;
                mLokasiEditText.setText(LATITUDE + "," + LONGTITUDE);
            }
        });
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        public static String day;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            switch (dayOfWeek) {
                case Calendar.SUNDAY:
                    // Current day is Sunday
                    day = "Minggu";
                    break;
                case Calendar.MONDAY:
                    // Current day is Monday
                    day = "Senin";
                    break;
                case Calendar.TUESDAY:
                    day = "Selasa";
                    break;
                case Calendar.WEDNESDAY:
                    day = "Rabu";
                    break;
                case Calendar.THURSDAY:
                    day = "Kamis";
                    break;
                case Calendar.FRIDAY:
                    day = "Jumat";
                    break;
                case Calendar.SATURDAY:
                    day = "Sabtu";
                    break;
                default:
                    day = "-";
                    break;
            }
            return new DatePickerDialog(getActivity(), this, year, month, dayOfMonth);
        }

        private DatePickerDialog.OnDateSetListener
                datePickerListener = new  DatePickerDialog.OnDateSetListener()
        {
            public void onDateSet(DatePicker view, int selectedYear,
                                  int selectedMonth, int selectedDay)
            {
                SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
                Date date = new Date(selectedYear, selectedMonth, selectedDay-1);
                String dayOfWeek = simpledateformat.format(date);
                Log.e("OrderStepActivity", "dayOfWeek :"+dayOfWeek);
            }
        };

        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String monthFix;
            String dayFix;
            //Change date 8 Februari 2017 from 2017/1/8 to 2017/02/08
            if (month < 10) {
                monthFix = "0" + (month + 1);
            } else {
                monthFix = String.valueOf(month + 1);
            }
            if (dayOfMonth < 10) {
                dayFix = "0" + dayOfMonth;
            } else {
                dayFix = String.valueOf(dayOfMonth);
            }

            SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
            Date date = new Date(year, month, dayOfMonth-1);
            String dayOfWeek = simpledateformat.format(date);
            switch (dayOfWeek) {
                case "Sunday":
                    // Current day is Sunday
                    day = "Minggu";
                    break;
                case "Monday":
                    // Current day is Monday
                    day = "Senin";
                    break;
                case "Tuesday":
                    day = "Selasa";
                    break;
                case "Wednesday":
                    day = "Rabu";
                    break;
                case "Thursday":
                    day = "Kamis";
                    break;
                case "Friday":
                    day = "Jumat";
                    break;
                case "Saturday":
                    day = "Sabtu";
                    break;
                default:
                    day = "-";
                    break;
            }
            Log.e("OrderStepActivity", "dayOfWeek :"+dayOfWeek);


            String dayAndDate = String.valueOf(year) + "/" + monthFix + "/" + dayFix;

            mDayAndDateEditText.setText("");

            mDayAndDateEditText.setText(day +", "+String.valueOf(dayOfMonth) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(year));

        }
    }

    public static class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
            mTimeEditText.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
        }
    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        fullNameM = mFullNameMEditText.getText().toString();
//        fullName = fullNameM;
//        Log.e("FormFragment", "fullNameM : "+fullNameM+" : 1");
//        Order order = new Order();
//        order.setFullNameM(fullNameM);
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        fullNameM = mFullNameMEditText.getText().toString();
//        fullName = fullNameM;
//        Log.e("FormFragment", "fullNameM : "+fullNameM+" : 1");
//        Order order = new Order();
//        order.setFullNameM(fullNameM);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        fullNameM = mFullNameMEditText.getText().toString();
//        fullName = fullNameM;
//        Log.e("FormFragment", "fullNameM : "+fullNameM+" : 1");
//        Order order = new Order();
//        order.setFullNameM(fullNameM);
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        fullNameM = mFullNameMEditText.getText().toString();
//        fullName = fullNameM;
//        Log.e("FormFragment", "fullNameM : "+fullNameM+" : 1");
//        Order order = new Order();
//        order.setFullNameM(fullNameM);
//    }
}


//DISCOUNT FRAGMENT



@SuppressLint("ValidFragment")
class DiscountFragment extends Fragment {

    TextView mDiscountTextView;
    ProgressBar mDiscountProgressBar;

    List<Discount> discounts;
    //Volley Request Queue
    private RequestQueue requestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_discount, container, false);

        mDiscountTextView = (TextView) rootView.findViewById(R.id.discount_text_view);
        mDiscountProgressBar = (ProgressBar) rootView.findViewById(R.id.discount_progress_bar);

        //Initializing our superheroes list
        discounts = new ArrayList<>();


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("DiscountFragment", "onStart");

        requestQueue = Volley.newRequestQueue(getActivity());
        getData();
    }

    //Request to get json from server we are passing an integer here
    //This integer will used to specify the page number for the request ?page = requestcount
    //This method would return a JsonArrayRequest that will be added to the request queue
    private JsonArrayRequest getDataFromServer() {
        //Initializing ProgressBar


        //Displaying Progressbar
        mDiscountProgressBar.setVisibility(View.VISIBLE);
//        setProgressBarIndeterminateVisibility(true);

        //JsonArrayRequest of volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Constant.DISCOUNT_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Calling method parseData to parse the json response
                        parseData(response);
                        //Hiding the progressbar
                        mDiscountProgressBar.setVisibility(View.GONE);

                        Log.e("DiscountFragment", "responseGetData : "+response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mDiscountProgressBar.setVisibility(View.GONE);
                        //If an error occurs that means end of the list has reached
                        Toast.makeText(getActivity(), "No More Items Available", Toast.LENGTH_SHORT).show();
                    }
                });

        //Returning the request
        return jsonArrayRequest;
    }

    //This method will get data from the web api
    private void getData() {
        //Adding the method to the queue by calling the method getDataFromServer
        requestQueue.add(getDataFromServer());
    }

    //This method will parse json data
    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            //Creating the superhero object
            Discount discount = new Discount();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);

                //Adding data to the superhero object
                discount.setDiscountId(json.getInt(Constant.TAG_DISCOUNT_ID));
                discount.setTotal(json.getInt(Constant.TAG_TOTAL));
                discount.setDiscount(json.getInt(Constant.TAG_DISCOUNT));

                Log.e("DiscountFragment", "total :"+json.getInt(Constant.TAG_TOTAL)+
                        ", discount :"+json.getInt(Constant.TAG_DISCOUNT));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the superhero object to the list
            discounts.add(discount);
        }
        viewDiscount();
    }

    private void viewDiscount() {
//        discounts.get()
        discountList = discounts;
//        mDiscountTextView.setText(String.valueOf(discountList.get(1).getDiscount()));
        Log.e("DiscountFragment", "viewDiscount");
        Log.e("DiscountFragment", "weddingCardQuantity : "+weddingCardQuantity);
        Log.e("DiscountFragment", "discountListSize : "+discountList.size());
        discount = 0;
        for (int i = discountList.size() - 1; 0 <= i; i--) {
            if (weddingCardQuantity >= discountList.get(i).getTotal()){
                discountId = discountList.get(i).getDiscountId();
                discount = discountList.get(i).getDiscount();
                mDiscountTextView.setText(String.valueOf(discountList.get(i).getDiscount()));
                Log.e("DiscountFragment", "total : "+discountList.get(i).getTotal());
                return;
            }
        }

        Log.e("DiscountFragment", "discount : "+discount);

    }

}




//ADDON FRAGMENT


@SuppressLint("ValidFragment")
class AddOnFragment extends Fragment {

    private TextView mWebPriceTextView;
    private CheckBox mAddOnCheckBox;

    //Volley Request Queue
    private RequestQueue requestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_on, container, false);

        mWebPriceTextView = (TextView) rootView.findViewById(R.id.web_price_text_view);
        mAddOnCheckBox = (CheckBox) rootView.findViewById(R.id.addon_check_box);


        if (isAddOn){
            mAddOnCheckBox.setChecked(true);
        }else {
            mAddOnCheckBox.setChecked(false);
        }
        mAddOnCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAddOnCheckBox.isChecked()){
                    isAddOn = true;
                    addon = "true";
                    storeList.clear();
                    totalView();
                    Log.e("AddOnFragment", "checked");
                }else {
                    isAddOn = false;
                    addon = "false";
                    storeList.clear();
                    totalView();
                    Log.e("AddOnFragment", "unchecked");
                }
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("DiscountFragment", "onStart");

        requestQueue = Volley.newRequestQueue(getActivity());
        getData();
    }

    //Request to get json from server we are passing an integer here
    //This integer will used to specify the page number for the request ?page = requestcount
    //This method would return a JsonArrayRequest that will be added to the request queue
    private JsonArrayRequest getDataFromServer() {
        //Initializing ProgressBar


        //Displaying Progressbar
//        mDiscountProgressBar.setVisibility(View.VISIBLE);
//        setProgressBarIndeterminateVisibility(true);

        //JsonArrayRequest of volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Constant.ADDON_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Calling method parseData to parse the json response
                        parseData(response);
                        //Hiding the progressbar
//                        mDiscountProgressBar.setVisibility(View.GONE);

                        Log.e("DiscountFragment", "responseGetData : "+response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        mDiscountProgressBar.setVisibility(View.GONE);
                        //If an error occurs that means end of the list has reached
                        Toast.makeText(getActivity(), "No More Items Available", Toast.LENGTH_SHORT).show();
                    }
                });

        //Returning the request
        return jsonArrayRequest;
    }

    //This method will get data from the web api
    private void getData() {
        //Adding the method to the queue by calling the method getDataFromServer
        requestQueue.add(getDataFromServer());
    }

    //This method will parse json data
    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            //Creating the superhero object
            AddOn addOn = new AddOn();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);

                //Adding data to the superhero object

                addonId = json.getInt(Constant.TAG_ADDON_ID);
                addonName = json.getString(Constant.TAG_NAME);
                addonPrice = json.getInt(Constant.TAG_PRICE);

                mWebPriceTextView.setText(String.valueOf(addonPrice));

//                addOn.setAddonId(json.getInt(Constant.TAG_ADDON_ID));
//                addOn.setName(json.getString(Constant.TAG_NAME));
//                addOn.setPrice(json.getInt(Constant.TAG_PRICE));

//                Log.e("DiscountFragment", "total :"+json.getInt(Constant.TAG_TOTAL)+
//                        ", discount :"+json.getInt(Constant.TAG_DISCOUNT));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the superhero object to the list
//            discounts.add(discount);
        }
//        viewDiscount();
    }

}





//TOTAL FRAGMENT




@SuppressLint("ValidFragment")
class TotalFragment extends Fragment {

    private TextView mItemNameTextView;
    private TextView mItemQuantityTextView;
    private TextView mItemRateTextView;
    private TextView mItemPriceTextView;
    private static TextView mDiscountTextView;
    private static TextView mDiscountPriceTextView;
    private static TextView mTaxPriceTextView;
    private static TextView mTotalTextView;

    //Creating a List of superheroes
    public static List<Store> storeList;

    //Creating Views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView.Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_total, container, false);

        //Initializing Views
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
//        layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);

        //Initializing our superheroes list
        storeList = new ArrayList<>();

        //initializing our adapter
        adapter = new CheckoutAdapter(storeList, getActivity());

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);


//        mItemNameTextView = (TextView) rootView.findViewById(R.id.item_name_text_view);
//        mItemQuantityTextView = (TextView) rootView.findViewById(R.id.item_quantity_text_view);
//        mItemRateTextView = (TextView) rootView.findViewById(R.id.item_rate_text_view);
//        mItemPriceTextView = (TextView) rootView.findViewById(R.id.item_price_text_view);
        mDiscountTextView = (TextView) rootView.findViewById(R.id.discount_text_view);
        mDiscountPriceTextView = (TextView) rootView.findViewById(R.id.discount_price_text_view);
        mTaxPriceTextView = (TextView) rootView.findViewById(R.id.tax_price_text_view);
        mTotalTextView = (TextView) rootView.findViewById(R.id.total_text_view);



        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        storeList.clear();
        totalView();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    public static void totalView() {
//        priceQuantity = price * weddingCardQuantity;
//        int tax = (priceQuantity * 5 / 100);
//        int pricePlusTax = priceQuantity + tax;
//        subTotalPrice = (pricePlusTax * discount / 100);
//        finalPrice = (subTotalPrice * 10 / 100);

        itemPriceQuantity = itemPrice * weddingCardQuantity;

        souvenirPriceQuantity = souvenirPrice * souvenirQuantity;
//        if (addonId != 0){
            addonPriceQuantity = addonPrice;
//        }

        totalPriceQuantity = itemPriceQuantity + souvenirPriceQuantity + addonPriceQuantity;

        int discountPrice = (totalPriceQuantity * discount / 100);
        subTotalPrice = totalPriceQuantity - discountPrice;
        taxPrice = (subTotalPrice * 10 / 100);
        finalPrice = subTotalPrice + taxPrice;


//        if (souvenirId != 0){
//            itemName = itemName + "\n" + souvenirName;
//            itemPrice = Integer.parseInt(String.valueOf(itemPrice) + "\n" + String.valueOf(souvenirPrice));
//        }

        Store store = new Store(itemName, weddingCardQuantity, itemPrice, itemPriceQuantity);
        storeList.add(store);

        if (souvenirId != -1) {
            Store store1 = new Store(souvenirName, souvenirQuantity, souvenirPrice, souvenirPriceQuantity);
            storeList.add(store1);
        }

        if (isAddOn) {
            if (addonId != -1) {
                Store store2 = new Store(addonName, 1, addonPrice, addonPriceQuantity);
                storeList.add(store2);
            }
        }


        adapter.notifyDataSetChanged();


//        mItemNameTextView.setText(itemName);
//        mItemQuantityTextView.setText(String.valueOf(weddingCardQuantity));
//        mItemRateTextView.setText(String.valueOf(itemPrice));
//        mItemPriceTextView.setText(String.valueOf(priceQuantity));
        mDiscountTextView.setText(String.valueOf(discount));
        mDiscountPriceTextView.setText(String.valueOf(discountPrice));
        mTaxPriceTextView.setText(String.valueOf(taxPrice));
        mTotalTextView.setText(String.valueOf(finalPrice));
    }

}



