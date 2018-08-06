package com.imcooking.fragment.chef;


import android.annotation.SuppressLint;
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
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.ChefProfileData;
import com.imcooking.Model.api.response.DishDetails;
import com.imcooking.R;
import com.imcooking.activity.Sub.Chef.ChefEditDish;
import com.imcooking.adapters.AdapterChefDishList;
import com.imcooking.adapters.DishDetailPagerAdapter;
import com.imcooking.adapters.Pager1;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;
import com.squareup.picasso.Picasso;

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
            "", isVideo, user_type;

    private VideoView mVideoView;
    private ProgressBar mBufferingTextView;
    // Current playback position (in milliseconds).
    private int mCurrentPosition = 0;
    private TinyDB tinyDB;
    private ApiResponse.UserDataBean userDataBean;
    // Tag for the instance state bundle.
    private static final String PLAYBACK_TIME = "play_time";
    private ImageView iv_share;

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
//        getMyData();
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

    private List<String> arrayList = new ArrayList<>();

    private ImageView iv_home_delivery_icon, iv_pickup_icon, iv_heart;

    private LinearLayout layout;

    private void init(){
        viewPager = getView().findViewById(R.id.chef_dish_details_view_pager);
        tabLayout = getView().findViewById(R.id.chef_dish_details_tab_layout);

        tinyDB = new TinyDB(getActivity());
        String loginData = tinyDB.getString("login_data");
        userDataBean = new ApiResponse.UserDataBean();
        userDataBean = new Gson().fromJson(loginData, ApiResponse.UserDataBean.class);
        user_type = userDataBean.getUser_type();

        iv_share = getView().findViewById(R.id.fragment_chef_dish_details_share);
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
        iv_heart = getView().findViewById(R.id.chef_dish_detalis_heart);

        tv_edit_dish.setOnClickListener(this);
        iv_heart.setOnClickListener(this);
        iv_share.setOnClickListener(this);

        mVideoView = getView().findViewById(R.id.fragment_chef_dish_details_videoview);
        mBufferingTextView = getView().findViewById(R.id.buffering_textview);

        // Set up the media controller widget and attach it to the video view.
        MediaController controller = new MediaController(getContext());
        controller.setMediaPlayer(mVideoView);
        mVideoView.setMediaController(controller);

        iv_home_delivery_icon = getView().findViewById(R.id.chef_dish_detalis_icon_home_delivery);
        iv_pickup_icon = getView().findViewById(R.id.chef_dish_detalis_icon_pickup);

        layout = getView().findViewById(R.id.home_details_layout);
    }

    private void getMyData(){

        str_id = getArguments().getString("id");
        getDetails(str_id);
    }

    ArrayList<String>nameList = new ArrayList<>();
    private ArrayList<String>imageList ;
    ApiResponse apiResponse = new ApiResponse();
    private Gson gson = new Gson();
    DishDetails dishDetails = new DishDetails();

    private ViewPager viewPager;// = getView().findViewById(R.id.chef_dish_details_view_pager);
    private TabLayout tabLayout;// = getView().findViewById(R.id.chef_dish_details_tab_layout);

    private void getDetails(String id){
        layout.setVisibility(View.GONE);

        if (nameList!=null){
            nameList.clear();
        }
        String detailRequest = "{\"dish_id\":" + id + ",\"foodie_id\": 1}";
        Log.d("MyRequest", detailRequest);
        new GetData(getContext(), getActivity()).getResponse(detailRequest, GetData.DISH_DETAILS,
                new GetData.MyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        imageList = new ArrayList<>();
                        apiResponse = gson.fromJson(result, ApiResponse.class);
                        apiResponse.dish_details = gson.fromJson(result, DishDetails.class);
                        dishDetails = apiResponse.dish_details;
                        if (apiResponse.isStatus()){
                            getActivity().runOnUiThread(new Runnable() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void run() {
                                    if (dishDetails.getDish_details()!=null){

                                            layout.setVisibility(View.VISIBLE);
                                            if (dishDetails.getDish_details().getDish_name()!=null)
                                                str_name = dishDetails.getDish_details().getDish_name();

                                            str_price = "£"+dishDetails.getDish_details().getDish_price();
                                            str_id = dishDetails.getDish_details().getDish_id();
                                            str_available = dishDetails.getDish_details().getDish_available();
                                            str_likes = dishDetails.getDish_details().getDish_total_like();
                                            arrayList = dishDetails.getDish_details().getDish_image();
                                            str_time = dishDetails.getDish_details().getDish_from() +
                                                    " - " + dishDetails.getDish_details().getDish_to();
                                            str_time_1 = dishDetails.getDish_details().getDish_from();
                                            str_time_2 = dishDetails.getDish_details().getDish_to();
                                            str_homedelivery = dishDetails.getDish_details().
                                                    getDish_homedelivery();
                                            str_pickup = dishDetails.getDish_details().getDish_pickup();
                                            str_special_note = dishDetails.getDish_details()
                                                    .getDish_special_note();
                                            str_description = dishDetails.getDish_details()
                                                    .getDish_description();

                                            VIDEO_SAMPLE = GetData.IMG_BASE_URL+dishDetails.getDish_details().getDish_video()+"";
                                            if (VIDEO_SAMPLE != null)
                                                isVideo = "yes";
                                            else isVideo = "no";

                                            str_count = dishDetails.getDish_details().getDish_quantity() + "";
                                            str_qyt = dishDetails.getDish_details().getDish_quantity() + "";

                                        base64Array = new ArrayList<>();
                                        Log.d("TAG", "getMyData: "+arrayList.size());

                                        if (arrayList.size()>0) {
                                            for (int i = 0; i < arrayList.size(); i++) {
                                                String urls[] = new String[arrayList.size()];
                                                String imageUrl = GetData.IMG_BASE_URL + arrayList.get(i);
                                                urls[i] = imageUrl;
                                                // Execute the task
                                                new GetImage().execute(GetData.IMG_BASE_URL + arrayList.get(i));

                                            }
                                        }

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

                                        tv_dish_price.setText(str_price);
                                        if (str_description!=null){
//            tv_dish_description.setText(str_description);
                                        }

                                        setAdapter();


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


                                        int c = tabLayout.getTabCount();
                                        if(c!=0){
                                            tabLayout.removeAllTabs();
                                        }
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
                                }
                            });

                        } else{
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    BaseClass.showToast(getContext(), "Something went wrong ");
                                }
                            });
                        }
                    }

                });
    }

    private void setAdapter() {
        int size =0;
        ArrayList<String> arr_ = new ArrayList<>();
        if(isVideo.equals("yes")){
            size = arrayList.size() + 1;
            arr_.add("video");
        } else {
            size = arrayList.size();
        }
        for(int i=0; i<arrayList.size(); i++){
            arr_.add("image");
        }

        dishDetailPagerAdapter = new DishDetailPagerAdapter(getContext(), arrayList,this,
                isVideo, size, getActivity(), VIDEO_SAMPLE, arr_);
        home_top_pager.setAdapter(dishDetailPagerAdapter);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if(id == R.id.chef_home_details_edit_dish){
            String s = new Gson().toJson(arrayList);

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
                    .putExtra("image", s)
                    .putExtra("qyt",str_qyt)
                    .putExtra("video",VIDEO_SAMPLE)
                    .putExtra("time1", str_time_1)
                    .putExtra("time2", str_time_2)
            );

            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
        } else if (id == R.id.chef_dish_detalis_heart){
           if (user_type.equals("1")){
               dish_likers(str_id);
           }
        } else if (id == R.id.fragment_chef_dish_details_share){

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
//            sendIntent.putExtra(Intent.EXTRA_TEXT, "Shared");
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, "Share Using"));
        }
    }

    private void dish_likers(String dish_id){
        Bundle bundle = new Bundle();
        bundle.putString("dish_id", dish_id);
        DishLikersFragment dishLikersFragment = new DishLikersFragment();
        dishLikersFragment.setArguments(bundle);
        BaseClass.callFragment(dishLikersFragment,DishLikersFragment.class.getName(), getFragmentManager());
    }

    @Override
    public void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow(); // in Activity's onCreate() for instance
//            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        getMyData();
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
