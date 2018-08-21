package com.imcooking.fragment.foodie;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ContextThemeWrapper;
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
import com.imcooking.Model.ApiRequest.AddToCart;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.DishDetails;
import com.imcooking.R;
import com.imcooking.activity.Sub.Chef.Test1;
import com.imcooking.activity.Sub.Foodie.CartActivity;
import com.imcooking.activity.Sub.Foodie.ChefProfile;
import com.imcooking.activity.Sub.Foodie.OtherDishDishActivity;
import com.imcooking.adapters.DishDetailPagerAdapter;
import com.imcooking.adapters.Pager1;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeDetails extends Fragment implements View.OnClickListener, DishDetailPagerAdapter.DishDetailPlayClick {

    TabLayout tabLayout;
    Pager1 adapter;
    Dialog dialog ;

    private  String VIDEO_SAMPLE =
            "",isVideo;

    private VideoView mVideoView;
    private ProgressBar mBufferingTextView;

    // Current playback position (in milliseconds).
    private int mCurrentPosition = 0;

    // Tag for the instance state bundle.
    private static final String PLAYBACK_TIME = "play_time";

    int foodie_id;
    ApiResponse.UserDataBean userDataBean = new ApiResponse.UserDataBean();
    Gson gson = new Gson();
    TinyDB tinyDB;
    private ImageView imgLike, iv_cart;

    public HomeDetails() {
        // Required empty public constructor
    }

    private String id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
//        getActivity().setTheme(R.style.AppTheme1);
        super.onCreate(savedInstanceState);

        id = getArguments().getString("dish_id");
        tinyDB = new TinyDB(getContext());
        String login_data= tinyDB.getString("login_data");

        userDataBean  = gson.fromJson(login_data, ApiResponse.UserDataBean.class);

        foodie_id = userDataBean.getUser_id();
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_details, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private ImageView iv_share, imgPickUp, imgDeliviery, imgChef;
    private TextView txtDishName, txtChefName, txtAddToCart, txtLike, txtOtherDish, txtDistance, txtPrice,
            txtDeliverytype, txtAvailable,txtTime ;
    private LinearLayout chef_profile, layout;
    ViewPager pager, home_top_pager;

    private String TAG  = HomeDetails.class.getName();


    private ProgressBar progressBar;
    private VideoView videoView;

    private void createVideoDialog(){

        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_video);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressBar = dialog.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);

        videoView = dialog.findViewById(R.id.dialog_video);
        videoView.setVideoURI(Uri.parse("http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4"));

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                progressBar.setVisibility(View.GONE);
                videoView.start();
            }
        });

        dialog.show();
    }

    private double latitude, longitude;
    private TextView tv_count;

    private void init(){
        latitude = tinyDB.getDouble("lat",0);
        longitude = tinyDB.getDouble("lang",0);
        tv_count = getView().findViewById(R.id.foodie_dish_details_cart_count);

        iv_cart = getView().findViewById(R.id.foodie_home_details_img_cart);
        home_top_pager = getView().findViewById(R.id.fragment_home_auto_scroll_page);
        iv_share = getView().findViewById(R.id.home_details_share);
        imgChef = getView().findViewById(R.id.home_details_user_icon);
        txtOtherDish = getView().findViewById(R.id.home_details_txtOtherDish);
        txtAddToCart = getView().findViewById(R.id.tv_add_to_cart);
        txtDishName = getView().findViewById(R.id.fragment_home_details_txtDishName);
        txtChefName = getView().findViewById(R.id.fragment_home_details_txtChefName);
        txtDistance = getView().findViewById(R.id.fragment_home_details_txtDistance);
        txtPrice = getView().findViewById(R.id.fragment_home_details_txtdishPrice);
        txtDeliverytype = getView().findViewById(R.id.fragment_home_details_txtDeliveryType);
        txtLike = getView().findViewById(R.id.home_details_like);
        txtAvailable = getView().findViewById(R.id.fragment_home_details_txtAvailable);
        txtTime = getView().findViewById(R.id.fragment_home_details_txtTime);
        imgDeliviery =  getView().findViewById(R.id.home_pager_imgHomeDelivery);
        imgPickUp =  getView().findViewById(R.id.home_pager_imgPick);
        imgLike = getView().findViewById(R.id.home_details_heart);
        iv_share.setOnClickListener(this);
        imgLike.setOnClickListener(this);

        tabLayout= (TabLayout)getView(). findViewById(R.id.cardet_Tab);
        tabLayout.addTab(tabLayout.newTab().setText("Ingredients of Recipe"));
        tabLayout.addTab(tabLayout.newTab().setText("Special Note "));
        pager = getView().findViewById(R.id.cardet_viewpager);


        mVideoView = getView().findViewById(R.id.videoview);
        mBufferingTextView = getView().findViewById(R.id.buffering_textview);

        // Set up the media controller widget and attach it to the video view.
        MediaController controller = new MediaController(getContext());
        controller.setMediaPlayer(mVideoView);
        mVideoView.setMediaController(controller);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        chef_profile = getView().findViewById(R.id.home_details_chef_profile);
        chef_profile.setOnClickListener(this);
        layout = getView().findViewById(R.id.home_details_layout);
        txtOtherDish.setOnClickListener(this);
        txtAddToCart.setOnClickListener(this);

        iv_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CartActivity.class).putExtra("foodie_id",
                        foodie_id));
                getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

            }
        });

        tinyDB = new TinyDB(getContext());

    }

    @Override
    public void onResume() {
        super.onResume();

//        ((MainActivity) getActivity()).toolbar.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow(); // in Activity'selectedValue onCreate() for instance
//            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        String cart_count = tinyDB.getString("cart_count");
        tv_count.setText(cart_count);

        getDetails(id);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow(); // in Activity'selectedValue onCreate() for instance
            w.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
*/
    }

    String chef_id,dishId;
    ApiResponse apiResponse = new ApiResponse();
    DishDetails dishDetails = new DishDetails();
    ArrayList<String>nameList = new ArrayList<>();
    private ArrayList<String>imageList ;
    private DishDetailPagerAdapter dishDetailPagerAdapter;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 2000; // time in milliseconds between successive task executions.

    private void getDetails(String id){
        layout.setVisibility(View.GONE);
        if (nameList!=null){
            nameList.clear();
        }

        String detailRequest = "{\"dish_id\":" + id + ",\"foodie_id\":" + foodie_id + ",\"latitude\":\"" + latitude +
                "\",\"longitude\":\"" + longitude + "\"}";

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
                                {
                                    layout.setVisibility(View.VISIBLE);
                                    if (dishDetails.getDish_details().getDish_name()!=null)
                                        txtDishName.setText(dishDetails.getDish_details().getDish_name());
                                    txtChefName.setText(dishDetails.getDish_details().getChef_name());
                                    txtPrice.setText("£"+dishDetails.getDish_details().getDish_price());
                                    chef_id=dishDetails.getDish_details().getChef_id();
                                    dishId=dishDetails.getDish_details().getDish_id();
                                    if (dishDetails.getDish_details().getDish_available().equalsIgnoreCase("yes")){
                                        txtAvailable.setText("Available ");
                                        txtAvailable.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_circle, 0);
                                    } else {
                                        txtAvailable.setText("No");
                                    }
                                    if (dishDetails.getDish_details().getFoodie_dish_like()!=null && Integer.parseInt(dishDetails.getDish_details().getFoodie_dish_like())==1)
                                        imgLike.setImageResource(R.drawable.ic_heart_red);
                                    else  if (dishDetails.getDish_details().getFoodie_dish_like()!=null &&
                                            Integer.parseInt(dishDetails.getDish_details().getFoodie_dish_like())==0)
                                        imgLike.setImageResource(R.drawable.ic_heart);
                                    if (dishDetails.getDish_details().getDish_total_like()!=null)
                                        txtLike.setText(dishDetails.getDish_details().getDish_total_like());
                                    if (dishDetails.getDish_details().getChef_image()!=null){
                                        Picasso.with(getContext()).load(GetData.IMG_BASE_URL+dishDetails.getDish_details().getChef_image()).into(imgChef);
                                    } else {
                                        imgChef.setBackgroundResource(R.drawable.details_profile);
                                    }
                                    txtTime.setText(dishDetails.getDish_details().getDish_from()+" - "+dishDetails.getDish_details().getDish_to());
                                    if (dishDetails.getDish_details().getDish_homedelivery().equalsIgnoreCase("No")){
                                        txtDeliverytype.setText("Pickup");
                                    } else if (dishDetails.getDish_details().getDish_homedelivery().equalsIgnoreCase("YES")
                                            && dishDetails.getDish_details().getDish_pickup().equalsIgnoreCase("YES")){
                                        txtDeliverytype.setText("Home Delivery / Pickup");
                                    } else {
                                        txtDeliverytype.setText("Home Delivery");
                                    }
                                    nameList.add(dishDetails.getDish_details().getDish_special_note());
                                    nameList.add(dishDetails.getDish_details().getDish_description());
                                    if (dishDetails.getDish_details().getDish_homedelivery().equalsIgnoreCase("No")){
                                        txtDeliverytype.setText("Pickup");
                                        imgPickUp.setVisibility(View.VISIBLE);
                                        imgDeliviery.setVisibility(View.GONE);
                                    } else if (dishDetails.getDish_details().getDish_homedelivery().equalsIgnoreCase("YES")
                                            && dishDetails.getDish_details().getDish_pickup().equalsIgnoreCase("YES")){
                                        txtDeliverytype.setText("Home Delivery / Pickup");
                                        imgDeliviery.setVisibility(View.VISIBLE);
                                        imgPickUp.setVisibility(View.VISIBLE);
                                    } else {
                                        imgDeliviery.setVisibility(View.VISIBLE);
                                        imgPickUp.setVisibility(View.GONE);
                                        txtDeliverytype.setText("Home Delivery");
                                    }

                                    if(dishDetails.getDish_details().getDish_quantity().equals("0")){
                                        txtAddToCart.setText("Food Not Available");
                                    } else{
                                        txtAddToCart.setText("Add To Cart");
                                    }

                                    VIDEO_SAMPLE = GetData.IMG_BASE_URL+dishDetails.getDish_details().getDish_video()+"";
                                    String video = dishDetails.getDish_details().getDish_video()+"";
                                    if (video.length() > 0)
                                        isVideo = "yes";
                                    else isVideo = "no";
                                    txtLike.setText(dishDetails.getDish_details().getDish_total_like() + "");
                                    if (dishDetails.getDish_details().getDistance()!=null){
                                        if (dishDetails.getDish_details().getDistance().equals("0")){
                                            txtDistance.setText("0.00"+" miles");
                                        }else {
                                            String distnace = BaseClass.df2.format(Double.parseDouble(dishDetails.getDish_details().getDistance()))+"";
                                            txtDistance.setText(distnace+" miles");
                                        }
                                    }

                                    if (dishDetails.getDish_details().getDish_video()!=null
                                            &&dishDetails.getDish_details().getDish_video().toString().length()>0)
                                        imageList.add(dishDetails.getDish_details().getDish_image().get(0));

                                    if (dishDetails.getDish_details().getDish_image()!=null)
                                        imageList.addAll(dishDetails.getDish_details().getDish_image());

                                    setAdapter();
                                    /*After setting the adapter use the timer */
                                    final Handler handler = new Handler();
                                    final Runnable Update = new Runnable() {
                                        public void run() {
                                            if (currentPage == imageList.size()-1) {
                                                currentPage = 0;
                                            }
                                            home_top_pager.setCurrentItem(currentPage++, true);
                                        }
                                    };

                                    timer = new Timer(); // This will create a new Thread
                                    timer .schedule(new TimerTask() { // task to be scheduled

                                        @Override
                                        public void run() {
                                            handler.post(Update);
                                        }
                                    }, DELAY_MS, PERIOD_MS);
                                    adapter=new Pager1(getContext(), nameList);
                                    pager.setAdapter(adapter);
                                    pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                                    tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                        @Override
                                        public void onTabSelected(TabLayout.Tab tab) {
                                            pager.setCurrentItem(tab.getPosition());
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

    private ArrayList<String> arr_ = new ArrayList<>();

    private void setAdapter(){
        int size =0;
        if(isVideo.equals("yes")){
            size = imageList.size() + 1;
            arr_.add("image");
        } else {
            size = imageList.size();
        }
        for(int i=0; i<imageList.size(); i++){
            arr_.add("image");
        }
        dishDetailPagerAdapter = new DishDetailPagerAdapter(getContext(), imageList, this,
                isVideo, size, getActivity(), VIDEO_SAMPLE, arr_);
        home_top_pager.setAdapter(dishDetailPagerAdapter);

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if(id == R.id.home_details_share){

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
//            sendIntent.putExtra(Intent.EXTRA_TEXT, "Shared");
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, "Share Using"));
        } else if(id == R.id.home_details_chef_profile) {
            startActivityForResult(new Intent(getContext(), ChefProfile.class)
                            .putExtra("chef_id", chef_id)
                            .putExtra("foodie_id", foodie_id + ""),
                    ChefProfile.CHEF_PROFILE_CODE);
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

        } else if (id == R.id.home_details_txtOtherDish){
            startActivityForResult(new Intent(getContext(), OtherDishDishActivity.class).putExtra("chef_id",chef_id)
                    , OtherDishDishActivity.OTHER_DISH_CODE);
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
        }

        else if (id == R.id.tv_add_to_cart){

            if(!txtAddToCart.getText().toString().trim().equals("Food Not Available")) {
                AddToCart addToCart = new AddToCart();
                addToCart.setChef_id(Integer.parseInt(chef_id));
                addToCart.setFoodie_id(foodie_id);
                addToCart.setDish_id(dishId);
                addToCart.setAddcart_yes("");
                addToCart.setAddcart_id("");
                addCart(addToCart);
            }
        }

        else if (id == R.id.home_details_heart){
            dishlike();
        }
    }

    private void dishlike(){
        String s ="{\"chef_id\":" + chef_id + ",\"foodie_id\":" + foodie_id + ",\"dish_id\":" + id + "}";
        try {
            JSONObject jsonObject = new JSONObject(s);
            Log.d("MyRequest", jsonObject.toString());

            new GetData(getContext(), getActivity()).sendMyData(jsonObject, GetData.DISH_LIKE, getActivity(),
                    new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            try {
                                JSONObject job = new JSONObject(result);
                                if(job.getBoolean("status")){
                                    if(job.getString("msg").equals("Successfully dish like")){
                                        BaseClass.showToast(getContext(), "Successfully Liked");
                                        int i = Integer.parseInt(txtLike.getText().toString());
                                        txtLike.setText((i+1) + "");
                                        imgLike.setImageDrawable(getActivity().getResources().getDrawable((R.drawable.ic_heart_red)));
                                    } else if(job.getString("msg").equals("Successfully unlike")){
                                        BaseClass.showToast(getContext(), "Dish Successfully unliked");
                                        int i = Integer.parseInt(txtLike.getText().toString());
                                        txtLike.setText((i-1) + "");
                                        imgLike.setImageDrawable(getActivity().getResources().getDrawable((R.drawable.ic_heart)));
                                    } else{
                                        BaseClass.showToast(getContext(), getResources().getString(R.string.error));
                                    }
                                } else{
                                    BaseClass.showToast(getContext(), getResources().getString(R.string.error));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addCart(AddToCart addToCart) {

        Log.d(TAG, "addCart: "+new Gson().toJson(addToCart));
        new GetData(getContext(), getActivity())
                .getResponse(new Gson().toJson(addToCart), GetData.ADD_CART,
                        new GetData.MyCallback() {
                            @Override
                            public void onSuccess(String result) {
                                final String response = result;
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ApiResponse apiResponse = new Gson().fromJson(response, ApiResponse.class);
                                        if (apiResponse.isStatus()){
                                            if (apiResponse.getMsg().equalsIgnoreCase("Add cart Successfully")){
                                                createMyDialog();
                                            }
                                        } else
                                            if((apiResponse.getMsg().equalsIgnoreCase("This chef already added"))){
                                            createChefDialog();
                                            }else if(apiResponse.getMsg().equals("This dish already added")) {
                                                createDialogAlreadyAdded();
    //                                            Toast.makeText(getContext(), apiResponse.getMsg(), Toast.LENGTH_SHORT).show();
                                            } else{
                                            BaseClass.showToast(getContext(), "Something Went Wrong.");
                                            }
                                    }
                                });
                            }
                        });
    }

    private void createMyDialog(){
       final Dialog dialog= new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_add_to_cart);

         dialog.findViewById(R.id.tv_cancel_add_to_cart).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 dialog.dismiss();
             }
         });

         dialog.findViewById(R.id.txtdialog_ok).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 dialog.dismiss();
                 startActivity(new Intent(getContext(), CartActivity.class).putExtra("foodie_id",
                         userDataBean.getUser_id()));
