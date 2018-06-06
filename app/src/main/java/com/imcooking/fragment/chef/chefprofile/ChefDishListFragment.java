package com.imcooking.fragment.chef.chefprofile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.ChefProfileData1;
import com.imcooking.R;
import com.imcooking.activity.Sub.Chef.ChefEditDish;
import com.imcooking.adapters.AdapterChefDishList;
import com.imcooking.fragment.chef.ChefHome;
import com.mukesh.tinydb.TinyDB;

import java.util.ArrayList;
import java.util.List;

public class ChefDishListFragment extends Fragment implements View.OnClickListener {

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
    private String loginData, user_type;

    private void init(){

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
        List<ChefProfileData1.ChefDishBean> chef_dish_list_current = new ArrayList<>();
        List<ChefProfileData1.ChefDishBean> chef_dish_list_old = new ArrayList<>();

        if(chef_dish_list.size() != 0) {
            for (int i = 0; i < chef_dish_list.size(); i++) {
                if (chef_dish_list.get(i).getDish_available().equals("Yes")) {
                    chef_dish_list_current.add(chef_dish_list.get(i));
                } else if (chef_dish_list.get(i).getDish_available().equals("No")) {
                    chef_dish_list_old.add(chef_dish_list.get(i));
                } else {
                }
            }
        }
        Log.d("MyDataSize", chef_dish_list_current.size()+"");
        AdapterChefDishList adapterChefDishListCurrent = new AdapterChefDishList(getParentFragment().getFragmentManager(),
                getContext(), getActivity(), chef_dish_list_current);
        AdapterChefDishList adapterChefDishListOld = new AdapterChefDishList(getParentFragment().getFragmentManager(),
                getContext(), getActivity(), chef_dish_list_old);
        viewPager_1.setAdapter(adapterChefDishListCurrent);
        viewPager_2.setAdapter(adapterChefDishListOld);
    }

    private void getMyData(){
        userDataBean = new ApiResponse.UserDataBean();
        tinyDB = new TinyDB(getContext());
        loginData = tinyDB.getString("login_data");
        userDataBean = new Gson().fromJson(loginData, ApiResponse.UserDataBean.class);
        user_type = userDataBean.getUser_type();
        if(user_type.equals("2")){
            tv_add_dish.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.chef_dish_list_add_dish){
            startActivity(new Intent(getContext(), ChefEditDish.class));
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
        } else { }
    }
}
