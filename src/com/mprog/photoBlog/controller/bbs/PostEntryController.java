package com.mprog.photoBlog.controller.bbs;

import java.util.Date;
import java.util.logging.Logger;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.controller.upload.FileItem;
import org.slim3.controller.validator.Validators;
import org.slim3.util.ApplicationMessage;
import org.slim3.util.BeanUtil;
import org.slim3.util.StringUtil;

import com.mprog.photoBlog.model.Head;
import com.mprog.photoBlog.model.Photo;
import com.mprog.photoBlog.service.BlogService;
import com.mprog.photoBlog.utils.ContentTypeUtil;
import com.mprog.photoBlog.validator.PhotoBlogValidators;

public class PostEntryController extends Controller {
    
    private final static Logger log = Logger.getLogger(PostEntryController.class.getName());
    
    static final int maxSize =  1 * 1024 * 1000;

    @Override
    public Navigation run() throws Exception {

        if (!isPost() || !validate()) {
            return forward("create");
        }
        Head head = new Head();
        Photo photo = new Photo();

        BeanUtil.copy(request, head);
        BeanUtil.copy(request, photo);
        head.setPostDate(new Date());

        FileItem fi = requestScope("photo");
        
        
//        if(fi != null && fi.getData() != null){
//            log.info("file-size="+ fi.getData().length);
//            log.info("file-contentType="+ fi.getData().length);
//            if( fi.getData().length  > 1024*1000){
//                errors.put("message", ApplicationMessage.get("error.photo.maxFileSize"));
//                return forward("create");
//            }
//            
//        }
        
        photo = new Photo();
        photo.setPhotoImage(fi.getData());
        
                BlogService service = new BlogService();
        service.insert(head, photo);

        return redirect(basePath);

        // return forward("postEntry.jsp");
    }

    private boolean validate() {
        PhotoBlogValidators v = new PhotoBlogValidators(request);
        v.add("title", v.required(), v.maxlength(50));
        v.add("username", v.required(), v.maxlength(50));

        FileItem fi = requestScope("photo");

        if (!ContentTypeUtil.isImage(fi)) {
            v.add("photo", v.isImageFile(null) );
        }

        if (!StringUtil.isEmpty(param("password"))) {
            v.add("password", v.minlength(6), v.maxlength(20));
        }
        
        
        return v.validate();
    }

}
