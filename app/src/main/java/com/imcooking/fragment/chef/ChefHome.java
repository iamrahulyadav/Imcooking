package com.imcooking.fragment.chef;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.ChefProfileData;
import com.imcooking.Model.api.response.ChefProfileData1;
import com.imcooking.Model.api.response.CuisineData;
import com.imcooking.Model.api.response.FollowUnfollow;
import com.imcooking.R;
import com.imcooking.activity.Sub.Chef.ChangePassword;
import com.imcooking.activity.Sub.Chef.ChefActivateDeactivate;
import com.imcooking.activity.Sub.Chef.ChefEditProfile;
import com.imcooking.activity.Sub.Foodie.ChefProfile;
import com.imcooking.activity.home.MainActivity;
import com.imcooking.activity.main.setup.LoginActivity;
import com.imcooking.adapters.AdapterChefHomeViewPager;
import com.imcooking.adapters.Page_Adapter;
import com.imcooking.fragment.chef.chefprofile.AboutChefFragment;
import com.imcooking.fragment.chef.chefprofile.ChefDishListFragment;
import com.imcooking.fragment.chef.chefprofile.FoodieRequestADish;
import com.imcooking.fragment.chef.chefprofile.ChefMyRequestFragment;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

//import static com.imcooking.activity.home.MainActivity.isProfile;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChefHome extends Fragment implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    public ChefHome() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chef_home_1, container, false);
    }

    String TAG = ChefProfile.class.getName(), phoneNo;
    public static ChefProfileData chefProfileData = new ChefProfileData();
    //    public  static ChefProfileData1 chefProfileData = new ChefProfileData1();
    public static ChefProfileData1 chefProfileData1;
    TextView txtName, txtAddress, txtFollowers, btn_follow;
    ImageView imgChef, imgBack;
    Page_Adapter adapter;
    ViewPager pager;

    private TextView tv_phoneno;
    private RatingBar ratingBar;
    private LinearLayout layout;

    private String loginData, user_type; public static String chef_id;
    private TinyDB tinyDB;
    private ApiResponse.UserDataBean userDataBean;

    private TextView btn_call;
    public static String foodie_id, user_id;
    private String user_name;

    public static CuisineData cuisineData = new CuisineData();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        getView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        getMyintentData();

        userDataBean = new ApiResponse.UserDataBean();
        tinyDB = new TinyDB(getContext());
        loginData = tinyDB.getString("login_data");
        userDataBean = new Gson().fromJson(loginData, ApiResponse.UserDataBean.class);
        user_type = userDataBean.getUser_type();
        user_id = userDataBean.getUser_id() + "";
        user_name = userDataBean.getUser_name();


        init();
/*
        getchefProfile();
        getCuisines();
*/
//        getUserProfile(foodie_id);

