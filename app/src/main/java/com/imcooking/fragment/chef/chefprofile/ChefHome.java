package com.imcooking.fragment.chef.chefprofile;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
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
import android.widget.TextView;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.ChefProfileData;
import com.imcooking.Model.api.response.ChefProfileData1;
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
import com.imcooking.fragment.chef.chefprofile.RequestDishFragment;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;
import com.squareup.picasso.Picasso;

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
        return inflater.inflate(R.layout.fragment_chef_home, container, false);
    }

    String TAG = ChefProfile.class.getName();
    public static ChefProfileData chefProfileData = new ChefProfileData();
    //    public  static ChefProfileData1 chefProfileData = new ChefProfileData1();
    public static ChefProfileData1 chefProfileData1;
    TextView txtName, txtAddress, txtFollowers, btn_follow;
    ImageView imgChef, imgBack;
    Page_Adapter adapter;
    ViewPager pager;

    private TextView tv_phoneno;
    private LinearLayout layout;

    private String loginData, user_type, chef_id;
    private TinyDB tinyDB;
    private ApiResponse.UserDataBean userDataBean;

    private TextView btn_call;
    private String foodie_id;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getMyintentData();

        userDataBean = new ApiResponse.UserDataBean();
        tinyDB = new TinyDB(getContext());
        loginData = tinyDB.getString("login_data");
        userDataBean = new Gson().fromJson(loginData, ApiResponse.UserDataBean.class);
        user_type = userDataBean.getUser_type();

//        chef_id = userDataBean.getUser_id() + "";

        init();
        getchefProfile();


