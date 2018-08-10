package com.deep.spider;

import com.deep.BaiduSpeechApi.SpeechApi;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * george 2018/8/7 14:32
 */
public class SpiderThread implements Runnable {
    private Integer pageNumber;
    private Integer pageSize;

    public SpiderThread(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    @Override
    public void run() {
        SpeechApi speechApi = SpeechApi.getInstance();
        HashMap<String, Object> options = getGenerateOptions();
        int offset = pageNumber % 5;
        int index = 24 + offset;
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
            if (null == id || addToTTS.queryExistTTS(answer)) {
                continue;
            }
            String question = document.get("question").toString();
            //String answer = document.get("answer").toString();
            String filePath = filDirectory + "\\" + id.toString() + ".mp3";
            try {
                String result = speechApi.generateMp3(answer.toString(), filePath, options, true);
                System.out.println(result);
                addToTTS.createTTSInfo(iid, question, answer.toString(), path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static HashMap<String, Object> getGenerateOptions() {
        HashMap<String, Object> options = new HashMap<>();
        //语速参数 0-9
        options.put("spd", "4");
        //音调参数 0-9
        options.put("pit", "5");
        //音量参数 0-9
        options.put("vol", "5");
        //发音人选择；0为女生，1为男生，3为情感合成-度逍遥，4为情感合成-度丫丫。默认为普通女
        options.put("per", "4");
        return options;
    }
}
