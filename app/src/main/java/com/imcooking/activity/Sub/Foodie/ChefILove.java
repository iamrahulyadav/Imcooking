package com.imcooking.activity.Sub.Foodie;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_ilove);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

//        find id
        recyclerView = findViewById(R.id.activity_chef_ilove_recycler);

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
            new GetData(getApplicationContext()).sendMyData(jsonObject, "cheflove", ChefILove.this,
                    new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            chefIloveData = gson.fromJson(result, ChefIloveData.class);
                            chefloveBeanList.clear();
                            if (chefIloveData!=null && chefIloveData.getCheflove()!=null){

                                chefloveBeanList.addAll(chefIloveData.getCheflove());
                                setChefLove();
                            }   else {
                                BaseClass.showToast(getApplicationContext(), "You are not following any chef yet.");
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*
        new GetData(getApplicationContext(), ChefILove.this).getResponse(request, "cheflove", new GetData.MyCallback() {
            @Override
            public void onSuccess(final String result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chefIloveData = gson.fromJson(result, ChefIloveData.class);
                        if (chefIloveData!=null&&chefIloveData.getCheflove()!=null){
                            chefloveBeanList.addAll(chefIloveData.getCheflove());
                            Log.d("TAG", "onSuccess: "+result);
                            setChefLove();
                        }
                    }
                });
            }
        }
        );
*/
    }

    private void setChefLove(){
        chefILoveAdatper = new ChefILoveAdatper(getApplicationContext(), chefloveBeanList, this);
        recyclerView.setAdapter(chefILoveAdatper);
        Log.d("AdapterSize", chefloveBeanList.size() + "");
    }

    @Override
    public void click_me_chef_i_love(int position, String click_type) {

        if(click_type.equals("layout")){

            startActivityForResult(new Intent(getApplicationContext(), ChefProfile.class)
                            .putExtra("chef_id", "72"/*chefIloveData.getCheflove().get(position).getChef_id()*/)
                            .putExtra("foodie_id", foodie_id),
                    ChefProfile.CHEF_PROFILE_CODE);
            overridePendingTransition(R.anim.enter, R.anim.exit);

        } else {
            remove_chef(position);
        }
    }

    private void remove_chef(final int position){

        try {
            String s = "{\n" +
                    "  \"chef_id\":" + "72" + ",\n" +
                    "  \"foodie_id\":" + foodie_id + "}";
            JSONObject jsonObject = new JSONObject(s);

            new GetData(getApplicationContext(), ChefILove.this).sendMyData(jsonObject, "follow",
                    ChefILove.this, new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            FollowUnfollow followUnfollow = new Gson().fromJson(result, FollowUnfollow.class);
                            if(followUnfollow.isStatus()){
                                if (followUnfollow.getMsg().equals("Successfully follow")){
                                   BaseClass.showToast(getApplicationContext(), "Something Went Wrong");
                                }
                                else if (followUnfollow.getMsg().equals("Successfully unfollow")){
                                    BaseClass.showToast(getApplicationContext(),
                                            "Successfully Unfollowed");
                                    chefloveBeanList.remove(position);
                                    chefILoveAdatper.notifyDataSetChanged();
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