package com.burning.click.burnheadphone.net;

import android.os.Handler;
import android.os.Looper;

import com.burning.click.burnheadphone.BHPApplication;
import com.burning.click.burnheadphone.ResponseHandler.BaseResponseHandler;

import java.util.LinkedList;
import java.util.Queue;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by click on 16-3-17.
 */
public class BHPHttpClient {

    private static BHPHttpClient bhpHttpClient;
    private OkHttpClient okHttpClient;

    private LinkedList<Queue> queues;

    private Handler myHandler;

    private BaseResponseHandler responseHandler;
    public BHPHttpClient(){
        okHttpClient =  new OkHttpClient();
        myHandler= new Handler(Looper.getMainLooper());
        queues= new LinkedList<>();
        responseHandler = new BaseResponseHandler(BHPApplication.mApplication);

    }

    public static BHPHttpClient getInstance(){
        if (bhpHttpClient==null){
            synchronized (BHPHttpClient.class){
                if (bhpHttpClient==null){
                    bhpHttpClient = new BHPHttpClient();

                }
            }
        }
            return bhpHttpClient;
    }

    private  void enque(Request request, BaseResponseHandler responseHandler){


    }

    


}
