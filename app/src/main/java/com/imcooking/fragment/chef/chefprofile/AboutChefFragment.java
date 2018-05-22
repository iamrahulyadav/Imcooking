package com.imcooking.fragment.chef.chefprofile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.imcooking.Model.api.response.ChefProfileData;
import com.imcooking.R;
import com.imcooking.fragment.chef.ChefHome;

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

    private void init(){

        tv_description = getView().findViewById(R.id.about_chef_description);
        tv_cuisine = getView().findViewById(R.id.about_chef_cuisine);
        tv_sub_cuisine = getView().findViewById(R.id.about_chef_sub_cuisine);

        ChefProfileData.ChefDataBean data = ChefHome.chefProfileData.getChef_data();
        if(data != null) {
//            String s1 = data.getAbout();
//            Toast.makeText(getContext(), s1, Toast.LENGTH_SHORT).show();
//            String s2 = data.getBestcuisine_name();
//            String s3 = data.getCuisine_name();
            tv_description.setText(data.getAbout());
            tv_cuisine.setText(data.getBestcuisine_name());
            tv_sub_cuisine.setText(data.getCuisine_name());
        }
    }
}
