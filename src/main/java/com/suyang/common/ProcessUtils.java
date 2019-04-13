package com.suyang.common;

import com.suyang.common.encode.EncodingUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class ProcessUtils {

    /**
     * 开始一个进程
     * @param command
     */
    public static void start(String... command) {
        ProcessBuilder pb = new ProcessBuilder(command);
        try {
            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始一个进程，并且等待进程结束
     * @param command
     * @param waitFor
     */
    public static void start(String[] command, boolean waitFor) {
        Process p = null;
        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            p = pb.start();
            if (waitFor) {
                p.waitFor();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (p != null) {
                p.destroy();
            }
        }
    }

    /**
     * 开始一个进程，等待进程结束后，返回结果
     * @param command
     * @param charset
     * @return
     */
    public static String startWithResult(String[] command, Charset charset) {
        StringBuilder sb = new StringBuilder();
        Process process = null;
        BufferedReader br = null;
        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            process = pb.start();
            process.waitFor();
            String line = null;
            br = new BufferedReader(new InputStreamReader(process.getInputStream(), charset == null ? EncodingUtils.DEFAULT_CHARSET : charset));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
