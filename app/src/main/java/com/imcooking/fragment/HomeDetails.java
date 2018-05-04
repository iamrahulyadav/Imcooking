package com.imcooking.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.DishDetails;
import com.imcooking.R;
import com.imcooking.activity.Sub.ChefProfile;
import com.imcooking.webservices.GetData;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeDetails extends Fragment implements View.OnClickListener {


    public HomeDetails() {
        // Required empty public constructor
    }

    String id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = getArguments().getString("dish_id");

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

    private ImageView iv_share, imgTop;
    TextView txtDishName, txtChefName, txtDistance, txtPrice, txtDeliverytype, txtAvailable,txtTime ;
    private LinearLayout chef_profile, layout;
    private String TAG  = HomeDetails.class.getName();

    private void init(){
        iv_share = getView().findViewById(R.id.home_details_share);
        imgTop = getView().findViewById(R.id.fragment_home_details_img_top);
        txtDishName = getView().findViewById(R.id.fragment_home_details_txtDishName);
        txtChefName = getView().findViewById(R.id.fragment_home_details_txtChefName);
        txtDistance = getView().findViewById(R.id.fragment_home_details_txtDistance);
        txtPrice = getView().findViewById(R.id.fragment_home_details_txtdishPrice);
        txtDeliverytype = getView().findViewById(R.id.fragment_home_details_txtDeliveryType);
        txtDistance = getView().findViewById(R.id.fragment_home_details_txtDistance);
        txtAvailable = getView().findViewById(R.id.fragment_home_details_txtAvailable);
        txtTime = getView().findViewById(R.id.fragment_home_details_txtTime);
        iv_share.setOnClickListener(this);

        chef_profile = getView().findViewById(R.id.home_details_chef_profile);
        chef_profile.setOnClickListener(this);

        layout = getView().findViewById(R.id.home_details_layout);
    }

    @Override
    public void onResume() {
        super.onResume();

//        ((MainActivity) getActivity()).toolbar.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        getDetails();

    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow(); // in Activity's onCreate() for instance
            w.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    Gson gson = new Gson();
    ApiResponse apiResponse = new ApiResponse();
    DishDetails dishDetails = new DishDetails();
    private void getDetails(){
        layout.setVisibility(View.GONE);
        String detailRequest = "{\"dish_id\":" + id + "}";
        Log.d("MyRequest", detailRequest);
        new GetData(getContext(), getActivity()).getResponse(detailRequest, "dishdetails", new GetData.MyCallback() {
            @Override
            public void onSuccess(String result) {

                String s  = result;
                Log.d(TAG, "onSuccess: data"+result);
                apiResponse.dish_details = gson.fromJson(result, DishDetails.class);
                dishDetails = apiResponse.dish_details;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        layout.setVisibility(View.VISIBLE);

                        txtDishName.setText(dishDetails.getDish_details().getDish_name());
                        txtChefName.setText(dishDetails.getDish_details().getChef_name());
                        txtPrice.setText("$"+dishDetails.getDish_details().getDish_price());
                        if (dishDetails.getDish_details().getDish_available().equalsIgnoreCase("yes")){
                            txtAvailable.setText("Available ");
                            txtAvailable.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_circle, 0);
                        } else {
                            txtAvailable.setText("No");
                        }
                        txtTime.setText(dishDetails.getDish_details().getDish_from()+"-"+dishDetails.getDish_details().getDish_to());
                        if (dishDetails.getDish_details().getDish_homedelivery().equalsIgnoreCase("No")){
                            txtDeliverytype.setText("Pickup");
                        } else if (dishDetails.getDish_details().getDish_homedelivery().equalsIgnoreCase("YES")
                                && dishDetails.getDish_details().getDish_pickup().equalsIgnoreCase("YES")){
                            txtDeliverytype.setText("Home Delivery / Pickup");
                        } else {
                            txtDeliverytype.setText("Home Delivery");
                        }
                        txtDistance.setText(dishDetails.getDish_details().getDish_deliverymiles()+" miles");
                        Picasso.with(getContext()).load(GetData.IMG_BASE_URL+dishDetails
                                .getDish_details().getDish_image().get(0))
//                                .placeholder( R.drawable.progress_animation )
                                .into(imgTop);
                    }
                });

                }

        });

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
