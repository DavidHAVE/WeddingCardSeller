package com.mustika.weddingcardseller.weddingcardseller.autentikasi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mustika.weddingcardseller.weddingcardseller.ui.MainActivity;
import com.mustika.weddingcardseller.weddingcardseller.R;
import com.mustika.weddingcardseller.weddingcardseller.helper.Constant;
import com.mustika.weddingcardseller.weddingcardseller.helper.SharedPrefManager;
import com.mustika.weddingcardseller.weddingcardseller.helper.VolleySingleton;
import com.mustika.weddingcardseller.weddingcardseller.model.Seller;
import com.mustika.weddingcardseller.weddingcardseller.ui.SplashScreenActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUsername, editTextPassword;
    ProgressBar progressBar;
    private String username2, response2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);


        //if user presses on login
        //calling the method login
        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sellerLogin();
            }
        });

        //if user presses on not registered
//        findViewById(R.id.textViewRegister).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //open register screen
//                finish();
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//            }
//        });
    }

    private void sellerLogin() {
        //first getting the values
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        //validating inputs
        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Please enter your username");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password");
            editTextPassword.requestFocus();
            return;
        }

        Log.e("LoginActivity", "username : "+username+", password : "+password);
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);

                        Log.e("LoginActivity", "responseLogin : "+response);
                        response2 = response;

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject sellerJson = obj.getJSONObject("seller");

                                //creating a new user object
                                Seller seller = new Seller(
                                        sellerJson.getInt("seller_id"),
                                        sellerJson.getString("username"),
                                        sellerJson.getString("email"),
                                        sellerJson.getString("telephone"),
                                        sellerJson.getString("city"),
                                        sellerJson.getInt("buyer_amount"),
                                        sellerJson.getString("banner_url")
                                );

                                if(TextUtils.isEmpty(seller.getUsername())){
                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                    Log.e("LoginActivity", "Kosong");
                                    viewEmpty(obj.getString("message"));
                                }

                                username2 = sellerJson.getString("username");

                                String bannerUrl = sellerJson.getString("banner_url");
                                Log.e("LoginActivity", "bannerUrl :"+bannerUrl);


                                //storing the user in shared preferences
                                SharedPrefManager.getInstance(getApplicationContext()).sellerLogin(seller);

                                Log.e("LoginActivity", "Response");


                                //starting the profile activity
                                startActivity(new Intent(LoginActivity.this, SplashScreenActivity.class));
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                viewEmpty(obj.getString("message"));
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
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        Log.e("LoginActivity", "username2 : "+username2);
    }

    private void viewEmpty(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Log.e("LoginActivity","viewEmpty");
    }

}
