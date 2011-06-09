package com.mprog.photoBlog.controller.bbs;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.util.ApplicationMessage;
import org.slim3.util.BeanUtil;

import com.mprog.photoBlog.model.Head;
import com.mprog.photoBlog.service.BlogService;

public class EditController extends Controller {

    @Override
    public Navigation run() throws Exception {
        
        
        if (!isPost() || !validate()) {
            // POSTではないリクエスト、またはバリデーションエラーの場合は記事詳細へ戻る
            return forward("read");
        }
        
        
        
        BlogService service = new BlogService();
        Head head = service.get(asKey("key"));
        if (head == null) {
            // 指定されたキーに該当する記事がない場合はトップへ戻る
            errors.put("message", ApplicationMessage.get("error.entry.notfound"));
            return forward(basePath);
        }
        
        if (!asString("password").equals(head.getPassword())) {
            // パスワードが不一致の場合は記事詳細へ戻る
            errors.put("password", ApplicationMessage.get("error.password.invalid"));
            return forward("read");
        }
        
        // パスワードが正しい場合はリクエストスコープにHead、Bodyのプロパティの値をセットする
        // Photo photo = head.getPhotoRef().getModel();
        BeanUtil.copy(head, request);
        
        
        // 写真を取得するためのキーをセット
        requestScope("key", head.getKey()); // 記事ヘッダ
        
        
        return forward("edit.jsp");
    }
    
    private boolean validate() {
        Validators v = new Validators(request);
        v.add("key", v.required());
        v.add("password", v.required());
        return v.validate();
    }
    
    
}
