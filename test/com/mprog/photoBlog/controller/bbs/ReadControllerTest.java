package com.mprog.photoBlog.controller.bbs;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.slim3.datastore.Datastore;
import org.slim3.tester.ControllerTestCase;

import com.google.appengine.api.images.Image;
import com.mprog.photoBlog.model.Head;
import com.mprog.photoBlog.model.Photo;
import com.mprog.photoBlog.service.BlogService;
import com.mprog.photoBlog.utils.ImageUtil;

public class ReadControllerTest extends ControllerTestCase {

    private BlogService service = new BlogService();
    private String keyString = null;
    
    @Override
    // 各テストの前に実行される処理
    public void setUp() throws Exception {
        super.setUp();
        
        // 各テストの実行前に記事を１件登録しておく
        insertEntry("テスト用の記事", "テストユーザ", new Date(), "テスト用の記事の本文です。");
        
        // 登録した１件の記事を取得する
        Head head = service.getAll().get(0);
        
        // 記事のKeyを文字列に変換して保持
        keyString = Datastore.keyToString(head.getKey());
    }

    // 記事を新規登録する
    private void insertEntry(String title, String username, Date postDate, String text) throws Exception {
        // 記事の作成
        Head head = new Head();
        head.setTitle(title);
        head.setUsername(username);
        head.setPostDate(postDate);
        
        
        // 写真の作成
        Image readImage = ImageUtil.readImage("./test/image/test1.jpg");
        Photo photo = new Photo();
        photo.setPhotoImage(readImage.getImageData());
        
        // データストアへ登録
        service.insert(head, photo);
    }
    
    @Test
    public void testValidParameter() throws Exception {
        
        // 記事一覧のタイトルリンクがクリックされた動作をエミュレート
        tester.param("key", keyString);
        tester.start("/bbs/read");
        
        ReadController controller = tester.getController();
        
        // 記事詳細ページを表示
        // ========== assertion start ========== //
        assertThat(controller, is(notNullValue()));
        
        // requestScopeに記事情報がセットされていること
        assertThat(tester.requestScope("head"), is(notNullValue()));
        assertThat(tester.requestScope("photo"), is(notNullValue()));
        assertThat(tester.requestScope("commentList"), is(notNullValue()));
        
        // エラーメッセージは空であること
        assertThat(tester.getErrors().isEmpty(), is(true));
        
        // read.jspにフォワードしていること
        assertThat(tester.getDestinationPath(), is("/bbs/read.jsp"));
        assertThat(tester.isRedirect(), is(false));
        // ========== assertion end ========== //
    }
    @Test
    public void testAfterDeleted() throws Exception {
        
        // 該当の記事を予め削除
        service.delete(Datastore.stringToKey(keyString));
        
        // 記事一覧のタイトルリンクがクリックされた動作をエミュレート
        tester.param("key", keyString);
        tester.start("/bbs/read");
        ReadController controller = tester.getController();
        
        // トップページへ戻る
        // ========== assertion start ========== //
        assertThat(controller, is(notNullValue()));
        
        // Errorsのキー"message"にエラーメッセージがセットされていること
        assertThat(tester.getErrors().get("message"), is(notNullValue()));
        
        // 記事一覧（/bbs/）にフォワードしていること
        assertThat(tester.getDestinationPath(), is("/bbs/"));
        assertThat(tester.isRedirect(), is(false));
        // ========== assertion end ========== //
    }
    @Test
    public void testInvalidParameter() throws Exception {
        
        // パラメータを不正な値に改ざんしてアクセスされた場合の動作をエミュレート
        tester.param("key", "aaaaaaaaaaa");
        tester.start("/bbs/read");
        ReadController controller = tester.getController();
        
        // トップページへ戻る
        // ========== assertion start ========== //
        assertThat(controller, is(notNullValue()));
        
        // Errorsのキー"message"にエラーメッセージがセットされていること
        assertThat(tester.getErrors().get("message"), is(notNullValue()));
        
        // 記事一覧（/bbs/）にフォワードしていること
        assertThat(tester.getDestinationPath(), is("/bbs/"));
        assertThat(tester.isRedirect(), is(false));
        
        // ========== assertion end ========== //
    }
}
