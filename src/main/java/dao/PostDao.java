package dao;

import models.Post;
import java.util.List;

public interface PostDao {
    List<Post> getAll();

    void add(Post post);

    Post findById(int id);

    void update(int id, String content, boolean published);

    void deleteById(int id);
    void clearAllPosts();

}
