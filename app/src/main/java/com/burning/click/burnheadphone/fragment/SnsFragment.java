package com.burning.click.burnheadphone.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.burning.click.burnheadphone.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SnsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SnsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SnsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;



    @Bind(R.id.swipe_layout_pic)
    SwipeRefreshLayout mSnsSwipeRefresh;
    @Bind(R.id.recly_view)
    RecyclerView mSnsRecyclerView;


    StaggeredGridLayoutManager staggeredGridLayoutManager;
    public SnsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SnsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SnsFragment newInstance(String param1, String param2) {
        SnsFragment fragment = new SnsFragment();
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mSnsRecyclerView.setHasFixedSize(true);
        mSnsRecyclerView.setLayoutManager(staggeredGridLayoutManager);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view = inflater.inflate(R.layout.fragment_sns,container,false);
        ButterKnife.bind(this,view);
        mSnsSwipeRefresh.setOnRefreshListener(this);
        mSnsSwipeRefresh.setRefreshing(true);
        onRefresh();
        return view;
    }


    @Override
    public void onRefresh() {

    }

}
