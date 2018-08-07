package com.imcooking.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imcooking.Model.api.response.ChefProfileData1;
import com.imcooking.R;
import com.imcooking.activity.Sub.Foodie.ChefProfile;
import com.imcooking.activity.home.MainActivity;
import com.imcooking.fragment.chef.ChefDishDetail;
import com.imcooking.fragment.foodie.HomeDetails;
import com.imcooking.fragment.foodie.SearchFragment;
import com.imcooking.webservices.GetData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterChefDishList extends PagerAdapter{

    private LayoutInflater mLayoutInflater;
    private FragmentManager manager;
    private Context context;
    private Activity activity;
    private ArrayList<String>base64Array = new ArrayList<>();
    private List<ChefProfileData1.ChefDishBean> chef_dish_list = new ArrayList<>();
    private ArrayList<String> arr_like = new ArrayList<>();

    private Click_interface_chef_dish_list click;
    private String click_type;

    public AdapterChefDishList(FragmentManager manager, Context context, Activity activity,
                               List<ChefProfileData1.ChefDishBean> chef_dish_list,
                               ArrayList<String> arr_like, Click_interface_chef_dish_list click, String click_type) {
        this.manager = manager;
        this.context = context;
        this.activity = activity;
        this.chef_dish_list = chef_dish_list;
        this.arr_like = arr_like;
        this.click = click;
        this.click_type = click_type;
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
        ImageView iv_heart = view.findViewById(R.id.home_heart);


/*
        if(chef_dish_list.get(position).getDish_foodie_like().equals("1")){
            iv_heart.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_heart_red));
        } else
            iv_heart.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_heart));
*/

        if(arr_like.get(position).equals("1")){
            iv_heart.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_heart_red));
        } else
            iv_heart.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_heart));


        iv_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.click_me_chef_dish_list(position, click_type);
            }
        });

        String url = "";
        if(chef_dish_list.get(position).getDish_image().size() != 0) {
            url = GetData.IMG_BASE_URL + chef_dish_list.get(position).getDish_image().get(0);
            Log.d("ChefDishImage", url);
            Picasso.with(context).load(url).into(iv_dish_image);
        }
//        Log.d("ChefCurrentDishes", url);


        final ArrayList<String> arrayList = new ArrayList<>();
        for (int i=0; i<chef_dish_list.get(position).getDish_image().size(); i++){
            arrayList.add("" + chef_dish_list.get(position).getDish_image().get(i));
            Log.d("TAG", "rakhi: "+chef_dish_list.get(position).getDish_image().get(i));
        }

        dish_name.setText(chef_dish_list.get(position).getDish_name());
        if(chef_dish_list.get(position).getDish_quantity()!=null && !chef_dish_list.get(position).getDish_quantity().equals("null"))
         dish_count.setText(chef_dish_list.get(position).getDish_quantity()+"");
        else dish_count.setText("0");
        dish_price.setText("£" + chef_dish_list.get(position).getDish_price());
        dish_likes.setText(chef_dish_list.get(position).getLike_no());

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
        String tim =  chef_dish_list.get(position).getDish_from() + " - " + chef_dish_list.get(position).getDish_to();

        dish_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                String type = BaseClass.getUserType(context);
//                if(type.equals("1")){
                ChefDishDetail fragment = new ChefDishDetail();
                Bundle bundle = new Bundle();
                bundle.putString("id", chef_dish_list.get(position).getDish_id() + "");
                bundle.putString("name", chef_dish_list.get(position).getDish_name());
                bundle.putString("qyt", chef_dish_list.get(position).getDish_quantity()+"");
                bundle.putString("available", chef_dish_list.get(position).getDish_available());
                bundle.putString("time", chef_dish_list.get(position).getDish_from() + " - " + chef_dish_list.get(position).getDish_to());
                bundle.putString("time1", chef_dish_list.get(position).getDish_from());
                bundle.putString("time2", chef_dish_list.get(position).getDish_to());
                bundle.putString("count", chef_dish_list.get(position).getDish_quantity()+"");
                bundle.putString("home_delivery", chef_dish_list.get(position).getDish_homedelivery());
                bundle.putString("pickup", chef_dish_list.get(position).getDish_pickup());
                bundle.putString("price", chef_dish_list.get(position).getDish_price() + "");
                bundle.putString("description", chef_dish_list.get(position).getDish_description());
                bundle.putString("special_note", chef_dish_list.get(position).getDish_special_note());
                bundle.putString("cuisine", chef_dish_list.get(position).getDish_cuisine());
                bundle.putString("likeno", chef_dish_list.get(position).getLike_no());

                if (chef_dish_list.get(position).getDish_video()!=null && chef_dish_list.get(position).getDish_video().length()>0)
                    bundle.putString("video",GetData.IMG_BASE_URL+chef_dish_list.get(position).getDish_video()+"");
                bundle.putStringArrayList("image", arrayList);
//                bundle.putString("cuisine", chef_dish_list.get(position).get);
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

//                        Toast.makeText(context, "called", Toast.LENGTH_SHORT).show();
//                        int i = new MainActivity().getSupportFragmentManager().getBackStackEntryCount();
//                        Toast.makeText(context,  i + "", Toast.LENGTH_SHORT).show();
//                        String tag = ""; //new MainActivity().getSupportFragmentManager()
//                                .getBackStackEntryAt(i-1).getName();
                        if(MainActivity.my_tag.equals(new SearchFragment().getClass().getName())){
//                            Toast.makeText(context, "Yes", Toast.LENGTH_SHORT).show();
                            HomeDetails fragment1 = new HomeDetails();
                            Bundle bundle1 = new Bundle();
                            bundle1.putString("dish_id", chef_dish_list.get(position).getDish_id() + "");
                            fragment1.setArguments(bundle1);

                            manager.beginTransaction().replace(R.id.frame_chef_profile, fragment1).commit();

//                            BaseClass.callFragment(fragment1, fragment1
//                                    .getClass().getName(), manager);

                        } else {
//                        if(new MainActivity().getSupportFragmentManager().findFragmentByTag(new SearchFragment().getTag()) == null){
                            Intent intent = new Intent();
                            intent.putExtra("dish_id", chef_dish_list.get(position).getDish_id() + "");
                            activity.setResult(ChefProfile.CHEF_PROFILE_CODE, intent);
                            activity.finish();
                        }
/*
                        } else {
                            manager.beginTransaction().setCustomAnimations(R.animator.fragment_slide_left_enter,
                                    R.animator.fade_out,
                                    0,
                                    R.animator.fragment_slide_right_exit)
                                    .replace(R.id.frame, fragment).addToBackStack(fragment.getClass().getName())
                                    .commit();
                        }
*/


/*                        manager.beginTransaction().setCustomAnimations(R.animator.fragment_slide_left_enter,
                                R.animator.fade_out,
                                0,
                                R.animator.fragment_slide_right_exit)
                                .replace(R.id.frame_chef_profile, fragment).addToBackStack(fragment.getClass()
                                .getName()).commit();*/
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

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public interface Click_interface_chef_dish_list{

        public void click_me_chef_dish_list(int position, String click_type);
    }



}
