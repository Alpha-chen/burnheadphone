package com.burning.click.burnheadphone.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;

import com.burning.click.burnheadphone.Log.LogUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 操作文件类
 * Created by click on 16-4-30.
 */
public class FileUtil {
    public static String sd_card = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static String app_path = sd_card + File.separator + "bhpDir"; // app 根路径
    public static String bhp_image_path = File.separator + "image"; // 图片存放路径
    public static String bhp_common_file = File.separator + "file"; // 文件路径
    public static String bhp_mp3_file = File.separator + "audioFile"; // mp3路径
    public static String pinkMp3 = File.separator + "pink.mp3";
    public static String whiteMp3 = File.separator + "white.mp3";

    /**
     * 创建应用的根目录
     */
    public static boolean createMyDir() {
        if (!checkSDStatus()) return false;
        File myFile = new File(app_path);
        if (myFile.exists()) return true;
        try {
            return myFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 创建指定的目录
     */
    public boolean createFile(String path) {
        if (!checkSDStatus())
            return false;
        if (TextUtils.isEmpty(path))
            throw new Resources.NotFoundException("path为空");
        File myFile = new File(path);
        if (myFile.exists()) return true;
        try {
            return myFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 保存文件
     *
     * @param bitmap 图片对象
     * @param path   保存路径
     */
    public static boolean saveFile(Bitmap bitmap, String path) {
        if (null == bitmap) return false;
        if (!checkSDStatus()) return false;
        File file = new File(sd_card + bhp_image_path + path);
        boolean temp = false;
        File imageDir = new File(sd_card + bhp_image_path);
        if (!imageDir.exists()) {
            if (!imageDir.mkdirs()) {
                LogUtil.e("图片保存文件创建失败");
                return false;
            }
        }
        if (!file.exists()) try {
            temp = file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedOutputStream bufferedOutputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bufferedOutputStream);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 保存文件到相应的文件
     *
     * @param str  文件内容
     * @param path 文件的路径
     */
    public static boolean saveFile(String str, String path) {
        if (!checkSDStatus()) return false;
        if (TextUtils.isEmpty(str)) return false;
        File app_file = new File(sd_card + bhp_common_file);
        if (!app_file.exists()) {
            if (!app_file.mkdir()) {
                LogUtil.e("bhp_common_file 创建失败 ");
                return false;
            }
        }
        File file = new File(sd_card + bhp_common_file + path);
        BufferedOutputStream bufferedOutputStream = null;
        FileOutputStream fileOutputStream = null;
        FileInputStream fileInputStream = null;
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    LogUtil.e(sd_card + bhp_common_file + path + " 创建失败 ");
                    return false;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fileOutputStream = new FileOutputStream(file);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            fileInputStream = new FileInputStream(str);
            byte buffer[] = new byte[4 * 1024];
            while (fileInputStream.read(buffer) != -1) {
                fileOutputStream.write(buffer);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取文件后缀名
     *
     * @param filepath
     * @return 文件的后缀名
     */
    public static String getFileName(String filepath) {
        String fileName = "";
        if (TextUtils.isEmpty(filepath)) return fileName;
        String[] arr = filepath.split(File.separator);
        return arr[arr.length - 1];
    }

    /***
     * SD卡装载 状态
     *
     * @return true 已经装载 false 没有装载
     */
    private static boolean checkSDStatus() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        LogUtil.e("SD卡没有装载");
        return false;
    }
}
