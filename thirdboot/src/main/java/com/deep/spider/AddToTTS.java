package com.deep.spider;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: george
 * @date: 2018/6/27-20-32
 * @description:
 */
public class AddToTTS {
    private static String uri = "mongodb://deepNlpAdminUser:ssd2es3cke@192.168.20.89:27017/deep-nlp-admin?maxPoolSize=1000&minPoolSize=10&connectTimeoutMS=30000";
    private static String fileUri = "http://cdnmusic.hezi.360iii.net";
    private static String remoteDir = "/prod/tts/childrenchat/";

    private MongoCollection<Document> ttsCollection;

    public AddToTTS() {
        MongoClient mongoClient = new MongoClient(new MongoClientURI(uri));
        MongoDatabase mongoDatabase = mongoClient.getDatabase("deep-nlp-admin");
        MongoCollection<Document> ttsCollection = mongoDatabase.getCollection("TTSAudioInfo");
        this.ttsCollection = ttsCollection;
    }

    public boolean queryExistTTS(ObjectId id) {
        boolean flag = false;
        Document filter = new Document();
        filter.append("_id", id);
        FindIterable<Document> iterables = ttsCollection.find(filter);
        MongoCursor<Document> cursor = iterables.iterator();
        while (cursor.hasNext()) {
            flag = true;
        }
        return flag;
    }

    public void createTTSInfo(ObjectId id, String question, String answer, String path) {
        Document document = new Document();
        String filePath = remoteDir + path + "/" + id.toString() + ".mp3";
        document.append("_id", id).append("question", question).append("answer", answer).append("fileUri", fileUri).append("filePath", filePath).append("enabled", true);
        ttsCollection.insertOne(document);
    }

}
