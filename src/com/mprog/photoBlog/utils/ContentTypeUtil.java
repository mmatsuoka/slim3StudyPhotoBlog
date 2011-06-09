package com.mprog.photoBlog.utils;

import org.slim3.controller.upload.FileItem;

public final class ContentTypeUtil {
    
    private final static  String [] imageType = new String[]{ "jpeg", "png","gif", };

    public static  boolean isImage(FileItem fi) {
        
        if(fi == null || fi.getData() == null || fi.getData().length < 1 ){
            return false;
        }
        
        
        for(String t : imageType){
            if(t.equals(fi.getContentType()) ){
                return true;
            }
        }
        
        return false;
    }
    
}
