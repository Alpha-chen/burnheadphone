package com.burning.click.burnheadphone.common;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** 安全、加密相关类 */
public class SecurityLib {
    /**
     * 进行MD5加密
     *
     * @param info
     *            要加密的信息
     * @return String 加密后的字符串
     */
    public static String EncryptToMD5(String info) {
        byte[] digesta = null;
        try {
            // 得到一个md5的消息摘要
            MessageDigest alga = MessageDigest.getInstance("MD5");
            // 添加要进行计算摘要的信息
            alga.update(info.getBytes());
            // 得到该摘要
            digesta = alga.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 将摘要转为字符串
        String rs = byte2hex(digesta);
        return rs.toLowerCase();
    }

    /**
     * 进行SHA加密
     *
     * @param info
     *            要加密的信息
     * @return String 加密后的字符串
     */
    public static String EncryptToSHA(String info) {
        byte[] digesta = null;
        try {
            // 得到一个SHA-1的消息摘要
            MessageDigest alga = MessageDigest.getInstance("SHA-1");
            // 添加要进行计算摘要的信息
            alga.update(info.getBytes());
            // 得到该摘要
            digesta = alga.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 将摘要转为字符串
        String rs = byte2hex(digesta);
        return rs;
    }

    /**
     * 将 byte array 转换为字符串
     *
     * @return byte[]
     */
    protected String bytesToString(byte[] encrytpByte) {
        String result = "";
        for (Byte bytes : encrytpByte) {
            result += (char) bytes.intValue();
        }
        return result;
    }

    /**
     * 将二进制转化为16进制字符串
     *
     * @param b
     *            二进制字节数组
     * @return String 转换为大写
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    /**
     * 十六进制字符串转化为2进制
     *
     * @param hex
     * @return
     */
    public byte[] hex2byte(String hex) {
        byte[] ret = new byte[8];
        byte[] tmp = hex.getBytes();
        for (int i = 0; i < 8; i++) {
            ret[i] = UniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }
        return ret;
    }

    /**
     * 将两个ASCII字符合成一个字节； 如："EF"--> 0xEF
     *
     * @param src0
     *            byte
     * @param src1
     *            byte
     * @return byte
     */
    public static byte UniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
                .byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
                .byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }

    /** 返回路径节点字符串，包括前面的/，包括uid，包括末尾的/，都是大写的 */
    public static String GetPathNode(int uid, String account) {
        String result = "";
        String mded = EncryptToMD5(uid + "" + account);
        int len = mded.length();
        result = File.separator + mded.substring(len - 6, len - 4)
                + File.separator;
        result += mded.substring(len - 4, len - 2) + File.separator;
        result += mded.substring(len - 2, len) + File.separator;
        result += uid + File.separator;
        return result;
    }

    /** 返回头像路径节点地址，包括UID和前后的/符号 */
    public static String XGetPortraitPath(int uid) {
        String result = "";
        String mded = EncryptToMD5(uid + "");
        int len = mded.length();
        result = File.separator + mded.substring(len - 6, len - 4)
                + File.separator;
        result += mded.substring(len - 4, len - 2) + File.separator;
        result += mded.substring(len - 2, len) + File.separator;
        result += uid + File.separator;
        return result;
    }

    /** 获取头像完整地址 */
    public static String GetUserIconPath(int uid) {
        String result = XGetPortraitPath(uid);
        return PORTRAIT_URL + result + uid + ".png";
    }

    public static final String PORTRAIT_URL = "http://icon.burnheadphone.com";

    public static final String attach_url = "v0.api.upyun.com";

    public static String getUpyunNode(String test) {
//		String result = "";
        String mded = EncryptToMD5(test);
        int len = mded.length();

        StringBuilder sb = new StringBuilder();
        sb.append(File.separator).append(mded.substring(len - 6, len - 4))
                .append(File.separator)
                .append(mded.substring(len - 4, len - 2))
                .append(File.separator)
                .append(mded.substring(len - 2, len))
                .append(File.separator);
        return sb.toString().toUpperCase();
    }

    public static String getUpyunPath(String node) {

        return attach_url + node;
    }

    /***
     *将城市信息进行加密处理
     */
    public static int  EncryptCityCode(String cityCode){
        if ("".equals(cityCode)){
            return -1;
        }
        return EncryptToMD5(cityCode).hashCode();
    }
}