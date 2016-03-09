package com.burning.click.burnheadphone;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.os.PersistableBundle;

import com.burning.click.burnheadphone.handler.MyHandler;

/**
 * 基类
 * Created by click on 16-3-9.
 */
public class BaseActivity extends Activity{
    private String TAG="BaseActivity";
    private MyHandler myHandler =  new MyHandler(BaseActivity.this){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        initView();
        initViewData();
    }

    private void initViewData() {
    }
    private void initView() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        onDestroy();
    }
}
