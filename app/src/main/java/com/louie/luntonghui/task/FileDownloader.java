package com.louie.luntonghui.task;

import android.content.Context;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jack on 15/12/10.
 */
public class FileDownloader {

    private static final String TAG = "FileDownloader";
    private Context context;
    private FileService fileService;

    /* ÒÑÏÂÔØÎÄ¼þ³¤¶È */
    private int downloadSize = 0;

    /* Ô­Ê¼ÎÄ¼þ³¤¶È */
    private int fileSize = 0;

    /* Ïß³ÌÊý */
    private DownloadThread[] threads;

    /* ±¾µØ±£´æÎÄ¼þ */
    private File saveFile;

    /* »º´æ¸÷Ïß³ÌÏÂÔØµÄ³¤¶È*/
    private Map<Integer, Integer> data = new ConcurrentHashMap<Integer, Integer>();

    /* Ã¿ÌõÏß³ÌÏÂÔØµÄ³¤¶È */
    private int block;

    /* ÏÂÔØÂ·¾¶  */
    private String downloadUrl;

    /**
     * »ñÈ¡Ïß³ÌÊý
     */
    public int getThreadSize() {
        return threads.length;
    }

    /**
     * »ñÈ¡ÎÄ¼þ´óÐ¡
     * @return
     */
    public int getFileSize() {
        return fileSize;
    }

    /**
     * ÀÛ¼ÆÒÑÏÂÔØ´óÐ¡
     * @param size
     */
    protected synchronized void append(int size) {
        downloadSize += size;
    }

    /**
     * ¸üÐÂÖ¸¶¨Ïß³Ì×îºóÏÂÔØµÄÎ»ÖÃ
     * @param threadId Ïß³Ìid
     * @param pos ×îºóÏÂÔØµÄÎ»ÖÃ
     */
    protected synchronized void update(int threadId, int pos) {
        this.data.put(threadId, pos);
        this.fileService.update(this.downloadUrl, this.data);
    }

    /**
     * ¹¹½¨ÎÄ¼þÏÂÔØÆ÷
     * @param downloadUrl ÏÂÔØÂ·¾¶
     * @param fileSaveDir ÎÄ¼þ±£´æÄ¿Â¼
     * @param threadNum ÏÂÔØÏß³ÌÊý
     */
    public FileDownloader(Context context, String downloadUrl, File fileSaveDir, int threadNum)
    {
        try {
            this.context = context;
            this.downloadUrl = downloadUrl;
            fileService = new FileService(this.context);
            URL url = new URL(this.downloadUrl);
            if(!fileSaveDir.exists()) fileSaveDir.mkdirs();
            this.threads = new DownloadThread[threadNum];

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5*1000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
            conn.setRequestProperty("Accept-Language", "zh-CN");
            conn.setRequestProperty("Referer", downloadUrl);
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.connect();
            printResponseHeader(conn);

            if (conn.getResponseCode()==200) {
                this.fileSize = conn.getContentLength();//¸ù¾ÝÏìÓ¦»ñÈ¡ÎÄ¼þ´óÐ¡
                if (this.fileSize <= 0) throw new RuntimeException("Unkown file size ");

                String filename = getFileName(conn);//»ñÈ¡ÎÄ¼þÃû³Æ
                this.saveFile = new File(fileSaveDir, filename);//¹¹½¨±£´æÎÄ¼þ
                Map<Integer, Integer> logdata = fileService.getData(downloadUrl);//»ñÈ¡ÏÂÔØ¼ÇÂ¼

                if(logdata.size()>0){//Èç¹û´æÔÚÏÂÔØ¼ÇÂ¼
                    for(Map.Entry<Integer, Integer> entry : logdata.entrySet())
                        data.put(entry.getKey(), entry.getValue());//°Ñ¸÷ÌõÏß³ÌÒÑ¾­ÏÂÔØµÄÊý¾Ý³¤¶È·ÅÈëdataÖÐ
                }

                if(this.data.size()==this.threads.length){//ÏÂÃæ¼ÆËãËùÓÐÏß³ÌÒÑ¾­ÏÂÔØµÄÊý¾Ý³¤¶È
                    for (int i = 0; i < this.threads.length; i++) {
                        this.downloadSize += this.data.get(i+1);
                    }

                    print("ÒÑ¾­ÏÂÔØµÄ³¤¶È"+ this.downloadSize);
                }

                this.block = (this.fileSize % this.threads.length)==0? this.fileSize / this.threads.length : this.fileSize / this.threads.length + 1;
            }else{
                throw new RuntimeException("server no response ");
            }
        } catch (Exception e) {
            print(e.toString());
            throw new RuntimeException("don't connection this url");
        }
    }

