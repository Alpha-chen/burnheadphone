package com.burning.click.burnheadphone.asyntask;

import android.os.AsyncTask;

import com.burning.click.burnheadphone.net.BHPHttpClient;
import com.burning.click.burnheadphone.util.ApiUtil;
import com.burning.click.burnheadphone.util.FileUtil;

/**
 * Created by click on 16-5-21.
 */
public class DownLoadAsync extends AsyncTask {


    @Override
    protected Object doInBackground(Object[] params) {
        boolean isOk = BHPHttpClient.getInstance()._downloadAsyn(ApiUtil.BHP_MUSIC_URL + FileUtil.pinkMp3, FileUtil.app_path+FileUtil.bhp_mp3_file );
        boolean isOk1 = BHPHttpClient.getInstance()._downloadAsyn(ApiUtil.BHP_MUSIC_URL + FileUtil.whiteMp3, FileUtil.app_path+FileUtil.bhp_mp3_file );
        if (isOk && isOk1) {
            return true;
        }
        return false;
    }



}
