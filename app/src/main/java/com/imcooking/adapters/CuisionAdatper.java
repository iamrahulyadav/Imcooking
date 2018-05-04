package com.imcooking.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.imcooking.Model.api.response.CuisineData;
import com.imcooking.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class CuisionAdatper extends RecyclerView.Adapter<CuisionAdatper.MyViewHolder> {

    OnItemClickListenerCategory listener;
    private Context context;
    private List<CuisineData.CuisineDataBean>cuisineDataBeans = new ArrayList<>();

    public CuisionAdatper( Context context,
                          List<CuisineData.CuisineDataBean> cuisineDataBeans) {
        this.listener = listener;
        this.context = context;
        this.cuisineDataBeans = cuisineDataBeans;
    }

    public interface  OnItemClickListenerCategory {
        void onItemClickCategory(int position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName;

        public MyViewHolder(View view) {
            super(view);

            txtName = (TextView) view.findViewById(R.id.txtCuisionName);
        }
        void bindListener(final int position, final OnItemClickListenerCategory listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    row_index=position;
                    notifyDataSetChanged();
                    listener.onItemClickCategory(position);

                }
            });
        }
    }

    int row_index=-1;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cuision_list_category, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.txtName.setText(cuisineDataBeans.get(position).getCuisine_name());
      //  holder.bindListener(position, listener);
    }

    @Override
    public int getItemCount() {
        return cuisineDataBeans.size();
    }
}