package com.mprog.photoBlog.controller.bbs;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.images.CompositeTransform;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import com.mprog.photoBlog.model.Head;
import com.mprog.photoBlog.model.Photo;
import com.mprog.photoBlog.service.BlogService;

public class ImageController extends Controller {

    private BlogService service = new BlogService();

    private final int smallHeight = 300;
    private final int smallWidth = 200;

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

        String size = asString(RequestKeys.SIZE);

        //小さいサイズにリサイズ
        if("s".equals(size)){
           Transform t =  ImagesServiceFactory.makeResize(smallHeight, smallWidth);
           CompositeTransform ct = ImagesServiceFactory.makeCompositeTransform();
           ct.concatenate(t);
           Image image = ImagesServiceFactory.makeImage(p.getPhotoImage());
           Image resizeImage = ImagesServiceFactory.getImagesService().applyTransform(t, image);

           response.getOutputStream().write(resizeImage.getImageData());
        }

        response.getOutputStream().write(p.getPhotoImage());

        return null;
    }
}
