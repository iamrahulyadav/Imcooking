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

    private SearchClickInterface click;

    public AdapterChefSearch(Context context, ArrayList<String> arr_id, ArrayList<String> arr_name
            , ArrayList<String> arr_type, SearchClickInterface click) {
        this.context = context;
        this.arr_id=arr_id;
        this.arr_name=arr_name;
        this.arr_type=arr_type;
        this.click = click;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_view_response;
        public MyViewHolder(View view) {
            super(view);

            tv_view_response = view.findViewById(R.id.item_forsear_recycler_tv_list);

            tv_view_response.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    click.search_click(getAdapterPosition());
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_forsearch_recycler, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv_view_response.setText(arr_name.get(position));

    }

    @Override
    public int getItemCount() {
        return arr_id.size();
    }

    public interface SearchClickInterface {
        public void search_click(int position);
    }
}