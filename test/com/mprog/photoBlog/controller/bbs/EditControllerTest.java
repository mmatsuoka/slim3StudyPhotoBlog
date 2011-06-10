package com.mprog.photoBlog.controller.bbs;

import java.util.Date;

import org.slim3.datastore.Datastore;
import org.slim3.tester.ControllerTestCase;
import org.junit.Test;

import com.google.appengine.api.images.Image;
import com.mprog.photoBlog.model.Head;
import com.mprog.photoBlog.model.Photo;
import com.mprog.photoBlog.service.BlogService;
import com.mprog.photoBlog.utils.ImageUtil;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class EditControllerTest extends ControllerTestCase {

    private BlogService service = new BlogService();
    private String keyString = null;
    
    // 正しいパスワード
    private static final String PASSWORD = RequestKeys.PASSWORD;
    
    @Override
    // 各テストの前に実行される処理
    public void setUp() throws Exception {
        
        super.setUp();
        
        // 各テストの実行前に記事を１件登録しておく
        insertEntry("テスト用の記事", "テストユーザ", new Date(), "./test/image/test1.jpg", PASSWORD);
        
        // 登録した１件の記事を取得する
        Head head = service.getAll().get(0);
        
        // 記事のKeyを文字列に変換して保持
        keyString = Datastore.keyToString(head.getKey());
    }

    // 記事を新規登録する
    private void insertEntry(
            String title,
            String username,
            Date postDate,
            String imagePath,
            String password) throws Exception {
        
        // 記事の作成
        Head head = new Head();
        head.setTitle(title);
        head.setUsername(username);
        head.setPostDate(postDate);
        head.setPassword(password);
        
        Image readImage = ImageUtil.readImage("./test/image/test1.jpg");
        Photo photo = new Photo();
        photo.setPhotoImage(readImage.getImageData());
        
        // データストアへ登録
        service.insert(head, photo);
    }

    @Test
    public void testValidParameter() throws Exception {
        
        // 記事詳細ページから正しいパスワードが入力されてきた動作をエミュレート
        tester.param("key", keyString);
        tester.param(RequestKeys.PASSWORD, PASSWORD);
        tester.request.setMethod("POST");
        tester.start("/bbs/edit");
        EditController controller = tester.getController();
        
        // 記事編集ページの表示
        // ========== assertion start ========== //
        assertThat(controller, is(notNullValue()));
        // requestScopeに記事情報がセットされていること
        assertThat(tester.requestScope("key"), is(notNullValue()));
        assertThat(tester.requestScope(RequestKeys.PASSWORD), is(notNullValue()));
        assertThat(tester.requestScope("title"), is(notNullValue()));
        assertThat(tester.requestScope("username"), is(notNullValue()));
        
        // エラーメッセージは空であること
        assertThat(tester.getErrors().isEmpty(), is(true));
        
        // 記事編集のjspへフォワードしていること
        assertThat(tester.getDestinationPath(), is("/bbs/edit.jsp"));
        assertThat(tester.isRedirect(), is(false));
        
        // ========== assertion end ========== //
    }

    @Test
    public void testInvalidParameter() throws Exception {
        
        // 記事詳細ページから不正なパスワードが入力されてきた動作をエミュレート
        tester.param("key", keyString);
        tester.param(RequestKeys.PASSWORD, "hogehoge");
        tester.request.setMethod("POST");
        tester.start("/bbs/edit");
        
        EditController controller = tester.getController();
        
        // 記事詳細ページへ戻る
        // ========== assertion start ========== //
        assertThat(controller, is(notNullValue()));
        
        // requestScopeに記事の主キーが指定されていること
        assertThat(tester.requestScope("key"), is(notNullValue()));
        
        // パスワードのエラーメッセージがセットされていること
        assertThat(tester.getErrors().get(RequestKeys.PASSWORD), is(notNullValue()));
        
        // 記事詳細ページへフォワードしていること
        assertThat(tester.getDestinationPath(), is("/bbs/read"));
        assertThat(tester.isRedirect(), is(false));
        
        // ========== assertion end ========== //
    }

    @Test
    public void testAfterDeleted() throws Exception {
        
        // 該当の記事を予め削除
        service.delete(Datastore.stringToKey(keyString));
        
        // 記事詳細ページから正しいパスワードが入力されてきた動作をエミュレート
        tester.param("key", keyString);
        tester.param(RequestKeys.PASSWORD, PASSWORD);
        tester.request.setMethod("POST");
        tester.start("/bbs/edit");
        EditController controller = tester.getController();
        
        // トップページへ戻る
        // ========== assertion start ========== //
        assertThat(controller, is(notNullValue()));
        
        // Errorsのキー"message"にエラーメッセージがセットされていること
        assertThat(tester.getErrors().get("message"), is(notNullValue()));
        
        // トップページへフォワードしていること
        assertThat(tester.getDestinationPath(), is("/bbs/"));
        assertThat(tester.isRedirect(), is(false));
        
        // ========== assertion end ========== //
    }
}
