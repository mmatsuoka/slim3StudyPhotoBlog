package com.mprog.photoBlog.validator;

import java.util.Map;

import org.datanucleus.util.StringUtils;
import org.slim3.controller.upload.FileItem;
import org.slim3.controller.validator.AbstractValidator;
import org.slim3.util.ApplicationMessage;

public class ImageFileValidator extends AbstractValidator {

    private static final int maxFileSize = 1024 * 1024;
    
    public ImageFileValidator() {
        super(null);
    }

    
    public ImageFileValidator(String message) {
        super(message);
    }

    public String validate(Map<String, Object> parameters, String name) {
        
        
        Object value = parameters.get(name);
        if (value == null || "".equals(value)) {
            return null;
        }
        
        FileItem fi = (FileItem)value;
        if(fi.getData() == null ){
            return null;
        }
        
        
        if(! isImageContentType(fi)){
            if (message != null) {
                return message;
            }
            return ApplicationMessage.get(
                "validator.isNotImageFile",
                getLabel(name) );
        }
        
       
        if(fi.getData().length > maxFileSize ){
            if (message != null) {
                return message;
            }
            return ApplicationMessage.get(
                "validator.maxFileSize",
                getLabel(name) );
        }
        
        return null;
        

        
    }

    @Override
    protected String getMessageKey() {
        // TODO Auto-generated method stub
        return null;
    }


    private boolean isImageContentType(FileItem fi){
        if(StringUtils.isEmpty(fi.getContentType())){
            return false;
        }
        
        if(fi.getContentType().startsWith("image")){
            return true;
        }else{
            return false;
        }
        
        
    }

    

}
