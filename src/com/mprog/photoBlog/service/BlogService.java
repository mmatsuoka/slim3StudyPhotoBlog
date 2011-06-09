package com.mprog.photoBlog.service;

import java.util.List;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

import com.mprog.photoBlog.meta.CommentMeta;
import com.mprog.photoBlog.meta.HeadMeta;
import com.mprog.photoBlog.meta.PhotoMeta;
import com.mprog.photoBlog.model.Comment;
import com.mprog.photoBlog.model.Head;
import com.mprog.photoBlog.model.Photo;


public class BlogService {

    public List<Head> getAll() {
        HeadMeta m = HeadMeta.get();
        return Datastore.query(m).sort(m.postDate.desc).asList();
    }

    public void insert(Head head, Photo photo) throws Exception {
        
        // Headの主キーは自動採番。
        head.setKey(Datastore.allocateId(HeadMeta.get()));
        
        //  HeadのKeyを親キーに指定してPhotoのKeyを生成。
        photo.setKey(Datastore.allocateId(head.getKey(), PhotoMeta.get()));
        
        // 関連づけを設定。
        head.getPhotoRef().setModel(photo);
        
        // トランザクションでデータストアにデータを登録する。
        Transaction tx = Datastore.beginTransaction();
        try {
            Datastore.put(tx, head, photo);
            tx.commit();
        }
        catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
        
    }

    public Head get(Key headKey) {
        //主キー検索
        return Datastore.getOrNull(HeadMeta.get(), headKey);
    }

    public void update(Head head, Photo photo) throws Exception {
        Transaction tx = Datastore.beginTransaction();
        try {
            // データのバージョンを使った楽観的排他制御
            Datastore.get(tx, HeadMeta.get(), head.getKey(), head.getVersion());
            
            // 更新
            Datastore.put(tx, head, photo);
            tx.commit();
        }
        catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    public void delete(Key headKey) throws Exception {
        Transaction tx = Datastore.beginTransaction();
        try {
            Head head = Datastore.get(tx, HeadMeta.get(), headKey);
            Key bodyKey = head.getPhotoRef().getKey();
            Datastore.delete(tx, headKey, bodyKey);
            tx.commit();
        }
        catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }        
    }

    public void insert(Head head, Comment comment) throws Exception {
        
        long newCommentId = head.getLastCommentId() + 1L;
        
        Key commentKey = Datastore.createKey(head.getKey(), CommentMeta.get(), newCommentId);
        comment.setKey(commentKey);
        comment.getHeadRef().setModel(head);
     
        head.setLastCommentId(newCommentId);
        head.setLastCommentDate(comment.getPostDate());
        
        Transaction tx = Datastore.beginTransaction();
        try {
            Datastore.get(tx, HeadMeta.get(), head.getKey(), head.getVersion());
            Datastore.put(tx, head, comment);
            tx.commit();
        }
        catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }      
        
    }

}
