package com.imcooking.fragment;


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
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.imcooking.R;
import com.imcooking.activity.Sub.ChefProfile;
import com.imcooking.activity.home.MainActivity;
import com.imcooking.activity.main.setup.LoginActivity;
import com.imcooking.activity.main.setup.SignUpActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeDetails extends Fragment implements View.OnClickListener {


    public HomeDetails() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

/*        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_details, container, false);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
    }

    private ImageView iv_share;
    private LinearLayout chef_profile;

    private void init(){
        iv_share = getView().findViewById(R.id.home_details_share);
        iv_share.setOnClickListener(this);

        chef_profile = getView().findViewById(R.id.home_details_chef_profile);
        chef_profile.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

//        ((MainActivity) getActivity()).toolbar.setVisibility(View.GONE);
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

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if(id == R.id.home_details_share){

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
//            sendIntent.putExtra(Intent.EXTRA_TEXT, "Shared");
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, "Share Using"));
        } else if(id == R.id.home_details_chef_profile) {
            startActivity(new Intent(getContext(), ChefProfile.class));
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

        } else {}
    }
}
