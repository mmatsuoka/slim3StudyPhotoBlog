package com.mprog.photoBlog.controller.bbs;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.slim3.controller.upload.FileItem;
import org.slim3.tester.ControllerTestCase;

import com.google.appengine.api.images.Image;
import com.mprog.photoBlog.service.BlogService;
import com.mprog.photoBlog.utils.ImageUtil;

public class PostEntryControllerTest extends ControllerTestCase {

    // バリデーションOKな入力値
    private static final String SUBJECT = "タイトルです";
    private static final String USERNAME = "投稿者名です";
    private static FileItem PHOTO = makeFileItem();
    private static final String PASSWORD = RequestKeys.PASSWORD;

    public static FileItem makeFileItem() {

        Image readImage;
        try {
            readImage = ImageUtil.readImage("./test/image/test1.jpg");
        } catch (IOException e) {
            return null;
        }

        return new FileItem("photo", "jpeg", readImage.getImageData());

    }

    // Service
    private BlogService service = new BlogService();

    @Test
    public void testValidParameters() throws Exception {
        // -----<< すべての入力値OKのテスト（パスワードあり） >>----- //
        PostEntryController controller =
            postEntry(SUBJECT, USERNAME, PHOTO, PASSWORD);

        // ========== assertion start ========== //
        assertThat(controller, is(notNullValue()));
        assertValidParameters(); // 入力値OK
        
        // ========== assertion end ========== //
    }

    // 記事作成ページでPOSTした動作をエミュレート
    private PostEntryController postEntry(String subject, String username,
            FileItem photo, String password) throws Exception {
        tester.param("subject", subject);
        tester.param("username", username);
        
        tester.requestScope("photo", photo);
        
        tester.param(RequestKeys.PASSWORD, password);
        tester.request.setMethod("POST");
        tester.start("/bbs/postEntry");
        return tester.getController();

    }

    // バリデーションOKな入力値であること
    private void assertValidParameters() {
        
        // 1件の記事が登録されていること
        assertThat(service.getAll().size(), is(1));

        // エラーメッセージが１つもセットされていないこと
        assertThat(tester.getErrors().isEmpty(), is(true));

        // トップページにリダイレクトしていること
        assertThat(tester.getDestinationPath(), is("/bbs/"));
        assertThat(tester.isRedirect(), is(true));
    }

    // バリデーションNGな入力値であること
    private void assertInvalidParameters() {

        // 記事が登録されていないこと（0件であること）
        assertThat(service.getAll().size(), is(0));

        // エラーメッセージが何かしらセットされていること
        assertThat(tester.getErrors().isEmpty(), is(false));

        // 記事作成ページにforwardしていること
        assertThat(tester.getDestinationPath(), is("/bbs/create"));
        assertThat(tester.isRedirect(), is(false));
    }

    @Test
    public void testValidParametersWithoutPassword() throws Exception {
        // -----<< すべての入力値OKのテスト（パスワードなし） >>----- //
        postEntry(SUBJECT, USERNAME, PHOTO, "");
        // ========== assertion start ========== //
        assertValidParameters(); // 入力値OK
        // ========== assertion end ========== //
    }

    @Test
    public void testSubjectEmpty() throws Exception {
        // -----<< タイトル未入力で入力値NGのテスト >>----- //
        postEntry("", USERNAME, PHOTO, PASSWORD);
        
        // ========== assertion start ========== //
        assertInvalidParameters(); // 入力値NG
        
        // subject のエラーメッセージがセットされていること
        assertThat(tester.getErrors().get("subject"), is(notNullValue()));
        // ========== assertion end ========== //
    }

    @Test
    public void testSubjectLentgh51() throws Exception {
        // -----<< タイトル文字数51文字で入力値NGのテスト >>----- //
        String char51 =
            "０１２３４５６７８９"
                + "０１２３４５６７８９"
                + "０１２３４５６７８９"
                + "０１２３４５６７８９"
                + "０１２３４５６７８９"
                + "０";
        postEntry(char51, USERNAME, PHOTO, PASSWORD);
        // ========== assertion start ========== //
        assertInvalidParameters(); // 入力値NG
        // subject のエラーメッセージがセットされていること
        assertThat(tester.getErrors().get("subject"), is(notNullValue()));
        // ========== assertion end ========== //
    }

