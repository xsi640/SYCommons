package com.github.xsi640.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import com.github.xsi640.common.encode.EncodingUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 * IO相关工具类
 */
public class IOUtils {

    /**
     * 判断文件或文件夹是否存在
     *
     * @param path 文件或文件夹路径
     * @return true-已存在，false-不存在
     */
    public static boolean exists(String path) {
        File f = new File(path);
        return f.exists();
    }

    /**
     * 判断是否是文件
     *
     * @param path 文件路径
     * @return true-是文件，false-不是文件
     */
    public static boolean isFile(String path) {
        File f = new File(path);
        return f.isFile();
    }

    /**
     * 判断是否是文件目录
     *
     * @param path 文件目录路径
     * @return true-是文件夹，false-不是文件夹
     */
    public static boolean isDirectory(String path) {
        File f = new File(path);
        return f.isDirectory();
    }

    /**
     * 获取文件大小
     *
     * @param path 文件路径
     * @return 如果是文件返回文件大小，否则，返回目录大小
     */
    public static long getFileSize(String path) {
        long result = 0;
        File f = new File(path);
        if (f.isFile()) {
            result = f.length();
        } else {
            result = FileUtils.sizeOfDirectory(f);
        }
        return result;
    }

    /**
     * 连接路径
     *
     * @param paths 路径名称
     * @return 完整路径
     */
    public static String combine(String... paths) {
        String result = "";
        for (String path : paths) {
            result = FilenameUtils.concat(result, path);
        }
        return result;
    }

    /**
     * 获取文件所在的目录，如果是文件夹，返回父文件夹
     *
     * @param path 文件路径
     * @return 所在目录，如果是文件夹，返回父文件夹
     */
    public static String getDirectory(String path) {
        File file = new File(path);
        return file.getParent();
    }

    /**
     * 删除指定文件
     *
     * @param path    文件路径
     * @param pattern 文件名称匹配的正则表达式
     */
    public static void delete(String path, String pattern) {
        File f = new File(path);
        if (f.exists()) {
            if (f.isFile()) {
                if (Pattern.compile(pattern).matcher(f.getName()).find()) {
                    f.delete();
                }
            } else {
                File[] files = f.listFiles();
                for (File file : files) {
                    delete(file.getPath(), pattern);
                }
            }
        }
    }

