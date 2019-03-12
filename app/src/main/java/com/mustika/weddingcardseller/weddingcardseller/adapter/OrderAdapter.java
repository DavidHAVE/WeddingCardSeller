package com.mustika.weddingcardseller.weddingcardseller.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.mustika.weddingcardseller.weddingcardseller.R;
import com.mustika.weddingcardseller.weddingcardseller.helper.Constant;
import com.mustika.weddingcardseller.weddingcardseller.model.Item;
import com.mustika.weddingcardseller.weddingcardseller.model.Order;
import com.mustika.weddingcardseller.weddingcardseller.ui.CardDetailActivity;
import com.mustika.weddingcardseller.weddingcardseller.ui.OrderDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2/25/18.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> implements Filterable {

    private Context context;

    //List to store all superheroes
//    List<SuperHero> superHeroes;
    List<Order> orders;
    List<Order> mFilteredList;

    //Constructor of this class
    public OrderAdapter(List<Order> orders, Context context){
        super();
        //Getting all superheroes
        this.orders = orders;
        this.context = context;
        this.mFilteredList = orders;
    }

    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_list, parent, false);
        OrderAdapter.ViewHolder viewHolder = new OrderAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OrderAdapter.ViewHolder holder, final int position) {

        //Getting the particular item from the list
        final Order order = orders.get(position);

        holder.textViewSellerName.setText(mFilteredList.get(position).getSellerName());
        holder.textViewBuyerName.setText(mFilteredList.get(position).getFullNameO());
        holder.textViewOrderTime.setText(mFilteredList.get(position).getOrderTime());

//        holder.textViewSellerName.setText(order.getSellerName());
//        holder.textViewBuyerName.setText(order.getFullNameO());
//        holder.textViewOrderTime.setText(order.getOrderTime());

        Log.e("OrderAdapter", "Tes"+", sellerName :"+order.getSellerName());


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                openDetailActivity(mFilteredList.get(position).getOrderId(), mFilteredList.get(position).getSellerName(), mFilteredList.get(position).getFullNameO(), mFilteredList.get(position).getOrderTime());
                                Toast.makeText(context, mFilteredList.get(position).getFullNameO(), Toast.LENGTH_SHORT).show();
                                //                openDetailActivity(order.getOrderId(), order.getSellerName(), order.getFullNameO(), order.getOrderTime());
//                Toast.makeText(context, order.getSellerName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    private void openDetailActivity(int orderId, String sellerName, String buyerName, String orderTime)
    {
        Intent i = new Intent(context, OrderDetailActivity.class);

        //PACK DATA TO SEND
        i.putExtra(Constant.TAG_ORDER_ID, orderId);
        i.putExtra(Constant.TAG_SELLER_NAME, sellerName);
        i.putExtra(Constant.TAG_BUYER_NAME, buyerName);
        i.putExtra(Constant.TAG_ORDER_TIME, orderTime);
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

                    mFilteredList = orders;
                } else {

                    List<Order> filteredList = new ArrayList<>();

                    for (Order order : orders) {

                        if (order.getFullNameO().toLowerCase().contains(charString)) {

                            filteredList.add(order);
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
                mFilteredList = (List<Order>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //Views
//        public NetworkImageView imageView;
        public TextView textViewSellerName;
        public TextView textViewBuyerName;
        public TextView textViewOrderTime;
        ItemClickListener itemClickListener;

        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
//            imageView = (NetworkImageView) itemView.findViewById(R.id.imageViewHero);
            textViewSellerName = (TextView) itemView.findViewById(R.id.seller_name_text_view);
            textViewBuyerName = (TextView) itemView.findViewById(R.id.buyer_name_text_view);
            textViewOrderTime = (TextView) itemView.findViewById(R.id.order_time_text_view);

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
