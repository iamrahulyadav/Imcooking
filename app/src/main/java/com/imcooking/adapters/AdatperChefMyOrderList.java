package com.imcooking.adapters;

import android.annotation.SuppressLint;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class AdatperChefMyOrderList extends RecyclerView.Adapter<AdatperChefMyOrderList.MyViewHolder> {

    ChefMyOrderInterface chefMyOrderInterface;
    private Context context;
    List<ChefMyorderList.MyOrderListBean>list=new ArrayList<>();
    String TAG ;

    public AdatperChefMyOrderList(Context context,
                                  List<ChefMyorderList.MyOrderListBean> list, ChefMyOrderInterface chefMyOrderInterface,
                                  String TAG) {
        this.context = context;
        this.list=list;
        this.chefMyOrderInterface = chefMyOrderInterface;
        this.TAG = TAG;
    }

    public interface ChefMyOrderInterface{
        void chefOrderdetails(int pos, String TAG);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_view_response,txt_order_status, txtPlacedon,txtOrderNo,txtTotalAmnt,txtEmail,
                txtPaymentMode,txtprice, txtfoodiename,txtdishname,txtTime,txtqty;



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
            txt_order_status = view.findViewById(R.id.item_chef_my_order_status);
            tv_view_response = view.findViewById(R.id.item_chef_order_response);
            txtTime = view.findViewById(R.id.fragment_chef_order_list_time);

            tv_view_response.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chefMyOrderInterface.chefOrderdetails(getAdapterPosition(), TAG);
                }
            });
            }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chef_order_list, parent, false);

        return new MyViewHolder(itemView); }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.txtPlacedon.setText(list.get(position).getBookdate());
        holder.txtOrderNo.setText("#"+list.get(position).getOrder_order_id()+"");
        holder.txtdishname.setText(list.get(position).getFoodie_name());
        holder.txtfoodiename.setText(list.get(position).getDish_name());
        holder.txtprice.setText("£"+list.get(position).getPrice()+"");
        if (list.get(position).getOrder_status()!=null){
            String status = list.get(position).getOrder_status();
            if (status.equals("0"))
                holder.txt_order_status.setText("Order Placed");
            else if (status.equals("1"))
                holder.txt_order_status.setText("Accept");
            else if (status.equals("2"))
                holder.txt_order_status.setText("Decline");
            else if (status.equals("3"))
                holder.txt_order_status.setText("In Prepration");
            else if (status.equals("4"))
                holder.txt_order_status.setText("Ready to Delivery");
            else if (status.equals("5"))
                holder.txt_order_status.setText("On Way");
            else if (status.equals("8"))
                holder.txt_order_status.setText("Delivered");
            else if (status.equals("9"))
                holder.txt_order_status.setText("Not Delivered");
        }

        holder.txtqty.setText("Qyt : "+list.get(position).getDish_qyt());
        holder.txtqty.setVisibility(View.VISIBLE);
        float price = Integer.parseInt(list.get(position).getDish_qyt())*Float.parseFloat(list.get(position).getPrice());
        holder.txtTotalAmnt.setText("£"+price);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date myDate = null;
        try {
            myDate = timeFormat.parse(list.get(position).getBookdate());

        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "MMM dd, yyyy HH:mm a");
        String finalDate = dateFormat.format(myDate);
        holder.txtTime.setText(finalDate);

       Picasso.with(context).load(GetData.IMG_BASE_URL +
                list.get(position).getFoodie_image()).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}