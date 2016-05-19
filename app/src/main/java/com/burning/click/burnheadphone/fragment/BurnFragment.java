package com.burning.click.burnheadphone.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.burning.click.burnheadphone.BaseFragment;
import com.burning.click.burnheadphone.BurnModeEditActivity;
import com.burning.click.burnheadphone.Log.LogUtil;
import com.burning.click.burnheadphone.R;
import com.burning.click.burnheadphone.adapter.SpinnerAdapter;
import com.burning.click.burnheadphone.broadReciver.HeadsetReceiver;
import com.burning.click.burnheadphone.callback.OnPlayCompleteListener;
import com.burning.click.burnheadphone.constant.Constant;
import com.burning.click.burnheadphone.customview.SkinView;
import com.burning.click.burnheadphone.node.BurnModeNode;
import com.burning.click.burnheadphone.node.BurnModeNodes;
import com.burning.click.burnheadphone.node.UserNode;
import com.burning.click.burnheadphone.service.PlayerService;
import com.burning.click.burnheadphone.sp.SpUtils;
import com.burning.click.burnheadphone.util.SpkeyName;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 煲耳机界面
 */
public class BurnFragment extends BaseFragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, OnPlayCompleteListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String TAG = "BurnFragment";
    private static final int PROGRESS = 0X0003;
    private String mParam1;
    private String mParam2;

    //定义一个进度
    private int progress;
    @Bind(R.id.burn_mode_select_text)
    TextView mode_select_text;
    Button mode_select_btn;
    SpinnerAdapter spinnerAdapter;
    private BurnModeNodes burnModeNodes = null;
    private android.support.v7.app.AlertDialog.Builder builder;
    private Dialog dialog;
    // dialog 中的view
    private ListView mDialogListView;
    private TextView mTitle;

    private ProgressBar progressBar;

    public int editBurnPosition = -1; // 点击 已有的mode的界面
    @Bind(R.id.burning_progress)
    SkinView mSinkView;

    @Bind(R.id.playing_song)
    TextView playing_song;
    private float mPercent;

    private Thread mThread;

    /**
     * 显示loading中的最大值
     */
    private int maxProgress;

    private View view; // layout的 view

    public BurnFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BurnFragment.
     */
    public static BurnFragment newInstance(String param1, String param2) {
        BurnFragment fragment = new BurnFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void check() {
        if (0 == SpkeyName.HEADSETSTATE) {
            Toast.makeText(getActivity(), "耳机未插入,无法进行煲耳机", Toast.LENGTH_SHORT).show();
            return;
        }
        String songTitle = burnModeNodes.getData().get(editBurnPosition).getSongNodes().getData().get(0).getTitle();
        String singer = burnModeNodes.getData().get(editBurnPosition).getSongNodes().getData().get(0).getSinger();

        playing_song.setText(songTitle + "--" + singer);
        playMusics(Constant.PLAY_AUDIO.PLAY_AUDIO_START);
        myHandler.sendEmptyMessageDelayed(PROGRESS, 1000);
        if (SpkeyName.BURN_STATUS == 1) {
            mode_select_btn.setBackgroundColor(R.color.black_overlay);
            mode_select_btn.setClickable(false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_burn, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initView() {
        super.initView();
        mode_select_btn = (Button) view.findViewById(R.id.burn_mode_select_btn);
        mode_select_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });
        spinnerAdapter = new SpinnerAdapter(getActivity());
        // 弹出模式选择的对话框
        builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.burn_mode_list, null);
        mDialogListView = (ListView) view.findViewById(R.id.burn_mode_list);
        mDialogListView.setAdapter(spinnerAdapter);
        mDialogListView.setOnItemClickListener(this);
        mDialogListView.setOnItemLongClickListener(this);
        mTitle = (TextView) view.findViewById(R.id.burn_mode_select_title);
        builder.setView(view);
        dialog = builder.create();

        // 注册广播
        //给广播绑定响应的过滤器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.HEADSET_PLUG");
        HeadsetReceiver headsetReceiver = new HeadsetReceiver();
        getActivity().registerReceiver(headsetReceiver, intentFilter);
    }

    @OnClick(R.id.burn_mode_select_text)
    void onModeSelect() {
        dialog.show();
    }

    @Override
    protected void initViewData() {
        super.initViewData();
        Gson gson = new Gson();
        String burnModeList = SpUtils.getString(getActivity(), SpUtils.BHP_SHARF, UserNode.getmUserNode().getUid(), "");
        if (TextUtils.isEmpty(burnModeList)) {
            burnModeNodes = gson.fromJson(BurnModeNode.DEFAULT_MODE, BurnModeNodes.class);
        } else {
            burnModeNodes = gson.fromJson(burnModeList, BurnModeNodes.class);
        }
        spinnerAdapter.setData(burnModeNodes.getData());
        spinnerAdapter.notifyDataSetChanged();
        mode_select_text.setText(burnModeNodes.getData().get(1).getName());
        int tempPosition = SpUtils.getInt(getActivity(), SpUtils.BHP_SHARF, SpkeyName.SELECT_BURN_MODE_POSITION, 0);
        if (0 != tempPosition) {
            mDialogListView.setSelection(tempPosition);
            maxProgress = burnModeNodes.getData().get(tempPosition).getBurnModeTime() * 3600;
            mSinkView.setMaxProgress(maxProgress);
            mSinkView.setCurrentProgress(burnModeNodes.getData().get(tempPosition).getHasBurnTime() * 3600);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initViewData();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.d(TAG, "");
        if (null == data) return;
        switch (resultCode) {
            case Constant.RESULT_CODE.MODE_CODE: // 由模式界面返回
                int modeStatus = data.getIntExtra("modeStatus", -1);
                switch (modeStatus) {
                    case 0:
                        BurnModeNode burnModeNode = (BurnModeNode) data.getSerializableExtra("burnModeNode");
                        burnModeNodes.getData().add(burnModeNode);
                        editBurnPosition = burnModeNodes.getData().size() - 1;
                        myHandler.sendEmptyMessage(Constant.NOTIFY_MODE_LIST.NOTIFY_MODE_LIST);
                        String burnModeList = BurnModeNodes.toJson(burnModeNodes);
                        LogUtil.d(TAG, "burnModeList=" + burnModeList);
                        SpUtils.put(getActivity(), SpUtils.BHP_SHARF, UserNode.getmUserNode().getUid() + "", burnModeList);
                        break;
                    case 1:
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (null == msg) return false;
        switch (msg.what) {
            case Constant.NOTIFY_MODE_LIST.NOTIFY_MODE_LIST:
                spinnerAdapter.setData(burnModeNodes.getData());
                spinnerAdapter.notifyDataSetChanged();
                changeBurnMode();
                break;
            case PROGRESS:
                if (SpkeyName.HEADSETSTATE == 0) {
                    break;
                }
                progress++;
                if (progress <= maxProgress) {
                    mSinkView.setCurrentProgress(progress);
                    myHandler.sendEmptyMessageDelayed(PROGRESS, 100);
                }
                break;
            default:
                break;

        }
        return super.handleMessage(msg);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LogUtil.d("getActivity=" + getActivity());
        if (0 == position) {
            if (burnModeNodes.getData().size() > 4) {
                Toast.makeText(getActivity(), "最多只能添加四种模式", Toast.LENGTH_LONG).show();
                dialog.dismiss();
                return;
            }
            Intent intent = new Intent();
            intent.setClass(getActivity(), BurnModeEditActivity.class);
            intent.putExtra("modeStatus", 0); // 添加模式
            this.startActivityForResult(intent, Constant.RESULT_CODE.MODE_CODE);
            SpUtils.put(getActivity(), SpUtils.BHP_SHARF, SpkeyName.SELECT_BURN_MODE_POSITION, position);
        } else {
            mode_select_text.setText(burnModeNodes.getData().get(position).getName());
            editBurnPosition = position;
            changeBurnMode();
        }
        dialog.dismiss();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        if (0 == position) return false;
        // 更新选中的模式
        mode_select_text.setText(burnModeNodes.getData().get(position).getName());
        Intent intent = new Intent();
        intent.setClass(getActivity(), BurnModeEditActivity.class);
        intent.putExtra("modeStatus", 1); // 编辑模式
        intent.putExtra("BurnModeNode", burnModeNodes.getData().get(position));
        this.startActivityForResult(intent, Constant.RESULT_CODE.MODE_CODE);
        editBurnPosition = position;
        dialog.dismiss();
        return true;
    }

    public void playMusics(int action) {
        Intent intent = new Intent();
        intent.putExtra("MSG", action);
        intent.putExtra("burnModeList", burnModeNodes);
        intent.putExtra("selectModePosition", editBurnPosition);
        intent.setClass(getActivity(), PlayerService.class);
        getActivity().startService(intent);
    }

    /**
     * 模式切换的时候更新数据源
     */
    public void changeBurnMode() {
        maxProgress = burnModeNodes.getData().get(editBurnPosition).getBurnModeTime() * 3600;
        mSinkView.setMaxProgress(maxProgress);
        mSinkView.setCurrentProgress(burnModeNodes.getData().get(editBurnPosition).getHasBurnTime() * 3600);
    }

    @Override
    public void onSongComplete(int selectModePosition, int songPosition) {
        String songTitle = burnModeNodes.getData().get(selectModePosition).getSongNodes().getData().get(songPosition).getTitle();
        String singer = burnModeNodes.getData().get(selectModePosition).getSongNodes().getData().get(songPosition).getSinger();
        playing_song.setText(songTitle + "--" + singer);
    }
}
