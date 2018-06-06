package com.imcooking.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.imcooking.Model.api.response.AddCart;
import com.imcooking.R;
import com.imcooking.activity.Sub.Foodie.CartActivity;
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
   static List<Integer> pricelist = new ArrayList<Integer>();
    //private List<CuisineData.CuisineDataBean>cuisineDataBeans = new ArrayList<>();

    public CartAdatper(Context context,
                       List<AddCart.AddDishBean> dishDetails) {
        this.context = context;
        this.dishDetails = dishDetails;
    }

    public CartAdatper() {
        getItemCount();

    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView txtPlus,txtMinus,txt_DishPrice,txt_DishCount,txtDishName,txtTotal,txtTax;
        public ImageView imgDish;

        //String dishname,dishimage,price;

        public MyViewHolder(View view) {
            super(view);

            txtDishName = view.findViewById(R.id.tv_dish_name);
            txt_DishCount=view.findViewById(R.id.tv_dish_count);
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
        final String[] dishCount = new String[1];
      final  String[] totalDishPrice = new String[1];
        final int[] price = {(dishDetails.get(position).getDish_price())};
      pricelist.add((count[0]* price[0]));
   //  getprice(pricelist);
        CartActivity.txtTotalprice.setText(String.valueOf(getprice(pricelist)));
        holder.txtPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dishCount[0] = String.valueOf(++count[0]);
                holder.txt_DishCount.setText(dishCount[0]);
               totalDishPrice[0] = String.valueOf(count[0]* price[0]);
                pricelist.set(position,count[0]* price[0]);
                CartActivity.txtTotalprice.setText(String.valueOf(getprice(pricelist)));
               holder.txt_DishPrice.setText("$"+totalDishPrice[0]);
            }
        });

        holder.txtMinus.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                //String dishCount= String.valueOf(--count);
                if(count[0] >1)
                {   dishCount[0] = String.valueOf(--count[0]);
                    holder.txt_DishCount.setText(dishCount[0]);
                    totalDishPrice[0] = String.valueOf(count[0]* price[0]);
                    pricelist.set(position,(count[0]* price[0]));
                    CartActivity.txtTotalprice.setText(String.valueOf(getprice(pricelist)));
                    holder.txt_DishPrice.setText("$"+totalDishPrice[0]);
                }
                else{
                    Toast.makeText(context, "minimum item added", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.txtDishName.setText(dishDetails.get(position).getDish_name());
        Picasso.with(context).load(GetData.IMG_BASE_URL +
                dishDetails.get(position).getDish_image()).into(holder.imgDish);
        holder.txt_DishPrice.setText("$"+String.valueOf(dishDetails.get(position).getDish_price()));
        //holder.txtTotal.setText(String.valueOf(getprice()));
    }

    @Override
    public int getItemCount() {
        return dishDetails.size();
    }

    public int getprice(List<Integer> pricelist){
       int sum = 0;
        for(int i=0;i<pricelist.size();i++){
            sum += pricelist.get(i);

        }
        return sum;
    }

}