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
    private Context context;
    CuisionInterface cuisionInterface;
    private List<CuisineData.CuisineDataBean>cuisineDataBeans;

    public CuisionAdatper(Context context, CuisionInterface cuisionInterface,
                          List<CuisineData.CuisineDataBean> cuisineDataBeans) {
        this.context = context;
        this.cuisionInterface = cuisionInterface;
        this.cuisineDataBeans = cuisineDataBeans;
    }

    public interface CuisionInterface {
        void CuisionInterfaceMethod(View view, int position);
    }
    public void CuisionInterfaceMethod(CuisionInterface quoteInterface) {
        this.cuisionInterface = quoteInterface;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName;

        public MyViewHolder(View view) {
            super(view);

            txtName = (TextView) view.findViewById(R.id.txtCuisionName);
            }

        void bindListener(final int position, final CuisionInterface listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    row_index=position;
                    notifyDataSetChanged();
                    listener.CuisionInterfaceMethod(itemView,position);
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
        holder.bindListener(position,cuisionInterface );

        if (row_index==position){
            holder.txtName.setBackground(context.getResources().getDrawable(R.drawable.shape_background_theme_1));
            holder.txtName.setTextColor(context.getResources().getColor(R.color.colorWhite));
        } else {
            holder.txtName.setTextColor(context.getResources().getColor(R.color.colorBlack));
            holder.txtName.setBackground(context.getResources().getDrawable(R.drawable.shape_theme_border));
        }
    }

    @Override
    public int getItemCount() {
        return cuisineDataBeans.size();
    }

}