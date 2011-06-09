package com.mprog.photoBlog.controller.bbs;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.util.ApplicationMessage;

import com.google.appengine.api.datastore.Key;
import com.mprog.photoBlog.model.Head;
import com.mprog.photoBlog.service.BlogService;

public class ReadController extends Controller {

    @Override
    public Navigation run() throws Exception {
        
        BlogService service = new BlogService();
        Head head = null;
        
        try {
            // 指定されたkeyから記事を取得
            Key key = asKey("key");
            head = service.get(key);
        }
        catch (Exception e) {
            // keyが不正な場合
        }
        
        // 記事が取得できなかった場合
        if (head == null) {
            
            // エラーメッセージをセットしてトップページへフォワード
            errors.put("message", ApplicationMessage.get("error.entry.notfound"));
            return forward(basePath);
        }
        
        // 記事が存在する場合はリクエストスコープへセット
        requestScope("head", head); // 記事ヘッダ
        requestScope("photo", head.getPhotoRef().getModel()); // 写真
        requestScope("commentList", head.getCommentRef().getModelList()); // コメント一覧
        return forward("read.jsp");
    }
}
