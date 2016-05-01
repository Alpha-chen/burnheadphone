package com.burning.click.burnheadphone;

import android.os.Bundle;

import butterknife.ButterKnife;

public class SelectBurnSongsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_burn_songs);
        ButterKnife.bind(this);
    }
}
