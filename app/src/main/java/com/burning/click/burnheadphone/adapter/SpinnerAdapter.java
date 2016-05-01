package com.burning.click.burnheadphone.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.burning.click.burnheadphone.R;
import com.burning.click.burnheadphone.common.ImageRes;
import com.burning.click.burnheadphone.node.BurnModeNode;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 煲耳机模式选取
 * Created by click on 16-4-30.
 */
public class SpinnerAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<BurnModeNode> data = null;
    private SpinnerOnItemListener onItemListener;

    public SpinnerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setOnItemListener(SpinnerOnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

    @Override
    public int getCount() {
        if (null == data) return 0;
        return data.size();
    }

    public void setData(ArrayList data) {
        this.data = data;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        SpinnerViewHoler holer;
        if (null == convertView) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.spinner_mode_select, null);
            holer = new SpinnerViewHoler(convertView);
            convertView.setTag(holer);
        } else {
            holer = (SpinnerViewHoler) convertView.getTag();
        }
        if (null == data || null == data.get(position)) return convertView;
        if (position == 0) {
            holer.modeImg.setBackgroundResource(ImageRes.getAddModePic());
        } else {
            holer.modeImg.setImageURI(Uri.parse(data.get(position).getModeImaUrl()));
        }
        holer.modeName.setText(data.get(position).getName());
        return convertView;
    }

    public interface SpinnerOnItemListener {
        boolean onItemLongClick(int position, boolean longClick, View view);

        void onItemClick(int position, View view);
    }

    public class SpinnerViewHoler {
        @Bind(R.id.spinner_mode_img)
        public SimpleDraweeView modeImg;
        @Bind(R.id.spinner_mode_name)
        public TextView modeName;

        public SpinnerViewHoler(View view) {
            ButterKnife.bind(this, view);
        }
    }
}



