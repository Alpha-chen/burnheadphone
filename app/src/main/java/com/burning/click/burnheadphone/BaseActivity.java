package com.burning.click.burnheadphone;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.burning.click.burnheadphone.Log.LogUtil;
import com.burning.click.burnheadphone.util.KeyBoardUtil;

/**
 * 基类
 * Created by click on 16-3-9.
 */
public class BaseActivity extends AppCompatActivity implements Handler.Callback, View.OnClickListener {
    protected String TAG = "BaseActivity";
    protected Handler myHandler = new Handler(Looper.getMainLooper(), this);


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    protected void initMethod() {

    }

    protected void initViewData() {
    }

    protected void initView() {
    }

    protected void initResponseHandler() {
    }

    ;

    protected void initIntent() {
    }

    ;

    protected void initButterKnifeClick() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        KeyBoardUtil.hideKeyBoard(BaseActivity.this, getCurrentFocus());
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void finish() {
        super.finish();
        KeyBoardUtil.hideKeyBoard(BaseActivity.this, getCurrentFocus());
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        onDestroy();
    }

    @Override
    public boolean handleMessage(Message msg) {
        LogUtil.d(62);
        return true;
    }


    @Override
    public void onClick(View v) {
    }
}
