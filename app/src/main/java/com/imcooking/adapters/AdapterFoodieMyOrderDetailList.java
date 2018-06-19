package com.imcooking.adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.imcooking.Model.api.response.FoodieMyorderList;
import com.imcooking.Model.api.response.OrderDetailsData;
import com.imcooking.R;
import com.imcooking.webservices.GetData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rakhi.
 * Contact Number : +91 99581874636
 */
public class AdapterFoodieMyOrderDetailList extends RecyclerView.Adapter<AdapterFoodieMyOrderDetailList.MyViewHolder> {
    private Context context;
    private List<OrderDetailsData.OrderDetailsBean>orderDetailsBeans;

    public AdapterFoodieMyOrderDetailList(Context context, List<OrderDetailsData.OrderDetailsBean>orderDetailsBeans) {
        this.context = context;
        this.orderDetailsBeans = orderDetailsBeans;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_chefname,tv_price,txtQyt,txt_dish_name;
        public ImageView dishImg;


        public MyViewHolder(View view) {
            super(view);
            tv_price = view.findViewById(R.id.item_foodie_my_order_price);
            tv_chefname = view.findViewById(R.id.item_foodie_my_order_ChefName);
            txt_dish_name = view.findViewById(R.id.item_foodie_my_order_dish_name);
            txtQyt = view.findViewById(R.id.item_foodie_my_order_qyt);
            dishImg = view.findViewById(R.id.item_foodie_my_order_chef_profile_image);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_details, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_chefname.setText(orderDetailsBeans.get(position).getChef_name());
        holder.txt_dish_name.setText(orderDetailsBeans.get(position).getOrder_dish_name());
        holder.txtQyt.setText("Qyt : "+orderDetailsBeans.get(position).getOrder_quantity());
        Picasso.with(context).load(GetData.IMG_BASE_URL +
                orderDetailsBeans.get(position).getOrder_dish_image()).into(holder.dishImg);
        holder.tv_price.setText("£"+orderDetailsBeans.get(position).getOrder_price());

    }

    @Override
    public int getItemCount() {
        return orderDetailsBeans.size();
    }
}