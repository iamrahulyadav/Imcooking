package com.imcooking.adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.imcooking.R;
import com.imcooking.activity.Sub.Chef.ChefEditDish;
import com.imcooking.fragment.foodie.FoodieOrderDetails;
import com.imcooking.utils.BaseClass;

import java.util.ArrayList;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class AdapterEditDishPhotos extends RecyclerView.Adapter<AdapterEditDishPhotos.MyViewHolder> {

    private Context context;
    private ArrayList<String> arrayList = new ArrayList<>();

    browse_photo click;
//TextView addmore;
    public AdapterEditDishPhotos(Context context, ArrayList<String> arrayList, browse_photo click/*,TextView addmore*/) {
        this.context = context;
        this.arrayList = arrayList;
        this.click = click;
  //      this.addmore=addmore;
      //  Toast.makeText(context,arrayList.size()+"",Toast.LENGTH_SHORT).show();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView btn, name;

        public MyViewHolder(View view) {
            super(view);

            btn = view.findViewById(R.id.edit_dish_photo_browse);
            name = view.findViewById(R.id.edit_dish_photo_name);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    click.browse_my_photo(view, getAdapterPosition());
                }
            });
            /*addmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    arrayList.add("photos");
                   // Toast.makeText(context,arrayList.size()+"",Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }
            });*/


//            tv_order_details = view.findViewById(R.id.item_foodie_orders_order_details);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_edit_dish_add_photos, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.name.setText(arrayList.get(position));

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public interface browse_photo{
        void browse_my_photo(View view, int position);
    }

}