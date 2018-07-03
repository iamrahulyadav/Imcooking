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
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayList<String> arrayList_status = new ArrayList<>();
    private Interface_CuisineAdapter click;

    public CuisionAdatper(Context context, ArrayList<String> arrayList, ArrayList<String> arrayList_status,
                          Interface_CuisineAdapter click) {
        this.context = context;
        this.arrayList = arrayList;
        this.arrayList_status = arrayList_status;
        this.click = click;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName;

        public MyViewHolder(View view) {
            super(view);

            txtName = (TextView) view.findViewById(R.id.txtCuisionName);
            txtName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    click.method_CuisineAdapter(getAdapterPosition());
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cuision_list_category, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.txtName.setText(arrayList.get(position));


        if (arrayList_status.get(position).equals("1")){
            holder.txtName.setBackground(context.getResources().getDrawable(R.drawable.shape_background_theme));
            holder.txtName.setTextColor(context.getResources().getColor(R.color.colorWhite));
        } else {
            holder.txtName.setTextColor(context.getResources().getColor(R.color.colorBlack));
            holder.txtName.setBackground(context.getResources().getDrawable(R.drawable.shape_theme_border));
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public interface Interface_CuisineAdapter{
        public void method_CuisineAdapter(int position);
    }
}