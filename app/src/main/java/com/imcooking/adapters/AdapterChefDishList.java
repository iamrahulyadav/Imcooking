package com.imcooking.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imcooking.Model.ApiRequest.DishLikeRequest;
import com.imcooking.Model.api.response.ChefProfileData;
import com.imcooking.Model.api.response.HomeData;
import com.imcooking.R;
import com.imcooking.activity.Sub.Foodie.ChefProfile;
import com.imcooking.activity.home.MainActivity;
import com.imcooking.fragment.chef.ChefDishDetail;
import com.imcooking.fragment.chef.ChefHome;
import com.imcooking.fragment.foodie.HomeDetails;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterChefDishList extends PagerAdapter{

    private LayoutInflater mLayoutInflater;
    private FragmentManager manager;
    private Context context;
    private Activity activity;
    private List<ChefProfileData.ChefDishBean> chef_dish_list = new ArrayList<>();

    public AdapterChefDishList(FragmentManager manager, Context context, Activity activity, List<ChefProfileData.ChefDishBean> chef_dish_list) {
        this.manager = manager;
        this.context = context;
        this.activity = activity;
        this.chef_dish_list = chef_dish_list;
        mLayoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return chef_dish_list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = mLayoutInflater.inflate(R.layout.item_chef_dish_list, container, false);

        TextView dish_name = view.findViewById(R.id.item_chef_dish_name);
        TextView dish_count = view.findViewById(R.id.item_chef_dish_count);
        final TextView dish_home_delivery = view.findViewById(R.id.item_chef_dish_home_delivery);
        TextView dish_price = view.findViewById(R.id.item_chef_dish_price);
        TextView dish_likes = view.findViewById(R.id.item_chef_dish_likes);

        ImageView iv_dish_image = view.findViewById(R.id.item_chef_dish_image);
        ImageView iv_home_delivery_image = view.findViewById(R.id.item_chef_home_delivery_icon);
        ImageView iv_pickup_image = view.findViewById(R.id.item_chef_pickyup_icon);

        String url = GetData.IMG_BASE_URL + chef_dish_list.get(position).getDish_image().get(0);
        Picasso.with(context).load(url).into(iv_dish_image);

        dish_name.setText(chef_dish_list.get(position).getDish_name());
        dish_count.setText(chef_dish_list.get(position).getDish_quantity() + "");
        dish_price.setText(chef_dish_list.get(position).getDish_price() + "");
        dish_likes.setText("10");

        if (chef_dish_list.get(position).getDish_homedelivery().equals("Yes")) {
            if (chef_dish_list.get(position).getDish_pickup().equals("Yes")) {
                dish_home_delivery.setText("Home Delivery / Pick-up");
                iv_home_delivery_image.setVisibility(View.VISIBLE);
                iv_pickup_image.setVisibility(View.VISIBLE);
            } else {
                dish_home_delivery.setText("Home Delivery");
                iv_home_delivery_image.setVisibility(View.VISIBLE);
                iv_pickup_image.setVisibility(View.GONE);
            }
        } else {
            iv_home_delivery_image.setVisibility(View.GONE);
            iv_home_delivery_image.setVisibility(View.VISIBLE);
            dish_home_delivery.setText("Pick-up");
        }

        dish_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChefDishDetail fragment = new ChefDishDetail();
                Bundle bundle = new Bundle();
                bundle.putString("name", chef_dish_list.get(position).getDish_name());
                bundle.putString("available", chef_dish_list.get(position).getDish_available());
                bundle.putString("time", chef_dish_list.get(position).getDish_from() + " - " + chef_dish_list.get(position).getDish_to());
                bundle.putString("count", chef_dish_list.get(position).getDish_quantity());
                bundle.putString("home_delivery", dish_home_delivery.getText().toString());
                bundle.putString("price", chef_dish_list.get(position).getDish_price() + "");
                bundle.putString("description", chef_dish_list.get(position).getDish_description());
                bundle.putString("special_note", chef_dish_list.get(position).getDish_special_note());
                bundle.putString("cuisine", chef_dish_list.get(position).getDish_cuisine());
                fragment.setArguments(bundle);

                if (manager.findFragmentByTag(new ChefDishDetail().getTag()) == null) {
                    //fragment not in back stack, create it.
                    if(activity.getClass().getName().equals(MainActivity.class.getName())) {
                        manager.beginTransaction().setCustomAnimations(R.animator.fragment_slide_left_enter,
                                R.animator.fade_out,
                                0,
                                R.animator.fragment_slide_right_exit)
                                .replace(R.id.frame, fragment).addToBackStack(fragment.getClass().getName())
                                .commit();
                    } else if(activity.getClass().getName().equals(ChefProfile.class.getName())){
                        manager.beginTransaction().setCustomAnimations(R.animator.fragment_slide_left_enter,
                                R.animator.fade_out,
                                0,
                                R.animator.fragment_slide_right_exit)
                                .replace(R.id.frame_chef_profile, fragment).addToBackStack(fragment.getClass()
                                .getName()).commit();
                    } else {}

//                manager.executePendingTransactions();
                } else {
                    if(activity.getClass().getName().equals(MainActivity.class.getName())) {
                        manager.beginTransaction().setCustomAnimations(R.animator.fragment_slide_left_enter,
                                R.animator.fade_out,
                                0,
                                R.animator.fragment_slide_right_exit)
                                .replace(R.id.frame, fragment).commit();
                    } else if(activity.getClass().getName().equals(ChefProfile.class.getName())){
                        manager.beginTransaction().setCustomAnimations(R.animator.fragment_slide_left_enter,
                                R.animator.fade_out,
                                0,
                                R.animator.fragment_slide_right_exit)
                                .replace(R.id.frame_chef_profile, fragment).commit();
                    } else {}
                    //              manager.executePendingTransactions();
                }
//                BaseClass.callFragment(fragment, fragment.getClass().getName(), manager);
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}
