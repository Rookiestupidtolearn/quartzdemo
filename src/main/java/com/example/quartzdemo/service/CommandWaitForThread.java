package com.example.quartzdemo.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandWaitForThread extends Thread {

    private String cmd;
    private boolean finish = false;
    private int exitValue = -1;

    public CommandWaitForThread(String cmd) {
        this.cmd = cmd;
    }

    public void run(){
        try {
            //执行脚本并等待脚本执行完成
            Process process = Runtime.getRuntime().exec(cmd);
            this.exitValue = process.waitFor();
            //写出脚本执行中的过程信息
            BufferedReader infoInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorInput = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line = "";
            while ((line = infoInput.readLine()) != null) {
                System.out.println(line);
            }
            while ((line = errorInput.readLine()) != null) {
                System.out.println(line);
            }
            infoInput.close();
            errorInput.close();

            //阻塞执行线程直至脚本执行完成后返回

        } catch (Throwable e) {
//            log.error("CommandWaitForThread accure exception,shell " + cmd, e);
            exitValue = 110;
        } finally {
            finish = true;
        }
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public int getExitValue() {
        return exitValue;
    }
}


