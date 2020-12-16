package com.github.xsi640.common;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * GZip压缩工具类（仅支持单文件，或者数据、流的压缩和解压缩）
 *
 * @author suyang
 */
public class GZipUtils {
    public static final int BUFFER_SIZE = 4096;
    public static final String EXT_NAME = ".gz";

    /**
     * byte[]压缩
     *
     * @param data 压缩的byte[]
     * @return 压缩后的byte[]
     * @throws IOException 如果发生IO错误
     */
    public static byte[] compress(byte[] data) throws IOException {
        if (data == null || data.length == 0)
            return null;

        try (ByteArrayInputStream bais = new ByteArrayInputStream(data)) {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                compress(bais, baos);
                baos.flush();
                return baos.toByteArray();
            } catch (IOException e) {
                throw e;
            }
        } catch (IOException e1) {
            throw e1;
        }
    }

    /**
     * 文件压缩，默认生成新文件，[原文件名].gz
     *
     * @param file 要压缩的文件
     * @throws IOException 如果发生IO错误
     */
    public static void compress(File file) throws IOException {
        compress(file, true);
    }

    /**
     * 文件压缩，默认生成新文件，[原文件名].gz
     *
     * @param file   要压缩的文件
     * @param delete 是否删除源文件
     * @throws IOException 如果发生IO错误
     */
    public static void compress(File file, boolean delete) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            try (FileOutputStream fos = new FileOutputStream(file.getPath() + EXT_NAME)) {
                compress(fis, fos);
                fos.flush();
            }
        }
        if (delete) {
            file.delete();
        }
    }

    /**
     * 数据压缩
     *
     * @param is 输入的数据流（压缩前）
     * @param os 输出的数据流（压缩后）
     * @throws IOException 如果发送IO错误
     */
    public static void compress(InputStream is, OutputStream os) throws IOException {
        try (GZIPOutputStream gos = new GZIPOutputStream(os)) {
            int count;
            byte data[] = new byte[BUFFER_SIZE];
            while ((count = is.read(data, 0, BUFFER_SIZE)) != -1) {
                gos.write(data, 0, count);
            }
            gos.finish();
            gos.flush();
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 文件压缩
     *
     * @param path 要压缩的文件路径，默认生成新文件名[原文件名].gz
     * @throws IOException 如果发生IO错误
     */
    public static void compress(String path) throws IOException {
        compress(path, false);
    }

    /**
     * 文件压缩
     *
     * @param path   要压缩的文件路径，默认生成新文件名[原文件名].gz
     * @param delete 是否删除原始文件
     * @throws IOException 如果发生IO错误
     */
    public static void compress(String path, boolean delete) throws IOException {
        File file = new File(path);
        compress(file, delete);
    }

    /**
     * 数据解压缩
     *
     * @param data 要解压缩的byte[]
     * @return 解压缩后的byte[]
     * @throws IOException 如果发生IO错误
     */
    public static byte[] decompress(byte[] data) throws IOException {
        if (data == null || data.length == 0)
            return null;
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data)) {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                decompress(bais, baos);
                baos.flush();
                return baos.toByteArray();
            } catch (IOException e) {
                throw e;
            }
        } catch (Exception e1) {
            throw e1;
        }
    }

    /**
     * 文件解压缩
     *
     * @param file 要解压缩的文件，解压缩的文件默认去除.gz扩展名
     * @throws IOException 如果发生IO错误
     */
    public static void decompress(File file) throws IOException {
        decompress(file, true);
    }

    /**
     * 文件解压缩文件
     *
     * @param file   要解压缩的文件，解压缩的文件默认去除.gz扩展名
     * @param delete 是否删除原始文件
     * @throws IOException 如果发生IO错误
     */
    public static void decompress(File file, boolean delete) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            try (FileOutputStream fos = new FileOutputStream(file.getPath().replace(EXT_NAME, ""))) {
                decompress(fis, fos);
                fos.flush();
            } catch (IOException e) {
                throw e;
            }
        } catch (IOException e1) {
            throw e1;
        }

        if (delete) {
            file.delete();
        }
    }

    /**
     * 数据解压缩
     *
     * @param is 要解压缩的数据流
     * @param os 解压缩后的数据流
     * @throws IOException 如果发生IO错误
     */
    public static void decompress(InputStream is, OutputStream os) throws IOException {
        try (GZIPInputStream gis = new GZIPInputStream(is)) {
            int count;
            byte data[] = new byte[BUFFER_SIZE];
            while ((count = gis.read(data, 0, BUFFER_SIZE)) != -1) {
                os.write(data, 0, count);
            }
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 文件解压缩
     *
     * @param path 要解压缩的文件，解压缩的文件默认去除.gz扩展名
     * @throws IOException 如果发生IO错误
     */
    public static void decompress(String path) throws IOException {
        decompress(path, true);
    }

    /**
     * 文件解压缩
     *
     * @param path   要解压缩的文件，解压缩的文件默认去除.gz扩展名
     * @param delete 是否删除原始文件
     * @throws IOException 如果发生IO错误
     */
    public static void decompress(String path, boolean delete) throws IOException {
        File file = new File(path);
        decompress(file, delete);
    }
}