//                 getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

             }
         });
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();

    }

    private void createChefDialog(){
        final Dialog dialog= new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_cart_chef_added);
        TextView txtRemove = dialog.findViewById(R.id.dialog_cart_chef_txt_remove);

        txtRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                AddToCart addToCart=new AddToCart();
                addToCart.setChef_id(Integer.parseInt(chef_id));
                addToCart.setFoodie_id(foodie_id);
                addToCart.setDish_id(dishId);
                addToCart.setAddcart_yes("yes");
                addToCart.setAddcart_id("");
                addCart(addToCart);
            }
        });

        dialog.findViewById(R.id.dialog_cart_chef_txt_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();
    }

    private void createDialogAlreadyAdded(){
        final Dialog dialog= new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_already_added);
        TextView tv1 = dialog.findViewById(R.id.dialog_already_added_go_to_cart);
        TextView tv2 = dialog.findViewById(R.id.dialog_already_added_cancel);

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                startActivity(new Intent(getContext(), CartActivity.class).putExtra("foodie_id",
                        userDataBean.getUser_id()));
                getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            if (requestCode == OtherDishDishActivity.OTHER_DISH_CODE){
                id = data.getStringExtra("dish_id");
                getDetails(id);
            } else if (requestCode == ChefProfile.CHEF_PROFILE_CODE){
                id = data.getStringExtra("dish_id");
                getDetails(id);
            } else{}
        } else{}
    }

    @Override
    public void onSaveInstanceState(Bundle outState)  {
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
        //createVideoDialog();
    }

    @Override
    public void onStop() {
        super.onStop();

        // Media playback takes a lot of resources, so everything should be
        // stopped and released at this time.
        if (videoView!=null)
        releasePlayer();
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
            if (videoView!=null)
            videoView.pause();
        }
    }

    private void releasePlayer() {
        videoView.stopPlayback();
    }
}