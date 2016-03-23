package com.burning.click.burnheadphone.ResponseHandler;

import android.content.Context;

import okhttp3.Response;

/**
 * Created by click on 16-3-16.
 */
public class LoginResponseHandler extends BaseResponseHandler {
    public LoginResponseHandler(Context context) {
        super(context);
    }

    @Override
    public void onFaileMesage(Response response) {
        super.onFaileMesage(response);
        if (null==response){
            return;
        }
        handleData(response);
        postFaileMessage(response);
    }

    @Override
    public void onSuccessMessage(Response response) {
        super.onSuccessMessage(response);
        if (null==response){
            return;
        }
        postSuccessMessage(response);
    }
}
