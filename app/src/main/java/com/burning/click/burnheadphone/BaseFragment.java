package com.burning.click.burnheadphone;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

/**
 * Created by click on 16-4-30.
 */
public class BaseFragment extends Fragment implements Handler.Callback{
    private String TAG= "BaseFragment";

//    private Handler handler = new Handler(Looper.getMainLooper());

    protected void  initMethod(){

    }

    protected void initViewData() {
    }
    protected void initView() {
    }
    protected void initResponseHandler(){};

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }
}
