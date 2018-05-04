package com.imcooking.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.imcooking.Model.api.response.HomeData;
import com.imcooking.R;
import com.imcooking.fragment.HomeDetails;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeBottomPagerAdapter extends PagerAdapter{

    LayoutInflater mLayoutInflater;
    Context context;
    List<HomeData.FavouriteDataBean> chefDishBeans = new ArrayList<>();
    FragmentManager manager;

    public HomeBottomPagerAdapter(Context context, FragmentManager manager, List<HomeData.FavouriteDataBean> chefDishBeans ) {
        this.context = context;
        this.manager = manager;
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
        View view = mLayoutInflater.inflate(R.layout.home_bottom_view, container, false);
        ImageView iv_dish_image;
        iv_dish_image = view.findViewById(R.id.fragment_home_bottom_img);

        Picasso.with(context).load(GetData.IMG_BASE_URL + chefDishBeans
                .get(position).getImage())
//                                .placeholder( R.drawable.progress_animation )
                .into(iv_dish_image);


        Log.d("HomeBottomImage", GetData.IMG_BASE_URL + chefDishBeans
                .get(position).getImage());

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
