package com.louie.luntonghui.task;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jack on 15/12/10.
 */
    /**
     * ÏÂÔØÏß³ÌÀà
     * @author Administrator
     *
     */
    public class DownloadThread extends Thread {
        private static final String TAG = "DownloadThread";
        private File saveFile;
        private URL downUrl;
        private int block;

        /* ÏÂÔØ¿ªÊ¼Î»ÖÃ  */
        private int threadId = -1;
        private int downLength;
        private boolean finish = false;
        private FileDownloader downloader;

        /**
         * @param downloader:ÏÂÔØÆ÷
         * @param downUrl:ÏÂÔØµØÖ·
         * @param saveFile:ÏÂÔØÂ·¾¶
         *
         */
        public DownloadThread(FileDownloader downloader, URL downUrl, File saveFile, int block, int downLength, int threadId) {
            this.downUrl = downUrl;
            this.saveFile = saveFile;
            this.block = block;
            this.downloader = downloader;
            this.threadId = threadId;
            this.downLength = downLength;
        }

        @Override
        public void run() {
            if(downLength < block){//Î´ÏÂÔØÍê³É
                try {
                    //Ê¹ÓÃGet·½Ê½ÏÂÔØ
                    HttpURLConnection http = (HttpURLConnection) downUrl.openConnection();
                    http.setConnectTimeout(5 * 1000);
                    http.setRequestMethod("GET");
                    http.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
                    http.setRequestProperty("Accept-Language", "zh-CN");
                    http.setRequestProperty("Referer", downUrl.toString());
                    http.setRequestProperty("Charset", "UTF-8");

                    int startPos = block * (threadId - 1) + downLength;//¿ªÊ¼Î»ÖÃ
                    int endPos = block * threadId -1;//½áÊøÎ»ÖÃ
                    http.setRequestProperty("Range", "bytes=" + startPos + "-"+ endPos);//ÉèÖÃ»ñÈ¡ÊµÌåÊý¾ÝµÄ·¶Î§
                    http.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
                    http.setRequestProperty("Connection", "Keep-Alive");

                    InputStream inStream = http.getInputStream();
                    byte[] buffer = new byte[1024];
                    int offset = 0;
                    print("Thread " + this.threadId + " start download from position "+ startPos);
                    RandomAccessFile threadfile = new RandomAccessFile(this.saveFile, "rwd");
                    threadfile.seek(startPos);

                    while ((offset = inStream.read(buffer, 0, 1024)) != -1) {
                        threadfile.write(buffer, 0, offset);
                        downLength += offset;
                        downloader.update(this.threadId, downLength);
                        downloader.append(offset);
                    }

                    threadfile.close();
                    inStream.close();
                    print("Thread " + this.threadId + " download finish");
                    this.finish = true;
                } catch (Exception e) {
                    this.downLength = -1;
                    print("Thread "+ this.threadId+ ":"+ e);
                }
            }
        }

        /**
         * ´òÓ¡ÈÕÖ¾ÐÅÏ¢
         * @param msg
         */
        private static void print(String msg){
           // Log.i(TAG, msg);
        }

        /**
         * ÏÂÔØÊÇ·ñÍê³É
         * @return
         */
        public boolean isFinish() {
            return finish;
        }

        /**
         * ÒÑ¾­ÏÂÔØµÄÄÚÈÝ´óÐ¡
         * @return Èç¹û·µ»ØÖµÎª-1,´ú±íÏÂÔØÊ§°Ü
         */
        public long getDownLength() {
            return downLength;
        }
}
