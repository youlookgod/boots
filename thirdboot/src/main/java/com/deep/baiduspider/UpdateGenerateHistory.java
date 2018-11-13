package com.deep.baiduspider;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.File;

/**
 * @Author: george
 * @Date: 2018/6/26-09-52
 * @Description:
 */
public class UpdateGenerateHistory {
    private static String uri = "mongodb://iengine:ienginep@122.144.200.8:27017/iengine3?maxPoolSize=1000&minPoolSize=10&connectTimeoutMS=30000";
    private static String fileUri = "http://cdnmusic.hezi.360iii.net";
    private static String remoteDir = "/vioce1/prod/tts/childrenchat/";
    //private static String remoteDirExt = "source1/";

    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient(new MongoClientURI(uri));
        MongoDatabase mongoDatabase = mongoClient.getDatabase("iengine3");
        MongoCollection<Document> collection = mongoDatabase.getCollection("GenerateHistory");
        MongoCollection<Document> questionCollection = mongoDatabase.getCollection("ChildrenQuestionAnswer");

        String fileDirectory = "F:\\source";
        String tempFileDirectory = "";
        for (int i = 1; i <= 21; i++) {
            tempFileDirectory = fileDirectory + i;

            String remoteDirExt = "source"+i+"/";

            File file = new File(tempFileDirectory);
            File[] listFile = file.listFiles();
            for (File f : listFile) {
                String fileName = f.getName();
                String id = fileName.split(".mp3")[0];

                ObjectId objectId = new ObjectId(id);
                Document filter = new Document();
                filter.append("_id", objectId);

                //查询ChildrenQuestionAnswer的question和answer
                FindIterable<Document> iterables = questionCollection.find(filter);
                MongoCursor<Document> cursor = iterables.iterator();
                String question = "";
                String answer = "";
                while (cursor.hasNext()) {
                    Document dooo = cursor.next();
                    question = dooo.get("question").toString();
                    answer = dooo.get("answer").toString();
                    System.out.println(question + "====" + answer);
                }

                Document update = new Document();
                update.append("$set", new Document("_id", objectId).append("question", question).append("answer", answer).append("fileUri", fileUri).append("filePath", remoteDir + remoteDirExt + fileName).append("enabled", true));
                collection.updateOne(filter, update);

                //System.out.println(f.getName().split(".mp3")[0]);
            }
            tempFileDirectory = "";
        }

        mongoClient.close();

    }

    /*public static List<Document> findBy(Bson filter) {
        List<Document> results = new ArrayList<Document>();
        FindIterable<Document> iterables = collection.find(filter);
        MongoCursor<Document> cursor = iterables.iterator();
        while (cursor.hasNext()) {
            results.add(cursor.next());
        }

        return results;
    }*/
}
