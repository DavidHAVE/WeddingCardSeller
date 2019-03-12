package com.mustika.weddingcardseller.weddingcardseller.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.mustika.weddingcardseller.weddingcardseller.R;
import com.mustika.weddingcardseller.weddingcardseller.adapter.GalleryAdapter;
import com.mustika.weddingcardseller.weddingcardseller.autentikasi.LoginActivity;
import com.mustika.weddingcardseller.weddingcardseller.fragment.GalleryFragment;
import com.mustika.weddingcardseller.weddingcardseller.helper.Constant;
import com.mustika.weddingcardseller.weddingcardseller.helper.SharedPrefManager;
import com.mustika.weddingcardseller.weddingcardseller.model.Item;
import com.mustika.weddingcardseller.weddingcardseller.ui.OrderActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ProgressDialog loadingDialog2;
    private String username, bannerUrl;


//    //Creating a List of superheroes
//    private List<Item> listItems;
//    //Creating Views
//    private RecyclerView recyclerView;
//    private RecyclerView.LayoutManager layoutManager;
//    private RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //if the user is not logged in
        //starting the login activity
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

//        adapter = new GalleryAdapter(listItems, this);
//        GalleryAdapter galleryAdapter = new GalleryAdapter(listItems, this);
//        galleryAdapter.getFilter();
        //getting the current user
//        Seller seller = SharedPrefManager.getInstance(this).getSeller();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //add this line to display menu1 when the activity is loaded
        displaySelectedScreen(R.id.nav_menu1);

//        username = SharedPrefManager.getInstance(this).getSeller().getUsername();
//        bannerUrl = SharedPrefManager.getInstance(this).getSeller().getBannerUrl();
//        loadingDialog2 = new ProgressDialog(this);
//        loadingDialog2.setCanceledOnTouchOutside(false);
//        loadingDialog2.setCancelable(false);
//        loadingDialog2.setMessage("Please Wait...");
//        loadingDialog2.show();
//        final URL[] imageUrl = new URL[1];
//        try {
//            imageUrl[0] = new URL(bannerUrl);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        new GetImageTask().execute(imageUrl[0]);
    }


    public class GetImageTask extends AsyncTask<URL, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(URL... params) {
            URL imgUrl = params[0];
            Log.e("TipActivity", " imgUrl : " + imgUrl);
            Bitmap bitmap = null;
            try {
                bitmap = Glide.with(getApplicationContext()).
                        load(imgUrl).
                        asBitmap().
                        into(800, 600). // Width and height
                        get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap == null) {
                Log.e("BuyerDetail", "Bitmap tidak ada");
            } else {
                Log.e("BuyerDetail", "Bitmap ada");
                storeImage(bitmap);
//                base = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);
//                Log.e("TipActivity", "base64 : " + base);
//                baseList.add(base);
//                if (baseList.size() == tipLists.size()) {
//                    save();
//                }
            }
        }
    }

    private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d("MainActivity", "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);

            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("MainActivity", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("MainActivity", "Error accessing file: " + e.getMessage());
        }
    }

    /** Create a File for saving an image or video */
    private File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        //creating a directory in SD card
//        File mydir = new File(Environment.getExternalStorageDirectory()
//                + StaticValue.PATH_ORDER_INVOICE); //PATH_PRODUCT_REPORT="/SIAS/REPORT_PRODUCT/"
//        //getting the full path of the PDF report name
//        String mPath = Environment.getExternalStorageDirectory().toString()
//                + StaticValue.PATH_ORDER_INVOICE //PATH_PRODUCT_REPORT="/SIAS/REPORT_PRODUCT/"
//                + orderNumber + ".pdf"; //reportName could be any name

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + Constant.PATH_BANNER_FILE);

//        File mediaStorageDir2 = new File(Environment.getExternalStorageDirectory()
//                + "/Android/data/"
//                + getApplicationContext().getPackageName()
//                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
//        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName = username +".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        loadingDialog2.dismiss();
        return mediaFile;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        MenuItem search = menu.findItem(R.id.search);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
//        search(searchView);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_logout) {
//            SharedPrefManager.getInstance(getApplicationContext()).logout();
//            finish();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_menu1:
                fragment = new GalleryFragment();
                break;
            case R.id.nav_menu2:
                startActivity(new Intent(this, OrderActivity.class));
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //calling the method displayselectedscreen and passing the id of selected menu
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;
    }

}
