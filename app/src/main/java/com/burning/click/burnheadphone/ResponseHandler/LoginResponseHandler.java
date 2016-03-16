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
    public Response onFaileMesage(Response response) {
        if (!response.networkResponse().isSuccessful()){
            handleData(response.networkResponse().message());
        }
        return super.onFaileMesage(response);
    }

    @Override
    public Response onSuccessMessage(Response response) {
        return super.onSuccessMessage(response);
    }
}
