package com.deep;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Header;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileChannel;

/**
 * @Author: George
 * @Date 2018/5/16 13:19
 * @Description
 **/
public class MediaPlayTrain {
    public static void main(String[] args) {
        File soundMedia = new File("C:\\Users\\Administrator\\Downloads\\SLA.avi");
//        getMediaSize(soundMedia);
//        getMediaTime(soundMedia);
        getMp3Time("http://cdnmusic.hezi.360iii.net/textbook/0201/02010201/242455838124.mp3");
    }

    /**
     * 获取音频大小
     *
     * @param file
     * @return
     */
    public static String getMediaSize(File file) {
        FileChannel fc = null;
        String size = "";
        try {
            FileInputStream in = new FileInputStream(file);
            fc = in.getChannel();
            BigDecimal bg = new BigDecimal(fc.size());
            size = bg.divide(new BigDecimal(1048576), 2, RoundingMode.HALF_UP) + "MB";
            System.out.println(size);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != fc) {
                try {
                    fc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return size;
    }

    /**
     * 获取音、视频时长
     *
     * @param file
     * @return
     */
    public static String getMediaTime(File file) {
        Encoder encoder = new Encoder();
        String length = "";
        try {
            MultimediaInfo multimediaInfo = encoder.getInfo(file);
            long ls = multimediaInfo.getDuration() / 1000;
            int hour = (int) ls / 3600;
            int minute = (int) ((ls % 3600) / 60);
            int seconds = (int) (ls - hour * 3600 - minute * 60);
            length = hour + "'" + minute + "'" + seconds + "'";
        } catch (EncoderException e) {
            e.printStackTrace();
        }
        System.out.println(length);
        return length;
    }

    /**
     * 功能描述:获取网络音乐文件时长
     *
     * @param url 音频文件url地址
     */
    public static String getMp3Time(String url) {
        String time = "";
        try {
            URL urlFile = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlFile.openConnection();
            httpURLConnection.setRequestMethod("GET");
            int code = 0;
            code = httpURLConnection.getResponseCode();
            int length = httpURLConnection.getContentLength();
            if (code == 200) {
                BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
                Bitstream bt = new Bitstream(bis);
                Header h = bt.readFrame();
                long times = (long) (h.total_ms(length) / 1000);//音频秒数
                int hour = (int) times / 3600;
                int minute = (int) (times % 3600) / 60;
                int seconds = (int) (times - hour * 3600 - minute * 60);
                time = hour + ":" + minute + ":" + seconds;
                System.out.println(time);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BitstreamException e) {
            e.printStackTrace();
        }
        return time;
    }
}
