package com.burning.click.burnheadphone.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.burning.click.burnheadphone.constant.Constant;
import com.burning.click.burnheadphone.node.BurnModeNode;
import com.burning.click.burnheadphone.node.BurnModeNodes;
import com.burning.click.burnheadphone.node.UserNode;
import com.burning.click.burnheadphone.sp.SpUtils;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 煲耳机界面
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link BurnFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BurnFragment extends BaseFragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String TAG = "BurnFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Bind(R.id.burn_mode_select_text)
    TextView mode_select_text;
    @Bind(R.id.burn_mode_select_btn)
    Button mode_select_btn;
    SpinnerAdapter spinnerAdapter;
    private BurnModeNodes burnModeNodes = null;
    private android.support.v7.app.AlertDialog.Builder builder;
    private Dialog dialog;
    // dialog 中的view
    private ListView mDialogListView;
    private TextView mTitle;

    private ProgressBar progressBar;

    private int editBurnPosition = -1; // 点击 已有的mode的界面

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
    // TODO: Rename and change types and number of parameters
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_burn, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initView() {
        super.initView();
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
//                        Message message = myHandler.obtainMessage();
//                        message.obj = burnModeNodes;
//                        message.what = Constant.NOTIFY_MODE_LIST.NOTIFY_MODE_LIST;
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
                changeBurnMode(editBurnPosition);
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
            if (burnModeNodes.getData().size() > 4)
                Toast.makeText(getActivity(), "最多只能添加四种模式", Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            intent.setClass(getActivity(), BurnModeEditActivity.class);
            intent.putExtra("modeStatus", 0); // 添加模式
            this.startActivityForResult(intent, Constant.RESULT_CODE.MODE_CODE);
        } else {
            mode_select_text.setText(burnModeNodes.getData().get(position).getName());
            changeBurnMode(position);
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

    /**
     * 模式切换的时候更新数据源
     */
    public void changeBurnMode(int position) {


    }
}
