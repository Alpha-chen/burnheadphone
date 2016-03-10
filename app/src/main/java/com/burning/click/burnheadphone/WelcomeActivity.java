package com.burning.click.burnheadphone;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Window;

import com.burning.click.burnheadphone.adapter.MyPagerAdapter;
import com.burning.click.burnheadphone.common.ImageRes;

import static android.support.v4.view.ViewPager.OnPageChangeListener;

/**
 * 引导页
 */
public class WelcomeActivity extends BaseActivity implements OnPageChangeListener{
    private ViewPager mGuideViewPager;
    private MyPagerAdapter myPagerAdapter;
    private int [] mGuidePic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);
        initViewData();
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        mGuideViewPager = (ViewPager) findViewById(R.id.guide_vp);
        mGuidePic = ImageRes.getGuidePic();
        myPagerAdapter =  new MyPagerAdapter(WelcomeActivity.this);
        myPagerAdapter.setmGuidePic(mGuidePic);
        mGuideViewPager.setAdapter(myPagerAdapter);
        mGuideViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
