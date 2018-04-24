package com.imcooking.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imcooking.R;
import com.imcooking.activity.home.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrderFragment extends Fragment {


    public MyOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_order, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity()).setBottomColor();
        ((MainActivity) getActivity()).tv_my_order.setTextColor(getResources().getColor(R.color.theme_color));
        ((MainActivity) getActivity()).iv_my_order.setImageResource(R.drawable.ic_salad_1);

    }
}
