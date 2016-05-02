package com.burning.click.burnheadphone.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.burning.click.burnheadphone.R;
import com.burning.click.burnheadphone.node.SongNode;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 搜索音乐界面的adapter
 * Created by click on 16-5-1.
 */
public class SelectSongRecycleAdapter extends RecyclerView.Adapter<SelectSongRecycleAdapter.SelectSongViewHoler> {
    private Context mContext;
    private ArrayList<SongNode> data = new ArrayList<>();


    public SelectSongRecycleAdapter(Context mContext) {
        this.mContext = mContext;
    }


    public void setData(ArrayList<SongNode> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    @Override
    public SelectSongViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.select_burn_song_item, null);
        return new SelectSongViewHoler(view);
    }

    @Override
    public void onBindViewHolder(SelectSongViewHoler holder, int position) {
        if (null == data.get(position)) return;
        SongNode songNode = data.get(position);
        holder.burn_song_name.setText(songNode.getTitle());
        holder.burn_song_artist.setText(songNode.getSinger());
    }

    @Override
    public int getItemCount() {
        if (null != data) {
            return data.size();
        }
        return 0;
    }

    public class SelectSongViewHoler extends RecyclerView.ViewHolder {
        @Bind(R.id.burn_song_name)
        TextView burn_song_name;
        @Bind(R.id.burn_song_artist)
        TextView burn_song_artist;
        @Bind(R.id.burn_song_detail)
        ImageView burn_song_detail;
        @Bind(R.id.burn_select_song_cb)
        CheckBox burn_select_song_cb;

        public SelectSongViewHoler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
