package com.imcooking.adapters;

import android.annotation.SuppressLint;
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

import com.imcooking.Model.api.response.HomeData;
import com.imcooking.R;
import com.imcooking.fragment.HomeDetails;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeDishPagerAdapter extends PagerAdapter{

    LayoutInflater mLayoutInflater;
    Context context;
    List<HomeData.ChefDishBean> chefDishBeans = new ArrayList<>();
    FragmentManager manager;

    public HomeDishPagerAdapter( Context context, FragmentManager manager,List<HomeData.ChefDishBean> chefDishBeans ) {
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
        View view = mLayoutInflater.inflate(R.layout.home_dish_pager_view, container, false);
        ImageView iv_dish_image, imgPickUp, imgDeliviery;
        TextView tv_dish_name, tv_chef_name, tv_chef_likes, tv_chef_followers, tv_dish_distance, tv_dish_delivery,
                tv_dish_price;
        RatingBar ratingBar;
        TextView tv_dish_likes, tv_chef_rating, tv_dish_address;

        ratingBar = view.findViewById(R.id.home_dish_pager_rating);
        iv_dish_image = view.findViewById(R.id.home_image);
        tv_dish_name =view.findViewById(R.id.home_show_detail_1);
        tv_chef_name = view.findViewById(R.id.home_chef_name);
        tv_chef_likes = view.findViewById(R.id.home_chef_like);
        tv_chef_followers = view.findViewById(R.id.home_chef_follow);
        tv_dish_distance =view.findViewById(R.id.home_dish_distance);
        tv_dish_delivery = view.findViewById(R.id.home_dish_home_delivery);
        tv_dish_price = view.findViewById(R.id.home_dish_price);
        imgDeliviery = view.findViewById(R.id.home_pager_imgHomeDelivery);
        imgPickUp = view.findViewById(R.id.home_pager_imgPick);
        tv_dish_likes = view.findViewById(R.id.home_dish_likes);
        tv_chef_rating = view.findViewById(R.id.home_chef_rating);
        tv_dish_address = view.findViewById(R.id.home_dish_address);

        tv_dish_likes.setText(chefDishBeans.get(position).getDishlike() + "");
        tv_chef_rating.setText("("+chefDishBeans.get(position).getRatingno() + ")");
        tv_dish_address.setText(chefDishBeans.get(position).getAddress());

        Picasso.with(context).load(GetData.IMG_BASE_URL + chefDishBeans
                .get(position).getDish_image().get(0))
//                                .placeholder( R.drawable.progress_animation )
                .into(iv_dish_image);
        tv_dish_name.setText(chefDishBeans
                .get(position).getDish_name());
        tv_chef_name.setText(chefDishBeans
                .get(position).getChef_name());
        tv_chef_name.setInputType(
                InputType.TYPE_CLASS_TEXT|
                        InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        tv_chef_likes.setText(String.valueOf(chefDishBeans
                .get(position).getLike()));
        tv_chef_followers.setText(chefDishBeans.get(position).getFollow()+"");
        tv_dish_distance.setText(chefDishBeans.get(position).getDish_deliverymiles()+" miles");
        if (chefDishBeans.get(position).getDish_homedelivery().equalsIgnoreCase("No")){
            tv_dish_delivery.setText("Pickup");
            imgPickUp.setVisibility(View.VISIBLE);
        } else if (chefDishBeans.get(position).getDish_homedelivery().equalsIgnoreCase("YES")
                && chefDishBeans.get(position).getDish_pickup().equalsIgnoreCase("YES")){
            tv_dish_delivery.setText("Home Delivery / Pickup");
            imgDeliviery.setVisibility(View.VISIBLE);
            imgPickUp.setVisibility(View.VISIBLE);
        } else {
            imgDeliviery.setVisibility(View.VISIBLE);
            tv_dish_delivery.setText("Home Delivery");
        }
        tv_dish_price.setText("$" + chefDishBeans
                .get(position).getDish_price());

        tv_dish_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeDetails fragment = new HomeDetails();
                Bundle bundle = new Bundle();
                bundle.putString("dish_id", chefDishBeans.get(position).getDish_id() + "");
                fragment.setArguments(bundle);

                BaseClass.callFragment(fragment, fragment
                        .getClass().getName(), manager);

            }
        });

        ratingBar.setRating(Float.parseFloat(chefDishBeans.get(position).getRating()));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
