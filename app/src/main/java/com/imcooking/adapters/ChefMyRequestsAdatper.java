package com.imcooking.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.imcooking.Model.api.response.AddCart;
import com.imcooking.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class ChefMyRequestsAdatper extends RecyclerView.Adapter<ChefMyRequestsAdatper.MyViewHolder> {

//    OnItemClickListenerCategory listener;
    private Context context;


    //private List<CuisineData.CuisineDataBean>cuisineDataBeans = new ArrayList<>();

    public ChefMyRequestsAdatper(Context context) {
        this.context = context;

    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_view_response;


        public MyViewHolder(View view) {
            super(view);

    //        tv_view_response = view.findViewById(R.id.item_chef_dish_requests_view_response);
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

      /*  holder.tv_view_response.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_view_response);
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(null);
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}