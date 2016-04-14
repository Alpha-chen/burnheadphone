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
import com.burning.click.burnheadphone.sp.SpUtils;
import com.burning.click.burnheadphone.util.SpkeyName;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.v4.view.ViewPager.OnPageChangeListener;

/**
 * 引导页
 */
public class WelcomeActivity extends BaseActivity implements OnPageChangeListener {
    private String TAG = "WelcomeActivity";
    @Bind(R.id.guide_vp)
    ViewPager mGuideViewPager;
    private MyPagerAdapter myPagerAdapter;
    private int[] mGuidePic;
    @Bind(R.id.welcome)
    Button welcomeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        checkStatus();
        initViewData();
        initView();
    }

    private void checkStatus() {
        if (!SpUtils.getBoolean(WelcomeActivity.this, SpUtils.BHP_SHARF, SpkeyName.IS_FIRST, true)) {
            if (0 == SpUtils.getInt(WelcomeActivity.this, SpUtils.BHP_SHARF, SpkeyName.LOGIN_STATUS, 0)) {
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                finish();
            } else {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        }
    }

    @Override
    protected void initView() {
        super.initView();
        mGuidePic = ImageRes.getGuidePic();
        myPagerAdapter = new MyPagerAdapter(WelcomeActivity.this);
        myPagerAdapter.setmGuidePic(mGuidePic);
        mGuideViewPager.setAdapter(myPagerAdapter);
        mGuideViewPager.addOnPageChangeListener(this);
        welcomeBtn.setVisibility(View.GONE);
    }


    @OnClick(R.id.welcome)
    void welcome() {
        Intent intent = new Intent();
        intent.setClass(WelcomeActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        SpUtils.putBoolean(WelcomeActivity.this,SpUtils.BHP_SHARF,SpkeyName.IS_FIRST,false);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.d(TAG, "position=" + position);
//        float percent =(positionOffsetPixels+1)/ScreenUtil.getScreenPixels(WelcomeActivity.this)[0];
//        LogUtil.d("percent",percent+"");
//        welcomeBtn.setAlpha(percent);
        if (position == mGuidePic.length - 1) {
            welcomeBtn.setVisibility(View.VISIBLE);
        } else {
            welcomeBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


}
