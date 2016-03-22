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

    public void onSuccessMessage(Response response){
    }


    public  void onFaileMesage(Response response){
    }


    /**
     *  得到信息之后发送出去
     * @param what
     * @param response
     */
    public void sendSuccessMessage(int what , Response response){

    }

    public void sendCacheSuccessMessage(int what , Response response){

    }
    public void sendCacheFailMessage(int what , Response response){

    }

    /**
     *
     * @param whar
     * @param response
     */
    public void sendFaileMessage(int whar, Response response){

    }

    /**
     * 处理错误码
     */
    private void handleData(Object result){


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
