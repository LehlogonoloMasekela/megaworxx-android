package com.doosy.megaworxx.ui.campaign;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.doosy.megaworxx.R;
import com.doosy.megaworxx.model.StatusModel;
import com.doosy.megaworxx.util.Constants;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SurveyFragment mSurveyFragment;
    public FeedbackFragment mFeedbackFragment;
    public StockFragment mStockFragment;
    public SalesFragment mSalesFragment;

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

        switch (position) {
            case 0:

                if (mStockFragment == null) {
                    mStockFragment = StockFragment.newInstance();
                }
                return mStockFragment;

            case 1:

                if (mSalesFragment == null) {
                    mSalesFragment = SalesFragment.newInstance();
                }
                return mSalesFragment;

            case 2:
                if (mSurveyFragment == null) {
                    mSurveyFragment = SurveyFragment.newInstance();
                }
                return mSurveyFragment;

            case 3:
                if (mFeedbackFragment == null) {
                    mFeedbackFragment = FeedbackFragment.newInstance();
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
        }else if(campaignTab == CampaignActivity.CampaignTab.Feedback){
            if(mFeedbackFragment != null){
                mFeedbackFragment.loadData();
            }
        }else if(campaignTab == CampaignActivity.CampaignTab.Survey){
            if(mSurveyFragment != null){
                mSurveyFragment.loadData();
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