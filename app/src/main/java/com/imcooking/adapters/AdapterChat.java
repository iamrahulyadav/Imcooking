package com.imcooking.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.imcooking.Model.api.response.ConversationData;
import com.imcooking.Model.api.response.FoodieMyRequest;
import com.imcooking.R;
import com.imcooking.utils.BaseClass;

import java.util.List;

/**
 * Created by RAkhi.
 * Contact Number : +91 9958187463
 */
public class AdapterChat extends RecyclerView.Adapter<AdapterChat.MyViewHolder> {

    private ConversationData conversationData;
    private String sender_id, reciever_id;

    public AdapterChat(ConversationData conversationData, String sender_id, String reciever_id) {

        this.conversationData = conversationData;
        this.sender_id = sender_id;
        this.reciever_id = reciever_id;

        Log.d("ConversationMessage", sender_id);
        Log.d("ConversationMessage", reciever_id);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_left, tv_right;

        public MyViewHolder(View view) {
            super(view);

            tv_left = view.findViewById(R.id.item_dialog_chat_message_left);
            tv_right = view.findViewById(R.id.item_dialog_chat_message_right);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dialog_chat, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        int pos = conversationData.getConversation_msg().size() - 1 - position;
        Log.d("ConversationMessage", pos + "");

        if(conversationData.getConversation_msg().get(pos)
                .getConversation_sender_id().equals(sender_id)){
            holder.tv_right.setVisibility(View.VISIBLE);
            holder.tv_left.setVisibility(View.GONE);
            holder.tv_right.setText(conversationData.getConversation_msg()
                    .get(pos).getConversation_message());
        } else if(conversationData.getConversation_msg()
                .get(pos)
                .getConversation_sender_id().equals(reciever_id)) {
            holder.tv_right.setVisibility(View.GONE);
            holder.tv_left.setVisibility(View.VISIBLE);
            holder.tv_left.setText(conversationData.getConversation_msg()
                    .get(pos).getConversation_message());

        } else {
//            BaseClass.showToast();
        }
    }

    @Override
    public int getItemCount() {
        return conversationData.getConversation_msg().size();
    }



}