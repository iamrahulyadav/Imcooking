package com.imcooking.adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.imcooking.Model.api.response.FoodieMyRequest;
import com.imcooking.R;
import com.imcooking.webservices.GetData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class AdapterFoodieMyRequest extends RecyclerView.Adapter<AdapterFoodieMyRequest.MyViewHolder> {

    private Context context;
    List<FoodieMyRequest.FoodieRequestDishChefDetailsBean> list=new ArrayList<>();
    FoodieMyRequestInterface foodieMyRequestInterface;

    public AdapterFoodieMyRequest(Context context, List<FoodieMyRequest.FoodieRequestDishChefDetailsBean> list,
                                  FoodieMyRequestInterface foodieMyRequestInterface) {
        this.context = context;
        this.list=list;
        this.foodieMyRequestInterface = foodieMyRequestInterface;
    }

    public interface FoodieMyRequestInterface{
        void method_AdapterFoodieMyRequest(int position, String tag);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView /*tv_view_response*/txtCategory,txtqty,txt_name,txt_address,txt_email,txt_time, txt_status,txt_dish_name, txt_phn;
        public RatingBar ratingBar;
        public ImageView img_profile;
        public LinearLayout layout_accept_decline, layout_cancel;
        public TextView tv_reply, tv_accept, tv_declie, tv_offered_price, tv_cancel;

        public MyViewHolder(View view) {
            super(view);

            layout_cancel = view.findViewById(R.id.item_foodie_my_request_layout_cancel);
            layout_accept_decline = view.findViewById(R.id.item_foodie_my_request_layout_accept_decline);

            tv_cancel = view.findViewById(R.id.item_foodie_my_requests_cancel);
            tv_reply = view.findViewById(R.id.item_foodie_my_request_reply);
            tv_accept = view.findViewById(R.id.item_foodie_my_requests_accept);
            tv_declie = view.findViewById(R.id.item_foodie_my_requests_decline);
            tv_offered_price = view.findViewById(R.id.item_foodie_my_request_offered_price);


            txtCategory =view.findViewById(R.id.item_chef_my_request_food_category);
            txtqty=view.findViewById(R.id.item_chef_my_request_qty);
//            tv_view_response = view.findViewById(R.id.item_foodie_my_requests_view_response);
            txt_name = view.findViewById(R.id.item_foodie_my_request_name);
            txt_address = view.findViewById(R.id.item_foodie_my_request_address);
            txt_email = view.findViewById(R.id.item_foodie_my_request_email);
            txt_time = view.findViewById(R.id.item_foodie_my_request_time);
            img_profile=view.findViewById(R.id.item_foodie_my_request_profile_image);
            ratingBar=view.findViewById(R.id.item_foodie_my_request_rating);
            txt_phn = view.findViewById(R.id.item_foodie_my_request_mob_number);
            txt_status = view.findViewById(R.id.item_foodie_my_request_txtStatus);
            txt_dish_name = view.findViewById(R.id.item_foodie_my_request_dish_name);

            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    foodieMyRequestInterface.method_AdapterFoodieMyRequest(getAdapterPosition(), "cancel");
                }
            });
            tv_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    foodieMyRequestInterface.method_AdapterFoodieMyRequest(getAdapterPosition(), "accept");
                }
            });
            tv_declie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    foodieMyRequestInterface.method_AdapterFoodieMyRequest(getAdapterPosition(), "decline");
                }
            });
            tv_reply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    foodieMyRequestInterface.method_AdapterFoodieMyRequest(getAdapterPosition(), "reply");
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_foodie_my_requests, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
       // FoodieMyRequest foodie=new FoodieMyRequest();

        if(list.get(position).getConversation_details()!=null){
            if(list.get(position).getConversation_details().size()>0){
                holder.tv_reply.setVisibility(View.VISIBLE);
            } else{
                holder.tv_reply.setVisibility(View.GONE);
            }
        }

        if(list.get(position).getChef_response()!=null) {
            if (list.get(position).getChef_response().equals("0")){
                holder.txt_status.setText("New Request");
                holder.txt_status.setTextColor(context.getResources().getColor(R.color.theme_color));
                holder.layout_cancel.setVisibility(View.VISIBLE);
                holder.tv_cancel.setText("Cancel Request");
                holder.layout_accept_decline.setVisibility(View.GONE);
                holder.tv_reply.setVisibility(View.GONE);
                holder.tv_offered_price.setVisibility(View.GONE);
            } else if (list.get(position).getChef_response().equals("1")){
                holder.txt_status.setText("Accepted");
                holder.txt_status.setTextColor(context.getResources().getColor(R.color.colorGreen));
                holder.layout_cancel.setVisibility(View.GONE);
                holder.layout_accept_decline.setVisibility(View.VISIBLE);
                holder.tv_accept.setText("Accept Offer");
                holder.tv_declie.setText("Decline Offer");
                holder.tv_reply.setVisibility(View.VISIBLE);
                holder.tv_offered_price.setVisibility(View.VISIBLE);
                if(list.get(position).getOffered_price()!=null){
                    holder.tv_offered_price.setText("Price Offered:- £" + list.get(position).getOffered_price());
                }
            } else if(list.get(position).getChef_response().equals("2")) {
                holder.txt_status.setText("Declined");
                holder.txt_status.setTextColor(context.getResources().getColor(R.color.colorRed));
                holder.layout_cancel.setVisibility(View.VISIBLE);
                holder.tv_cancel.setText("Delete Request");
                holder.layout_accept_decline.setVisibility(View.GONE);
                holder.tv_reply.setVisibility(View.GONE);
                holder.tv_offered_price.setVisibility(View.GONE);
            }
        }

        if(list.get(position).getFoodie_response() != null){
            if(list.get(position).getFoodie_response().equals("2")){
                holder.layout_accept_decline.setVisibility(View.GONE);
            } else if (list.get(position).getFoodie_response().equals("1")){
                holder.layout_accept_decline.setVisibility(View.VISIBLE);

            } else {}
        }
        holder.txt_name.setText(list.get(position).getChef_name());
        holder.txt_address.setText(list.get(position).getChef_address());
        holder.txt_email.setText(list.get(position).getChef_email());
        holder.txt_phn.setText(list.get(position).getChef_phone()+"");
        holder.txt_time.setText(list.get(position).getChef_request_datetime());
        holder.txt_dish_name.setText(list.get(position).getRequest_dishname());

        Picasso.with(context).load(GetData.IMG_BASE_URL +
                list.get(position).getChef_image()).into(holder.img_profile);
        if (list.get(position).getChef_rating()+""!=null)
            holder.ratingBar.setRating(Float.parseFloat(list.get(position).getChef_rating()+""));
        holder.txtCategory.setText(list.get(position).getRequest_cusine_name());
        if (list.get(position).getRequest_quantity()!=null)
            holder.txtqty.setText(list.get(position).getRequest_quantity());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}