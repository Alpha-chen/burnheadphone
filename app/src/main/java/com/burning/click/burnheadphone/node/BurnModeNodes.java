package com.burning.click.burnheadphone.node;

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
}
