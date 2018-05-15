package com.imcooking.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.imcooking.R;
import com.imcooking.fragment.HomeFragment;

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


    public static void callFragment(Fragment fragment, String tag, FragmentManager manager){


        if(tag.equals(new HomeFragment().getClass().getName()))
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
