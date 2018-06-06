package com.imcooking.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.imcooking.Model.api.response.ChefProfileData1;
import com.imcooking.Model.api.response.CuisineData;
import com.imcooking.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class AdapterChefAboutCuisineList extends RecyclerView.Adapter<AdapterChefAboutCuisineList.MyViewHolder> {

    private Context context;
    private List<ChefProfileData1.ChefDataBean.CuisineNameBean> list = new ArrayList<>();


    public AdapterChefAboutCuisineList(Context context, List<ChefProfileData1.ChefDataBean.CuisineNameBean> list) {
        this.context = context;
        this.list = list;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtName;
        public MyViewHolder(View view) {
            super(view);

            txtName = (TextView) view.findViewById(R.id.txtCuisionName);
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

        holder.txtName.setText(list.get(position).getCuisine_name());
//        Toast.makeText(context, list.get(position).getCuisine_name() + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}