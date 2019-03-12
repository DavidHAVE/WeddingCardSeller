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

import com.mustika.weddingcardseller.weddingcardseller.R;
import com.mustika.weddingcardseller.weddingcardseller.helper.Constant;
import com.mustika.weddingcardseller.weddingcardseller.model.Order;
import com.mustika.weddingcardseller.weddingcardseller.model.OrderHistory;
import com.mustika.weddingcardseller.weddingcardseller.ui.OrderDetailActivity;
import com.mustika.weddingcardseller.weddingcardseller.ui.OrderHistoryDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 3/8/18.
 */

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> implements Filterable {

    private Context context;

    //List to store all superheroes
//    List<SuperHero> superHeroes;
    List<OrderHistory> orderHistories;
    List<OrderHistory> mFilteredList;

    //Constructor of this class
    public OrderHistoryAdapter(List<OrderHistory> orderHistories, Context context){
        super();
        //Getting all superheroes
        this.orderHistories = orderHistories;
        this.context = context;
        mFilteredList = orderHistories;
    }

    @Override
    public OrderHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_history_list, parent, false);
        OrderHistoryAdapter.ViewHolder viewHolder = new OrderHistoryAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OrderHistoryAdapter.ViewHolder holder, final int position) {

        //Getting the particular item from the list
        final OrderHistory orderHistory = orderHistories.get(position);

        holder.textViewSellerName.setText(mFilteredList.get(position).getSellerName());
        holder.textViewBuyerName.setText(mFilteredList.get(position).getFullNameO());
        holder.textViewOrderTime.setText(mFilteredList.get(position).getOrderTime());
//        holder.textViewSellerName.setText(orderHistory.getSellerName());
//        holder.textViewBuyerName.setText(orderHistory.getFullNameO());
//        holder.textViewOrderTime.setText(orderHistory.getOrderTime());

        Log.e("OrderAdapter", "Tes"+", sellerName :"+orderHistory.getSellerName());


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                openDetailActivity(mFilteredList.get(position).getOrderHistoryId(), mFilteredList.get(position).getSellerName(), mFilteredList.get(position).getFullNameO(), mFilteredList.get(position).getOrderTime());
                Toast.makeText(context, mFilteredList.get(position).getFullNameO(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, order.getSellerName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    private void openDetailActivity(int orderHistoryId, String sellerName, String buyerName, String orderTime)
    {
        Intent i = new Intent(context, OrderHistoryDetailActivity.class);

        //PACK DATA TO SEND
        i.putExtra(Constant.TAG_ORDER_HISTORY_ID, orderHistoryId);
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

                    mFilteredList = orderHistories;
                } else {

                    List<OrderHistory> filteredList = new ArrayList<>();

                    for (OrderHistory orderHistory : orderHistories) {

                        if (orderHistory.getFullNameO().toLowerCase().contains(charString)) {

                            filteredList.add(orderHistory);
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
                mFilteredList = (List<OrderHistory>) filterResults.values;
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
