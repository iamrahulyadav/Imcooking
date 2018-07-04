package com.imcooking.activity.Sub.Foodie;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.imcooking.Model.ApiRequest.AddressRequest;
import com.imcooking.Model.api.response.ApiResponse;
import com.imcooking.R;
import com.imcooking.adapters.PlacesAutoCompleteAdapter;
import com.imcooking.utils.AppBaseActivity;
import com.imcooking.utils.AppUtils;
import com.imcooking.utils.BaseClass;
import com.imcooking.webservices.GetData;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddAddressActivity extends AppBaseActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, RadioGroup.OnCheckedChangeListener {
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static String TAG = "MAP LOCATION";
    Context mContext;
    private LatLng mCenterLatLong;
    private MapView mMapView;
    private ApiResponse.UserDataBean userDataBean = new ApiResponse.UserDataBean();
    private TextView txtPlaceName, txtLocatName, txtConfirm;
    private EditText edtHouse, edtLandmark;
    TinyDB  tinyDB ;
    String title,foodie_id,address;
    private Gson gson = new Gson();
    private String name="", address_id;
    private boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            BaseClass.setLightStatusBar(getWindow().getDecorView(),AddAddressActivity.this);
            }
        mContext = this;
        tinyDB = new TinyDB(mContext);
        String  login_data = tinyDB.getString("login_data");

        userDataBean = gson.fromJson(login_data, ApiResponse.UserDataBean.class);
        foodie_id = userDataBean.getUser_id()+"";
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            /*Toast.makeText(getContext(), "...", Toast.LENGTH_SHORT).show();*/
            ActivityCompat.requestPermissions(AddAddressActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            return;
        }
        mMapView = (MapView) findViewById(R.id.mapView);
        edtHouse = findViewById(R.id.add_address_edtHouse);
        edtLandmark = findViewById(R.id.add_address_edtLandMark);
        txtLocatName = findViewById(R.id.activity_add_aaddres_txtLocaname);
        txtPlaceName = findViewById(R.id.activity_add_aaddres_txtLoc);
        txtConfirm = findViewById(R.id.add_address_btnConfirm);
        if (getIntent().hasExtra("address_id")){
            address_id = getIntent().getStringExtra("address_id");
            name = getIntent().getStringExtra("name");
            txtPlaceName.setText(name);
            txtLocatName.setText(name);
            isEdit = getIntent().getBooleanExtra("edit",false);
            title = getIntent().getStringExtra("title");
        }

        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        View locationButton = ((View) mMapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
// position on right bottom
//        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        mMapView.getMapAsync(this);
        if (checkPlayServices()) {
            // If this check succeeds, proceed with normal processing.
            // Otherwise, prompt user to get valid Play Services APK.
            if (!AppUtils.isLocationEnabled(mContext)) {
                // notify user
                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                dialog.setMessage("Location not enabled!");
                dialog.setPositiveButton("Open location settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        // TODO Auto-generated method stub
                    }
                });
                dialog.show();
            }
            buildGoogleApiClient();
        } else {
            Toast.makeText(mContext, "Location not supported in this device", Toast.LENGTH_SHORT).show();
        }
        AutoCompleteTextView autocompleteView = (AutoCompleteTextView) findViewById(R.id.autocomplete);
        autocompleteView.setAdapter(new PlacesAutoCompleteAdapter(getApplicationContext(), R.layout.autocomplete_list_item));
        autocompleteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get data associated with the specified position
                // in the list (AdapterView)
                String description = (String) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), description, Toast.LENGTH_SHORT).show();
            }
        });

        txtConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createMyDialog();
            }
        });
    }

    Dialog dialog;
    EditText edtMsg;
    RadioButton radioHome, radioOffice,radioOther;
    TextView txtCanel, txtSave, txtCancleIcon;
    private RadioGroup radioGroup;

    private void createMyDialog(){

        dialog = new Dialog(AddAddressActivity.this);
        dialog.setContentView(R.layout.dialog_save_address);
//        dialog.getWindow().setBackgroundDrawable(null);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        edtMsg = dialog.findViewById(R.id.dialog_address_edtMsg);

        radioGroup = dialog.findViewById(R.id.dialog_address_radiogroup);
        radioHome = dialog.findViewById(R.id.dialog_address_radioHome);
        radioOther = dialog.findViewById(R.id.dialog_address_radioOther);
        radioOffice = dialog.findViewById(R.id.dialog_address_radioOffice);

        radioGroup.setOnCheckedChangeListener(this);
//        radioHome.setChecked(true);
/*
        radioOther.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                edtMsg.setFocusable(true);
            }
        });
*/

        txtSave = dialog.findViewById(R.id.dialog_address_txtSave);
        txtCanel = dialog.findViewById(R.id.dialog_address_txtCancel);
        txtCancleIcon = dialog.findViewById(R.id.cancel_icon);

        txtCancleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        txtCanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
/*
        if (address_id!=null){
            if (title.equalsIgnoreCase("Home"))
                radioHome.setChecked(true);
            else if (title.equalsIgnoreCase("Office"))
                radioOffice.setChecked(true);
            else radioOther.setChecked(true);
        }
*/
        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (title!=null){
                    address = txtLocatName.getText().toString().trim();
                    if (!edtHouse.getText().toString().trim().isEmpty()){
                        address = address + " , "+ edtHouse.getText().toString().trim();
                    }
                    if (!edtLandmark.getText().toString().trim().isEmpty()){
                        address = address + " , "+ edtLandmark.getText().toString().trim();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!edtMsg.getText().toString().trim().isEmpty()){
                             title= edtMsg.getText().toString().trim();
                            }
                            if (address_id!=null){
                                addAddress(title,address,address_id);
                            } else {
                                addAddress(title,address,"");
                            }
                        }
                    });
                } else {
                    Toast.makeText(mContext, "Please select address type", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

        if(radioGroup.getId() == R.id.dialog_address_radiogroup){

            int id = radioGroup.getCheckedRadioButtonId();
            RadioButton radioButton = radioGroup.findViewById(id);
            if(radioButton.getId() == R.id.dialog_address_radioOther){
                edtMsg.setVisibility(View.VISIBLE);
                edtMsg.setFocusable(true);
            } else{
                edtMsg.setVisibility(View.GONE);
            }
        }
    }





    LatLng latLng=mCenterLatLong;
    JSONObject jsonObject2;

    public LatLng getLatLong(String place){
        final ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog.setMessage("Loading...");
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address="+place+"&key=AIzaSyD8rFBw_mmTdTCVQ4IdjhzcXt5P1trKrYw";
        url = url.replace(" ", "+");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, (JSONObject) null, new Response.Listener<JSONObject>() {
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
                            if (latLng!=null){
                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(latLng).zoom(19f).tilt(70).build();
                                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.
                                    return;
                                }
                               if (mMap!=null){
                                   mMap.setMyLocationEnabled(true);
                                   mMap.animateCamera(CameraUpdateFactory
                                           .newCameraPosition(cameraPosition));
                               }
                            }
                            Log.d(TAG, "onActivityResult: "+pickUplat+"\n"+pickUplang);
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
                Log.d(TAG, "onErrorResponse: "+error);
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
        return  latLng;
    }


    private void addAddress(String title, String address, String address_id){
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setTitle(title);
        addressRequest.setFoodie_id(foodie_id);
        addressRequest.setAddress(address);
        if (address_id!=null){
            addressRequest.setAddress_id(address_id);
        } else {
            addressRequest.setAddress_id("");
        }
        String s = gson.toJson(addressRequest);
        Log.d(TAG, "addAddress: "+s);
        new GetData(mContext, AddAddressActivity.this).getResponse(s, GetData.ADDRESS, new GetData.MyCallback() {
            @Override
            public void onSuccess(final String result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ApiResponse apiResponse = new ApiResponse();
                        apiResponse = new Gson().fromJson(result, ApiResponse.class);
                        Toast.makeText(mContext, ""+apiResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        finish();
                    }
                });
            }
        });
    }

    public void dialog_radioClick(View view){
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.dialog_address_radioHome:
                if (checked){
                    edtMsg.setVisibility(View.GONE);
                    title= "Home";
                }
                    break;
            case R.id.dialog_address_radioOffice:
                if (checked){
                    edtMsg.setVisibility(View.GONE);
                    title= "Office";
                }
                break;
            case R.id.dialog_address_radioOther:
                if (checked){
                    edtMsg.setVisibility(View.VISIBLE);
                    title= edtMsg.getText().toString().trim();
                    edtMsg.setFocusable(true);

                }
                    break;
        }
    }








    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, AddAddressActivity.this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                //finish();
            }
            return false;
        }
        return true;

    }

    private void changeMap(Location location) {

        Log.d(TAG, "Reaching map" + mMap);

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        // check if map is created successfully or not
        if (mMap != null) {
            mMap.getUiSettings().setZoomControlsEnabled(false);
            LatLng latLong;
            latLong = new LatLng(location.getLatitude(), location.getLongitude());
           /* mMap.addMarker(new MarkerOptions().position(latLong)
                    .icon(BaseClass.bitmapDescriptorFromVectorR(getApplicationContext())));*/
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLong).zoom(19f).build();
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
        } else {
            Toast.makeText(getApplicationContext(),
                    "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                    .show();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            mGoogleApiClient.connect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public StringBuffer getAddress(LatLng latLng) throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        StringBuffer result = new StringBuffer();
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            result.append(address);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void onStop() {
        super.onStop();
        try {

        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);


        if (mLastLocation != null) {
            changeMap(mLastLocation);

            Log.d(TAG, "ON connected");

        } else
            try {
                LocationServices.FusedLocationApi.removeLocationUpdates(
                        mGoogleApiClient, this);

            } catch (Exception e) {
                e.printStackTrace();
            }
        try {
            @SuppressLint("RestrictedApi") LocationRequest mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(10000);
            mLocationRequest.setFastestInterval(5000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            if (location != null)
                changeMap(location);
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "OnMapReady");
        mMap = googleMap;
//        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(false);

        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                Log.d("Camera posItion change" + "", cameraPosition + "");
                mCenterLatLong = cameraPosition.target;
                //   mMap.clear();

                try {
                    Location mLocation = new Location("");
                    mLocation.setLatitude(mCenterLatLong.latitude);
                    mLocation.setLongitude(mCenterLatLong.longitude);
                    StringBuffer stringBuffer  = new StringBuffer();
                    try {
                        stringBuffer=getAddress(new LatLng(mCenterLatLong.latitude,mCenterLatLong.longitude));
                        if (isEdit){
                            txtPlaceName.setText(name);
                            txtLocatName.setText(name);
                            getLatLong(name);
                            isEdit = false;
                        } else {
                            txtPlaceName.setText(stringBuffer);
                            txtLocatName.setText(stringBuffer);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}


