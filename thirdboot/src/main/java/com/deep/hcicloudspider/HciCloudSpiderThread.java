package com.deep.hcicloudspider;

import com.deep.BaiduSpeechApi.SpeechApi;
import com.deep.baiduspider.AddToTTS;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.sinovoice.hcicloudsdk.common.HciErrorCode;
import com.sinovoice.hcicloudsdk.common.tts.TtsConfig;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * george 2018/8/7 14:32
 */
public class HciCloudSpiderThread implements Runnable {
    private Integer pageNumber;
    private Integer pageSize;
    private HciCloudTtsMain hciCloudTtsMain;
    private String ttsType;
    private String voiceType;

    public HciCloudSpiderThread(Integer pageNumber, Integer pageSize, HciCloudTtsMain hciCloudTtsMain, String ttsType, String voiceType) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.hciCloudTtsMain = hciCloudTtsMain;
        this.ttsType = ttsType;
        this.voiceType = voiceType;
    }

    @Override
    public void run() {
        //HciCloudTtsMain hciCloudTtsMain = new HciCloudTtsMain();
        TtsConfig ttsConfig = HciCloudTtsMain.setTtsConfig();
        int offset = pageNumber % 10;
        int index = 30 + offset;
        String basePath = "source";
        String path = "source" + index;
        String baseFilDirectory = "F:\\TTSMP3\\source";
        String filDirectory = "F:\\TTSMP3\\source" + index;
        File file = new File(filDirectory);
        if (!file.exists()) {
            file.mkdir();
        }

        AddToTTS addToTTS = new AddToTTS();
        MongoCollection<Document> childrenQuestionAnswer = addToTTS.getChildrenQuestionAnswer();
        FindIterable<Document> findIterable = childrenQuestionAnswer.find().skip(pageNumber * pageSize).limit(pageSize).noCursorTimeout(true);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while (mongoCursor.hasNext()) {
            Document document = mongoCursor.next();
            Object id = document.get("_id");
            Object answer = document.get("answer");
            ObjectId iid = new ObjectId(id.toString());
            //如果已经生成，则不重复生成
            if (null == id || addToTTS.queryExistTTS(answer,"hciCloud","tts.cloud.liangjiahe")) {
                continue;
            }
            String question = document.get("question").toString();
            //String answer = document.get("answer").toString();
            String filePath = filDirectory + "\\" + id.toString() + ".mp3";
            hciCloudTtsMain.generateTxtToMp3(answer.toString(), filePath, ttsConfig);
            addToTTS.createTTSInfo(iid, question, answer.toString(), path, ttsType, voiceType);
            int nRet = hciCloudTtsMain.generateTxtToMp3(answer.toString(), filePath, ttsConfig);
            if (nRet == HciErrorCode.HCI_ERR_NONE) {
                addToTTS.createTTSInfo(iid, question, answer.toString(), path, ttsType, voiceType);
            } else {
                System.out.println("hciTtsSynth failed:" + nRet);
                continue;
            }
        }
    }
}
