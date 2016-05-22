package com.burning.click.burnheadphone.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import com.burning.click.burnheadphone.Log.LogUtil;
import com.burning.click.burnheadphone.callback.OnPlayCompleteListener;
import com.burning.click.burnheadphone.constant.Constant;
import com.burning.click.burnheadphone.node.BurnModeNodes;
import com.burning.click.burnheadphone.node.UserNode;
import com.burning.click.burnheadphone.sp.SpUtils;
import com.burning.click.burnheadphone.util.FileUtil;
import com.burning.click.burnheadphone.util.SpkeyName;

import java.io.IOException;

/**
 * Created by click on 16-5-18.
 */
public class PlayerService extends Service implements Runnable,
        MediaPlayer.OnCompletionListener {
    public static MediaPlayer mMediaPlayer = null;
    private static boolean isLoop = false;
    private int MSG;

    private BurnModeNodes playBurnNodes = null;
    private int selectModePosition = -1; // 选中哪一个模式

    private int playingSongNodesPosition = -1; //  播放某一个模式下的第几个歌曲

    private int songDuration = -1; //  某一首歌曲的长度
    public static OnPlayCompleteListener onPlayCompleteListener;

    private boolean isRobot = false;

    public void setOnPlayCompleteListener(OnPlayCompleteListener onPlayCompleteListener) {
        this.onPlayCompleteListener = onPlayCompleteListener;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnCompletionListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }

        System.out.println("service onDestroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (null == intent) return -1;
        selectModePosition = intent.getIntExtra("selectModePosition", -1);
        playBurnNodes = (BurnModeNodes) intent.getSerializableExtra("burnModeList");
        MSG = intent.getIntExtra("MSG", Constant.PLAY_AUDIO.PLAY_AUDIO_START);
        isRobot = intent.getBooleanExtra("isRobot", false);
        if (MSG == Constant.PLAY_AUDIO.PLAY_AUDIO_START) {
            if (isRobot) {
                playMusic1(0);
            } else {
                playMusic(0);
            }
        }
        if (MSG == Constant.PLAY_AUDIO.PLAY_AUDIO_PAUSE) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
            } else {
                mMediaPlayer.start();
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 播放列表中的哪一个position上的音频文件
     *
     * @param position
     */
    public void playMusic(int position) {
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(
                    playBurnNodes.getData().get(selectModePosition).getSongNodes().getData().get(position).getFilePath());
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            SpkeyName.BURN_STATUS = 1;
            mMediaPlayer.setLooping(isLoop);
            songDuration = mMediaPlayer.getDuration();
            new Thread(this).start();
        } catch (IOException e) {
        }

    }


    /**
     * 播放列表中的哪一个position上的音频文件
     *
     * @param position
     */
    public void playMusic1(int position) {
        try {
            mMediaPlayer.reset();
            if (0 == position) {
                mMediaPlayer.setDataSource(
                        FileUtil.app_path + FileUtil.bhp_mp3_file + FileUtil.pinkMp3);
            } else {
                mMediaPlayer.setDataSource(
                        FileUtil.app_path + FileUtil.bhp_mp3_file + FileUtil.whiteMp3);
            }
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            SpkeyName.BURN_STATUS = 1;
            mMediaPlayer.setLooping(isLoop);
            songDuration = mMediaPlayer.getDuration();
            new Thread(this).start();
        } catch (IOException e) {
        }

    }

    // ˢ�½�����
    @Override
    public void run() {
        int CurrentPosition = 0;// ����Ĭ�Ͻ�������ǰλ��
        int total = mMediaPlayer.getDuration();//
        while (mMediaPlayer != null && CurrentPosition < total) {
            try {
                Thread.sleep(1000);
                if (mMediaPlayer != null) {
                    CurrentPosition = mMediaPlayer.getCurrentPosition();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (isRobot) {
            playMusic1(1);
            onPlayCompleteListener.onSongComplete(-1, -1);
        } else {
            playBurnNodes.getData().get(selectModePosition).setHasBurnTime(playBurnNodes.getData().get(selectModePosition).getHasBurnTime() + songDuration);
            ++playingSongNodesPosition;
            if (playBurnNodes.getData().get(selectModePosition).getHasBurnTime() * 3600 < playBurnNodes.getData().get(selectModePosition).getBurnModeTime() * 3600) {
                if (playingSongNodesPosition < playBurnNodes.getData().get(selectModePosition).getSongNodes().getData().size()) {
                    Toast.makeText(PlayerService.this, "已经到达设定的煲耳机时间", Toast.LENGTH_LONG).show();
                    SpkeyName.HEADSETSTATE = 0; //  煲耳机停止
                    return;
                } else {
                    Toast.makeText(PlayerService.this, "将要重新播放歌单", Toast.LENGTH_SHORT)
                            .show();
                    playingSongNodesPosition = 0;
                }
            }

            playMusic(playingSongNodesPosition);
            String burnModeList = BurnModeNodes.toJson(playBurnNodes);
            onPlayCompleteListener.onSongComplete(selectModePosition, playingSongNodesPosition);
            SpUtils.put(PlayerService.this, SpUtils.BHP_SHARF, UserNode.getmUserNode().getUid() + "", burnModeList);
        }
    }


    public ServiceBinder mBinder = new ServiceBinder(); /* 数据通信的桥梁 */

    /* 第一种模式通信：Binder */
    public class ServiceBinder extends Binder {

        public void stop() throws InterruptedException {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
            }
        }

        public boolean isPlaying() {
            LogUtil.d("service", mMediaPlayer.isPlaying() + "");
            return mMediaPlayer.isPlaying();
        }
    }
}