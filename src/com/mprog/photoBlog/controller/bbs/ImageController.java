package com.mprog.photoBlog.controller.bbs;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.google.appengine.api.datastore.Key;
import com.mprog.photoBlog.model.Head;
import com.mprog.photoBlog.model.Photo;
import com.mprog.photoBlog.service.BlogService;

public class ImageController extends Controller {

    private BlogService service = new BlogService();

    @Override
    public Navigation run() throws Exception {

        Head head = null;

        try {
            // 指定されたkeyから記事を取得
            Key key = asKey(RequestKeys.KEY);
            head = service.get(key);
        }
        catch (Exception e) {
            // keyが不正な場合

            return null;
        }

        Photo p =  head.getPhotoRef().getModel();
//        requestScope("photo", head.getPhotoRef().getModel()); // 写真

        response.getOutputStream().write(p.getPhotoImage());

        return null;
    }
}
