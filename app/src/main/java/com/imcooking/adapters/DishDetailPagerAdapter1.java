package com.imcooking.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import com.imcooking.R;
import com.imcooking.webservices.GetData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DishDetailPagerAdapter1 extends PagerAdapter{

    LayoutInflater mLayoutInflater;
    Context context;
    List<String> chefDishBeans ;
    String video;
   private DishDetailPlayClick dishDetailPlayClick;

    public interface DishDetailPlayClick{
        void playVideo(int pos, String tag);
    }

    public DishDetailPagerAdapter1(Context context, List<String> chefDishBeans, DishDetailPlayClick dishDetailPlayClick, String video) {
        this.context = context;
        this.chefDishBeans = chefDishBeans;
        this.dishDetailPlayClick = dishDetailPlayClick;
        this.video = video;
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
        final ImageView iv_dish_image, imgPlay;
        VideoView mVideoView;
        iv_dish_image = view.findViewById(R.id.home_image);
        mVideoView = view.findViewById(R.id.videoview);

        imgPlay = view.findViewById(R.id.item_video_play);
        if (video.equalsIgnoreCase("no")){
            imgPlay.setVisibility(View.GONE);
        }
        if (position>0){
            imgPlay.setVisibility(View.GONE);
        }

        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dishDetailPlayClick.playVideo(position, "play");
            }
        });
        Picasso.with(context).load(GetData.IMG_BASE_URL + chefDishBeans
                .get(position)).into(iv_dish_image);

        mVideoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


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
