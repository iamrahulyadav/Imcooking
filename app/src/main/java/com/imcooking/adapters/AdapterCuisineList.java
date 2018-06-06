package com.imcooking.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imcooking.Model.api.response.CuisineData;
import com.imcooking.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class AdapterCuisineList extends RecyclerView.Adapter<AdapterCuisineList.MyViewHolder> {

    private Context context;
    private click_adapter_cuisine_list obj;
    private ArrayList<String> arrayList = new ArrayList();
    private CuisineData cuisineData;

    public AdapterCuisineList(Context context, CuisineData cuisineData, ArrayList<String> arrayList
            , click_adapter_cuisine_list obj) {
        this.context = context;
        this.arrayList = arrayList;
        this.obj = obj;
        this.cuisineData = cuisineData;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public LinearLayout layout;
        public ImageView iv;

        public MyViewHolder(View view) {
            super(view);

            textView = view.findViewById(R.id.item_cuisine_list_text);
            iv = view.findViewById(R.id.item_cuisine_list_image);
            layout = view.findViewById(R.id.item_cuisine_list_icon);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    obj.click_adapter_cuisine_list_m(getAdapterPosition());
                }
            });

        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cuisine_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if(arrayList.get(position).equals("1")){
            holder.iv.setImageResource(R.drawable.ic_like);
        } else{
            holder.iv.setImageResource(R.drawable.ic_heart_orange);
        }

        holder.textView.setText(cuisineData.getCuisine_data().get(position).getCuisine_name());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public interface click_adapter_cuisine_list{

        public void click_adapter_cuisine_list_m(int position);
    }
}