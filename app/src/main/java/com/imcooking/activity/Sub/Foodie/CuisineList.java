package com.imcooking.activity.Sub.Foodie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.CuisineData;
import com.imcooking.R;
import com.imcooking.activity.Sub.Chef.ChefEditDish;
import com.imcooking.adapters.AdapterCuisineList;
import com.imcooking.fragment.foodie.HomeFragment;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CuisineList extends AppBaseActivity implements AdapterCuisineList.click_adapter_cuisine_list {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisine_list);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        init();
        getMyCuisines();

    }

    private RecyclerView rv;
    private ArrayList<String> arrayList = new ArrayList<>(); // (Arrays.asList("0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0"));
    private AdapterCuisineList adapter;

    private CuisineData cuisineData = new CuisineData();
    private List<CuisineData.CuisineDataBean> cuisionList=new ArrayList<>();

    private String foodie_id = HomeFragment.foodie_id + "";

    private void init(){

        rv = findViewById(R.id.cuisine_list_recycler);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(CuisineList.this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(horizontalLayoutManagaer);

    }

    private void getMyCuisines() {

        try {
            String s = "";
            JSONObject jsonObject = new JSONObject("{}");

            rv.setVisibility(View.GONE);
            new GetData(getApplicationContext(), CuisineList.this).sendMyData(jsonObject, "cuisine",
                    CuisineList.this, new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            rv.setVisibility(View.VISIBLE);
                            cuisineData = new Gson().fromJson(result, CuisineData.class);
//                            cuisineList.addAll(cuisineData.getCuisine_data());

                            setMyAdapter(cuisineData);
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setMyAdapter(CuisineData cuisineData){

        arrayList.clear();
        for (int i=0; i<cuisineData.getCuisine_data().size(); i++){
            arrayList.add("0");
        }

        markMyFav(cuisineData);
/*
        arrayList.set(3, "1");
        arrayList.set(8, "1");
        arrayList.set(11, "1");
*/

        adapter = new AdapterCuisineList(getApplicationContext(), cuisineData, arrayList, this);
        rv.setAdapter(adapter);
    }

    private void markMyFav(CuisineData cuisineData){

        if(FavoriteCusine.data!=null && FavoriteCusine.data.getCuisine_data() != null) {
            for (int i = 0; i < cuisineData.getCuisine_data().size(); i++) {
                for (int j = 0; j < FavoriteCusine.data.getCuisine_data().size(); j++) {
                    if (FavoriteCusine.data.getCuisine_data().get(j).getCuisine_name()
                            .equals(cuisineData.getCuisine_data().get(i).getCuisine_name())) {
                        arrayList.set(i, "1");
                    }
                }
            }
        }
    }

    @Override
    public void click_adapter_cuisine_list_m(int position) {

        callApi(cuisineData.getCuisine_data().get(position).getCuisine_id(),
                cuisineData.getCuisine_data().get(position).getCuisine_name(), position);
//        BaseClass.showToast(getApplicationContext(), position + "");
/*
        if(arrayList.get(position).equals("1")) {
            arrayList.set(position, "0");
        } else {
            arrayList.set(position, "1");
        }
        adapter.notifyDataSetChanged();
*/

    }

    private void callApi(int cuisine_id, final String cuisine_name, final int position){

        Log.d("foodie_id", foodie_id);
        String s = "{\"foodie_id\":" + foodie_id + ", \"cuisine_id\":" + cuisine_id + "}" ;
        try {
            JSONObject jsonObject = new JSONObject(s);

            new GetData(getApplicationContext(), CuisineList.this).sendMyData(jsonObject
                    , "addfavourite", CuisineList.this, new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {

                            try {
                                JSONObject job = new JSONObject(result);
                                if(job.getBoolean("status")){
                                    if(job.getString("msg").equals("Add favourite Successfully")){
                                        BaseClass.showToast(getApplicationContext(),
                                                cuisine_name + " has been added to your favourite cuisine list");
                                        arrayList.set(position, "1");
                                        adapter.notifyDataSetChanged();
                                        finish();
                                    } else {}
                                }else {
                                    if (job.getString("msg").equals(" Dislike cuisine!")) {
                                        BaseClass.showToast(getApplicationContext(),
                                                cuisine_name + " has been removed from your favourite cuisine list");
                                        arrayList.set(position, "0");
                                        adapter.notifyDataSetChanged();
                                        finish();
                                    } else {
                                        BaseClass.showToast(getApplicationContext(), "Something Went Wrong.");
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
