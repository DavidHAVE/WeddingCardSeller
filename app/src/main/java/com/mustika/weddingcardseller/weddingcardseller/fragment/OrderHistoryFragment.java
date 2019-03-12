package com.mustika.weddingcardseller.weddingcardseller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mustika.weddingcardseller.weddingcardseller.R;
import com.mustika.weddingcardseller.weddingcardseller.adapter.OrderAdapter;
import com.mustika.weddingcardseller.weddingcardseller.adapter.OrderHistoryAdapter;
import com.mustika.weddingcardseller.weddingcardseller.helper.Constant;
import com.mustika.weddingcardseller.weddingcardseller.helper.SharedPrefManager;
import com.mustika.weddingcardseller.weddingcardseller.model.Order;
import com.mustika.weddingcardseller.weddingcardseller.model.OrderHistory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderHistoryFragment extends Fragment {

    //Creating a List of superheroes
    private List<OrderHistory> listOrderHistory;

    //Creating Views
    private RecyclerView mOrderHistoryRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
//    private RecyclerView.Adapter adapter;
    private OrderHistoryAdapter orderHistoryAdapter;

    //Volley Request Queue
    private RequestQueue requestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_order_history, container, false);

        //Initializing Views
        mOrderHistoryRecyclerView = (RecyclerView) rootView.findViewById(R.id.order_history_recycler_view);
        mOrderHistoryRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
//        layoutManager = new GridLayoutManager(getActivity(), 3);
        mOrderHistoryRecyclerView.setLayoutManager(layoutManager);

        //Initializing our superheroes list
        listOrderHistory = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getActivity());

        Log.e("OrderHistoryFragment", "OrderHistoryFragment");

        //Calling method to get data to fetch data
//        getData();

        //initializing our adapter
        orderHistoryAdapter = new OrderHistoryAdapter(listOrderHistory, getActivity());

        //Adding adapter to recyclerview
        mOrderHistoryRecyclerView.setAdapter(orderHistoryAdapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        listOrderHistory.clear();
        getData();
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

                if (orderHistoryAdapter != null){
//                    galleryAdapter = new GalleryAdapter(listItems, getActivity());
                    //Adding adapter to recyclerview
//                    recyclerView.setAdapter(galleryAdapter);
                    orderHistoryAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
    }

    private JsonArrayRequest getDataFromServer() {
        //Initializing ProgressBar
        //Displaying Progressbar
//        progressBar.setVisibility(View.VISIBLE);
//        setProgressBarIndeterminateVisibility(true);

        final int sellerId = SharedPrefManager.getInstance(getActivity()).getSeller().getSellerId();

        //JsonArrayRequest of volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Constant.READ_ORDER_HISTORY_URL+sellerId,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Calling method parseData to parse the json response
                        parseData(response);
                        //Hiding the progressbar
//                        progressBar.setVisibility(View.GONE);
                        Log.e("OrderHistoryFragment", "response :"+response);

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
        requestQueue.add(getDataFromServer());

        Log.e("OrderFragment", "OrderFragment2");
    }

    String name;

    //This method will parse json data
    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            //Creating the superhero object
            OrderHistory orderHistory = new OrderHistory();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);

                //Adding data to the superhero object
                orderHistory.setOrderHistoryId(json.getInt(Constant.TAG_ORDER_HISTORY_ID));
                orderHistory.setFullNameO(json.getString(Constant.TAG_BUYER_NAME));
                orderHistory.setOrderTime(json.getString(Constant.TAG_ORDER_TIME));
                orderHistory.setSellerName(json.getString(Constant.TAG_SELLER_NAME));

                Log.e("OrderHistoryFragment", "fullNameO :"+orderHistory.getFullNameO());

//                superHero.setName(json.getString(Constant.TAG_NAME));
//                superHero.setPublisher(json.getString(Constant.TAG_PUBLISHER));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the superhero object to the list
            listOrderHistory.add(orderHistory);
        }

//        Log.e("OrderFragment", "listOrders :"+listOrderHistory.get(1).getSellerName());

        //Notifying the adapter that data has been added or changed
        orderHistoryAdapter.notifyDataSetChanged();
    }

}
