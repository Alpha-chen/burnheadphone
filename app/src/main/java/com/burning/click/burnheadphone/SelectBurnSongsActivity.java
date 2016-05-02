package com.burning.click.burnheadphone;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;

import com.burning.click.burnheadphone.Log.LogUtil;
import com.burning.click.burnheadphone.adapter.SelectSongRecycleAdapter;
import com.burning.click.burnheadphone.constant.Constant;
import com.burning.click.burnheadphone.node.SongMessage;
import com.burning.click.burnheadphone.node.SongNode;
import com.burning.click.burnheadphone.node.SongNodes;
import com.burning.click.burnheadphone.node.StorageInfo;
import com.burning.click.burnheadphone.util.MediaUtils;
import com.burning.click.burnheadphone.util.ProgressUtil;
import com.burning.click.burnheadphone.util.StorageListUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SelectBurnSongsActivity extends BaseActivity {

    @Bind(R.id.select_burn_song_progressbar)
    ProgressBar progressBar; //

    @Bind(R.id.select_burn_song_recycler)
    RecyclerView recyclerView;

    private RecyclerView.LayoutManager layoutManager;
    private SelectSongRecycleAdapter selectSongRecycleAdapter;
    /**
     * 是否完成
     */
    private boolean isFinish = false;
    /**
     * 歌曲首数
     */
    private int songSize = 0;

    private ArrayList<SongNode> oldSongNodes = new ArrayList<>();

    private int notifyDataSize = 20; // 20条数据刷新一次列表
    private int m = 0;
    private ArrayList<SongNode> tempList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_burn_songs);
        ButterKnife.bind(this);
        TAG = "SelectBurnSongsActivity";
        initIntent();
        initView();
        initViewData();
        loadData();
    }


    @Override
    public boolean handleMessage(Message msg) {
        if (null == msg) return false;
        switch (msg.what) {
            case Constant.SEARCH_BURN_SONG_STATUS.SEARCH_SONG_START:
                ProgressUtil.showProgress(progressBar, true);
                // 开始扫描
                LogUtil.d("扫描开始");
                break;
            case Constant.SEARCH_BURN_SONG_STATUS.SEARCH_SONG_END:
                // 扫描完成
                ProgressUtil.showProgress(progressBar, false);
                LogUtil.d("扫描完成");
                LogUtil.d(TAG, "songNodes.size()=" + oldSongNodes.size());

                break;
            case Constant.SEARCH_BURN_SONG_STATUS.SEARCHING_SONG:
                LogUtil.d("扫描中");
                // 扫描中
                oldSongNodes.addAll(oldSongNodes.size(), (Collection<? extends SongNode>) msg.obj);
                selectSongRecycleAdapter.setData(oldSongNodes);
//                    recyclerView.scrollToPosition(oldSongNodes.size());
                tempList.clear();
                LogUtil.d(TAG, "oldSongNodes=" + oldSongNodes.size());
//                ToastUtil.makeText(SelectBurnSongsActivity.this, oldSongNodes.size() + "");
                break;
            default:
                break;
        }

        return super.

                handleMessage(msg);

    }

    @Override
    protected void initIntent() {
        super.initIntent();
        if (null == getIntent()) return;
        SongNodes songNodes = (SongNodes) getIntent().getSerializableExtra("oldSongNodes");
        if (null == songNodes) return;
        oldSongNodes = songNodes.getData();
    }

    @Override
    protected void initView() {
        super.initView();

//        mSwipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent, R.color.colorLight);
//        mSwipeRefreshLayout.setOnRefreshListener(this);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(SelectBurnSongsActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        selectSongRecycleAdapter = new SelectSongRecycleAdapter(SelectBurnSongsActivity.this);
        recyclerView.setAdapter(selectSongRecycleAdapter);
        recyclerView.addOnScrollListener(mListener);

    }

    @Override
    protected void initViewData() {
        super.initViewData();
    }


    private void loadData() {
        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... arg0) {
                scanStart();
                scaning();
                // try {
                // Thread.sleep(3000);
                // } catch (InterruptedException e) {
                // e.printStackTrace();
                // }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                scaned();
            }

        }.execute("");
    }

    /**
     * 扫描开始
     */
    private void scanStart() {
        myHandler.sendEmptyMessage(Constant.SEARCH_BURN_SONG_STATUS.SEARCH_SONG_START);
        isFinish = false;
    }

    /**
     * 扫描中
     */
    private void scaning() {
        scannerMusic();
    }

    /**
     * 扫描歌曲，从手机文件夹里面进行递归扫描
     */
    private void scannerMusic() {
        songSize = 0;
        List<StorageInfo> list = StorageListUtil
                .listAvaliableStorage(getApplicationContext());
        LogUtil.d("list", list.size() + "");
        for (int i = 0; i < list.size(); i++) {
            StorageInfo storageInfo = list.get(i);
            scannerLocalMP3File(storageInfo.path, ".mp3", true);
        }
    }

    public void selectBurnSong() {
        // 将扫描到的数据保存到播放列表
        SongNode songInfo = MediaUtils.getSongNodeByFile(SelectBurnSongsActivity.this);
        LogUtil.d(TAG, "SongSize=" + songSize);
        Log.d(TAG, "songInfo=" + songInfo.toString());
        if (songInfo == null) return;
//                            SongDB.getSongInfoDB(this).add(songInfo);
        songSize++;
        if (songSize > 1) {
            Message msg = myHandler.obtainMessage();
            msg.what = Constant.SEARCH_BURN_SONG_STATUS.SEARCHING_SONG;
            msg.obj = tempList;
            myHandler.sendMessage(msg);
            tempList = new ArrayList<>();
        } else {
            tempList.add(songInfo);
        }
    }

    /**
     * @param Path      搜索目录
     * @param Extension 扩展名
     *                  `x* @param IsIterative 是否进入子文件夹
     */
    public void scannerLocalMP3File(String Path, String Extension,
                                    boolean IsIterative) {
        File[] files = new File(Path).listFiles();
        if (files != null) {
            for (int i = 0; files.length > i; i++) {
                File f = files[i];

                if (f.isFile()) {
                    if (f.getPath().endsWith(Extension)) // 判断扩展名
                    {
                        if (!f.exists()) {
                            continue;
                        }
                        // 文件名
                        String displayName = f.getName();
                        if (displayName.endsWith(Extension)) {
                            String[] displayNameArr = displayName
                                    .split(Extension);
                            displayName = displayNameArr[0].trim();
                        }

//                        boolean isExists = SongDB.getSongInfoDB(this)
//                                .songIsExists(displayName);
//                        if (isExists) {
//                            continue;
//                        }
                        // 将扫描到的数据保存到播放列表
                        SongNode songInfo = MediaUtils.getSongNodeByFile(SelectBurnSongsActivity.this);
                        LogUtil.d(TAG, "SongSize=" + songSize);
                        Log.d(TAG, "songInfo=" + songInfo.toString());
                        if (songInfo == null) {
                            continue;
                        } else {
                            songSize++;
                            if (songSize > 1) {
                                Message msg = myHandler.obtainMessage();
                                msg.what = Constant.SEARCH_BURN_SONG_STATUS.SEARCHING_SONG;
                                msg.obj = tempList;
                                myHandler.sendMessage(msg);
                                tempList = new ArrayList<>();
                            } else {
                                tempList.add(songInfo);
                            }
                        }

                    }
                    if (!IsIterative)
                        break;
                } else if (f.isDirectory() && f.getPath().indexOf("/.") == -1) // 忽略点文件（隐藏文件/文件夹）
                {
                    scannerLocalMP3File(f.getPath(), Extension, IsIterative);
                }
            }
        }
    }

    /**
     * 扫描完成
     */
    private void scaned() {
        myHandler.sendEmptyMessage(Constant.SEARCH_BURN_SONG_STATUS.SEARCH_SONG_END);
        isFinish = true;

        SongMessage songMessage = new SongMessage();
        songMessage.setType(SongMessage.SCANEDMUSIC);
        // 通知
//        ObserverManage.getObserver().setMessage(songMessage);
    }

    private RecyclerView.OnScrollListener mListener = new RecyclerView.OnScrollListener() {


        private int lastVisibItem;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibItem + 1 == selectSongRecycleAdapter.getItemCount()) {
                //加载更多
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

        }

    };

}
