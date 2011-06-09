package com.mprog.photoBlog.controller.bbs;

import java.util.Date;
import java.util.List;

import org.slim3.datastore.Datastore;
import org.slim3.tester.ControllerTestCase;
import org.junit.Test;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.images.Image;
import com.mprog.photoBlog.model.Comment;
import com.mprog.photoBlog.model.Head;
import com.mprog.photoBlog.model.Photo;
import com.mprog.photoBlog.service.BlogService;
import com.mprog.photoBlog.utils.ImageUtil;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class PostCommentControllerTest extends ControllerTestCase {

    // バリデーションOKな入力値
    private static final String USERNAME = "コメント投稿者名です";
    private static final String COMMENT = "コメント本文です。";
    
    // 正しいパスワード
    private static final String PASSWORD = "password";
    
    private BlogService service = new BlogService();
    private String keyString = null;
    
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

    // 記事詳細ページでコメントを投稿した動作をエミュレート
    private PostCommentController postComment(String username, String comment) throws Exception {
        tester.param("key", keyString);
        tester.param("username", username);
        tester.param("comment", comment);
        tester.request.setMethod("POST");
        tester.start("/bbs/postComment");
        return tester.getController();
    }
    // バリデーションOKな入力値であること
    private void assertValidParameters() {
        // 記事を再取得
        Head storedHead = service.get(Datastore.stringToKey(keyString));
        
        // 記事のコメントリストを取得
        List<Comment> comments = storedHead.getCommentRef().getModelList();
        
        // 1件のコメントが登録されていること
        assertThat(comments.size(), is(1));
        
        // エラーメッセージが１つもセットされていないこと
        assertThat(tester.getErrors().isEmpty(), is(true));
        
        // 記事詳細ページにリダイレクトしていること
        assertThat(tester.getDestinationPath(), is("/bbs/read?key="+keyString));
        assertThat(tester.isRedirect(), is(true));
    }
    // バリデーションNGな入力値であること
    private void assertInvalidParameters() {
        // 記事を再取得
        Head storedHead = service.get(Datastore.stringToKey(keyString));
        
        // 記事のコメントリストを取得
        List<Comment> comments = storedHead.getCommentRef().getModelList();
        
        // 記事が登録されていないこと（0件であること）
        assertThat(comments.size(), is(0));
        
        // エラーメッセージが何かしらセットされていること
        assertThat(tester.getErrors().isEmpty(), is(false));
        
        // リクエストスコープに記事の主キーが指定されていること
        assertThat(tester.asString("key"), is(keyString));
        
        // 記事詳細ページにフォワードしていること
        assertThat(tester.getDestinationPath(), is("/bbs/read"));
        assertThat(tester.isRedirect(), is(false));
    }

    @Test
    public void testValidParameters() throws Exception {
        
        // -----<< すべての入力値OKのテスト >>----- //
        PostCommentController controller =
            postComment(USERNAME, COMMENT);
        
        // ========== assertion start ========== //
        assertThat(controller, is(notNullValue()));
        assertValidParameters(); // 入力値OK
        // ========== assertion end ========== //
    }

    @Test
    public void testUsernameEmpty() throws Exception {
        
        // -----<< 投稿者名未入力で入力値NGのテスト >>----- //
        postComment("", COMMENT);
        
        // ========== assertion start ========== //
        assertInvalidParameters(); // 入力値NG
        // username のエラーメッセージがセットされていること
        assertThat(tester.getErrors().get("username"), is(notNullValue()));
        // ========== assertion end ========== //
    }
    
    @Test
    public void testUsernameLentgh51() throws Exception {
        
        // -----<< 投稿者名文字数51文字で入力値NGのテスト >>----- //
        String char51 =
            "０１２３４５６７８９" +
            "０１２３４５６７８９" +
            "０１２３４５６７８９" +
            "０１２３４５６７８９" +
            "０１２３４５６７８９" +
            "０";
        postComment(char51, COMMENT);
        
        // ========== assertion start ========== //
        assertInvalidParameters(); // 入力値NG
        // username のエラーメッセージがセットされていること
        assertThat(tester.getErrors().get("username"), is(notNullValue()));
        // ========== assertion end ========== //
    }
    
    @Test
    public void testUsernameLength50() throws Exception {
        
        // -----<< 投稿者名文字数50文字で入力値OKのテスト >>----- //
        String char50 =
            "０１２３４５６７８９" +
            "０１２３４５６７８９" +
            "０１２３４５６７８９" +
            "０１２３４５６７８９" +
            "０１２３４５６７８９";
        postComment(char50, COMMENT);
        
        // ========== assertion start ========== //
        assertValidParameters(); // 入力値OK
        // ========== assertion end ========== //
    }

    @Test
    public void testCommentEmpty() throws Exception {
        
        // -----<< コメント未入力で入力値NGのテスト >>----- //
        postComment(USERNAME, "");
        
        // ========== assertion start ========== //
        assertInvalidParameters(); // 入力値NG
        
        // comment のエラーメッセージがセットされていること
        assertThat(tester.getErrors().get("comment"), is(notNullValue()));
        // ========== assertion end ========== //
    }
    
    @Test
    public void testCommentLength100() throws Exception {
        
        // -----<< コメント文字数100文字で入力値OKのテスト >>----- //
        String char100 =
            "０１２３４５６７８９" + "０１２３４５６７８９" +
            "０１２３４５６７８９" + "０１２３４５６７８９" +
            "０１２３４５６７８９" + "０１２３４５６７８９" +
            "０１２３４５６７８９" + "０１２３４５６７８９" +
            "０１２３４５６７８９" + "０１２３４５６７８９";
        postComment(USERNAME, char100);
        
        // ========== assertion start ========== //
        assertValidParameters(); // 入力値OK
        // ========== assertion end ========== //
    }
    
    @Test
    public void testAfterDeleted() throws Exception {
        
        // -----<< コメント時に記事が存在しない場合のテスト >>----- //
        
        // 主キーの文字列をKeyオブジェクトに変換
        Key key = Datastore.stringToKey(keyString);
        
        // 該当の記事を削除しておく
        service.delete(key);
        
        // -----<< 有効な入力値でコメントを投稿 >>----- //
        postComment(USERNAME, COMMENT);
        
        // ========== assertion start ========== //
        
        // 記事が削除されてるので取得できないこと
        assertThat(service.get(key), is(nullValue()));
        
        // Errorsのキー"message"にエラーメッセージがセットされていること
        assertThat(tester.getErrors().get("message"), is(notNullValue()));
        
        // トップページにforwardしていること
        assertThat(tester.getDestinationPath(), is("/bbs/"));
        assertThat(tester.isRedirect(), is(false));
        
        // ========== assertion end ========== //
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

}
