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
import android.support.v7.widget.RecyclerView;
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
import com.imcooking.Model.api.response.AddCart;
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
import com.imcooking.adapters.ChefMyRequestsAdatper;
import com.imcooking.adapters.Page_Adapter;
import com.imcooking.utils.BaseClass;
import com.imcooking.utils.CustomLayoutManager;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class RequestDishFragment extends Fragment {

    private RecyclerView requestRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_request_dish, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        requestRecyclerView = getView().findViewById(R.id.recycler_chef_dish_requests);

        CustomLayoutManager manager = new CustomLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        requestRecyclerView.setLayoutManager(manager);
        setDishAdapter();
    }

    List<AddCart.AddDishBean> dishDetails = new ArrayList<>();

    private void setDishAdapter() {
        ChefMyRequestsAdatper chefMyRequestsAdatper = new ChefMyRequestsAdatper(getContext());
        requestRecyclerView.setAdapter(chefMyRequestsAdatper);

    }

}

