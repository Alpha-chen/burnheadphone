package com.burning.click.burnheadphone.node;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 煲耳机 模式列表
 * Created by click on 16-4-30.
 */
public class BurnModeNodes implements Serializable {

    private ArrayList<BurnModeNode> data = new ArrayList<>();

    public ArrayList<BurnModeNode> getData() {
        return data;
    }

    public void setData(ArrayList<BurnModeNode> data) {
        this.data = data;
    }


    public static String toJson(BurnModeNodes burnModeNodes) {
        if (null == burnModeNodes) return null;
        Gson gson = new Gson();
        return gson.toJson(burnModeNodes);
    }
}
