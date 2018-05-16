package com.imcooking.fragment.chef.chefprofile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imcooking.Model.api.response.ChefProfileData;
import com.imcooking.R;
import com.imcooking.adapters.AdapterChefDishList;
import com.imcooking.adapters.AdapterChefHomeViewPager;
import com.imcooking.fragment.chef.ChefHome;

import java.util.List;

public class ChefDishListFragment extends Fragment {

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
    }


    private ViewPager viewPager_1, viewPager_2;

    private void init(){

        viewPager_1 = getView().findViewById(R.id.chef_dish_list_pager_one);
        viewPager_2 = getView().findViewById(R.id.chef_dish_list_pager_two);

        List<ChefProfileData.ChefDishBean> chef_dish_list = ChefHome.chefProfileData.getChef_dish();
        AdapterChefDishList adapterChefDishList = new AdapterChefDishList(getParentFragment().getFragmentManager(), getContext(), chef_dish_list);
        viewPager_1.setAdapter(adapterChefDishList);
        viewPager_2.setAdapter(adapterChefDishList);
    }
}
