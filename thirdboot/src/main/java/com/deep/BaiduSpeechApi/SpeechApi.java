package com.deep.BaiduSpeechApi;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.baidu.aip.util.Util;
import org.json.JSONObject;

import java.io.*;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpeechApi {
    public static final String APP_ID = "10734778";
    public static final String API_KEY = "LDxCKuuug7qolGBUBWqecR0p";
    public static final String SECRET_KEY = "BAiGnLSxqGeKrg2f2HW7GCn7Nm8NoeTO";

    private static final SpeechApi speechApi = new SpeechApi();
    private static AipSpeech aipSpeech;

    public AipSpeech getAipSpeech() {
        return aipSpeech;
    }

    private SpeechApi() {
    }

    public static SpeechApi getInstance() {
        aipSpeech = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
        return speechApi;
    }

    /**
     * 文字合成MP3文件
     *
     * @param txt       文本文字
     * @param filePath  生成的MP3保存位置  !!!也即MP3全路径，例如-D:\\source\\test11.mp3
     * @param options   生成MP3时的参数，包括语速、音调、音量、男女声音、
     * @param overwrite 覆盖已存在的文件 true-覆盖，false-不覆盖
     * @return 合成成功与否 message
     * @throws IOException
     */
    public String generateMp3(String txt, String filePath, HashMap<String, Object> options, boolean overwrite) throws IOException {
        File tempFile = new File(filePath);
        if (!overwrite && tempFile.exists()) {
            return "source exists,not generate";
        }
        txt = replaceSpecialSymbol(txt);
        long start = System.currentTimeMillis();
        TtsResponse response = aipSpeech.synthesis(txt, "zh", 1, options);
        long end = System.currentTimeMillis();
        //System.out.println("耗时" + (end - start));
        byte[] data = response.getData();
        JSONObject result = response.getResult();
        if (null != data) {
            Util.writeBytesToFileSystem(data, filePath);
            return (end - start) + "";
        }
        if (null != result) {
            return result.get("err_msg").toString();
        }
        return "success";
    }

    /**
     * 文本文件合成语音
     *
     * @param file         待合成的文件
     * @param mp3Name      合成后MP3的名称
     * @param downloadPath 合成后的文件存放位置
     * @param options      合成参数
     * @param overwrite    是否覆盖已有MP3
     * @return 合成成功与否 message
     * @Param perLength    每个文件的语句长度-中文最长不能超过512
     */
    public String generateMp3FromFile(File file, String mp3Name, String downloadPath, HashMap<String, Object> options, Integer perLength, boolean overwrite) {
        String result = "";
        if (!downloadPath.endsWith("/")) {
            downloadPath += "/";
        }
        if (!file.exists()) {
            throw new SpeechException(SpeechExceptionEnum.FILE_NOT_EXIST);
        }
        if (perLength < 0 || perLength > 512) {//每行每行合成
            result = generatePerLine(file, mp3Name, downloadPath, options, overwrite);
        }else{//规定字数合成
            result = generatePerLength(file, mp3Name, downloadPath, options, perLength, overwrite);
        }
        return result;
    }

    /**
     * 一行一行合成
     * @param file
     * @param mp3Name
     * @param downloadPath
     * @param options
     * @param overwrite
     * @return
     */
    private String generatePerLine(File file,String mp3Name, String downloadPath,HashMap<String,Object> options,boolean overwrite) {
        StringBuffer result = new StringBuffer();
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader ins = new InputStreamReader(fis,"utf-8");
            BufferedReader br = new BufferedReader(ins);
            String line="";
            String[] arrs=null;
            String path = downloadPath + mp3Name;
            int index = 1;
            while ((line=br.readLine())!=null) {
                String filePath = path + index + ".mp3";
                String res = this.generateMp3(line, filePath, options, overwrite);
                result.append(res);
                index++;
                line = "";
            }
            br.close();
            ins.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 规定字数合成
     * @param file
     * @param mp3Name
     * @param downloadPath
     * @param options
     * @param perLength
     * @param overwrite
     * @return
     */
    private String generatePerLength(File file,String mp3Name, String downloadPath,HashMap<String,Object> options,Integer perLength,boolean overwrite){
        StringBuffer result = new StringBuffer();
        try {
            char[] chs = new char[perLength];
            FileReader fileReader = new FileReader(file);
            int length = -1;
            int index = 1;
            String path = downloadPath + mp3Name;
            while ((length = fileReader.read(chs)) != -1) {
                String text = String.valueOf(chs);
                String filePath = path + index + ".mp3";
                String res = this.generateMp3(text, filePath, options, overwrite);
                result.append(filePath + "合成结果:").append(res).append("\n");
                index++;
                chs = new char[perLength];
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 去除换行等特殊符号
     *
     * @param oldStr
     * @return
     */
    public static String replaceSpecialSymbol(String oldStr) {
        String dest = "";
        if (null != oldStr) {
            Pattern pattern = Pattern.compile("\\s*|\t|\r|\n");
            Matcher matcher = pattern.matcher(oldStr);
            dest = matcher.replaceAll("");
        }
        return dest;
    }
}
