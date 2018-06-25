package com.imcooking.activity.Sub.Chef;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.gson.Gson;
import com.imcooking.Model.ApiRequest.ModelChefAddDish;
import com.imcooking.Model.ApiRequest.ModelChefEditDish;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.CuisineData;
import com.imcooking.R;
import com.imcooking.adapters.AdapterEditDishPhotos;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static java.util.Calendar.HOUR_OF_DAY;

public class ChefEditDish extends AppBaseActivity implements CompoundButton.OnCheckedChangeListener,
        AdapterView.OnItemSelectedListener, AdapterEditDishPhotos.browse_photo {

    private String chef_id, dish_id, name, cuisine, price, description, special_note,qyt, available, homedelivery, pickup;
    private EditText edt_name, edt_price, edt_description, edt_special_note, edt_qyt;
    private SwitchCompat switch_1, switch_2, switch_3;
    private String sw_1 = "Yes", sw_2 = "Yes", sw_3 = "Yes";
    private String dish_miles = "10";
    private Spinner sp;
    private SeekBar seekBar;
    private final int  REQUEST_CAMERA=0, SELECT_FILE = 1,REQUEST_TAKE_GALLERY_VIDEO=2;
    Bitmap bitmap;
    boolean result;
    private String userChoosenTask;

    private TextView tv_title;
    private TextView tv_add_more_photo, txt_video;

    private String bitmapString="a";
    private ArrayList<String> imgBase64List;
    private String title;
    private VideoView videoView;
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

        getMyCuisines();
        init();
        getMyIntentData();
    }

    private TextView tv_photo_name;
    private RecyclerView photorv1;
    private ArrayList<String> arr_photos = new ArrayList<>();
    private int pos;
    private Spinner sp_cuisine;
    private List<CuisineData.CuisineDataBean> cuisineList=new ArrayList<>();
    private CuisineData cuisineData = new CuisineData();
    private String selected_cuisine, selected_cuisine_id;

    private ArrayList<String> arrayList = new ArrayList<>();

    private void init(){
        videoView = findViewById(R.id.vvVideo);
    //    layout_photos = findViewById(R.id.edit_dish_photos);
        txt_video = findViewById(R.id.chef_edit_dish_photos_ttx_select_video);
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
        switch_3 = findViewById(R.id.edit_dish_switch_current_available);
        seekBar = findViewById(R.id.activity_chef_edit_dish_time);
        Calendar c= Calendar.getInstance();
        int hour = c.get(HOUR_OF_DAY);

//        seekBar.setMax(24 * 4); //24 hours and 4 step in one hour.

/*
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                int hours = progress / 4; // it will return hours.
                int minutes = (progress % 4) * 15; // here will be minutes.
                Toast.makeText(getApplicationContext(),"seekbar progress: "+hours + "\n"+minutes, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(),"seekbar touch started!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(),"seekbar touch stopped!", Toast.LENGTH_SHORT).show();
            }
        });
*/


        switch_1.setOnCheckedChangeListener(this);
        switch_2.setOnCheckedChangeListener(this);
        switch_3.setOnCheckedChangeListener(this);
        txt_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Video"),REQUEST_TAKE_GALLERY_VIDEO);
            }
        });

        ArrayList<String> spinnerData =new ArrayList<>();
        spinnerData.add("10 miles ");
        spinnerData.add("20 miles ");
        spinnerData.add("30 miles ");
        spinnerData.add("50 miles ");

        sp = findViewById(R.id.edit_dish_spinner);
        sp.setOnItemSelectedListener(this);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,
                R.layout.spinner_row, spinnerData);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_row);
        sp.setAdapter(arrayAdapter);

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
        arr_photos.add("Photo");

        setMyAdapter(arr_photos);
    }

    private void getMyIntentData() {
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
            arrayList = getIntent().getExtras().getStringArrayList("image");
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
            title = "editdish";
            tv_title.setText("Edit Dish");

        } else{
            title = "dish";
            tv_title.setText("Add Dish");
        }

        TinyDB tinyDB = new TinyDB(getApplicationContext());

        String login_data = tinyDB.getString("login_data");
        ApiResponse.UserDataBean userDataBean = new ApiResponse.UserDataBean();
        userDataBean = new Gson().fromJson(login_data, ApiResponse.UserDataBean.class);

        chef_id = userDataBean.getUser_id() + "";
    }

    private void getMyCuisines() {

        try {
            String s = "";
            JSONObject jsonObject = new JSONObject("{}");

            layout.setVisibility(View.GONE);
            new GetData(getApplicationContext(), ChefEditDish.this).sendMyData(jsonObject, "cuisine",
                    ChefEditDish.this, new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            layout.setVisibility(View.VISIBLE);
                            cuisineData = new Gson().fromJson(result, CuisineData.class);
//                            cuisineList.addAll(cuisineData.getCuisine_data());

                            setMyCuisines(cuisineData);
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void setMyCuisines(CuisineData cuisines){

        ArrayList<String> arrayList = new ArrayList<>();
        for(int i=0; i<cuisines.getCuisine_data().size(); i++){
            arrayList.add(cuisines.getCuisine_data().get(i).getCuisine_name());
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
                        if(!bitmapString.equals("a")) {
                            if(title.equals("dish")){
                                adddish(title);
                            } else if(title.equals("editdish")){
                                editdish(title);
                            }
                        } else{
                            BaseClass.showToast(getApplicationContext() , "Please Select a Photo");
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

        ArrayList<String> arrayList = new ArrayList<>( Arrays.asList(bitmapString));

        ModelChefAddDish requestData = new ModelChefAddDish();
        requestData.setUser_id(chef_id);
        requestData.setDish_name(name);
        requestData.setCuisine(selected_cuisine_id);
        requestData.setSubcuisine("15");
        requestData.setDescription(description);
        requestData.setSpecial_note(special_note);
        requestData.setDish_price(price);
        requestData.setDish_from("12:10 AM");
        requestData.setDish_to("4:10 AM");
        requestData.setAvailable(sw_1);
        requestData.setHomedeliver(sw_2);
        requestData.setPickup(sw_3);
        requestData.setDeliverymiles(dish_miles);
        requestData.setDish_video("abc");
        requestData.setDish_image(arr_photos);
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
                                            if(job.getString("message").equals("Add dish Successfully")){
                                                BaseClass.showToast(getApplicationContext(), "Dish Added Successfully" );
                                                finish();
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

    private void editdish(String title){

        ArrayList<String> arrayList = new ArrayList<>( Arrays.asList(bitmapString));
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
        requestData.setDish_from("12:10 AM");
        requestData.setDish_to("4:10 AM");
        requestData.setAvailable(sw_1);
        requestData.setHomedeliver(sw_2);
        requestData.setPickup(sw_3);
        requestData.setDeliverymiles(dish_miles);
        requestData.setDish_video("abc");
        requestData.setDish_image(arrayList);
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

    public void add_more_photos(View view){

        arr_photos.add("Photo");
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
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ) {
                        ActivityCompat.requestPermissions(ChefEditDish.this,
                                new String[]{Manifest.permission.CAMERA},
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
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
            else if (requestCode == REQUEST_TAKE_GALLERY_VIDEO){
                System.out.println("SELECT_VIDEO");
                Uri selectedImageUri = data.getData();
                String selectedPath = getPath(selectedImageUri);
                Log.d("TAG", "rakhi: "+selectedPath.substring(selectedPath.lastIndexOf("/") + 1));

//                upload video

             //   int reponse=upLoad2Server(""+selectedPath);

                txt_video.setText(selectedPath.substring(selectedPath.lastIndexOf("/") + 1));
                videoView.setVideoURI(selectedImageUri);
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.setLooping(true);
                        videoView.start();
                    }
                });
            }
        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
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

/*                if(arr_photos.size() == 1){
                    arr_photos.clear();
                }*/

//                arr_photos.remove(arr_photos.size()-1);
//                arr_photos.add(filePath.substring(filePath.lastIndexOf("/") + 1));
                arr_photos.set(pos, filePath.substring(filePath.lastIndexOf("/") + 1));
                adapterEditDishPhotos.notifyDataSetChanged();
//                setMyAdapter(arr_photos);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        bitmap = bm;
        bitmapString = BaseClass.BitMapToString(bitmap);
        imgBase64List.add(bitmapString);

    }

    private void onCaptureImageResult(Intent data) {
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bitmap = thumbnail;

        Uri tempUri = getImageUri(getApplicationContext(), bitmap);

        // CALL THIS METHOD TO GET THE ACTUAL PATH
        File finalFile = new File(getRealPathFromURI(tempUri));

        String filePath = finalFile + "";
        Log.d("MyImagePath", filePath.substring(filePath.lastIndexOf("/") + 1));


/*                if(arr_photos.size() == 1){
                    arr_photos.clear();
                }*/
//                arr_photos.remove(arr_photos.size()-1);
//                arr_photos.add(filePath.substring(filePath.lastIndexOf("/") + 1));
        arr_photos.set(pos, filePath.substring(filePath.lastIndexOf("/") + 1));
        adapterEditDishPhotos.notifyDataSetChanged();
//                setMyAdapter(arr_photos);

//        imgUser.setImageBitmap(thumbnail);
//        uploadImg(bitmap);
        bitmapString = BaseClass.BitMapToString(bitmap);
        imgBase64List.add(bitmapString);
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


        if (adapterView.getId() == R.id.edit_dish_spinner) {
            dish_miles = adapterView.getItemAtPosition(i).toString().replace("miles","");
            Log.d("MySpinner", dish_miles);
        } else if(adapterView.getId() == R.id.chef_edit_dish_spinner_cuisine){
            selected_cuisine = adapterView.getItemAtPosition(i).toString();
            selected_cuisine_id = cuisineData.getCuisine_data().get(i).getCuisine_id() + "";
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
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ChefEditDish.this,
                    new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CAMERA);
        } else {
            Log.e("DB", "PERMISSION GRANTED");
            result = true;
        }
        selectImage();

    }

    private static int serverResponseCode;

    public static int upLoad2Server(String sourceFileUri) {
        String upLoadServerUri = "your remote server link";
        // String [] string = sourceFileUri;
        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        DataInputStream inStream = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        String responseFromServer = "";

        File sourceFile = new File(sourceFileUri);
        if (!sourceFile.isFile()) {
            Log.e("Huzza", "Source File Does not exist");
            return 0;
        }
        try { // open a URL connection to the Servlet
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            URL url = new URL(upLoadServerUri);
            conn = (HttpURLConnection) url.openConnection(); // Open a HTTP  connection to  the URL
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("uploaded_file", fileName);
            dos = new DataOutputStream(conn.getOutputStream());

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""+ fileName + "\"" + lineEnd);
            dos.writeBytes(lineEnd);

            bytesAvailable = fileInputStream.available(); // create a buffer of  maximum size
            Log.i("Huzza", "Initial .available : " + bytesAvailable);

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // Responses from the server (code and message)
            serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn.getResponseMessage();

            Log.i("Upload file to server", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);
            // close streams
            Log.i("Upload file to server", fileName + " File is written");
            fileInputStream.close();
            dos.flush();
            dos.close();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
        } catch (Exception e) {
            e.printStackTrace();
        }
//this block will give the response of upload link
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn
                    .getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                Log.i("Huzza", "RES Message: " + line);
            }
            rd.close();
        } catch (IOException ioex) {
            Log.e("Huzza", "error: " + ioex.getMessage(), ioex);
        }
        return serverResponseCode;  // like 200 (Ok)

    } // end upLoad2Server

}
