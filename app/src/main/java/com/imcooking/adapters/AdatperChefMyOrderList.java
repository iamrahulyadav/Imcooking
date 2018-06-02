package com.imcooking.adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
public class AdatperChefMyOrderList extends RecyclerView.Adapter<AdatperChefMyOrderList.MyViewHolder> {

//    OnItemClickListenerCategory listener;
    private Context context;
    List<ChefMyorderList.MyOrderListBean>list=new ArrayList<>();


    public AdatperChefMyOrderList(Context context, FragmentManager fragmentManager, List<ChefMyorderList.MyOrderListBean> list) {
        this.context = context;
        this.list=list;

    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_view_response,txtPlacedon,txtOrderNo,txtTotalAmnt,txtEmail,
                txtPaymentMode,txtprice, txtfoodiename,txtdishname,txtqty;

public ImageView img;
        public MyViewHolder(View view) {
            super(view);
            txtEmail=view.findViewById(R.id.fragment_chef_order_list_email);
            txtPaymentMode =view.findViewById(R.id.fragment_chef_order_list_payment_mode);
            txtPlacedon=view.findViewById(R.id.fragment_chef_order_list_placedon);
            txtOrderNo=view.findViewById(R.id.fragment_chef_order_list_orderid);
            txtTotalAmnt=view.findViewById(R.id.fragment_chef_order_list_total_amnt);
            txtqty=view.findViewById(R.id.fragment_chef_order_list_qty);
            txtprice =view.findViewById(R.id.fragment_chef_order_list_price);
            txtfoodiename =view.findViewById(R.id.fragment_chef_order_list_foodie_name);
            txtdishname=view.findViewById(R.id.fragment_chef_order_list_dish_name);
            img=view.findViewById(R.id.fragment_chef_order_list_img);

    }


    }

    int row_index=-1;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chef_order_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.txtPlacedon.setText(list.get(position).getBookdate());
        holder.txtOrderNo.setText(list.get(position).getOrder_id());
        holder.txtdishname.setText(list.get(position).getFoodie_name());
        holder.txtfoodiename.setText(list.get(position).getFoodie_name());
        holder.txtprice.setText(list.get(position).getPrice());
       /* holder.txtqty.setText(list.get(position).);
        holder.txtEmail.setText(list.get(position).);*/
       // holder.txtTotalAmnt.setText(list.get(position).);
       // holder.txtPaymentMode.setText(list.get(position).);
        Picasso.with(context).load(GetData.IMG_BASE_URL +
                list.get(position).getFoodie_image()).into(holder.img);

        holder.tv_view_response.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}