package com.doosy.megaworxx.ui.campaign;

import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;
import com.doosy.megaworxx.R;
import com.doosy.megaworxx.ui.BaseActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

public class CampaignActivity extends AppCompatActivity {

    private Animation fabOpenAnimation;
    private Animation fabCloseAnimation;
    private boolean isFabMenuOpen = false;
    private FloatingActionButton baseFloatingActionButton;
    private CoordinatorLayout mainLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_detail);
        baseFloatingActionButton = findViewById(R.id.baseFloatingActionButton);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        mainLayout = findViewById(R.id.mainLayout);
        getAnimations();
        baseFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBaseFabClick();
            }
        });
    }

    private void getAnimations() {

        fabOpenAnimation = AnimationUtils.loadAnimation(this, R.anim.fab_open);

        fabCloseAnimation = AnimationUtils.loadAnimation(this, R.anim.fab_close);

    }

    private void expandFabMenu() {

        ViewCompat.animate(baseFloatingActionButton).rotation(45.0F).withLayer().setDuration(300).setInterpolator(new OvershootInterpolator(10.0F)).start();
        mainLayout.findViewById(R.id.createLayout).startAnimation(fabOpenAnimation);
        mainLayout.findViewById(R.id.shareLayout).startAnimation(fabOpenAnimation);
        mainLayout.findViewById(R.id.createFab).setClickable(true);
        mainLayout.findViewById(R.id.shareFab).setClickable(true);
        isFabMenuOpen = true;


    }

    private void collapseFabMenu() {

        ViewCompat.animate(baseFloatingActionButton).rotation(0.0F).withLayer().setDuration(300).setInterpolator(new OvershootInterpolator(10.0F)).start();
        mainLayout.findViewById(R.id.createLayout).startAnimation(fabCloseAnimation);
        mainLayout.findViewById(R.id.shareLayout).startAnimation(fabCloseAnimation);
        mainLayout.findViewById(R.id.createFab).setClickable(false);
        mainLayout.findViewById(R.id.shareFab).setClickable(false);
        isFabMenuOpen = false;

    }

    public void onBaseFabClick() {

        if (isFabMenuOpen)
            collapseFabMenu();
        else
            expandFabMenu();

    }


}
