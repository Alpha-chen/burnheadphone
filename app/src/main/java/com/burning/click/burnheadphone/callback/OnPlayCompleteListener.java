package com.burning.click.burnheadphone.callback;

/**
 * 当歌曲播放完毕之后,更新界面中显示的歌曲名字
 * Created by click on 16-5-19.
 */
public interface OnPlayCompleteListener {
    void onSongComplete(int selectModePosition,int songPosition);

}
