package com.doosy.megaworxx;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.doosy.megaworxx.model.FirebaseModel;
import com.doosy.megaworxx.model.ServerResponse;
import com.doosy.megaworxx.ui.BaseActivity;
import com.doosy.megaworxx.ui.home.FilterFragment;
import com.doosy.megaworxx.ui.home.HomeFragment;
import com.doosy.megaworxx.ui.home.message.MessageFragment;
import com.doosy.megaworxx.ui.home.ProfileFragment;
import com.doosy.megaworxx.util.Constants;
import com.doosy.megaworxx.viewmodel.HomeViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private AppBarConfiguration mAppBarConfiguration;
    private HomeFragment mHomeFragment;
    private FilterFragment mFilterFragment;
    private MessageFragment mMessageFragment;
    private ProfileFragment mProfileFragment;

    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;

    private LinearLayout mLinearSignOut;
    private NavController navController;

    private HomeViewModel homeViewModel;
    private LiveData<ServerResponse> response;

    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.activity_main);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        initViews();

        mLinearSignOut.setOnClickListener(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mDrawer = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_campaign, R.id.nav_message, R.id.nav_sign_out)
                .setDrawerLayout(mDrawer)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(mNavigationView, navController);
        setUpNavDrawer(mNavigationView);

        if(savedInstanceState == null){
            mHomeFragment = HomeFragment.newInstance();
            loadFragment(mHomeFragment, "home");
        }

        if(!settings.isFirebaseTokenSaved()){
            sendTokenToServer();
        }

    }

    @Override
    public void displayPage(boolean hasContent) {

    }

    @Override
    public void retryLoad() {

    }


    private void sendTokenToServer(){

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(Constants.TAG, "Fetching FCM registration token failed",
                                    task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        final String token = task.getResult();

                        Log.w(Constants.TAG, "Firebase token: " + token);
                        FirebaseModel firebaseModel = new FirebaseModel(settings.getUserId(), token);
                        homeViewModel.saveFirBaseToken(settings.getToken(), firebaseModel);
                        response = homeViewModel.getResponse();

                        response.observe(HomeActivity.this, new Observer<ServerResponse>() {
                            @Override
                            public void onChanged(ServerResponse response) {
                                //flag that token has been sent to the server
                                settings.setFireBaseToken(response != null && response.isSuccessful());
                            }
                        });
                    }
                });

    }

    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        mLinearSignOut = findViewById(R.id.nav_sign_out);
    }



    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void loadFragment(Fragment fragment, String tag){
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.commit();
    }

    private void setUpNavDrawer(NavigationView navigationView){

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.nav_home:
                        bottomNavigationView.setSelectedItemId( R.id.menu_home);
                        break;

                    case R.id.nav_campaign:
                        bottomNavigationView.setSelectedItemId( R.id.menu_campaigns);
                        break;

                    case R.id.nav_message:
                        bottomNavigationView.setSelectedItemId( R.id.menu_messages);
                        break;
                }

                item.setChecked(true);
                mDrawer.closeDrawers();
                return true;
            }
        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId())
            {
                case R.id.menu_home:

                    toolbar.setTitle("Home");
                    if(mHomeFragment == null){

                        mHomeFragment = HomeFragment.newInstance();

                    }
                    mNavigationView.setCheckedItem(R.id.menu_home);
                    loadFragment(mHomeFragment, "home");
                    return true;

                case R.id.menu_campaigns:
                    toolbar.setTitle("Campaign");
                    if(mFilterFragment == null){

                        mFilterFragment = FilterFragment.newInstance();

                    }
                    mNavigationView.setCheckedItem(R.id.nav_campaign);
                    loadFragment(mFilterFragment, "filter");
                    return true;

                case R.id.menu_messages:
                    toolbar.setTitle("Message");
                    if(mMessageFragment == null){

                        mMessageFragment = MessageFragment.newInstance();

                    }
                    mNavigationView.setCheckedItem(R.id.nav_message);
                    loadFragment(mMessageFragment, "message");
                    return true;

                case R.id.menu_profile:
                    toolbar.setTitle("Profile");
                    if(mProfileFragment == null){

                        mProfileFragment = ProfileFragment.newInstance();

                    }
                    loadFragment(mProfileFragment, "profile");
                    return true;

            }
            return false;
        }
    };

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.nav_sign_out){
            signOut();
        }
    }

    @Override
    public void onBackPressed() {
        if(mHomeFragment != null && mHomeFragment.isVisible()){
            finish();
            super.onBackPressed();
        }else{
            bottomNavigationView.setSelectedItemId(R.id.menu_home);
        }
    }

    private void signOut(){
        settings.setIsLoggedIn(false);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}