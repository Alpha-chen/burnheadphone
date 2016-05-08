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

    private String uid;
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
        this.uid = jsonObject.getString("uid");
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
        setmUserNode(this);
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
        setmUserNode(this);

    }

    public int getLogin_status() {
        return login_status;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        setmUserNode(this);

    }

    public void setLogin_status(int login_status) {
        this.login_status = login_status;
        setmUserNode(this);

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

    public static String toJson(UserNode userNode) {
        if (null == userNode) return null;
        Gson gson = new Gson();
        return gson.toJson(userNode);
    }
}
