package com.imcooking.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.imcooking.Model.api.response.AddCart;
import com.imcooking.Model.api.response.ChefIloveData;
import com.imcooking.R;
import com.imcooking.webservices.GetData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rakhi.
 * Contact Number : +91 9958187463
 */
public class ChefILoveAdatper extends RecyclerView.Adapter<ChefILoveAdatper.MyViewHolder> {

//    OnItemClickListenerCategory listener;
    private Context context;
    private List<ChefIloveData.ChefloveBean> dishDetails = new ArrayList<>();
    private interface_chef_i_love click;

    //private List<CuisineData.CuisineDataBean>cuisineDataBeans = new ArrayList<>();

    public ChefILoveAdatper(Context context, List<ChefIloveData.ChefloveBean> dishDetails
            , interface_chef_i_love click) {
        this.context = context;
        this.dishDetails = dishDetails;
        this.click = click;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView txtChefName, tv_distance;
        public ImageView imgDish;
        public LinearLayout heart;
        public RelativeLayout layout;

        public MyViewHolder(View view) {
            super(view);

            txtChefName = view.findViewById(R.id.chef_love_txtName);
            imgDish = view.findViewById(R.id.chef_love_img_profile);
            tv_distance = view.findViewById(R.id.chef_i_love_txtDistance);
            heart = view.findViewById(R.id.item_chef_i_love_heart_icon);
            layout = view.findViewById(R.id.item_chef_i_love_layout);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    click.click_me_chef_i_love(getAdapterPosition(), "layout");
                }
            });

            heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    click.click_me_chef_i_love(getAdapterPosition(), "heart");
                }
            });

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chefi_love_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.txtChefName.setText(dishDetails.get(position).getChef_name());
        Picasso.with(context).load(GetData.IMG_BASE_URL + dishDetails.get(position).getChef_image())
                .placeholder(R.drawable.camera).into(holder.imgDish);
        holder.tv_distance.setText(dishDetails.get(position).getMiles()+" miles");

    }

    @Override
    public int getItemCount() {
        return dishDetails.size();
    }

    public interface interface_chef_i_love {
        public void click_me_chef_i_love(int position, String click_type);
    }
}