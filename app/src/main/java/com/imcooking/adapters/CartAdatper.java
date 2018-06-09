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
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class CartAdatper extends RecyclerView.Adapter<CartAdatper.MyViewHolder> {
    private Context context;
    List<AddCart.AddDishBean> dishDetails = new ArrayList<>();
   static List<Double> pricelist = new ArrayList<Double>();
   public String chef_id;
    Activity activity;
    public CartAdatper(Context context,
                       List<AddCart.AddDishBean> dishDetails, Activity activity, String chef_id) {
        this.context = context;
        this.dishDetails = dishDetails;
        this.activity = activity;
        this.chef_id = chef_id;
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

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, ""+getAdapterPosition(), Toast.LENGTH_SHORT).show();
                  //  deleteData(getAdapterPosition());
                }
            });
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
    List<CartAddedItemList> cartAddedItemLists;
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        myHolder=holder;
        final int[] count = {1};
        final String[] dishCount = new String[1];
      final  String[] totalDishPrice = new String[1];
        final double[] price = {(Double.parseDouble(dishDetails.get(position).getDish_price()))};

      pricelist.add((count[0]* price[0]));
   //  getprice(pricelist);
        CartActivity.txtTotalprice.setText(String.valueOf(getprice(pricelist)));

        holder.txtPlus.setTag(position);
        holder.txtMinus.setTag(position);
        cartAddedItemLists = new ArrayList<>();
        holder.txtPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CartAddedItemList cartAddedItemList = new CartAddedItemList();
                dishCount[0] = String.valueOf(++count[0]);
                holder.txt_DishCount.setText(dishCount[0]);
               totalDishPrice[0] = String.valueOf(count[0]* price[0]);
                pricelist.set(position,count[0]* price[0]);
                CartActivity.txtTotalprice.setText(String.valueOf(getprice(pricelist)));
               holder.txt_DishPrice.setText("$"+totalDishPrice[0]);
                cartAddedItemList.setDish_qyt(dishCount[0]);
                cartAddedItemList.setDish_id(dishDetails.get((int)holder.txtPlus.getTag()).getDish_id()+"");
                cartAddedItemList.setDish_price(dishDetails.get((int)holder.txtPlus.getTag()).getDish_price());
                cartAddedItemList.setDish_qyt(holder.txt_DishCount.getText().toString());
                cartAddedItemList.setPosition((int)holder.txtPlus.getTag());
                cartAddedItemLists.add(cartAddedItemList);

                Set<CartAddedItemList> catBeans1 = new TreeSet<>(new Comparator<CartAddedItemList>() {
                    @Override
                    public int compare(CartAddedItemList catBean,CartAddedItemList t1) {
                        if(catBean.getPosition()==(t1.getPosition())){
                            if (Integer.parseInt(catBean.getDish_qyt())>Integer.parseInt(t1.getDish_qyt())){
                            }
                            return 0;
                        }
                        return 1;
                    }
                });
                catBeans1.addAll(cartAddedItemLists);
                cartAddedItemLists.clear();
                cartAddedItemLists.addAll(catBeans1);

                Log.d("TAG", "Rakhi: "+new Gson().toJson(cartAddedItemLists));
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
//                    cartAddedItemList.setDish_qyt(dishCount[0]);
                    holder.txt_DishCount.getText().toString().trim();

                    Log.d("TAG", "Rakhi: "+holder.txt_DishCount.getText().toString().trim()+(int)holder.txtPlus.getTag());
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

    public double getprice(List<Double> pricelist){
       int sum = 0;
        for(int i=0;i<pricelist.size();i++){
            sum += pricelist.get(i);
        }
        return sum;
    }


    private void deleteData(int pos){

        String dishId=dishDetails.get(pos).getDish_id()+"";
        AddToCart addToCart=new AddToCart();
        addToCart.setChef_id(Integer.parseInt(chef_id));
        addToCart.setFoodie_id(Integer.parseInt(HomeFragment.foodie_id));
        addToCart.setDish_id(dishId);
        addToCart.setAddcart_id(dishDetails.get(pos).getAddcart_id()+"");
        try {
            JSONObject jsonObject = new JSONObject(new Gson().toJson(addToCart));
            new GetData(context, activity).sendMyData(jsonObject, GetData.ADD_CART, activity, new GetData.MyCallback() {
                @Override
                public void onSuccess(String result) {
                    Log.d(CartAdatper.class.getName(), "Rakhi: "+result);
                    notifyDataSetChanged();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}