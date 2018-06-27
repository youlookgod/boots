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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: george
 * @Date: 2018/6/13-11-09
 * @Description:
 */
public class SpiderTest {
    private static String uri = "mongodb://iengine:ienginep@122.144.200.8:27017/iengine3?maxPoolSize=1000&minPoolSize=10&connectTimeoutMS=30000";

    public static void main(String[] args) {
        SpeechApi speechApi = SpeechApi.getInstance();
        HashMap<String, Object> options = getGenerateOptions();
        String filDirectory = "F:\\mp3source6";

        MongoClientURI mongoClientURI = new MongoClientURI(uri);
        MongoClient mongoClient = new MongoClient(mongoClientURI);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("iengine3");
        MongoCollection<Document> childrenQuestionAnswer = mongoDatabase.getCollection("ChildrenQuestionAnswer");

        //已经保存的id
        MongoCollection<Document> generateHistory = mongoDatabase.getCollection("GenerateHistory");

        Map<String, String> generateMap = getHadGenerateMap(generateHistory);
        FindIterable<Document> findIterable = childrenQuestionAnswer.find().noCursorTimeout(true);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while (mongoCursor.hasNext()) {
            Document document = mongoCursor.next();
            Object id = document.get("_id");
            //如果已经生成，则不重复生成
            if (null == id || null != generateMap.get(id.toString())) {
                continue;
            }
            String name = document.get("answer").toString();
            String filePath = filDirectory + "\\" + id.toString() + ".mp3";
            try {
                String result = speechApi.generateMp3(name, filePath, options, true);
                print(result);
                Document idDocument = new Document("_id", id);
                generateHistory.insertOne(idDocument);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mongoClient.close();
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
