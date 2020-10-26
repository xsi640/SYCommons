package com.github.xsi640.common;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import com.github.xsi640.common.encode.EncodingUtils;
import org.apache.commons.lang3.StringUtils;

public class HttpUtils {

    public final static int RESPONSE_STREAM_MAX_LENGTH = 3850;
    public final static int MAX_TIME_OUT = 30000;
    public final static String DEFAULT_CONTENT_TYPE = "application/x-www-form-urlencoded";
    private final static String DEFAULT_USER_AGENT = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; SV1; .NET CLR 1.1.4322; .NET CLR 2.0.50727)";
    private static Map<String, String> MIME_FILE_TYPE = new HashMap<>();

    public static void download(String url, NameValueCollection nvc, String method, String path, HttpProxy proxy) {
        Method m = Method.GET;
        if ("GET".equalsIgnoreCase(method)) {
            m = Method.GET;
        } else if ("POST".equalsIgnoreCase(method)) {
            m = Method.POST;
        } else {
            throw new IllegalArgumentException("Http Method Failured.");
        }

        HttpURLConnection conn = null;
        InputStream inputStream = null;
        BufferedOutputStream bufferedOutputStream = null;

        try {
            conn = getConnection(url, nvc, m, proxy);
            inputStream = getInputStream(conn, nvc, m);
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(path));
            byte[] buffer = new byte[RESPONSE_STREAM_MAX_LENGTH];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                bufferedOutputStream.write(buffer, 0, read);
            }
            bufferedOutputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public static String getContent(String url, NameValueCollection nvc, String method, HttpProxy proxy) {
        Method m = Method.GET;
        if ("GET".equalsIgnoreCase(method)) {
            m = Method.GET;
        } else if ("POST".equalsIgnoreCase(method)) {
            m = Method.POST;
        } else {
            throw new IllegalArgumentException("Http Method Failured.");
        }

        HttpURLConnection conn = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        String result = "";

        try {
            conn = getConnection(url, nvc, m, proxy);
            inputStream = getInputStream(conn, nvc, m);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
            result = sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return result;
    }

    private static String getParams(NameValueCollection nvc) {
        if (nvc != null && nvc.size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (String key : nvc.keySet()) {
                sb.append(EncodingUtils.urlEncode((key)));
                sb.append("=");
                sb.append(EncodingUtils.urlEncode(nvc.get(key)));
                sb.append("&");
            }
            return sb.substring(0, sb.length() - 1);
        }
        return "";
    }

    private static String getUrl(String url, NameValueCollection nvc) {
        if (nvc != null && nvc.size() > 0) {
            StringBuffer sb = new StringBuffer(url);
            sb.append("?");
            for (String key : nvc.keySet()) {
                sb.append(EncodingUtils.urlEncode((key)));
                sb.append("=");
                sb.append(EncodingUtils.urlEncode(nvc.get(key)));
                sb.append("&");
            }
            return sb.substring(0, sb.length() - 1);
        }
        return url;
    }

    public enum Method {
        GET, POST
    }

    class UploadFileItem {
        private String fileName;
        private String filePath;
        private byte[] data;
        private long size;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public byte[] getData() {
            return data;
        }

        public void setData(byte[] data) {
            this.data = data;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }
    }

    public static HttpURLConnection getConnection(String url, NameValueCollection nvc, Method method, HttpProxy proxy)
            throws IOException {
        HttpURLConnection result = null;
        if (method == Method.POST) {
            URL u = new URL(url);
            if (u.getProtocol().equalsIgnoreCase("https")) {
                trustHttpsEveryone();
            }
            if (proxy == null)
                result = (HttpURLConnection) u.openConnection();
            else
                result = (HttpURLConnection) u.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxy.getHost(), proxy.getPort())));
            result.setRequestMethod("POST");
        } else {
            URL u = new URL(getUrl(url, nvc));
            result = (HttpURLConnection) u.openConnection();
            result.setRequestMethod("GET");
        }
        result.setRequestProperty("Content-Type", DEFAULT_CONTENT_TYPE);
        result.setRequestProperty("User-Agent", DEFAULT_USER_AGENT);
        result.setReadTimeout(MAX_TIME_OUT);
        return result;
    }

    public static InputStream getInputStream(HttpURLConnection conn, NameValueCollection nvc, Method method)
            throws IOException {
        if (method == Method.POST) {
            conn.setDoInput(true);
            conn.setDoOutput(true);
            String param = getParams(nvc);
            if (param != null && !param.isEmpty()) {
                byte[] data = EncodingUtils.decode(param);
                conn.setRequestProperty("Content-Lenth", String.valueOf(data.length));
                OutputStream outputStream = (OutputStream) conn.getOutputStream();
                outputStream.write(data);
                outputStream.flush();
                outputStream.close();
            }
        }
        return conn.getInputStream();
    }

    public static String getRedirectUrl(String url) throws IOException {
        HttpURLConnection.setFollowRedirects(false);
        URL u = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        if (u.getProtocol().equalsIgnoreCase("https")) {
            trustHttpsEveryone();
        }
        conn.setReadTimeout(5000);
        conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
        conn.addRequestProperty("User-Agent", "Mozilla");
        conn.addRequestProperty("Referer", "google.com");
        boolean redirect = false;
        int status = conn.getResponseCode();
        if (status != HttpURLConnection.HTTP_OK) {
            if (status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM
                    || status == HttpURLConnection.HTTP_SEE_OTHER)
                redirect = true;
        }
        if (redirect) {
            String newUrl = conn.getHeaderField("Location");
            if (!StringUtils.isEmpty(newUrl)) {
                return newUrl;
            }
        }
        return url;
    }

    public static void trustHttpsEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (Exception e) {
        }
    }

    /**
     * 通过文件扩展名获取mime type
     *
     * @param extName 文件扩展名
     * @return mime-type
     */
    public static String getMimeByExtName(String extName) {
        if (extName.startsWith(".")) {
            extName = extName.substring(1);
        }
        if (MIME_FILE_TYPE.containsKey(extName))
            return MIME_FILE_TYPE.get(extName);
        return "";
    }

    static {
        MIME_FILE_TYPE.put("css", "text/css");
        MIME_FILE_TYPE.put("ps", "application/postscript");
        MIME_FILE_TYPE.put("movie", "video/x-sgi-movie");
        MIME_FILE_TYPE.put("bin", "application/octet-stream");
        MIME_FILE_TYPE.put("dms", "application/octet-stream");
        MIME_FILE_TYPE.put("skd", "application/x-koan");
        MIME_FILE_TYPE.put("xul", "application/vnd.mozilla.xul+xml");
        MIME_FILE_TYPE.put("midi", "audio/midi");
        MIME_FILE_TYPE.put("exe", "application/octet-stream");
        MIME_FILE_TYPE.put("igs", "model/iges");
        MIME_FILE_TYPE.put("skm", "application/x-koan");
        MIME_FILE_TYPE.put("texi", "application/x-texinfo");
        MIME_FILE_TYPE.put("mov", "video/quicktime");
        MIME_FILE_TYPE.put("dvi", "application/x-dvi");
        MIME_FILE_TYPE.put("xml", "application/xml");
        MIME_FILE_TYPE.put("skp", "application/x-koan");
        MIME_FILE_TYPE.put("apk", "application/vnd.android.package-archive");
        MIME_FILE_TYPE.put("skt", "application/x-koan");
        MIME_FILE_TYPE.put("pict", "image/pict");
        MIME_FILE_TYPE.put("ram", "audio/x-pn-realaudio");
        MIME_FILE_TYPE.put("zip", "application/zip");
        MIME_FILE_TYPE.put("mpe", "video/mpeg");
        MIME_FILE_TYPE.put("qt", "video/quicktime");
        MIME_FILE_TYPE.put("cdf", "application/x-netcdf");
        MIME_FILE_TYPE.put("mpg", "video/mpeg");
        MIME_FILE_TYPE.put("ras", "image/x-cmu-raster");
        MIME_FILE_TYPE.put("bcpio", "application/x-bcpio");
        MIME_FILE_TYPE.put("tex", "application/x-tex");
        MIME_FILE_TYPE.put("torrent", "application/octet-stream");
        MIME_FILE_TYPE.put("ai", "application/postscript");
        MIME_FILE_TYPE.put("png", "image/png");
        MIME_FILE_TYPE.put("eps", "application/postscript");
        MIME_FILE_TYPE.put("mathml", "application/mathml+xml");
        MIME_FILE_TYPE.put("mxu", "video/vnd.mpegurl");
        MIME_FILE_TYPE.put("oda", "application/oda");
        MIME_FILE_TYPE.put("texinfo", "application/x-texinfo");
        MIME_FILE_TYPE.put("pnm", "image/x-portable-anymap");
        MIME_FILE_TYPE.put("ra", "audio/x-pn-realaudio");
        MIME_FILE_TYPE.put("au", "audio/basic");
        MIME_FILE_TYPE.put("pnt", "image/x-macpaint");
        MIME_FILE_TYPE.put("doc", "application/msword");
        MIME_FILE_TYPE.put("rm", "application/vnd.rn-realmedia");
        MIME_FILE_TYPE.put("aif", "audio/x-aiff");
        MIME_FILE_TYPE.put("xwd", "image/x-xwindowdump");
        MIME_FILE_TYPE.put("js", "application/x-javascript");
        MIME_FILE_TYPE.put("mid", "audio/midi");
        MIME_FILE_TYPE.put("mif", "application/vnd.mif");
        MIME_FILE_TYPE.put("mac", "image/x-macpaint");
        MIME_FILE_TYPE.put("smi", "application/smil");
        MIME_FILE_TYPE.put("sh", "application/x-sh");
        MIME_FILE_TYPE.put("pgn", "application/x-chess-pgn");
        MIME_FILE_TYPE.put("vcd", "application/x-cdlink");
        MIME_FILE_TYPE.put("pgm", "image/x-portable-graymap");
        MIME_FILE_TYPE.put("wml", "text/vnd.wap.wml");
        MIME_FILE_TYPE.put("jpeg", "image/jpeg");
        MIME_FILE_TYPE.put("man", "application/x-troff-man");
        MIME_FILE_TYPE.put("3gp", "video/3gpp");
        MIME_FILE_TYPE.put("so", "application/octet-stream");
        MIME_FILE_TYPE.put("dxr", "application/x-director");
        MIME_FILE_TYPE.put("wmv", "video/x-ms-wmv");
        MIME_FILE_TYPE.put("lha", "application/octet-stream");
        MIME_FILE_TYPE.put("rtf", "text/rtf");
        MIME_FILE_TYPE.put("svg", "image/svg+xml");
        MIME_FILE_TYPE.put("gram", "application/srgs");
        MIME_FILE_TYPE.put("snd", "audio/basic");
        MIME_FILE_TYPE.put("ppm", "image/x-portable-pixmap");
        MIME_FILE_TYPE.put("silo", "model/mesh");
        MIME_FILE_TYPE.put("vrml", "model/vrml");
        MIME_FILE_TYPE.put("asc", "text/plain");
        MIME_FILE_TYPE.put("flv", "video/x-flv");
        MIME_FILE_TYPE.put("txt", "text/plain");
        MIME_FILE_TYPE.put("shar", "application/x-shar");
        MIME_FILE_TYPE.put("t", "application/x-troff");
        MIME_FILE_TYPE.put("xpm", "image/x-xpixmap");
        MIME_FILE_TYPE.put("ppt", "application/vnd.ms-powerpoint");
        MIME_FILE_TYPE.put("rdf", "application/rdf+xml");
        MIME_FILE_TYPE.put("rtx", "text/richtext");
        MIME_FILE_TYPE.put("atom", "application/atom+xml");
        MIME_FILE_TYPE.put("cpio", "application/x-cpio");
        MIME_FILE_TYPE.put("iges", "model/iges");
        MIME_FILE_TYPE.put("tr", "application/x-troff");
        MIME_FILE_TYPE.put("dif", "video/x-dv");
        MIME_FILE_TYPE.put("msh", "model/mesh");
        MIME_FILE_TYPE.put("swf", "application/x-shockwave-flash");
        MIME_FILE_TYPE.put("bmp", "image/bmp");
        MIME_FILE_TYPE.put("xht", "application/xhtml+xml");
        MIME_FILE_TYPE.put("cgm", "image/cgm");
        MIME_FILE_TYPE.put("roff", "application/x-troff");
        MIME_FILE_TYPE.put("ice", "x-conference/x-cooltalk");
        MIME_FILE_TYPE.put("pic", "image/pict");
        MIME_FILE_TYPE.put("dir", "application/x-director");
        MIME_FILE_TYPE.put("latex", "application/x-latex");
        MIME_FILE_TYPE.put("hqx", "application/mac-binhex40");
        MIME_FILE_TYPE.put("mpga", "audio/mpeg");
        MIME_FILE_TYPE.put("ogg", "application/ogg");
        MIME_FILE_TYPE.put("tif", "image/tiff");
        MIME_FILE_TYPE.put("dv", "video/x-dv");
        MIME_FILE_TYPE.put("ico", "image/x-icon");
        MIME_FILE_TYPE.put("ics", "text/calendar");
        MIME_FILE_TYPE.put("me", "application/x-troff-me");
        MIME_FILE_TYPE.put("sgm", "text/sgml");
        MIME_FILE_TYPE.put("xyz", "chemical/x-xyz");
        MIME_FILE_TYPE.put("wbmp", "image/vnd.wap.wbmp");
        MIME_FILE_TYPE.put("html", "text/html");
        MIME_FILE_TYPE.put("lzh", "application/octet-stream");
        MIME_FILE_TYPE.put("ogv", "video/ogv");
        MIME_FILE_TYPE.put("tar", "application/x-tar");
        MIME_FILE_TYPE.put("cpt", "application/mac-compactpro");
        MIME_FILE_TYPE.put("ms", "application/x-troff-ms");
        MIME_FILE_TYPE.put("qti", "image/x-quicktime");
        MIME_FILE_TYPE.put("etx", "text/x-setext");
        MIME_FILE_TYPE.put("grxml", "application/srgs+xml");
        MIME_FILE_TYPE.put("jp2", "image/jp2");
        MIME_FILE_TYPE.put("spl", "application/x-futuresplash");
        MIME_FILE_TYPE.put("webm", "video/webm");
        MIME_FILE_TYPE.put("djv", "image/vnd.djvu");
        MIME_FILE_TYPE.put("ez", "application/andrew-inset");
        MIME_FILE_TYPE.put("nc", "application/x-netcdf");
        MIME_FILE_TYPE.put("qtif", "image/x-quicktime");
        MIME_FILE_TYPE.put("mpeg", "video/mpeg");
        MIME_FILE_TYPE.put("pbm", "image/x-portable-bitmap");
        MIME_FILE_TYPE.put("xbm", "image/x-xbitmap");
        MIME_FILE_TYPE.put("tiff", "image/tiff");
        MIME_FILE_TYPE.put("aiff", "audio/x-aiff");
        MIME_FILE_TYPE.put("gif", "image/gif");
        MIME_FILE_TYPE.put("pntg", "image/x-macpaint");
        MIME_FILE_TYPE.put("sgml", "text/sgml");
        MIME_FILE_TYPE.put("aifc", "audio/x-aiff");
        MIME_FILE_TYPE.put("smil", "application/smil");
        MIME_FILE_TYPE.put("ief", "image/ief");
        MIME_FILE_TYPE.put("rgb", "image/x-rgb");
        MIME_FILE_TYPE.put("m3u", "audio/x-mpegurl");
        MIME_FILE_TYPE.put("xsl", "application/xml");
        MIME_FILE_TYPE.put("dcr", "application/x-director");
        MIME_FILE_TYPE.put("avi", "video/x-msvideo");
        MIME_FILE_TYPE.put("dtd", "application/xml-dtd");
        MIME_FILE_TYPE.put("sv4crc", "application/x-sv4crc");
        MIME_FILE_TYPE.put("tsv", "text/tab-separated-values");
        MIME_FILE_TYPE.put("vxml", "application/voicexml+xml");
        MIME_FILE_TYPE.put("sv4cpio", "application/x-sv4cpio");
        MIME_FILE_TYPE.put("wbxml", "application/vnd.wap.wbxml");
        MIME_FILE_TYPE.put("m4a", "audio/mp4a-latm");
        MIME_FILE_TYPE.put("tcl", "application/x-tcl");
        MIME_FILE_TYPE.put("class", "application/octet-stream");
        MIME_FILE_TYPE.put("kar", "audio/midi");
        MIME_FILE_TYPE.put("jpe", "image/jpeg");
        MIME_FILE_TYPE.put("mesh", "model/mesh");
        MIME_FILE_TYPE.put("sit", "application/x-stuffit");
        MIME_FILE_TYPE.put("htm", "text/html");
        MIME_FILE_TYPE.put("jpg", "image/jpeg");
        MIME_FILE_TYPE.put("pct", "image/pict");
        MIME_FILE_TYPE.put("ustar", "application/x-ustar");
        MIME_FILE_TYPE.put("ifb", "text/calendar");
        MIME_FILE_TYPE.put("dll", "application/octet-stream");
        MIME_FILE_TYPE.put("src", "application/x-wais-source");
        MIME_FILE_TYPE.put("wmlsc", "application/vnd.wap.wmlscriptc");
        MIME_FILE_TYPE.put("m4p", "audio/mp4a-latm");
        MIME_FILE_TYPE.put("djvu", "image/vnd.djvu");
        MIME_FILE_TYPE.put("wmls", "text/vnd.wap.wmlscript");
        MIME_FILE_TYPE.put("hdf", "application/x-hdf");
        MIME_FILE_TYPE.put("wav", "audio/x-wav");
        MIME_FILE_TYPE.put("gtar", "application/x-gtar");
        MIME_FILE_TYPE.put("m4v", "video/x-m4v");
        MIME_FILE_TYPE.put("mp2", "audio/mpeg");
        MIME_FILE_TYPE.put("m4u", "video/vnd.mpegurl");
        MIME_FILE_TYPE.put("pdb", "chemical/x-pdb");
        MIME_FILE_TYPE.put("xhtml", "application/xhtml+xml");
        MIME_FILE_TYPE.put("mp4", "video/mp4");
        MIME_FILE_TYPE.put("wrl", "model/vrml");
        MIME_FILE_TYPE.put("mp3", "audio/mpeg");
        MIME_FILE_TYPE.put("gz", "application/x-gzip");
        MIME_FILE_TYPE.put("pdf", "application/pdf");
        MIME_FILE_TYPE.put("csh", "application/x-csh");
        MIME_FILE_TYPE.put("jnlp", "application/x-java-jnlp-file");
        MIME_FILE_TYPE.put("wmlc", "application/vnd.wap.wmlc");
        MIME_FILE_TYPE.put("xslt", "application/xslt+xml");
        MIME_FILE_TYPE.put("xls", "application/vnd.ms-excel");
        MIME_FILE_TYPE.put("dmg", "application/octet-stream");
    }
}
