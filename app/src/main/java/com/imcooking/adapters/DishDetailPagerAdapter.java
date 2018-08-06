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
        return size;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    private VideoView videoView;
    private ProgressBar bar;
    private ImageView iv_dish_image, imgPlay;

    @SuppressLint("SetTextI18n")
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View view = mLayoutInflater.inflate(R.layout.item_dish_details_pager, container, false);

//        final VideoView videoView;


        bar = view.findViewById(R.id.buffering_textview);
        videoView = view.findViewById(R.id.item_dish_details_pager_video_view);
        iv_dish_image = view.findViewById(R.id.home_image);
        imgPlay = view.findViewById(R.id.item_video_play);


        if (video.equalsIgnoreCase("no")){
            imgPlay.setVisibility(View.GONE);
            videoView.setVisibility(View.GONE);
            iv_dish_image.setVisibility(View.VISIBLE);
        } else {
            if(position == 0){
                imgPlay.setVisibility(View.VISIBLE);
                videoView.setVisibility(View.GONE);
                iv_dish_image.setVisibility(View.VISIBLE);
                Picasso.with(context).load(GetData.IMG_BASE_URL + chefDishBeans
                        .get(position)).into(iv_dish_image);
            } else {
                imgPlay.setVisibility(View.GONE);
                videoView.setVisibility(View.GONE);
                bar.setVisibility(View.GONE);
                iv_dish_image.setVisibility(View.VISIBLE);
                Picasso.with(context).load(GetData.IMG_BASE_URL + chefDishBeans
                        .get(position-1)).into(iv_dish_image);

            }
        }

        if(arr_.get(position).equals("video")){
            initializePlayer();
        }

        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgPlay.setVisibility(View.GONE);
                videoView.setVisibility(View.VISIBLE);
                iv_dish_image.setVisibility(View.GONE);


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

    private void initializePlayer() {
        // Show the "Buffering..." message while the video loads.
        bar.setVisibility(VideoView.VISIBLE);
        videoView.setVisibility(View.VISIBLE);

        // Buffer and decode the video sample.
        Log.d("VideoUrl", video_url);
//        Uri videoUri = getMedia("http://www.swebsiteaaa.com/video/mp4/720/big_buck_bunny_720p_1mb.mp4");
        Uri videoUri = Uri.parse("http://webdevelopmentreviews.net/imcooking/upload/03081538840723VID_20180702_152802228.mp4");

        videoView.setVideoURI(Uri.parse("android.resource://" + activity.getPackageName() +
                "/raw/" + video_url));

//        int resID=getResources().getIdentifier(fname, "raw", activity.getPackageName());

/*
        videoView.setVideoURI(Uri.parse("android.resource://" + activity.getPackageName() + "/" +
                R.raw.video1));
*/

        // Listener for onPrepared() event (runs after the media is prepared).
        videoView.setOnPreparedListener(
                new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        // Hide buffering message.
                        bar.setVisibility(VideoView.INVISIBLE);

                        // Restore saved position, if available.
                        if (mCurrentPosition > 0) {
                            videoView.seekTo(mCurrentPosition);
                        } else {
                            // Skipping to 1 shows the first frame of the video.
                            videoView.seekTo(1);
                        }

                        imgPlay.setVisibility(View.GONE);
                        videoView.setVisibility(View.VISIBLE);
                        iv_dish_image.setVisibility(View.GONE);

                        // Start playing!
                        videoView.start();
                    }
                });
//        videoView.start();

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                imgPlay.setVisibility(View.VISIBLE);
                videoView.setVisibility(View.GONE);
                iv_dish_image.setVisibility(View.VISIBLE);
                bar.setVisibility(View.GONE);

                return true;
            }
        });

//        videoView.setOn
        Log.d("MyVideo", videoView.getBufferPercentage() + "" );

        // Listener for onCompletion() event (runs after media has finished
        // playing).
        videoView.setOnCompletionListener(
                new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        Toast.makeText(context,
                                "Video end",
                                Toast.LENGTH_SHORT).show();

                        // Return the video position to the start.
                        videoView.seekTo(0);

                        imgPlay.setVisibility(View.VISIBLE);
                        videoView.setVisibility(View.GONE);
                        iv_dish_image.setVisibility(View.VISIBLE);
                        bar.setVisibility(View.GONE);

                    }
                });
    }

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