    /**
     * 删除文件或目录
     *
     * @param path 文件或文件夹路径
     */
    public static void delete(String path) {
        File f = new File(path);
        if (f.exists()) {
            if (f.isFile()) {
                f.delete();
            } else {
                try {
                    FileUtils.deleteDirectory(f);
                } catch (IOException e) {
                    try {
                        FileUtils.forceDelete(f);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 获取文件的扩展名，不带"."
     *
     * @param path 获取文件名的路径
     * @return 文件的扩展名，不带"."
     */
    public static String getExtension(String path) {
        return FilenameUtils.getExtension(path);
    }

    /**
     * 根据路径创建文件夹，如果文件夹已存在，不处理
     *
     * @param path 文件路径
     */
    public static void createDirectory(String path) {
        File f = new File(path);
        if (!f.exists())
            f.mkdirs();
    }

    /**
     * 获取所有文件，包括子文件
     *
     * @param path 文件夹路径
     * @return 所有文件的集合
     */
    public static List<File> getAllFiles(String path) {
        List<File> result = new ArrayList<File>();
        File file = new File(path);
        if (file.isFile()) {
            result.add(file);
        } else if (file.isDirectory()) {
            fillAllFiles(file, result);
        }
        return result;
    }

    private static void fillAllFiles(File file, List<File> fileLists) {
        File[] files = file.listFiles();
        for (File f : files) {
            fileLists.add(f);
            if (f.isDirectory()) {
                fillAllFiles(f, fileLists);
            }
        }
    }

    /**
     * 读取文件所有文本内容，默认UTF-8
     *
     * @param path 文件路径
     * @return 文件内容
     * @throws IOException 文件不存在或不能读取
     */
    public static String readFileAllText(String path) throws IOException {
        return FileUtils.readFileToString(new File(path), EncodingUtils.DEFAULT_CHARSET);
    }

    /**
     * 读取文件所有文本内容，默认UTF-8
     *
     * @param path    文件路径
     * @param charset 文件编码方式
     * @return 文件内容
     * @throws IOException 文件不存在或不能读取
     */
    public static String readFileAllText(String path, Charset charset) throws IOException {
        return FileUtils.readFileToString(new File(path), charset);
    }

    /**
     * 读取文件所有行内容，默认UTF-8编码
     *
     * @param path 文件路径
     * @return 文件所有的lines
     * @throws IOException 文件不存在或不能读取
     */
    public static List<String> readFileAllLine(String path) throws IOException {
        return FileUtils.readLines(new File(path), EncodingUtils.DEFAULT_CHARSET);
    }

    /**
     * 读取文件所有行内容
     *
     * @param path    文件路径
     * @param charset 编码方式
     * @return 文件所有的lines
     * @throws IOException 文件不存在或不能读取
     */
    public static List<String> readFileAllLine(String path, Charset charset) throws IOException {
        return FileUtils.readLines(new File(path), charset);
    }

    /**
     * 将制定内容写入一个文件，默认UTF-8编码
     *
     * @param path      文件路径
     * @param text      文件内容
     * @param overwrite 是否覆盖
     * @throws IOException 文件不能写入
     */
    public static void writeFileAllText(String path, String text, boolean overwrite) throws IOException {
        writeFileAllText(path, text, EncodingUtils.DEFAULT_CHARSET, overwrite);
    }

    /**
     * 将制定内容写入一个文件，默认UTF-8编码
     *
     * @param path      文件路径
     * @param text      文件内容
     * @param charset   编码方式
     * @param overwrite 是否覆盖
     * @throws IOException 文件不能写入
     */
    public static void writeFileAllText(String path, String text, Charset charset, boolean overwrite) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            if (!overwrite) {
                return;
            }
        } else {
            File parent = file.getParentFile();
            if (!parent.exists()) {
                parent.mkdir();
            }
        }

        FileUtils.writeStringToFile(file, text, charset);
    }


    /**
     * 将制定内容写入一个文件，默认UTF-8编码
     *
     * @param path      文件路径
     * @param lines     文件内容
     * @param overwrite 是否覆盖
     * @throws IOException 文件不能写入
     */
    public static void writeFileAllLines(String path, List<String> lines, boolean overwrite) throws IOException {
        writeFileAllLines(path, lines, EncodingUtils.DEFAULT_CHARSET, overwrite);
    }

    /**
     * 将制定内容写入一个文件，默认UTF-8编码
     *
     * @param path      文件路径
     * @param lines     文件内容
     * @param charset   编码方式
     * @param overwrite 是否覆盖
     * @throws IOException 文件不能写入
     */
    public static void writeFileAllLines(String path, List<String> lines, Charset charset, boolean overwrite) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            if (!overwrite) {
                return;
            }
        } else {
            File parent = file.getParentFile();
            if (!parent.exists()) {
                parent.mkdir();
            }
        }

        FileUtils.writeLines(file, charset.name(), lines);
    }

    /**
     * 将制定内容追加到一个文件，如果文件不存在，创建文件
     *
     * @param path    文件路径
     * @param charset 文件编码
     * @param lines   文件内容
     * @throws IOException 文件不能写入
     */
    public static void appendFile(String path, Charset charset, String... lines) throws IOException {
        File file = new File(path);
        boolean exists = file.exists();
        if (!exists) {
            String directory = getDirectory(path);
            createDirectory(directory);
        }

        FileUtils.writeLines(file, charset.name(), Arrays.asList(lines), true);
    }

    /**
     * 读取文件byte[]
     *
     * @param path 文件路径
     * @return 文件内容byte[]
     * @throws IOException 文件不存在或不能读取
     */
    public static byte[] readeFile(String path) throws IOException {
        return FileUtils.readFileToByteArray(new File(path));
    }

    /**
     * 写入文件
     * @param path 文件路径
     * @param data 文件内容byte[]
     * @param overwrite 是否覆盖
     * @throws IOException 文件不能写入
     */
    public static void writeFile(String path, byte[] data, boolean overwrite) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            if (overwrite) {
                return;
            }
        } else {
            File parent = file.getParentFile();
            if (!parent.exists()) {
                parent.mkdir();
            }
        }
        FileUtils.writeByteArrayToFile(file, data);
    }
}
