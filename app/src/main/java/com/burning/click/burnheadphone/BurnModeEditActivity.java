package com.burning.click.burnheadphone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.burning.click.burnheadphone.Log.LogUtil;
import com.burning.click.burnheadphone.constant.Constant;
import com.burning.click.burnheadphone.node.BurnModeNode;
import com.burning.click.burnheadphone.node.SongNodes;
import com.burning.click.burnheadphone.node.UserNode;
import com.burning.click.burnheadphone.sp.SpUtils;
import com.burning.click.burnheadphone.util.SpkeyName;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 编辑煲耳机模式
 */
public class BurnModeEditActivity extends BaseActivity {
    /**
     * 模式名称
     */
    @Bind(R.id.burn_mode_name_lay)
    RelativeLayout burn_mode_name_lay;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @OnClick(R.id.burn_mode_name_lay)
    void burn_mode_name_lay() {
        showEditModeName();
    }

    @Bind(R.id.mode_name_)
    TextView mode_name_;
    /**
     * 煲耳机歌曲选择
     */
    @Bind(R.id.burn_mode_music_lay)
    RelativeLayout burn_mode_music_lay;

    @OnClick(R.id.burn_mode_music_lay)
    void burn_mode_music_lay() {
        selectBurnSong();
    }

    @Bind(R.id.burn_mode_song_size)
    TextView burn_mode_song_size;

    @Bind(R.id.burn_mode_image)
    ImageView burn_mode_image;
    /**
     * 该模式下煲耳机时长
     */
    @Bind(R.id.burn_mode_time_lay)
    RelativeLayout burn_mode_time_lay;

    @OnClick(R.id.burn_mode_time_lay)
    void burn_mode_time_lay() {
        editBurnTime();
    }


    @Bind(R.id.burn_mode_time_select)
    ImageView burn_mode_time_select;
    @Bind(R.id.mode_name_time_select_)
    TextView mode_name_time_select_;
    /**
     * 记录煲耳机时长
     */
    @Bind(R.id.burn_mode_record_time_lay)
    RelativeLayout burn_mode_record_time_lay;

    @OnClick(R.id.burn_mode_record_time_lay)
    void setBurn_mode_record_time_lay() {

    }

    @Bind(R.id.burn_mode_record_switch)
    Switch burn_mode_record_switch;


    @Bind(R.id.burn_mode_record_time)
    TextView burn_mode_record_time;

    /***
     * 已经包耳机时长显示
     */
    @Bind(R.id.burn_mode_had_burn_time)
    RelativeLayout burn_mode_had_burn_time;
    @Bind(R.id.burn_mode_had_burn_time_display_txt)
    TextView burn_mode_had_burn_time_display_txt;
    /**
     * 保存
     */
    @Bind(R.id.edit_burn_mode_ok)
    ImageView edit_burn_mode_ok;

    @OnClick(R.id.edit_burn_mode_ok)
    void edit_burn_mode_ok() {
        Intent intent = new Intent();
        intent.putExtra("modeStatus", modeStatus);
        if (!burnModeNode.compareTo(oldBurnModeNode)) {
            burnModeNode.setId(UserNode.getmUserNode().getUid());
            intent.putExtra("burnModeNode", burnModeNode);
        } else {
            // 不做修改
            intent.putExtra("burnModeNode", burnModeNode);
        }
        setResult(Constant.RESULT_CODE.MODE_CODE, intent);
        finish();
    }

    private EditText edit_mode_name;

    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;

    private BurnModeNode burnModeNode;
    private BurnModeNode oldBurnModeNode;
    private int modeStatus;// "0"添加模式 "1" 编辑模式

