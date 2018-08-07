package com.imcooking.fragment.chef.chefprofile;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.ChefProfileData1;
import com.imcooking.R;
import com.imcooking.activity.Sub.Chef.ChefEditDish;
import com.imcooking.activity.home.MainActivity;
import com.imcooking.activity.main.setup.LoginActivity;
import com.imcooking.activity.main.setup.SignUpActivity;
import com.imcooking.adapters.AdapterChefDishList;
import com.imcooking.fragment.chef.ChefHome;
import com.imcooking.fragment.chef.DishLikersFragment;
import com.imcooking.fragment.foodie.SearchFragment;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChefDishListFragment extends Fragment implements View.OnClickListener, AdapterChefDishList.Click_interface_chef_dish_list {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chef_dish_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
        getMyData();
    }

    private ViewPager viewPager_1, viewPager_2;
    private TextView tv_add_dish;
    List<ChefProfileData1.ChefDishBean> chef_dish_list=new ArrayList<>();
    private ApiResponse.UserDataBean userDataBean;// = new ApiResponse.UserDataBean();
    private TinyDB tinyDB;
    private String loginData, user_type, user_id;

    private ArrayList<String> arr_like_current = new ArrayList<>();
    private ArrayList<String> arr_like_old = new ArrayList<>();

    private AdapterChefDishList adapterChefDishListCurrent;
    private AdapterChefDishList adapterChefDishListOld;

    private List<ChefProfileData1.ChefDishBean> chef_dish_list_current = new ArrayList<>();
    private List<ChefProfileData1.ChefDishBean> chef_dish_list_old = new ArrayList<>();

    private LinearLayout layout_current_dish_no_record, layout_old_dish_no_record;

    private TextView tv_current_counting, tv_old_counting;

    private void init(){

        createMyDialog();

        layout_current_dish_no_record = getView().findViewById(R.id.chef_dish_list_current_dish_no_record);
        layout_old_dish_no_record = getView().findViewById(R.id.chef_dish_list_old_dish_no_record);

        tv_add_dish = getView().findViewById(R.id.chef_dish_list_add_dish);
        tv_add_dish.setOnClickListener(this);

        viewPager_1 = getView().findViewById(R.id.chef_dish_list_pager_one);
        viewPager_2 = getView().findViewById(R.id.chef_dish_list_pager_two);
        viewPager_1.setPageMargin(10);
        viewPager_2.setPageMargin(10);

//        if (getActivity().getClass().getName().equals(MainActivity.class.getName())){
            chef_dish_list = ChefHome.chefProfileData1.getChef_dish();

//        } else {
//            chef_dish_list = ChefProfile.chefProfileData.getChef_dish();
//        }
        chef_dish_list_current.clear(); chef_dish_list_old.clear();
        if(chef_dish_list.size() != 0) {
            for (int i = 0; i < chef_dish_list.size(); i++) {
                if (chef_dish_list.get(i).getDish_available().equals("Yes") ||
                        chef_dish_list.get(i).getDish_available().equals("yes") ) {
                    chef_dish_list_current.add(chef_dish_list.get(i));
                    arr_like_current.add(chef_dish_list.get(i).getDish_foodie_like());
                } else if (chef_dish_list.get(i).getDish_available().equals("No") ||
                chef_dish_list.get(i).getDish_available().equals("no")) {
                    chef_dish_list_old.add(chef_dish_list.get(i));
                    arr_like_old.add(chef_dish_list.get(i).getDish_foodie_like());
                } else {
                }
            }
        }

        if(chef_dish_list_current.size() == 0){
            viewPager_1.setVisibility(View.GONE);
            layout_current_dish_no_record.setVisibility(View.VISIBLE);
        } else{
            viewPager_1.setVisibility(View.VISIBLE);
            layout_current_dish_no_record.setVisibility(View.GONE);
        }

        if(chef_dish_list_old.size() == 0){
            viewPager_2.setVisibility(View.GONE);
            layout_old_dish_no_record.setVisibility(View.VISIBLE);
        } else{
            viewPager_2.setVisibility(View.VISIBLE);
            layout_old_dish_no_record.setVisibility(View.GONE);
        }

        Log.d("MyDataSize", chef_dish_list_current.size()+"");
        adapterChefDishListCurrent = new AdapterChefDishList(getParentFragment().getFragmentManager(),
                getContext(), getActivity(), chef_dish_list_current, arr_like_current, this, "current");

        adapterChefDishListOld = new AdapterChefDishList(getParentFragment().getFragmentManager(),
                getContext(), getActivity(), chef_dish_list_old, arr_like_old, this, "old");

        viewPager_1.setAdapter(adapterChefDishListCurrent);
        viewPager_2.setAdapter(adapterChefDishListOld);


        tv_current_counting = getView().findViewById(R.id.fragment_chef_dish_list_counting_current);
        tv_old_counting = getView().findViewById(R.id.fragment_chef_dish_list_counting_old);

        if(chef_dish_list_current != null && chef_dish_list_current.size() != 0) {
            tv_current_counting.setText("(" + chef_dish_list_current.size() + ")");
        } else {
            tv_current_counting.setText("(0)");
        }
        if(chef_dish_list_old != null && chef_dish_list_old.size() != 0) {
            tv_old_counting.setText("(" + chef_dish_list_old.size() + ")");
        } else {
            tv_old_counting.setText("(0)");
        }
    }

    private void getMyData(){
        userDataBean = new ApiResponse.UserDataBean();
        tinyDB = new TinyDB(getContext());
        loginData = tinyDB.getString("login_data");
        userDataBean = new Gson().fromJson(loginData, ApiResponse.UserDataBean.class);
        user_type = userDataBean.getUser_type();
        user_id = userDataBean.getUser_id() + "";

        Log.d("UserData", loginData);
        if(user_type.equals("2")){
            tv_add_dish.setVisibility(View.GONE);
        }
    }

    private Dialog dialog;
    private TextView tv_dialog, tv_ok_dialog, tv_cross_dialog;

    private void createMyDialog(){

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_add_dish);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        tv_dialog = dialog.findViewById(R.id.dialog_add_dish_text);
        tv_ok_dialog = dialog.findViewById(R.id.dialog_add_dish_btn);
        tv_cross_dialog = dialog.findViewById(R.id.dialog_add_dish_cross);

        tv_ok_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tv_cross_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.chef_dish_list_add_dish) {
            if (ChefHome.chefProfileData1.getChef_data().getAddress().equals("") ||
                    ChefHome.chefProfileData1.getChef_data().getAddress() == null) {

                tv_dialog.setText(getResources().getString(R.string.dialog_add_dish_text_1));
                dialog.show();
            } else if(ChefHome.chefProfileData1.getChef_data().getCuisine_name().size() == 0) {
                tv_dialog.setText(getResources().getString(R.string.dialog_add_dish_text_2));
                dialog.show();
            } else {
                startActivity(new Intent(getContext(), ChefEditDish.class));
                getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        }else {

        }
    }

