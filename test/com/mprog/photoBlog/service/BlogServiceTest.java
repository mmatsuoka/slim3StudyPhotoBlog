package com.mprog.photoBlog.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.slim3.tester.AppEngineTestCase;
import org.slim3.util.DateUtil;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.mprog.photoBlog.model.Comment;
import com.mprog.photoBlog.model.Head;
import com.mprog.photoBlog.model.Photo;
import com.mprog.photoBlog.utils.ImageUtil;

public class BlogServiceTest extends AppEngineTestCase {

    private BlogService service = new BlogService();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }

    @Test
    public void getAll() throws Exception {

        // データ一覧の取得
        List<Head> headList = service.getAll();

        // 初回は0件であること
        assertNotNull(headList);
        assertTrue(headList.isEmpty());

        // データの作成
        Head head = new Head();
        head.setTitle("初めての写真");
        head.setUsername("ユーザ１");
        head.setPostDate(new Date());

        // 写真の読み込み
        Photo photo = new Photo();
        File f = new File("./test/image/test1.jpg");
        assertTrue(f.isFile());

        Image readImage = ImageUtil.readImage("./test/image/test1.jpg");

        photo.setPhotoImage(readImage.getImageData());
        // データストアへ登録
        service.insert(head, photo);

        // 更新後のデータ一覧の取得
        headList = service.getAll();

        // 投稿後のデータ一覧は1件であること
        assertNotNull(headList);
        assertTrue(headList.size() == 1);

        // 以降、1件の中身のチェック
        Head storedHead = headList.get(0);
        assertNotNull(storedHead);

        // データタイトル、ユーザ名、投稿日時が正しく保存されているか
        assertEquals(head.getTitle(), storedHead.getTitle());
        assertEquals(head.getUsername(), storedHead.getUsername());
        assertEquals(head.getPostDate(), storedHead.getPostDate());

        // HeadからリレーションシップでPhotoを取得できるか
        Photo storedPhoto = storedHead.getPhotoRef().getModel();
        assertNotNull(storedPhoto);

        // Photoが正しく保存されているか
        Image actual =
            ImagesServiceFactory.makeImage(storedPhoto.getPhotoImage());
        assertEquals(readImage, actual);
    }

    public void crudTest() throws Exception {

        /* データの全件取得 */

        // データ一覧の取得
        List<Head> headList = service.getAll();

        // 初回は0件であること
        assertNotNull(headList);
        assertTrue(headList.isEmpty());

        /* データの新規登録 */

        // データの作成
        Head head = new Head();
        head.setTitle("初めての写真");
        head.setUsername("ユーザ１");
        head.setPostDate(new Date());

        // 写真の読み込み
        Photo photo = new Photo();
        File f = new File("./test/image/test1.jpg");
        assertTrue(f.isFile());

        Image readImage = ImageUtil.readImage("./test/image/test1.jpg");

        photo.setPhotoImage(readImage.getImageData());

        // データストアを更新
        service.insert(head, photo);

        // 更新後のデータ一覧の取得
        headList = service.getAll();

        // 投稿後のデータ一覧は1件であること
        assertNotNull(headList);
        assertTrue(headList.size() == 1);

        // 以降、1件の中身のチェック
        Head storedHead = headList.get(0);

        // 新規作成したHeadと、登録後再取得したHeadが等しいこと
        assertEqualsHead(head, storedHead);

        // 登録後のバージョンが1であること
        assertEquals(storedHead.getVersion(), Long.valueOf(1L));

        
        /* データを1件取得 */

        // データ一覧から一つのデータが選択された想定で再取得
        storedHead = service.get(storedHead.getKey());

        // 指定されたKeyのデータが取得できていること
        assertNotNull(storedHead);

        // 先に登録したHeadであること
        assertEqualsHead(head, storedHead);

        // バージョンが1であること
        assertEquals(storedHead.getVersion(), Long.valueOf(1L));

        
        /* データの上書き更新 */
        
        Image updateImage = ImageUtil.readImage("./test/image/updatetest1.jpg");

        storedHead.setTitle("修正したデータ");
        Photo storedPhoto = storedHead.getPhotoRef().getModel();
        storedPhoto.setPhotoImage(updateImage.getImageData());

        // データストアへ上書き更新
        service.update(storedHead, storedPhoto);
        
        // 更新後のデータ一覧の取得
        headList = service.getAll();

        // 投稿後のデータ一覧は1件であること
        assertNotNull(headList);
        assertTrue(headList.size() == 1);
        
        // 以降、1件の中身のチェック
        Head updatedHead = headList.get(0);
        
        // 修正したHeadと、更新後再取得したHeadが等しいこと
        assertEqualsHead(storedHead, updatedHead);
        
        // 上書き更新したのでバージョンが2になっていること
        assertEquals(updatedHead.getVersion(), Long.valueOf(2L));
        
    
        
        /* データの削除 */ 
        
        // データの削除 
        service.delete(updatedHead.getKey());
        
        // 削除後のデータの取得 
        storedHead = service.get(updatedHead.getKey());
        
        // 削除後のデータ一覧の取得 
        headList   = service.getAll();

        // このデータは削除されているのでNullであること 
        assertNull(storedHead);
        
        // データ一覧は０件のリストであること 
        assertNotNull(headList);
        assertTrue(headList.isEmpty());
        

    }
    
 // 記事一覧のソート順テスト（投稿日時降順） 
    @Test
    public void headListSortOrderTest() throws Exception {
        
        Image readImage = ImageUtil.readImage("./test/image/test1.jpg");
        
        // 投稿日付をランダムに並べ記事を順次登録 
        insertHead("題名", "ユーザ", toDate("2010/10/18 15:45:00"), readImage.getImageData());
        insertHead("題名", "ユーザ", toDate("2011/01/01 00:00:00"), readImage.getImageData());
        insertHead("題名", "ユーザ", toDate("2011/01/01 12:34:56"), readImage.getImageData());
        insertHead("題名", "ユーザ", toDate("2011/05/05 10:30:30"), readImage.getImageData());
        insertHead("題名", "ユーザ", toDate("2010/10/22 15:45:45"), readImage.getImageData());
        insertHead("題名", "ユーザ", toDate("2012/01/01 23:59:10"), readImage.getImageData());
        insertHead("題名", "ユーザ", toDate("2011/05/05 11:30:30"), readImage.getImageData());
        insertHead("題名", "ユーザ", toDate("2010/10/20 15:45:00"), readImage.getImageData());
        insertHead("題名", "ユーザ", toDate("2010/10/22 15:45:00"), readImage.getImageData());
        
        // 一覧の取得 
        List<Head> headList = service.getAll();
        assertNotNull(headList);
        assertTrue(headList.size() == 9);
        
        // 投稿日付の降順に並んでいるかチェック 
        Head preHead = null;
        for (Head head : headList) {
            if (preHead == null) {
                preHead = head;
                continue;
            }
            // 一件前の日付より古い投稿日付であること 
            assertTrue(head.getPostDate().before(preHead.getPostDate()));
            preHead = head;
        }
    }
    
    
    // コメント一覧のソート順テスト（コメントID順） 
    @Test
    public void commentListSortOrderTest() throws Exception {
        
        Image readImage = ImageUtil.readImage("./test/image/test1.jpg");
        
        // データの登録 
        insertHead("題名", "ユーザ", new Date(), readImage.getImageData());
        
        // 一覧の取得 
        List<Head> headList = service.getAll();
        assertNotNull(headList);
        assertTrue(headList.size() == 1);
        
        // データの取得 
        Head head = headList.get(0);
        
        // このデータにコメントを1000件投稿してみる 
        int max = 1000;
        for (int i = 0; i < max; i++) {
            Comment newComment = createComment("ユーザ", new Date(), "コメント");
            service.insert(head, newComment);
        }
        
        // コメント投稿されたデータを再取得 
        head = service.get(head.getKey());
        
        // コメント一覧をリレーションシップで取得 
        List<Comment> commentList = head.getCommentRef().getModelList();
        assertNotNull(commentList);
        assertTrue(commentList.size() == max);
        
        // コメントID昇順に並んでいるかチェック 
        Comment preComment = null;
        for (Comment comment : commentList) {
            if (preComment == null) {
                preComment = comment;
                continue;
            }
            // 一件前のコメントIDより＋１大きいIDであること 
            assertEquals(comment.getKey().getId(), preComment.getKey().getId() + 1L);
            preComment = comment;
        }
        
        // データのコメント数が1000であること 
        assertEquals(head.getLastCommentId(), Long.valueOf(max));
        
        // データの最終コメント日付が最後のコメントの日付と同じであること 
        assertEquals(head.getLastCommentDate(), preComment.getPostDate());
    }
    
    
    
    // データを新規登録する 
    private void insertHead(String title, String username, Date postDate, byte [] photoImage) throws Exception {
        
        Head head = new Head();
        head.setTitle(title);
        head.setUsername(username);
        head.setPostDate(postDate);

        // 本文の作成 
        Photo photo = new Photo();
        photo.setPhotoImage(photoImage);
        
        // データストアへ更新 
        service.insert(head, photo);
    }
    
    
    private Date toDate(String yyyyMMddHHmmss) {
        return DateUtil.toDate(yyyyMMddHHmmss, "yyyy/MM/dd HH:mm:ss");
    }
    

    private void assertEqualsHead(Head head1, Head head2) {

        // 共にNullではないこと
        assertNotNull(head1);
        assertNotNull(head2);

        // データタイトル、ユーザ名、投稿日時が等しいこと
        assertEquals(head1.getTitle(), head2.getTitle());
        assertEquals(head1.getUsername(), head2.getUsername());
        assertEquals(head1.getPostDate(), head2.getPostDate());

        // 共にリレーションシップからPhotoが取得できること
        Photo photo1 = head1.getPhotoRef().getModel();
        Photo photo2 = head2.getPhotoRef().getModel();

        assertNotNull(photo1);
        assertNotNull(photo2);

        // 本文が等しいこと
        assertEquals(
            ImagesServiceFactory.makeImage(photo1.getPhotoImage()),
            ImagesServiceFactory.makeImage(photo2.getPhotoImage()));
    }
    
    @Test
    public void postCommentTest() throws Exception {
        
        // データの作成 
        Head head = new Head();
        head.setTitle("初めてのデータ");
        head.setUsername("ユーザ１");
        head.setPostDate(new Date());
        
        // 写真の設定 
        Image photoImage = ImageUtil.readImage("./test/image/updatetest1.jpg");
        Photo photo = new Photo();
        photo.setPhotoImage(photoImage.getImageData());
        
        // データストアへ更新 
        service.insert(head, photo);
        
        // データ一覧の取得 
        List<Head> headList = service.getAll();
        
        // 投稿後のデータ一覧は1件であること 
        assertNotNull(headList);
        assertTrue(headList.size() == 1);
        
        // データを取得 
        Head storedHead = headList.get(0);
        
        // このデータのコメント一覧を取得 
        List<Comment> commentList = storedHead.getCommentRef().getModelList();

        // コメント投稿前なので０件であること 
        assertNotNull(commentList);
        assertTrue(commentList.isEmpty());
        
        // コメントの作成 
        Comment comment =
            createComment("ユーザ２", new Date(), "コメントの投稿です。");
        
        // データストアへ更新 
        service.insert(storedHead, comment);
        
        // データの再取得 
        storedHead = service.get(storedHead.getKey());
        
        // コメント一覧の再取得 
        commentList = storedHead.getCommentRef().getModelList();
        
        // １件のコメントが投稿されていること 
        assertNotNull(commentList);
        assertTrue(commentList.size() == 1);
        
        // 以降、1件の中身のチェック 
        Comment postedComment = commentList.get(0);
        assertEquals(comment.getUsername(), postedComment.getUsername());
        assertEquals(comment.getComment(), postedComment.getComment());
        assertEquals(comment.getPostDate(), postedComment.getPostDate());
        
        // 主キーであるコメントIDが１であること 
        assertEquals(postedComment.getKey().getId(), 1L);
     
        // コメント件数が１であること 
        assertTrue(storedHead.getLastCommentId() == 1L);
        
        // 最終コメント日時がコメント投稿日時と同じであること 
        assertEquals(storedHead.getLastCommentDate(), comment.getPostDate());
        
    }
    
    private Comment createComment(String username, Date postDate, String text) throws Exception {
        // コメントの作成 
        Comment comment = new Comment();
        comment.setUsername(username);
        comment.setComment(text);
        comment.setPostDate(postDate);
        return comment;
    }

}
