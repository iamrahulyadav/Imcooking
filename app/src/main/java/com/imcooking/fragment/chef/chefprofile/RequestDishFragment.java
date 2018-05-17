package com.imcooking.fragment.chef.chefprofile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imcooking.Model.api.response.AddCart;
import com.imcooking.R;
import com.imcooking.adapters.ChefDishRequestsAdatper;
import com.imcooking.adapters.OtherDishAdatper;

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

    private void init(){
        requestRecyclerView = getView().findViewById(R.id.recycler_chef_dish_requests);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        requestRecyclerView.setLayoutManager(horizontalLayoutManagaer);
        setDishAdapter();
    }
    List<AddCart.AddDishBean> dishDetails = new ArrayList<>();

    private void setDishAdapter(){
        ChefDishRequestsAdatper chefDishRequestsAdatper = new ChefDishRequestsAdatper(getContext(),dishDetails);
        requestRecyclerView.setAdapter(chefDishRequestsAdatper);

    }
}
