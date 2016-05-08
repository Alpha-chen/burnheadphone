package com.burning.click.burnheadphone.constant;

/**
 * Created by click on 16-3-22.
 */
public interface Constant {

    interface NET_WHAT {
        int EMPTY_MESSAGE = 1000;
        int SUCCESS_MESSAGE = EMPTY_MESSAGE + 1;
        int UNKNOW_ERROR = SUCCESS_MESSAGE + 1;
        int ERROR_404 = UNKNOW_ERROR + 1;
        int ERROR_408 = ERROR_404 + 1;
    }

    interface WHAT {
        int EMPTY_SUCCESS = 2000;
        int EMPTY_FAILE = EMPTY_SUCCESS + 1;

    }

    /**
     * startActivityForResult 的返回标示吗
     */
    interface RESULT_CODE {
        int MODE_CODE = 3000;
        int ADD_MODE_CODE = MODE_CODE + 1; // 新增模式
        int EDIT_MODE_CODE = ADD_MODE_CODE + 1; // 编辑模式
        int SELECT_BURN_SONG = EDIT_MODE_CODE + 1; // 选择 煲耳机音乐
        int SELECT_BURN_SONG_TIME = SELECT_BURN_SONG + 1; // 选择 煲耳机 时间


    }


    /***
     * 煲耳机时间
     */
    public static int BURN_TIME = 0; //  30 分钟   60  180  999 永远


    /**
     * 扫描本地歌曲列表
     */
    interface SEARCH_BURN_SONG_STATUS {
        int SEARCH_SONG_START = 4000; // K开始扫扫描
        int SEARCHING_SONG = SEARCH_SONG_START + 1; // 扫描中
        int SEARCH_SONG_END = SEARCHING_SONG + 1; // 扫描完成
    }

    /**
     * 煲耳机界面数据变换
     */

    interface NOTIFY_MODE_LIST {

        int NOTIFY_MODE_LIST = 4000; // 从 模式编辑界面进入到煲耳机界面

    }

    /**
     * 加载图片 的uri
     */
    interface IMAGE_URI {
        String FRESCO_IMAGE_URI = "res://com.burning.click.burnheadphone/";
    }
}
