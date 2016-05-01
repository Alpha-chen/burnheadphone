package com.burning.click.burnheadphone.node;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 模式下歌曲列表
 * Created by click on 16-5-1.
 */
public class SongNodes implements Serializable {

    private ArrayList<SongNode> data = new ArrayList<>();

    public ArrayList<SongNode> getData() {
        return data;
    }

    public void setData(ArrayList<SongNode> data) {
        this.data = data;
    }

    public String toJson(SongNodes list) {
        if (null == list) return null;
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