//        setMyData();
    }

    private void getMyintentData() {

        foodie_id = getArguments().getString("foodie_id");
        chef_id = getArguments().getString("chef_id");

    }

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageView iv_settings, heart, iv_settings_1;

    private void init() {
        btn_call = getView().findViewById(R.id.chef_home_call_btn);
        layout = getView().findViewById(R.id.layout_chef_home);
//        layout = getView().findViewById(R.id.app_bar);
        pager = getView().findViewById(R.id.cardet_viewpager);
        txtName = getView().findViewById(R.id.activity_chef_txtname);
        txtAddress = getView().findViewById(R.id.activity_chef_txtAdderss);
        txtFollowers = getView().findViewById(R.id.activity_chef_txtFollower);
        imgChef = getView().findViewById(R.id.chef_profile_image);
        imgBack = getView().findViewById(R.id.imgBack);
        ratingBar = getView().findViewById(R.id.fragment_chef_home_rating);

        heart = getView().findViewById(R.id.chef_home_heart_icon);
        heart.setOnClickListener(this);
        btn_follow = getView().findViewById(R.id.chef_home_follow_button);
        btn_follow.setOnClickListener(this);
        btn_call.setOnClickListener(this);

        tv_phoneno = getView().findViewById(R.id.chef_home_phoneno);
        iv_settings = getView().findViewById(R.id.chef_home_settings);
        iv_settings.setOnClickListener(this);
        iv_settings_1 = getView().findViewById(R.id.chef_home_settings_1);
        iv_settings_1.setOnClickListener(this);

        if (user_type.equals("2")) {
            btn_follow.setVisibility(View.VISIBLE);
            iv_settings.setVisibility(View.GONE);
            btn_call.setVisibility(View.VISIBLE);
            heart.setVisibility(View.GONE);
        } else if (user_type.equals("1")) {
            btn_call.setVisibility(View.GONE);
            btn_follow.setVisibility(View.GONE);
            iv_settings.setVisibility(View.VISIBLE);
            heart.setVisibility(View.VISIBLE);
        } else {
        }


        viewPager = getView().findViewById(R.id.chef_home_viewpager);
        tabLayout = (TabLayout) getView().findViewById(R.id.chef_home_tablayout);
        tabLayout.setupWithViewPager(viewPager);
        setupViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {

        AdapterChefHomeViewPager adapter = new AdapterChefHomeViewPager(getChildFragmentManager());
        adapter.addFragment(new AboutChefFragment(), "ABOUT CHEF");
        adapter.addFragment(new ChefDishListFragment(), "DISHES");
        if (user_type.equals("1")) {
            adapter.addFragment(new ChefMyRequestFragment(), "DISH REQUESTS");
        } else if (user_type.equals("2")) {
            adapter.addFragment(new FoodieRequestADish(), "REQUEST A DISH");
        } else {
        }
        viewPager.setAdapter(adapter);

    }

    private void getchefProfile() {
        String s = "{\"chef_id\":" + chef_id + ",\"foodie_id\":" + foodie_id + "}";
        Log.d("MyRequest", s);
        layout.setVisibility(View.GONE);

        new GetData(getContext(), getActivity()).getResponse(s, GetData.CHEF_DETAILS, new GetData.MyCallback() {
            @Override
            public void onSuccess(final String result) {
                Log.d(TAG, "onSuccess: " + result);
                if (result!=null){
                    Log.d(TAG, "onSuccess: ChefData"+result);
                    chefProfileData1 = new ChefProfileData1();
                    chefProfileData1 = new Gson().fromJson(result, ChefProfileData1.class);
                    getActivity().runOnUiThread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            if (chefProfileData1 != null) {
                                if (chefProfileData1.isStatus()) {
                                    layout.setVisibility(View.VISIBLE);
                                   /* if (chefProfileData1.getChef_data().getAddress()==null
                                            &&chefProfileData1.getChef_data().getAddress().equalsIgnoreCase("null")){
                                        txtAddress.setText("Your address");
                                    } else
                                       */

                                    //    else txtAddress.setText("Your Address");
                                     createMyDialog();
                                    if (chefProfileData1.getChef_data().getChef_full_name()!=null){
                                        txtName.setText(chefProfileData1.getChef_data().getChef_full_name() + "");
                                        txtAddress.setText(chefProfileData1.getChef_data().getAddress()+" "+
                                                chefProfileData1.getChef_data().getChef_city());
                                        MainActivity.tv_name.setText(chefProfileData1.getChef_data().getChef_full_name() + "");
                                  }


                                    if (chefProfileData1.getChef_data().getChef_phone()!=null){
                                        MainActivity.tv_phone.setText(chefProfileData1.getChef_data().getChef_phone() + "");
                                        tv_phoneno.setText(chefProfileData1.getChef_data().getChef_phone() + "");
                                    }

                                    if (chefProfileData1.getChef_data().getRating() != null) {
                                        ratingBar.setRating(Float.parseFloat(chefProfileData1.getChef_data().getRating()));
                                    }
                                    if (chefProfileData1
                                            .getChef_data().getChef_image()!=null&&!chefProfileData1
                                            .getChef_data().getChef_image().equalsIgnoreCase("null")){
                                        Picasso.with(getContext()).load(GetData.IMG_BASE_URL + chefProfileData1
                                                .getChef_data().getChef_image())
                                                .into(imgChef);
                                    } else {
                                        imgChef.setImageResource(R.drawable.details_profile);
                                    }
//                                    tv_deactivate.setText(chefProfileData1.getChef_data().get);

                                    if (chefProfileData1.getChef_data().getChef_foodie_follow() == 0)
                                        btn_follow.setText("Follow");
                                    else if (chefProfileData1.getChef_data().getChef_foodie_follow() == 1) {
                                        btn_follow.setText("Unfollow");
                                    } else {
                                        btn_follow.setText("");
                                    }

                                    if (Integer.parseInt(chefProfileData1.getChef_data().getFollow()) == 1) {
                                        txtFollowers.setText(chefProfileData1.getChef_data().getFollow() + " Followers");
                                    } else if (Integer.parseInt(chefProfileData1.getChef_data().getFollow()) > 1) {
                                        txtFollowers.setText(chefProfileData1.getChef_data().getFollow() + " Followers");
                                    } else {
                                        txtFollowers.setText(" 0 Follower");
                                    }

     /*                               if(MainActivity.isProfile) {
                                        TabLayout.Tab tab = tabLayout.getTabAt(1);
                                        tab.select();
                                    } else {

                                        TabLayout.Tab tab = tabLayout.getTabAt(0);
                                        tab.select();
                                    }
     */                           } else {
                                    BaseClass.showToast(getContext(), getResources().getString(R.string.error));
                                }
                            }
                        }
                    });
                }
/*
                if (result != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                JSONObject jsonObject1 = jsonObject.getJSONObject("chef_data");
                                String s1 = jsonObject1.getString("cuisine_id");
                                String s2 = jsonObject1.getString("cuisine_name");
                                String s3 = jsonObject1.getString("bestcuisine_id");
                                String s4 = jsonObject1.getString("bestcuisine_name");

                                if (s1.equals("")) {
                                    jsonObject1.put("cuisine_id", "0");
                                }
                                if (s2.equals("")) {
                                    jsonObject1.put("cuisine_name", "abc");
                                }
                                if (s3.equals("")) {
                                    jsonObject1.put("bestcuisine_id", "0");
                                }
                                if (s4.equals("")) {
                                    jsonObject1.put("bestcuisine_name", "abc");
                                }

                                JSONArray jsonArray=jsonObject.getJSONArray("chef_dish");
for(int i=0;i<jsonArray.length();i++){

    if(jsonArray.getJSONObject(i).getString("").equals("null")){

    }
     if(jsonArray.getJSONObject(i).isNull("dish_image")){

     }
}

                                //  Toast.makeText(getActivity(),jsonObject1.getString("cuisine_id")+".."+jsonObject1.getString("cuisine_name"),Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                            try {
                                Gson gson = new GsonBuilder().serializeNulls().create();
                                chefProfileData = gson.fromJson(result, ChefProfileData.class);


                            } catch (JsonSyntaxException e) {
                                Log.d("MyException", e.toString());
                            } catch (NumberFormatException e) {
                                Log.d("MyException", e.toString());
                            }

                            Log.d("MyCheck", new Gson().toJson(chefProfileData));

                            if (chefProfileData != null) {
                                if (chefProfileData.isStatus()) {
                                    layout.setVisibility(View.VISIBLE);
                                    txtAddress.setText(chefProfileData.getChef_data().getAddress());
                                    txtName.setText(chefProfileData.getChef_data().getChef_name());
                                    tv_phoneno.setText(chefProfileData.getChef_data().getChef_phone() + "");
                                    Picasso.with(getContext()).load(GetData.IMG_BASE_URL + chefProfileData
                                            .getChef_data().getChef_image())
//                                .placeholder( R.drawable.progress_animation )
                                            .into(imgChef);
                                    if (chefProfileData.getChef_data().getFollow() == 1) {
                                        txtFollowers.setText(chefProfileData.getChef_data().getFollow() + " Follower");
                                    } else if (chefProfileData.getChef_data().getFollow() > 1) {
                                        txtFollowers.setText(chefProfileData.getChef_data().getFollow() + " Followers");
                                    } else {
                                    }
                                    viewPager = getView().findViewById(R.id.chef_home_viewpager);
                                    setupViewPager(viewPager);

                                    tabLayout = (TabLayout) getView().findViewById(R.id.chef_home_tablayout);
                                    tabLayout.setupWithViewPager(viewPager);
                                } else {
                                    BaseClass.showToast(getContext(), "Something Went Wrong.");
                                }
                            } else {
                                BaseClass.showToast(getContext(), "Something Went Wrong");
                            }

                        }
                    });
                }
*/
            }
        });
    }

    private void getCuisines(){
        try {
            JSONObject jsonObject = new JSONObject("{}");
//            layout.setVisibility(View.GONE);
            new GetData(getContext(), getActivity()).sendMyData(jsonObject, "cuisine",
                    getActivity(), new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {

                            cuisineData = new Gson().fromJson(result, CuisineData.class);
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getFollowUnfollow(){
        try {
            String s = "{\n" +
                    "  \"chef_id\":" + chef_id + ",\n" +
                    "  \"foodie_id\":" + user_id + "}";
            Log.d("TAGFOLL",s);
            JSONObject jsonObject = new JSONObject(s);


//            layout.setVisibility(View.GONE);
            new GetData(getContext(), getActivity()).sendMyData(jsonObject, "follow",
                    getActivity(), new GetData.MyCallback() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onSuccess(String result) {
//                            layout.setVisibility(View.VISIBLE);
                            FollowUnfollow followUnfollow = new Gson().fromJson(result, FollowUnfollow.class);
                            if(followUnfollow.isStatus()){
                                if (followUnfollow.getMsg().equals("Successfully follow")){
//                                    Toast.makeText(getContext(), followUnfollow.getMsg(), Toast.LENGTH_SHORT).show();
                                    BaseClass.showToast(getContext(), "Successfully Followed");
                                    btn_follow.setText("Unfollow");

                                    if(!txtFollowers.getText().toString().trim().equals("0 Follower")) {
                                        if (txtFollowers.getText().toString().equals("1 Follower")) {
                                            txtFollowers.setText(Integer.parseInt(txtFollowers.getText().toString()
                                                    .replace(" Follower", "")) + 1 + " Followers");
                                        } else {
                                            txtFollowers.setText(Integer.parseInt(txtFollowers.getText().toString()
                                                    .replace(" Followers", "")) + 1 + " Followers");
                                        }
                                    } else{
                                        txtFollowers.setText("1 Follower");
                                    }
//                                    txtFollowers.setText(Integer.parseInt(txtFollowers.getText()));
                                }
                                else if (followUnfollow.getMsg().equals("Successfully unfollow")) {
                                    BaseClass.showToast(getContext(), "Successfully Unfollowed");
                                    btn_follow.setText("Follow");

                                    if (txtFollowers.getText().toString().trim().equals("1 Follower")) {
                                        txtFollowers.setText("0 Follower");
                                    } else {
                                        txtFollowers.setText(Integer.parseInt(txtFollowers.getText().toString()
                                                .replace(" Followers", ""))
                                                - 1 + " Followers");
                                    }
                                }
                            }else {
                                Toast.makeText(getContext(), "Something went Wrong", Toast.LENGTH_SHORT).show();
                            }

//                            setMyCuisines(cuisineData);
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    Dialog dialog;
    private void createMyDialog(){
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.popup_chef_home);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setLayout(400, 350);

        TextView tv_edit_profile = dialog.findViewById(R.id.chef_home_popup_edit_profile);
        TextView tv_change_password = dialog.findViewById(R.id.chef_home_popup_change_password);
        tv_deactivate_1 = dialog.findViewById(R.id.chef_home_popup_deacivate);
        TextView tv_logout = dialog.findViewById(R.id.chef_home_popup_logout);

        if(chefProfileData1.getChef_data().getActivate_status().equals("1")){
            tv_deactivate_1.setText("Deactivate");
        } else if (chefProfileData1.getChef_data().getActivate_status().equals("0")){
            tv_deactivate_1.setText("Activate");
        } else{
            tv_deactivate_1.setText("DeActivate");
        }

        tv_edit_profile.setOnClickListener(this);
        tv_change_password.setOnClickListener(this);
        tv_deactivate_1.setOnClickListener(this);
        tv_logout.setOnClickListener(this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 105){
            if(resultCode == 105){

                TabLayout.Tab tab = tabLayout.getTabAt(1);
                tab.select();

                Toast.makeText(getContext(), "hii", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getActivity().getClass().getName().equals(MainActivity.class.getName())) {
//            ((MainActivity) getActivity()).setBottomColor();
//            ((MainActivity) getActivity()).tv_home.setTextColor(getResources().getColor(R.color.theme_color));
//            ((MainActivity) getActivity()).iv_home.setImageResource(R.drawable.ic_home_1);
            ((MainActivity) getActivity()).tv_home.setTextColor(getResources().getColor(R.color.theme_color));
            ((MainActivity) getActivity()).iv_home.setImageResource(R.drawable.ic_home_1);

            ((MainActivity) getActivity()).tv_profile.setTextColor(getResources().getColor(R.color.text_color_10));
            ((MainActivity) getActivity()).iv_profile.setImageResource(R.drawable.ic_user_name);

            ((MainActivity) getActivity()).tv_my_order.setTextColor(getResources().getColor(R.color.text_color_10));
            ((MainActivity) getActivity()).iv_my_order.setImageResource(R.drawable.ic_salad);

            ((MainActivity) getActivity()).tv_notification.setTextColor(getResources().getColor(R.color.text_color_10));
            ((MainActivity) getActivity()).iv_notification.setImageResource(R.drawable.ic_ring);
        } else {

        }

        getchefProfile();
        getCuisines();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if(popupwindow_obj != null) {
            popupwindow_obj.dismiss();
            popupwindow_obj = null;
        }

    }

    @Override
    public void onStop() {
        super.onStop();

        if(popupwindow_obj != null) {
            popupwindow_obj.dismiss();
            popupwindow_obj = null;
        }

    }

    private PopupWindow popupwindow_obj;

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == R.id.chef_home_settings) {

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;

            if(width == 720 && height == 1184){
                dialog.show();
            } else {
                popupwindow_obj = showMyPopup();
                popupwindow_obj.showAsDropDown(iv_settings, 10, 20); // where u want show on view click event popupwindow.showAsDropDown(view, x, y);
            }
        } else if (id == R.id.chef_home_settings_1) {
            popupwindow_obj = showMyPopup();
            popupwindow_obj.showAsDropDown(iv_settings_1, 10, 20); // where u want show on view click event popupwindow.showAsDropDown(view, x, y);
        } else if (id == R.id.chef_home_popup_edit_profile) {
            if(dialog != null){
                dialog.dismiss();
            }
            startActivity(new Intent(getContext(), ChefEditProfile.class));
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
        } else if (id == R.id.chef_home_popup_change_password) {
            if(dialog != null){
                dialog.dismiss();
            }
            startActivity(new Intent(getContext(), ChangePassword.class));
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
        } else if (id == R.id.chef_home_popup_deacivate) {
            if(dialog != null){
                dialog.dismiss();
            }
            startActivity(new Intent(getContext(), ChefActivateDeactivate.class));
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
        } else if (id == R.id.chef_home_popup_logout) {
            if(dialog != null){
                dialog.dismiss();
            }
            String s = "{\"device_id\":\"cM7WiSvFCvI:APA91bHrXcZOzGoxDKT7ksLche1KAzgxStLCtgyUjD3GiXBchJPp4p0qsOG67M3KkPkvcK4OKbuvjhqHCP8CrW8UlVfI548etzPkXQu1w1tZH0IVchq23yDZ-BP13XvtjWo5yLQ-RR2hC6IHVk3Mn7AbzQPAFOqj8Q\", \"user_name\":"
                    + user_name + "}";
            Log.d("MyRequest", s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                new GetData(getContext(), getActivity()).sendMyData(jsonObject, GetData.LOGOUT,
                        getActivity(), new GetData.MyCallback() {
                            @Override
                            public void onSuccess(String result) {
                                ApiResponse apiResponse = new Gson().fromJson(result, ApiResponse.class);
                                if(apiResponse.isStatus()){
                                    if (apiResponse.getMsg().equals("device_id deleted Successfully ")){
                                        new TinyDB(getContext()).remove("login_data");
                                        startActivity(new Intent(getContext(), LoginActivity.class));
                                        getActivity().finish();
                                    } else{
                                        BaseClass.showToast(getContext(), "Something Went Wrong.");
                                    }
                                }
                            }
                        });
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (id == R.id.chef_home_follow_button){
            getFollowUnfollow();
        }
        else if (id == R.id.chef_home_heart_icon){
            BaseClass.callFragment(new ChefFollowersFragment(),ChefFollowersFragment.class.getName(),getFragmentManager());
        }
        else if (id == R.id.chef_home_call_btn){
            phoneNo = tv_phoneno.getText().toString().trim();
            if (phoneNo!=null){
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNo, null));
                startActivity(intent);
            }
        }
    }

    private TextView tv_deactivate, tv_deactivate_1;
    public PopupWindow showMyPopup() {
        final PopupWindow popupWindow = new PopupWindow(getActivity());

        // inflate your layout or dynamically add view
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.popup_chef_home, null);
        TextView tv_edit_profile = view.findViewById(R.id.chef_home_popup_edit_profile);
        TextView tv_change_password = view.findViewById(R.id.chef_home_popup_change_password);
        tv_deactivate = view.findViewById(R.id.chef_home_popup_deacivate);
        TextView tv_logout = view.findViewById(R.id.chef_home_popup_logout);

//        tv_deactivate.setText(chefProfileData1.getChef_data().);

        if(chefProfileData1.getChef_data().getActivate_status().equals("1")){
            tv_deactivate.setText("Deactivate");
        } else if (chefProfileData1.getChef_data().getActivate_status().equals("0")){
            tv_deactivate.setText("Activate");
        } else{
            tv_deactivate.setText("DeActivate");
        }

        tv_edit_profile.setOnClickListener(this);
        tv_change_password.setOnClickListener(this);
        tv_deactivate.setOnClickListener(this);
        tv_logout.setOnClickListener(this);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        popupWindow.setFocusable(true);
        popupWindow.setWidth(width - 600);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setContentView(view);
        return popupWindow;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}