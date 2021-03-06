package com.github.xsi640.common;

import com.github.xsi640.common.encode.EncodingUtils;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * 进程相关工具类
 */
public class ProcessUtils {

    /**
     * 开始一个进程，运行指定的命令
     *
     * @param command 命令
     * @throws IOException 发送IO错误
     */
    public static void start(String... command) throws IOException {
        start(command, false);
    }

    /**
     * 开始一个进程，并且等待进程结束
     *
     * @param command 命令
     * @param waitFor 是否等待进程执行完成
     * @throws IOException 发送IO错误
     */
    public static void start(String[] command, boolean waitFor) throws IOException {
        Process p = null;
        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            p = pb.start();
            if (waitFor) {
                p.waitFor();
            }
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
     *
     * @param command 命令
     * @param charset 编码方式
     * @return 命令执行的结果
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

    /**
     * 使用当前操作系统默认进程打开一个文件
     *
     * @param path 文件路径
     * @throws IOException 如果发生IO错误
     */
    public static void openFile(String path) throws IOException {
        if (OSUtils.isWindows()) {
            Runtime.getRuntime().exec(new String[]{"rundll32", "url.dll,FileProtocolHandler", path});
        } else if (OSUtils.isLinux() || OSUtils.isMacOS()) {
            Runtime.getRuntime().exec(new String[]{"/usr/bin/open", path});
        } else {
            // Unknown OS, try with desktop
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(new File(path));
            }
        }
    }
}
