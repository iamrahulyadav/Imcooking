package com.imcooking.activity.Sub.Chef;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.imcooking.Model.ApiRequest.ModelChefEditProfile;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.Model.api.response.ChefProfileData1;
import com.imcooking.Model.api.response.CuisineData;
import com.imcooking.R;
import com.imcooking.activity.Sub.Foodie.EditProfile;
import com.imcooking.adapters.AdapterCuisineList;
import com.imcooking.fragment.chef.ChefHome;
import com.imcooking.fragment.foodie.ProfileFragment;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


public class ChefEditProfile extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        CompoundButton.OnCheckedChangeListener, AdapterCuisineList.click_adapter_cuisine_list {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_edit_profile);

        init();
        getProfileData();
    }

    public static CuisineData cuisineData = new CuisineData();
//    private RecyclerView cuisineRecycler;
    private RatingBar ratingBar;
    private EditText edt_name, edt_address, edt_city, edt_email, edt_zipcoede, edt_about, edt_phn;
    private Spinner sp_miles, sp_cuisine;
    private SwitchCompat /*sw_notification,*/ sw_available;
    private String str_id, str_name, str_address, str_city, str_email, str_zipcode, str_miles,
            str_cuisine = "Indian Food"/*, str_notification*/,
                    str_available = "0", str_about, str_phn;
    private TextView txt_name, txt_address, txt_phone;
    private TinyDB tinyDB;
    private ImageView imgProfile;
    private ApiResponse.UserDataBean userDataBean;
    private ProgressBar progressBar;

    private void init(){

        tinyDB = new TinyDB(getApplicationContext());
        userDataBean = new ApiResponse.UserDataBean();
        ratingBar = findViewById(R.id.activity_chef_edit_profile_rating);
        edt_phn = findViewById(R.id.chef_edit_profile_phone);
        imgProfile = findViewById(R.id.other_dish_profile_image);
        progressBar = findViewById(R.id.actvity_chef_edit_progress);
        edt_name = findViewById(R.id.chef_edit_profile_name);
        edt_address = findViewById(R.id.chef_edit_profile_address);
        edt_city = findViewById(R.id.chef_edit_profile_city);
        edt_email = findViewById(R.id.chef_edit_profile_email);
        edt_zipcoede = findViewById(R.id.chef_edit_profile_zipcode);
        edt_about = findViewById(R.id.chef_edit_profile_About);
        txt_name = findViewById(R.id.activity_chef_edit_txtName);
        txt_address = findViewById(R.id.activity_chef_edit_txtAddress);
        txt_phone = findViewById(R.id.activity_chef_edit_txtPhone);
        sp_miles = findViewById(R.id.chef_edit_profile_spinner_miles);
        sp_cuisine = findViewById(R.id.chef_edit_profile_spinner_select_cuisines);
//        cuisineRecycler = findViewById(R.id.chef_edit_profile_recycler_cuisine);
        sp_miles.setOnItemSelectedListener(this);
        sp_cuisine.setOnItemSelectedListener(this);
//        LinearLayoutManager horizontalLayoutManagaer
//                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
//        cuisineRecycler.setLayoutManager(horizontalLayoutManagaer);
//        sw_notification = findViewById(R.id.chef_edit_profile_notification);
        sw_available = findViewById(R.id.chef_edit_profile_available);

//        sw_notification.setOnCheckedChangeListener(this);
        sw_available.setOnCheckedChangeListener(this);

        ArrayList<String> spinnerData =new ArrayList<>();
        spinnerData.add("10 miles ");
        spinnerData.add("20 miles ");
        spinnerData.add("30 miles ");
        spinnerData.add("50 miles ");

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,
                R.layout.spinner_row, spinnerData);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_row);
        sp_miles.setAdapter(arrayAdapter);
        sp_cuisine.setAdapter(arrayAdapter);

        String login_data = tinyDB.getString("login_data");

        userDataBean = new Gson().fromJson(login_data, ApiResponse.UserDataBean.class);

        str_id = userDataBean.getUser_id() + "";

        getMyCuisines();
    }

    private ArrayList<String> arrayList = new ArrayList<>();
    private AdapterCuisineList adapter;

    private List<CuisineData.CuisineDataBean> cuisionList=new ArrayList<>();

    private void setMyAdapter(CuisineData cuisineData){

/*        arrayList.clear();
        for (int i=0; i<cuisineData.getCuisine_data().size(); i++){
            arrayList.add("0");
        }

        adapter = new AdapterCuisineList(getApplicationContext(), cuisineData, arrayList, this);
        cuisineRecycler.setAdapter(adapter);*/
    }

    private void getProfileData(){
        ChefProfileData1 chefProfileData1 = new ChefProfileData1();
        chefProfileData1 = ChefHome.chefProfileData1;
        str_phn = chefProfileData1.getChef_data().getChef_phone();
        str_id = chefProfileData1.getChef_data().getChef_id() + "";
        str_name = chefProfileData1.getChef_data().getChef_full_name() + "";
        str_address = chefProfileData1.getChef_data().getAddress() + "";
        str_city = chefProfileData1.getChef_data().getChef_city() + "";
        str_email = chefProfileData1.getChef_data().getChef_email() + "";
        str_zipcode = chefProfileData1.getChef_data().getChef_zipcode();
        str_about = chefProfileData1.getChef_data().getChef_description();

        edt_email.setText(str_email);
        if (str_name!=null && !str_name.equalsIgnoreCase("null")){
            edt_name.setText(str_name);
            txt_name.setText(str_name);
        } else {
            txt_name.setText("Your Name");
        }
        if (str_city!=null&&!str_city.equalsIgnoreCase("null")){
            edt_city.setText(str_city);
        }
        if (str_address!=null&&!str_address.equalsIgnoreCase("null")){
            edt_address.setText(str_address);
            txt_address.setText(str_address);
        } else {
            txt_address.setText("Address");
        }
        if (str_zipcode!=null){
            edt_zipcoede.setText(str_zipcode);
        }
        if(str_about != null){
            edt_about.setText(str_about);
        }
        if (str_phn!=null){
            edt_phn.setText(str_phn);
        }

        if (chefProfileData1.getChef_data().getRating()!=null){
            ratingBar.setRating(Float.parseFloat(chefProfileData1.getChef_data().getRating()));
        }
        progressBar.setVisibility(View.VISIBLE);
        getUserProfile(str_id);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        int id = adapterView.getId();
        if(id == R.id.chef_edit_profile_spinner_miles){

            str_miles = adapterView.getItemAtPosition(i).toString();

        } else if (id == R.id.chef_edit_profile_spinner_select_cuisines){

            str_cuisine = adapterView.getItemAtPosition(i).toString();

        } else {}
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        int id = compoundButton.getId();
       /* if(id == R.id.chef_edit_profile_notification){
            if(b) str_notification = "1";
            else  str_notification = "0";

        } else */
       if(id == R.id.chef_edit_profile_available){
            if(b) str_available = "1";
            else  str_available = "0";
        } else {}
    }

    public void select_cuisines(View view){

        startActivityForResult(new Intent(ChefEditProfile.this, SelectCuisines.class), 143);
        overridePendingTransition(R.anim.enter, R.anim.exit);

    }

    public void chef_edit_profile_save(View view){

//        str_id
        str_name = edt_name.getText().toString().trim();
        str_address = edt_address.getText().toString().trim();
        str_city = edt_city.getText().toString();
        str_email = edt_email.getText().toString().trim();
        str_zipcode = edt_zipcoede.getText().toString().trim();
        str_about = edt_about.getText().toString().trim();
        str_phn = edt_phn.getText().toString().trim();
//        str_miles , str_notification, str_available, str_cuisine

        ArrayList<String> cuisine_list = new ArrayList<>();
        cuisine_list.add(str_cuisine);

        ModelChefEditProfile data = new ModelChefEditProfile();

        data.setChef_id(str_id);
        data.setFullname(str_name);
        data.setAddress(str_address);
        data.setCity(str_city);
        data.setEmail(str_email);
        data.setZipcode(str_zipcode);
        data.setDefault_miles(str_miles);
        data.setAvailable(str_available);
        data.setCuisine_list(str_cuisine);
        data.setAbout(str_about);
        data.setPhone(str_phn);

        if(!str_name.isEmpty()) {
            if(!str_address.isEmpty()){
                if(!str_city.isEmpty()){
                        if(!str_zipcode.isEmpty()){
                            if(!str_about.isEmpty()) {
                                if (( android.util.Patterns.PHONE.matcher(str_phn).matches())){
                                try {
                                    JSONObject jsonObject = new JSONObject(new Gson().toJson(data));

                                    Log.d("MyRequest", jsonObject.toString());
                                    new GetData(getApplicationContext()).sendMyData(jsonObject, GetData.CHEF_PROFILE_UPDATE,
                                            ChefEditProfile.this, new GetData.MyCallback() {
                                                @Override
                                                public void onSuccess(String result) {
                                                    try {
                                                        JSONObject job = new JSONObject(result);
                                                        if (job.getBoolean("status")) {
                                                            if (job.getString("msg").equals("chef profile update successfully")) {
                                                                BaseClass.showToast(getApplicationContext(), "Profile Updated Successfully");
                                                                finish();
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
                                } else {
                                    BaseClass.showToast(getApplicationContext(), "Please enter valid phone number");
                                }
                            } else{
                                BaseClass.showToast(getApplicationContext(), "Please enter your description");
                            }
                        }  else{
                            BaseClass.showToast(getApplicationContext(), "All Fields Required");
                        }
                    }  else{
                        BaseClass.showToast(getApplicationContext(), "All Fields Required");
                    }
            }  else{
                BaseClass.showToast(getApplicationContext(), "All Fields Required");
            }
        }  else{
            BaseClass.showToast(getApplicationContext(), "All Fields Required");
        }
    }

    public void chef_edit_profile_cancel(View view){

    }

    private void getMyCuisines() {

        try {
            String s = "";
            JSONObject jsonObject = new JSONObject("{}");

            new GetData(getApplicationContext(), ChefEditProfile.this).sendMyData(jsonObject, GetData.CUISINE,
                    ChefEditProfile.this, new GetData.MyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            cuisineData = new Gson().fromJson(result, CuisineData.class);
//                            cuisineList.addAll(cuisineData.getCuisine_data());

                            setMyCuisines(cuisineData);
//                            setMyAdapter(cuisineData);
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

    public void change_dp(View view){
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ChefEditProfile.this,
                    new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CAMERA);
        } else {
            result = true;
        }
        selectImage();
    }

    Bitmap bitmap;
    private String bitmapString="a",userChoosenTask;
    private boolean result;


    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(ChefEditProfile.this.getApplicationContext().getContentResolver(), data.getData());
                Uri tempUri = getImageUri(getApplicationContext(), bm);

                // CALL THIS METHOD TO GET THE ACTUAL PATH
                File finalFile = new File(getRealPathFromURI(tempUri));

                System.out.println(finalFile + "");

                String filePath = finalFile + "";
                Log.d("MyImagePath", filePath.substring(filePath.lastIndexOf("/") + 1));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        bitmap = bm;
        imgProfile.setImageBitmap(bm);
        bitmapString = BaseClass.BitMapToString(bitmap);
        uploadProfile(bitmapString);
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

        System.out.println(finalFile + "");

        String filePath = finalFile + "";
        Log.d("MyImagePath", filePath.substring(filePath.lastIndexOf("/") + 1));

        bitmapString = BaseClass.BitMapToString(bitmap);
        imgProfile.setImageBitmap(bitmap) ;
        uploadProfile(bitmapString);

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
    private final int  REQUEST_CAMERA=0, SELECT_FILE = 1;


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
//                        cameraIntent();
                    }
                }
                break;
            case SELECT_FILE:
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(getApplicationContext(), "file Permission not granted", Toast.LENGTH_SHORT).show();
                } else {
                    result =true;
                    if(result){
//                        galleryIntent();
                    }
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ChefEditProfile.this);
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
                        ActivityCompat.requestPermissions(ChefEditProfile.this,
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
                        ActivityCompat.requestPermissions(ChefEditProfile.this,
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
            else if(requestCode == 143){
                Toast.makeText(this, "yeah", Toast.LENGTH_SHORT).show();
            } else {}

        }
    }

    private void uploadProfile(String base64){
        String request = "{\"user_id\":" + str_id + ",\"image\":\"" + base64 + "\"}";
        try {
            JSONObject jsonObject = new JSONObject(request);
            new GetData(getApplicationContext(), ChefEditProfile.this).sendMyData(jsonObject, GetData.PROFILE_IMAGE, ChefEditProfile.this, new GetData.MyCallback() {
                @Override
                public void onSuccess(String result) {
                    Log.d("TAG", "Rakhi: "+result);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getUserProfile(String str_id){
        String request = "{\"user_id\":" + str_id + "\"}";
        try {
            JSONObject jsonObject = new JSONObject(request);
            new GetData(getApplicationContext(), ChefEditProfile.this).sendMyData(jsonObject,
                    GetData.GETPROFILE_PIC, ChefEditProfile.this, new GetData.MyCallback() {
                @Override
                public void onSuccess(String result) {
                    Log.d("TAG", "Rakhi: "+result);
                    if (result!=null){
                        try {
                            JSONObject jsonObject1 = new JSONObject(result);
                            if (jsonObject1.getBoolean("status")){
                                JSONObject imgObj = jsonObject1.getJSONObject("user_profile_image");
                                String user_image =  imgObj.getString("user_image");
                                if (user_image!=null && !user_image.equalsIgnoreCase("null")){
                                    String url = GetData.IMG_BASE_URL + user_image;
                                    GetImage task = new GetImage();
                                    // Execute the task
                                    task.execute(new String[] { url });
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    imgProfile.setImageResource(R.drawable.details_profile);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void click_adapter_cuisine_list_m(int position) {

    }

    public class GetImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;
            for (String url : urls) {
                map = downloadImage(url);
            }
            return map;
        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
            progressBar.setVisibility(View.GONE);
            imgProfile.setImageBitmap(result);
        }

        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.
                        decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }

        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }
    }

}
