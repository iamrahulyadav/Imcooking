package com.imcooking.adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.imcooking.Model.api.response.ChefFollowers;
import com.imcooking.Model.api.response.ChefMyorderList;
import com.imcooking.R;
import com.imcooking.customtext.CustomTextView;
import com.imcooking.webservices.GetData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class AdatperChefFollowers extends RecyclerView.Adapter<AdatperChefFollowers.MyViewHolder> {

//    OnItemClickListenerCategory listener;
    private Context context;
  private   List<ChefFollowers.FoodieDetailsListBean>list=new ArrayList<>();


    public AdatperChefFollowers(Context context, FragmentManager fragmentManager, List<ChefFollowers.FoodieDetailsListBean> list) {
        this.context = context;
        this.list=list;

    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_view_response,txtPhone,txtEmail,txtaddress, txtfoodiename;

public ImageView img;
        public MyViewHolder(View view) {
            super(view);
            txtEmail=view.findViewById(R.id.item_chef_followers_email);
            txtaddress=view.findViewById(R.id.item_chef_followers_address);
            txtPhone=view.findViewById(R.id.followers_mob_number);
            txtfoodiename =view.findViewById(R.id.item_chef_followers__name);
            img=view.findViewById(R.id.item_chef_followers_foodie_image);

    }
    }

    int row_index=-1;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chef_followers, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if(holder.img!=null && holder.txtfoodiename!=null && holder.txtaddress!=null
                && holder.txtEmail!=null && holder.txtPhone!=null){
           holder.txtPhone.setText(list.get(position).getFoodie_phone()+"");
          //  Toast.makeText(context, list.get(position).getFoodie_phone(), Toast.LENGTH_SHORT).show();
            holder.txtaddress.setText(list.get(position).getFoodie_address());
            holder.txtEmail.setText(list.get(position).getFoodie_email());
            holder.txtfoodiename.setText(list.get(position).getFoodie_name());
            Picasso.with(context).load(GetData.IMG_BASE_URL +
                    list.get(position).getFoodie_image()).into(holder.img);

        }
/*
        holder.tv_view_response.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });*/
    }

    @Override
    public int getItemCount() {
        return list.size();

    }
}