package com.burning.click.burnheadphone.ResponseHandler;

import android.content.Context;
import android.os.Message;

import com.burning.click.burnheadphone.handler.MyHandler;

import okhttp3.Response;


/**
 * Created by click on 16-3-16.
 */
public class BaseResponseHandler extends MyHandler {

    public BaseResponseHandler(Context context) {
        super(context);
    }

    public Response onSuccessMessage(Response response){
        return response;
    }


    public  Response onFaileMesage(Response response){
        return response;
    }

    /**
     * 处理错误码
     */
    public void handleData(Object result){


    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
    }
}
