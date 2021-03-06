package com.imcooking.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.imcooking.Model.ApiRequest.AddToCart;
import com.imcooking.Model.api.response.AddCart;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.CartAddedItemList;
import com.imcooking.R;
import com.imcooking.activity.Sub.Foodie.CartActivity;
import com.imcooking.fragment.foodie.HomeFragment;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by RAkhi.
 * Contact Number : +91 9958187463
 */
public class CartAdatper extends RecyclerView.Adapter<CartAdatper.MyViewHolder> {

    private Context context;
    List<AddCart.AddDishBean> dishDetails = new ArrayList<>();
    static List<Double> pricelist = new ArrayList<Double>();
    CartInterface  cartInterface;


    public CartAdatper(Context context,
                       List<AddCart.AddDishBean> dishDetails,CartInterface cartInterface) {

        this.context = context;
        this.dishDetails = dishDetails;
        this.cartInterface = cartInterface;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView txtPlus,txtMinus,txt_DishPrice,txt_DishCount,txtDishName,txtTotal,txtTax;
        public ImageView imgDish, imgDelete;

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
            imgDelete = view.findViewById(R.id.delete_added_dish_from_cart);

            txtPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cartInterface.CartInterfaceMethod(getAdapterPosition(), "plus");
                }
            });
            txtMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cartInterface.CartInterfaceMethod(getAdapterPosition(), "minus");
                }
            });
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cartInterface.CartInterfaceMethod(getAdapterPosition(), "delete");
                }
            });
        }
    }

    public interface CartInterface {
        void CartInterfaceMethod(int position, String click_type);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_cart, parent, false);
        return new MyViewHolder(itemView);
    }

    MyViewHolder myHolder;

    List<CartAddedItemList> cartAddedItemLists;
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
    /*    myHolder=holder;

        final int[] count = {1};
        int dish_available = 0;
        final String[] dishCount = new String[1];
        final  String[] totalDishPrice = new String[1];
        final double[] price = {(Double.parseDouble(dishDetails.get(position).getDish_price()))};

        pricelist.add((count[0]* price[0]));
        //  getprice(pricelist);
        //   CartActivity.txtTotalprice.setText(String.valueOf(getprice(pricelist)));

        holder.txtPlus.setTag(position);
        holder.txtMinus.setTag(position);
        cartAddedItemLists = new ArrayList<>();
        if (dishDetails.get(position).getDish_available()!=null){
            dish_available = Integer.parseInt(dishDetails.get(position).getDish_available());

        }
        if (dishDetails.get(position).getDish_quantity_selected()!=null){
            int dish_quantity = Integer.parseInt(dishDetails.get(position).getDish_quantity_selected());
            holder.txt_DishCount.setText(dish_quantity+"");
        }
        final int finalDish_available1 = dish_available;
        count[0] = Integer.parseInt(holder.txt_DishCount.getText().toString().trim());

      *//*  holder.txtPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count[0]< finalDish_available1){
                    dishCount[0] = String.valueOf(++count[0]);
                    holder.txt_DishCount.setText(dishCount[0]);
                    totalDishPrice[0] = String.valueOf(count[0] * price[0]);
                    pricelist.set(position, count[0] * price[0]);
                    CartActivity.txtTotalprice.setText(String.valueOf(getprice(pricelist)));
                    holder.txt_DishPrice.setText("£" + totalDishPrice[0]);
                } else {
                    BaseClass.showToast(context, "Maximum Number available with chef");
                }
            }
        });

        holder.txtMinus.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                //String dishCount= String.valueOf(--count);
                if(count[0] >1)
                {   dishCount[0] = String.valueOf( --count[0]);
                    holder.txt_DishCount.setText(dishCount[0]);
                    totalDishPrice[0] = String.valueOf(count[0]* price[0]);
                    pricelist.set(position,(count[0]* price[0]));
                    CartActivity.txtTotalprice.setText(String.valueOf(getprice(pricelist)));
                    holder.txt_DishPrice.setText("£"+totalDishPrice[0]);
//                    cartAddedItemList.setDish_qyt(dishCount[0]);
                    holder.txt_DishCount.getText().toString().trim();
                }
                else{
                    Toast.makeText(context, "minimum item added", Toast.LENGTH_SHORT).show();
                }
            }
        });*//*
*/




//        if(dishDetails.get(position).getDish_quantity().equals("0")){
//            holder.txt_DishCount.setText("0");
//        } else {
            holder.txt_DishCount.setText(dishDetails.get(position).getDish_quantity_selected());
//        }
        holder.txtDishName.setText(dishDetails.get(position).getDish_name());
        Picasso.with(context).load(GetData.IMG_BASE_URL +
                dishDetails.get(position).getDish_image()).into(holder.imgDish);
        Double i = Double.parseDouble(dishDetails.get(position).getDish_price());
        Double j = Double.parseDouble(dishDetails.get(position).getDish_quantity_selected());

        holder.txt_DishPrice.setText("£" + (i * j));

    }

    @Override
    public int getItemCount() {
        return dishDetails.size();
    }

    public double getprice(List<Double> pricelist){
        int sum = 0;
        for(int i=0;i<pricelist.size();i++){
            sum += pricelist.get(i);
        }
        return sum;
    }


}