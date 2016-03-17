package com.burning.click.burnheadphone.ResponseHandler;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import okhttp3.Response;


/**
 * Created by click on 16-3-16.
 */
public class BaseResponseHandler extends Handler {

    public BaseResponseHandler(Context context){

    }

    protected void onSuccessMessage(Response response){
    }


    protected  void onFaileMesage(Response response){
    }


    /**
     *  得到信息之后发送出去
     * @param what
     * @param response
     */
    protected void sendSuccessMessage(int what , Response response){


    }

    /**
     *
     * @param whar
     * @param response
     */
    protected void sendFaileMessage(int whar, Response response){

    }
    /**
     * 处理错误码
     */
    protected void handleData(Object result){


    }

    public void onSuccess(Response response){

    }

    public void  onFailure(Response response){

    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
    }
}
