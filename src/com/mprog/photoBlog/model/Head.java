package com.mprog.photoBlog.model;

import java.io.Serializable;
import java.util.Date;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.InverseModelListRef;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

import com.google.appengine.api.datastore.Key;
import com.mprog.photoBlog.meta.CommentMeta;

@Model(schemaVersion = 1)
public class Head implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;
    
    // 見出し 
    private String title;
    
    // 投稿日時 
    private Date postDate;
    
    // 投稿者名 
    private String username;
    
    // 編集用パスワード 
    private String password;
    
    // 最新コメントID（＝コメント数） 
    private Long lastCommentId = 0L;
    
    // 最新コメント日時 
    private Date lastCommentDate;
    
    // Bodyへの1対1の関連 
    private ModelRef<Photo> photoRef = new ModelRef<Photo>(Photo.class);
    
    // Commentへの1対多の関連 
    @Attribute(persistent=false)
    private InverseModelListRef<Comment, Head> commentRef =
        new InverseModelListRef<Comment, Head>(Comment.class, CommentMeta.get().headRef, this);
 
    


    /**
     * Returns the key.
     *
     * @return the key
     */
    public Key getKey() {
        return key;
    }

    /**
     * Sets the key.
     *
     * @param key
     *            the key
     */
    public void setKey(Key key) {
        this.key = key;
    }

    /**
     * Returns the version.
     *
     * @return the version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Sets the version.
     *
     * @param version
     *            the version
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * @return the postDate
     */
    public Date getPostDate() {
        return postDate;
    }

    /**
     * @param postDate the postDate to set
     */
    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the lastCommentId
     */
    public Long getLastCommentId() {
        return lastCommentId;
    }

    /**
     * @param lastCommentId the lastCommentId to set
     */
    public void setLastCommentId(Long lastCommentId) {
        this.lastCommentId = lastCommentId;
    }

    /**
     * @return the lastCommentDate
     */
    public Date getLastCommentDate() {
        return lastCommentDate;
    }

    /**
     * @param lastCommentDate the lastCommentDate to set
     */
    public void setLastCommentDate(Date lastCommentDate) {
        this.lastCommentDate = lastCommentDate;
    }

    /**
     * @return the subject
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param subject the subject to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the photoRef
     */
    public ModelRef<Photo> getPhotoRef() {
        return photoRef;
    }



    /**
     * @return the commentRef
     */
    public InverseModelListRef<Comment, Head> getCommentRef() {
        return commentRef;
    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Head other = (Head) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }
}
