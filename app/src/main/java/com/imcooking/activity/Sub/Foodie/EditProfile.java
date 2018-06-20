package com.imcooking.activity.Sub.Foodie;

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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.R;
import com.imcooking.activity.Sub.Chef.ChefEditDish;
import com.imcooking.activity.Sub.Chef.ChefEditProfile;
import com.imcooking.fragment.foodie.ProfileFragment;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import okhttp3.internal.Util;

public class EditProfile extends AppBaseActivity {
    private ImageView imgProfile;
    private JSONObject jsonData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
        }

        init();
        getUserData();
    }

    private TextView tv_fullname, tv_email, tv_phone, btn;
    private EditText edt_full_name, edt_email, edt_phone;
    private String str_id, str_uname;
    private ProgressBar progressBar;

    private void init(){

        tv_fullname = findViewById(R.id.foodie_edit_profile_full_name);
        tv_email = findViewById(R.id.foodie_edit_profile_email);
        tv_phone = findViewById(R.id.foodie_edit_profile_phone);
        imgProfile = findViewById(R.id.foodie_edit_profile_image);
        progressBar = findViewById(R.id.profile_edit_img_progress);
        edt_full_name = findViewById(R.id.foodie_edit_profile_full_name_edit);
        edt_phone = findViewById(R.id.foodie_edit_profile_phone_edit);
        edt_email = findViewById(R.id.foodie_edit_profile_email_edit);
    }

    private void getUserData(){

        TinyDB tinyDB = new TinyDB(getApplicationContext());
        progressBar.setVisibility(View.VISIBLE);
        String login_data = tinyDB.getString("login_data");
        Log.d("LoginData", login_data);
        ApiResponse.UserDataBean userDataBean = new ApiResponse.UserDataBean();
        userDataBean = new Gson().fromJson(login_data, ApiResponse.UserDataBean.class);

        String str_full_name, str_phone, str_email, dp;

        str_full_name = userDataBean.getFull_name() + "";
        str_phone = userDataBean.getUser_phone() + "";
        str_email = userDataBean.getUser_email() + "";
        str_id = userDataBean.getUser_id() + "";
        str_uname = userDataBean.getUser_name();
        tv_fullname.setText(str_full_name);
        if(str_full_name.equals("null")){
            tv_fullname.setText("Your Name");
        } else{
            tv_fullname.setText(str_full_name);
        }

        tv_phone.setText(str_phone);
        if(userDataBean.getUser_phone() == null){
            tv_phone.setText("XXXXXXXXX");
        } else{
            tv_phone.setText(str_phone);
        }

        tv_email.setText(str_email);
        edt_email.setText(str_email);
        if (str_full_name!=null&&!str_full_name.equalsIgnoreCase("null")){
            edt_full_name.setText(str_full_name);
        }
        if (str_phone!=null&&!str_phone.equalsIgnoreCase("null")){
            edt_phone.setText(str_phone);
        }
        getUserProfile(str_id);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
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
    JSONObject jsonObject;

    public void foodie_profile_update(View view){

        final String name = edt_full_name.getText().toString().trim();
        final String email = edt_email.getText().toString().trim();
        final String phone = edt_phone.getText().toString().trim();

        if(!name.isEmpty()) {
            if(!phone.isEmpty()){

                String s = "{\"user_type\":\"2\",\"foodie_id\":\"" + str_id + "\",\"name\":\"" + name + "\",\"email\":\"" +
                        email + "\", \"phone\":\"" + phone + "\"}";
                Log.d("MyRequest", s);

                try {
                    jsonData = new JSONObject(s);

                        new GetData(getApplicationContext()).sendMyData(jsonData, "foodieprofileedit",
                            EditProfile.this, new GetData.MyCallback() {
                                @Override
                                public void onSuccess(String result) {

                                    try {
                                        JSONObject jsonObject = new JSONObject(result);
                                        if(jsonObject.getBoolean("status")){

                                            BaseClass.showToast(getApplicationContext(), "Profile Updated Successfully");
                                            tv_phone.setText(phone);
                                            tv_fullname.setText(name);

                                            new TinyDB(getApplicationContext()).remove("login_data");

                                            ApiResponse.UserDataBean userData = new ApiResponse.UserDataBean();

                                            userData.setUser_type("2");
                                            userData.setFull_name(name);
                                            userData.setUser_email(email);
                                            userData.setUser_id(Integer.parseInt(str_id));
                                            userData.setUser_name(str_uname);
                                            userData.setUser_phone(phone);

                                            new TinyDB(getApplicationContext()).putString("login_data", new Gson().toJson(userData));
                                            String s = new TinyDB(getApplicationContext()).getString("login_data");
                                            Log.d(TAG, "Rakhi: "+s);
//                                            Toast.makeText(EditProfile.this, ""+s, Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else{
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
            } else{
                BaseClass.showToast(getApplicationContext(), "Please Enter Phone Number");
            }
        } else{
            BaseClass.showToast(getApplicationContext(), "Please Enter Your Full Name");
        }
    }

    public void change_dp(View view){
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EditProfile.this,
                    new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CAMERA);
        } else {
            Log.e("DB", "PERMISSION GRANTED");
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
                bm = MediaStore.Images.Media.getBitmap(EditProfile.this.getApplicationContext().getContentResolver(), data.getData());
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

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(EditProfile.this);
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
                        ActivityCompat.requestPermissions(EditProfile.this,
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
                        ActivityCompat.requestPermissions(EditProfile.this,
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

        }
    }

    private void uploadProfile(String base64){
        String request = "{\"user_id\":" + str_id + ",\"image\":\"" + base64 + "\"}";
        try {
            JSONObject jsonObject = new JSONObject(request);
            new GetData(getApplicationContext(), EditProfile.this).sendMyData(jsonObject, GetData.PROFILE_IMAGE, EditProfile.this, new GetData.MyCallback() {
                @Override
                public void onSuccess(String result) {
                    Log.d("TAG", "Rakhi: "+result);

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    String TAG = EditProfile.class.getName();

    private void getUserProfile(String str_id){
        String request = "{\"user_id\":" + str_id + "\"}";
        try {
            JSONObject jsonObject = new JSONObject(request);
            new GetData(getApplicationContext(), EditProfile.this).sendMyData(jsonObject, GetData.GETPROFILE_PIC, EditProfile.this, new GetData.MyCallback() {
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
            bitmapString = BaseClass.BitMapToString(result);
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
