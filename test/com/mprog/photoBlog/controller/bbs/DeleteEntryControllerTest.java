package com.mprog.photoBlog.controller.bbs;

import java.util.Date;
import java.util.List;

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

public class DeleteEntryControllerTest extends ControllerTestCase {
    
    private BlogService service = new BlogService();
    private String keyString = null;
    
    // 正しいパスワード
    private static final String PASSWORD = "password";
    
    
    @Override
    // 各テストの前に実行される処理
    public void setUp() throws Exception {
        
        super.setUp();
        
        // 登録されているデータを全て削除
        List<Head> heads = service.getAll();
        if(heads != null && heads.size() != 0){
            
            for(Head h : heads){
                service.delete(h.getKey());
            }
            
        }
        
        // 各テストの実行前に記事を１件登録しておく
        insertEntry("テスト用の記事", "テストユーザ", new Date(), "./test/image/test1.jpg", PASSWORD);
        
        // 登録した１件の記事を取得する
        Head head = service.getAll().get(0);
     
        
        // 記事のKeyを文字列に変換して保持
        keyString = Datastore.keyToString(head.getKey());
    }
    
    @Test
    public void testValidParameter() throws Exception {
        
        // 記事編集で削除ボタンが押下された動作をエミュレート
        tester.param("key", keyString);
        tester.param("password", "password");
        tester.request.setMethod("POST");
        tester.start("/bbs/deleteEntry");
        
        DeleteEntryController controller = tester.getController();
        
        // ========== assertion start ========== //
        assertThat(controller, is(notNullValue()));
        
        // 記事は０件であること
        assertThat(service.getAll().size(), is(0));
        
        // エラーメッセージは空であること
        assertThat(tester.getErrors().isEmpty(), is(true));
        
        // トップページにリダイレクトしていること
        assertThat(tester.getDestinationPath(), is("/bbs/"));
        assertThat(tester.isRedirect(), is(true));
        
        // ========== assertion end ========== //
    }
    @Test
    public void testAfterDeleted() throws Exception {
        
        // 該当の記事を削除しておく
        service.delete(Datastore.stringToKey(keyString));
        
        // 記事編集で削除ボタンが押下された動作をエミュレート
        tester.param("key", keyString);
        tester.param("password", "password");
        tester.request.setMethod("POST");
        tester.start("/bbs/deleteEntry");
        DeleteEntryController controller = tester.getController();
        
        // ========== assertion start ========== //
        assertThat(controller, is(notNullValue()));
        
        // Errorsのキー"message"にエラーメッセージがセットされていること
        assertThat(tester.getErrors().get("message"), is(notNullValue()));
        
        // 記事一覧（/bbs/）にフォワードしていること
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