    @Test
    public void testSubjectLength50() throws Exception {
        // -----<< タイトル文字数50文字で入力値OKのテスト >>----- //
        String char50 =
            "０１２３４５６７８９"
                + "０１２３４５６７８９"
                + "０１２３４５６７８９"
                + "０１２３４５６７８９"
                + "０１２３４５６７８９";
        postEntry(char50, USERNAME, PHOTO, PASSWORD);
        // ========== assertion start ========== //
        assertValidParameters(); // 入力値OK
        // ========== assertion end ========== //
    }

    @Test
    public void testUsernameEmpty() throws Exception {
        // -----<< 投稿者名未入力で入力値NGのテスト >>----- //
        postEntry(SUBJECT, "", PHOTO, PASSWORD);
        
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
            "０１２３４５６７８９"
                + "０１２３４５６７８９"
                + "０１２３４５６７８９"
                + "０１２３４５６７８９"
                + "０１２３４５６７８９"
                + "０";
        postEntry(SUBJECT, char51, PHOTO, PASSWORD);
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
            "０１２３４５６７８９"
                + "０１２３４５６７８９"
                + "０１２３４５６７８９"
                + "０１２３４５６７８９"
                + "０１２３４５６７８９";
        postEntry(SUBJECT, char50, PHOTO, PASSWORD);
        // ========== assertion start ========== //
        assertValidParameters(); // 入力値OK
        // ========== assertion end ========== //
    }

    @Test
    public void testPhotoEmpty() throws Exception {
        
        // -----<< 写真未入力で入力値NGのテスト >>----- //
        postEntry(SUBJECT, USERNAME, null, PASSWORD);
        
        // ========== assertion start ========== //
        assertInvalidParameters(); // 入力値NG
        
        // text のエラーメッセージがセットされていること
        assertThat(tester.getErrors().get("photo"), is(notNullValue()));
        
        // ========== assertion end ========== //
    }

//    @Test
//    public void testTextLength100() throws Exception {
//        // -----<< 本文文字数100文字で入力値OKのテスト >>----- //
//
//        postEntry(SUBJECT, USERNAME, char100, PASSWORD);
//        // ========== assertion start ========== //
//        assertValidParameters(); // 入力値OK
//        // ========== assertion end ========== //
//    }

    @Test
    public void testPasswordEmpty() throws Exception {
        // -----<< パスワード未入力で入力値OKのテスト >>----- //
        postEntry(SUBJECT, USERNAME, PHOTO, "");
        // ========== assertion start ========== //
        assertValidParameters(); // 入力値OK
        // ========== assertion end ========== //
    }

    @Test
    public void testPasswordLength5() throws Exception {
        // -----<< パスワード文字数5文字で入力値NGのテスト >>----- //
        String char5 = "01234";
        postEntry(SUBJECT, USERNAME, PHOTO, char5);
        // ========== assertion start ========== //
        assertInvalidParameters(); // 入力値NG
        // password のエラーメッセージがセットされていること
        assertThat(tester.getErrors().get(RequestKeys.PASSWORD), is(notNullValue()));
        // ========== assertion end ========== //
    }

    @Test
    public void testPasswordLength6() throws Exception {
        // -----<< パスワード文字数6文字で入力値OKのテスト >>----- //
        String char6 = "012345";
        postEntry(SUBJECT, USERNAME, PHOTO, char6);
        // ========== assertion start ========== //
        assertValidParameters(); // 入力値OK
        // ========== assertion end ========== //
    }

    @Test
    public void testPasswordLength20() throws Exception {
        // -----<< パスワード文字数20文字で入力値OKのテスト >>----- //
        String char20 = "0123456789" + "0123456789";
        postEntry(SUBJECT, USERNAME, PHOTO, char20);
        // ========== assertion start ========== //
        assertValidParameters(); // 入力値OK
        // ========== assertion end ========== //
    }

    @Test
    public void testPasswordLength21() throws Exception {
        // -----<< パスワード文字数21文字で入力値NGのテスト >>----- //
        String char21 = "0123456789" + "0123456789" + "0";
        postEntry(SUBJECT, USERNAME, PHOTO, char21);
        // ========== assertion start ========== //
        assertInvalidParameters(); // 入力値NG
        // password のエラーメッセージがセットされていること
        assertThat(tester.getErrors().get(RequestKeys.PASSWORD), is(notNullValue()));
        // ========== assertion end ========== //
    }

    @Test
    public void run() throws Exception {
        tester.start("/bbs/postEntry");
        PostEntryController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is("/bbs/create"));
    }
}
