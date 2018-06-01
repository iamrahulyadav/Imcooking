package com.imcooking.adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.imcooking.Model.api.response.FoodieMyRequest;
import com.imcooking.R;
import com.imcooking.webservices.GetData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class AdapterFoodieMyRequest extends RecyclerView.Adapter<AdapterFoodieMyRequest.MyViewHolder> {

//    OnItemClickListenerCategory listener;
    private Context context;
    List<FoodieMyRequest.FoodieRequestDishChefDetailsBean> list=new ArrayList<>();
    //private List<CuisineData.CuisineDataBean>cuisineDataBeans = new ArrayList<>();

    public AdapterFoodieMyRequest(Context context, List<FoodieMyRequest.FoodieRequestDishChefDetailsBean> list) {
        this.context = context;
this.list=list;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_view_response,txt_name,txt_address,txt_email,txt_time;
        public RatingBar ratingBar;
        public ImageView img_profile;
        public MyViewHolder(View view) {
            super(view);

            tv_view_response = view.findViewById(R.id.item_foodie_my_requests_view_response);
            txt_name = view.findViewById(R.id.item_foodie_my_request_name);
            txt_address = view.findViewById(R.id.item_foodie_my_request_address);
            txt_email = view.findViewById(R.id.item_foodie_my_request_email);
            txt_time = view.findViewById(R.id.item_foodie_my_request_time);
            img_profile=view.findViewById(R.id.item_foodie_my_request_profile_image);
            ratingBar=view.findViewById(R.id.item_foodie_my_request_rating);
        }
    }

    int row_index=-1;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_foodie_my_requests, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
       // FoodieMyRequest foodie=new FoodieMyRequest();

      holder.txt_name.setText(list.get(position).getChef_name());
        holder.txt_address.setText(list.get(position).getChef_address());
        holder.txt_email.setText(list.get(position).getChef_email());
      //  holder.txt_time.setText(foodie.get());
        Picasso.with(context).load(GetData.IMG_BASE_URL +
                list.get(position).getChef_image()).into(holder.img_profile);
        holder.tv_view_response.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_view_response);
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(null);
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}