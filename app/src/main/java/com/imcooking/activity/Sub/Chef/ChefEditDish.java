package com.imcooking.activity.Sub.Chef;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imcooking.Model.ApiRequest.ModelChefAddDish;
import com.imcooking.Model.ApiRequest.ModelChefEditDish;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.ChefProfileData1;
import com.imcooking.Model.api.response.CuisineData;
import com.imcooking.R;
import com.imcooking.adapters.AdapterEditDishPhotos;
import com.imcooking.fragment.chef.ChefDishDetail;
import com.imcooking.fragment.chef.ChefHome;
import com.imcooking.utils.AndroidMultiPartEntity;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class ChefEditDish extends AppBaseActivity implements CompoundButton.OnCheckedChangeListener,
        AdapterView.OnItemSelectedListener, AdapterEditDishPhotos.browse_photo, SeekBar.OnSeekBarChangeListener {

    private String chef_id="", dish_id="", name, cuisine, price, description, special_note,qyt, available, homedelivery,
            pickup,video_sample, time_1, time_2;
    private EditText edt_name, edt_price, edt_description, edt_special_note, edt_qyt;
    private SwitchCompat switch_1, switch_2, switch_3;
    private String sw_1 = "No", sw_2 = "No", sw_3 = "No";
    private String dish_miles = "10";
//    private Spinner sp;
//    private SeekBar seekBar;
    private final int  REQUEST_CAMERA=3, SELECT_FILE = 1,REQUEST_TAKE_GALLERY_VIDEO=2;
    Bitmap bitmap;
    boolean result;
    private String userChoosenTask;
    private Spinner toSpinner, fromSpinner;
    private TextView tv_title;
    private TextView tv_add_more_photo, txt_video, btn_browse_video;

    private String bitmapString="a";
    private ArrayList<String>base;
    private ArrayList<String> imgBase64List;
    private String title;
    private LinearLayout layout_photos, layout;
//    public TextView addMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_edit_dish);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        layout = findViewById(R.id.layout_chef_edit_dish);

        init();
        getMyCuisines();
        getMyIntentData();
        availabilitySpinner();

    }

    private TextView tv_photo_name;
    private RecyclerView photorv1;

    private ArrayList<String> arr_photos = new ArrayList<>();
    private ArrayList<String> arr_edit_photos_base64 = new ArrayList<>();

    private int pos;
    private Spinner sp_cuisine;
    private List<CuisineData.CuisineDataBean> cuisineList=new ArrayList<>();
    private CuisineData cuisineData = new CuisineData();
    private String selected_cuisine, selected_cuisine_id;

    private ArrayList<String> arrayList = new ArrayList<>();

    private SeekBar seekBar_available_time;
    private TextView tv_seekbar_text, tv_time_1, tv_time_2;
    private String str_time_from /*= "00:00 AM"*/, str_time_to /*= "04:00 AM"*/;

    private void init(){

        seekBar_available_time = findViewById(R.id.edit_dish_seekbar);
        tv_seekbar_text = findViewById(R.id.edit_dish_seekbar_text);
        tv_time_1 = findViewById(R.id.edit_dish_time_1);
        tv_time_2 = findViewById(R.id.edit_dish_time_2);
        fromSpinner = findViewById(R.id.activity_chef_edit_dish_spinner_from);
        toSpinner = findViewById(R.id.activity_chef_edit_dish_spinner_to);

        seekBar_available_time.setProgress(0);
        seekBar_available_time.setMax(24);

        ShapeDrawable thumb = new ShapeDrawable( new RectShape() );
        thumb.getPaint().setColor(getResources().getColor(R.color.theme_color));
        thumb.setIntrinsicHeight( 80 );
        thumb.setIntrinsicWidth( 200 );
        seekBar_available_time.setThumb( thumb );

        seekBar_available_time.setOnSeekBarChangeListener(this);

    //    layout_photos = findViewById(R.id.edit_dish_photos);
        btn_browse_video = findViewById(R.id.chef_edit_dish_photos_ttx_select_video);
//        tv_photo_name = findViewById(R.id.chef_edit_dish_photo_name);
        edt_qyt = findViewById(R.id.chef_edit_dish_qyt);
        sp_cuisine = findViewById(R.id.chef_edit_dish_spinner_cuisine);
        tv_add_more_photo = findViewById(R.id.edit_dish_add_more);
        imgBase64List = new ArrayList<>();
        tv_title = findViewById(R.id.chef_edit_dish_title);
        txt_video = findViewById(R.id.activity_chef_edit_dish_video_txt);
        //addMore=findViewById(R.id.addMore);
        edt_name = findViewById(R.id.chef_edit_dish_name);
//        edt_cuisine = findViewById(R.id.chef_edit_dish_cuisine);
        edt_price = findViewById(R.id.chef_edit_dish_price);
        edt_description = findViewById(R.id.chef_edit_dish_description);
        edt_special_note = findViewById(R.id.chef_edit_dish_special_note);

        switch_1 = findViewById(R.id.edit_dish_switch_current_available);
        switch_2 = findViewById(R.id.edit_dish_switch_home_delivery);
        switch_3 = findViewById(R.id.edit_dish_switch_pickup);
//        seekBar = findViewById(R.id.activity_chef_edit_dish_time);

        switch_1.setOnCheckedChangeListener(this);
        switch_2.setOnCheckedChangeListener(this);
        switch_3.setOnCheckedChangeListener(this);
        btn_browse_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions(ChefEditDish.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_TAKE_GALLERY_VIDEO);
                } else {
                    Log.e("DB", "PERMISSION GRANTED");
                    result = true;
                    Intent intent = new Intent();
                    intent.setType("video/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,"Select Video"),REQUEST_TAKE_GALLERY_VIDEO);
                }
            }
        });


        photorv1 = findViewById(R.id.chef_edit_dish_photos_recycler);
