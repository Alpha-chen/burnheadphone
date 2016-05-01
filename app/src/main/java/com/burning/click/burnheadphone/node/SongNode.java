package com.burning.click.burnheadphone.node;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * 音乐信息
 * Created by click on 16-5-1.
 */
public class SongNode implements Serializable {

    private int id;
    private String artist;
    private String title;
    private String data;
    private String displayName;
    private String duration; // 时长


    public SongNode() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String toJson(SongNode songNode) {
        if (null == songNode) return null;
        Gson gson = new Gson();
        return gson.toJson(songNode);
    }
}
