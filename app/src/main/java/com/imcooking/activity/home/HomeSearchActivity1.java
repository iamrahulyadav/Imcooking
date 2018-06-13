package com.imcooking.activity.home;

import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
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

public class HomeSearchActivity1 extends AppCompatActivity implements AdapterChefSearch.SearchClickInterface{

    public static final String RESPONSE_DATA = "location" ;
    public static final int RESPONSE_LOCATION = 200;
    AdapterChefSearch adapter ;
    RecyclerView rvChef;
    EditText etSearchHome;
    private String token,cityname, languagecode, keyword;
    List<String> listChef;
//    ImageView imgBack;
    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        tinyDB = new TinyDB(getApplicationContext());
        token  = tinyDB.getString("access_token") ;
        languagecode = tinyDB.getString("language_code");
//        setLanguages(languagecode);
       // actionBar=getSupportActionBar();
       // if(actionBar!=null) actionBar.hide();
//        imgBack=findViewById(R.id.activity_home_search_back);
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
                } else{

                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        etSearchHome.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_DEL) {
                    if(etSearchHome.getText().toString().length() >2){
                        String keyword = etSearchHome.getText().toString().trim();
                        try {
                            getChefList(keyword);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else{
                        arr_id.clear(); arr_name.clear(); arr_type.clear();
                        adapter.notifyDataSetChanged();
                    }
                }
                return false;
            }
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

/*
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
*/
    }

    private ArrayList<String> arr_id = new ArrayList<>();
    private ArrayList<String> arr_name = new ArrayList<>();
    private ArrayList<String> arr_type = new ArrayList<>();

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

                        arr_id.clear(); arr_name.clear(); arr_type.clear();
                        if(chefAndDish.isStatus()) {
                            if ( chefAndDish.getResponse().getDish_lst().isEmpty()){
                                BaseClass.showToast(getApplicationContext(), "No Search Found");
                                adapter.notifyDataSetChanged();
                            }
                            else {
                                for (int i = 0; i < chefAndDish.getResponse().getDish_lst().size();i++){

                                    arr_id.add(chefAndDish.getResponse().getDish_lst().get(i).getDish_id() + "");
                                    arr_name.add(chefAndDish.getResponse().getDish_lst().get(i).getDish_name() + " \t (Dish Name)");
                                    arr_type.add("Dish");

                                    Log.d("getchef_for", "\n" + String.valueOf(chefAndDish.getResponse().getDish_lst().get(i)));
                                }
                                for (int i = 0; i < chefAndDish.getResponse().getUser_list().size();i++){

                                    arr_id.add(chefAndDish.getResponse().getUser_list().get(i).getUser_id() + "");
                                    arr_name.add(chefAndDish.getResponse().getUser_list().get(i).getUser_name() + " \t (Chef Name)");
                                    arr_type.add("User");
                                }
//                                setMyAdapter();
                            }
                            setMyAdapter();
                        }
                        else {
                            BaseClass.showToast(getApplicationContext(), "Something Went Wrong");
                        }
                    }
                });
    }

    private void setMyAdapter(){
        adapter = new AdapterChefSearch(getApplicationContext(),arr_id,
                arr_name, arr_type, this);
        rvChef.setAdapter(adapter);

    }

    @Override
    public void search_click(int position) {
        String type = arr_type.get(position);


    }
}
