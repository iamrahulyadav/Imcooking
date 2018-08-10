package com.imcooking.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imcooking.Model.api.response.DishLikeData;
import com.imcooking.Model.api.response.SavedCardData;
import com.imcooking.R;
import com.imcooking.webservices.GetData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakhi.
 * Contact Number : +91 9958187463
 * Date : 10/08/2018
 */
public class AdatperSavedCard extends RecyclerView.Adapter<AdatperSavedCard.MyViewHolder> {

    private Context context;
    private List<SavedCardData.PaymentDetailsListBean>paymentDetailsListBeans ;

    public AdatperSavedCard(Context context, List<SavedCardData.PaymentDetailsListBean> paymentDetailsListBeans) {
        this.context = context;
        this.paymentDetailsListBeans = paymentDetailsListBeans;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

    public ImageView img;
    private TextView txtCardNo, txtCardDelete;

        public MyViewHolder(View view) {
            super(view);
            txtCardDelete = view.findViewById(R.id.item_saved_card_delete);
            txtCardNo = view.findViewById(R.id.item_saved_card_no);
    }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_saved_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.txtCardNo.setText(paymentDetailsListBeans.get(position).getPayment_details_card_number()+"");

    }

    @Override
    public int getItemCount() {
        return paymentDetailsListBeans.size();

    }
}