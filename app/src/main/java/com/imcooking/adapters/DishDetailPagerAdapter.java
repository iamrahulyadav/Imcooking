package com.imcooking.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

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
    List<String> chefDishBeans ;
    String video;
   private DishDetailPlayClick dishDetailPlayClick;

    public interface DishDetailPlayClick{
        void playVideo(int pos, String tag);
    }

    private Activity activity;
    private int size;
    private String video_url;
    private ArrayList<String> arr_ = new ArrayList<>();

    public DishDetailPagerAdapter(Context context, List<String> chefDishBeans,
                                  DishDetailPlayClick dishDetailPlayClick, String video, int size,
                                  Activity activity, String video_url, ArrayList<String> arr_) {
        this.context = context;
        this.chefDishBeans = chefDishBeans;
        this.dishDetailPlayClick = dishDetailPlayClick;
        this.video = video;
        this.size = size;
        this.activity = activity;
        this.video_url = video_url;
        this.arr_ = arr_;
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

//    private VideoView videoView;
//    private ProgressBar bar;
    private ImageView iv_dish_image, imgPlay;

    @SuppressLint("SetTextI18n")
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View view = mLayoutInflater.inflate(R.layout.item_dish_details_pager, container, false);

        iv_dish_image = view.findViewById(R.id.home_image);
        imgPlay = view.findViewById(R.id.item_video_play);

         imgPlay.setVisibility(View.GONE);
//              iv_dish_image.setVisibility(View.VISIBLE);
                Picasso.with(context).load(GetData.IMG_BASE_URL + chefDishBeans
                        .get(position)).into(iv_dish_image);

       /* if(arr_.get(position).equals("video")){
            initializePlayer();
        }
*/
        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                imgPlay.setVisibility(View.GONE);
//                videoView.setVisibility(View.VISIBLE);
//                iv_dish_image.setVisibility(View.GONE);
                
//                initializePlayer();

                                dishDetailPlayClick.playVideo(position, "play");
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

    private  String VIDEO_SAMPLE = "";
    private int mCurrentPosition = 0;


    private Uri getMedia(String mediaName) {
        if (URLUtil.isValidUrl(mediaName)) {
            // Media name is an external URL.
            return Uri.parse(mediaName);
        } else {
            // Media name is a raw resource embedded in the app.
            return Uri.parse("android.resource://" + activity.getPackageName() +
                    "/raw/" + mediaName);
        }
    }
}