package com.burning.click.burnheadphone.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.provider.MediaStore;

import com.burning.click.burnheadphone.Log.LogUtil;
import com.burning.click.burnheadphone.node.SongNode;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.id3.ID3v23Frame;
import org.jaudiotagger.tag.id3.ID3v23Tag;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
    public static ArrayList<SongNode> getSongNodeByFile(Context context) {
        ArrayList<SongNode> songNodes = new ArrayList<>();
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
                SongNode songNode = new SongNode();
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
                    songNode.setSizeStr((size + "").substring(0, 4) + "M");
                } else {
                    songNode.setSizeStr("未知大小");
                }
                if (cursor.getString(9) != null) {
                    songNode.setFilePath(cursor.getString(9));
                }
                songNodes.add(songNode);
                LogUtil.d("aaaa", songNode.toString() + "");
            } while (cursor.moveToNext());
            cursor.close();
        }////////////////
        return songNodes;
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

    private static String where = "mime_type in ('audio/mpeg','audio/x-ms-wma') "; // and bucket_display_name <> 'audio' and is_music > 0
    private static String sortOrder = MediaStore.Audio.Media.DATA;
    private static String[] projection = {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.SIZE
    };

    public static SongNode selectAll(final Context context) {
        if (context == null) { //判断传入的参数的有效性
            return null;
        }
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = null;
        SongNode audio = null;
        try {
            //查询数据库，参数分别为（路径，要查询的列名，条件语句，条件参数，排序）
            cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, where, null, sortOrder);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    audio = new SongNode();
                    audio.setSid("" + cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID))); //获取唯一id
                    audio.setFilePath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))); //文件路径
                    audio.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))); //文件名
                    //...   还有很多属性可以设置
                    //可以通过下一行查看属性名，然后去Audio.Media里寻找对应常量名

                    //获取专辑封面（如果数据量大的话，会很耗时——需要考虑如何开辟子线程加载）
//                    Bitmap albumArt = createAlbumArt(audio.getFilePath());
//                    audio.set
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return audio;
    }

    /**
     * @param filePath 文件路径，like XXX/XXX/XX.mp3
     * @return 专辑封面bitmap
     * @Description 获取专辑封面
     */
    public static Bitmap createAlbumArt(final String filePath) {
        Bitmap bitmap = null;
        //能够获取多媒体文件元数据的类
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath); //设置数据源
            byte[] embedPic = retriever.getEmbeddedPicture(); //得到字节型数据
            bitmap = BitmapFactory.decodeByteArray(embedPic, 0, embedPic.length); //转换为图片
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return bitmap;
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
