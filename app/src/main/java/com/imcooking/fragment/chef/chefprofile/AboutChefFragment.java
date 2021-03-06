package com.imcooking.fragment.chef.chefprofile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.imcooking.Model.api.response.ChefProfileData1;
import com.imcooking.Model.api.response.CuisineData;
import com.imcooking.R;
import com.imcooking.adapters.AdapterChefAboutCuisineList;
import com.imcooking.fragment.chef.ChefHome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AboutChefFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_chef, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
    }

    private TextView tv_description, tv_cuisine, tv_sub_cuisine;
    private RecyclerView rv;

    @SuppressLint("SetTextI18n")
    private void init(){

        rv = getView().findViewById(R.id.chef_about_recyler);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(horizontalLayoutManagaer);

        tv_description = getView().findViewById(R.id.about_chef_description);
//        tv_cuisine = getView().findViewById(R.id.about_chef_cuisine);
//        tv_sub_cuisine = getView().findViewById(R.id.about_chef_sub_cuisine);

        ChefProfileData1.ChefDataBean data = ChefHome.chefProfileData1.getChef_data();
        if(data != null) {
//            String s1 = data.getAbout();
//            Toast.makeText(getContext(), s1, Toast.LENGTH_SHORT).show();
//            String s2 = data.getBestcuisine_name();
//            String s3 = data.getCuisine_name();
            if (data.getAbout()!=null)
                tv_description.setText(data.getAbout()+"");
            List<ChefProfileData1.ChefDataBean.CuisineNameBean>cuisineData = new ArrayList<>();
            cuisineData = data.getCuisine_name();
            Collections.sort(cuisineData, new Comparator<ChefProfileData1.ChefDataBean.CuisineNameBean>() {
                @Override
                public int compare(ChefProfileData1.ChefDataBean.CuisineNameBean cuisineNameBean, ChefProfileData1.ChefDataBean.CuisineNameBean t1) {
                    return cuisineNameBean.getCuisine_name().compareTo(t1.getCuisine_name());
                }
            });

//            tv_cuisine.setText(data.getBestcuisine_name());

            AdapterChefAboutCuisineList adapter = new AdapterChefAboutCuisineList(getContext(), cuisineData);
            rv.setAdapter(adapter);

            //  tv_sub_cuisine.setText(data.getCuisine_name());
        }
    }
}