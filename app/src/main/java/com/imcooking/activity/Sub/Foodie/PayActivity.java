package com.imcooking.activity.Sub.Foodie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.imcooking.MainActivity1;
import com.imcooking.R;

import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.Transaction;
import co.paystack.android.model.Card;
import co.paystack.android.model.Charge;

public class PayActivity extends AppCompatActivity {
    //variables
    private Card card;
    private Charge charge;
    private String email, cardNumber, cvv;
    private int expiryMonth, expiryYear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //init paystack sdk
        PaystackSdk.initialize(getApplicationContext());
        setContentView(R.layout.activity_pay);
        //init view



        Button payBtn = (Button) findViewById(R.id.pay_button);
       /* emailField = (EditText) findViewById(R.id.edit_email_address);
        cardNumberField = (EditText) findViewById(R.id.edit_card_number);
        expiryMonthField = (EditText) findViewById(R.id.edit_expiry_month);
        expiryYearField = (EditText) findViewById(R.id.edit_expiry_year);
        cvvField = (EditText) findViewById(R.id.edit_cvv);*/
        init();

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  if (!validateForm()) {
                    return;
                }*/

                cardNo = edtCardNo.getText().toString().trim();
                cvv = edtCVV.getText().toString().trim();

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
                } else {
                    try {
                        email = "rakhi.askonline@gmail.com";
                        cardNumber = edtCardNo.getText().toString().trim();
                   /* expiryMonth = Integer.parseInt(expiryMonthField.getText().toString().trim());
                    expiryYear = Integer.parseInt(expiryYearField.getText().toString().trim());*/
                        cvv = edtCVV.getText().toString().trim();

                        String cardNumber = "4084084084084081";
                        int expiryMonth = 11; //any month in the future
                        int expiryYear = 18; // any year in the future
                        String cvv = "408";
                        card = new Card(cardNumber, expiryMonth, expiryYear, cvv);

                        if (card.isValid()) {

                            performCharge();
                        } else {
                            Toast.makeText(PayActivity.this, "Card not Valid", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private EditText edtCardNo, edtCVV;
    private Spinner yyyySpinner, mmSpinner;
    private int count;

    private void init(){
        edtCardNo = findViewById(R.id.payment_card_number);
        edtCVV = findViewById(R.id.payment_card_cvv);
        yyyySpinner = findViewById(R.id.payment_card_spinner_yyyy);
        mmSpinner = findViewById(R.id.payment_card_spinner_mm);


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
        expirySpinner();


    }
    private String cardNo, cardName, cardType,  str_month, str_year;


    private void expirySpinner(){
        String[] mm = new String[]{"Expiry MM","01", "02", "03", "04", "05" , "06" , "07" , "08","09","10","11","12"};
        final ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, R.layout.spinner_item2, mm);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);


        String[] yyyy = new String[]{"Expiry YYYY","2018", "2019", "2020", "2021", "2022","2023","2024","2025","2026"};
        final ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, R.layout.spinner_item2, yyyy);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mmSpinner.setAdapter(arrayAdapter1);
        yyyySpinner.setAdapter(arrayAdapter2);

        if (str_month!=null){
            mmSpinner.setSelection(arrayAdapter1.getPosition(str_month));
        }

        if (str_year!=null){
            yyyySpinner.setSelection(arrayAdapter2.getPosition(str_year));
        }
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

    /**
     * Method to perform the charging of the card
     */
    private void performCharge() {
        //create a Charge object
        charge = new Charge();

        //set the card to charge
        charge.setCard(card);
        /*charge.setCurrency("£");*/

        //call this method if you set a plan
        //charge.setPlan("PLN_yourplan");

        charge.setEmail(email); //dummy email address

        charge.setAmount(100); //test amount


        PaystackSdk.chargeCard(PayActivity.this, charge, new Paystack.TransactionCallback() {
            @Override
            public void onSuccess(Transaction transaction) {
                // This is called only after transaction is deemed successful.
                // Retrieve the transaction, and send its reference to your server
                // for verification.
                String paymentReference = transaction.getReference();
                Log.d("TAG", "onSuccess: "+paymentReference);
                Toast.makeText(PayActivity.this, "Transaction Successful! payment reference: "
                        + paymentReference, Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.putExtra("paymentReference", paymentReference);
                setResult(MainActivity1.REQUEST_CODE_PAYSTACK, intent);

                finish();
            }

            @Override
            public void beforeValidate(Transaction transaction) {
                // This is called only before requesting OTP.
                // Save reference so you may send to server. If
                // error occurs with OTP, you should still verify on server.
            }

            @Override
            public void onError(Throwable error, Transaction transaction) {

                Toast.makeText(PayActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                //handle error here
            }
        });
    }

//    private boolean validateForm() {
//        boolean valid = true;
//
//        String email = emailField.getText().toString();
//        if (TextUtils.isEmpty(email)) {
//            emailField.setError("Required.");
//            valid = false;
//        } else {
//            emailField.setError(null);
//        }
//
//        String cardNumber = cardNumberField.getText().toString();
//        if (TextUtils.isEmpty(cardNumber)) {
//            cardNumberField.setError("Required.");
//            valid = false;
//        } else {
//            cardNumberField.setError(null);
//        }
//
//
//        String expiryMonth = expiryMonthField.getText().toString();
//        if (TextUtils.isEmpty(expiryMonth)) {
//            expiryMonthField.setError("Required.");
//            valid = false;
//        } else {
//            expiryMonthField.setError(null);
//        }
//
//        String expiryYear = expiryYearField.getText().toString();
//        if (TextUtils.isEmpty(expiryYear)) {
//            expiryYearField.setError("Required.");
//            valid = false;
//        } else {
//            expiryYearField.setError(null);
//        }
//
//        String cvv = cvvField.getText().toString();
//        if (TextUtils.isEmpty(cvv)) {
//            cvvField.setError("Required.");
//            valid = false;
//        } else {
//            cvvField.setError(null);
//        }
//
//        return valid;
//    }


}
