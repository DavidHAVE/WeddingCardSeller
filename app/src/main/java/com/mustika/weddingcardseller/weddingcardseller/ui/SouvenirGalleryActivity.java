package com.mustika.weddingcardseller.weddingcardseller.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.mustika.weddingcardseller.weddingcardseller.adapter.SouvenirGalleryAdapter;
import com.mustika.weddingcardseller.weddingcardseller.helper.Constant;
import com.mustika.weddingcardseller.weddingcardseller.helper.SharedPrefManager;
import com.mustika.weddingcardseller.weddingcardseller.model.Item;
import com.mustika.weddingcardseller.weddingcardseller.model.Souvenir;
import com.mustika.weddingcardseller.weddingcardseller.model.Store;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.itemId;
import static com.mustika.weddingcardseller.weddingcardseller.ui.OrderStepActivity.weddingCardQuantity;

public class SouvenirGalleryActivity extends AppCompatActivity implements RecyclerView.OnScrollChangeListener {

    private ProgressBar progressBar;

    //Creating a List of superheroes
    private List<Souvenir> listSouvenirs;

    //Creating Views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
//    private RecyclerView.Adapter adapter;
    private SouvenirGalleryAdapter souvenirGalleryAdapter;

    //Volley Request Queue
    private RequestQueue requestQueue;

    private int requestCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_souvenir_gallery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        mStepButton = (Button) rootView.findViewById(R.id.step_button);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        //Initializing Views
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

//        if (this.getIntent() != null){
//            itemId = this.getIntent().getExtras().getInt(Constant.TAG_ITEM_ID);
//            name = this.getIntent().getExtras().getString(Constant.TAG_NAME);
////            description = getActivity().getIntent().getExtras().getString(Constant.TAG_DESCRIPTION);
//            price = this.getIntent().getExtras().getInt(Constant.TAG_PRICE);
////            imageUrl = getActivity().getIntent().getExtras().getString(Constant.TAG_IMAGE_URL);
//            weddingCardQuantity = this.getIntent().getExtras().getInt("weddingcardquantity");
//
////            mFormTextView.setText(name +"\n"+ description+"\n"+ String.valueOf(price)+"\n"+ imageUrl);
//        }

        recyclerView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        //Initializing our superheroes list
        listSouvenirs = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        //Calling method to get data to fetch data
        getData();

        //Adding an scroll change listener to recyclerview
        recyclerView.setOnScrollChangeListener(this);

        //initializing our adapter
        souvenirGalleryAdapter = new SouvenirGalleryAdapter(listSouvenirs, this);

        //Adding adapter to recyclerview
        recyclerView.setAdapter(souvenirGalleryAdapter);
    }

    private JsonArrayRequest getDataFromServer(int requestCount) {
        //Initializing ProgressBar

        //Displaying Progressbar
        progressBar.setVisibility(View.VISIBLE);
//        setProgressBarIndeterminateVisibility(true);

        //JsonArrayRequest of volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Constant.SOUVENIR_URL + String.valueOf(requestCount),
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
                        Toast.makeText(SouvenirGalleryActivity.this, "No More Items Available", Toast.LENGTH_SHORT).show();
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
            Souvenir souvenir = new Souvenir();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);

                //Adding data to the superhero object
                souvenir.setSouvenirId(json.getInt(Constant.TAG_SOUVENIR_ID));
                souvenir.setName(json.getString(Constant.TAG_NAME));
                souvenir.setPrice(json.getInt(Constant.TAG_PRICE));
                souvenir.setImageUrl(json.getString(Constant.TAG_IMAGE_URL));
//                superHero.setName(json.getString(Constant.TAG_NAME));
//                superHero.setPublisher(json.getString(Constant.TAG_PUBLISHER));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the superhero object to the list
            listSouvenirs.add(souvenir);
        }

        //Notifying the adapter that data has been added or changed
        souvenirGalleryAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.souvenir, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_skip) {
            showSkipSouvenirConfirmationDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (souvenirGalleryAdapter != null) souvenirGalleryAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    private void showSkipSouvenirConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Ingin melewati souvenir ?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                SharedPrefManager.getInstance(getApplicationContext()).deleteSouvenir();
                startActivity(new Intent(SouvenirGalleryActivity.this, OrderStepActivity.class));
                finish();
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    return;
                }
            }
        });
        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    //This method would check that the recyclerview scroll has reached the bottom or not
    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }

    //Overriden method to detect scrolling
    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        //Ifscrolled at last then
        if (isLastItemDisplaying(recyclerView)) {
            //Calling the method getdata again
            getData();
        }
    }

}
