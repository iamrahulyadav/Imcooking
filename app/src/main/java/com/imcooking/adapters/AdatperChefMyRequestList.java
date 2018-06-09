package com.imcooking.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imcooking.Model.api.response.ChefMyRequestList;
import com.imcooking.Model.api.response.ChefMyorderList;
import com.imcooking.R;
import com.imcooking.webservices.GetData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class AdatperChefMyRequestList extends RecyclerView.Adapter<AdatperChefMyRequestList.MyViewHolder> {

//    OnItemClickListenerCategory listener;
    private Context context;
    List<ChefMyRequestList.ChefDishDetailsBean>list=new ArrayList<>();


    public AdatperChefMyRequestList(Context context, List<ChefMyRequestList.ChefDishDetailsBean> list) {
        this.context = context;
        this.list=list;

    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_view_response, txtTime, txtAddress,txtTotalAmnt,txtEmail,
                txtCategory,txtmob, txtfoodiename,txtdishname,txtqty;
                public ImageView img;
        public MyViewHolder(View view) {
            super(view);
            txtEmail=view.findViewById(R.id.item_chef_my_request_email);
            txtCategory =view.findViewById(R.id.item_chef_my_request_food_category);
            txtTime =view.findViewById(R.id.item_chef_my_request_time);
            txtAddress =view.findViewById(R.id.item_chef_my_request_address);
            txtTotalAmnt=view.findViewById(R.id.item_chef_my_request_qty);
            txtqty=view.findViewById(R.id.fragment_chef_order_list_qty);
            txtfoodiename =view.findViewById(R.id.item_chef_my_request_name);
            txtdishname=view.findViewById(R.id.item_chef_my_request_dishName);
            img=view.findViewById(R.id.item_chef_my_request_profile_img);
            txtmob=view.findViewById(R.id.item_chef_my_request_phone);

    }
    }

    int row_index=-1;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chef_my_request, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.txtTime.setText(list.get(position).getRequest_date());
        holder.txtAddress.setText(list.get(position).getFoodie_address()+"");
      holder.txtdishname.setText(list.get(position).getDish_name());
        holder.txtfoodiename.setText(list.get(position).getFoodie_name());
       // holder.txtprice.setText(list.get(position).+"");
      //  holder.txtTotalAmnt.setText(list.get(position).);
        holder.txtCategory.setText(list.get(position).getRequest_cusine_name());
      //  holder.txtqty.setText(list.get(position).getRequest_quantity()+"");
        holder.txtEmail.setText(list.get(position).getFoodie_email());
        holder.txtmob.setText(list.get(position).getFoodie_phone()+"");
    /*    Picasso.with(context).load(GetData.IMG_BASE_URL +
                list.get(position).ge()).into(holder.img);
    *//*    holder.tv_view_response.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    */}

    @Override
    public int getItemCount() {
        return list.size();
    }
}