package com.imcooking.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imcooking.Model.api.response.OtherDish;
import com.imcooking.R;
import com.imcooking.webservices.GetData;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Rakhi.
 * Contact Number : +91 9958187463
 * Date : 12/06/2018
 */
public class OtherDishAdatper extends RecyclerView.Adapter<OtherDishAdatper.MyViewHolder> {

    private Context context;
    private List<OtherDish.ChefDishBean>chefDishBeans ;
    OtherDishInterface cuisionInterface;

    public OtherDishAdatper(Context context,
                            List<OtherDish.ChefDishBean>chefDishBeans) {
        this.context = context;
        this.chefDishBeans = chefDishBeans;
    }

    public interface OtherDishInterface {
        void OtherDishInterfaceMethod(View view, int position, String TAG );
    }

    public void CuisionInterfaceMethod(OtherDishInterface quoteInterface) {
        this.cuisionInterface = quoteInterface;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView txtName,txtDeliverytype,tv_dish_count, tv_dish_price, txtTotalLike;
        ImageView imgPickUp, iv_dish_image,imgDeliviery, imgHeart;

        public MyViewHolder(View view) {
            super(view);
            imgDeliviery = view.findViewById(R.id.home_pager_imgHomeDelivery);
            imgPickUp = view.findViewById(R.id.home_pager_imgPick);
            imgHeart = view.findViewById(R.id.other_dish_view_heart);
            tv_dish_count = view.findViewById(R.id.item_other_dish_count);
            tv_dish_price = view.findViewById(R.id.home_dish_price);
            iv_dish_image = view.findViewById(R.id.home_image);
            txtTotalLike = view.findViewById(R.id.other_dish_likes);
            txtName = (TextView) view.findViewById(R.id.home_show_detail_1);
            txtDeliverytype = view.findViewById(R.id.home_dish_home_delivery);
            txtName.setOnClickListener(this);
            imgHeart.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.other_dish_view_heart:
                    if (cuisionInterface != null) {
                        cuisionInterface.OtherDishInterfaceMethod(view, getAdapterPosition(), "like");
                    }
                    break;
                case R.id.home_show_detail_1:
                    if (cuisionInterface != null) {
                        cuisionInterface.OtherDishInterfaceMethod(view, getAdapterPosition(), "name_detail");
                    }
                    break;
            }

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.other_dish_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if (chefDishBeans!=null && chefDishBeans.size()>0){
            if(chefDishBeans.get(position).getDish_quantity() != null) {
                holder.tv_dish_count.setText(chefDishBeans.get(position).getDish_quantity());
            }
            holder.txtName.setText(chefDishBeans.get(position).getDish_name());
            holder.tv_dish_price.setText("£" + chefDishBeans
                    .get(position).getDish_price());
            if (chefDishBeans.get(position).getDish_homedelivery().equalsIgnoreCase("No")){
                holder.txtDeliverytype.setText("Pickup");
                holder.imgPickUp.setVisibility(View.VISIBLE);
            } else if (chefDishBeans.get(position).getDish_homedelivery().equalsIgnoreCase("YES")
                    && chefDishBeans.get(position).getDish_pickup().equalsIgnoreCase("YES")){
                holder.txtDeliverytype.setText("Home Delivery / Pickup");
                holder.imgDeliviery.setVisibility(View.VISIBLE);
                holder.imgPickUp.setVisibility(View.VISIBLE);
            } else {
                holder.imgDeliviery.setVisibility(View.VISIBLE);
                holder.txtDeliverytype.setText("Home Delivery");
            }

            if (chefDishBeans.get(position).getLikeno()!= null && Integer.parseInt(chefDishBeans.get(position).getLikeno())>0)
                holder.txtTotalLike.setText(chefDishBeans.get(position).getLikeno());
            else holder.txtTotalLike.setText("0");

            Log.d(OtherDishAdatper.class.getName(), "Rakhi: "+chefDishBeans.get(position).getDishlike());
            if (Integer.parseInt(chefDishBeans.get(position).getDishlike())==1){
                holder.imgHeart.setImageResource(R.drawable.ic_heart_red);
            } else if (Integer.parseInt(chefDishBeans.get(position).getDishlike())==0){
                holder.imgHeart.setImageResource(R.drawable.ic_heart);
            }
            if (chefDishBeans.get(position).getDish_image()!=null && chefDishBeans.get(position).getDish_image().size()>0){
                Picasso.with(context).load(GetData.IMG_BASE_URL + chefDishBeans
                        .get(position).getDish_image().get(0)).into(holder.iv_dish_image);
            }
        }

        }

    @Override
    public int getItemCount() {
        return chefDishBeans.size();
    }
}