package com.mprog.photoBlog.validator;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slim3.controller.validator.Validators;

public class PhotoBlogValidators extends Validators {

    public PhotoBlogValidators(Map<String, Object> parameters)
            throws NullPointerException, IllegalStateException {
        super(parameters);
    }

    public PhotoBlogValidators(HttpServletRequest request) {
        super(request);
    }

    
    public ImageFileValidator isImageFile() {
        return new ImageFileValidator();
    }
    
    public ImageFileValidator isImageFile( String message) {
        return new ImageFileValidator(message);
    }
    

}
