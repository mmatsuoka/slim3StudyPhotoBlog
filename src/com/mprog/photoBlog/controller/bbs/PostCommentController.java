package com.mprog.photoBlog.controller.bbs;

import java.util.Date;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.util.ApplicationMessage;
import org.slim3.util.BeanUtil;

import com.mprog.photoBlog.model.Comment;
import com.mprog.photoBlog.model.Head;
import com.mprog.photoBlog.service.BlogService;

public class PostCommentController extends Controller {

    @Override
    public Navigation run() throws Exception {
        
        if (!isPost()) {
            // POSTではないリクエストはトップページへリダイレクト
            return redirect(basePath);
        }
        
        // 入力値のバリデーション
        if (!validate()) {
            // バリデーションエラーの場合は記事詳細ページを表示
            return forward("read");
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
        
        // コメントを作成
        Comment comment = new Comment();
        BeanUtil.copy(request, comment);
        comment.setPostDate(new Date());
        
        // 記事とコメントを登録
        service.insert(head, comment);
        
        // 記事詳細にリダイレクト（GETパラメータで記事の主キーを指定）
        return redirect(basePath + "read?key="+asString("key"));
    }

    private boolean validate() {
        
        Validators v = new Validators(request);
        v.add("username", v.required(),v.maxlength(50));
        v.add("comment", v.required());
        return v.validate();
    }

}
