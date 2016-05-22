package com.burning.click.burnheadphone.util;

/**
 * SharePreference 变量名称
 * Created by click on 16-4-14.
 */
public class SpkeyName {
    public static final String IS_FIRST = "is_first";
    public static final String USER_NAME = "user_name";
    public static final String PASS_WORD = "pass_word";
    public static final String PEOPLE_NODE = "people_node";
    public static final String LOGIN_STATUS = "login_status"; // 0 未登录，1登陆
    public static final String USER_NODE = "user_node"; // 登录用户信息

    public static final String RECORD_BURN_TIME_STATUS = "record_burn_time_status"; // 记录煲耳机 时间状态 0 "开" 1 "关"
    public static final String RECORD_BURN_TIME = "record_burn_time";  // 记录煲耳机时间
    public static final String SELECT_BURN_MODE_POSITION = "select_burn_mode_position"; // 记录煲耳机选取模式的位置
    public static int HEADSETSTATE = 0; // "0" 为插入 "1"插入
    public static int BURN_STATUS = 0; // "0" 没有在煲耳机 "1"正在煲耳机

    public static final String USER_LIST = "user_list"; // 用户列表

    public static final String ROBOT_HAS_BURN_TIME = "ROBOT_HAS_BURN_TIME";// 智能模式已经煲的时间
    public static final String ROBOT_BURN_TIME = "ROBOT_BURN_TIME"; // 智能模式煲耳机时间
}