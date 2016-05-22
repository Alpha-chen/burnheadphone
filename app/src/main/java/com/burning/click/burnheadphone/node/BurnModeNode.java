package com.burning.click.burnheadphone.node;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * 煲耳机模式mode
 * Created by click on 16-4-30.
 */
public class BurnModeNode implements Serializable {
    private String modeImaUrl = ""; // 模式生成图片的位置
    private String name; // 模式的名称
    private String id; // 模式的id
    // 模式内歌单列表
    private SongNodes songNodes;
    // 记录该模式 已经播放 播放时长
    private int hasBurnTime;
    private int recordTimeStatus; // "0" 表示没有开启时长记录 "1" 表示开启了时长记录
    // 设置 煲耳机的时长
    private int burnModeTime = 0; // "30" "60" "180" "99999999":不停止 一直煲耳机
    // 扩展
    private String extra; //

    // 播放到第几首歌曲
    private int playingPosition = 0;

    public String getModeImaUrl() {
        return modeImaUrl;
    }

    public void setModeImaUrl(String modeImaUrl) {
        this.modeImaUrl = modeImaUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SongNodes getSongNodes() {
        return songNodes;
    }

    public void setSongNodes(SongNodes songNodes) {
        this.songNodes = songNodes;
    }

    public int getRecordTimeStatus() {
        return recordTimeStatus;
    }

    public void setRecordTimeStatus(int recordTimeStatus) {
        this.recordTimeStatus = recordTimeStatus;
    }

    public int getHasBurnTime() {
        return hasBurnTime;
    }

    public void setHasBurnTime(int hasBurnTime) {
        this.hasBurnTime = hasBurnTime;
    }

    public int getBurnModeTime() {
        return burnModeTime;
    }

    public void setBurnModeTime(int burnModeTime) {
        this.burnModeTime = burnModeTime;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public int getPlayingPosition() {
        return playingPosition;
    }

    public void setPlayingPosition(int playingPosition) {
        this.playingPosition = playingPosition;
    }

    public static String DEFAULT_MODE = "{'data':[{'id':'0','modeImaUrl':'','name':'添加新的模式'}," +
            "{'id':'1','modeImaUrl':'','name':'智能模式'}]}";


    public boolean compareTo(BurnModeNode burnModeNode) {
        if (null == burnModeNode) return false;
//        if (this.equals(burnModeNode)) return true;
        if (this.burnModeTime != burnModeNode.burnModeTime) {
            return false;
        } else if (!this.id.equals(burnModeNode.id)) {
            return false;
        } else if (!this.modeImaUrl.equals(burnModeNode.modeImaUrl)) {
            return false;
        } else if (!this.name.equals(burnModeNode.name)) {
            return false;
        } else if (this.recordTimeStatus != burnModeNode.recordTimeStatus) {
            return false;
        } else if (!this.songNodes.equals(burnModeNode.songNodes)) {
            return false;
        } else if (this.hasBurnTime != burnModeNode.hasBurnTime) {
            return false;
        } else if (this.playingPosition != burnModeNode.playingPosition) {
            return false;
        }
        return true;
    }

    public BurnModeNode copyTo(BurnModeNode burnModeNode) {
        if (null == burnModeNode) return null;
        this.name = burnModeNode.name;
        this.recordTimeStatus = burnModeNode.recordTimeStatus;
        this.modeImaUrl = burnModeNode.modeImaUrl;
        this.id = burnModeNode.id;
        this.burnModeTime = burnModeNode.burnModeTime;
        this.extra = burnModeNode.extra;
        this.songNodes = burnModeNode.songNodes;
        this.hasBurnTime = burnModeNode.hasBurnTime;
        this.playingPosition = burnModeNode.playingPosition;
        return this;
    }

    public static String toJson(BurnModeNode burnModeNode) {
        if (null == burnModeNode) return null;
        Gson gson = new Gson();
        return gson.toJson(burnModeNode);
    }
}
