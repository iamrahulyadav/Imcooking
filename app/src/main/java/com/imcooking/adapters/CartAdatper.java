package com.imcooking.adapters;

import android.annotation.SuppressLint;
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
    static int count=0;
    List<AddCart.AddDishBean> dishDetails = new ArrayList<>();

    //private List<CuisineData.CuisineDataBean>cuisineDataBeans = new ArrayList<>();

    public CartAdatper(Context context,
                       List<AddCart.AddDishBean> dishDetails) {
        this.context = context;
        this.dishDetails = dishDetails;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView txtPlus,txtMinus,txt_DishCount,txtDishName;
        public ImageView imgDish;
        //String dishname,dishimage,price;

        public MyViewHolder(View view) {
            super(view);

            txtDishName = view.findViewById(R.id.tv_dish_name);
            txt_DishCount=view.findViewById(R.id.tv_dish_count);
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

        /*holder.txtMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cnt= String.valueOf(--count);
                if(cnt.equals("0"))
                {

                    Toast.makeText(context, "no item added", Toast.LENGTH_SHORT).show();
                }
                else{
                    holder.txt_DishCount.setText(count);
                }
            }
        });
        holder.txtPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cnt= String.valueOf(++count);
                holder.txt_DishCount.setText(cnt);
            }
        });*/
//        holder.txt_DishCount.setTag(position);
        holder.txtDishName.setText(dishDetails.get(position).getDish_name());
        Picasso.with(context).load(GetData.IMG_BASE_URL +
                dishDetails.get(position).getDish_image()).into(holder.imgDish);


    }

    @Override
    public int getItemCount() {
        return dishDetails.size();
    }
}