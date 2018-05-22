package com.imcooking.adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.imcooking.R;
import com.imcooking.fragment.foodie.FoodieOrderDetails;
import com.imcooking.utils.BaseClass;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class AdapterFoodieMyOrder extends RecyclerView.Adapter<AdapterFoodieMyOrder.MyViewHolder> {

    private Context context;
    private FragmentManager manager;

    public AdapterFoodieMyOrder(Context context, FragmentManager manager) {
        this.context = context;
        this.manager = manager;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_order_details;

        public MyViewHolder(View view) {
            super(view);

            tv_order_details = view.findViewById(R.id.item_foodie_orders_order_details);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_foodie_my_orders, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv_order_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BaseClass.callFragment1(new FoodieOrderDetails(), new FoodieOrderDetails().getClass().getName(), manager);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 15;
    }
}