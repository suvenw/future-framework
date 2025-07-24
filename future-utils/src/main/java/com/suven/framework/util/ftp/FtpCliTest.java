//package com.ds.live.base.util.ftp;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//import static org.junit.Assert.*;
//
///**
// * <p>Description: [类功能描述]</p>
// * Created on 2018/6/6
// *
// * @author 叶向阳
// * @version 1.0
// */
//public class FtpCliTest {
//    FtpClient ftpCli;
//    File file;
//
////    ftp://ygdy8:ygdy8@yg90.dydytt.net:8528/阳光电影www.ygdy8.com.粽邪.BD.720p.国语中字.mkv
//    @Before
//    public void before() throws IOException {
//        ftpCli = FtpClient.createFtpCli("yg90.dydytt.net",8528, "ygdy8", "ygdy8","UTF-8");
//        ftpCli.connect();
//
//        String fileName = "阳光电影www.ygdy8.com.粽邪.BD.720p.国语中字.mkv";
//        file = new File(fileName);
//        file.createNewFile();
//    }
//
//    @After
//    public void after() {
//        ftpCli.disconnect();
//    }
//
//    @Test
//    public void uploadFileToDailyDir() throws IOException {
//        String path = "/Users/suven/project/";
//        String fileName = "阳光电影www.ygdy8.com.粽邪.BD.720p.国语中字.mkv";
//        File file1 = new File(path);
//        ftpCli.download(fileName,file1);
////        ftpCli.downloadFileFromDailyDir(path , new FileOutputStream(new File("testFtp.txt")));
//    }
//}