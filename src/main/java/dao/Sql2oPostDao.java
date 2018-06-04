package dao;

import models.Post;
import org.sql2o.*;
import java.util.List;

public class Sql2oPostDao implements PostDao{
    private final Sql2o sql2o;

    public Sql2oPostDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }
    @Override
    public void add(Post post){
        String sql = "INSERT INTO posts (content, published) VALUES (:content, :published)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(post)
                    .executeUpdate()
                    .getKey();
            post.setId(id);
        } catch(Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Post> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM posts")
                    .executeAndFetch(Post.class);
        }
    }

    @Override
    public Post findById(int id){
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM posts WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Post.class);
        }
    }

    @Override
    public void update(int id, String content, boolean published){
        String sql = "UPDATE posts SET content = :content, published = :published WHERE id = :id";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("content", content)
                    .addParameter("published", published)
                    .executeUpdate();
        } catch(Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id){
        String sql = "DELETE from posts WHERE id=:id";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllPosts(){
        String sql = "DELETE from posts";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}
