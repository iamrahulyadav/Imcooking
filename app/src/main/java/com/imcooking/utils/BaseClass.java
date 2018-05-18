package com.imcooking.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.imcooking.R;
import com.imcooking.fragment.foodie.HomeFragment;
import com.imcooking.fragment.chef.ChefHome;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
    public static LatLng latLng;
    public static JSONObject jsonObject2;


    public static LatLng getLatLong(String place, Context context){
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address="+place+"&key=AIzaSyD8rFBw_mmTdTCVQ4IdjhzcXt5P1trKrYw";
        url = url.replace(" ", "+");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response!=null){
                    try {
                        JSONArray jsonArray = response.getJSONArray("results");
                        for (int i=0; i<jsonArray.length();i++){
                            JSONObject jsonObject ;
                            jsonObject = jsonArray.getJSONObject(i);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("geometry");
                            jsonObject2 = jsonObject1.getJSONObject("location");
                            double pickUplat = jsonObject2.getDouble("lat");
                            double pickUplang = jsonObject2.getDouble("lng");
                            latLng = new LatLng(pickUplat,pickUplang);
//                    Log.d(TAG, "onResponse:d "+lat +"\n" +longi );
                            Log.d("TAG", "onActivityResult: "+pickUplat+"\n"+pickUplang);
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse: "+error);
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
        return  latLng;
    }

    public static StringBuffer getAddress(LatLng latLng, Context mContext) throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        StringBuffer result = new StringBuffer();
        geocoder = new Geocoder(mContext, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            /*String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();*/
            result.append(city);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
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


    public static void callFragment(Fragment fragment, String tag, FragmentManager manager){


        if(tag.equals(new HomeFragment().getClass().getName())&& tag.equals(new ChefHome().getClass().getName()))
        {
            manager.beginTransaction().replace(R.id.frame, fragment)/*.addToBackStack("home")*/.commit();
        }
        else {
//            Toast.makeText(context, (manager.findFragmentByTag(tag) + ""), Toast.LENGTH_SHORT).show();
            if (manager.findFragmentByTag(tag) == null) { //fragment not in back stack, create it.
                manager.beginTransaction().replace(R.id.frame, fragment).addToBackStack(tag).commit();

//                manager.executePendingTransactions();
            } else {
                manager.beginTransaction().replace(R.id.frame, fragment).commit();
                //              manager.executePendingTransactions();
            }
        }
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

}
