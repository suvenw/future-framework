package com.suven.framework.util.tool;

import java.io.File;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * 调用shell
 */
public class RunShellUtil {
    /**
     * 执行shell命令 String[] cmd = { "sh", "-c", "lsmod |grep linuxVmux" }或者
     * String[] cmd = { "sh", "-c", "./load_driver.sh" }
     */
    public static String runScript(String[] cmd) {
        StringBuffer buf = new StringBuffer();
        String rt = "-1";
        try {
            Process pos = Runtime.getRuntime().exec(cmd);
            pos.waitFor();
            InputStreamReader ir = new InputStreamReader(pos.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String ln = "";
            while ((ln = input.readLine()) != null) {
                buf.append(ln + "\n");
//                System.err.println(ln);
            }
            rt = buf.toString();
            input.close();
            ir.close();
        } catch (java.io.IOException e) {
            rt = e.toString();
        } catch (Exception e) {
            rt = e.toString();
        }
        return rt;
    }

    /**
     * 执行简单命令 String cmd="ls"
     */
    public static String runScript(String cmd) {
        StringBuffer buf = new StringBuffer();
        String rt = "-1";
        try {
            System.out.println("start");
            Process pos = Runtime.getRuntime().exec(cmd);
            pos.waitFor();
            InputStreamReader ir = new InputStreamReader(pos.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String ln = "";
            while ((ln = input.readLine()) != null) {
                System.err.println(ln);

                buf.append(ln + "\n");

            }
            rt = buf.toString();
            input.close();
            ir.close();
            System.out.println("end");
        } catch (java.io.IOException e) {
            rt = e.toString();
        } catch (Exception e) {
            rt = e.toString();
        }
        return rt;
    }


    /**
     *
     */
    public static void runC(String cmd) {
        try {
            Process proc = Runtime.getRuntime().exec("python  D:\\demo.py");
            proc.waitFor();
        } catch (java.io.IOException e) {
        } catch (Exception e) {
        }

    }

    /**
     * 执行简单命令 String cmd="ls"
     */
    public static String runScript(String[] cmd, File file) {
        StringBuffer buf = new StringBuffer();
        String rt = "-1";
        try {
            System.out.println("start");
            Process pos = Runtime.getRuntime().exec(cmd, null, file);
            pos.waitFor();
            pos.getOutputStream().flush();
            InputStreamReader ir = new InputStreamReader(pos.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String ln = "";
            while ((ln = input.readLine()) != null) {
                System.err.println(ln);

                buf.append(ln + "\n");

            }
            rt = buf.toString();
            input.close();
            ir.close();
            System.out.println("end");
        } catch (java.io.IOException e) {
            rt = e.toString();
        } catch (Exception e) {
            rt = e.toString();
        }
        return rt;
    }


    //    /**
//     *
//     */
//    public static void runC(String  cmd) {
//        try {
//            Process proc = Runtime.getRuntime().exec("python  D:\\demo.py");
//            proc.waitFor();
//        } catch (java.io.IOException e) {
//        } catch (Exception e) {
//        }
//
//    }
    public static void main2(String args[]) {

        //String[] command = {"sh", "-c", "ps -aux|grep java"};
//        String[] cmd = {"git pull" };
        System.out.println(runScript("ps -ef | grep java"));
//        System.out.println(runScript("ls"));
//        System.out.println(runScript("ls"));
    }

   
}