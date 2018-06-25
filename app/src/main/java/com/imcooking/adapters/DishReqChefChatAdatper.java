package com.imcooking.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.imcooking.Model.api.response.ChefDishRequestData;
import com.imcooking.Model.api.response.FoodieMyRequest;
import com.imcooking.R;

import java.util.List;

/**
 * Created by RAkhi.
 * Contact Number : +91 9958187463
 */
public class DishReqChefChatAdatper extends RecyclerView.Adapter<DishReqChefChatAdatper.MyViewHolder> {

    private Context context;
    private String chef_id, foodie_id;
    private List<ChefDishRequestData.ChefDishDetailsBean.ConversationDetailsBean> conversationDetailsBeans;


    public DishReqChefChatAdatper(Context context, String chef_id, String foodie_id,
                                  List<ChefDishRequestData.ChefDishDetailsBean.ConversationDetailsBean> conversationDetailsBeans) {
        this.context = context;
        this.chef_id = chef_id;
        this.foodie_id = foodie_id;
        this.conversationDetailsBeans = conversationDetailsBeans;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView txt_foodie,txt_chef;

        public MyViewHolder(View view) {
            super(view);
            txt_foodie = view.findViewById(R.id.item_chat_view_txt_foodie);
            txt_chef=view.findViewById(R.id.item_chat_view_txt_chef);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        String id = conversationDetailsBeans.get(position).getConversation_sender_id() + "";
        if(id.equals(chef_id)) {
            holder.txt_foodie.setText(conversationDetailsBeans.get(position).getConversation_message());
            holder.txt_chef.setVisibility(View.GONE);
            holder.txt_foodie.setVisibility(View.VISIBLE);
        } else{
            holder.txt_chef.setText(conversationDetailsBeans.get(position).getConversation_message());
            holder.txt_foodie.setVisibility(View.GONE);
            holder.txt_chef.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return conversationDetailsBeans.size();
    }


}