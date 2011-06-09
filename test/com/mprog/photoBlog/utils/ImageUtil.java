package com.mprog.photoBlog.utils;

import java.io.*;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesServiceFactory;

public class ImageUtil {

    // ファイルから画像を読み込み
    public static Image readImage(String filename) throws IOException {
        File f = new File(filename);
        InputStream is = new FileInputStream(f);
        int length = (int) f.length();
        byte [] buffer = new byte[length];
        
        int current = 0;
        while (current < length) {
            int len = is.read(buffer, current, length - current);
            if (len < 0)
                throw new IOException("could not fully read the file");
            current += len;
        }
        return ImagesServiceFactory.makeImage(buffer);
    }


    // ヘルパメソッド．ストリームからバイト列を取りだし 
    public static byte[] readBytes(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len;
        byte[] buf = new byte[10 * 1024];
        while ((len = is.read(buf)) >= 0) {
            bos.write(buf, 0, len);
        }
        return bos.toByteArray();
    }
}
