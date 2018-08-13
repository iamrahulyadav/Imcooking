package com.imcooking.activity.Sub.Foodie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.CuisineData;
import com.imcooking.Model.api.response.SavedCardData;
import com.imcooking.R;
import com.imcooking.adapters.AdapterCuisineList;
import com.imcooking.adapters.AdatperSavedCard;
import com.imcooking.fragment.foodie.HomeFragment;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PaymentMethod extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView cardRecyclerView;
    private TextView txtAddCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        BaseClass.setLightStatusBar(getWindow().getDecorView(),PaymentMethod.this);

        init();
        getSavedcardList();

    }


    private void init(){
        cardRecyclerView = findViewById(R.id.activity_pay_method_list);
        txtAddCard = findViewById(R.id.activity_payment_method_txtAddCard);

        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(PaymentMethod.this, LinearLayoutManager.VERTICAL, false);
        cardRecyclerView.setLayoutManager(horizontalLayoutManagaer);

        txtAddCard.setOnClickListener(this);
    }



    private void setMyAdapter(List<SavedCardData.PaymentDetailsListBean> paymentDetailsListBeans){

        AdatperSavedCard adapter = new AdatperSavedCard(getApplicationContext(),paymentDetailsListBeans);
        cardRecyclerView.setAdapter(adapter);
    }

    private List<SavedCardData.PaymentDetailsListBean>paymentDetailsListBeans ;
    private void getSavedcardList(){
        paymentDetailsListBeans = new ArrayList<>();
        String foodie_id =HomeFragment.foodie_id;
        String s ="{\"foodies_id\":\""+foodie_id+"\"\n" +
                "\n" +
                " }";
        try {
            JSONObject jsonObject = new JSONObject(s);

            new GetData(getApplicationContext(), PaymentMethod.this).sendMyData(jsonObject
                    , GetData.PAYMENT_DETAILS_LIST, PaymentMethod.this, new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            if (result!=null){
                                SavedCardData savedCardData = new Gson().fromJson(result, SavedCardData.class);
                                if (savedCardData.isStatus()){
                                    if (paymentDetailsListBeans!=null)
                                        paymentDetailsListBeans.clear();
                                    paymentDetailsListBeans = savedCardData.getPayment_details_list();
                                    setMyAdapter(paymentDetailsListBeans);
                                }
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_payment_method_txtAddCard:
                startActivity(new Intent(PaymentMethod.this, AddCardActivity.class));
                break;
        }
    }
}
