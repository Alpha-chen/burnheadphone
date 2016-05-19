package com.burning.click.burnheadphone.node;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 用户列表
 * Created by click on 16-5-19.
 */
public class UserNodes implements Serializable {
    private ArrayList<UserNode> datas = new ArrayList<>();

    public ArrayList<UserNode> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<UserNode> datas) {
        this.datas = datas;
    }

    public static String toJson(UserNodes list) {
        if (null == list) return null;
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
