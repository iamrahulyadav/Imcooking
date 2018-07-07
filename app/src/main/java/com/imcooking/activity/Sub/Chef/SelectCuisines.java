package com.imcooking.activity.Sub.Chef;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.imcooking.Model.api.response.CuisineData;
import com.imcooking.R;
import com.imcooking.adapters.AdapterSelectCuisine;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.utils.BaseClass;

import java.util.ArrayList;

public class SelectCuisines extends AppBaseActivity implements AdapterSelectCuisine.Interface_select_cuisine {

    ArrayList<String> arr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_cuisines);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        arr.clear();
        arr = getIntent().getExtras().getStringArrayList("cuisine_list");

        init();

    }

    private RecyclerView rv;
    private ArrayList<String> arrayList_name = new ArrayList<>();
    private ArrayList<String> arrayList_id = new ArrayList<>();
    private ArrayList<String> arrayList_status = new ArrayList<>();

    private void init(){

        rv = findViewById(R.id.chef_profile_recycler_select_cuisine);
        LinearLayoutManager layoutManagaer
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManagaer);

        setMyAdapter(ChefEditProfile.cuisineData);

    }

    private AdapterSelectCuisine adapter;

    private void setMyAdapter(CuisineData cuisines){

        arrayList_name.clear(); arrayList_status.clear(); arrayList_id.clear();

        for(int i=0; i<cuisines.getCuisine_data().size(); i++){
            arrayList_name.add(cuisines.getCuisine_data().get(i).getCuisine_name());
            arrayList_id.add(cuisines.getCuisine_data().get(i).getCuisine_id() + "");
            arrayList_status.add("0");
        }

        for (int i=0; i<arr.size(); i++){
            for(int j=0; j<arrayList_name.size(); j++) {
                if (arr.get(i).equals(arrayList_name.get(j))) {
                    arrayList_status.set(j, "1");
                }
            }
        }

        adapter = new AdapterSelectCuisine(arrayList_name, arrayList_status, getApplicationContext(), this);
        rv.setAdapter(adapter);
    }

    public void select_cuisine_done(View view){
        String str_name = "", str_id = "";
        for(int i=0; i< arrayList_status.size(); i++){
            if(arrayList_status.get(i).equals("1")){
                if(str_name.length() == 0) {
                    str_name = str_name + arrayList_name.get(i);
                    str_id = str_id + arrayList_id.get(i);
                } else{
                    str_name = str_name + "," + arrayList_name.get(i);
                    str_id = str_id + "," + arrayList_id.get(i);
                }
            }
        }

        Intent intent = new Intent();
        intent.putExtra("cuisineName", str_name);
        intent.putExtra("cuisineId", str_id);
        setResult(143, intent);
        finish();
    }

    @Override
    public void method_select_cuisine(int position) {
        if(position>=0) {
            if (arrayList_status.get(position).equals("0")) {
                arrayList_status.set(position, "1");
            } else {
                arrayList_status.set(position, "0");
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

//        BaseClass.showToast(getApplicationContext(), "No Filters Applied!");
    }
}