package com.imcooking.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imcooking.Model.api.response.AddCart;
import com.imcooking.R;
import com.imcooking.webservices.GetData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class CartAdatper extends RecyclerView.Adapter<CartAdatper.MyViewHolder> {

//    OnItemClickListenerCategory listener;
    private Context context;
    List<AddCart.AddDishBean> dishDetails = new ArrayList<>();

    //private List<CuisineData.CuisineDataBean>cuisineDataBeans = new ArrayList<>();

    public CartAdatper(Context context,
                       List<AddCart.AddDishBean> dishDetails) {
        this.context = context;
        this.dishDetails = dishDetails;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView txtDishName;
        public ImageView imgDish;

        public MyViewHolder(View view) {
            super(view);

            txtDishName = view.findViewById(R.id.tv_dish_name);
            imgDish=view.findViewById(R.id.img_dish);
            //txtDishName.setOnClickListener(this);
        }


    }

    int row_index=-1;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_cart, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.txtDishName.setText(dishDetails.get(position).getDish_name());
        Picasso.with(context).load(GetData.IMG_BASE_URL + dishDetails.get(position).getDish_image()).into(holder.imgDish);
    }

    @Override
    public int getItemCount() {
        return dishDetails.size();
    }
}