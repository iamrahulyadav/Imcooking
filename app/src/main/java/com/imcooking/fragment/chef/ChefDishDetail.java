package com.imcooking.fragment.chef;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.imcooking.Model.api.response.ChefProfileData;
import com.imcooking.R;
import com.imcooking.activity.Sub.Chef.ChefEditDish;
import com.imcooking.adapters.AdapterChefDishList;
import com.imcooking.adapters.DishDetailPagerAdapter;
import com.imcooking.adapters.Pager1;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChefDishDetail extends Fragment implements View.OnClickListener, DishDetailPagerAdapter.DishDetailPlayClick {

    private  String VIDEO_SAMPLE =
            "", isVideo;

    private VideoView mVideoView;
    private ProgressBar mBufferingTextView;
    // Current playback position (in milliseconds).
    private int mCurrentPosition = 0;

    // Tag for the instance state bundle.
    private static final String PLAYBACK_TIME = "play_time";

    public ChefDishDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chef_dish_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
        getMyData();
    }

    private TextView tv_dish_name, tv_dish_likes, tv_dish_text_available, tv_dish_time, tv_dish_count, tv_dish_home_delivery,
    tv_dish_price, /*tv_dish_description,*/ tv_edit_dish ;
    private DishDetailPagerAdapter dishDetailPagerAdapter;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.
    private ProgressBar progressBar;
    ViewPager home_top_pager;
    private String str_id, str_name, str_likes, str_available,str_like, str_time, str_time_1, str_time_2, str_count, str_homedelivery, str_pickup, str_price,
            str_description, str_special_note, str_cuisine, str_qyt;

    private ArrayList<String> arrayList = new ArrayList<>();

    private ImageView iv_home_delivery_icon, iv_pickup_icon;

    private void init(){
        home_top_pager = getView().findViewById(R.id.fragment_chef_auto_scroll_page);
        tv_dish_name = getView().findViewById(R.id.chef_dish_detalis_dish_name);
        tv_dish_likes = getView().findViewById(R.id.chef_dish_detalis_dish_likes);
        tv_dish_text_available = getView().findViewById(R.id.chef_dish_detalis_dish_available);
        tv_dish_time = getView().findViewById(R.id.chef_dish_detalis_dish_time);
        tv_dish_count = getView().findViewById(R.id.chef_dish_detalis_dish_count);
        tv_dish_home_delivery = getView().findViewById(R.id.chef_dish_detalis_dish_home_deliveryery);
        tv_dish_price = getView().findViewById(R.id.chef_dish_details_dish_price);
//        tv_dish_description = getView().findViewById(R.id.chef_dish_details_description);
        tv_edit_dish = getView().findViewById(R.id.chef_home_details_edit_dish);
        tv_edit_dish.setOnClickListener(this);


        mVideoView = getView().findViewById(R.id.fragment_chef_dish_details_videoview);
        mBufferingTextView = getView().findViewById(R.id.buffering_textview);

        // Set up the media controller widget and attach it to the video view.
        MediaController controller = new MediaController(getContext());
        controller.setMediaPlayer(mVideoView);
        mVideoView.setMediaController(controller);

        iv_home_delivery_icon = getView().findViewById(R.id.chef_dish_detalis_icon_home_delivery);
        iv_pickup_icon = getView().findViewById(R.id.chef_dish_detalis_icon_pickup);
    }

    private void getMyData(){

        str_id = getArguments().getString("id");
        str_name = getArguments().getString("name");
        str_available = getArguments().getString("available");
        str_time = getArguments().getString("time");
        str_time_1 = getArguments().getString("time1");
        str_time_2 = getArguments().getString("time2");
        str_count = getArguments().getString("count");
        str_homedelivery = getArguments().getString("home_delivery");
        str_pickup = getArguments().getString("pickup");
        str_price = getArguments().getString("price");
        str_description = getArguments().getString("description");
        str_special_note = getArguments().getString("special_note");
        str_cuisine = getArguments().getString("cuisine");
        arrayList = getArguments().getStringArrayList("image");
        str_like = getArguments().getString("likeno");
        VIDEO_SAMPLE = getArguments().getString("video");
        Log.d("TAG", "getMyData: "+VIDEO_SAMPLE);

        if (VIDEO_SAMPLE!=null)
            isVideo = "yes";
        else isVideo = "no";

        base64Array = new ArrayList<>();
        Log.d("TAG", "getMyData: "+arrayList.size());

        if (arrayList.size()>0){
            for (int i=0;i<arrayList.size();i++){
                String urls[] = new String[arrayList.size()];
                String imageUrl = GetData.IMG_BASE_URL+arrayList.get(i);
                urls[i] = imageUrl;
                // Execute the task
                new GetImage().execute(GetData.IMG_BASE_URL+arrayList.get(i));

            }

        }
        str_qyt = getArguments().getString("qyt");
        tv_dish_name.setText(str_name);
        if(str_available.equalsIgnoreCase("Yes")){
            tv_dish_text_available.setText("Available");
        } else{
            tv_dish_text_available.setText("Not Available");
        }

        tv_dish_time.setText(str_time);
        if(!str_qyt.equals("null")){
            tv_dish_count.setText(str_qyt);
        } else{
            tv_dish_count.setText("0");
        }


//        tv_dish_home_delivery.setText(str_homedelivery);
        if(str_homedelivery.equals("Yes")){
            if(str_pickup.equals("Yes")){
                tv_dish_home_delivery.setText("Home Delivery / Pick-up");
                iv_home_delivery_icon.setVisibility(View.VISIBLE);
                iv_pickup_icon.setVisibility(View.VISIBLE);
            } else if (str_pickup.equals("No") ){
                iv_home_delivery_icon.setVisibility(View.VISIBLE);
                iv_pickup_icon.setVisibility(View.GONE);
                tv_dish_home_delivery.setText("Home Delivery");
            } else {}
        } else if(str_homedelivery.equals("No")){
            iv_home_delivery_icon.setVisibility(View.GONE);
            iv_pickup_icon.setVisibility(View.VISIBLE);
            tv_dish_home_delivery.setText("Pick-up");
        }

        tv_dish_price.setText("£" + str_price);
        if (str_description!=null){
//            tv_dish_description.setText(str_description);
        }

        setAdapter();
        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == arrayList.size()-1) {
                    currentPage = 0;
                }
                home_top_pager.setCurrentItem(currentPage++, true);
            }
        };

        if (str_like!=null){
            tv_dish_likes.setText(str_like);
        } else tv_dish_likes.setText("0");

        timer = new Timer(); // This will create a new Thread
        timer .schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);


        final ViewPager viewPager = getView().findViewById(R.id.chef_dish_details_view_pager);
        TabLayout tabLayout = getView().findViewById(R.id.chef_dish_details_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Ingredients of Recipe"));
        tabLayout.addTab(tabLayout.newTab().setText("Special Note"));

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(str_description);
        arrayList.add(str_special_note);
        Pager1 adapter = new Pager1(getContext(), arrayList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setAdapter() {
        dishDetailPagerAdapter = new DishDetailPagerAdapter(getContext(), arrayList,this, isVideo);
        home_top_pager.setAdapter(dishDetailPagerAdapter);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if(id == R.id.chef_home_details_edit_dish){
            startActivity(new Intent(getContext(), ChefEditDish.class)
                    .putExtra("id", str_id)
                    .putExtra("name", str_name)
                    .putExtra("cuisine", str_cuisine)
                    .putExtra("price", str_price)
                    .putExtra("ingredients", str_description)
                    .putExtra("special_note", str_special_note)
                    .putExtra("available", str_available)
                    .putExtra("home_delivery", str_homedelivery)
                    .putExtra("pickup", str_pickup)
                    .putExtra("image", arrayList)
                    .putExtra("qyt",str_qyt)
                    .putExtra("video",VIDEO_SAMPLE)
                    .putExtra("time1", str_time_1)
                    .putExtra("time2", str_time_2)


            );
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
        } else{}
    }


    @Override
    public void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow(); // in Activity's onCreate() for instance
//            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow(); // in Activity's onCreate() for instance
//            w.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        // Load the media each time onStart() is called.

    }

    @Override
    public void onPause() {
        super.onPause();

        // In Android versions less than N (7.0, API 24), onPause() is the
        // end of the visual lifecycle of the app.  Pausing the video here
        // prevents the sound from continuing to play even after the app
        // disappears.
        //
        // This is not a problem for more recent versions of Android because
        // onStop() is now the end of the visual lifecycle, and that is where
        // most of the app teardown should take place.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mVideoView.pause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        // Media playback takes a lot of resources, so everything should be
        // stopped and released at this time.
        releasePlayer();
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current playback position (in milliseconds) to the
        // instance state bundle.
        outState.putInt(PLAYBACK_TIME, mVideoView.getCurrentPosition());
    }

    private void initializePlayer() {
        // Show the "Buffering..." message while the video loads.
        mBufferingTextView.setVisibility(VideoView.VISIBLE);

        // Buffer and decode the video sample.
        Uri videoUri = getMedia(VIDEO_SAMPLE);
        mVideoView.setVideoURI(videoUri);
        // Listener for onPrepared() event (runs after the media is prepared).
        mVideoView.setOnPreparedListener(
                new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        // Hide buffering message.
                        mBufferingTextView.setVisibility(VideoView.INVISIBLE);

                        // Restore saved position, if available.
                        if (mCurrentPosition > 0) {
                            mVideoView.seekTo(mCurrentPosition);
                        } else {
                            // Skipping to 1 shows the first frame of the video.
                            mVideoView.seekTo(1);
                        }

                        // Start playing!
                        mVideoView.start();
                    }
                });

        // Listener for onCompletion() event (runs after media has finished
        // playing).
        mVideoView.setOnCompletionListener(
                new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        Toast.makeText(getContext(),
                                "Video end",
                                Toast.LENGTH_SHORT).show();

                        // Return the video position to the start.
                        mVideoView.seekTo(0);
                    }
                });
    }

    private void releasePlayer() {
        mVideoView.stopPlayback();
    }

    private Uri getMedia(String mediaName) {
        if (URLUtil.isValidUrl(mediaName)) {
            // Media name is an external URL.
            return Uri.parse(mediaName);
        } else {
            // Media name is a raw resource embedded in the app.
            return Uri.parse("android.resource://" + getActivity().getPackageName() +
                    "/raw/" + mediaName);
        }
    }

    @Override
    public void playVideo(int pos, String tag) {
        home_top_pager.setVisibility(View.GONE);
        initializePlayer();
        mVideoView.setVisibility(View.VISIBLE);
        mBufferingTextView.setVisibility(View.VISIBLE);
    }

    public static ArrayList<String>base64Array;

    public class GetImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          //  progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;
            for (String url : urls) {
                map = downloadImage(url);
            }
            return map;
        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
            String s = BaseClass.BitMapToString(result);
            base64Array.add(s);
           // progressBar.setVisibility(View.GONE);
        }

        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;
            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.
                        decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }

        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();
                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }
    }

}
