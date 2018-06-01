package com.imcooking.fragment.chef;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imcooking.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChefOrderListFragment extends Fragment {


    public ChefOrderListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chef_order_list, container, false);
    }

}
