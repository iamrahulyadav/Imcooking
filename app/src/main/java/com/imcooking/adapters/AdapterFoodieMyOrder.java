package com.imcooking.adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.imcooking.Model.api.response.FoodieMyorder;
import com.imcooking.R;
import com.imcooking.fragment.foodie.FoodieOrderDetails;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class AdapterFoodieMyOrder extends RecyclerView.Adapter<AdapterFoodieMyOrder.MyViewHolder> {

    private Context context;
    private FragmentManager manager;
    private List<FoodieMyorder.FoodieOrderListBean> list = new ArrayList();

    public AdapterFoodieMyOrder(Context context, FragmentManager manager, List<FoodieMyorder.FoodieOrderListBean>list) {
        this.context = context;
        this.manager = manager;
        this.list = list;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_order_details,tv_chefname,tv_price,tv_orderid,tv_status;
        public ImageView chefProfile;
        RatingBar ratingBar;
        public MyViewHolder(View view) {
            super(view);
            tv_order_details = view.findViewById(R.id.item_foodie_orders_order_details);
            tv_chefname = view.findViewById(R.id.item_foodie_my_order_ChefName);
            tv_orderid = view.findViewById(R.id.item_foodie_my_order_orderid);
            tv_price = view.findViewById(R.id.item_foodie_my_order_price);
            tv_status = view.findViewById(R.id.item_foodie_my_order_status);
            chefProfile = view.findViewById(R.id.item_foodie_my_order_chef_profile_image);
            ratingBar=view.findViewById(R.id.item_foodie_my_order_rating);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_foodie_my_orders, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_chefname.setText(list.get(position).getChef_name());
        holder.tv_orderid.setText("#"+list.get(position).getOrder_order_id());
        holder.tv_price.setText(String.valueOf(list.get(position).getPrice()));
        holder.tv_status.setText(list.get(position).getOrder_status());
        holder.ratingBar.setRating((float) list.get(position).getRating());
        Picasso.with(context).load(GetData.IMG_BASE_URL +
                list.get(position).getChef_image()).into(holder.chefProfile);
        holder.tv_order_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseClass.callFragment1(new FoodieOrderDetails(),
                        new FoodieOrderDetails().getClass().getName(), manager);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}