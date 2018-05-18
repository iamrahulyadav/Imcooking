package com.imcooking.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.imcooking.Model.api.response.AddCart;
import com.imcooking.R;
import com.imcooking.webservices.GetData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class CartAdatper extends RecyclerView.Adapter<CartAdatper.MyViewHolder> {

//    OnItemClickListenerCategory listener;
    private Context context;
    List<AddCart.AddDishBean> dishDetails = new ArrayList<>();
    List<String[]> cartAdatperslist = new ArrayList<String[]>();
    //private List<CuisineData.CuisineDataBean>cuisineDataBeans = new ArrayList<>();

    public CartAdatper(Context context,
                       List<AddCart.AddDishBean> dishDetails) {
        this.context = context;
        this.dishDetails = dishDetails;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView txtPlus,txtMinus,txt_DishPrice,txt_DishCount,txtDishName,txtTotal,txtTax;
        public ImageView imgDish;

        //String dishname,dishimage,price;

        public MyViewHolder(View view) {
            super(view);

            txtDishName = view.findViewById(R.id.tv_dish_name);
            txt_DishCount=view.findViewById(R.id.tv_dish_count);
            txtTotal=view.findViewById(R.id.tv_total);
            txtTax=view.findViewById(R.id.tv_tax);
            txt_DishPrice=view.findViewById(R.id.tv_dish_price);
            imgDish=view.findViewById(R.id.img_dish);
            txtPlus=view.findViewById(R.id.tv_plus);
            txtMinus=view.findViewById(R.id.tv_minus);
            //txtDishName.setOnClickListener(this);
        }


    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_cart, parent, false);

        return new MyViewHolder(itemView);
    }

    MyViewHolder myHolder;
    int pos;
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        myHolder=holder;
        final int[] count = {1};
        final String[] cnt = new String[1];
        final  String[] cnt2 = new String[1];
        final int[] price = {(dishDetails.get(position).getDish_price())};

        holder.txtMinus.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                //String cnt= String.valueOf(--count);
                if(count[0] >0)
                {   cnt[0] = String.valueOf(--count[0]);
                    holder.txt_DishCount.setText(cnt[0]);
                    cnt2[0] = String.valueOf(count[0]* price[0]);
                    //cartAdatperslist.add(cnt2);
              holder.txt_DishPrice.setText("$"+cnt2[0]);
                }
                else{
                    Toast.makeText(context, "no item added", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.txtPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               cnt[0] = String.valueOf(++count[0]);
                holder.txt_DishCount.setText(cnt[0]);
               cnt2[0] = String.valueOf(count[0]* price[0]);
                //cartAdatperslist.add(cnt2);
               holder.txt_DishPrice.setText("$"+cnt2[0]);


            }
        });

        holder.txtDishName.setText(dishDetails.get(position).getDish_name());
        Picasso.with(context).load(GetData.IMG_BASE_URL +
                dishDetails.get(position).getDish_image()).into(holder.imgDish);
        holder.txt_DishPrice.setText(String.valueOf(dishDetails.get(position).getDish_price()));
        //holder.txtTotal.setText(String.valueOf(getprice()));



    }

    @Override
    public int getItemCount() {
        return dishDetails.size();
    }
    public String[] getprice(){
        String[] sum = {};
        for(int i=0;i<=getItemCount();i++){
            //sum=sum[0]+;

        }
        return sum;
    }

}