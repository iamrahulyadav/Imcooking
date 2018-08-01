package com.imcooking.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.R;
import com.imcooking.fragment.chef.ChefHome;
import com.imcooking.fragment.foodie.HomeFragment;
import com.mukesh.tinydb.TinyDB;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class BaseClass {

    public static Boolean checkemail(final String emai) {
        if(emai!=null)
        {
            Pattern pattern = Patterns.EMAIL_ADDRESS;
            if(pattern.matcher(emai).matches())
            {
                return pattern.matcher(emai).matches();
            }
        }
        return false;
    }

    public static BitmapDescriptor bitmapDescriptorFromVectorR(Context context) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_placeholder);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        //  vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private static Toast t;
    public static void showToast(Context context, String msg){
        if(t != null)
            t.cancel();
        t = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        t.show();
    }

    public static void setLightStatusBar(View view, Activity activity){


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            activity.getWindow().setStatusBarColor(Color.WHITE);
        }
    }


    public static void callFragment(Fragment fragment, String tag, FragmentManager manager  ){

        if(manager.getBackStackEntryCount() != 0) {
            String tag1 = manager.getBackStackEntryAt(manager.getBackStackEntryCount() - 1).getName();
            if (tag1.equals(tag)) {

            } else {

//            Toast.makeText(context, (manager.findFragmentByTag(tag) + ""), Toast.LENGTH_SHORT).show();
                    if (manager.findFragmentByTag(tag) == null) { //fragment not in back stack, create it.
                        manager.beginTransaction().replace(R.id.frame, fragment).addToBackStack(tag).commit();

//                manager.executePendingTransactions();
                    } else {
                        manager.beginTransaction().replace(R.id.frame, fragment).commit();
                        //              manager.executePendingTransactions();
                    }
            }
        } else{
            manager.beginTransaction().replace(R.id.frame, fragment).addToBackStack(tag).commit();
        }
    }

    public static void callFragment1(Fragment fragment, String tag, FragmentManager manager){

        if(tag.equals(new HomeFragment().getClass().getName())&& tag.equals(new ChefHome().getClass().getName()))
        {
            manager.beginTransaction().replace(R.id.frame, fragment)/*.addToBackStack("home")*/.commit();
        }
        else {
//            Toast.makeText(context, (manager.findFragmentByTag(tag) + ""), Toast.LENGTH_SHORT).show();
            if (manager.findFragmentByTag(tag) == null) { //fragment not in back stack, create it.
                manager.beginTransaction().setCustomAnimations(R.animator.fragment_slide_left_enter,
                        R.animator.fade_out,
                        0,
                        R.animator.fragment_slide_right_exit).replace(R.id.frame, fragment).addToBackStack(tag).commit();

//                manager.executePendingTransactions();
            } else {
                manager.beginTransaction().setCustomAnimations(R.animator.fragment_slide_left_enter,
                        R.animator.fade_out,
                        0,
                        R.animator.fragment_slide_right_exit).replace(R.id.frame, fragment).commit();
                //              manager.executePendingTransactions();
            }
        }
    }

    public static String BitMapToString(Bitmap bitmap){
        String temp="";
        if(bitmap!=null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
            byte[] b = baos.toByteArray();
            temp = Base64.encodeToString(b, Base64.DEFAULT);
        }
        return temp;
    }

    public static Bitmap getBitmapFromURL(String src, Activity activity) {
        Log.d("MyImageUrl", src);
        try {
            URL url = new URL("http://webdevelopmentreviews.net/imcooking/upload/1521269701cajun.jpg");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    public static Bitmap getBitmapFromURL1(String strURL) {
        try {
            URL url = new URL("https://api.androidhive.info/images/minion.jpg");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertToBase64(String imagePath) {

//        Bitmap bm = BitmapFactory.decodeFile(imagePath);

        Bitmap bm = null;
        try {
            URL url = new URL(imagePath);
            bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch(IOException e) {
            System.out.println(e);
        }
        
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] byteArrayImage = baos.toByteArray();

        String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

        return encodedImage;

    }


    public static StringBuffer getAddress(Context context, LatLng latLng) throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        StringBuffer result = new StringBuffer();
        geocoder = new Geocoder(context, Locale.getDefault());

        if (latLng!=null){
            try {
                addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                if (addresses!=null){
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    result.append(address);
                }
            /*String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();*/


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    public static Boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if (networkInfo!=null && networkInfo.isConnectedOrConnecting())
            return true;

        return false;
    }

    public static void no_internet(final Activity activity, final Context context) {
        View v = activity.findViewById(android.R.id.content);
        Snackbar.make(v,"No Internet Connection", Snackbar.LENGTH_INDEFINITE)
                .setAction(context.getResources().getString(R.string.retry),
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(!isNetworkConnected(context)){
                                    no_internet(activity, context);
                                }
//                                no_internet(v, context);
                            }
                        }).setActionTextColor(context.getResources().getColor(R.color.colorWhite)).show();

    }

    public static String getUserType(Context context){
        TinyDB tinyDB = new TinyDB(context);
        ApiResponse.UserDataBean userDataBean = new ApiResponse.UserDataBean();
        String login_data = tinyDB.getString("login_data");
        userDataBean = new Gson().fromJson(login_data, ApiResponse.UserDataBean.class);
        String type = userDataBean.getUser_type();

        return type;
    }

}
