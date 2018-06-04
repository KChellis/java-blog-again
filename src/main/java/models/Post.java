package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;


public class Post {
    private  String content;
    private boolean published;
    private LocalDateTime createdAt;
    private int id;

    public Post(String content) {
        this.content = content;
        this.published = false;
        this.createdAt = LocalDateTime.now();
    }

    public String getContent() {
        return content;
    }

    public boolean getPublished(){
        return this.published;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return published == post.published &&
                id == post.id &&
                Objects.equals(content, post.content);
    }

    @Override
    public int hashCode() {

        return Objects.hash(content, published, id);
    }
}
