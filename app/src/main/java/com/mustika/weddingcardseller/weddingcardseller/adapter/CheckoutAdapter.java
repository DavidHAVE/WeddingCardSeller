package com.mustika.weddingcardseller.weddingcardseller.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.mustika.weddingcardseller.weddingcardseller.R;
import com.mustika.weddingcardseller.weddingcardseller.helper.Constant;
import com.mustika.weddingcardseller.weddingcardseller.helper.CustomVolleyRequest;
import com.mustika.weddingcardseller.weddingcardseller.model.Store;
import com.mustika.weddingcardseller.weddingcardseller.ui.CardDetailActivity;

import java.util.List;

/**
 * Created by apple on 3/16/18.
 */

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.ViewHolder> {

    //Imageloader to load image
    private ImageLoader imageLoader;
    private Context context;

    //List to store all superheroes
//    List<SuperHero> superHeroes;
    List<Store> stores;

    //Constructor of this class
    public CheckoutAdapter(List<Store> stores, Context context){
        super();
        //Getting all superheroes
        this.stores = stores;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.checkout_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Getting the particular item from the list
        final Store store = stores.get(position);

//        String url = Constant.IMAGE_URL+""+item.getImageUrl();

        holder.textViewName.setText(store.getStoreName());
        holder.textViewTotal.setText(String.valueOf(store.getStoreTotal()));
        holder.textViewRate.setText(String.valueOf(store.getStoreRate()));
        holder.textViewPrice.setText(String.valueOf(store.getStorePrice()));

        //Showing data on the views
//        holder.imageView.setImageUrl(url, imageLoader);

//        final String name = item.getName();
//        final String description = item.getDescription();
//        final int price = item.getPrice();
//        holder.textViewName.setText(superHero.getName());
//        holder.textViewPublisher.setText(superHero.getPublisher());

    }

    @Override
    public int getItemCount() {
        return stores.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //Views
        public TextView textViewName;
        public TextView textViewTotal;
        public TextView textViewRate;
        public TextView textViewPrice;

        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
//            imageView = (NetworkImageView) itemView.findViewById(R.id.imageViewHero);
//            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
//            textViewPublisher = (TextView) itemView.findViewById(R.id.textViewPublisher);

        textViewName = (TextView) itemView.findViewById(R.id.name_text_view);
        textViewTotal = (TextView) itemView.findViewById(R.id.quantity_text_view);
        textViewRate = (TextView) itemView.findViewById(R.id.rate_text_view);
        textViewPrice = (TextView) itemView.findViewById(R.id.price_text_view);
        }
    }
}
