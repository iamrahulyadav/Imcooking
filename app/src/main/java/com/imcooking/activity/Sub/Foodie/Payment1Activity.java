package com.imcooking.activity.Sub.Foodie;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.imcooking.Model.ApiRequest.PlaceOrder;
import com.imcooking.R;
import com.imcooking.activity.home.MainActivity;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalProfileSharingActivity;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class Payment1Activity extends AppBaseActivity {
    private TextView txt_price, txt_place_order;
    private PlaceOrder placeOrder = new PlaceOrder();
    private Gson gson = new Gson();
    private String payment_type="cod", chef_name;
    private RadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            BaseClass.setLightStatusBar(getWindow().getDecorView(),Payment1Activity.this);
        }
        if (getIntent().hasExtra("order_details")){
            placeOrder = gson.fromJson(getIntent().getStringExtra("order_details"), PlaceOrder.class);
        }
        if (getIntent().hasExtra("chef_name")){
            chef_name = getIntent().getStringExtra("chef_name");
        }
        init();
    }

    private void init(){
        txt_price = findViewById(R.id.activity_payment_total_price);
        txt_place_order = findViewById(R.id.activity_payment_btn_place);
        radioGroup = findViewById(R.id.activity_payment_radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.activity_payment_radio_paypal){
                    payment_type = "paypal";
                }
                else if(checkedId==R.id.activity_payment_radio_cod){
                    payment_type = "cod";
                }
            }
        });

        txt_price.setText("Total Price: £"+placeOrder.getTotal_price());

        txt_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeOrder.setPayment_type(payment_type);

                if(payment_type.equals("cod")) {
                    makePayment(placeOrder);
                } else {
                    myPayPal();
                }
            }
        });

    }

    private void makePayment(PlaceOrder placeOrder ){
        Log.d(Payment1Activity.class.getName(), "Rakhi "+gson.toJson(placeOrder));

        try {
            JSONObject jsonObject = new JSONObject(gson.toJson(placeOrder));
            new GetData(getApplicationContext(), Payment1Activity.this).sendMyData(jsonObject, GetData.PLACE_ORDER, Payment1Activity.this, new GetData.MyCallback() {
                @Override
                public void onSuccess(final String result) {
                    /*{"status":true,"booking_id":"CO14061939577511","msg":"Dish Booking Successfully"}*/
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject1 = new JSONObject(result);
                                if (jsonObject1.getBoolean("status")){
                                    createMyDialog(jsonObject1.getString("booking_id"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.d(Payment1Activity.class.getName(), "Rakhi "+result);
                        }
                    });
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    Dialog dialog ;
    @SuppressLint("SetTextI18n")
    private void createMyDialog(final String booking_id){
        dialog= new Dialog(Payment1Activity.this);
        dialog.setContentView(R.layout.dialog_add_to_cart);

      // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imgDia = dialog.findViewById(R.id.imageDialog);
        TextView txtDialog = dialog.findViewById(R.id.imgdialog_text);
        TextView txtOk = dialog.findViewById(R.id.txtdialog_ok);

        imgDia.setImageResource(R.drawable.ordersuccess);

        txtDialog.setText("Your Order has been Successfully Place with \"" +chef_name+"\" ");

        dialog.findViewById(R.id.tv_cancel_add_to_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(true);
        dialog.show();
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  startActivity(new Intent(Payment1Activity.this, ChefOrderDetailsActivity.class)
                        .putExtra("order_id",booking_id));*/

                startActivity(new Intent(Payment1Activity.this, MainActivity.class).putExtra("pay","payorder"));
            }
        });
    }



    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    private static final String CONFIG_CLIENT_ID = "AYGjWZQjVH1JkxtJv28UEE7jEYXlA6nEHtV5FNRt5pse0pi4dPrQ84PRU7R76prbxuwvY2CJGG-NqPbm";

    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    private static final int REQUEST_CODE_PROFILE_SHARING = 3;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("Example Merchant")
            .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));

    private void myPayPal(){

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);


        PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent1 = new Intent(Payment1Activity.this, PaymentActivity.class);
        intent1.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        intent1.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

        startActivityForResult(intent1, REQUEST_CODE_PAYMENT);

    }

    private PayPalPayment getThingToBuy(String paymentIntent) {
        return new PayPalPayment(new BigDecimal(placeOrder.getTotal_price()), "GBP", "Make Payment",
                paymentIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm =
                        data.getParcelableExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        Log.i("MyPayPal", confirm.toJSONObject().toString(4));
                        Log.i("MyPayPal", confirm.getPayment().toJSONObject().toString(4));
                        JSONObject jsonObject = new JSONObject(confirm.toJSONObject().toString(4));
                        JSONObject  jsonObject1 = jsonObject.getJSONObject("response");
                        String transaction_id = jsonObject1.getString("id");
                        placeOrder.setTransaction_id(transaction_id);
                        makePayment(placeOrder);
                        displayResultText("PaymentConfirmation info received from PayPal");

                    } catch (JSONException e) {
                        Log.e("MyPayPal", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("MyPayPal", "The user canceled.");
            } else if (resultCode == com.paypal.android.sdk.payments.PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(
                        "MyPayPal",
                        "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        } else if (requestCode == REQUEST_CODE_FUTURE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth =
                        data.getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        Log.i("FuturePaymentExample", auth.toJSONObject().toString(4));

                        String authorization_code = auth.getAuthorizationCode();
                        Log.i("FuturePaymentExample", authorization_code);

                        sendAuthorizationToServer(auth);
                        displayResultText("Future Payment code received from PayPal");

                    } catch (JSONException e) {
                        Log.e("FuturePaymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("FuturePaymentExample", "The user canceled.");
            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(
                        "FuturePaymentExample",
                        "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
            }
        } else if (requestCode == REQUEST_CODE_PROFILE_SHARING) {
            if (resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth =
                        data.getParcelableExtra(PayPalProfileSharingActivity.EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        Log.i("ProfileSharingExample", auth.toJSONObject().toString(4));

                        String authorization_code = auth.getAuthorizationCode();
                        Log.i("ProfileSharingExample", authorization_code);

                        sendAuthorizationToServer(auth);
                        displayResultText("Profile Sharing code received from PayPal");

                    } catch (JSONException e) {
                        Log.e("ProfileSharingExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("ProfileSharingExample", "The user canceled.");
            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(
                        "ProfileSharingExample",
                        "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
            }
        }
    }

    protected void displayResultText(String result) {
//        ((TextView)findViewById(R.id.txtResult)).setText("Result : " + result);
        Toast.makeText(
                getApplicationContext(),
                result, Toast.LENGTH_LONG)
                .show();
    }

    private void sendAuthorizationToServer(PayPalAuthorization authorization) {

        /**
         * TODO: Send the authorization response to your server, where it can
         * exchange the authorization code for OAuth access and refresh tokens.
         *
         * Your server must then store these tokens, so that your server code
         * can execute payments for this user in the future.
         *
         * A more complete example that includes the required app-server to
         * PayPal-server integration is available from
         * https://github.com/paypal/rest-api-sdk-python/tree/master/samples/mobile_backend
         */

    }

}

