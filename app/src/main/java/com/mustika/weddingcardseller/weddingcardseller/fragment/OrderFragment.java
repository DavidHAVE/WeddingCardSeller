package com.mustika.weddingcardseller.weddingcardseller.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mustika.weddingcardseller.weddingcardseller.R;
import com.mustika.weddingcardseller.weddingcardseller.adapter.GalleryAdapter;
import com.mustika.weddingcardseller.weddingcardseller.adapter.OrderAdapter;
import com.mustika.weddingcardseller.weddingcardseller.helper.Constant;
import com.mustika.weddingcardseller.weddingcardseller.helper.SharedPrefManager;
import com.mustika.weddingcardseller.weddingcardseller.model.Item;
import com.mustika.weddingcardseller.weddingcardseller.model.Order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class OrderFragment extends Fragment {

    //Creating a List of superheroes
    private List<Order> listOrders;

    //Creating Views
    private RecyclerView mOrderRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
//    private RecyclerView.Adapter adapter;
    private OrderAdapter orderAdapter;

    //Volley Request Queue
    private RequestQueue requestQueue;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_order, container, false);

        //Initializing Views
        mOrderRecyclerView = (RecyclerView) rootView.findViewById(R.id.order_recycler_view);
        mOrderRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
//        layoutManager = new GridLayoutManager(getActivity(), 3);
        mOrderRecyclerView.setLayoutManager(layoutManager);

        //Initializing our superheroes list
        listOrders = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getActivity());

        //Calling method to get data to fetch data
//        getData();

        //initializing our adapter
        orderAdapter = new OrderAdapter(listOrders, getActivity());

        //Adding adapter to recyclerview
        mOrderRecyclerView.setAdapter(orderAdapter);


        Log.e("OrderFragment", "OrderFragment");

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        listOrders.clear();
        getData();
//        orderAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.main, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            SharedPrefManager.getInstance(getActivity()).logout();
            getActivity().finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Log.e("GalleryFragment", "isQuery :")
//                isQuery = true;
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
//                isQuery = true;
//                Log.e("GalleryFragment", "onTextChange isQuery :"+isQuery);

                if (orderAdapter != null){
//                    galleryAdapter = new GalleryAdapter(listItems, getActivity());
                    //Adding adapter to recyclerview
//                    recyclerView.setAdapter(galleryAdapter);
                    orderAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
    }


    //    private void loadOrderList() {
//        //getting the progressbar
////        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
//
//        //making the progressbar visible
////        progressBar.setVisibility(View.VISIBLE);
//
//        //creating a string request to send request to the url
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.READ_ORDER_URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        //hiding the progressbar after completion
////                        progressBar.setVisibility(View.INVISIBLE);
//
//
//                        try {
//                            //getting the whole json object from the response
//                            JSONObject obj = new JSONObject(response);
//
//                            //we have the array named hero inside the object
//                            //so here we are getting that json array
//                            JSONArray heroArray = obj.getJSONArray("heroes");
//
//                            //now looping through all the elements of the json array
//                            for (int i = 0; i < heroArray.length(); i++) {
//                                //getting the json object of the particular index inside the array
//                                JSONObject heroObject = heroArray.getJSONObject(i);
//
//                                //creating a hero object and giving them the values from json object
//                                Hero hero = new Hero(heroObject.getString("name"), heroObject.getString("imageurl"));
//
//                                //adding the hero to herolist
//                                heroList.add(hero);
//                            }
//
//                            //creating custom adapter object
//                            ListViewAdapter adapter = new ListViewAdapter(heroList, getApplicationContext());
//
//                            //adding the adapter to listview
//                            listView.setAdapter(adapter);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        //displaying the error in toast if occurrs
//                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//        //creating a request queue
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//
//        //adding the string request to request queue
//        requestQueue.add(stringRequest);
//    }

    private JsonArrayRequest getDataFromServer() {
        //Initializing ProgressBar


        //Displaying Progressbar
//        progressBar.setVisibility(View.VISIBLE);
//        setProgressBarIndeterminateVisibility(true);

        final int sellerId = SharedPrefManager.getInstance(getActivity()).getSeller().getSellerId();

        //JsonArrayRequest of volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Constant.READ_ORDER_URL_ID+sellerId,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Calling method parseData to parse the json response
                        parseData(response);
                        //Hiding the progressbar
//                        progressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        progressBar.setVisibility(View.GONE);
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
//        requestQueue.add(getDataFromServer(1));

        requestQueue.add(getDataFromServer());

        Log.e("OrderFragment", "OrderFragment2");

//        getInstance().addToReqQueue(requestQueue);
        //Incrementing the request counter
//        requestCount++;
    }

    String name;

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
                order.setOrderId(json.getInt(Constant.TAG_ORDER_ID));
                order.setFullNameO(json.getString(Constant.TAG_BUYER_NAME));
                order.setOrderTime(json.getString(Constant.TAG_ORDER_TIME));
                order.setSellerName(json.getString(Constant.TAG_SELLER_NAME));

                Log.e("OrderFragment", "fullNameO :"+order.getFullNameO());

//                superHero.setName(json.getString(Constant.TAG_NAME));
//                superHero.setPublisher(json.getString(Constant.TAG_PUBLISHER));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the superhero object to the list
            listOrders.add(order);
        }

//        Log.e("OrderFragment", "listOrders :"+listOrders.get(1).getSellerName());

        //Notifying the adapter that data has been added or changed
        orderAdapter.notifyDataSetChanged();
    }

}
