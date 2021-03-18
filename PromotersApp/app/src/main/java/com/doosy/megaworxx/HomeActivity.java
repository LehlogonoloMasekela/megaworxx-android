package com.doosy.megaworxx;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.LinearLayout;

import com.doosy.megaworxx.ui.BaseActivity;
import com.doosy.megaworxx.ui.campaign.CampaignActivity;
import com.doosy.megaworxx.ui.filter.FilterFragment;
import com.doosy.megaworxx.ui.home.HomeFragment;
import com.doosy.megaworxx.ui.message.MessageFragment;
import com.doosy.megaworxx.ui.profile.ProfileFragment;
import com.doosy.megaworxx.util.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.activity_main);
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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_campaign, R.id.nav_message, R.id.nav_sign_out)
                .setDrawerLayout(drawer)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        if(savedInstanceState == null){
            mHomeFragment = HomeFragment.newInstance();
            loadFragment(mHomeFragment, "home");
        }

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
        transaction.replace(R.id.nav_host_fragment, fragment, tag);
        transaction.commit();
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
                        Log.d(Constants.TAG, "On Home null");
                        mHomeFragment = HomeFragment.newInstance();

                    }
                    loadFragment(mHomeFragment, "home");
                    return true;

                case R.id.menu_campaigns:
                    toolbar.setTitle("Campaign");
                    if(mFilterFragment == null){
                        Log.d(Constants.TAG, "On Campaign null");
                        mFilterFragment = FilterFragment.newInstance();

                    }
                    loadFragment(mFilterFragment, "filter");
                    return true;

                case R.id.menu_messages:
                    toolbar.setTitle("Message");
                    if(mMessageFragment == null){
                        Log.d(Constants.TAG, "On Message null");
                        mMessageFragment = MessageFragment.newInstance();

                    }
                    loadFragment(mMessageFragment, "message");
                    return true;

                case R.id.menu_profile:
                    toolbar.setTitle("Profile");
                    if(mProfileFragment == null){
                        Log.d(Constants.TAG, "On Profile null");
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

    private void signOut(){
        settings.setIsLoggedIn(false);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}