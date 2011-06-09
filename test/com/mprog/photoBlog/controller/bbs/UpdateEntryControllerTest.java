package com.mprog.photoBlog.controller.bbs;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.slim3.controller.upload.FileItem;
import org.slim3.datastore.Datastore;
import org.slim3.tester.ControllerTestCase;
import org.datanucleus.util.StringUtils;
import org.hamcrest.core.IsNull;
import org.junit.Test;

import com.google.appengine.api.images.Image;
import com.mprog.photoBlog.model.Head;
import com.mprog.photoBlog.model.Photo;
import com.mprog.photoBlog.service.BlogService;
import com.mprog.photoBlog.utils.ImageUtil;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class UpdateEntryControllerTest extends ControllerTestCase {

    private BlogService service = new BlogService();
    private String keyString = null;
    private static final String PASSWORD = "password";

    // 初期登録の値
    private static final String BEFORE_TITLE = "タイトルです";
    private static final String BEFORE_USERNAME = "投稿者名です1";
    private static final String BEFORE_PHOTO = "./test/image/test1.jpg";
    
    // 更新後のバリデーションOKな入力値
    private static final String AFTER_TITLE = "更新したタイトルです";
    private static final String AFTER_USERNAME = "更新した投稿者名です";
    private static final String AFTER_PHOTO = "./test/image/updatetest1.jpg";
    
    @Override
    // 各テストの前に実行される処理
    public void setUp() throws Exception {
        

        super.setUp();
        
        // テスト前に記事を１件登録しておく
        insertEntry(BEFORE_TITLE, BEFORE_USERNAME, new Date(), BEFORE_PHOTO, PASSWORD);
        
        // 登録した１件の記事を取得する
        
        Head head = service.getAll().get(0);
        
        // この記事のKeyを文字列に変換して保持
        keyString = Datastore.keyToString(head.getKey());
    }

    // 記事を新規登録する
    private void insertEntry(
            String subject,
            String username,
            Date postDate,
            String imagePath,
            String password) throws Exception {
        
        // 記事の作成
        Head head = new Head();
        head.setTitle(subject);
        head.setUsername(username);
        head.setPostDate(postDate);
        head.setPassword(password);
        
        // 写真の作成
        Photo photo = new Photo();
        Image readImage = ImageUtil.readImage(imagePath);
        
        photo.setPhotoImage(readImage.getImageData());
        // データストアへ登録
        service.insert(head, photo);
    }

    // 記事編集ページで修正した記事をPOSTした動作をエミュレート
    private UpdateEntryController updateEntry(String title, String username, String imagePath ) throws Exception {
     
        tester.param("key", keyString);     // hidden 
        tester.param("password", PASSWORD); // hidden
        tester.param("title", title);
        tester.param("username", username);
        
        if(!StringUtils.isEmpty(imagePath) ){
        
            Image readImage = ImageUtil.readImage(imagePath);// "./test/image/test1.jpg" src以下の相対指定 
            FileItem photo = new FileItem("photo", "jpeg", readImage.getImageData());
            tester.requestScope("photo", photo);
        
        }
        
        tester.request.setMethod("POST");
        tester.start("/bbs/updateEntry");
        return tester.getController();
    }
    
    // バリデーションOKな入力値であること
    private void assertValidParameters(String title, String username, String imagePath) throws IOException {
        
        // 1件の記事が登録されていること
//        List<Head> lstHead = service.getAll();
//        assertThat(lstHead.size(), is(1));
        
        // １件の記事の内容が更新した内容であること
//        Head head = lstHead.get(0);
        Head head = service.get(Datastore.stringToKey(keyString));
        assertThat(head.getTitle(), is(title));
        assertThat(head.getUsername(), is(username));
        
        Image readImage = ImageUtil.readImage(imagePath);
        assertThat(head.getPhotoRef().getModel().getPhotoImage(), is(readImage.getImageData()));
        
        // エラーメッセージが１つもセットされていないこと
        assertThat(tester.getErrors().isEmpty(), is(true));
        
        // 記事詳細ページにリダイレクトしていること
        assertThat(tester.getDestinationPath(), is("/bbs/read?key="+keyString));
        assertThat(tester.isRedirect(), is(true));
    }
    
    // バリデーションNGな入力値であること
    private void assertInvalidParameters() throws IOException {
        
//        List<Head> lstHead = service.getAll();
//        assertThat(lstHead.size(), is(1));
        
        // １件の記事の内容が更新前の内容であること
//        Head head = lstHead.get(0);
        Head head = service.get(Datastore.stringToKey(keyString));
        assertThat(head.getTitle(), is(BEFORE_TITLE));
        assertThat(head.getUsername(), is(BEFORE_USERNAME));
        
        Image readImage = ImageUtil.readImage(BEFORE_PHOTO);
        assertThat(head.getPhotoRef().getModel().getPhotoImage(), is(readImage.getImageData()));
        
        // エラーメッセージが何かしらセットされていること
        assertThat(tester.getErrors().isEmpty(), is(false));
        
        // 記事編集JSPを表示していること
        assertThat(tester.getDestinationPath(), is("/bbs/edit.jsp"));
        assertThat(tester.isRedirect(), is(false));
    }

    @Test
    public void testValidParameters() throws Exception {
        
        // -----<< すべての入力値OKのテスト >>----- //
        updateEntry(AFTER_TITLE, AFTER_USERNAME, AFTER_PHOTO);
        
        // ========== assertion start ========== //
        assertValidParameters(AFTER_TITLE, AFTER_USERNAME, AFTER_PHOTO);
        
        // ========== assertion end ========== //
    }

    @Test
    public void testSubjectEmpty() throws Exception {
        
        // -----<< タイトル未入力で入力値NGのテスト >>----- //
        updateEntry("", AFTER_USERNAME, AFTER_PHOTO);
        
        // ========== assertion start ========== //
        assertInvalidParameters(); // 入力値NG
        
        // subject のエラーメッセージがセットされていること
        assertThat(tester.getErrors().get("title"), is(notNullValue()));
        
        // ========== assertion end ========== //
    }
    



    
    
    @Test
    public void testAfterDeleted() throws Exception {
        // 該当の記事を削除しておく
        service.delete(Datastore.stringToKey(keyString));
        
        // -----<< 有効な入力値で更新 >>----- //
        updateEntry(AFTER_TITLE, AFTER_USERNAME, AFTER_PHOTO);
        
        // ========== assertion start ========== //
        // 記事が削除されてるので０件であること
//        List<Head> lstHead = service.getAll();
//        assertThat(lstHead.size(), is(0));
        
        Head head = service.get(Datastore.stringToKey(keyString));
        assertThat(head, is(nullValue()));
        
        // Errorsのキー"message"にエラーメッセージがセットされていること
        assertThat(tester.getErrors().get("message"), is(notNullValue()));
        
        // トップページにforwardしていること
        assertThat(tester.getDestinationPath(), is("/bbs/"));
        assertThat(tester.isRedirect(), is(false));
        // ========== assertion end ========== //
    }
}
