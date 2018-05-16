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
    private List<ChefProfileData.ChefDishBean> chef_dish_list = new ArrayList<>();

    public AdapterChefDishList(FragmentManager manager, Context context, List<ChefProfileData.ChefDishBean> chef_dish_list) {
        this.manager = manager;
        this.context = context;
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

//        TextView

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}
