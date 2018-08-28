package com.imcooking.activity.Sub.Foodie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.ChefIloveData;
import com.imcooking.Model.api.response.CuisineData;
import com.imcooking.Model.api.response.ModelFoodieFavCuisines;
import com.imcooking.R;
import com.imcooking.adapters.AdapterFoodieFavCuisines;
import com.imcooking.adapters.FavrouiteAdatper;
import com.imcooking.fragment.foodie.HomeFragment;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FavoriteCusine extends AppBaseActivity {

    RecyclerView recyclerView;
    String foodie_id = HomeFragment.foodie_id+"";
    private List<ChefIloveData.ChefloveBean> chefloveBeanList= new ArrayList<>();

    private Gson gson = new Gson();
    private CuisineData cuisineData;
    private AdapterFoodieFavCuisines adapter;
    private ArrayList<String> arrayList = new ArrayList<>();
    public static ModelFoodieFavCuisines data;
    //

    private TinyDB tinyDB;
    private ApiResponse.UserDataBean userDataBean;
    private String user_id, user_type;
    private RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_cusine);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

//        find id/;
        recyclerView = findViewById(R.id.activity_chef_ilove_recycler);

        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);

        layout = findViewById(R.id.fav_cuisine_layout);


        tinyDB = new TinyDB(getApplicationContext());
        String s = tinyDB.getString("login_data");
        userDataBean = gson.fromJson(s, ApiResponse.UserDataBean.class);
        user_id = userDataBean.getUser_id()+"";
        user_type = userDataBean.getUser_type();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(user_type.equals("2")) {
            layout.setVisibility(View.VISIBLE);
            getFav_foodie();
        } else {
            layout.setVisibility(View.GONE);
            getFav_chef();
        }
    }

    private void getFav_foodie(){

        Log.d("Foodie_id", foodie_id);
        String s = "{\"foodie_id\":" + foodie_id + "}";
        try {
            JSONObject jsonObject = new JSONObject(s);

            new GetData(getApplicationContext(), FavoriteCusine.this).sendMyData(jsonObject,
                    "foodie_my_cuisine_list", FavoriteCusine.this, new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            data = new ModelFoodieFavCuisines();
                            data = new Gson().fromJson(result, ModelFoodieFavCuisines.class);

//                            if(data.isStatus()){
//                                setMyAdapter(data.getCuisine_data());
//                            } else{
                                try {
                                    JSONObject jsonObject1 = new JSONObject(result);
                                    if(jsonObject1.has("msg")) {
                                        if (jsonObject1.get("msg").equals("Not record found")) {
                                            recyclerView.setVisibility(View.GONE);
                                            BaseClass.showToast(getApplicationContext(), "Your Favorite Cuisine List is Empty.");
                                        } else {
                                        }
                                    } else if(jsonObject1.has("cuisine_data")){
                                        recyclerView.setVisibility(View.VISIBLE);
                                        setMyAdapter(data.getCuisine_data());
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
//                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getFav_chef(){

        String s = "{\"chef_id\":" + user_id + "}";
        try {
            final JSONObject jsonObject = new JSONObject(s);

            new GetData(getApplicationContext(), FavoriteCusine.this).sendMyData(jsonObject,
                    GetData.CHEF_MY_CUISINE_LIST, FavoriteCusine.this, new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            data = new ModelFoodieFavCuisines();
                            data = new Gson().fromJson(result, ModelFoodieFavCuisines.class);

                            if(data.isStatus()){
                                setMyAdapter(data.getCuisine_data());
                            } else {
                                try {

                                    BaseClass.showToast(getApplicationContext(), new JSONObject(result).getString("msg"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setMyAdapter(List<ModelFoodieFavCuisines.CuisineDataBean> cuisineData){

        arrayList.clear();
        for (int i=0; i<cuisineData.size(); i++){
            arrayList.add("1");
        }
        adapter = new AdapterFoodieFavCuisines(getApplicationContext(), cuisineData, arrayList);
        recyclerView.setAdapter(adapter);
    }

    public void add_your_favorite_cuisine(View view){

        startActivity(new Intent(FavoriteCusine.this, CuisineList.class));
    }

}
