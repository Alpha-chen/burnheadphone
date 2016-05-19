package com.burning.click.burnheadphone.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.burning.click.burnheadphone.BaseFragment;
import com.burning.click.burnheadphone.R;
import com.burning.click.burnheadphone.TipDetailActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 小知识
 */
public class TipFragment extends BaseFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Bind(R.id.burn_mode_tip_lay)
    RelativeLayout burn_mode_tip_lay;
    @Bind(R.id.burn_mode_tip_lay1)
    RelativeLayout burn_mode_tip_lay1;
    @Bind(R.id.burn_mode_tip_lay2)
    RelativeLayout burn_mode_tip_lay2;
    @Bind(R.id.burn_mode_tip_lay3)
    RelativeLayout burn_mode_tip_lay3;
    @Bind(R.id.burn_mode_tip_lay4)
    RelativeLayout burn_mode_tip_lay4;

    public TipFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TipFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TipFragment newInstance(String param1, String param2) {
        TipFragment fragment = new TipFragment();
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
        View view = inflater.inflate(R.layout.fragment_tip, container, false);
        ButterKnife.bind(this,view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        burn_mode_tip_lay.setOnClickListener(this);
        burn_mode_tip_lay1.setOnClickListener(this);
        burn_mode_tip_lay2.setOnClickListener(this);
        burn_mode_tip_lay3.setOnClickListener(this);
        burn_mode_tip_lay4.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.burn_mode_tip_lay:
                Intent intent = new Intent();
                intent.setClass(getActivity(), TipDetailActivity.class);
                intent.putExtra("title", 0);
                getActivity().startActivity(intent);
                break;
            case R.id.burn_mode_tip_lay1:
                Intent intent1 = new Intent();
                intent1.setClass(getActivity(), TipDetailActivity.class);
                intent1.putExtra("title", 1);
                getActivity().startActivity(intent1);
                break;
            case R.id.burn_mode_tip_lay2:
                Intent intent2 = new Intent();
                intent2.setClass(getActivity(), TipDetailActivity.class);
                intent2.putExtra("title", 2);
                getActivity().startActivity(intent2);
                break;
            case R.id.burn_mode_tip_lay3:
                Intent intent3 = new Intent();
                intent3.setClass(getActivity(), TipDetailActivity.class);
                intent3.putExtra("title", 3);
                getActivity().startActivity(intent3);
                break;
            case R.id.burn_mode_tip_lay4:
                Intent intent4 = new Intent();
                intent4.setClass(getActivity(), TipDetailActivity.class);
                intent4.putExtra("title", 4);
                getActivity().startActivity(intent4);
                break;
            default:
                break;

        }
    }
}
