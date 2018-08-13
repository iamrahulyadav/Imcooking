package com.imcooking.activity.Sub.Foodie;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imcooking.Model.ApiRequest.AddressRequest;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.R;
import com.imcooking.fragment.foodie.HomeFragment;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;

import static java.security.AccessController.getContext;

public class AddCardActivity extends AppBaseActivity {
    private EditText edtCardHolder, edtCardNo, edtCVV, edtCardType;
    private Spinner yyyySpinner, mmSpinner;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorBlack));
        }

        init();
        expirySpinner();
    }


    private void init(){
        edtCardHolder = findViewById(R.id.payment_card_cardholder_name);
        edtCardNo = findViewById(R.id.payment_card_number);
        edtCVV = findViewById(R.id.payment_card_cvv);
        yyyySpinner = findViewById(R.id.payment_card_spinner_yyyy);
        mmSpinner = findViewById(R.id.payment_card_spinner_mm);
        edtCardType = findViewById(R.id.payment_card_type);

        edtCardNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (count <= edtCardNo.getText().toString().length()
                        && (edtCardNo.getText().toString().length()==4
                        || edtCardNo.getText().toString().length()==9
                        || edtCardNo.getText().toString().length()==14)){
                    edtCardNo.setText(edtCardNo.getText().toString()+"-");
                    int pos = edtCardNo.getText().length();
                    edtCardNo.setSelection(pos);
                }
                else if (count >= edtCardNo.getText().toString().length()
                        &&(edtCardNo.getText().toString().length()==4
                        ||edtCardNo.getText().toString().length()==9
                        ||edtCardNo.getText().toString().length()==14)) {
                    edtCardNo.setText(edtCardNo.getText().toString().substring(0, edtCardNo.getText().toString().length() - 1));
                    int pos = edtCardNo.getText().length();
                    edtCardNo.setSelection(pos);
                }
                count = edtCardNo.getText().toString().length();
            }
        });

    }


    private void expirySpinner(){
        String[] mm = new String[]{"Expiry MM","01", "02", "03", "04", "05" , "06" , "07" , "08","09","10","11","12"};
        final ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, R.layout.spinner_item2, mm);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);


        String[] yyyy = new String[]{"Expiry YYYY","2018", "2019", "2020", "2021", "2022","2023","2024","2025","2026"};
        final ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, R.layout.spinner_item2, yyyy);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mmSpinner.setAdapter(arrayAdapter1);
        yyyySpinner.setAdapter(arrayAdapter2);

        mmSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_month = arrayAdapter1.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        yyyySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_year = arrayAdapter2.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void saveCardDetail(String cardtype, String cardno, String name){
        cardno = cardno.replace("-","");
        String request = "{\"foodies_id\":\""+ HomeFragment.foodie_id+"\",\n" +
                " \"card_type\":\""+cardtype+"\",\n" +
                " \"card_number\":\""+cardno+"\",\n" +
                " \"expiray_date\":\""+str_month+"/"+str_year+"\",\n" +
                " \"name_of_card\":\""+name+"\"\n" +
                " }";


        Log.d("TAG", "saveCard: "+request);
        new GetData(getApplicationContext(), AddCardActivity.this).getResponse(request, GetData.ADDRESS, new GetData.MyCallback() {
            @Override
            public void onSuccess(final String result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });
    }

    String cardNo, cardName, cardType, cvv, str_month, str_year;
    public void saveCard(View view){
        cardNo = edtCardNo.getText().toString().trim();
        cardType = edtCardType.getText().toString().trim();
        cvv = edtCVV.getText().toString().trim();
        cardName = edtCardHolder.getText().toString().trim();
        if (cardNo.length() < 19) {
            edtCardNo.requestFocus();
            edtCardNo.setError("Invalid card number");
        }
        else if (cvv.length() < 3) {
            edtCVV.requestFocus();
            edtCVV.setError("Invalid cvv");
        }
        else if (str_month.equals("Expiry MM") || str_month.equals("")){
            Toast.makeText(getApplicationContext(), "Select Expiry month", Toast.LENGTH_SHORT).show();
        }
        else if (str_year.equals("Expiry YYYY") || str_year.equals("")){
            Toast.makeText(getApplicationContext(), "Select Expiry year", Toast.LENGTH_SHORT).show();
        } else if (cardType.isEmpty()){
            edtCardType.requestFocus();
            edtCardType.setError("Enter card type");
        }
        else {
            saveCardDetail(cardType,cardNo,cardName);
        }
    }

    public void cancel(View view){
        finish();
    }


}
