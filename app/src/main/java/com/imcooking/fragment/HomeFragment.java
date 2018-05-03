package com.imcooking.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imcooking.Model.ApiRequest.SearchHomeRequest;
import com.imcooking.R;
import com.imcooking.activity.home.HomeDetails;
import com.imcooking.activity.home.MainActivity;
import com.imcooking.utils.BaseClass;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    SearchHomeRequest searchHomeRequest = new SearchHomeRequest();


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    private Toolbar toolbar;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        LinearLayout toolbar_left = getView().findViewById(R.id.toolbar_left);
        toolbar_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).drawerLayout1.openDrawer(GravityCompat.START);
            }
        });

        init();
    }

    private TextView tv_cusine, arrow_show_detail, txtCityName;
    private LinearLayout cusine_list;

    private void init(){

        cusine_list = getView().findViewById(R.id.home_cuisine_list);
        tv_cusine = getView().findViewById(R.id.home_cuisine);
        tv_cusine.setOnClickListener(this);

        arrow_show_detail = getView().findViewById(R.id.home_show_detail_1);
        txtCityName = getView().findViewById(R.id.fragment_home_txtcity);

        arrow_show_detail.setOnClickListener(this);
    }


    private boolean cuisine_status = false;

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.home_cuisine){
            if(!cuisine_status){
                cusine_list.setVisibility(View.VISIBLE);
                tv_cusine.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_drop_down_aarrow, 0);
                cuisine_status = true;
            }else{
                cusine_list.setVisibility(View.GONE);
                tv_cusine.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_drop_down_arrow, 0);
                cuisine_status = false;
            }
        } else if(v.getId() == R.id.home_show_detail_1){
            BaseClass.callFragment(new com.imcooking.fragment.HomeDetails(), new com.imcooking.fragment.HomeDetails()
                    .getClass().getName(), getFragmentManager());

        }
        else{

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setBottomColor();
        ((MainActivity) getActivity()).tv_home.setTextColor(getResources().getColor(R.color.theme_color));
        ((MainActivity) getActivity()).iv_home.setImageResource(R.drawable.ic_home_1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txtCityName.setText(MainActivity.stringBuffer.toString());
            }
        },3000);

    }
}
