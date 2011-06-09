package com.mprog.photoBlog.controller.bbs;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;

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

public class ImageControllerTest extends ControllerTestCase {
    
    private BlogService service = new BlogService();
    private String keyString = null;
    
    @Override
    // 各テストの前に実行される処理
    public void setUp() throws Exception {
        super.setUp();
        
        // 各テストの実行前に記事を１件登録しておく
        insertEntry("テスト用の記事", "テストユーザ", new Date());
        
        // 登録した１件の記事を取得する
        Head head = service.getAll().get(0);
        
        // 記事のKeyを文字列に変換して保持
        keyString = Datastore.keyToString(head.getKey());
    }
    
    
    @Test
    public void getImage() throws Exception, IllegalArgumentException, IOException, ServletException{
        
        tester.param("key", keyString);
        tester.start("/bbs/image");
        ImageController controller = tester.getController();
        
        assertThat(controller, is(notNullValue()));        
        
    }
    

    @Test
    public void run() throws Exception {
        tester.start("/bbs/image");
        ImageController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
    }
    
    // 記事を新規登録する
    private void insertEntry(String title, String username, Date postDate) throws Exception {
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
}
