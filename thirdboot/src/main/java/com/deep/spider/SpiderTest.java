package com.deep.spider;


import com.deep.BaiduSpeechApi.SpeechApi;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: george
 * @Date: 2018/6/13-11-09
 * @Description: 抓取iengine demo数据库中的childrenQuestionAnswer并转成mp3.
 */
@Deprecated
public class SpiderTest {
    private static String uri = "mongodb://iengine:ienginep@122.144.200.8:27017/iengine3?maxPoolSize=1000&minPoolSize=10&connectTimeoutMS=30000";

    public static void main(String[] args) {
        SpeechApi speechApi = SpeechApi.getInstance();
        HashMap<String, Object> options = getGenerateOptions();
        int index = 23;
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
//        MongoClientURI mongoClientURI = new MongoClientURI(uri);
//        MongoClient mongoClient = new MongoClient(mongoClientURI);
//        MongoDatabase mongoDatabase = mongoClient.getDatabase("iengine3");
//        MongoCollection<Document> childrenQuestionAnswer = mongoDatabase.getCollection("ChildrenQuestionAnswer");
        MongoCollection<Document> childrenQuestionAnswer = addToTTS.getChildrenQuestionAnswer();
        FindIterable<Document> findIterable = childrenQuestionAnswer.find().noCursorTimeout(true);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while (mongoCursor.hasNext()) {
            Document document = mongoCursor.next();
            Object id = document.get("_id");
            ObjectId iid = new ObjectId(id.toString());
            Object answer = document.get("answer");
            //如果已经生成，则不重复生成
            if (null == answer || addToTTS.queryExistTTS(answer)) {
                continue;
            }
            boolean ff = checkDirectorySize(filDirectory);
            while (!ff) {//如果目录下个数>=100000，则递增目录
                index += 1;
                filDirectory = baseFilDirectory + index;
                path = basePath + index;
                ff = checkDirectorySize(filDirectory);
            }
            String question = document.get("question").toString();
            //String answer = document.get("answer").toString();
            String filePath = filDirectory + "\\" + id.toString() + ".mp3";
            try {
                String result = speechApi.generateMp3(answer.toString(), filePath, options, true);
                print(result);
                addToTTS.createTTSInfo(iid, question, answer.toString(), path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        addToTTS.closeMontoClient();
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

    private static MongoDatabase getDatabase(String databaseName) {
        MongoClientURI mongoClientURI = new MongoClientURI(uri);
        MongoClient mongoClient = new MongoClient(mongoClientURI);
        MongoDatabase mongoDatabase = mongoClient.getDatabase(databaseName);
        return mongoDatabase;
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

    private static void print(Object obj) {
        System.out.println(obj);
    }

    /**
     * 功能描述:获取已经生成mp3的数据
     *
     * @param generateHistory
     * @return:
     */
    private static Map<String, String> getHadGenerateMap(MongoCollection<Document> generateHistory) {
        FindIterable<Document> findIterable = generateHistory.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        Map<String, String> map = new HashMap<>();
        while (mongoCursor.hasNext()) {
            Document document = mongoCursor.next();
            Object id = document.get("_id");
            if (null == id) {
                continue;
            }
            map.put(id.toString(), id.toString());
        }
        return map;
    }
}
