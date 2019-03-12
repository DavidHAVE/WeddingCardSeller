package com.mustika.weddingcardseller.weddingcardseller.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.mustika.weddingcardseller.weddingcardseller.R;
import com.mustika.weddingcardseller.weddingcardseller.helper.Constant;
import com.mustika.weddingcardseller.weddingcardseller.helper.CustomVolleyRequest;
import com.mustika.weddingcardseller.weddingcardseller.model.Item;
import com.mustika.weddingcardseller.weddingcardseller.ui.CardDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 23/02/2018.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> implements Filterable{

    //Imageloader to load image
    private ImageLoader imageLoader;
    private Context context;

    //List to store all superheroes
//    List<SuperHero> superHeroes;
    List<Item> items;
    List<Item> mFilteredList;

    //Constructor of this class
    public GalleryAdapter(List<Item> items, Context context){
        super();
        //Getting all superheroes
        this.items = items;
        this.context = context;
        this.mFilteredList = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        //Getting the particular item from the list
        final Item item = items.get(position);

//        viewHolder.tv_name.setText(mFilteredList.get(i).getName());

//        String url = Constant.IMAGE_URL+""+item.getImageUrl();


            String url = Constant.IMAGE_URL + "" + mFilteredList.get(position).getImageUrl();
//        String url = mFilteredList.get(position).getName();


            //Loading image from url
            imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
            imageLoader.get(url, ImageLoader.getImageListener(holder.imageView, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));
//
//        //Showing data on the views
            holder.imageView.setImageUrl(url, imageLoader);


//        final String name = item.getName();
//        final String description = item.getDescription();
//        final int price = item.getPrice();
//        holder.textViewName.setText(superHero.getName());
//        holder.textViewPublisher.setText(superHero.getPublisher());


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                openDetailActivity(mFilteredList.get(position).getItemId(), mFilteredList.get(position).getName(),
                        mFilteredList.get(position).getDescription(), mFilteredList.get(position).getPrice(), mFilteredList.get(position).getImageUrl());
                Toast.makeText(context, mFilteredList.get(position).getName(), Toast.LENGTH_SHORT).show();
//                openDetailActivity(item.getItemId(), item.getName(), item.getDescription(), item.getPrice(), item.getImageUrl());
//                Toast.makeText(context, item.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    private void openDetailActivity(int id, String name, String description, int price, String imageUrl)
    {
        Intent i = new Intent(context, CardDetailActivity.class);

        //PACK DATA TO SEND
        i.putExtra(Constant.TAG_ITEM_ID, id);
        i.putExtra(Constant.TAG_NAME, name);
        i.putExtra(Constant.TAG_DESCRIPTION, description);
        i.putExtra(Constant.TAG_PRICE, price);
        i.putExtra(Constant.TAG_IMAGE_URL, imageUrl);
        //open activity
        context.startActivity(i);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = items;
                } else {

                    List<Item> filteredList = new ArrayList<>();

                    for (Item item : items) {

                        if (item.getName().toLowerCase().contains(charString)) {

                            filteredList.add(item);
                        }
                    }
                    mFilteredList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (List<Item>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //Views
        public NetworkImageView imageView;
        public TextView textViewName;
        public TextView textViewPublisher;
        ItemClickListener itemClickListener;

        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (NetworkImageView) itemView.findViewById(R.id.imageViewHero);
//            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
//            textViewPublisher = (TextView) itemView.findViewById(R.id.textViewPublisher);

            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(this.getLayoutPosition());
        }
    }
}
