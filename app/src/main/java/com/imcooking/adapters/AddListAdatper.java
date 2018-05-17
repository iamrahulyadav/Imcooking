package com.imcooking.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imcooking.Model.api.response.AddressListData;
import com.imcooking.Model.api.response.ChefIloveData;
import com.imcooking.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rakhi.
 * Contact Number : +91 9958187463
 */
public class AddListAdatper extends RecyclerView.Adapter<AddListAdatper.MyViewHolder> {
    private Context context;
    private List<AddressListData.AddressBean>addressBeanList = new ArrayList<>();

    public AddListAdatper(Context context, List<AddressListData.AddressBean> addressBeanList) {
        this.context = context;
        this.addressBeanList = addressBeanList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView txtChefName, tv_distance;
        public ImageView imgDish;

        public MyViewHolder(View view) {
            super(view);

//            txtChefName = view.findViewById(R.id.chef_love_txtName);
//            imgDish=view.findViewById(R.id.chef_love_img_profile);
//            tv_distance = view.findViewById(R.id.chef_i_love_txtDistance);

        }
    }

    int row_index=-1;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_address_list_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

       /* holder.txtChefName.setText(dishDetails.get(position).getChef_name());
        Picasso.with(context).load(GetData.IMG_BASE_URL + dishDetails.get(position).getChef_image()).into(holder.imgDish);

        holder.tv_distance.setTag(position);

*/
    }

    @Override
    public int getItemCount() {
        return addressBeanList.size();
    }
}