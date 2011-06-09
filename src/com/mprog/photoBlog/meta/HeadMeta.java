package com.mprog.photoBlog.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2011-06-09 16:59:17")
/** */
public final class HeadMeta extends org.slim3.datastore.ModelMeta<com.mprog.photoBlog.model.Head> {

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.mprog.photoBlog.model.Head, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<com.mprog.photoBlog.model.Head, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.mprog.photoBlog.model.Head, java.util.Date> lastCommentDate = new org.slim3.datastore.CoreAttributeMeta<com.mprog.photoBlog.model.Head, java.util.Date>(this, "lastCommentDate", "lastCommentDate", java.util.Date.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.mprog.photoBlog.model.Head, java.lang.Long> lastCommentId = new org.slim3.datastore.CoreAttributeMeta<com.mprog.photoBlog.model.Head, java.lang.Long>(this, "lastCommentId", "lastCommentId", java.lang.Long.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.mprog.photoBlog.model.Head> password = new org.slim3.datastore.StringAttributeMeta<com.mprog.photoBlog.model.Head>(this, "password", "password");

    /** */
    public final org.slim3.datastore.ModelRefAttributeMeta<com.mprog.photoBlog.model.Head, org.slim3.datastore.ModelRef<com.mprog.photoBlog.model.Photo>, com.mprog.photoBlog.model.Photo> photoRef = new org.slim3.datastore.ModelRefAttributeMeta<com.mprog.photoBlog.model.Head, org.slim3.datastore.ModelRef<com.mprog.photoBlog.model.Photo>, com.mprog.photoBlog.model.Photo>(this, "photoRef", "photoRef", org.slim3.datastore.ModelRef.class, com.mprog.photoBlog.model.Photo.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.mprog.photoBlog.model.Head, java.util.Date> postDate = new org.slim3.datastore.CoreAttributeMeta<com.mprog.photoBlog.model.Head, java.util.Date>(this, "postDate", "postDate", java.util.Date.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.mprog.photoBlog.model.Head> title = new org.slim3.datastore.StringAttributeMeta<com.mprog.photoBlog.model.Head>(this, "title", "title");

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.mprog.photoBlog.model.Head> username = new org.slim3.datastore.StringAttributeMeta<com.mprog.photoBlog.model.Head>(this, "username", "username");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.mprog.photoBlog.model.Head, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<com.mprog.photoBlog.model.Head, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final HeadMeta slim3_singleton = new HeadMeta();

    /**
     * @return the singleton
     */
    public static HeadMeta get() {
       return slim3_singleton;
    }

    /** */
    public HeadMeta() {
        super("Head", com.mprog.photoBlog.model.Head.class);
    }

    @Override
    public com.mprog.photoBlog.model.Head entityToModel(com.google.appengine.api.datastore.Entity entity) {
        com.mprog.photoBlog.model.Head model = new com.mprog.photoBlog.model.Head();
        model.setKey(entity.getKey());
        model.setLastCommentDate((java.util.Date) entity.getProperty("lastCommentDate"));
        model.setLastCommentId((java.lang.Long) entity.getProperty("lastCommentId"));
        model.setPassword((java.lang.String) entity.getProperty("password"));
        if (model.getPhotoRef() == null) {
            throw new NullPointerException("The property(photoRef) is null.");
        }
        model.getPhotoRef().setKey((com.google.appengine.api.datastore.Key) entity.getProperty("photoRef"));
        model.setPostDate((java.util.Date) entity.getProperty("postDate"));
        model.setTitle((java.lang.String) entity.getProperty("title"));
        model.setUsername((java.lang.String) entity.getProperty("username"));
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        com.mprog.photoBlog.model.Head m = (com.mprog.photoBlog.model.Head) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setProperty("lastCommentDate", m.getLastCommentDate());
        entity.setProperty("lastCommentId", m.getLastCommentId());
        entity.setProperty("password", m.getPassword());
        if (m.getPhotoRef() == null) {
            throw new NullPointerException("The property(photoRef) must not be null.");
        }
        entity.setProperty("photoRef", m.getPhotoRef().getKey());
        entity.setProperty("postDate", m.getPostDate());
        entity.setProperty("title", m.getTitle());
        entity.setProperty("username", m.getUsername());
        entity.setProperty("version", m.getVersion());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        com.mprog.photoBlog.model.Head m = (com.mprog.photoBlog.model.Head) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        com.mprog.photoBlog.model.Head m = (com.mprog.photoBlog.model.Head) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        com.mprog.photoBlog.model.Head m = (com.mprog.photoBlog.model.Head) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void assignKeyToModelRefIfNecessary(com.google.appengine.api.datastore.AsyncDatastoreService ds, java.lang.Object model) {
        com.mprog.photoBlog.model.Head m = (com.mprog.photoBlog.model.Head) model;
        if (m.getPhotoRef() == null) {
            throw new NullPointerException("The property(photoRef) must not be null.");
        }
        m.getPhotoRef().assignKeyIfNecessary(ds);
    }

    @Override
    protected void incrementVersion(Object model) {
        com.mprog.photoBlog.model.Head m = (com.mprog.photoBlog.model.Head) model;
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
        com.mprog.photoBlog.model.Head m = (com.mprog.photoBlog.model.Head) model;
        writer.beginObject();
        org.slim3.datastore.json.JsonCoder encoder = null;
        if(m.getCommentRef() != null){
            writer.setNextPropertyName("commentRef");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getCommentRef());
        }
        if(m.getKey() != null){
            writer.setNextPropertyName("key");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getKey());
        }
        if(m.getLastCommentDate() != null){
            writer.setNextPropertyName("lastCommentDate");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getLastCommentDate());
        }
        if(m.getLastCommentId() != null){
            writer.setNextPropertyName("lastCommentId");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getLastCommentId());
        }
        if(m.getPassword() != null){
            writer.setNextPropertyName("password");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getPassword());
        }
        if(m.getPhotoRef() != null && m.getPhotoRef().getKey() != null){
            writer.setNextPropertyName("photoRef");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getPhotoRef(), maxDepth, currentDepth);
        }
        if(m.getPostDate() != null){
            writer.setNextPropertyName("postDate");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getPostDate());
        }
        if(m.getTitle() != null){
            writer.setNextPropertyName("title");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getTitle());
        }
        if(m.getUsername() != null){
            writer.setNextPropertyName("username");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getUsername());
        }
        if(m.getVersion() != null){
            writer.setNextPropertyName("version");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getVersion());
        }
        writer.endObject();
    }

    @Override
    protected com.mprog.photoBlog.model.Head jsonToModel(org.slim3.datastore.json.JsonRootReader rootReader, int maxDepth, int currentDepth) {
        com.mprog.photoBlog.model.Head m = new com.mprog.photoBlog.model.Head();
        org.slim3.datastore.json.JsonReader reader = null;
        org.slim3.datastore.json.JsonCoder decoder = null;
        reader = rootReader.newObjectReader("commentRef");
        decoder = new org.slim3.datastore.json.Default();
        reader = rootReader.newObjectReader("key");
        decoder = new org.slim3.datastore.json.Default();
        m.setKey(decoder.decode(reader, m.getKey()));
        reader = rootReader.newObjectReader("lastCommentDate");
        decoder = new org.slim3.datastore.json.Default();
        m.setLastCommentDate(decoder.decode(reader, m.getLastCommentDate()));
        reader = rootReader.newObjectReader("lastCommentId");
        decoder = new org.slim3.datastore.json.Default();
        m.setLastCommentId(decoder.decode(reader, m.getLastCommentId()));
        reader = rootReader.newObjectReader("password");
        decoder = new org.slim3.datastore.json.Default();
        m.setPassword(decoder.decode(reader, m.getPassword()));
        reader = rootReader.newObjectReader("photoRef");
        decoder = new org.slim3.datastore.json.Default();
        decoder.decode(reader, m.getPhotoRef(), maxDepth, currentDepth);
        reader = rootReader.newObjectReader("postDate");
        decoder = new org.slim3.datastore.json.Default();
        m.setPostDate(decoder.decode(reader, m.getPostDate()));
        reader = rootReader.newObjectReader("title");
        decoder = new org.slim3.datastore.json.Default();
        m.setTitle(decoder.decode(reader, m.getTitle()));
        reader = rootReader.newObjectReader("username");
        decoder = new org.slim3.datastore.json.Default();
        m.setUsername(decoder.decode(reader, m.getUsername()));
        reader = rootReader.newObjectReader("version");
        decoder = new org.slim3.datastore.json.Default();
        m.setVersion(decoder.decode(reader, m.getVersion()));
        return m;
    }
}