    private boolean hasSongList = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burn_mode);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
        initIntent();
        initViewData();

    }

    @Override
    protected void initIntent() {
        super.initIntent();
        burnModeNode = new BurnModeNode();
        if (null == getIntent()) return;
        modeStatus = getIntent().getIntExtra("modeStatus", -1);
        oldBurnModeNode = (BurnModeNode) getIntent().getSerializableExtra("BurnModeNode");
        if (null == oldBurnModeNode) return;
        burnModeNode = burnModeNode.copyTo(oldBurnModeNode); // 将老的数据复制给新的node
    }

    @Override
    protected void initViewData() {
        super.initViewData();
        if (null == oldBurnModeNode) return;
        if (!TextUtils.isEmpty(oldBurnModeNode.getName()))
            mode_name_.setText(oldBurnModeNode.getName());
        if (null != oldBurnModeNode.getSongNodes()) {
            if (0 != oldBurnModeNode.getSongNodes().getData().size()) {
                burn_mode_image.setVisibility(View.GONE);
                burn_mode_song_size.setVisibility(View.VISIBLE);
                burn_mode_song_size.setText(oldBurnModeNode.getSongNodes().getData().size() + "首");
                hasSongList = true;
            }
        }

        if (0 != oldBurnModeNode.getBurnModeTime()) {
            burn_mode_time_select.setVisibility(View.GONE);
            mode_name_time_select_.setVisibility(View.VISIBLE);
            mode_name_time_select_.setText(oldBurnModeNode.getBurnModeTime() + "");
        }
        if (1 == oldBurnModeNode.getRecordTimeStatus()) {
            burn_mode_record_switch.setChecked(true);
            burn_mode_had_burn_time.setVisibility(View.VISIBLE);
            if (0 != oldBurnModeNode.getHasBurnTime()) {
                burn_mode_had_burn_time_display_txt.setText(getString(oldBurnModeNode.getHasBurnTime(), getResources().getString(R.string.edit_mode_had_burn_time_display)));
            } else {
                burn_mode_had_burn_time_display_txt.setText(getString(oldBurnModeNode.getHasBurnTime(), getResources().getString(R.string.edit_mode_had_burn_time_display)));
            }
        } else {
            burn_mode_record_switch.setChecked(false);
        }

    }


    @Override
    protected void initView() {
        super.initView();
        burn_mode_record_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SpUtils.put(BurnModeEditActivity.this, SpUtils.BHP_SHARF, SpkeyName.RECORD_BURN_TIME_STATUS, 1);
                    int burn_time = SpUtils.getInt(BurnModeEditActivity.this, SpUtils.BHP_SHARF, SpkeyName.RECORD_BURN_TIME, 0);
                    if (0 == burn_time) {
                        burn_mode_had_burn_time.setVisibility(View.GONE);
                    } else {
                        burn_mode_had_burn_time.setVisibility(View.VISIBLE);
                        burn_mode_had_burn_time_display_txt.setText(getString(burn_time, getResources().getString(R.string.edit_mode_had_burn_time_display)));
                    }
                } else {
                    SpUtils.put(BurnModeEditActivity.this, SpUtils.BHP_SHARF, SpkeyName.RECORD_BURN_TIME_STATUS, 0);
                    SpUtils.put(BurnModeEditActivity.this, SpUtils.BHP_SHARF, SpkeyName.RECORD_BURN_TIME, 0);
                }
            }
        });

    }

    @Override
    protected void initMethod() {
        super.initMethod();
    }

    /**
     * 显示模式命名的对话框
     */
    private void showEditModeName() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.sigle_line_text_input, null);
        edit_mode_name = (EditText) view.findViewById(R.id.single_line_input);
        TextView cancle = (TextView) view.findViewById(R.id.edit_mode_cancle);
        TextView ok = (TextView) view.findViewById(R.id.edit_mode_ok);
        cancle.setOnClickListener(this);
        ok.setOnClickListener(this);
        builder = new AlertDialog.Builder(this);
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * 选择煲耳机时间
     */
    private void editBurnTime() {
        Intent intent = new Intent();
        intent.setClass(BurnModeEditActivity.this, SelectBurnTimeActivity.class);
        if (null == burnModeNode) {
            intent.putExtra("burnTime", oldBurnModeNode.getBurnModeTime());
        } else {
            intent.putExtra("burnTime", burnModeNode.getBurnModeTime());
        }
        startActivityForResult(intent, Constant.RESULT_CODE.SELECT_BURN_SONG_TIME);
    }


    /**
     * 选择煲耳机的音乐
     */
    private void selectBurnSong() {
        String songsId[] = null;
        Intent intent = new Intent();
        intent.setClass(BurnModeEditActivity.this, SelectBurnSongsActivity.class);
        if (null == oldBurnModeNode && !hasSongList) {
            LogUtil.d(228);
            startActivityForResult(intent, Constant.RESULT_CODE.SELECT_BURN_SONG);
            return;
        }
        // 存在数据的时候传给下一个界面
        if (null != burnModeNode.getSongNodes() && hasSongList) {
            intent.putExtra("oldSongNodes", burnModeNode.getSongNodes());
            LogUtil.d(235);
        } else if (null != oldBurnModeNode.getSongNodes()) {
            LogUtil.d(237);
            intent.putExtra("oldSongNodes", oldBurnModeNode.getSongNodes());
        }
        startActivityForResult(intent, Constant.RESULT_CODE.SELECT_BURN_SONG);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null == data) return;
        switch (resultCode) {
            case Constant.RESULT_CODE.SELECT_BURN_SONG:
                SongNodes songNodes = new SongNodes();
                songNodes = (SongNodes) data.getSerializableExtra("selectSongNodes");
                burnModeNode.setSongNodes(songNodes);
                burn_mode_image.setVisibility(View.GONE);
                burn_mode_song_size.setVisibility(View.VISIBLE);
                burn_mode_song_size.setText(burnModeNode.getSongNodes().getData().size() + "首");
                hasSongList = true;
                break;
            case Constant.RESULT_CODE.SELECT_BURN_SONG_TIME:
                burnModeNode.setBurnModeTime(data.getIntExtra("selectBurnTime", 0));
                if (0 != burnModeNode.getBurnModeTime()) {
                    burn_mode_time_select.setVisibility(View.GONE);
                    mode_name_time_select_.setVisibility(View.VISIBLE);
                    mode_name_time_select_.setText(burnModeNode.getBurnModeTime() + "分钟");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();


    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.edit_mode_cancle: // 取消
                alertDialog.dismiss();
                break;
            case R.id.edit_mode_ok: // 确定
                String mode_name = edit_mode_name.getText().toString();
                if (TextUtils.isEmpty(mode_name)) {
                    alertDialog.dismiss();
                    break;
                }
                // // TODO: 16-5-1  此处 要 保存要数据库.并与现有的 数据进行比对
                alertDialog.dismiss();
                mode_name_.setText(mode_name);
                burnModeNode.setName(mode_name);
                break;
            default:
                break;
        }
    }
}
