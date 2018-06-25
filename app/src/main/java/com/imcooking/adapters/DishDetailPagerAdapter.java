package com.imcooking.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.imcooking.Model.api.response.HomeData;
import com.imcooking.R;
import com.imcooking.fragment.foodie.HomeDetails;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DishDetailPagerAdapter extends PagerAdapter{

    LayoutInflater mLayoutInflater;
    Context context;
    List<String> chefDishBeans = new ArrayList<>();
    private ArrayList<String> arr_like;

    public DishDetailPagerAdapter(Context context, List<String> chefDishBeans) {
        this.context = context;
        this.chefDishBeans = chefDishBeans;
        mLayoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return chefDishBeans.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = mLayoutInflater.inflate(R.layout.item_dish_details_pager, container, false);
        ImageView iv_dish_image, imgPlay;
        iv_dish_image = view.findViewById(R.id.home_image);
        imgPlay = view.findViewById(R.id.item_video_play);
        if (position>0){
            imgPlay.setVisibility(View.GONE);
        }

        Picasso.with(context).load(GetData.IMG_BASE_URL + chefDishBeans
                .get(position)).into(iv_dish_image);
        ((ViewPager)container).addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
//        return super.getItemPosition(object);
        return POSITION_NONE;
    }


}
