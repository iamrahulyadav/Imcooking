package com.imcooking.activity.Sub.Foodie;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.ChefIloveData;
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

public class ChefILove extends AppBaseActivity {
    RecyclerView recyclerView;
    String foodie_id = HomeFragment.foodie_id+"";
    private List<ChefIloveData.ChefloveBean>chefloveBeanList= new ArrayList<>();
    private Gson gson = new Gson();

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        getChefLove(foodie_id);
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
         ChefILoveAdatper chefILoveAdatper = new ChefILoveAdatper(getApplicationContext(), chefloveBeanList);
        recyclerView.setAdapter(chefILoveAdatper);
        Log.d("VK", chefloveBeanList.size() + "");
    }

}
