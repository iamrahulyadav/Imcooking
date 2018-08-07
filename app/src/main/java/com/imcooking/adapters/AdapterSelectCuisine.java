package com.imcooking.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.imcooking.R;

import java.util.ArrayList;

public class AdapterSelectCuisine extends RecyclerView.Adapter<AdapterSelectCuisine.MyViewHolder> {

    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayList<String> arrayList_status = new ArrayList<>();

    private Interface_select_cuisine click;
    private Context context;

    public AdapterSelectCuisine(ArrayList<String> arrayList, ArrayList<String> arrayList_status, Context context,
                                Interface_select_cuisine click) {

        this.arrayList = arrayList;
        this.arrayList_status = arrayList_status;
        this.click = click;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView tv; ImageView iv;
        public RelativeLayout select;
        public MyViewHolder(View view) {
            super(view);

            tv = view.findViewById(R.id.item_select_cuisine_text);
            iv = view.findViewById(R.id.item_select_cuisine_check_image);
            select = view.findViewById(R.id.item_select_cuisine_click);

            select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    click.method_select_cuisine(getAdapterPosition());
                }
            });

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_select_cuisine, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv.setText(arrayList.get(position));
        if(arrayList_status.get(position).equals("0")){
            holder.iv.setImageResource(R.drawable.ic_tick_white);
            holder.tv.setTextColor(context.getResources().getColor(R.color.text_color_4));
        } else{
            holder.iv.setImageResource(R.drawable.ic_tick_theme);
            holder.tv.setTextColor(context.getResources().getColor(R.color.theme_color));
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public interface Interface_select_cuisine{

        public void method_select_cuisine(int position);
    }
}