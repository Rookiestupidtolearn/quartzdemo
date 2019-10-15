package com.example.quartzdemo.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class JobService {


    @Resource
    private HttpAPIService httpAPIService;


    public void test() throws Exception {
        String str = httpAPIService.doGet("http://www.baidu.com");
        System.out.println(str);

    }


    //cron = "0/3 40 11 * * ?" 每天11:40触发，没三秒执行一次
    @Scheduled(cron = "0/50 * * * * ?")
    public void printTime() {
//        System.out.println("current time :"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        try {
            test();
//           service("test.sh");
            //        shell();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static  void shell(){
        try {
            String shpath="/home/test/test.sh";
            Process ps = Runtime.getRuntime().exec(shpath);
            ps.waitFor();

            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            String result = sb.toString();
            System.out.println(result);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void service(String shellName) throws Exception{
        String shellDir = "";
        String shellPath = "";
        try {
            //获取脚本所在的目录
            String configFilePath = Thread.currentThread().getContextClassLoader().getResource("config.properties").getPath();
            File f = new File("/home/test/");
            shellDir = f.getPath();
//            log.info("shell dir = " + shellDir);
            System.out.println("shell dir = " + shellDir);
            //拼接完整的脚本目录
            shellPath = shellDir  +"/"+ shellName;
//            log.info("shell path = " + shellPath);
            System.out.println("shell path = " + shellPath);
            //执行脚本
            callScript(shellPath);

        } catch (Exception e) {
//            log.error("ShellExcutor异常" + e.getMessage(), e);
            throw e;
        }
    }


    private static void callScript(String script) throws Exception{
        try {
            String cmd =  script;

            //启动独立线程等待process执行完成
            CommandWaitForThread commandThread = new CommandWaitForThread(cmd);
            commandThread.start();

            while (!commandThread.isFinish()) {
//                log.info("shell " + script + " 还未执行完毕,10s后重新探测");
                Thread.sleep(10000);
            }

            //检查脚本执行结果状态码
            if(commandThread.getExitValue() != 0){
                throw new Exception("shell " + script + "执行失败,exitValue = " + commandThread.getExitValue());
            }
//            log.info("shell " + script + "执行成功,exitValue = " + commandThread.getExitValue());
        }
        catch (Exception e){
            throw new Exception("执行脚本发生异常,脚本路径" + script, e);
        }
    }


}
