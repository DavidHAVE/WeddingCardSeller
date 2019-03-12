package com.mustika.weddingcardseller.weddingcardseller.fragment;

import android.annotation.TargetApi;
import android.os.Build;
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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mustika.weddingcardseller.weddingcardseller.R;
import com.mustika.weddingcardseller.weddingcardseller.adapter.GalleryAdapter;
import com.mustika.weddingcardseller.weddingcardseller.helper.Constant;
import com.mustika.weddingcardseller.weddingcardseller.helper.SharedPrefManager;
import com.mustika.weddingcardseller.weddingcardseller.model.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment implements View.OnClickListener, RecyclerView.OnScrollChangeListener {

    private Button mStepButton;
    private ProgressBar progressBar;

    //Creating a List of superheroes
    private List<Item> listItems;

    //Creating Views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
//    private RecyclerView.Adapter adapter;
    private GalleryAdapter galleryAdapter;

    public static boolean isQuery = true;

    //Volley Request Queue
    private RequestQueue requestQueue;

    //The request counter to send ?page=1, ?page=2  requests
    private int requestCount = 1;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_gallery, container, false);

//        mStepButton = (Button) rootView.findViewById(R.id.step_button);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar1);

        //Initializing Views
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);

        //Initializing our superheroes list
        listItems = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getActivity());

        //Calling method to get data to fetch data
        getData();

        //Adding an scroll change listener to recyclerview
        recyclerView.setOnScrollChangeListener(this);

//        //initializing our adapter
        galleryAdapter = new GalleryAdapter(listItems, getActivity());
//
////        adapter.getFil
//
//        //Adding adapter to recyclerview
        recyclerView.setAdapter(galleryAdapter);

//        mStepButton.setOnClickListener(this);
        return rootView;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
//        if (id == R.id.step_button){
//            startActivity(new Intent(getActivity(), OrderStepActivity.class));
//        }
    }

    //Request to get json from server we are passing an integer here
    //This integer will used to specify the page number for the request ?page = requestcount
    //This method would return a JsonArrayRequest that will be added to the request queue
    private JsonArrayRequest getDataFromServer(int requestCount) {
        //Initializing ProgressBar


        //Displaying Progressbar
        progressBar.setVisibility(View.VISIBLE);
//        setProgressBarIndeterminateVisibility(true);

        //JsonArrayRequest of volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Constant.DATA_URL + String.valueOf(requestCount),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Calling method parseData to parse the json response
                        parseData(response);
                        //Hiding the progressbar
                        progressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
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
        requestQueue.add(getDataFromServer(requestCount));
        //Incrementing the request counter
        requestCount++;
    }

    String name;

    //This method will parse json data
    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            //Creating the superhero object
            Item item = new Item();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);

                //Adding data to the superhero object
                item.setItemId(json.getInt(Constant.TAG_ITEM_ID));
                item.setName(json.getString(Constant.TAG_NAME));
                item.setDescription(json.getString(Constant.TAG_DESCRIPTION));
                item.setPrice(json.getInt(Constant.TAG_PRICE));
                item.setImageUrl(json.getString(Constant.TAG_IMAGE_URL));
//                superHero.setName(json.getString(Constant.TAG_NAME));
//                superHero.setPublisher(json.getString(Constant.TAG_PUBLISHER));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the superhero object to the list
            listItems.add(item);
        }

//        galleryAdapter = new GalleryAdapter(listItems, getActivity());

//        adapter.getFil

        //Adding adapter to recyclerview
//        recyclerView.setAdapter(galleryAdapter);
        //Notifying the adapter that data has been added or changed
        galleryAdapter.notifyDataSetChanged();
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


                if (galleryAdapter != null){
//                    galleryAdapter = new GalleryAdapter(listItems, getActivity());
                    //Adding adapter to recyclerview
//                    recyclerView.setAdapter(galleryAdapter);
                    galleryAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
    }

    //This method would check that the recyclerview scroll has reached the bottom or not
    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
//        if (isQuery == false) {
            if (recyclerView.getAdapter().getItemCount() != 0) {
                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                    return true;
            }
//        }
        return false;
    }

    //Overriden method to detect scrolling
    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        //Ifscrolled at last then
        if (isLastItemDisplaying(recyclerView)) {
            //Calling the method getdata again
            Log.e("GalleryFragment", "onScorll isQuery :"+isQuery);

//            if (isQuery == false){
                getData();
//            }else {
//                return;
//            }
        }
    }
}
