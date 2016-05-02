package com.burning.click.burnheadphone.util;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.burning.click.burnheadphone.node.SongNode;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.id3.ID3v23Frame;
import org.jaudiotagger.tag.id3.ID3v23Tag;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.logging.Level;

public class MediaUtils {
    /**
     * 通过文件获取mp3的相关数据信息
     *
     * @param filePath
     * @return
     */

    public static SongNode getSongNodeByFile(String filePath) {
        File sourceFile = new File(filePath);
        if (!sourceFile.exists())
            return null;
        SongNode songInfo = null;
        try {
            AudioFileIO.logger.setLevel(Level.SEVERE);
            ID3v23Frame.logger.setLevel(Level.SEVERE);
            ID3v23Tag.logger.setLevel(Level.SEVERE);
            MP3File mp3file = new MP3File(sourceFile);
            MP3AudioHeader header = mp3file.getMP3AudioHeader();
            if (header == null)
                return null;
            songInfo = new SongNode();
            // 歌曲时长
            String durationStr = header.getTrackLengthAsString();
            long duration = getTrackLength(durationStr);
            // 文件名
            String displayName = sourceFile.getName();
            if (displayName.contains(".mp3")) {
                String[] displayNameArr = displayName.split(".mp3");
                displayName = displayNameArr[0].trim();
            }
            String artist = "";
            String title = "";
            if (displayName.contains("-")) {
                String[] titleArr = displayName.split("-");
                artist = titleArr[0].trim();
                title = titleArr[1].trim();
            } else {
                title = displayName;
            }

            if (sourceFile.length() < 1024 * 1024) {
                return null;
            }

            songInfo.setSid(IDGenerate.getId());
            songInfo.setDisplayName(displayName);
            songInfo.setSinger(artist);
            songInfo.setTitle(title);
            songInfo.setDuration(duration);
            songInfo.setDurationStr(durationStr);
            songInfo.setSize(sourceFile.length());
            songInfo.setSizeStr(getFileSize(sourceFile.length()));
            songInfo.setFilePath(filePath);
            songInfo.setType(SongNode.LOCALSONG);
            songInfo.setIslike(SongNode.UNLIKE);
            songInfo.setDownloadStatus(SongNode.DOWNLOADED);
            songInfo.setCreateTime(DateUtil.dateToString(new Date()));

            mp3file = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return songInfo;

    }

    /**
     * 获取音频文件
     *
     * @param context
     * @return
     */
    public static SongNode getSongNodeByFile(Context context) {
        SongNode songNode = null;
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new String[]{
                        MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME,
                        MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DURATION,
                        MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.YEAR, MediaStore.Audio.Media.MIME_TYPE,
                        MediaStore.Audio.Media.SIZE, MediaStore.Audio.Media.DATA},
                MediaStore.Audio.Media.MIME_TYPE + "=? OR " + MediaStore.Audio.Media.MIME_TYPE + "=?",
                new String[]{
                        "audio/mpeg", "audio/x-ms-wma"}, null);
        if (cursor.moveToFirst()) {
            do {
                songNode = new SongNode();
                songNode.setSid(cursor.getString(0));
                songNode.setDisplayName(cursor.getString(1));
                songNode.setTitle(cursor.getString(2));
                songNode.setDurationStr(cursor.getString(3));
//                songNode.setDuration(cursor.getLong(3));
                songNode.setSinger(cursor.getString(4));
                if (cursor.getString(6) != null) {
                    songNode.setCreateTime(cursor.getString(6));
                } else {
                    songNode.setCreateTime("未知");
                }

                if ("audio/mpeg".equals(cursor.getString(7).trim())) {
                    songNode.setSongType("mp3");
                } else if ("audio/x-ms-wma".equals(cursor.getString(7).trim())) {
                    songNode.setSongType("wma");
                }
                if (cursor.getString(8) != null) {
                    float size = cursor.getInt(8) / 1024f / 1024f;
                    songNode.setSid((size + "").substring(0, 4) + "M");
                } else {
                    songNode.setSizeStr("未知大小");
                }
                if (cursor.getString(9) != null) {
                    songNode.setFilePath(cursor.getString(9));
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        return songNode;
    }

    /**
     * 时间格式转换
     *
     * @param time
     * @return
     */
    public static String formatTime(int time) {

        time /= 1000;
        int minute = time / 60;
        // int hour = minute / 60;
        int second = time % 60;
        minute %= 60;
        return String.format("%02d:%02d", minute, second);
    }

    /**
     * 计算文件的大小，返回相关的m字符串
     *
     * @param fileS
     * @return
     */
    public static String getFileSize(long fileS) {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 获取歌曲长度
     *
     * @param trackLengthAsString
     * @return
     */
    private static long getTrackLength(String trackLengthAsString) {

        if (trackLengthAsString.contains(":")) {
            String temp[] = trackLengthAsString.split(":");
            if (temp.length == 2) {
                int m = Integer.parseInt(temp[0]);// 分
                int s = Integer.parseInt(temp[1]);// 秒
                int currTime = (m * 60 + s) * 1000;
                return currTime;
            }
        }
        return 0;
    }
}
