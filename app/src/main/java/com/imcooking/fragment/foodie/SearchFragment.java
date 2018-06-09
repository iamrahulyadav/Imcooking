package com.imcooking.fragment.foodie;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.ChefAndDish;
import com.imcooking.R;
import com.imcooking.activity.Sub.Foodie.ChefProfile;
import com.imcooking.activity.home.HomeSearchActivity1;
import com.imcooking.adapters.AdapterChefSearch;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements AdapterChefSearch.SearchClickInterface{


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        init();
    }

    AdapterChefSearch adapter ;
    RecyclerView rvChef;
    EditText etSearchHome;
    private String token,cityname, languagecode, keyword;
    List<String> listChef;
    //    ImageView imgBack;
    TinyDB tinyDB;

    private void init(){
        tinyDB = new TinyDB(getContext());
        token  = tinyDB.getString("access_token") ;
        languagecode = tinyDB.getString("language_code");
//        setLanguages(languagecode);
        // actionBar=getSupportActionBar();
        // if(actionBar!=null) actionBar.hide();
//        imgBack=findViewById(R.id.activity_home_search_back);
        etSearchHome  = getView().findViewById(R.id.etSearch_home) ;
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

        rvChef = (RecyclerView) getView().findViewById(R.id.rvLocations) ;
        listChef =  new ArrayList<>();
        RecyclerView.LayoutManager layoutManager  =  new LinearLayoutManager(getContext());
        rvChef.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext()
                ,DividerItemDecoration.VERTICAL);
        rvChef.addItemDecoration(itemDecoration);

    }

    private ArrayList<String> arr_id = new ArrayList<>();
    private ArrayList<String> arr_name = new ArrayList<>();
    private ArrayList<String> arr_type = new ArrayList<>();

    private void getChefList(String keyword)
            throws JSONException {

        String s = "{  \"keyword\":" + keyword + "}" ;
        JSONObject jsonObject = new JSONObject(s);

        Log.d("getchef", jsonObject.toString());
        new GetData(getContext(),getActivity()).sendMyData(jsonObject,
                "search", getActivity(), new GetData.MyCallback() {
                    @Override
                    public void onSuccess(String result) {

                        ChefAndDish chefAndDish = new ChefAndDish();
                        chefAndDish = new Gson().fromJson(result, ChefAndDish.class);

                        arr_id.clear(); arr_name.clear(); arr_type.clear();
                        Log.d("getchef", "inside");
                        if(chefAndDish.isStatus()) {


                            if ( chefAndDish.getResponse().getDish_lst().isEmpty()){
                                BaseClass.showToast(getContext(), "No search found");

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

                                    Log.d("getchef_for", "\n" + String.valueOf(chefAndDish.getResponse().getUser_list().get(i)));
                                }

                                setMyAdapter();
                            }

                        }
                        else {
                            BaseClass.showToast(getContext(), "Something Went Wrong");
                        }

                    }
                });
    }

    private void setMyAdapter(){
        adapter = new AdapterChefSearch(getContext(),arr_id,
                arr_name, arr_type, this);
        rvChef.setAdapter(adapter);

    }

    @Override
    public void search_click(int position) {
        String type = arr_type.get(position);

        if(type.equals("Dish")){
            HomeDetails fragment = new HomeDetails();
            Bundle args = new Bundle();
            args.putString("dish_id", arr_id.get(position));
            fragment.setArguments(args);
            BaseClass.callFragment1(fragment, fragment.getClass().getName(), getFragmentManager());
        } else{
            startActivity(new Intent(getContext(), ChefProfile.class)
                            .putExtra("chef_id", arr_id.get(position))
                            .putExtra("foodie_id", HomeFragment.foodie_id));
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

        }


    }

}