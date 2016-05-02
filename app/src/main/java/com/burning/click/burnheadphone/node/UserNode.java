package com.burning.click.burnheadphone.node;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * 用户信息
 * Created by click on 16-4-15.
 */
public class UserNode implements Serializable {

    private int uid;
    private String password;
    private int login_status; // 0
    private String email;
    private static UserNode mUserNode;

    public UserNode() {
    }

    public UserNode(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        this.uid = jsonObject.getInt("uid");
        this.password = jsonObject.getString("password");
        this.login_status = jsonObject.getInt("login_status");
        this.email = jsonObject.getString("email");
    }

    public static UserNode getmUserNode() {
        return mUserNode;
    }

    public static void setmUserNode(UserNode mUserNode) {
        UserNode.mUserNode = mUserNode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getLogin_status() {
        return login_status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLogin_status(int login_status) {
        this.login_status = login_status;
    }

    @Override
    public String toString() {
        return "UserNode{" +
                "uid=" + uid +
                ", password='" + password + '\'' +
                ", login_status=" + login_status +
                ", email='" + email + '\'' +
                '}';
    }

    public String toJson(UserNode userNode) {
        if (null == userNode) return null;
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
