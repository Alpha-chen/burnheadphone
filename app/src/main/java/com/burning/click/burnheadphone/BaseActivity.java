package com.burning.click.burnheadphone;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.view.KeyEvent;
import android.view.View;

import com.burning.click.burnheadphone.handler.MyHandler;
import com.burning.click.burnheadphone.util.KeyBoardUtil;

/**
 * 基类
 * Created by click on 16-3-9.
 */
public class BaseActivity extends Activity implements Handler.Callback,View.OnClickListener{
    private String TAG="BaseActivity";
    private MyHandler myHandler =  new MyHandler(BaseActivity.this);

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }
    protected void  initMethod(){

    }

    protected void initViewData() {
    }
    protected void initView() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        KeyBoardUtil.hideKeyBoard(this);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        onDestroy();
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}
