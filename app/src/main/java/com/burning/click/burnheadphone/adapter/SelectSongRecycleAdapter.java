package com.burning.click.burnheadphone.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    private OnItemClickListener onItemClickListener;
    boolean lockState;
    private SparseBooleanArray checkBoxStatus = new SparseBooleanArray();

    public SelectSongRecycleAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        if (null == onItemClickListener) return;
        this.onItemClickListener = onItemClickListener;
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
    public void onBindViewHolder(final SelectSongViewHoler holder, final int position) {
        if (null == data.get(position)) return;
        SongNode songNode = data.get(position);
        lockState = false;

        holder.burn_song_name.setText(songNode.getTitle());
        holder.burn_song_artist.setText(songNode.getSinger());
        holder.burn_select_song_cb.setTag(position);
        holder.burn_select_song_cb.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int pos = (int) holder.burn_select_song_cb.getTag();
                if (lockState) {
                    lockState = false;
                    return;
                }
                if (isChecked) {
                    checkBoxStatus.put(pos, true);
                    onItemClickListener.selectSong(position);
                } else {
                    checkBoxStatus.delete(pos);
                    onItemClickListener.removeSong(position);
                }
            }
        });

        holder.burn_select_song_cb.setChecked(checkBoxStatus.get(position, false));
        if (1 == data.get(position).getIsSelect()) {
            lockState = true;
            holder.burn_select_song_cb.setChecked(true);
            onItemClickListener.selectSong(position);
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClickListener(position);
            }
        });
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemClickListener.onItemLonClickListener(position);
                return true;
            }
        });
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
        private View mView;

        public SelectSongViewHoler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mView = itemView;
        }
    }


    public interface OnItemClickListener {
        void onItemLonClickListener(int position);

        void onItemClickListener(int position);

        void selectSong(int positions);

        void removeSong(int position);
    }
}
