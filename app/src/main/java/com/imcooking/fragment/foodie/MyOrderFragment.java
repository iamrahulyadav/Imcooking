package com.imcooking.fragment.foodie;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imcooking.R;
import com.imcooking.activity.home.MainActivity;
import com.imcooking.adapters.AdapterFoodieMyOrder;
import com.imcooking.utils.CustomLayoutManager;

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        init();
    }

    private RecyclerView rv_1, rv_2;

    private void init(){

        rv_1 = getView().findViewById(R.id.recycler_foodie_my_orders_current);
        CustomLayoutManager manager = new CustomLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
//        LinearLayoutManager horizontalLayoutManagaer
//                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_1.setLayoutManager(manager);

        rv_2 = getView().findViewById(R.id.recycler_foodie_my_orders_past);
        CustomLayoutManager manager1 = new CustomLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv_2.setLayoutManager(manager1);

        setMyAdapter();

    }

    private void setMyAdapter(){

        AdapterFoodieMyOrder adapterFoodieMyOrder = new AdapterFoodieMyOrder(getContext(), getFragmentManager());
        rv_1.setAdapter(adapterFoodieMyOrder);
        rv_2.setAdapter(adapterFoodieMyOrder);
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity()).setBottomColor();
        ((MainActivity) getActivity()).tv_my_order.setTextColor(getResources().getColor(R.color.theme_color));
        ((MainActivity) getActivity()).iv_my_order.setImageResource(R.drawable.ic_salad_1);

    }
}
