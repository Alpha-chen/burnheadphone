package com.burning.click.burnheadphone.net;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.burning.click.burnheadphone.Constant;
import com.burning.click.burnheadphone.ResponseHandler.BaseResponseHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by click on 16-3-17.
 */
public class BHPHttpClient {

    private static BHPHttpClient bhpHttpClient;
    private OkHttpClient okHttpClient;
    private static ArrayList<Request> httpRequestList= new ArrayList<>();
    private LinkedList<Runnable> queues;

    private Handler myHandler;

    private ExecutorService mThreadPool;
    private Thread mThread;
    private Semaphore mSemaphorePool;
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

    public BHPHttpClient(){
        initClient();
    }

    private void initClient() {
        if (null==okHttpClient){
            okHttpClient =  new OkHttpClient();
        }
        myHandler= new Handler(Looper.getMainLooper());
        queues= new LinkedList<>();
        // 这个是模仿的
        mThreadPool = Executors.newFixedThreadPool(6);
        mSemaphorePool = new Semaphore(6);
        mThread = new Thread(){
            @Override
            public void run() {
                super.run();
                Looper.prepare();
                myHandler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Runnable runnable = getBHPTask();
                        if (runnable!=null){
                            mThreadPool.execute(runnable);
                        }
                    }
                };
                Looper.loop();
            }
        };
        mThread.start();
    }

    private Runnable getBHPTask() {
        if (queues==null || queues.size()==0){
            return null;
        }
        try
        {
            return  queues.removeFirst();
        }catch (Exception e){
            return  null;
        }

    }

    private synchronized void addBHPTask(Runnable runnable){
        queues.add(runnable);
        myHandler.sendEmptyMessage(0x110);

    }

    public   void enque(final Request request, final BaseResponseHandler responseHandler){
        if (request==null){
            return;
        }
            addBHPTask(buildBHPTask(request,responseHandler));
    }

    private Runnable buildBHPTask(final Request request, final BaseResponseHandler responseHandler) {
    return new Runnable() {
        @Override
        public void run() {
            try {
                mSemaphorePool.acquire();
                if (request==null&&responseHandler !=null){
                    responseHandler.sendEmptyMessage(Constant.NET_WHAT.EMPTY_MESSAGE);
                }
                Response response;
                try {
                     response = okHttpClient.newCall(request).execute();
                    if (response==null){
                        return;
                    }
                    if (response.cacheControl()==null){
                        return;
                    }
                    if (responseHandler==null){
                        return;
                    }
                    // 先读取缓存
                    if (response.isSuccessful()&&response.cacheResponse()!=null){
                        responseHandler.sendSuccessMessage(Constant.NET_WHAT.SUCCESS_MESSAGE,response.networkResponse());
                    }else if (response.isSuccessful()){
                            responseHandler.sendSuccessMessage(Constant.NET_WHAT.SUCCESS_MESSAGE,response.networkResponse());
                    }else if (!response.isSuccessful()){
                        if (response.code()==404){
                            responseHandler.sendEmptyMessage(Constant.NET_WHAT.ERROR_404);
                        }else if (response.code()==408){
                            responseHandler.sendEmptyMessage(Constant.NET_WHAT.ERROR_408);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if (responseHandler!=null){
                        responseHandler.sendEmptyMessage(Constant.NET_WHAT.UNKNOW_ERROR);
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                mSemaphorePool.release();
            }

        }
    };

    }


}
