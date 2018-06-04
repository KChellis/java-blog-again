import java.util.*;
import java.time.LocalDateTime;

import dao.Sql2oPostDao;
import models.Post;

import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        String connectionString = "jdbc:h2:~/blog.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        Sql2oPostDao postDao = new Sql2oPostDao(sql2o);

        get("/posts/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "newpost-form.hbs");
        }, new HandlebarsTemplateEngine());

        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            List<Post> posts = postDao.getAll();
            model.put("posts", posts);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        post("/posts/new", (request, response) -> { //URL to make new post on POST route
            Map<String, Object> model = new HashMap<String, Object>();
            String content = request.queryParams("content");
            Post newPost = new Post(content);
            postDao.add(newPost);
            model.put("posts", newPost);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        get("/posts/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            postDao.clearAllPosts();
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        get("/posts/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfPostToFind = Integer.parseInt(req.params("id"));
            Post foundPost = postDao.findById(idOfPostToFind);
            model.put("post", foundPost);
            return new ModelAndView(model, "post-detail.hbs");
        }, new HandlebarsTemplateEngine());

        get("/posts/:id/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfPostToEdit = Integer.parseInt(req.params("id"));
            Post editPost = postDao.findById(idOfPostToEdit);
            model.put("editPost", editPost);
            return new ModelAndView(model, "newpost-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("/posts/:id/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String newContent = req.queryParams("content");
            int idOfPostToEdit = Integer.parseInt(req.params("id"));
            postDao.update(idOfPostToEdit, newContent, false);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        get("/posts/:id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfPostToDelete = Integer.parseInt(req.params("id")); //pull id - must match route segment
            postDao.deleteById(idOfPostToDelete);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());
    }
}