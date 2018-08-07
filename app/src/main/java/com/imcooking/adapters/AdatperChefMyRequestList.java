package com.imcooking.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imcooking.Model.api.response.ChefDishRequestData;
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

    private Context context;
    List<ChefDishRequestData.ChefDishDetailsBean>list=new ArrayList<>();
    ChefMyrequestInterface chefMyrequestInterface;

    public interface ChefMyrequestInterface{
        void setresponse(int pos, String TAG);
    }

    public AdatperChefMyRequestList(Context context, List<ChefDishRequestData.ChefDishDetailsBean> list,
                                    ChefMyrequestInterface chefMyrequestInterface) {
        this.context = context;
        this.list=list;
        this.chefMyrequestInterface = chefMyrequestInterface;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView txt_reply, txtTime, txtAddress,txtTotalAmnt,txtEmail,
                txtCategory,txt_view,txtPhone, txtfoodiename,txtdishname,txtqty;
        public ImageView img;

        public MyViewHolder(View view) {
            super(view);
            txtEmail=view.findViewById(R.id.item_chef_my_request_email);
            txtCategory =view.findViewById(R.id.item_chef_my_request_food_category);
            txtTime =view.findViewById(R.id.item_chef_my_request_time);
            txtAddress =view.findViewById(R.id.item_chef_my_request_address);

            txtqty=view.findViewById(R.id.item_chef_my_request_qty);
            txtfoodiename =view.findViewById(R.id.item_chef_my_request_name);
            txtdishname=view.findViewById(R.id.item_chef_my_request_dishName);
            img=view.findViewById(R.id.item_chef_my_request_profile_img);
            txtPhone = view.findViewById(R.id.item_chef_my_request_phone);
            txt_reply = view.findViewById(R.id.item_chef_my_request_reply);
            txt_view = view.findViewById(R.id.item_chef_my_request_view);

            txt_reply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chefMyrequestInterface.setresponse(getAdapterPosition(), "reply");
                }
            });

            txt_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chefMyrequestInterface.setresponse(getAdapterPosition(), "view");
                }
            });
            }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chef_my_request, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.txtTime.setText(list.get(position).getRequest_date());
        holder.txtAddress.setText(list.get(position).getFoodie_address()+"");
        holder.txtdishname.setText(list.get(position).getDish_name());
        holder.txtfoodiename.setText(list.get(position).getFoodie_name());
        holder.txtEmail.setText(list.get(position).getFoodie_email());
        if (list.get(position).getFoodie_phone()!=null)
            holder.txtPhone.setText(list.get(position).getFoodie_phone());
        holder.txtCategory.setText(list.get(position).getRequest_cusine_name());
        if (list.get(position).getRequest_quantity()!=null)
            holder.txtqty.setText(list.get(position).getRequest_quantity());
        holder.txtTime.setText(list.get(position).getRequest_date());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}