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
    protected void onFaileMesage(Response response) {
        super.onFaileMesage(response);
    }

    @Override
    protected void onSuccessMessage(Response response) {
        super.onSuccessMessage(response);
    }
}
