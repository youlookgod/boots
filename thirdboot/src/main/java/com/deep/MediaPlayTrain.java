package com.deep;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.channels.FileChannel;

/**
 * @Author: George
 * @Date 2018/5/16 13:19
 * @Description
 **/
public class MediaPlayTrain {
    public static void main(String[] args) {
        File soundMedia = new File("C:\\Users\\Administrator\\Downloads\\SLA.avi");
        getMediaSize(soundMedia);
        getMediaTime(soundMedia);
    }

    /**
     * 获取音频大小
     *
     * @param file
     * @return
     */
    private static String getMediaSize(File file) {
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
    private static String getMediaTime(File file) {
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
}
