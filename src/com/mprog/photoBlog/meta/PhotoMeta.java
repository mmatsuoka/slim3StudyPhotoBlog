package com.mprog.photoBlog.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2011-06-09 16:59:17")
/** */
public final class PhotoMeta extends org.slim3.datastore.ModelMeta<com.mprog.photoBlog.model.Photo> {

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.mprog.photoBlog.model.Photo, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<com.mprog.photoBlog.model.Photo, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.CoreUnindexedAttributeMeta<com.mprog.photoBlog.model.Photo, byte[]> photoImage = new org.slim3.datastore.CoreUnindexedAttributeMeta<com.mprog.photoBlog.model.Photo, byte[]>(this, "photoImage", "photoImage", byte[].class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.mprog.photoBlog.model.Photo, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<com.mprog.photoBlog.model.Photo, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final PhotoMeta slim3_singleton = new PhotoMeta();

    /**
     * @return the singleton
     */
    public static PhotoMeta get() {
       return slim3_singleton;
    }

    /** */
    public PhotoMeta() {
        super("Photo", com.mprog.photoBlog.model.Photo.class);
    }

    @Override
    public com.mprog.photoBlog.model.Photo entityToModel(com.google.appengine.api.datastore.Entity entity) {
        com.mprog.photoBlog.model.Photo model = new com.mprog.photoBlog.model.Photo();
        model.setKey(entity.getKey());
        model.setPhotoImage(blobToBytes((com.google.appengine.api.datastore.Blob) entity.getProperty("photoImage")));
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        com.mprog.photoBlog.model.Photo m = (com.mprog.photoBlog.model.Photo) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setUnindexedProperty("photoImage", bytesToBlob(m.getPhotoImage()));
        entity.setProperty("version", m.getVersion());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        com.mprog.photoBlog.model.Photo m = (com.mprog.photoBlog.model.Photo) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        com.mprog.photoBlog.model.Photo m = (com.mprog.photoBlog.model.Photo) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        com.mprog.photoBlog.model.Photo m = (com.mprog.photoBlog.model.Photo) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void assignKeyToModelRefIfNecessary(com.google.appengine.api.datastore.AsyncDatastoreService ds, java.lang.Object model) {
    }

    @Override
    protected void incrementVersion(Object model) {
        com.mprog.photoBlog.model.Photo m = (com.mprog.photoBlog.model.Photo) model;
        long version = m.getVersion() != null ? m.getVersion().longValue() : 0L;
        m.setVersion(Long.valueOf(version + 1L));
    }

    @Override
    protected void prePut(Object model) {
    }

    @Override
    public String getSchemaVersionName() {
        return "slim3.schemaVersion";
    }

    @Override
    public String getClassHierarchyListName() {
        return "slim3.classHierarchyList";
    }

    @Override
    protected boolean isCipherProperty(String propertyName) {
        return false;
    }

    @Override
    protected void modelToJson(org.slim3.datastore.json.JsonWriter writer, java.lang.Object model, int maxDepth, int currentDepth) {
        com.mprog.photoBlog.model.Photo m = (com.mprog.photoBlog.model.Photo) model;
        writer.beginObject();
        org.slim3.datastore.json.JsonCoder encoder = null;
        if(m.getHeadRef() != null){
            writer.setNextPropertyName("headRef");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getHeadRef());
        }
        if(m.getKey() != null){
            writer.setNextPropertyName("key");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getKey());
        }
        if(m.getPhotoImage() != null){
            writer.setNextPropertyName("photoImage");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, new com.google.appengine.api.datastore.ShortBlob(m.getPhotoImage()));
        }
        if(m.getVersion() != null){
            writer.setNextPropertyName("version");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getVersion());
        }
        writer.endObject();
    }

    @Override
    protected com.mprog.photoBlog.model.Photo jsonToModel(org.slim3.datastore.json.JsonRootReader rootReader, int maxDepth, int currentDepth) {
        com.mprog.photoBlog.model.Photo m = new com.mprog.photoBlog.model.Photo();
        org.slim3.datastore.json.JsonReader reader = null;
        org.slim3.datastore.json.JsonCoder decoder = null;
        reader = rootReader.newObjectReader("headRef");
        decoder = new org.slim3.datastore.json.Default();
        reader = rootReader.newObjectReader("key");
        decoder = new org.slim3.datastore.json.Default();
        m.setKey(decoder.decode(reader, m.getKey()));
        reader = rootReader.newObjectReader("photoImage");
        decoder = new org.slim3.datastore.json.Default();
        if(m.getPhotoImage() != null){
            m.setPhotoImage(decoder.decode(reader, new com.google.appengine.api.datastore.ShortBlob(m.getPhotoImage())).getBytes());
        } else{
            com.google.appengine.api.datastore.ShortBlob v = decoder.decode(reader, (com.google.appengine.api.datastore.ShortBlob)null);
            if(v != null){
                m.setPhotoImage(v.getBytes());
            } else{
                m.setPhotoImage(null);
            }
        }
        reader = rootReader.newObjectReader("version");
        decoder = new org.slim3.datastore.json.Default();
        m.setVersion(decoder.decode(reader, m.getVersion()));
        return m;
    }
}