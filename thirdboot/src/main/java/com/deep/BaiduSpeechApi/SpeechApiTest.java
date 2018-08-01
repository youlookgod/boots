package com.deep.BaiduSpeechApi;

import com.baidu.aip.speech.AipSpeech;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SpeechApiTest {
    public static final String APP_ID = "10734778";
    public static final String API_KEY = "LDxCKuuug7qolGBUBWqecR0p";
    public static final String SECRET_KEY = "BAiGnLSxqGeKrg2f2HW7GCn7Nm8NoeTO";

    public static void main(String[] args) {
        SpeechApi speechApi = SpeechApi.getInstance();
        //testGenerateFromFile();
        testGenerateMp3(speechApi);
        //testAsr();
        //runGenerate(speechApi, 50);

    }

    private static void runGenerate(SpeechApi speechApi, int threadNum) {
        AtomicInteger sum = new AtomicInteger(0);
        for (int i = 0; i < threadNum; i++) {
            new Thread(() -> {
                String time = testGenerateMp3(speechApi);
                sum.addAndGet(Integer.valueOf(time));
            }).start();
        }

        try {
            Thread.sleep(3000);
            System.out.println("总时间==" + sum.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void testGenerateFromFile() {
        //File file = new File("F:\\临时文件\\红楼梦.txt");
        File file = new File("F:\\语义.txt");
        SpeechApi speechApi = SpeechApi.getInstance();
        String result = null;
        try {
            result = speechApi.generateMp3FromFile(file, "语音合成", "F:\\MP31", null, -1, true);
        } catch (SpeechException e) {
            SpeechExceptionInterface se = e.getSpeechExceptionInterface();
            System.out.println("信息" + se.getErrorMessage());
            e.printStackTrace();
        }
        System.out.println(result);
    }

    /**
     * 测试合成语音
     */
    private static String testGenerateMp3(SpeechApi speechApi) {
        //String text = "中行(xing2)很好"; 多音字用拼音加音调数字表示
        //String text = "中行是不对的";
        String text = "上海今天天气";
        String filePath = "F:\\test.mp3";
        HashMap<String, Object> options = new HashMap<>();
        //语速参数 0-9
        options.put("spd", "5");
        //音调参数 0-9
        options.put("pit", "5");
        //音量参数 0-9
        options.put("vol", "5");
        //发音人选择；0为女生，1为男生，3为情感合成-度逍遥，4为情感合成-度丫丫。默认为普通女
        options.put("per", "0");

        if (null == speechApi) {
            speechApi = SpeechApi.getInstance();
        }
        String result = null;
        try {
            result = speechApi.generateMp3(text, filePath, options, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    //测试语音识别
    private static void testAsr() {
        String mp3Path = "F:\\MP3\\红楼梦2.mp3";
        String pcmPath = "F:\\MP3\\temp\\红楼梦2.pcm";
        Mp3ToPcm.covertMp3ToPcm(mp3Path, pcmPath);
        SpeechApi speechApi = SpeechApi.getInstance();
        AipSpeech aipSpeech = speechApi.getAipSpeech();
        //rate是音频采样率，只支持8K和16K的
        JSONObject result = aipSpeech.asr(pcmPath, "pcm", 16000, null);
        System.out.println(result);
    }
}