    /**
     * »ñÈ¡ÎÄ¼þÃû
     * @param conn
     * @return
     */
    private String getFileName(HttpURLConnection conn) {
        String filename = this.downloadUrl.substring(this.downloadUrl.lastIndexOf('/') + 1);

        if(filename==null || "".equals(filename.trim())){//Èç¹û»ñÈ¡²»µ½ÎÄ¼þÃû³Æ
            for (int i = 0;; i++) {
                String mine = conn.getHeaderField(i);

                if (mine == null) break;

                if("content-disposition".equals(conn.getHeaderFieldKey(i).toLowerCase())){
                    Matcher m = Pattern.compile(".*filename=(.*)").matcher(mine.toLowerCase());
                    if(m.find()) return m.group(1);
                }
            }

            filename = UUID.randomUUID()+ ".tmp";//Ä¬ÈÏÈ¡Ò»¸öÎÄ¼þÃû
        }

        return filename;
    }

    /**
     *  ¿ªÊ¼ÏÂÔØÎÄ¼þ
     * @param listener ¼àÌýÏÂÔØÊýÁ¿µÄ±ä»¯,Èç¹û²»ÐèÒªÁË½âÊµÊ±ÏÂÔØµÄÊýÁ¿,¿ÉÒÔÉèÖÃÎªnull
     * @return ÒÑÏÂÔØÎÄ¼þ´óÐ¡
     * @throws Exception
     */
    public int download(DownloadProgressListener listener) throws Exception{
        try {
            RandomAccessFile randOut = new RandomAccessFile(this.saveFile, "rw");
            if(this.fileSize>0) randOut.setLength(this.fileSize);
            randOut.close();
            URL url = new URL(this.downloadUrl);

            if(this.data.size() != this.threads.length){
                this.data.clear();

                for (int i = 0; i < this.threads.length; i++) {
                    this.data.put(i+1, 0);//³õÊ¼»¯Ã¿ÌõÏß³ÌÒÑ¾­ÏÂÔØµÄÊý¾Ý³¤¶ÈÎª0
                }
            }

            for (int i = 0; i < this.threads.length; i++) {//¿ªÆôÏß³Ì½øÐÐÏÂÔØ
                int downLength = this.data.get(i+1);

                if(downLength < this.block && this.downloadSize<this.fileSize){//ÅÐ¶ÏÏß³ÌÊÇ·ñÒÑ¾­Íê³ÉÏÂÔØ,·ñÔò¼ÌÐøÏÂÔØ
                    this.threads[i] = new DownloadThread(this, url, this.saveFile, this.block, this.data.get(i+1), i+1);
                    this.threads[i].setPriority(7);
                    this.threads[i].start();
                }else{
                    this.threads[i] = null;
                }
            }

            this.fileService.save(this.downloadUrl, this.data);
            boolean notFinish = true;//ÏÂÔØÎ´Íê³É

            while (notFinish) {// Ñ­»·ÅÐ¶ÏËùÓÐÏß³ÌÊÇ·ñÍê³ÉÏÂÔØ
                Thread.sleep(900);
                notFinish = false;//¼Ù¶¨È«²¿Ïß³ÌÏÂÔØÍê³É

                for (int i = 0; i < this.threads.length; i++){
                    if (this.threads[i] != null && !this.threads[i].isFinish()) {
                        notFinish = true;

                        if(this.threads[i].getDownLength() == -1){
                            this.threads[i] = new DownloadThread(this, url, this.saveFile, this.block, this.data.get(i+1), i+1);
                            this.threads[i].setPriority(7);
                            this.threads[i].start();
                        }
                    }
                }

                if(listener!=null) listener.onDownloadSize(this.downloadSize);
            }

            fileService.delete(this.downloadUrl);
        } catch (Exception e) {
            print(e.toString());
            throw new Exception("file download fail");
        }
        return this.downloadSize;
    }

    /**
     * »ñÈ¡HttpÏìÓ¦Í·×Ö¶Î
     * @param http
     * @return
     */
    public static Map<String, String> getHttpResponseHeader(HttpURLConnection http) {
        Map<String, String> header = new LinkedHashMap<String, String>();

        for (int i = 0;; i++) {
            String mine = http.getHeaderField(i);
            if (mine == null) break;
            header.put(http.getHeaderFieldKey(i), mine);
        }

        return header;
    }

    /**
     * ´òÓ¡HttpÍ·×Ö¶Î
     * @param http
     */
    public static void printResponseHeader(HttpURLConnection http){
        Map<String, String> header = getHttpResponseHeader(http);

        for(Map.Entry<String, String> entry : header.entrySet()){
            String key = entry.getKey()!=null ? entry.getKey()+ ":" : "";
            print(key+ entry.getValue());
        }
    }

    /**
     * ´òÓ¡ÈÕÖ¾ÐÅÏ¢
     * @param msg
     */
    private static void print(String msg){
       // Log.i(TAG, msg);
    }
}
