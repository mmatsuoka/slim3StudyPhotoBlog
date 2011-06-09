package com.mprog.photoBlog.controller.bbs;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.controller.upload.FileItem;
import org.slim3.controller.validator.Validators;
import org.slim3.util.ApplicationMessage;
import org.slim3.util.BeanUtil;

import com.mprog.photoBlog.model.Head;
import com.mprog.photoBlog.model.Photo;
import com.mprog.photoBlog.service.BlogService;
import com.mprog.photoBlog.utils.ContentTypeUtil;

public class UpdateEntryController extends Controller {

    @Override
    public Navigation run() throws Exception {
        if (!isPost()) {
            // POSTではないリクエストはトップページへリダイレクト
            return redirect(basePath);
        }
        // 入力値のバリデーション
        if (!validate()) {
            // バリデーションエラーの場合は記事詳細ページを表示
            return forward("edit.jsp");
        }
        
        BlogService service = new BlogService();
        Head head = null;
        try {
            // 指定されたkeyから記事を取得
            head = service.get(asKey("key"));
        }
        catch (Exception e) {
            // keyが不正な場合
        }
        
        // 記事が取得できなかった場合
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
        
        // リクエストの値をHead、Photoのプロパティにセットする
        BeanUtil.copy(request, head);
        
        Photo photo = head.getPhotoRef().getModel();
        FileItem fi = requestScope("photo");
        if(fi != null){
            photo.setPhotoImage(fi.getData());
        }
        
        
        // 上書き更新
        service.update(head, photo);
        
        // 記事詳細にリダイレクト（GETパラメータで記事の主キーを指定）
        return redirect(basePath + "read?key="+asString("key"));
    }

    private boolean validate() {
        Validators v = new Validators(request);
        v.add("title", v.required(),v.maxlength(50));
        v.add("username", v.required(),v.maxlength(50));
        
        FileItem fi = requestScope("photo");
        if(fi == null){
            // 写真は更新しない。
        }else{
        
            if (!ContentTypeUtil.isImage(fi)) {
                v.add("photo", v.required());
            }
        }
        
        
        return v.validate();
    }
}
