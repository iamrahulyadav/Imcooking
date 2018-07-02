package com.imcooking.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.imcooking.Model.api.response.FoodieMyorderList;
import com.imcooking.R;
import com.imcooking.fragment.foodie.FoodieOrderDetails;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class AdapterFoodieMyOrderList extends RecyclerView.Adapter<AdapterFoodieMyOrderList.MyViewHolder> {

    private MyorderInterface myorderInterface;
    private Context context;
    private FragmentManager manager;
    private List<FoodieMyorderList.FoodieOrderListBean> list ;
    private String TAG;

    public AdapterFoodieMyOrderList(Context context, FragmentManager manager, List<FoodieMyorderList.FoodieOrderListBean>list,
                                    MyorderInterface myorderInterface,
                                    String TAG) {
        this.context = context;
        this.manager = manager;
        this.list = list;
        this.myorderInterface = myorderInterface;
        this.TAG = TAG;
//        this.click = click;
    }

    public interface MyorderInterface{
        void getDetails(int position, String TAG);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_order_details,tv_chefname,tv_price,txtQyt,tv_orderid,tv_status,
                  txt_time;
//        public LinearLayout layout;
        public ImageView chefProfile;
        RatingBar ratingBar;
        public LinearLayout layout_rate;

        public MyViewHolder(View view) {
            super(view);

            layout_rate = view.findViewById(R.id.item_foodie_my_order_ratingbar_layout);

            tv_order_details = view.findViewById(R.id.item_foodie_orders_order_details);
            tv_chefname = view.findViewById(R.id.item_foodie_my_order_ChefName);
            tv_orderid = view.findViewById(R.id.item_foodie_my_order_orderid);
            txtQyt = view.findViewById(R.id.item_foodie_my_order_qyt);
            tv_price = view.findViewById(R.id.item_foodie_my_order_price);
            tv_status = view.findViewById(R.id.item_foodie_my_order_status);
            chefProfile = view.findViewById(R.id.item_foodie_my_order_chef_profile_image);
            ratingBar=view.findViewById(R.id.item_foodie_my_order_rating);
//            layout = view.findViewById(R.id.layout_order_details);
            txt_time = view.findViewById(R.id.item_foodie_my_order_time);
            tv_order_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myorderInterface.getDetails(getAdapterPosition(), TAG);
                }
            });
            layout_rate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(context, "a", Toast.LENGTH_SHORT).show();
                    myorderInterface.getDetails(getAdapterPosition(), "ratingbar");
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_foodie_my_orders, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_chefname.setText(list.get(position).getChef_name());
        holder.tv_orderid.setText("#"+list.get(position).getOrder_order_id());
        if (list.get(position).getOrder_status()!=null){
            String status = list.get(position).getOrder_status();
            if (status.equals("0"))
            holder.tv_status.setText("New Order");
            else if (status.equals("1"))
                holder.tv_status.setText("Accept");
            else if (status.equals("2"))
                holder.tv_status.setText("Decline");
            else if (status.equals("3"))
                holder.tv_status.setText("In Process");
            else if (status.equals("4"))
                holder.tv_status.setText("Ready");
            else if (status.equals("5"))
                holder.tv_status.setText("On Way");
            else if (status.equals("8"))
                holder.tv_status.setText("Delivered");
            else if (status.equals("9"))
                holder.tv_status.setText("Not Delivered");
        }

        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date myDate = null;
        try {
            myDate = timeFormat.parse(list.get(position).getBookdate());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat(
                "MMM dd, yyyy HH:mm a");
        String finalDate = dateFormat.format(myDate);
        holder.txt_time.setText(finalDate);
        if (list.get(position).getRating()!=null && list.get(position).getRating().length()>0){
            holder.ratingBar.setRating((Float.parseFloat(list.get(position).getRating())));
        }
        float price = Float.parseFloat(list.get(position).getPrice())*(list.get(position).getDish_qyt());
        holder.tv_price.setText("£" +String.valueOf(price));
        Picasso.with(context).load(GetData.IMG_BASE_URL +
                list.get(position).getChef_image()).into(holder.chefProfile);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}