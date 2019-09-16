package com.github.xsi640.common;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZipUtils {
    public static final int BUFFER = 4096;
    public static final String EXT = ".gz";

    /**
     * 数据压缩
     *
     * @param data
     * @return
     * @throws Exception
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
     * 文件压缩
     *
     * @param file
     * @throws Exception
     */
    public static void compress(File file) throws IOException {
        compress(file, true);
    }

    /**
     * 文件压缩
     *
     * @param file
     * @param delete 是否删除原始文件
     * @throws IOException
     */
    public static void compress(File file, boolean delete) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            try (FileOutputStream fos = new FileOutputStream(file.getPath() + EXT)) {
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
     * @param is
     * @param os
     * @throws Exception
     */
    public static void compress(InputStream is, OutputStream os) throws IOException {
        try (GZIPOutputStream gos = new GZIPOutputStream(os)) {
            int count;
            byte data[] = new byte[BUFFER];
            while ((count = is.read(data, 0, BUFFER)) != -1) {
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
     * @param path
     * @throws Exception
     */
    public static void compress(String path) throws IOException {
        compress(path, false);
    }

    /**
     * 文件压缩
     *
     * @param path
     * @param delete 是否删除原始文件
     * @throws Exception
     */
    public static void compress(String path, boolean delete) throws IOException {
        File file = new File(path);
        compress(file, delete);
    }

    /**
     * 数据解压缩
     *
     * @param data
     * @return
     * @throws Exception
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
     * @param file
     * @throws Exception
     */
    public static void decompress(File file) throws IOException {
        decompress(file, true);
    }

    /**
     * 文件解压缩
     *
     * @param file
     * @param delete 是否删除原始文件
     * @throws Exception
     */
    public static void decompress(File file, boolean delete) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            try (FileOutputStream fos = new FileOutputStream(file.getPath().replace(EXT, ""))) {
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
     * @param is
     * @param os
     * @throws Exception
     */
    public static void decompress(InputStream is, OutputStream os) throws IOException {
        try (GZIPInputStream gis = new GZIPInputStream(is)) {
            int count;
            byte data[] = new byte[BUFFER];
            while ((count = gis.read(data, 0, BUFFER)) != -1) {
                os.write(data, 0, count);
            }
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 文件解压缩
     *
     * @param path
     * @throws Exception
     */
    public static void decompress(String path) throws IOException {
        decompress(path, true);
    }

    /**
     * 文件解压缩
     *
     * @param path
     * @param delete 是否删除原始文件
     * @throws Exception
     */
    public static void decompress(String path, boolean delete) throws IOException {
        File file = new File(path);
        decompress(file, delete);
    }
}
