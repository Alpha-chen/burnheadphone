package com.burning.click.burnheadphone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.burning.click.burnheadphone.adapter.MyPagerAdapter;
import com.burning.click.burnheadphone.common.ImageRes;

import static android.support.v4.view.ViewPager.OnPageChangeListener;

/**
 * 引导页
 */
public class WelcomeActivity extends BaseActivity implements OnPageChangeListener{
    private String TAG="WelcomeActivity";
    private ViewPager mGuideViewPager;
    private MyPagerAdapter myPagerAdapter;
    private int [] mGuidePic;
    private Button welcomeBtn;

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
        welcomeBtn = (Button) findViewById(R.id.welcome);
        welcomeBtn.setVisibility(View.GONE);
        welcomeBtn.setOnClickListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    Log.d(TAG,"aaaaaa");
    }

    @Override
    public void onPageSelected(int position) {
        Log.d(TAG,"position="+position);
        android.util.Log.d(TAG,"12222222222");
            if (position==mGuidePic.length-1){
                welcomeBtn.setVisibility(View.VISIBLE);
            }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case  R.id.welcome:
                Intent intent = new Intent();
                intent.setClass(WelcomeActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;





        }
    }
}
