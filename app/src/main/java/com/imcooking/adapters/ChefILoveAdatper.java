package com.imcooking.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    List<ChefIloveData.ChefloveBean> dishDetails = new ArrayList<>();

    //private List<CuisineData.CuisineDataBean>cuisineDataBeans = new ArrayList<>();


    public ChefILoveAdatper(Context context, List<ChefIloveData.ChefloveBean> dishDetails) {
        this.context = context;
        this.dishDetails = dishDetails;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView txtChefName, tv_distance;
        public ImageView imgDish;

        public MyViewHolder(View view) {
            super(view);

            txtChefName = view.findViewById(R.id.chef_love_txtName);
            imgDish=view.findViewById(R.id.chef_love_img_profile);
            tv_distance = view.findViewById(R.id.chef_i_love_txtDistance);

        }
    }

    int row_index=-1;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chefi_love_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.txtChefName.setText(dishDetails.get(position).getChef_name());
        Picasso.with(context).load(GetData.IMG_BASE_URL + dishDetails.get(position).getChef_image()).into(holder.imgDish);

        holder.tv_distance.setText(dishDetails.get(position).getMiles()+" miles");

    }

    @Override
    public int getItemCount() {
        return dishDetails.size();
    }
}