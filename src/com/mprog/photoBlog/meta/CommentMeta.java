package com.mprog.photoBlog.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2011-06-09 16:59:17")
/** */
public final class CommentMeta extends org.slim3.datastore.ModelMeta<com.mprog.photoBlog.model.Comment> {

    /** */
    public final org.slim3.datastore.StringUnindexedAttributeMeta<com.mprog.photoBlog.model.Comment> comment = new org.slim3.datastore.StringUnindexedAttributeMeta<com.mprog.photoBlog.model.Comment>(this, "comment", "comment");

    /** */
    public final org.slim3.datastore.ModelRefAttributeMeta<com.mprog.photoBlog.model.Comment, org.slim3.datastore.ModelRef<com.mprog.photoBlog.model.Head>, com.mprog.photoBlog.model.Head> headRef = new org.slim3.datastore.ModelRefAttributeMeta<com.mprog.photoBlog.model.Comment, org.slim3.datastore.ModelRef<com.mprog.photoBlog.model.Head>, com.mprog.photoBlog.model.Head>(this, "headRef", "headRef", org.slim3.datastore.ModelRef.class, com.mprog.photoBlog.model.Head.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.mprog.photoBlog.model.Comment, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<com.mprog.photoBlog.model.Comment, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.mprog.photoBlog.model.Comment, java.util.Date> postDate = new org.slim3.datastore.CoreAttributeMeta<com.mprog.photoBlog.model.Comment, java.util.Date>(this, "postDate", "postDate", java.util.Date.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.mprog.photoBlog.model.Comment> username = new org.slim3.datastore.StringAttributeMeta<com.mprog.photoBlog.model.Comment>(this, "username", "username");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.mprog.photoBlog.model.Comment, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<com.mprog.photoBlog.model.Comment, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final CommentMeta slim3_singleton = new CommentMeta();

    /**
     * @return the singleton
     */
    public static CommentMeta get() {
       return slim3_singleton;
    }

    /** */
    public CommentMeta() {
        super("Comment", com.mprog.photoBlog.model.Comment.class);
    }

    @Override
    public com.mprog.photoBlog.model.Comment entityToModel(com.google.appengine.api.datastore.Entity entity) {
        com.mprog.photoBlog.model.Comment model = new com.mprog.photoBlog.model.Comment();
        model.setComment(textToString((com.google.appengine.api.datastore.Text) entity.getProperty("comment")));
        if (model.getHeadRef() == null) {
            throw new NullPointerException("The property(headRef) is null.");
        }
        model.getHeadRef().setKey((com.google.appengine.api.datastore.Key) entity.getProperty("headRef"));
        model.setKey(entity.getKey());
        model.setPostDate((java.util.Date) entity.getProperty("postDate"));
        model.setUsername((java.lang.String) entity.getProperty("username"));
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        com.mprog.photoBlog.model.Comment m = (com.mprog.photoBlog.model.Comment) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setUnindexedProperty("comment", stringToText(m.getComment()));
        if (m.getHeadRef() == null) {
            throw new NullPointerException("The property(headRef) must not be null.");
        }
        entity.setProperty("headRef", m.getHeadRef().getKey());
        entity.setProperty("postDate", m.getPostDate());
        entity.setProperty("username", m.getUsername());
        entity.setProperty("version", m.getVersion());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        com.mprog.photoBlog.model.Comment m = (com.mprog.photoBlog.model.Comment) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        com.mprog.photoBlog.model.Comment m = (com.mprog.photoBlog.model.Comment) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        com.mprog.photoBlog.model.Comment m = (com.mprog.photoBlog.model.Comment) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void assignKeyToModelRefIfNecessary(com.google.appengine.api.datastore.AsyncDatastoreService ds, java.lang.Object model) {
        com.mprog.photoBlog.model.Comment m = (com.mprog.photoBlog.model.Comment) model;
        if (m.getHeadRef() == null) {
            throw new NullPointerException("The property(headRef) must not be null.");
        }
        m.getHeadRef().assignKeyIfNecessary(ds);
    }

    @Override
    protected void incrementVersion(Object model) {
        com.mprog.photoBlog.model.Comment m = (com.mprog.photoBlog.model.Comment) model;
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
        com.mprog.photoBlog.model.Comment m = (com.mprog.photoBlog.model.Comment) model;
        writer.beginObject();
        org.slim3.datastore.json.JsonCoder encoder = null;
        if(m.getComment() != null){
            writer.setNextPropertyName("comment");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getComment());
        }
        if(m.getHeadRef() != null && m.getHeadRef().getKey() != null){
            writer.setNextPropertyName("headRef");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getHeadRef(), maxDepth, currentDepth);
        }
        if(m.getKey() != null){
            writer.setNextPropertyName("key");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getKey());
        }
        if(m.getPostDate() != null){
            writer.setNextPropertyName("postDate");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getPostDate());
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
    protected com.mprog.photoBlog.model.Comment jsonToModel(org.slim3.datastore.json.JsonRootReader rootReader, int maxDepth, int currentDepth) {
        com.mprog.photoBlog.model.Comment m = new com.mprog.photoBlog.model.Comment();
        org.slim3.datastore.json.JsonReader reader = null;
        org.slim3.datastore.json.JsonCoder decoder = null;
        reader = rootReader.newObjectReader("comment");
        decoder = new org.slim3.datastore.json.Default();
        m.setComment(decoder.decode(reader, m.getComment()));
        reader = rootReader.newObjectReader("headRef");
        decoder = new org.slim3.datastore.json.Default();
        decoder.decode(reader, m.getHeadRef(), maxDepth, currentDepth);
        reader = rootReader.newObjectReader("key");
        decoder = new org.slim3.datastore.json.Default();
        m.setKey(decoder.decode(reader, m.getKey()));
        reader = rootReader.newObjectReader("postDate");
        decoder = new org.slim3.datastore.json.Default();
        m.setPostDate(decoder.decode(reader, m.getPostDate()));
        reader = rootReader.newObjectReader("username");
        decoder = new org.slim3.datastore.json.Default();
        m.setUsername(decoder.decode(reader, m.getUsername()));
        reader = rootReader.newObjectReader("version");
        decoder = new org.slim3.datastore.json.Default();
        m.setVersion(decoder.decode(reader, m.getVersion()));
        return m;
    }
}