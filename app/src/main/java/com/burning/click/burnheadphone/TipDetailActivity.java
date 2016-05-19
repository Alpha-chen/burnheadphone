package com.burning.click.burnheadphone;

import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 煲耳机详细信息界面
 */
public class TipDetailActivity extends BaseActivity {

    @Bind(R.id.tip_detail_back)
    ImageView back;

    @Bind(R.id.tip_detail_title)
    TextView tip_detail_title;

    @Bind(R.id.tip_detail_message)
    TextView tip_detail_message;

    @OnClick(R.id.tip_detail_back)
    void tip_detail_back() {
        finish();
    }

    private int title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tip_detail);
        ButterKnife.bind(this);
        initIntent();
        initView();
    }

    @Override
    protected void initIntent() {
        super.initIntent();
        if (null == getIntent()) return;
    }

    @Override
    protected void initView() {
        super.initView();
        switch (getIntent().getIntExtra("title", 0)) {
            case 0:
                tip_detail_title.setText(getResources().getString(R.string.burn_tip1));
                tip_detail_message.setText(getResources().getString(R.string.burn_tip1_detail1));

                break;
            case 1:
                tip_detail_title.setText(getResources().getString(R.string.burn_tip2));
                tip_detail_message.setText(getResources().getString(R.string.burn_tip1_detail2));

                break;
            case 2:
                tip_detail_title.setText(getResources().getString(R.string.burn_tip3));
                tip_detail_message.setText(getResources().getString(R.string.burn_tip1_detail3));

                break;
            case 3:
                tip_detail_title.setText(getResources().getString(R.string.burn_tip4));
                tip_detail_message.setText(getResources().getString(R.string.burn_tip1_detail4));

                break;
            case 4:
                tip_detail_title.setText(getResources().getString(R.string.burn_tip5));
                tip_detail_message.setText(getResources().getString(R.string.burn_tip1_detail5));

                break;
            default:
                break;

        }
    }
}
