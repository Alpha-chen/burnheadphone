package com.burning.click.burnheadphone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.burning.click.burnheadphone.Log.LogUtil;
import com.burning.click.burnheadphone.constant.Constant;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectBurnTimeActivity extends BaseActivity {
    private String TAG = "SelectBurnTimeActivity";
    @Bind(R.id.burn_mode_time_lay)
    RelativeLayout burn_mode_time_lay;
    @Bind(R.id.burn_mode_time_select)
    ImageView burn_mode_time_select;

    @Bind(R.id.burn_mode_time_lay1)
    RelativeLayout burn_mode_time_lay1;
    @Bind(R.id.burn_mode_time_select1)
    ImageView burn_mode_time_select1;

    @Bind(R.id.burn_mode_time_lay2)
    RelativeLayout burn_mode_time_lay2;
    @Bind(R.id.burn_mode_time_select2)
    ImageView burn_mode_time_select2;

    @Bind(R.id.burn_mode_time_lay3)
    RelativeLayout burn_mode_time_lay3;
    @Bind(R.id.burn_mode_time_select3)
    ImageView burn_mode_time_select3;


    @Bind(R.id.select_burn_time_ok)
    ImageView select_burn_time_ok;
    private int burn_time = 0; //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.select_burn_time);
        ButterKnife.bind(this);
        initIntent();
        initViewData();

    }

    @Override
    protected void initViewData() {
        super.initViewData();
        switch (burn_time) {
            case 30:
                changeStatus(R.id.burn_mode_time_select);
                break;
            case 60:
                changeStatus(R.id.burn_mode_time_select1);
                break;
            case 180:
                changeStatus(R.id.burn_mode_time_select2);
                break;
            case 999:
                changeStatus(R.id.burn_mode_time_select3);
                break;
            default:
                break;
        }
    }

    public void changeStatus(int id) {
        switch (id) {
            case R.id.burn_mode_time_select:
                burn_mode_time_select.setVisibility(View.VISIBLE);
                burn_mode_time_select1.setVisibility(View.GONE);
                burn_mode_time_select2.setVisibility(View.GONE);
                burn_mode_time_select3.setVisibility(View.GONE);
                break;
            case R.id.burn_mode_time_select1:
                burn_mode_time_select.setVisibility(View.GONE);
                burn_mode_time_select1.setVisibility(View.VISIBLE);
                burn_mode_time_select2.setVisibility(View.GONE);
                burn_mode_time_select3.setVisibility(View.GONE);
                break;

            case R.id.burn_mode_time_select2:
                burn_mode_time_select.setVisibility(View.GONE);
                burn_mode_time_select1.setVisibility(View.GONE);
                burn_mode_time_select2.setVisibility(View.VISIBLE);
                burn_mode_time_select3.setVisibility(View.GONE);
                break;
            case R.id.burn_mode_time_select3:
                burn_mode_time_select.setVisibility(View.GONE);
                burn_mode_time_select1.setVisibility(View.GONE);
                burn_mode_time_select2.setVisibility(View.GONE);
                burn_mode_time_select3.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }

    }

    @OnClick(R.id.select_burn_time_ok)
    void saveBurnTime() {
        Intent intent = new Intent();
        intent.putExtra("selectBurnTime", burn_time);
        setResult(Constant.RESULT_CODE.SELECT_BURN_SONG_TIME, intent);
        LogUtil.d(TAG, "BurnTime=" + burn_time);
        finish();
    }

    @OnClick(R.id.burn_mode_time_lay3)
    void clickBurnTime3() {
        changeStatus(R.id.burn_mode_time_select3);
        burn_time = 999;
    }

    @OnClick(R.id.burn_mode_time_lay2)
    void clickBurnTime2() {
        changeStatus(R.id.burn_mode_time_select2);
        burn_time = 180;
    }

    @OnClick(R.id.burn_mode_time_lay1)
    void clickBurnTime1() {
        changeStatus(R.id.burn_mode_time_select1);
        burn_time = 60;
    }

    @OnClick(R.id.burn_mode_time_lay)
    void clickBurnTime() {
        changeStatus(R.id.burn_mode_time_select);
        burn_time = 30;

    }


    @Override
    protected void initIntent() {
        super.initIntent();
        if (null == getIntent()) return;
        burn_time = getIntent().getIntExtra("burnTime", 0);
    }

    @Override
    protected void initMethod() {
        super.initMethod();
    }
}
