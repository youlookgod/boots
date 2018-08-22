package com.deep.spider;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: george
 * @date: 2018/6/27-20-32
 * @description:
 */
public class AddToTTS {
    private static String uri = "mongodb://deepbrain:deepbrainadmin@192.168.20.170:27017/deep-nlp-admin?maxPoolSize=1000&minPoolSize=10&connectTimeoutMS=30000";
    private static String fileUri = "http://cdnmusic.hezi.360iii.net";
    private static String remoteDir = "/prod/tts/childrenchat/";

    private MongoClient mongoClient;
    private MongoCollection<Document> ttsCollection;
    private MongoCollection<Document> resourceData;
    private MongoCollection<Document> childrenQuestionAnswer;
    private MongoCollection<Document> functionTypeExt;

    public AddToTTS() {
        MongoClient mongoClient = new MongoClient(new MongoClientURI(uri));

        MongoDatabase mongoDatabase = mongoClient.getDatabase("deepbrain");
        MongoCollection<Document> ttsCollection = mongoDatabase.getCollection("TTSAudioInfo");
        MongoCollection<Document> childrenQuestionAnswer = mongoDatabase.getCollection("ChildrenQuestionAnswer");
        MongoCollection<Document> resourceData = mongoDatabase.getCollection("ResourceData");
        MongoCollection<Document> functionTypeExt = mongoDatabase.getCollection("FunctionTypeExt");
        this.mongoClient = mongoClient;
        this.ttsCollection = ttsCollection;
        this.childrenQuestionAnswer = childrenQuestionAnswer;
        this.resourceData = resourceData;
        this.functionTypeExt = functionTypeExt;
    }

    public boolean queryExistTTS(Object answer) {
        boolean flag = false;
        Document filter = new Document();
        filter.append("answer", answer.toString());
        FindIterable<Document> iterables = ttsCollection.find(filter);
        MongoCursor<Document> cursor = iterables.iterator();
        while (cursor.hasNext()) {
            return true;
        }
        return flag;
    }

    public void createTTSInfo(ObjectId id, String question, String answer, String path) {
        if (null != id) {
            Document document = new Document();
            String filePath = remoteDir + path + "/" + id.toString() + ".mp3";
            document.append("_id", id).append("question", question).append("answer", answer).append("fileUri", fileUri).append("filePath", filePath).append("ttsType", "百度").append("createdTime", new Date()).append("enabled", true);
            ttsCollection.insertOne(document);
        }
    }

    public void closeMontoClient(){
        mongoClient.close();
    }

    public void setChildrenQuestionAnswer(MongoCollection<Document> childrenQuestionAnswer) {
        this.childrenQuestionAnswer = childrenQuestionAnswer;
    }

    public MongoCollection<Document> getChildrenQuestionAnswer() {
        return childrenQuestionAnswer;
    }

    public MongoCollection<Document> getResourceData() {
        return resourceData;
    }
    public MongoCollection<Document> getFunctionTypeExt() {
        return functionTypeExt;
    }

}
