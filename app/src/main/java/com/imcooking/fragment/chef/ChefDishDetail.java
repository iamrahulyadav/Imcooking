package com.imcooking.fragment.chef;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.imcooking.R;
import com.imcooking.activity.Sub.Chef.ChefEditDish;

import java.lang.reflect.Field;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChefDishDetail extends Fragment implements View.OnClickListener {


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
    }

    private TextView tv_edit_dish;

    private void init(){

        tv_edit_dish = getView().findViewById(R.id.chef_home_details_edit_dish);
        tv_edit_dish.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if(id == R.id.chef_home_details_edit_dish){
            startActivity(new Intent(getContext(), ChefEditDish.class));
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
        } else{}
    }


    @Override
    public void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow(); // in Activity's onCreate() for instance
            w.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

}
