package com.imcooking.activity.home;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.ChefAndDish;
import com.imcooking.R;
import com.imcooking.adapters.AdapterChefSearch;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeSearchActivity1 extends AppCompatActivity {
    public static final String RESPONSE_DATA = "location" ;
    public static final int RESPONSE_LOCATION = 200;
    AdapterChefSearch adapter ;
    RecyclerView rvChef;
    EditText etSearchHome;
    private String token,cityname, languagecode, keyword;
    List<String> listChef;
    ImageView imgBack;
    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search);

        tinyDB = new TinyDB(getApplicationContext());
        token  = tinyDB.getString("access_token") ;
        languagecode = tinyDB.getString("language_code");
//        setLanguages(languagecode);
       // actionBar=getSupportActionBar();
       // if(actionBar!=null) actionBar.hide();
        imgBack=findViewById(R.id.activity_home_search_back);
        etSearchHome  =  findViewById(R.id.etSearch_home) ;
        etSearchHome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                keyword  = charSequence.toString().trim() ;
                if (keyword.length()>2){
                    try {
                        getChefList(keyword);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

       /* etSearchLocation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                clickonDrawable(v, event);
                return false;
            }
        });*/
       // imgBack = findViewById(R.id.fragment_home_search_linearlayout);

        rvChef = (RecyclerView) findViewById(R.id.rvLocations) ;
        listChef =  new ArrayList<>();
        RecyclerView.LayoutManager layoutManager  =  new LinearLayoutManager(this);
        rvChef.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rvChef.addItemDecoration(itemDecoration);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
    }

    private void getChefList(String keyword)
            throws JSONException {

        String s = "{  \"keyword\":" + keyword + "}" ;
        JSONObject jsonObject = new JSONObject(s);

       Log.d("getchef", jsonObject.toString());
        new GetData(getApplicationContext(),HomeSearchActivity1.this).sendMyData(jsonObject,
                "search", HomeSearchActivity1.this, new GetData.MyCallback() {
                    @Override
                    public void onSuccess(String result) {

                        ChefAndDish chefAndDish = new ChefAndDish();
                        chefAndDish = new Gson().fromJson(result, ChefAndDish.class);

                        Log.d("getchef", "inside");
                        if(chefAndDish.isStatus()) {

                            ArrayList<String> arr_id = new ArrayList<>();
                            ArrayList<String> arr_name = new ArrayList<>();
                            ArrayList<String> arr_type = new ArrayList<>();
                            if ( chefAndDish.getResponse().getDish_lst().isEmpty()){
                                BaseClass.showToast(getApplicationContext(), "No search found");

                            }
                            else {
                                for (int i = 0; i < chefAndDish.getResponse().getDish_lst().size();i++){

                                    arr_id.add(chefAndDish.getResponse().getDish_lst().get(i).getDish_id() + "");
                                    arr_name.add(chefAndDish.getResponse().getDish_lst().get(i).getDish_name() + "");
                                    arr_type.add("Dish");

                                    Log.d("getchef_for", "\n" + String.valueOf(chefAndDish.getResponse().getDish_lst().get(i)));
                                }
                                for (int i = 0; i < chefAndDish.getResponse().getUser_list().size();i++){

                                    arr_id.add(chefAndDish.getResponse().getUser_list().get(i).getUser_id() + "");
                                    arr_name.add(chefAndDish.getResponse().getUser_list().get(i).getUser_name());
                                    arr_type.add("User");

                                    Log.d("getchef_for", "\n" + String.valueOf(chefAndDish.getResponse().getUser_list().get(i)));
                                }

                                adapter = new AdapterChefSearch(getApplicationContext(),arr_id,arr_name,arr_type);

                                rvChef.setAdapter(adapter);}

                        }
                        else {
                            BaseClass.showToast(getApplicationContext(), "Something Went Wrong");
                        }

                    }
                });






    }


    boolean clickonDrawable(View v, MotionEvent event)
    {
        final int DRAWABLE_LEFT = 0;
        final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        final int DRAWABLE_BOTTOM = 3;
        if(event.getAction() == MotionEvent.ACTION_UP) {
            if(event.getRawX() >= (etSearchHome.getRight() -
                    etSearchHome.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                try {
                    getChefList(keyword);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }



/*    private void refreshList(List<String> filterList) {
        filterList = new ArrayList<>();

        adapter = new AdapterChefSearch(filterList, new AdapterChefSearch.OnItemClickListener() {
            @Override
            public void OnItemClick(Location location) {
                Intent intent  =  new Intent();
                intent.putExtra(RESPONSE_DATA,location);
                setResult(RESPONSE_LOCATION,intent);
                finish();
            }
        });
        rvChef.setAdapter(adapter);
    }*/

  /*  @Override
    protected void onResume() {
        super.onResume();
        actionBar.setTitle(getResources().getString(R.string.select_loc));
    }*/
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }*/

  /*  @Override
    public void refreshTokenCallBack() {
        token = tinyDB.getString("access_token");
        getLocationList(keyword,languagecode);
    }*/

}
