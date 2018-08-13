package com.imcooking.activity.Sub.Foodie;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.ChefIloveData;
import com.imcooking.Model.api.response.FollowUnfollow;
import com.imcooking.R;
import com.imcooking.adapters.ChefILoveAdatper;
import com.imcooking.fragment.foodie.HomeFragment;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChefILove extends AppBaseActivity implements ChefILoveAdatper.interface_chef_i_love {

    RecyclerView recyclerView;
    String foodie_id = HomeFragment.foodie_id+"";
    private List<ChefIloveData.ChefloveBean>chefloveBeanList= new ArrayList<>();
    private Gson gson = new Gson();
    private ChefILoveAdatper chefILoveAdatper;
    private LinearLayout layoutNoRecord, layout_top;
    private TextView  title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_ilove);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

//        find id
        title = findViewById(R.id.chef_i_love_title);
        layout_top = findViewById(R.id.chef_i_love_layout_top);
        layoutNoRecord = findViewById(R.id.activity_chef_ilove_no_record_image);
        recyclerView = findViewById(R.id.activity_chef_ilove_recycler);

        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        if(bundle != null){
//            bundle = i.getExtras();
            layout_top.setVisibility(View.VISIBLE);
            title.setText("Request a dish");
        } else{
            layout_top.setVisibility(View.GONE);
            title.setText("Chef i love");
        }

        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);

        getChefLove(foodie_id);

    }

    @Override
    protected void onResume() {
        super.onResume();
//        setChefLove();
    }

    ChefIloveData chefIloveData = new ChefIloveData();

    private void getChefLove(String foodie_id){

        String request = "{\"foodie_id\":" + foodie_id + "}";
        try {
            JSONObject jsonObject = new JSONObject(request);
            new GetData(getApplicationContext()).sendMyData(jsonObject, GetData.CHEF_I_LOVE, ChefILove.this,
                    new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            chefIloveData = gson.fromJson(result, ChefIloveData.class);
                            chefloveBeanList.clear();
                            if (chefIloveData!=null && chefIloveData.getCheflove()!=null && chefIloveData.getCheflove().size()>0){

                                chefloveBeanList.addAll(chefIloveData.getCheflove());
                                recyclerView.setVisibility(View.VISIBLE);
                                layoutNoRecord.setVisibility(View.GONE);
                                setChefLove();
                            }   else {
                                recyclerView.setVisibility(View.GONE);
                                layoutNoRecord.setVisibility(View.VISIBLE);
//                                BaseClass.showToast(getApplicationContext(), "You are not following any chef yet.");
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setChefLove(){
        chefILoveAdatper = new ChefILoveAdatper(getApplicationContext(), chefloveBeanList, this);
        recyclerView.setAdapter(chefILoveAdatper);
        Log.d("AdapterSize", chefloveBeanList.size() + "");
    }

    @Override
    public void click_me_chef_i_love(int position, String click_type) {
        String chef_id =  chefIloveData.getCheflove().get(position).getChef_id()+"";
        if(click_type.equals("layout")){

            startActivityForResult(new Intent(getApplicationContext(), ChefProfile.class)
                            .putExtra("chef_id",chef_id)
                            .putExtra("foodie_id", foodie_id),
                    ChefProfile.CHEF_PROFILE_CODE);
            overridePendingTransition(R.anim.enter, R.anim.exit);
        } else {
            remove_chef(position, chef_id);
        }
    }

    private void remove_chef(final int position, String chef_id){

        try {
            String s = "{\n" +
                    "\"chef_id\":" + chef_id + ",\n" +
                    "\"foodie_id\":" + foodie_id + "}";

            JSONObject jsonObject = new JSONObject(s);

            new GetData(getApplicationContext(), ChefILove.this).sendMyData(jsonObject, "follow",
                    ChefILove.this, new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            FollowUnfollow followUnfollow = new Gson().fromJson(result, FollowUnfollow.class);
                            if(followUnfollow.isStatus()){
                                if (followUnfollow.getMsg().equals("Successfully follow")){
                                   BaseClass.showToast(getApplicationContext(), "Successfully unfollow");
                                }
                                else if (followUnfollow.getMsg().equals("Successfully unfollow")){
                                    BaseClass.showToast(getApplicationContext(),
                                            "Successfully Unfollowed");
                                    chefloveBeanList.remove(position);
                                    chefILoveAdatper.notifyDataSetChanged();

                                    if(chefloveBeanList.size() == 0){
                                        recyclerView.setVisibility(View.GONE);
                                        layoutNoRecord.setVisibility(View.VISIBLE);
                                    } else{
                                        recyclerView.setVisibility(View.VISIBLE);
                                        layoutNoRecord.setVisibility(View.GONE);
                                    }
                                } else {
                                    BaseClass.showToast(getApplicationContext(),
                                            "Something Went Wrong");
                                }
                            }else {
                                Toast.makeText(getApplicationContext(), "Something went Wrong",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}