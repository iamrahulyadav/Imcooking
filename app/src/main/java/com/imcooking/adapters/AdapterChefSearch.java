package com.imcooking.adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.imcooking.Model.api.response.ChefAndDish;
import com.imcooking.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterChefSearch extends RecyclerView.Adapter<AdapterChefSearch.MyViewHolder> {

//    OnItemClickListenerCategory listener;
    private Context context;

    private ArrayList<String> arr_id = new ArrayList<>();
    private ArrayList<String> arr_name = new ArrayList<>();
    private ArrayList<String> arr_type = new ArrayList<>();
    public AdapterChefSearch(Context context, List<String> listChef) {
        this.context = context;
       // chefAndDishList=listChef;
    }

    public AdapterChefSearch(Context context, ArrayList<String> arr_id, ArrayList<String> arr_name, ArrayList<String> arr_type) {
        this.context = context;
        this.arr_id=arr_id;
        this.arr_name=arr_name;
        this.arr_type=arr_type;

    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_view_response;


        public MyViewHolder(View view) {
            super(view);

            tv_view_response = view.findViewById(R.id.item_forsear_recycler_tv_list);
        }


    }

    int row_index=-1;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_forsearch_recycler, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


            holder.tv_view_response.setText(arr_name.get(position));


        holder.tv_view_response.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

if(arr_type.get(0).equals("User")){
    Toast.makeText(context, ""+"id:"+arr_id.get(position)+"name:"+arr_name.get(position), Toast.LENGTH_SHORT).show();
}
else if(arr_type.get(0).equals("Dish")){
    Toast.makeText(context, ""+"id:"+arr_id.get(position)+"name:"+arr_name.get(position), Toast.LENGTH_SHORT).show();
}
else {
    Toast.makeText(context, "sorrry", Toast.LENGTH_SHORT).show();
}
            }
        });
    }

    @Override
    public int getItemCount() {
        return arr_id.size();
    }

    public class OnItemClickListener {

    }
}