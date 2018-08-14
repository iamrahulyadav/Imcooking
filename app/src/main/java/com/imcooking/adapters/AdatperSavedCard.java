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
    private AdapterSaveCardInterface saveCardInterface;

    public AdatperSavedCard(Context context, List<SavedCardData.PaymentDetailsListBean> paymentDetailsListBeans,
                            AdapterSaveCardInterface saveCardInterface) {
        this.context = context;
        this.paymentDetailsListBeans = paymentDetailsListBeans;
        this.saveCardInterface = saveCardInterface;
    }

    public interface AdapterSaveCardInterface{
        void clickCard(String edit, int pos);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

    public ImageView txtCardDelete, iv_edit;
    private TextView txtCardNo;

        public MyViewHolder(View view) {
            super(view);
            txtCardDelete = view.findViewById(R.id.item_saved_card_delete);
            iv_edit = view.findViewById(R.id.item_saved_card_edit);
            txtCardNo = view.findViewById(R.id.item_saved_card_no);

            iv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveCardInterface.clickCard("edit",getAdapterPosition());
                }
            });

            txtCardDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveCardInterface.clickCard("delete", getAdapterPosition());
                }
            });

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

        String str = getEncrypted(paymentDetailsListBeans.get(position).getPayment_details_card_number());
        holder.txtCardNo.setText(str + "");

    }

    @Override
    public int getItemCount() {
        return paymentDetailsListBeans.size();

    }

    private String getEncrypted(String s){

        String str = s.substring(0, 2) + "XXXXXXXXXXXX" + s.substring(14);
        return str;
    }
}