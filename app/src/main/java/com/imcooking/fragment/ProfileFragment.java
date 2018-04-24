package com.imcooking.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.imcooking.R;
import com.imcooking.activity.Sub.ChefILove;
import com.imcooking.activity.Sub.EditProfile;
import com.imcooking.activity.Sub.FavoriteCusine;
import com.imcooking.activity.Sub.Help;
import com.imcooking.activity.Sub.ManageAddress;
import com.imcooking.activity.Sub.PaymentMethod;
import com.imcooking.activity.Sub.Setting;
import com.imcooking.activity.home.MainActivity;
import com.imcooking.activity.main.setup.LoginActivity;
import com.imcooking.activity.main.setup.SignUpActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
    }

    private TextView tv_manage_address, tv_chef_i_love, tv_payment_method, tv_favorite_cuisine, tv_setting, tv_help, tv_edit;
    private void init(){

        tv_manage_address = getView().findViewById(R.id.profile_edit_manage_address);
        tv_chef_i_love = getView().findViewById(R.id.profile_edit_chef_i_love);
        tv_payment_method = getView().findViewById(R.id.profile_edit_payment_method);
        tv_favorite_cuisine = getView().findViewById(R.id.profile_edit_favorite_cuisine);
        tv_setting = getView().findViewById(R.id.profile_edit_setting);
        tv_help = getView().findViewById(R.id.profile_edit_help);
        tv_edit = getView().findViewById(R.id.profile_edit);

        tv_manage_address.setOnClickListener(this);
        tv_chef_i_love.setOnClickListener(this);
        tv_payment_method.setOnClickListener(this);
        tv_favorite_cuisine.setOnClickListener(this);
        tv_setting.setOnClickListener(this);
        tv_help.setOnClickListener(this);
        tv_edit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if(v.getId() == R.id.profile_edit_manage_address){
            startActivity(new Intent(getContext(), ManageAddress.class));
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

        } else if(v.getId() == R.id.profile_edit_chef_i_love){
            startActivity(new Intent(getContext(), ChefILove.class));
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

        } else if(v.getId() == R.id.profile_edit_payment_method){
            startActivity(new Intent(getContext(), PaymentMethod.class));
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

        } else if(v.getId() == R.id.profile_edit_favorite_cuisine){
            startActivity(new Intent(getContext(), FavoriteCusine.class));
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

        } else if(v.getId() == R.id.profile_edit_setting){
            startActivity(new Intent(getContext(), Setting.class));
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

        } else if(v.getId() == R.id.profile_edit_help){
            startActivity(new Intent(getContext(), Help.class));
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

        } else if(v.getId() == R.id.profile_edit){
            startActivity(new Intent(getContext(), EditProfile.class));
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

        } else{

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity()).setBottomColor();
        ((MainActivity) getActivity()).tv_profile.setTextColor(getResources().getColor(R.color.theme_color));
        ((MainActivity) getActivity()).iv_profile.setImageResource(R.drawable.ic_user_name_1);

    }
}
