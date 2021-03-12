package com.doosy.megaworxx.ui.campaign;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.doosy.megaworxx.R;
import com.doosy.megaworxx.dialog.CheckDialog;
import com.doosy.megaworxx.dialog.ConfirmDialog;
import com.doosy.megaworxx.entity.Campaign;
import com.doosy.megaworxx.entity.CampaignModel;
import com.doosy.megaworxx.model.DialogMessage;
import com.doosy.megaworxx.model.CheckModel;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.ServerResponse;
import com.doosy.megaworxx.service.listener.OnCheckDialogListener;
import com.doosy.megaworxx.service.listener.OnConfirmDialogListener;
import com.doosy.megaworxx.ui.BaseActivity;
import com.doosy.megaworxx.ui.ViewDetailActivity;
import com.doosy.megaworxx.ui.feedback.AddFeedbackActivity;
import com.doosy.megaworxx.ui.sales.AddSalesActivity;
import com.doosy.megaworxx.ui.stock.AddStockActivity;
import com.doosy.megaworxx.ui.survey.AddSurveyActivity;
import com.doosy.megaworxx.util.Constants;
import com.doosy.megaworxx.viewmodel.CampaignViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class CampaignActivity extends BaseActivity implements View.OnClickListener,
        OnCheckDialogListener, OnConfirmDialogListener {

    public enum CampaignTab {
        Survey,
        Stock,
        Feedback,
        Sales
    }

    private Animation fabOpenAnimation;
    private Animation fabCloseAnimation;
    private boolean isFabMenuOpen = false;
    private FloatingActionButton baseFloatingActionButton;
    private CoordinatorLayout mainLayout;
    private FloatingActionButton fabAdd;
    private FloatingActionButton fabCheckIn, fabCheckOut;
    private TextView tvAdd;
    private CampaignTab mCampaignTab = CampaignTab.Survey;
    private Location mLocation;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private LocationManager mLocationManager;

    private CampaignViewModel campaignViewModel;
    private LiveData<ServerResponse> mResponse;
    private LiveData<DataServerResponse<Campaign>> mDataResponse;

    private CampaignModel mCampaignModel;
    private Campaign mCampaign;

    private int REQUEST_CODE_ADD_STOCK = 1001;
    private int REQUEST_CODE_ADD_SALE = 1002;

    private int REQUEST_CHECK = 1;

    private SectionsPagerAdapter sectionsPagerAdapter;
    private ConfirmDialog confirmDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_detail);
        campaignViewModel = new ViewModelProvider(this).get(CampaignViewModel.class);
        initViews();

        String key = getString(R.string.key_campaign_model);

        if(getIntent().hasExtra(key)){
            mCampaignModel = (CampaignModel) getIntent().getSerializableExtra(key);
            showLoading();
            campaignViewModel.getCampaignById(settings.getToken(), mCampaignModel.getCampaignId());
            mDataResponse = campaignViewModel.getCampaignResponse();
            mDataResponse.observe(this, new Observer<DataServerResponse<Campaign>>() {
                @Override
                public void onChanged(DataServerResponse<Campaign> response) {
                    hideLoading();
                    if(response != null && response.isSuccessful()){
                        mCampaign = response.getDataList().get(0);
                        initViewPager();
                    }
                }
            });
            MaterialToolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle(mCampaignModel.getCampaignName());
        }

        baseFloatingActionButton = findViewById(R.id.baseFloatingActionButton);


        mainLayout = findViewById(R.id.mainLayout);
        getAnimations();
        baseFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBaseFabClick();
            }
        });

        fabAdd.setOnClickListener(this);
        fabCheckIn.setOnClickListener(this);
        fabCheckOut.setOnClickListener(this);
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        checkLocationPermission();
        requestLocation();

    }

    private void initViewPager(){
        sectionsPagerAdapter =
                new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeAction(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //changeAction(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //changeAction(tab.getPosition());
            }
        });
        changeAction(0);
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            mLocation = location;

            //Log.d(Constants.TAG, "Latitude: " +
            // mLocation.getLatitude() + " ==== Longitude: " + mLocation.getLongitude());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public Location getLocation() {
        return mLocation;
    }

    private void check(final int checkType) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
           showError("Location permission is not granted.");
            return;
        }

        Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location == null){
            DialogMessage dialogMessage = null;
            String[] strList = getResources().getStringArray(R.array.check_error);
            dialogMessage = new DialogMessage(strList[0],
                    checkType == 1? strList[1] : strList[2] ,
                    R.drawable.ic_warning2);
            showDialog(dialogMessage);
            return;
        }
        String formattedLocation = location.getLatitude() + ";" +location.getLongitude();

        CheckModel checkModel = new CheckModel(settings.getUserId(),
                mCampaignModel.getCampaignDateId(), mCampaignModel.getCampaignId(),formattedLocation,checkType);
        campaignViewModel.check(settings.getToken(), checkModel);
        mResponse = campaignViewModel.getResponse();
        mResponse.observe(this, new Observer<ServerResponse>() {
            @Override
            public void onChanged(ServerResponse response) {
                mResponse.removeObserver(this);
                if(mResponse.hasActiveObservers()){
                    mResponse.removeObservers(CampaignActivity.this);
                }

                DialogMessage dialogMessage = null;
                String[] strList = getResources().getStringArray(R.array.check_error);
                if(response == null){
                    dialogMessage = new DialogMessage(strList[0],
                            checkType == 1? strList[1] : strList[2] ,
                            R.drawable.ic_warning2);
                    showDialog(dialogMessage);
                    return;
                }

                if(response.isSuccessful()){
                    strList = getResources().getStringArray(R.array.check_success);
                    dialogMessage = new DialogMessage(strList[0],
                            checkType == 1? strList[1] : strList[2] , R.drawable.ic_success_icon);
                }else{
                    String messege = "";
                    if(response.getMessages().size() > 0){
                        for (String msg:
                             response.getMessages()) {
                            messege = messege + msg + "\n";
                        }
                    }

                    if(messege.isEmpty()) {
                        dialogMessage = new DialogMessage(strList[0],
                                checkType == 1 ? strList[1] : strList[2], R.drawable.ic_warning2);
                    }else{
                        dialogMessage = new DialogMessage(strList[0], messege , R.drawable.ic_warning2);
                    }
                }
                showDialog(dialogMessage);
            }
        });


    }

    private void showDialog(DialogMessage dialogMessage){
        CheckDialog dialog = CheckDialog.newInstance(dialogMessage);
        dialog.show(getSupportFragmentManager(), "check");
    }

    private void showConfirmDialog(DialogMessage dialogMessage){
        confirmDialog = ConfirmDialog.newInstance(dialogMessage);
        confirmDialog.show(getSupportFragmentManager(), "confirmDialog");
    }

    public CampaignModel getCampaignModel(){
        return this.mCampaignModel;
    }

    public Campaign getCampaign(){
        return this.mCampaign;
    }

    @Override
    public void onOkClicked() {

    }

    @Override
    public void onYesClicked() {
        check(REQUEST_CHECK);
    }

    @Override
    public void onNoClicked() {
        confirmDialog.dismiss();
    }

    private void requestLocation(){
        if(checkLocationPermission()) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                    0, mLocationListener);
        }
    }

    private void changeAction(int position) {
        Log.d(Constants.TAG, "Position: "+position);
        switch (position){

            case 0:

                mCampaignTab = CampaignTab.Stock;
                tvAdd.setText(getString(R.string.action_add_stock));
                break;
            case 1:
                mCampaignTab = CampaignTab.Sales;
                tvAdd.setText(getString(R.string.action_add_sale));
                break;
            case 2:
                mCampaignTab = CampaignTab.Survey;
                tvAdd.setText(getString(R.string.action_add_survey));

                break;
            case 3:
                mCampaignTab = CampaignTab.Feedback;
                tvAdd.setText(getString(R.string.action_add_feedback));
                break;
        }
    }


    private void initViews() {
        fabCheckIn = findViewById(R.id.checkInFab);
        fabCheckOut = findViewById(R.id.checkOutFab);
        fabAdd = findViewById(R.id.addFab);
        tvAdd = findViewById(R.id.addLayoutLabelTextView);
    }

    private void getAnimations() {

        fabOpenAnimation = AnimationUtils.loadAnimation(this, R.anim.fab_open);

        fabCloseAnimation = AnimationUtils.loadAnimation(this, R.anim.fab_close);

    }

    private void expandFabMenu() {

        ViewCompat.animate(baseFloatingActionButton).rotation(90.0F).withLayer()
                .setDuration(300).setInterpolator(new OvershootInterpolator(10.0F)).start();
        mainLayout.findViewById(R.id.createLayout).startAnimation(fabOpenAnimation);
        mainLayout.findViewById(R.id.shareLayout).startAnimation(fabOpenAnimation);
        mainLayout.findViewById(R.id.addLayout).startAnimation(fabOpenAnimation);

        mainLayout.findViewById(R.id.checkInFab).setClickable(true);
        mainLayout.findViewById(R.id.checkOutFab).setClickable(true);
        mainLayout.findViewById(R.id.addFab).setClickable(true);
        isFabMenuOpen = true;


    }

    private void collapseFabMenu() {

        ViewCompat.animate(baseFloatingActionButton).rotation(0.0F).withLayer()
                .setDuration(300).setInterpolator(new OvershootInterpolator(10.0F)).start();
        mainLayout.findViewById(R.id.createLayout).startAnimation(fabCloseAnimation);
        mainLayout.findViewById(R.id.shareLayout).startAnimation(fabCloseAnimation);
        mainLayout.findViewById(R.id.addLayout).startAnimation(fabCloseAnimation);
        mainLayout.findViewById(R.id.checkInFab).setClickable(false);
        mainLayout.findViewById(R.id.checkOutFab).setClickable(false);
        mainLayout.findViewById(R.id.addFab).setClickable(false);
        isFabMenuOpen = false;

    }

    public void onBaseFabClick() {

        if (isFabMenuOpen)
            collapseFabMenu();
        else
            expandFabMenu();

    }

    @Override
    public void onClick(View v) {

        Bundle bundle = new Bundle();
        String keyCampaign = getString(R.string.key_campaign);
        String keyCampaignModel = getString(R.string.key_campaign_model);
        bundle.putSerializable(keyCampaign, mCampaign);
        bundle.putSerializable(keyCampaignModel, mCampaignModel);

        String coordinates = mLocation.getLongitude() +","+ mLocation.getLatitude();
        settings.saveCoordinates(coordinates);

        if(v.getId() == R.id.addFab){
            if(mCampaignTab == CampaignTab.Survey){
                startActivity(new Intent(this, AddSurveyActivity.class));
            }else if(mCampaignTab == CampaignTab.Stock){

                Intent intent = new Intent(this, AddStockActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_CODE_ADD_STOCK);
            }
            else if(mCampaignTab == CampaignTab.Feedback){
                startActivity(new Intent(this, AddFeedbackActivity.class));
            }else if(mCampaignTab == CampaignTab.Sales){

                Intent intent = new Intent(this, AddSalesActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_CODE_ADD_SALE);
            }
        }else if(v.getId() == R.id.checkInFab){
            if(checkLocationPermission()){
                REQUEST_CHECK = 1;
                DialogMessage dialogMessage = new DialogMessage("Check in",
                        "Are you sure you want to check-in?.");
                showConfirmDialog(dialogMessage);
            }

        }else if(v.getId() == R.id.checkOutFab){
            if(checkLocationPermission()){
                REQUEST_CHECK = 2;
                DialogMessage dialogMessage = new DialogMessage("Check out",
                        "Are you sure you want to check-out?.");
                showConfirmDialog(dialogMessage);
            }

        }

        if(isFabMenuOpen) collapseFabMenu();
    }

    public void viewDetail(CampaignTab campaignTab){
        Intent intent = new Intent(this, ViewDetailActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if(requestCode == REQUEST_CODE_ADD_STOCK){
                if(data.hasExtra("isSuccessful")){
                    boolean isSuccessful = (boolean)data.getSerializableExtra("isSuccessful");
                    if(isSuccessful){
                        if(sectionsPagerAdapter != null){
                            sectionsPagerAdapter.reload(CampaignTab.Stock);
                        }
                    }
                }
            }else  if(requestCode == REQUEST_CODE_ADD_SALE){
                if(data.hasExtra("isSuccessful")){
                    boolean isSuccessful = (boolean)data.getSerializableExtra("isSuccessful");
                    if(isSuccessful){
                        if(sectionsPagerAdapter != null){
                            sectionsPagerAdapter.reload(CampaignTab.Sales);
                        }
                    }
                }
            }

        }
    }

    private boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected. In this UI,
                // include a "cancel" or "no thanks" button that allows the user to
                // continue using your app without granting the permission.
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(CampaignActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        LOCATION_PERMISSION_REQUEST_CODE);
                            }
                        })
                        .create()
                        .show();
            } else {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        requestLocation();

                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            requestLocation();

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            requestLocation();
            //locationManager.removeUpdates(this);
        }
    }

    @Override
    protected void onStop() {

        if(mLocationManager != null && mLocationListener != null){
          mLocationManager.removeUpdates(mLocationListener);
        }
        super.onStop();
    }
}
