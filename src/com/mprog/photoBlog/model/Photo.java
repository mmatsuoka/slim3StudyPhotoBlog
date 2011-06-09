package com.mprog.photoBlog.model;

import java.io.Serializable;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.InverseModelRef;
import org.slim3.datastore.Model;

import com.google.appengine.api.datastore.Key;
import com.mprog.photoBlog.meta.HeadMeta;

@Model(schemaVersion = 1)
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;
    
 // 記事本文 
    @Attribute(lob=true)
    private byte[] photoImage;
    
    // Headへの1対1の関連 
    @Attribute(persistent=false)
    private InverseModelRef<Head, Photo> headRef =
        new InverseModelRef<Head, Photo>(Head.class, HeadMeta.get().photoRef, this);
 

    /**
     * @return the photoImage
     */
    public byte[] getPhotoImage() {
        return photoImage;
    }

    /**
     * @param photoImage the photoImage to set
     */
    public void setPhotoImage(byte[] photoImage) {
        this.photoImage = photoImage;
    }

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
        Photo other = (Photo) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }


    /**
     * @return the headRef
     */
    public InverseModelRef<Head, Photo> getHeadRef() {
        return headRef;
    }
}
