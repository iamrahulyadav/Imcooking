package com.imcooking.activity.main.setup;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.ApiRequest.SignUp;
import com.imcooking.R;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;

public class SignUpActivity extends AppBaseActivity implements RadioGroup.OnCheckedChangeListener {

    LinearLayout linearLayout;
    TextView txtLogin;
    private EditText edt_uname, edt_email, edt_pass, edt_conf_pass;
    private RadioGroup radioGroup;
    private String radio = "";

    // Dialog
    private Dialog dialog;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

//        find id
        linearLayout = findViewById(R.id.activity_signup_inputlayout);
        txtLogin = findViewById(R.id.activity_signup_txtLogin);

        edt_uname = findViewById(R.id.activity_signup_edtUserName);
        edt_email = findViewById(R.id.activity_signup_edtEmail);
        edt_pass = findViewById(R.id.activity_signup_edtPass);
        edt_conf_pass = findViewById(R.id.activity_signup_edtConfirmPass);

        radioGroup = findViewById(R.id.signup_radiogroup);
        radioGroup.setOnCheckedChangeListener(this);

//        set animation in layout
        Animation animationLayout = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        linearLayout.startAnimation(animationLayout);

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        createMyDialog();
    }

    private void createMyDialog(){

        dialog = new Dialog(SignUpActivity.this);
        dialog.setContentView(R.layout.dialog_signup);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(null);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        tv = dialog.findViewById(R.id.dialog_signup_text);
    }

    public void register(View view) {

        String uname = edt_uname.getText().toString().trim();
        String email = edt_email.getText().toString().trim();
        String pass = edt_pass.getText().toString().trim();
        String conf_pass = edt_conf_pass.getText().toString().trim();
        String selected = radio.trim();

        if(!selected.equals("")) {

            if (!uname.isEmpty() && !email.isEmpty() && !pass.isEmpty() && !conf_pass.isEmpty()) {

                if (BaseClass.checkemail(email)) {

                    if (pass.equals(conf_pass)) {

                        String str = "";
                        if(selected.equals("Chef")) str = "1";
                        else str = "2";
                        SignUp data = new SignUp();
                        data.setUser_type(str);
                        data.setUser_name(uname);
                        data.setEmail(email);
                        data.setPassword(pass);
                        data.setConfirmpassword(conf_pass);

                        new GetData(getApplicationContext(), SignUpActivity.this)
                                .getResponse(new Gson().toJson(data), "signup",
                                new GetData.MyCallback() {
                                    @Override
                                    public void onSuccess(String result) {

                                        final String response = result;
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                ApiResponse apiResponse = new Gson().fromJson(response, ApiResponse.class);
                                                Log.d("ShowResponse", apiResponse.isStatus() + "");
                                                Log.d("ShowResponse", apiResponse.getMsg());
//                                            Log.d("ShowResponse", apiResponse.getUser_data().toString());
                                                tv.setText(apiResponse.getMsg());

                                                if (apiResponse.isStatus()) {
                                                    if (apiResponse.getMsg().equals("Foodie successfully registered and send verification code your email!")) {
                                                        edt_uname.setText("");
                                                        edt_email.setText("");
                                                        edt_pass.setText("");
                                                        edt_conf_pass.setText("");
//                                                        dialog.show();
                                                    } else {
                                                        BaseClass.showToast(getApplicationContext(), "Something Went Wrong");
                                                    }
                                                } else {
//                                                    dialog.show();
                                                }
                                                dialog.show();
                                            }
                                        });
                                    }
                                });
                    } else {
                        BaseClass.showToast(getApplicationContext(), "Password does not match.");
                        edt_pass.setText("");
                        edt_conf_pass.setText("");
                    }
                } else {
                    BaseClass.showToast(getApplicationContext(), "Please Enter a Valid Email.");
                    edt_email.setText("");
                }
            } else {
                BaseClass.showToast(getApplicationContext(), "All fields are required.");
            }
        } else {
            BaseClass.showToast(getApplicationContext(), "Please select an option.");
        }
    }

    public void register_okay(View view){

        if(tv.getText().toString().equals("Foodie successfully registered and send verification code your email!")){
            dialog.dismiss();
            finish();
        } else{
            dialog.dismiss();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        RadioButton radioButton = findViewById(checkedId);
        radio = radioButton.getText().toString();
//        BaseClass.showToast(getApplicationContext(),radioButton.getText().toString());
    }
}


/*
    public static final MediaType JSON
            = MediaType.parse("application/json");

    public void makeBooking(String cateRequest){
        // Utility.showloadingPopup(this);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        okhttp3.RequestBody requestBody = okhttp3.RequestBody.create(JSON,cateRequest);

        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://webdevelopmentreviews.net/imcooking/api/signup")
                .post(requestBody)
                .addHeader("Content-Type", "application/json")
                .addHeader("cache-control", "no-cache")
                .build();
        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.SECONDS)
                .writeTimeout(10000, TimeUnit.SECONDS)
                .readTimeout(30000, TimeUnit.SECONDS)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, final IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        String msg = e.getMessage();

                        Toast.makeText(SignUpActivity.this, "No Net", Toast.LENGTH_SHORT).show();
//                        Utility.message(getApplicationContext(), getResources().getString(R.string.no_internet_connection));
                    }
                });
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                progressDialog.dismiss();

                if (response!=null&&response.body().toString().length()>0){
                    if (request.body()!=null){
                        String msg = response.body().string();
                        Log.d("TAG", "onResponse: booking"+ msg);

                    }
                }
            }
        });
    }


    public void register2(View view){

        StringRequest stringRequest = new StringRequest(Request.Method.POST,ApiClient.BASE_URL + "signup", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                Log.d("TAG", "onResponse: " + s);
            }
        },

                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String>stringHashMap = new HashMap<>();

                stringHashMap.put("user_type", "1");
                stringHashMap.put("user_name", "vaibhav123");
                stringHashMap.put("email", "vaibhav@gmail.com");
                stringHashMap.put("password", "123");
                stringHashMap.put("confirmpassword", "123");

                return stringHashMap;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void register1(View view){
        ApiInterface retroFitApis = ApiClient.getClient();//.create(ApiInterface.class);

        Call<ApiResponse> responseCall = retroFitApis.signup("1", "vaibhav",
                "vaibhav@gmail.com", "123", "123") ;
        final Gson gson = new Gson();
        responseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                 Log.d("SignUp1", "onResponse: point "+gson.toJson(response.body()));
                }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
            }
        });

    }
*/
