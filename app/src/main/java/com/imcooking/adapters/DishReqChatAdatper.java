package com.imcooking.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imcooking.Model.api.response.AddCart;
import com.imcooking.Model.api.response.CartAddedItemList;
import com.imcooking.Model.api.response.FoodieMyRequest;
import com.imcooking.R;
import com.imcooking.fragment.foodie.HomeFragment;
import com.imcooking.webservices.GetData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RAkhi.
 * Contact Number : +91 9958187463
 */
public class DishReqChatAdatper extends RecyclerView.Adapter<DishReqChatAdatper.MyViewHolder> {

    private Context context;
    private String chef_id, foodie_id;
    private List<FoodieMyRequest.FoodieRequestDishChefDetailsBean.ConversationDetailsBean> conversationDetailsBeans;

    public DishReqChatAdatper(Context context,  List<FoodieMyRequest.FoodieRequestDishChefDetailsBean
            .ConversationDetailsBean> conversationDetailsBeans, String chef_id, String foodie_id) {
        this.context = context;
        this.conversationDetailsBeans = conversationDetailsBeans;
        this.chef_id = chef_id;
        this.foodie_id = foodie_id;
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
        if (String.valueOf(conversationDetailsBeans.get(position).getConversation_sender_id()).equalsIgnoreCase(foodie_id)){
            if (conversationDetailsBeans.get(position).getConversation_message()!=null){
                holder.txt_foodie.setText(conversationDetailsBeans.get(position).getConversation_message());
            }
        } else if (String.valueOf(conversationDetailsBeans.get(position).getConversation_sender_id())
                .equalsIgnoreCase(chef_id)){
            if (conversationDetailsBeans.get(position).getConversation_message()!=null){
                holder.txt_chef.setText(conversationDetailsBeans.get(position).getConversation_message());
            }
        }
    }

    @Override
    public int getItemCount() {
        return conversationDetailsBeans.size();
    }


}