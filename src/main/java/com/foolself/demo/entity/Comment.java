package com.foolself.demo.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    private Long toId;
    private Long articleId;
    private String fromUid;
    private String toUid;
    private Date created;
    private Integer likes;
    @Column(columnDefinition = "TEXT")
    private String content;

    public Comment() {
    }

    public Comment(long toId, long articleId, String fromUid, String toUid, Date created, Integer likes, String content) {
        this.toId = toId;
        this.articleId = articleId;
        this.fromUid = fromUid;
        this.toUid = toUid;
        this.created = created;
        this.likes = likes;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getToId() {
        return toId;
    }

    public void setToId(Long toId) {
        this.toId = toId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getFromUid() {
        return fromUid;
    }

    public void setFromUid(String fromUid) {
        this.fromUid = fromUid;
    }

    public String getToUid() {
        return toUid;
    }

    public void setToUid(String toUid) {
        this.toUid = toUid;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", toId=" + toId +
                ", fromUid='" + fromUid + '\'' +
                ", toUid='" + toUid + '\'' +
                ", created=" + created +
                ", likes=" + likes +
                '}';
    }
}
