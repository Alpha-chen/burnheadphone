package com.burning.click.burnheadphone.net;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.burning.click.burnheadphone.Log.LogUtil;
import com.burning.click.burnheadphone.constant.Constant;
import com.burning.click.burnheadphone.ResponseHandler.BaseResponseHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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

    /**
     * 知情求网络
     * @param request
     */
    public void enque(final Request request){

        if (request==null){
            return;
        }

        addBHPTask(buildBHPTask(request,null));
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
                httpRequestList.add(request);
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
                        LogUtil.d(11111111);
                        responseHandler.sendSuccessMessage(Constant.NET_WHAT.SUCCESS_MESSAGE,response.networkResponse());
                    }else if (response.isSuccessful()){
                        LogUtil.d(22222222);
                            responseHandler.sendSuccessMessage(Constant.NET_WHAT.SUCCESS_MESSAGE,response.networkResponse());
                    }else if (!response.isSuccessful()){
                        if (response.code()==404){
                            LogUtil.d(404);
                            responseHandler.sendEmptyMessage(Constant.NET_WHAT.ERROR_404);
                        }else if (response.code()==408){
                            LogUtil.d(408);
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

    public void cancle(final Request request){
        if (request==null){
            return;
        }
        Call call =okHttpClient.newCall(request);
        if (!call.isCanceled()){
            call.cancel();
        }
    }

    /**
     * 停止所有的请求
     */
    public void cancle(){
        if (null==httpRequestList||httpRequestList.size()==0){
            return;
        }
        int temp =httpRequestList.size();
        for (int i=0;i<temp;i++){
            Call call = okHttpClient.newCall(httpRequestList.get(i));
            if (!call.isCanceled()){
                call.cancel();
            }
        }
    }

    /**
     * 自定义构建请求
     */
    public static Request getRequest(String url, RequestBody requestBody){
        if (TextUtils.isEmpty(url)||null==requestBody){
            return null;
        }
        return new Request.Builder().url(url).post(requestBody).build();
    }

    public static Request getRequest(String url){
        if (TextUtils.isEmpty(url)){
            return null;
        }
        return new Request.Builder().url(url).build();
    }

    public static Request getRequest(String url, CacheControl cacheControl){
        if (TextUtils.isEmpty(url)||null==cacheControl){
            return null;
        }
        return new Request.Builder().url(url).cacheControl(cacheControl).build();
    }

}
