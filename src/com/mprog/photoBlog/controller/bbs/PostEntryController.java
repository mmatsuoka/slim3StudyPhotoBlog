package com.mprog.photoBlog.controller.bbs;

import java.util.Date;
import java.util.logging.Logger;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.controller.upload.FileItem;
import org.slim3.util.BeanUtil;
import org.slim3.util.StringUtil;

import com.google.appengine.api.users.User;
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

        User user = sessionScope(SessionKeys.AUTH_USER);
        Head head = new Head();
        Photo photo = new Photo();

        BeanUtil.copy(request, head);
        BeanUtil.copy(request, photo);
        head.setPostDate(new Date());
        head.setUser(user);

        FileItem fi = requestScope(RequestKeys.PHOTO);

        photo = new Photo();
        photo.setPhotoImage(fi.getData());

                BlogService service = new BlogService();
        service.insert(head, photo);

        return redirect(basePath);

        // return forward("postEntry.jsp");
    }

    private boolean validate() {
        PhotoBlogValidators v = new PhotoBlogValidators(request);
        v.add(RequestKeys.TITLE, v.required(), v.maxlength(50));
        v.add(RequestKeys.USERNAME, v.required(), v.maxlength(50));

        FileItem fi = requestScope(RequestKeys.PHOTO);

        if (!ContentTypeUtil.isImage(fi)) {
            v.add(RequestKeys.PHOTO, v.isImageFile(null) );
        }

        if (!StringUtil.isEmpty(param(RequestKeys.PASSWORD))) {
            v.add(RequestKeys.PASSWORD, v.minlength(6), v.maxlength(20));
        }


        return v.validate();
    }

}