//        CustomLayoutManager manager = new CustomLayoutManager(getApplicationContext()){
//            @Override
//            public boolean canScrollVertically() {
//                return false;
//            }
//        };

        photorv1.setHasFixedSize(false);
        LinearLayoutManager manager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        photorv1.setLayoutManager(manager);
        arr_photos.clear();
        arr_edit_photos_base64.clear();
//        arr_photos.add("Photo");

        setMyAdapter(arr_photos);
        createMyDialog();
    }

    private ArrayList<String> toList, fromList;
    private void availabilitySpinner() {
        toList = new ArrayList<>();
        fromList = new ArrayList<>();
        int j=1;
        for (int i=0;i<=24;i++){
            if (i<=12){
                if (i<=9){
                    toList.add("0"+i+":00 AM");
                    fromList.add("0"+i+":00 AM");
                } else{

                    if (i==12){
                        toList.add(i+":00 PM");
                        fromList.add(i+":00 PM");
                    } else {
                        toList.add(i+":00 AM");
                        fromList.add(i+":00 AM");
                    }
                }
            } else {
                if (i<24){
                    if (j<=9){
                        toList.add("0"+j+":00 PM");
                        fromList.add("0"+j+":00 PM");
                    } else{
                        toList.add(j+":00 PM");
                        fromList.add(j+":00 PM");
                    }
                    j++;
                }
            }
        }

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(ChefEditDish.this,
                R.layout.spinner_row, toList);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_row);
        toSpinner.setAdapter(arrayAdapter);
        ArrayAdapter<String> fromAdapter=new ArrayAdapter<String>(ChefEditDish.this,
                R.layout.spinner_row, fromList);
        fromAdapter.setDropDownViewResource(R.layout.spinner_row);

        fromSpinner.setAdapter(fromAdapter);

        if (str_time_from != null) {
            int spinnerPosition = fromAdapter.getPosition(str_time_from);
            fromSpinner.setSelection(spinnerPosition);
        }
        if (str_time_to != null) {
            int spinnerPosition = arrayAdapter.getPosition(str_time_to);
            toSpinner.setSelection(spinnerPosition);
        }


     toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
             str_time_to = adapterView.getItemAtPosition(i)+"";
         }

         @Override
         public void onNothingSelected(AdapterView<?> adapterView) {

         }
     });

        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                str_time_from = adapterView.getItemAtPosition(i)+"";
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }




    private void getMyIntentData() {
        base = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            qyt = getIntent().getExtras().getString("qyt");
            dish_id = getIntent().getExtras().getString("id");
            name = getIntent().getExtras().getString("name");
            cuisine = getIntent().getExtras().getString("cuisine");
            price = getIntent().getExtras().getString("price");
            description = getIntent().getExtras().getString("ingredients");
            special_note = getIntent().getExtras().getString("special_note");
            available = getIntent().getExtras().getString("available");
            homedelivery = getIntent().getExtras().getString("home_delivery");
            pickup = getIntent().getExtras().getString("pickup");
            time_1 = getIntent().getExtras().getString("time1");
            time_2 = getIntent().getExtras().getString("time2");
            tv_time_1.setText(time_1);
            tv_time_2.setText(time_2);
            str_time_from = time_1;
            str_time_to = time_2;

            tv_seekbar_text.setText("From:" + time_1 + "\t To:" + time_2);
            setMySeekbarProgress("From:" + time_1 + " \t To:" + time_2);

            if (getIntent().hasExtra("video")){
                video_sample = getIntent().getExtras().getString("video");
                if (video_sample!=null){
                    txt_video.setText(video_sample.replace(GetData.IMG_BASE_URL,""));
                } else txt_video.setText("Video");
            }

//            arrayList = getIntent().getExtras().getStringArrayList("image");
            String s = getIntent().getExtras().getString("image");
            arrayList = new Gson().fromJson(s,ArrayList.class);

            base = ChefDishDetail.base64Array;

            if (!qyt.equals("null")){
                edt_qyt.setText(qyt);
            } else edt_qyt.setText("0");

            for(int i=0; i<arrayList.size(); i++){
                if(i<3){
                    arr_photos.add(arrayList.get(i));
                }
            }

            adapterEditDishPhotos.notifyDataSetChanged();

            Log.d("TAG", "getMyIntentData: "+arrayList.size());

            if(arr_photos.size() == 3){
                tv_add_more_photo.setVisibility(View.GONE);
            }
            edt_name.setText(name);
//            sp_cuisine.setSelection();
//            edt_cuisine.setText(cuisine);
            edt_price.setText(price);
            edt_description.setText(description);
            edt_special_note.setText(special_note);

            Log.d("ChefEditDishData", "Available = " + available + "\n HomeDelivery = "
                    + homedelivery + "\n Pickup = " + pickup);
            if(available.equals("Yes")||available.equals("yes")){
                switch_1.setChecked(true);
            } else if(available.equals("No")||available.equals("no")){
                switch_1.setChecked(false);
            } else {}

            if(homedelivery.equals("Yes")||homedelivery.equals("yes")){
                switch_2.setChecked(true);
            } else if(homedelivery.equals("No")||homedelivery.equals("no")){
                switch_2.setChecked(false);
            } else {}

            if(pickup.equals("Yes")||pickup.equals("yes")){
                switch_3.setChecked(true);
            } else if(pickup.equals("No")||pickup.equals("no")){
                switch_3.setChecked(false);
            } else {

            }
            for (int i=0; i<list.size(); i++){
                if(list.get(i).getCuisine_name().equals(cuisine)){
                    sp_cuisine.setSelection(i);
                }
            }
            title = "editdish";
            tv_title.setText("Edit Dish");

        } else{
            title = "dish";
            tv_title.setText("Add Dish");
            arr_photos.add("Photo");
            base.add("MyBase64String");
        }

        TinyDB tinyDB = new TinyDB(getApplicationContext());

        String login_data = tinyDB.getString("login_data");
        ApiResponse.UserDataBean userDataBean = new ApiResponse.UserDataBean();
        userDataBean = new Gson().fromJson(login_data, ApiResponse.UserDataBean.class);

        chef_id = userDataBean.getUser_id() + "";
    }

    private void getMyCuisines() {

        list = ChefHome.chefProfileData1.getChef_data().getCuisine_name();
        setMyCuisines(list);

    }

    private List<ChefProfileData1.ChefDataBean.CuisineNameBean> list = new ArrayList<>();

    private void setMyCuisines(List<ChefProfileData1.ChefDataBean.CuisineNameBean> list){

        ArrayList<String> arrayList = new ArrayList<>();
        for(int i=0; i<list.size(); i++){
            arrayList.add(list.get(i).getCuisine_name());
        }

        sp_cuisine.setOnItemSelectedListener(this);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this,
                R.layout.spinner_row_1, arrayList);
        arrayAdapter1.setDropDownViewResource(R.layout.spinner_row_1);
        sp_cuisine.setAdapter(arrayAdapter1);
        sp_cuisine.setOnItemSelectedListener(this);
    }

    public void edit_dish_submit(View view){

        name = edt_name.getText().toString().trim();
//        cuisine = edt_cuisine.getText().toString().trim();
        price = edt_price.getText().toString().trim();
        description = edt_description.getText().toString().trim();
        special_note = edt_special_note.getText().toString().trim();
        qyt = edt_qyt.getText().toString().trim();

        if(!name.isEmpty()){
            if(!price.isEmpty()){
                if(!description.isEmpty()){
                    if(!special_note.isEmpty()){
                        if(!qyt.isEmpty()) {
                            if(sw_2.equals("No") && sw_3.equals("No")) {
                                BaseClass.showToast(getApplicationContext(), "Please select either" +
                                        "Home delivery or Pick-up only.");
                            } else{
                                /* if(selectedPath!=null) {*/
                                    if (title.equals("dish")) {
                                        if (!bitmapString.equals("a")) {
                                            adddish(title);
                                        } else {
                                            BaseClass.showToast(getApplicationContext(), "Please Select a Photo");
                                        }
                                    } else if (title.equals("editdish")) {
                                        try {
                                            editdish(title);
                                        } catch (MalformedURLException e) {
                                            e.printStackTrace();
                                        }
                                    }
                            }
                        } else{
                            BaseClass.showToast(getApplicationContext(), "All Fields Are Required");
                        }
                    } else{
                        BaseClass.showToast(getApplicationContext(), "All Fields Are Required");
                    }
                } else{
                    BaseClass.showToast(getApplicationContext(), "All Fields Are Required");
                }
            } else{
                BaseClass.showToast(getApplicationContext(), "All Fields Are Required");
            }
        } else{
            BaseClass.showToast(getApplicationContext(), "All Fields Are Required");
        }
    }

    private void adddish(String title){

        Log.d("Base64Size", base.size() + "");
        ArrayList<String> base1 = new ArrayList<>();
//        base1.add("a");

        for(int i=0; i<base.size(); i++) {
            if (!base.get(i).equals("MyBase64String")) {
                base1.add(base.get(i));
            }
        }

        ModelChefAddDish requestData = new ModelChefAddDish();
        requestData.setUser_id(chef_id);
        requestData.setDish_name(name);
        requestData.setCuisine(selected_cuisine_id);
        requestData.setSubcuisine("15");
        requestData.setDescription(description);
        requestData.setSpecial_note(special_note);
        requestData.setDish_price(price);
        requestData.setDish_from(str_time_from/*"12:10 AM"*/);
        requestData.setDish_to(str_time_to/*"4:10 AM"*/);
        requestData.setAvailable(sw_1);
        requestData.setHomedeliver(sw_2);
        requestData.setPickup(sw_3);
        requestData.setDeliverymiles(dish_miles);
        requestData.setDish_video("abc");
        requestData.setDish_image(base1);
        requestData.setDish_qyt(qyt);


        Log.d("Base64Size", base1.size() + "");


        try {
            JSONObject jsonObject = new JSONObject(new Gson().toJson(requestData));
            Log.d("MyRequest", jsonObject.toString());

            new GetData(getApplicationContext()).sendMyData(jsonObject, title,
                    ChefEditDish.this, new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {

                            try {
                                JSONObject job = new JSONObject(result);
                                if(job.has("status")){
                                    if(job.getBoolean("status")){
                                        if(job.has("message")){
                                            if(job.getString("message").equals("Add dish Successfully")){
                                                dish_id = job.getString("last_insertid");
                                                dialog.show();

                                              /*  if (selectedPath!=null){
                                                    if (duration<=20000) {
                                                        new UploadFileToServer(selectedPath).execute();
                                                    }
                                                } else {
                                                    // finish();
                                                }*/
                                            } else {
                                                BaseClass.showToast(getApplicationContext(), getResources().getString(R.string.error));
                                            }
                                        } else{
                                            BaseClass.showToast(getApplicationContext(), getResources().getString(R.string.error));
                                        }
                                    } else {
                                        BaseClass.showToast(getApplicationContext(), getResources().getString(R.string.error));
                                    }
                                } else {
                                    BaseClass.showToast(getApplicationContext(), getResources().getString(R.string.error));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

            } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void editdish(String title) throws MalformedURLException {

        Log.d("Base64Size", base.size() + "");
        ArrayList<String> base1 = new ArrayList<>();
        for(int i=0; i<base.size(); i++) {
            if (!base.get(i).equals("MyBase64String")) {
                base1.add(base.get(i));
            }
        }

        if(price.contains("£")){
            price = price.replace("£", "");
        }

        qyt = edt_qyt.getText().toString().trim();
        ModelChefEditDish requestData = new ModelChefEditDish();
        requestData.setDish_id(dish_id);
        requestData.setUser_id(chef_id);
        requestData.setDish_name(name);
        requestData.setCuisine(selected_cuisine_id);
        requestData.setSubcuisine("15");
        requestData.setDescription(description);
        requestData.setSpecial_note(special_note);
        requestData.setDish_price(price);
        requestData.setDish_from(str_time_from);
        requestData.setDish_to(str_time_to);
        requestData.setAvailable(sw_1);
        requestData.setHomedeliver(sw_2);
        requestData.setPickup(sw_3);
        requestData.setDeliverymiles(dish_miles);
        requestData.setDish_video("abc");
        requestData.setDish_image(base1);
        requestData.setDish_qyt(qyt);

        try {
            JSONObject jsonObject = new JSONObject(new Gson().toJson(requestData));
            Log.d("MyRequest", jsonObject.toString());

            new GetData(getApplicationContext()).sendMyData(jsonObject, title,
                    ChefEditDish.this, new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            try {
                                JSONObject job = new JSONObject(result);
                                if(job.has("status")){
                                    if(job.getBoolean("status")){
                                        if(job.has("message")){
                                            if(job.getString("message").equals("update dish Successfully")){
                                                BaseClass.showToast(getApplicationContext(), "Dish Updated Successfully" );
                                                finish();

                                               /* if (selectedPath!=null){
                                                    if (duration<=20000)
                                                        new UploadFileToServer(selectedPath).execute();
                                                }*/

                                            } else {
                                                BaseClass.showToast(getApplicationContext(   ), getResources().getString(R.string.error));
                                            }
                                        } else{
                                            BaseClass.showToast(getApplicationContext(), getResources().getString(R.string.error));
                                        }
                                    } else {
                                        BaseClass.showToast(getApplicationContext(), getResources().getString(R.string.error));
                                    }
                                } else {
                                    BaseClass.showToast(getApplicationContext(), getResources().getString(R.string.error));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Dialog dialog;
    private void createMyDialog(){

        dialog = new Dialog(ChefEditDish.this);
        dialog.setContentView(R.layout.dialog_add_dish);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        TextView tv_dialog = dialog.findViewById(R.id.dialog_add_dish_text);
        TextView tv_ok_dialog = dialog.findViewById(R.id.dialog_add_dish_btn);
        TextView tv_cross_dialog = dialog.findViewById(R.id.dialog_add_dish_cross);

        tv_dialog.setText("Thank you!! \n The dish has been added to your list.");
        tv_dialog.setGravity(Gravity.CENTER_HORIZONTAL);

        tv_cross_dialog.setVisibility(View.GONE);

        tv_ok_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent i = new Intent();
                setResult(105, i);
                finish();
            }
        });
        tv_cross_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    public void add_more_photos(View view){
//1
        arr_photos.add("Photo");
        base.add("MyBase64String");
        if(arr_photos.size() == 3){
            tv_add_more_photo.setVisibility(View.GONE);
        }
        adapterEditDishPhotos.notifyDataSetChanged();
    }

    private AdapterEditDishPhotos adapterEditDishPhotos;

    private void setMyAdapter(ArrayList<String> arr_p){

        adapterEditDishPhotos = new AdapterEditDishPhotos(ChefEditDish.this, arr_p, this/*,addMore*/);
        photorv1.setAdapter(adapterEditDishPhotos);
//        adapterEditDishPhotos.notifyDataSetChanged();
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ChefEditDish.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                        ActivityCompat.requestPermissions(ChefEditDish.this,
                                new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                REQUEST_CAMERA);
                    } else {
                        Log.e("DB", "PERMISSION GRANTED");
                        result = true;
                        cameraIntent();
                    }

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ChefEditDish.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                SELECT_FILE);
                    } else {
                        Log.e("DB", "PERMISSION GRANTED");
                        result = true;
                        galleryIntent();
                    }

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                     @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(getApplicationContext(), "Camera Permission not granted", Toast.LENGTH_SHORT).show();
                } else {
                    result =true;
                    if(result){
//                        Toast.makeText(getApplicationContext(), "Camera Permission  granted", Toast.LENGTH_SHORT).show();
                        //cameraIntent();
                    }
                }
                break;
            case SELECT_FILE:
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(getApplicationContext(), "file Permission not granted", Toast.LENGTH_SHORT).show();
                } else {
                    result =true;
                    if(result){}
//                        galleryIntent();
//                        Toast.makeText(getApplicationContext(), "file Permission granted", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_TAKE_GALLERY_VIDEO:
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(getApplicationContext(), "file Permission not granted", Toast.LENGTH_SHORT).show();
                } else {
                    result =true;
                    if(result){}
//                        galleryIntent();
//                        Toast.makeText(getApplicationContext(), "file Permission granted", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    int duration;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == SELECT_FILE){
                onSelectFromGalleryResult(data);
            }
            else if (requestCode == REQUEST_CAMERA)
            {
                onCaptureImageResult(data);
            }
            else if (requestCode == REQUEST_TAKE_GALLERY_VIDEO) {
                Uri selectedImageUri = data.getData();
                selectedPath = getPath(selectedImageUri);
                if (selectedPath!=null) {
                    MediaPlayer mp = MediaPlayer.create(this, Uri.parse(selectedPath));
                    duration = mp.getDuration();
                    Log.d("TAG", "video:path " + duration);
                    if (duration > 20000) {
                        BaseClass.showToast(this, "Your Video is too large. You can upload video of max 20 seconds.");
                    } else {
                        txt_video.setText(selectedPath.substring(selectedPath.lastIndexOf("/") + 1));
                    }
                }
            }
        }
    }

    private String selectedPath;

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

        if(seekBar.getId() == R.id.edit_dish_seekbar){
            String text = getMySeekbarText(i);
            tv_seekbar_text.setText(text);
        }
    }

    private String getMySeekbarText(int progress){

        String text = "";
        if(progress == 0){
            text = "From:00:00AM \t To:04:00AM";
            str_time_from = "00:00 AM"; str_time_to = "04:00 AM";
        } else if(progress == 1){
            text = "From:00:00AM \t To:04:00AM";
            str_time_from = "00:00 AM"; str_time_to = "04:00 AM";
        } else if(progress == 2){
            text = "From:01:00AM \t To:05:00AM";
            str_time_from = "01:00 AM"; str_time_to = "05:00 AM";
        } else if(progress == 3){
            text = "From:02:00AM \t To:06:00AM";
            str_time_from = "02:00 AM"; str_time_to = "06:00 AM";
        } else if(progress == 4){
            text = "From:03:00AM \t To:07:00AM";
            str_time_from = "03:00 AM"; str_time_to = "07:00 AM";
        } else if(progress == 5){
            text = "From:04:00AM \t To:08:00AM";
            str_time_from = "04:00 AM"; str_time_to = "08:00 AM";
        } else if(progress == 6){
            text = "From:05:00AM \t To:09:00AM";
            str_time_from = "05:00 AM"; str_time_to = "09:00 AM";
        } else if(progress == 7){
            text = "From:06:00AM \t To:10:00AM";
            str_time_from = "06:00 AM"; str_time_to = "10:00 AM";
        } else if(progress == 8){
            text = "From:07:00AM \t To:11:00AM";
            str_time_from = "07:00 AM"; str_time_to = "11:00 AM";
        } else if(progress == 9){
            text = "From:08:00AM \t To:12:00PM";
            str_time_from = "08:00 AM"; str_time_to = "12:00 PM";
        } else if(progress == 10){
            text = "From:09:00AM \t To:01:00PM";
            str_time_from = "09:00 AM"; str_time_to = "01:00 PM";
        } else if(progress == 11){
            text = "From:10:00AM \t To:02:00PM";
            str_time_from = "10:00 AM"; str_time_to = "02:00 PM";
        } else if(progress == 12){
            text = "From:11:00AM \t To:03:00PM";
            str_time_from = "11:00 AM"; str_time_to = "03:00 PM";
        } else if(progress == 13){
            text = "From:12:00PM \t To:04:00PM";
            str_time_from = "12:00 PM"; str_time_to = "04:00 PM";
        } else if(progress == 14){
            text = "From:01:00PM \t To:05:00PM";
            str_time_from = "01:00 PM"; str_time_to = "05:00 PM";
        } else if(progress == 15){
            text = "From:02:00PM \t To:06:00PM";
            str_time_from = "02:00 PM"; str_time_to = "06:00 PM";
        } else if(progress == 16){
            text = "From:03:00PM \t To:07:00PM";
            str_time_from = "03:00 PM"; str_time_to = "07:00 PM";
        } else if(progress == 17){
            text = "From:04:00PM \t To:08:00PM";
            str_time_from = "04:00 PM"; str_time_to = "08:00 PM";
        } else if(progress == 18){
            text = "From:05:00PM \t To:09:00PM";
            str_time_from = "05:00 PM"; str_time_to = "09:00 PM";
        } else if(progress == 19){
            text = "From:06:00PM \t To:10:00PM";
            str_time_from = "06:00 PM"; str_time_to = "10:00 PM";
        }else if(progress == 20){
            text = "From:07:00PM \t To:11:00PM";
            str_time_from = "07:00 PM"; str_time_to = "11:00 PM";
        } else if(progress == 21){
            text = "From:08:00PM \t To:00:00AM";
            str_time_from = "08:00 PM"; str_time_to = "00:00 AM";
        } else if(progress == 22){
            text = "From:08:00PM \t To:00:00AM";
            str_time_from = "08:00 PM"; str_time_to = "00:00 AM";
        } else if(progress == 23){
            text = "From:08:00PM \t To:00:00AM";
            str_time_from = "08:00 PM"; str_time_to = "00:00 AM";
        } else if(progress == 24){
            text = "From:08:00PM \t To:00:00AM";
            str_time_from = "08:00 PM"; str_time_to = "00:00 AM";
        } else {}

        tv_time_1.setText(str_time_from);
        tv_time_2.setText(str_time_to);

        return text;
    }

    private void setMySeekbarProgress(String progress_time){
        if(progress_time.equals("From:00:00 AM \t To:04:00 AM")){
            seekBar_available_time.setProgress(1);
        } else if(progress_time.equals("From:01:00 AM \t To:05:00 AM")){
            seekBar_available_time.setProgress(2);
        } else if(progress_time.equals("From:02:00 AM \t To:06:00 AM")){
            seekBar_available_time.setProgress(3);
        } else if(progress_time.equals("From:03:00 AM \t To:07:00 AM")){
            seekBar_available_time.setProgress(4);
        } else if(progress_time.equals("From:04:00 AM \t To:08:00 AM")){
            seekBar_available_time.setProgress(5);
        } else if(progress_time.equals("From:05:00 AM \t To:09:00 AM")){
            seekBar_available_time.setProgress(6);
        } else if(progress_time.equals("From:06:00 AM \t To:10:00 AM")){
            seekBar_available_time.setProgress(7);
        } else if(progress_time.equals("From:07:00 AM \t To:11:00 AM")){
            seekBar_available_time.setProgress(8);
        } else if(progress_time.equals("From:08:00 AM \t To:12:00 PM")){
            seekBar_available_time.setProgress(9);
        } else if(progress_time.equals("From:09:00 AM \t To:01:00 PM")){
            seekBar_available_time.setProgress(10);
        } else if(progress_time.equals("From:10:00 AM \t To:02:00 PM")){
            seekBar_available_time.setProgress(11);
        } else if(progress_time.equals("From:11:00 AM \t To:03:00 PM")){
            seekBar_available_time.setProgress(12);
        } else if(progress_time.equals("From:12:00 PM \t To:04:00 PM")){
            seekBar_available_time.setProgress(13);
        } else if(progress_time.equals("From:01:00 PM \t To:05:00 PM")){
            seekBar_available_time.setProgress(14);
        } else if(progress_time.equals("From:02:00 PM \t To:06:00 PM")){
            seekBar_available_time.setProgress(15);
        } else if(progress_time.equals("From:03:00 PM \t To:07:00 PM")){
            seekBar_available_time.setProgress(16);
        } else if(progress_time.equals("From:04:00 PM \t To:08:00 PM")){
            seekBar_available_time.setProgress(17);
        } else if(progress_time.equals("From:05:00 PM \t To:09:00 PM")){
            seekBar_available_time.setProgress(18);
        } else if(progress_time.equals("From:06:00 PM \t To:10:00 PM")){
            seekBar_available_time.setProgress(19);
        } else if(progress_time.equals("From:07:00 PM \t To:11:00 PM")){
            seekBar_available_time.setProgress(20);
        } else if(progress_time.equals("From:08:00 PM \t To:00:00 AM")){
            seekBar_available_time.setProgress(21);
        } else if(progress_time.equals("From:08:00 PM \t To:00:00 AM")){
            seekBar_available_time.setProgress(22);
        } else if(progress_time.equals("From:08:00 PM \t To:00:00 AM")){
            seekBar_available_time.setProgress(23);
        } else if(progress_time.equals("From:08:00 PM \t To:00:00 AM")){
            seekBar_available_time.setProgress(24);
        } else {}
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public String getPath(Uri uri) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        if (cursor!=null && cursor.moveToFirst()){
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));

        }
        cursor.close();

        return path;
    }

    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(ChefEditDish.this.getApplicationContext().getContentResolver(), data.getData());
                Uri tempUri = getImageUri(getApplicationContext(), bm);

                // CALL THIS METHOD TO GET THE ACTUAL PATH
                File finalFile = new File(getRealPathFromURI(tempUri));
                String filePath = finalFile + "";
                Log.d("MyImagePath", filePath.substring(filePath.lastIndexOf("/") + 1));

                arr_photos.set(pos, filePath.substring(filePath.lastIndexOf("/") + 1));
                adapterEditDishPhotos.notifyDataSetChanged();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        bitmap = bm;
        bitmapString = BaseClass.BitMapToString(bitmap);
        if (base!=null){
            base.set(pos, bitmapString);
        }

    }

    private void onCaptureImageResult(Intent data) {
        if (data.hasExtra("data")){
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

            File destination = new File(Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis() + ".jpg");

            FileOutputStream fo;
            try {
                destination.createNewFile();
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();

            bitmap = thumbnail;

            Uri tempUri = getImageUri(getApplicationContext(), bitmap);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            File finalFile = new File(getRealPathFromURI(tempUri));

            String filePath = finalFile + "";

            arr_photos.set(pos, filePath.substring(filePath.lastIndexOf("/") + 1));
            adapterEditDishPhotos.notifyDataSetChanged();

            bitmapString = BaseClass.BitMapToString(bitmap);
            base.set(pos, bitmapString);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        int id = compoundButton.getId();
        if(id == R.id.edit_dish_switch_current_available){
            if(b){
                sw_1 = "Yes";
            } else{
                sw_1 = "No";
            }
        } else if(id == R.id.edit_dish_switch_home_delivery){
            if(b){
                sw_2 = "Yes";
            } else{
                sw_2 = "No";
            }
        } else if(id == R.id.edit_dish_switch_pickup){
            if(b){
                sw_3 = "Yes";
            } else{
                sw_3 = "No";
            }
        } else {}
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if(adapterView.getId() == R.id.chef_edit_dish_spinner_cuisine){
            selected_cuisine = adapterView.getItemAtPosition(i).toString();
            selected_cuisine_id = list.get(i).getCuisine_id() + "";
            Log.d("MySpinner", selected_cuisine_id);
        }else{}

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void browse_my_photo(View view, int position) {
        pos = position;
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED  ) {
                ActivityCompat.requestPermissions(ChefEditDish.this,
                    new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CAMERA);
        } else {
            Log.e("DB", "PERMISSION GRANTED");
            result = true;
        }
        selectImage();
    }


    ProgressBar progressBar;

    private long totalSize = 0;
    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        String filePath = null;
        String sessionId, contentData;
//        ProgressDialog uploading;

        public UploadFileToServer(String filePath) {
            this.filePath = filePath;

        }

        @Override
        protected void onPreExecute() {
//            progressBar = new ProgressBar(ChefEditDish.this, null, android.R.attr.h);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(30, 30, 30, 30);
            progressBar.setLayoutParams(layoutParams);
            progressBar.setIndeterminate(true);

         /*   LinearLayout linearLayout = findViewById(R.id.rootContainer);
            // Add horizontal ProgressBar to LinearLayout
            if (linearLayout != null) {
                linearLayout.addView(progressBar);
            }*/

            // setting progress bar to zero
            progressBar.setProgress(0);
         //   BaseClass.showToast(getApplicationContext(), "0%");
          //  uploading = ProgressDialog.show(ChefEditDish.this, "Uploading File", "Please wait...", false, false);


            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
//            progressBar.setVisibility(View.VISIBLE);

            // updating progress bar value
            progressBar.setProgress(progress[0]);


            BaseClass.showToast(getApplicationContext(), progress[0] + "%");


            // updating percentage value
//            txtPercentage.setText(String.valueOf(progress[0]) + "%");


            //code to show progress in notification bar
//            FileUploadNotification fileUploadNotification = new FileUploadNotification(UploadActivity.this);
//            fileUploadNotification.updateNotification(String.valueOf(progress[0]), "Image 123.jpg", "Camera Upload");

        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://webdevelopmentreviews.net/imcooking/api/upload_video");

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });


                // Un-comment below 2 lines to compress image
                // ImageCompressionUtils class will compress image size from 2mb to 300 kb
                // for more small images just change the below line in ImageCompressionUtils
                //             scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                // change compression from 100 to 0 as per your requirements
                // comment below 2 lines to remove  image compression

//                ImageCompressionUtils imageCompressionUtils = new ImageCompressionUtils(UploadActivity.this);
//                imageCompressionUtils.compressImage(filePath);

                File sourceFile = new File(filePath);
                // Adding file data to http body
                entity.addPart("myFile", new FileBody(sourceFile));
                entity.addPart("chef_id", new StringBody(chef_id));
                entity.addPart("dish_id", new StringBody(dish_id));

                // Extra parameters if you want to pass to server
                //entity.addPart("website", new StringBody("https://androidluckyguys.wordpress.com"));
                //entity.addPart("email", new StringBody("luckyrana321@gmail.com"));


                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("TAG", "Response from server: " + result);
            // showing the server response in an alert dialog
            showAlert(result);

            super.onPostExecute(result);
        }
    }

    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setTitle("Response from Servers")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // do nothing
                    }
                });
        progressBar.setVisibility(View.GONE);

        AlertDialog alert = builder.create();
        alert.show();
    }
}