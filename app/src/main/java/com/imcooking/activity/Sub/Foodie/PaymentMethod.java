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

public class PaymentMethod extends AppCompatActivity implements View.OnClickListener, AdatperSavedCard.AdapterSaveCardInterface{
    private RecyclerView cardRecyclerView;
    private TextView txtAddCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        BaseClass.setLightStatusBar(getWindow().getDecorView(),PaymentMethod.this);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    private AdatperSavedCard adapter;

    private void setMyAdapter(List<SavedCardData.PaymentDetailsListBean> paymentDetailsListBeans){
       adapter = new AdatperSavedCard(getApplicationContext(),paymentDetailsListBeans, this);
        cardRecyclerView.setAdapter(adapter);
    }

    private List<SavedCardData.PaymentDetailsListBean>paymentDetailsListBeans ;

    private void getSavedcardList(){
        paymentDetailsListBeans = new ArrayList<>();
        String foodie_id =HomeFragment.foodie_id;
        String s ="{\"foodies_id\":\""+foodie_id+"\"\n" +
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
                startActivity(new Intent(PaymentMethod.this, AddCardActivity.class).putExtra("key","add"));
                break;
        }
    }

    @Override
    public void clickCard(String edit, int pos) {
        SavedCardData.PaymentDetailsListBean paymentDetailsListBean = paymentDetailsListBeans.get(pos);
        if (edit.equals("edit")){
            startActivity(new Intent(PaymentMethod.this, AddCardActivity.class)
                    .putExtra("key","edit").putExtra("details", new Gson().toJson(paymentDetailsListBean)));
        } else if (edit.equals("delete")){
            deleteCardDetail(paymentDetailsListBean.getPayment_details_id()+"");
            paymentDetailsListBeans.remove(pos);
        }
    }

    private void deleteCardDetail(String cardid){

        String request = "{\"payment_details_id\":\""+cardid+"\"\n" +
                "\n" +
                " }";

        Log.d("TAG", "saveCard: "+request);
        new GetData(getApplicationContext(), PaymentMethod.this).getResponse(request, GetData.DELETE_PAYMENT_DETAILS, new GetData.MyCallback() {
            @Override
            public void onSuccess(final String result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            boolean status = jsonObject.getBoolean("status");
                            String msg = jsonObject.getString("msg");
                            if (status){
                                if (msg.equals("Delete successfully")){
                                    BaseClass.showToast(getApplicationContext(),"Card deleted Successfully");
                                }
                                adapter.notifyDataSetChanged();
                            } else { }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }


}
