package com.burning.click.burnheadphone.constant;

/**
 * Created by click on 16-3-22.
 */
public interface Constant {
    interface NET_WHAT{
        int EMPTY_MESSAGE=1000;
        int SUCCESS_MESSAGE=EMPTY_MESSAGE+1;
        int UNKNOW_ERROR=SUCCESS_MESSAGE+1;
        int ERROR_404=UNKNOW_ERROR+1;
        int ERROR_408=ERROR_404+1;
    }

    interface  WHAT{
        int EMPTY_SUCCESS=2000;
        int EMPTY_FAILE=EMPTY_SUCCESS+1;
    }
}