//        setMyData();
    }

    private void getMyintentData() {

        foodie_id = getArguments().getString("foodie_id");
        chef_id = getArguments().getString("chef_id");
    }

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageView iv_settings;

    private void init() {
        btn_call = getView().findViewById(R.id.chef_home_call_btn);
        layout = getView().findViewById(R.id.layout_chef_home);

        pager = getView().findViewById(R.id.cardet_viewpager);
        txtName = getView().findViewById(R.id.activity_chef_txtname);
        txtAddress = getView().findViewById(R.id.activity_chef_txtAdderss);
        txtFollowers = getView().findViewById(R.id.activity_chef_txtFollower);
        imgChef = getView().findViewById(R.id.chef_profile_image);
        imgBack = getView().findViewById(R.id.imgBack);
        btn_follow = getView().findViewById(R.id.chef_home_follow_button);

        tv_phoneno = getView().findViewById(R.id.chef_home_phoneno);
        iv_settings = getView().findViewById(R.id.chef_home_settings);
        iv_settings.setOnClickListener(this);

        if (user_type.equals("2")) {
            btn_follow.setVisibility(View.VISIBLE);
            iv_settings.setVisibility(View.GONE);
            btn_call.setVisibility(View.VISIBLE);
        } else if (user_type.equals("1")) {
            btn_call.setVisibility(View.GONE);
            btn_follow.setVisibility(View.GONE);
            iv_settings.setVisibility(View.VISIBLE);
        } else {
        }

        new Runnable() {
            @Override
            public void run() {
            }
        };
    }

    private void setupViewPager(ViewPager viewPager) {

        AdapterChefHomeViewPager adapter = new AdapterChefHomeViewPager(getChildFragmentManager());
        adapter.addFragment(new AboutChefFragment(), "ABOUT CHEF");
        adapter.addFragment(new ChefDishListFragment(), "DISHES");
        if (user_type.equals("1")) {
            adapter.addFragment(new RequestDishFragment(), "DISH REQUESTS");
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

        new GetData(getContext(), getActivity()).getResponse(s, "chefdetails", new GetData.MyCallback() {
            @Override
            public void onSuccess(final String result) {
                Log.d(TAG, "onSuccess: " + result);
                if (result!=null){
                    Log.d(TAG, "onSuccess: ChefData"+result);
                    chefProfileData1 = new ChefProfileData1();
                    chefProfileData1 = new Gson().fromJson(result, ChefProfileData1.class);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (chefProfileData1 != null) {
                                if (chefProfileData1.isStatus()) {
                                    layout.setVisibility(View.VISIBLE);
                                    txtAddress.setText(chefProfileData1.getChef_data().getAddress());
//                                    if (chefProfileData1.getChef_data().getChef_name()!=null&&!chefProfileData1.getChef_data().getChef_name().equals("null"))
                                    txtName.setText(chefProfileData1.getChef_data().getChef_full_name() + "");
                                    tv_phoneno.setText(chefProfileData1.getChef_data().getChef_phone() + "");
                                    Picasso.with(getContext()).load(GetData.IMG_BASE_URL + chefProfileData1
                                            .getChef_data().getChef_image()+"")
//                                .placeholder( R.drawable.progress_animatio+n )
                                            .into(imgChef);
                                    if (Integer.parseInt(chefProfileData1.getChef_data().getFollow()) == 1) {
                                        txtFollowers.setText(chefProfileData1.getChef_data().getFollow() + " Follower");
                                    } else if (Integer.parseInt(chefProfileData1.getChef_data().getFollow()) > 1) {
                                        txtFollowers.setText(chefProfileData1.getChef_data().getFollow() + " Followers");
                                    } else {
                                    }
                                    viewPager = getView().findViewById(R.id.chef_home_viewpager);
                                    tabLayout = (TabLayout) getView().findViewById(R.id.chef_home_tablayout);
                                    tabLayout.setupWithViewPager(viewPager);
                                    setupViewPager(viewPager);

                                } else {
                                    BaseClass.showToast(getContext(), "Something Went Wrong.");
                                }
                            } else {
                                BaseClass.showToast(getContext(), "Something Went Wrong");
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

    @Override
    public void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

//            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            w.setFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS, WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            w.setStatusBarColor(Color.TRANSPARENT);
        }

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }*/
/*
        //make full transparent statusBar
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(getActivity(), WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(getActivity(), WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
*/


        if (getActivity().getClass().getName().equals(MainActivity.class.getName())) {
            ((MainActivity) getActivity()).setBottomColor();
            ((MainActivity) getActivity()).tv_home.setTextColor(getResources().getColor(R.color.theme_color));
            ((MainActivity) getActivity()).iv_home.setImageResource(R.drawable.ic_home_1);
        } else {

        }

        getchefProfile();
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow(); // in Activity's onCreate() for instance
            w.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == R.id.chef_home_settings) {


            PopupWindow popupwindow_obj = showMyPopup();
            popupwindow_obj.showAsDropDown(iv_settings, 10, 20); // where u want show on view click event popupwindow.showAsDropDown(view, x, y);


        } else if (id == R.id.chef_home_popup_edit_profile) {

            startActivity(new Intent(getContext(), ChefEditProfile.class));
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
        } else if (id == R.id.chef_home_popup_change_password) {
            startActivity(new Intent(getContext(), ChangePassword.class));
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

        } else if (id == R.id.chef_home_popup_deacivate) {
            startActivity(new Intent(getContext(), ChefActivateDeactivate.class));
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

        } else if (id == R.id.chef_home_popup_logout) {
            new TinyDB(getContext()).remove("login_data");
            startActivity(new Intent(getContext(), LoginActivity.class));
            getActivity().finish();
        } else {
        }
    }

    public PopupWindow showMyPopup() {

        final PopupWindow popupWindow = new PopupWindow(getActivity());

        // inflate your layout or dynamically add view
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.popup_chef_home, null);
        TextView tv_edit_profile = view.findViewById(R.id.chef_home_popup_edit_profile);
        TextView tv_change_password = view.findViewById(R.id.chef_home_popup_change_password);
        TextView tv_deactivate = view.findViewById(R.id.chef_home_popup_deacivate);
        TextView tv_logout = view.findViewById(R.id.chef_home_popup_logout);

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
        popupWindow.setWidth(width - 700);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(null);
        popupWindow.setContentView(view);
        return popupWindow;
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
