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
    private static String uri = "mongodb://deepbrain:deepbrainadmin@122.144.200.100:47017/deepbrain?maxPoolSize=1000&minPoolSize=10&connectTimeoutMS=30000";
    public static void main(String[] args) {
        MongoClientURI mongoClientURI = new MongoClientURI(uri);
        MongoClient mongoClient = new MongoClient(mongoClientURI);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("deepbrain");
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
        mongoClient.close();

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet();
        XSSFRow row0 = sheet.createRow(0);
        XSSFCell cell0 = row0.createCell(0);
        cell0.setCellValue("appId");
        XSSFCell cell1 = row0.createCell(1);
        cell1.setCellValue("robotId");
        XSSFCell cell2 = row0.createCell(2);
        cell2.setCellValue("数量");

        List<TestDto> testDtos = new ArrayList<>();

        deviceSNList.stream().filter(s->s.getAppId()!=null).collect(Collectors.groupingBy(DeviceSN::getAppId)).forEach((appId, list)->{
            list.stream().filter(a->a.getRobotId()!=null).collect(Collectors.groupingBy(DeviceSN::getRobotId)).forEach((robotId,rllist)->{
                int count = rllist.size();
                TestDto testDto = new TestDto();
                testDto.setAppId(appId);
                testDto.setRobotId(robotId);
                testDto.setNumber(count);
                testDtos.add(testDto);
                System.out.println("appId是"+appId+"===robotId是"+robotId+"的sn数量"+count);
            });
        });

        int i=1;
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

}
