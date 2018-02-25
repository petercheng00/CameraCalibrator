package net.petercheng.cameracalibrator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    List<Fragment> mFragments;
    List<String> mTitles;

    private TextView mTitle;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragments = new ArrayList<>();
        mFragments.add(new LoadImagesFragment());
        mFragments.add(new FindCornersFragment());
        mFragments.add(new CalibrateFragment());
        mTitles = new ArrayList<>();
        mTitles.add("Load Images");
        mTitles.add("Find Corners");
        mTitles.add("Calculate Intrinsics");

        mTitle = findViewById(R.id.title);
        mTitle.setText(mTitles.get(0));
        mPager = findViewById(R.id.viewpager);
        mPagerAdapter = new CalibratorPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mTitle.setText(mTitles.get(position));
            }
        });

        TextView back = findViewById(R.id.back);
        TextView next = findViewById(R.id.next);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(mPager.getCurrentItem() + 1);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private class CalibratorPagerAdapter extends FragmentStatePagerAdapter {

        public CalibratorPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}