//    private Dialog dialog;
//    private TextView tv_ok_dialog, tv_cross_dialog;

    @Override
    public void click_me_chef_dish_list(int position, String click_type) {


        if(user_type.equals("2")){
            like_dislike(position, click_type);
        } else{
            dish_likers(chef_dish_list.get(position).getDish_id()+"");
        }
//        BaseClass.showToast(getContext(), position + "");

    }

    private void like_dislike(final int position, final String click_type){

        String s = "{\"chef_id\":" + ChefHome.chefProfileData1.getChef_data().getChef_id() +
                ",\"foodie_id\":" + user_id +
                ",\"dish_id\":" + chef_dish_list.get(position).getDish_id() + "}";

        try {
            JSONObject job = new JSONObject(s);

            new GetData(getContext(), getActivity()).sendMyData(job, GetData.DISH_LIKER, getActivity(), new GetData.MyCallback() {
                @Override
                public void onSuccess(String result) {

                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if(jsonObject.getBoolean("status")){
                            if(jsonObject.getString("msg").equals("Successfully dish like")){

                                if(click_type.equals("current")){
                                    arr_like_current.set(position, "1");
                                    int i = Integer.parseInt(chef_dish_list_current.get(position).getLike_no());
                                    chef_dish_list_current.get(position).setLike_no((i+1) + "");

                                    adapterChefDishListCurrent.notifyDataSetChanged();
                                } else{
                                    arr_like_old.set(position, "1");
                                    int i = Integer.parseInt(chef_dish_list_old.get(position).getLike_no());
                                    chef_dish_list_old.get(position).setLike_no((i+1) + "");
                                    adapterChefDishListOld.notifyDataSetChanged();
                                }

                                BaseClass.showToast(getContext(), "Successfully Liked");

                            } else if(jsonObject.getString("msg").equals("Successfully unlike")){
                                if(click_type.equals("current")){
                                    int i = Integer.parseInt(chef_dish_list_current.get(position).getLike_no());
                                    chef_dish_list_current.get(position).setLike_no((i-1) + "");
                                    arr_like_current.set(position, "0");
                                    adapterChefDishListCurrent.notifyDataSetChanged();
                                } else{
                                    arr_like_old.set(position, "0");
                                    int i = Integer.parseInt(chef_dish_list_old.get(position).getLike_no());
                                    chef_dish_list_old.get(position).setLike_no((i-1) + "");
                                    adapterChefDishListOld.notifyDataSetChanged();
                                }
                                BaseClass.showToast(getContext(), "Dish Successfully unliked");
//                                int i = Integer.parseInt(homeData.getChef_dish().get(position).getDishlikeno());
//                                homeData.getChef_dish().get(position).setDishlikeno((i-1) + "");
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

    private void dish_likers(String dish_id){
        Bundle bundle = new Bundle();
        bundle.putString("dish_id", dish_id);
        DishLikersFragment dishLikersFragment = new DishLikersFragment();
        dishLikersFragment.setArguments(bundle);

        BaseClass.callFragment(dishLikersFragment,DishLikersFragment.class.getName(),getParentFragment().getFragmentManager());
    }
}
