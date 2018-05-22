package com.imcooking.fragment.foodie;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.imcooking.R;
import com.imcooking.adapters.AdapterFoodieMyRequest;
import com.imcooking.adapters.ChefMyRequestsAdatper;

/**
 * A simple {@link Fragment} subclass.
 */

public class FoodieMyRequest extends Fragment {


    public FoodieMyRequest() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_foodie_my_request, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        init();

    }

    private RecyclerView rv;

    private void init(){
        rv = getView().findViewById(R.id.recycler_foodie_my_requests);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(horizontalLayoutManagaer);

        setMyAdapter();
    }

    private void setMyAdapter(){
        AdapterFoodieMyRequest adatper = new AdapterFoodieMyRequest(getContext());
        rv.setAdapter(adatper);
    }


}