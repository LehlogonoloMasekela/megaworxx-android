package com.doosy.megaworxx.ui.campaign;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.doosy.megaworxx.R;
import com.doosy.megaworxx.util.Constants;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private SurveyFragment mSurveyFragment;
    private FeedbackFragment mFeedbackFragment;
    private StockFragment mStockFragment;
    private SalesFragment mSalesFragment;

    @StringRes
    private static final int[] TAB_TITLES = new int[]
    {
            R.string.tab_stock,
            R.string.tab_sales,
            R.string.tab_survey,
            R.string.tab_feedback,

    };

    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Log.d(Constants.TAG, "Inside Pager Adapter: " + position);
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0:

                if (mStockFragment == null) {
                    mStockFragment = StockFragment.newInstance();
                }
                return mStockFragment;

            case 1:

                if (mSalesFragment == null) {
                    mSalesFragment = SalesFragment.newInstance(1);
                }
                return mSalesFragment;

            case 2:
                if (mSurveyFragment == null) {
                    mSurveyFragment = SurveyFragment.newInstance(2);
                }
                return mSurveyFragment;

            case 3:
                if (mFeedbackFragment == null) {
                    mFeedbackFragment = FeedbackFragment.newInstance(3);
                }
                return mFeedbackFragment;

        }

        return null;
    }

    public void reload(CampaignActivity.CampaignTab campaignTab){

        if(campaignTab == CampaignActivity.CampaignTab.Stock){
            if(mStockFragment != null){
                mStockFragment.loadData();
            }
        }else if(campaignTab == CampaignActivity.CampaignTab.Sales){
            if(mSalesFragment != null){
                mSalesFragment.loadData();
            }
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 4;
    }
}