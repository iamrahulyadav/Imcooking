package com.imcooking.activity.Sub.Foodie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.ChefIloveData;
import com.imcooking.Model.api.response.CuisineData;
import com.imcooking.Model.api.response.ModelFoodieFavCuisines;
import com.imcooking.R;
import com.imcooking.adapters.AdapterCuisineList;
import com.imcooking.adapters.AdapterFoodieFavCuisines;
import com.imcooking.adapters.FavrouiteAdatper;
import com.imcooking.fragment.foodie.HomeFragment;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.webservices.GetData;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_cusine);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

//        find id
        recyclerView = findViewById(R.id.activity_chef_ilove_recycler);

        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);

    }

    @Override
    protected void onResume() {
        super.onResume();

        getFav();

    }

    private void getFav(){

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

                            if(data.isStatus()){

                                setMyAdapter(data.getCuisine_data());

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
