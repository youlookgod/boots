package com.deep.CountLicenseHistory;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: george
 * @Date: 2018/6/22-11-46
 * @Description:
 */
public class LicenseCount {
    private static String uri = "mongodb://deepbrain:deepbrainadmin@122.144.200.102:47017/deepbrain?maxPoolSize=1000&minPoolSize=10&connectTimeoutMS=30000";

    //private static String uri = "mongodb://deepNlpAdminUser:ssd2es3cke@192.168.20.89:27017/deep-nlp-admin?maxPoolSize=1000&minPoolSize=10&connectTimeoutMS=30000";
    public static void main(String[] args) {
        MongoClientURI mongoClientURI = new MongoClientURI(uri);
        MongoClient mongoClient = new MongoClient(mongoClientURI);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("deepbrain");
        //MongoDatabase mongoDatabase = mongoClient.getDatabase("deep-nlp-admin");

        List<TestDto> testDtos = getSnWithGrouprobot(mongoDatabase);
        //exportActiveSn(testDtos);

        updateRobotLicenseCount(mongoDatabase,testDtos);
        mongoClient.close();
    }

    private static void updateRobotLicenseCount(MongoDatabase mongoDatabase, List<TestDto> testDtos) {
        MongoCollection<Document> robotLicenseCountCollection = mongoDatabase.getCollection("RobotLicenseCount");
        FindIterable<Document> robotLicense = robotLicenseCountCollection.find();
        MongoCursor<Document> cursor = robotLicense.iterator();

        List<RobotLicenseCount> licenseCounts = new ArrayList<>();
        while (cursor.hasNext()) {
            RobotLicenseCount robotLicenseCount = new RobotLicenseCount();
            Document dooo = cursor.next();
            String id = dooo.get("_id").toString();
            String appId = dooo.get("appId").toString();
            String robotId = dooo.get("robotId").toString();
            int totalLicense = Integer.parseInt(dooo.get("totalLicense").toString());
            int activeLicense = Integer.parseInt(dooo.get("activeLicense").toString());
            int remainderLicense = Integer.parseInt(dooo.get("remainderLicense").toString());
            robotLicenseCount.setId(id);
            robotLicenseCount.setAppId(appId);
            robotLicenseCount.setRobotId(robotId);
            robotLicenseCount.setTotalLicense(totalLicense);
            robotLicenseCount.setActiveLicense(activeLicense);
            robotLicenseCount.setRemainderLicense(remainderLicense);
            licenseCounts.add(robotLicenseCount);
        }
        System.out.println(licenseCounts.size());

        licenseCounts.stream().forEach(count -> {
            testDtos.stream().forEach(s -> {
                if (s.getAppId().equals(count.getAppId()) && s.getRobotId().equals(count.getRobotId())) {
                    int activeLicense = count.getActiveLicense() + s.getNumber();
                    int remainderLicense = count.getRemainderLicense() - s.getNumber();
                    Document filter = new Document("_id", new ObjectId(count.getId()));
                    Document update = new Document("_id", new ObjectId(count.getId())).append("appId", count.getAppId()).append("robotId", count.getRobotId())
                            .append("totalLicense", count.getTotalLicense()).append("activeLicense", activeLicense).append("remainderLicense", remainderLicense);

                    Document updateDocument = new Document();
                    updateDocument.append("$set", update);
                    robotLicenseCountCollection.updateOne(filter, updateDocument);
                }
            });
        });
    }

    /**
     * 功能描述:导出以激活的sn数量
     *
     * @param testDtos
     * @return: void
     */
    private static void exportActiveSn(List<TestDto> testDtos) {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet();
        XSSFRow row0 = sheet.createRow(0);
        XSSFCell cell0 = row0.createCell(0);
        cell0.setCellValue("appId");
        XSSFCell cell1 = row0.createCell(1);
        cell1.setCellValue("robotId");
        XSSFCell cell2 = row0.createCell(2);
        cell2.setCellValue("数量");

        int i = 1;
        for (TestDto dto : testDtos) {
            XSSFRow row = sheet.createRow(i);
            XSSFCell cell01 = row.createCell(0);
            cell01.setCellValue(dto.getAppId());
            XSSFCell cell11 = row.createCell(1);
            cell11.setCellValue(dto.getRobotId());
            XSSFCell cell21 = row.createCell(2);
            cell21.setCellValue(dto.getNumber());
            i++;
        }

        try {
            wb.write(new FileOutputStream(new File("F:\\激活license历史数据.xlsx")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能描述:根据appId、robotId分组统计sn数量
     *
     * @param mongoDatabase
     * @return: java.util.List<com.deep.CountLicenseHistory.TestDto>
     */
    private static List<TestDto> getSnWithGrouprobot(MongoDatabase mongoDatabase) {
        MongoCollection<Document> deviceSNCollection = mongoDatabase.getCollection("DeviceSN");

        FindIterable<Document> findIterable = deviceSNCollection.find().noCursorTimeout(true);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        List<DeviceSN> deviceSNList = new ArrayList<>();
        while (mongoCursor.hasNext()) {
            DeviceSN deviceSN = new DeviceSN();
            Document document = mongoCursor.next();
            String appId = document.get("appId").toString();
            String robotId = document.get("robotId").toString();
            String sn = document.get("sn").toString();
            deviceSN.setAppId(appId);
            deviceSN.setRobotId(robotId);
            deviceSN.setSn(sn);
            deviceSNList.add(deviceSN);
        }

        List<TestDto> testDtos = new ArrayList<>();

        deviceSNList.stream().filter(s -> s.getAppId() != null).collect(Collectors.groupingBy(DeviceSN::getAppId)).forEach((appId, list) -> {
            list.stream().filter(a -> a.getRobotId() != null).collect(Collectors.groupingBy(DeviceSN::getRobotId)).forEach((robotId, rllist) -> {
                int count = rllist.size();
                TestDto testDto = new TestDto();
                testDto.setAppId(appId);
                testDto.setRobotId(robotId);
                testDto.setNumber(count);
                testDtos.add(testDto);
                System.out.println("appId是" + appId + "===robotId是" + robotId + "的sn数量" + count);
            });
        });
        return testDtos;
    }

}
