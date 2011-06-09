package com.mprog.photoBlog.controller.bbs;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.util.ApplicationMessage;

import com.mprog.photoBlog.model.Head;
import com.mprog.photoBlog.service.BlogService;

public class DeleteEntryController extends Controller {

    @Override
    public Navigation run() throws Exception {
        if (!isPost()) {
            // POSTではないリクエストはトップページへリダイレクト
            return redirect(basePath);
        }
        
        BlogService service = new BlogService();
        Head head = null;
        
        try {
            // 指定されたkeyから記事を取得
            head = service.get(asKey("key"));
        } catch (Exception e) {
            // keyが不正な場合
        }
        
        // 記事が取得できなかった場合
        if (head == null) {
            // 指定されたキーに該当する記事がない場合はトップへ戻る
            errors.put(
                "message",
                ApplicationMessage.get("error.entry.notfound"));
            return forward(basePath);
        }
        
        if (!asString("password").equals(head.getPassword())) {
            // パスワードが不一致の場合は記事詳細へ戻る
            errors.put(
                "password",
                ApplicationMessage.get("error.password.invalid"));
            return forward("read");
        }
        
        // 記事削除
        service.delete(head.getKey());
        
        // トップページへリダイレクト
        return redirect(basePath);

    }
}
