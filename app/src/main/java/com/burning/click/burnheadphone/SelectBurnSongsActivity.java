package com.burning.click.burnheadphone;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.burning.click.burnheadphone.Log.LogUtil;
import com.burning.click.burnheadphone.adapter.SelectSongRecycleAdapter;
import com.burning.click.burnheadphone.constant.Constant;
import com.burning.click.burnheadphone.node.SongMessage;
import com.burning.click.burnheadphone.node.SongNode;
import com.burning.click.burnheadphone.node.SongNodes;
import com.burning.click.burnheadphone.util.MediaUtils;
import com.burning.click.burnheadphone.util.ProgressUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择 歌曲界面
 */
public class SelectBurnSongsActivity extends BaseActivity implements SelectSongRecycleAdapter.OnItemClickListener {

    @Bind(R.id.select_burn_song_progressbar)
    ProgressBar progressBar; //

    @Bind(R.id.select_burn_song_recycler)
    RecyclerView recyclerView;

    @Bind(R.id.select_burn_song_ok)
    ImageView select_burn_song_ok;

    @OnClick(R.id.select_burn_song_ok)
    void selectBurnSongOK() {
        selectSong();
    }

    private ArrayList<SongNode> mSongNodes = new ArrayList<>();
    private SongNodes selectSongNodes = new SongNodes();
    private LinearLayoutManager layoutManager;
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_select_burn_songs);
        ButterKnife.bind(this);
        TAG = "SelectBurnSongsActivity";
        initIntent();
        initView();
        initViewData();
        loadData();
    }

    public void selectSong() {
        if (!CheckSongSize()) return;
        Intent intent = new Intent();
        intent.putExtra("selectSongNodes", selectSongNodes);
        setResult(Constant.RESULT_CODE.SELECT_BURN_SONG, intent);
        finish();
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
                ArrayList<SongNode> temp = (ArrayList<SongNode>) msg.obj;
                if (0 < oldSongNodes.size()) {
                    for (int j = 0; j < oldSongNodes.size(); j++) {
                        for (int i = 0; i < temp.size(); i++) {
                            if (temp.get(i).getTitle().equals(oldSongNodes.get(j).getTitle())) {
                                temp.get(i).setIsSelect(1);
                            }
                        }
                    }
                    oldSongNodes.clear();
                }
                oldSongNodes.addAll(temp);
                selectSongRecycleAdapter.setData(oldSongNodes);
                LogUtil.d(TAG, "oldSongNodes=" + oldSongNodes.size());
                break;
            default:
                break;
        }
        return super.
                handleMessage(msg);
    }

    public boolean CheckSongSize() {
        if (mSongNodes.size() > 10) {
            Toast.makeText(SelectBurnSongsActivity.this, "不能超过十首歌曲~~", Toast.LENGTH_SHORT).show();
            return false;
        }
        selectSongNodes.setData(mSongNodes);
        return true;
    }

    @Override
    protected void initIntent() {
        super.initIntent();
        if (null == getIntent()) return;
        SongNodes songNodes = (SongNodes) getIntent().getSerializableExtra("oldSongNodes");
        LogUtil.d(TAG, "songNodes=" + songNodes);
        LogUtil.d(148);
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
        selectSongRecycleAdapter.setOnItemClickListener(this);
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
        selectBurnSong();
    }

    public void selectBurnSong() {
        // 将扫描到的数据保存到播放列表
        ArrayList<SongNode> songInfo = MediaUtils.getSongNodeByFile(getApplicationContext());
        Message msg = myHandler.obtainMessage();
        msg.what = Constant.SEARCH_BURN_SONG_STATUS.SEARCHING_SONG;
        msg.obj = songInfo;
        myHandler.sendMessage(msg);
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
                LogUtil.d(TAG, "ssssssssssssssssssssssssss");
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibItem = layoutManager.findLastVisibleItemPosition();
        }

    };

    @Override
    public void onItemLonClickListener(int position) {

    }

    @Override
    public void onItemClickListener(int position) {
        if (null == oldSongNodes.get(position)) return;
        playAudio(oldSongNodes.get(position).getFilePath());
    }

    @Override
    public void selectSong(int positions) {
        if (null == oldSongNodes.get(positions)) return;
        oldSongNodes.get(positions).setIsSelect(1);
        mSongNodes.add(oldSongNodes.get(positions));
    }

    @Override
    public void removeSong(int position) {
        if (mSongNodes.contains(oldSongNodes.get(position)))
            mSongNodes.remove(oldSongNodes.get(position));
    }

    private void playAudio(String audioPath) {
        if (null == audioPath) throw new NullPointerException("audioPath 为空");
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(audioPath), "audio/mp3");
//        intent.setComponent(new ComponentName("com.android.music", "com.android.music.MediaPlaybackActivity"));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }
}
