package com.imcooking.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.imcooking.Model.ApiRequest.Home;
import com.imcooking.R;
import com.imcooking.fragment.foodie.HomeFragment;
import com.imcooking.fragment.chef.ChefHome;

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

    public static boolean checkGooglePlayService(Activity activity)
    {
        int checkGooglePlayService= GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        int Requestcode=200;
        if(checkGooglePlayService!= ConnectionResult.SUCCESS)
        {
            GooglePlayServicesUtil.getErrorDialog(checkGooglePlayService,activity,Requestcode);
            Toast.makeText(activity,activity.getResources().getString(R.string.no_working),Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public static boolean requestPermissionToLocation(final Context context, final Fragment fragment) {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        } else if (ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION") != 0 && ActivityCompat.checkSelfPermission(context, "android.permission.ACCESS_COARSE_LOCATION") != 0) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)context, "android.permission.ACCESS_FINE_LOCATION") && ActivityCompat.shouldShowRequestPermissionRationale((Activity)context, "android.permission.ACCESS_COARSE_LOCATION")) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle("Permission necessary");
                alertBuilder.setMessage("Location permission is necessary");
                alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @TargetApi(16)
                    public void onClick(DialogInterface dialog, int which) {
                        if (fragment == null) {
                            ActivityCompat.requestPermissions((Activity)context, new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, 2);
                        } else {
                            fragment.requestPermissions(new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, 2);
                        }

                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
            } else if (fragment == null) {
                ActivityCompat.requestPermissions((Activity)context, new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, 2);
            } else {
                fragment.requestPermissions(new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, 2);
            }

            return false;
        } else {
            return true;
        }
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
