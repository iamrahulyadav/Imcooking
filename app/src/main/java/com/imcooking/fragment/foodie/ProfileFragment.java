package com.imcooking.fragment.foodie;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.R;
import com.imcooking.activity.Sub.Foodie.ChefILove;
import com.imcooking.activity.Sub.Foodie.EditProfile;
import com.imcooking.activity.Sub.Foodie.FavoriteCusine;
import com.imcooking.activity.Sub.Foodie.Help;
import com.imcooking.activity.Sub.Foodie.ManageAddress;
import com.imcooking.activity.Sub.Foodie.PaymentMethod;
import com.imcooking.activity.Sub.Foodie.Setting;
import com.imcooking.activity.home.MainActivity;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        init();
    }

    private TextView tv_fullname, tv_phone_email;
    private TextView tv_manage_address, tv_chef_i_love, tv_payment_method, tv_favorite_cuisine, tv_setting, tv_help, tv_edit;
    private String str_fullname, str_phone_and_email, userid;
    private TinyDB tinyDB;
    private ImageView imgProfile;
    private ProgressBar progressBar;

    private void init(){

        tinyDB = new TinyDB(getContext());
        imgProfile = getView().findViewById(R.id.foodie_profile_image);
        tv_fullname = getView().findViewById(R.id.foodie_full_name);
        tv_phone_email = getView().findViewById(R.id.foodie_phone_and_email);
        progressBar = getView().findViewById(R.id.profile_img_progress);
        tv_manage_address = getView().findViewById(R.id.profile_edit_manage_address);
        tv_chef_i_love = getView().findViewById(R.id.profile_edit_chef_i_love);
        tv_payment_method = getView().findViewById(R.id.profile_edit_payment_method);
        tv_favorite_cuisine = getView().findViewById(R.id.profile_edit_favorite_cuisine);
        tv_setting = getView().findViewById(R.id.profile_edit_setting);
        tv_help = getView().findViewById(R.id.profile_edit_help);
        tv_edit = getView().findViewById(R.id.profile_edit);

        tv_manage_address.setOnClickListener(this);
        tv_chef_i_love.setOnClickListener(this);
        tv_payment_method.setOnClickListener(this);
        tv_favorite_cuisine.setOnClickListener(this);
        tv_setting.setOnClickListener(this);
        tv_help.setOnClickListener(this);
        tv_edit.setOnClickListener(this);
    }

    private void getUserData(){
        String login_data = tinyDB.getString("login_data");
        ApiResponse.UserDataBean userDataBean = new ApiResponse.UserDataBean();
        userDataBean = new Gson().fromJson(login_data, ApiResponse.UserDataBean.class);
        progressBar.setVisibility(View.VISIBLE);

        str_fullname = userDataBean.getFull_name() + "";
        userid = userDataBean.getUser_id()+"";
        str_phone_and_email = userDataBean.getUser_phone() + "   " + userDataBean.getUser_email();

        tv_fullname.setText(str_fullname);
        tv_phone_email.setText(str_phone_and_email);

        if(str_fullname.equals("null")){
            tv_fullname.setText("Your Name");
        }
        if(userDataBean.getUser_phone() == null){
            tv_phone_email.setText("9999999999   " + userDataBean.getUser_email());
        }

        getUserProfile(userid);

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if(v.getId() == R.id.profile_edit_manage_address){
            startActivity(new Intent(getContext(), ManageAddress.class));
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

        } else if(v.getId() == R.id.profile_edit_chef_i_love){
            startActivity(new Intent(getContext(), ChefILove.class));
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

        } else if(v.getId() == R.id.profile_edit_payment_method){
            startActivity(new Intent(getContext(), PaymentMethod.class));
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

        } else if(v.getId() == R.id.profile_edit_favorite_cuisine){
            startActivity(new Intent(getContext(), FavoriteCusine.class));
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

        } else if(v.getId() == R.id.profile_edit_setting){
            startActivity(new Intent(getContext(), Setting.class));
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

        } else if(v.getId() == R.id.profile_edit_help){
            startActivity(new Intent(getContext(), Help.class));
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

        } else if(v.getId() == R.id.profile_edit){
            startActivity(new Intent(getContext(), EditProfile.class));
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

        } else{

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity()).setBottomColor();
        ((MainActivity) getActivity()).tv_profile.setTextColor(getResources().getColor(R.color.theme_color));
        ((MainActivity) getActivity()).iv_profile.setImageResource(R.drawable.ic_user_name_1);

        getUserData();


    }


    private void getUserProfile(String str_id){
        String request = "{\"user_id\":" + str_id + "\"}";
        try {
            JSONObject jsonObject = new JSONObject(request);
            new GetData(getContext(), getActivity()).sendMyData(jsonObject, GetData.GETPROFILE_PIC, getActivity(), new GetData.MyCallback() {
                @Override
                public void onSuccess(String result) {
                    Log.d("TAG", "Rakhi: "+result);
                    if (result!=null){
                        try {
                            JSONObject jsonObject1 = new JSONObject(result);
                            if (jsonObject1.getBoolean("status")){
                                JSONObject imgObj = jsonObject1.getJSONObject("user_profile_image");
                                String url = GetData.IMG_BASE_URL + imgObj.getString("user_image");

                                Log.d("ProfileImage", url);

                                GetImage task = new GetImage();
                                // Execute the task
                                task.execute(new String[] { url });
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
