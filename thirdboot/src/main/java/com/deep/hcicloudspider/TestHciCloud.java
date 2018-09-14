package com.deep.hcicloudspider;

import com.deep.baiduspider.AddToTTS;
import com.deep.baiduspider.SpiderThread;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.sinovoice.hcicloudsdk.common.HciErrorCode;
import com.sinovoice.hcicloudsdk.common.tts.TtsConfig;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.awt.*;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * george 2018/8/22 19:48
 */
public class TestHciCloud {
    public static void main(String[] args) {
//        HciCloudTtsMain hciCloudTtsMain = new HciCloudTtsMain();
//        String ttsType = "hciCloud";
//        String voiceType = "tts.cloud.liangjiahe";
//        ExecutorService executorService = Executors.newFixedThreadPool(12);
//        int pageSize = 100000;
//        for (int i = 24; i < 35; i++) {
//            executorService.execute(new HciCloudSpiderThread(i, pageSize, hciCloudTtsMain, ttsType, voiceType));
//        }


        //单线程执行  不会出现资源重复问题
        ttsSpider("hciCloud", "tts.cloud.liangjiahe");
    }

    /**
     * 功能描述:不能多线程合成，音频会内容会对不上
     */
    private static void ttsSpider(String ttsType, String voiceType) {
        HciCloudTtsMain hciCloudTtsMain = new HciCloudTtsMain();
        TtsConfig ttsConfig = HciCloudTtsMain.setTtsConfig();

        int index = 49;
        String basePath = "source";
        String path = "source" + index;
        String baseFilDirectory = "F:\\TTSMP3\\source";
        String filDirectory = "F:\\TTSMP3\\source" + index;
        boolean flag = checkDirectorySize(filDirectory);
        while (!flag) {//如果目录下个数>=100000，则递增目录
            index += 1;
            filDirectory = baseFilDirectory + index;
            path = basePath + index;
            flag = checkDirectorySize(filDirectory);
        }

        AddToTTS addToTTS = new AddToTTS();
        MongoCollection<Document> childrenQuestionAnswer = addToTTS.getChildrenQuestionAnswer();
        FindIterable<Document> findIterable = childrenQuestionAnswer.find().noCursorTimeout(true);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while (mongoCursor.hasNext()) {
            Document document = mongoCursor.next();
            Object id = document.get("_id");
            Object answer = document.get("answer");
            ObjectId iid = new ObjectId();
            //如果已经生成，则不重复生成
            if (null == id || addToTTS.queryExistTTS(answer,"hciCloud","tts.cloud.liangjiahe")) {
                continue;
            }
            long start = System.currentTimeMillis();
            boolean ff = checkDirectorySize(filDirectory);
            while (!ff) {//如果目录下个数>=100000，则递增目录
                index += 1;
                filDirectory = baseFilDirectory + index;
                path = basePath + index;
                ff = checkDirectorySize(filDirectory);
            }

            String question = document.get("question").toString();
            //String answer = document.get("answer").toString();
            String filePath = filDirectory + "\\" + iid.toString() + ".mp3";
            int nRet = hciCloudTtsMain.generateTxtToMp3(answer.toString(), filePath, ttsConfig);
            if (nRet == HciErrorCode.HCI_ERR_NONE) {
                addToTTS.createTTSInfo(iid, question, answer.toString(), path, ttsType, voiceType);
                System.out.println("complited " + iid + " use:" + (System.currentTimeMillis() - start) + "ms");
            } else {
                System.out.println("hciTtsSynth failed:" + nRet);
                continue;
            }
        }
        hciCloudTtsMain.release();
    }

    /**
     * 功能描述:判断目录下文件个数小于100000
     *
     * @param path 路径
     */
    private static boolean checkDirectorySize(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
            flag = true;
        } else {
            int size = file.listFiles().length;
            if (size < 100000) {
                flag = true;
            }
        }
        return flag;
    }
}
