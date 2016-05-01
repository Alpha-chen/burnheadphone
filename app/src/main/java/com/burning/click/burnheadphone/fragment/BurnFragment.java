package com.burning.click.burnheadphone.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.burning.click.burnheadphone.BaseFragment;
import com.burning.click.burnheadphone.BurnModeEditActivity;
import com.burning.click.burnheadphone.Log.LogUtil;
import com.burning.click.burnheadphone.ModesActivity;
import com.burning.click.burnheadphone.R;
import com.burning.click.burnheadphone.adapter.SpinnerAdapter;
import com.burning.click.burnheadphone.constant.Constant;
import com.burning.click.burnheadphone.node.BurnModeNode;
import com.burning.click.burnheadphone.node.BurnModeNodes;
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
        showModeDialog();
    }

    /**
     * 模式选取的dialog
     */
    public void showModeDialog() {
        dialog.show();
    }

    @Override
    protected void initViewData() {
        super.initViewData();
        Gson gson = new Gson();
        burnModeNodes = gson.fromJson(BurnModeNode.DEFAULT_MODE, BurnModeNodes.class);
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
        if (null == data) return;
        switch (requestCode) {
            case Constant.RESULT_CODE.MODE_CODE: // 由模式界面返回
                boolean hasChange;
                hasChange = data.getBooleanExtra("hasChange", false);
                if (!hasChange) return; // 没有做过修改 就不在重新搜索数据库
                //  TODO: 16-4-30  查询数据库更新当前模式列表中的数据
                break;
            default:
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LogUtil.d("getActivity=" + getActivity());
        if (0 == position) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), BurnModeEditActivity.class);
            intent.putExtra("modeStatus", 0); // 添加模式
            getActivity().startActivityForResult(intent, Constant.RESULT_CODE.MODE_CODE);

        } else {
            // 更新选中的模式
            mode_select_text.setText(burnModeNodes.getData().get(position).getName());
        }
        dialog.dismiss();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ModesActivity.class);
        intent.putExtra("modeStatus", 1); // 编辑模式
        intent.putExtra("BurnModeNode", burnModeNodes.getData().get(position));
        getActivity().startActivityForResult(intent, Constant.RESULT_CODE.MODE_CODE);
        dialog.dismiss();

        return true;
    }
}
