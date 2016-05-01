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
        int SELECT_BURN_SONG=EDIT_MODE_CODE+1; // 选择 煲耳机音乐
    }

    /**
     * 加载图片 的uri
     */
    interface IMAGE_URI {
        String FRESCO_IMAGE_URI = "res://com.burning.click.burnheadphone/";
    }
}
