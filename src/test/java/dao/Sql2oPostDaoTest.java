package dao;

import models.Post;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.*;
import org.sql2o.Connection;

import java.util.List;

import static org.junit.Assert.*;


public class Sql2oPostDaoTest {
    private Sql2oPostDao postDao; //ignore me for now. We'll create this soon.
    private Connection conn; //must be sql2o class conn

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        postDao = new Sql2oPostDao(sql2o); //ignore me for now
        conn = sql2o.open(); //keep connection open through entire test so it does not get erased
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingPostSetsId() throws Exception {
        Post testPost = new Post("Hi there people.");
        int originalPostId= testPost.getId();
        postDao.add(testPost);
        assertNotEquals(originalPostId, testPost.getId());
    }

    @Test
    public void existingPostCanBeFound() throws Exception{
        Post testPost = new Post("Hi there people.");
        postDao.add(testPost);
        Post foundPost = postDao.findById(1);
        assertEquals(testPost, foundPost);
    }

    @Test
    public void noPostsFound() throws Exception {
        List<Post> allPosts = postDao.getAll();
        assertEquals(0, allPosts.size());
    }

    @Test
    public void allPostsAreFound() throws Exception{
        Post testPost = new Post("Hi there people.");
        Post bestPost = new Post("Bye there people.");
        postDao.add(testPost);
        postDao.add(bestPost);
        List<Post> allPosts = postDao.getAll();
        assertEquals(2, allPosts.size());
    }

    @Test
    public void postIsUpdated() {
        Post testPost = new Post("Hi there people.");
        postDao.add(testPost);
        postDao.update(1,"Bye there people.",false );
        Post updatedPost = postDao.findById(1);
        assertEquals("Bye there people.", updatedPost.getContent());
        assertEquals(1, postDao.getAll().size());
    }

    @Test
    public void postDeletedById() {
        Post testPost = new Post("Hi there people.");
        Post bestPost = new Post("Bye there people.");
        postDao.add(testPost);
        postDao.add(bestPost);
        postDao.deleteById(1);
        List<Post> allPosts =postDao.getAll();
        assertEquals(1, allPosts.size());
        assertTrue(allPosts.contains(bestPost));
        assertFalse(allPosts.contains(testPost));
    }

    @Test
    public void allPostsAreDeleted() {
        Post testPost = new Post("Hi there people.");
        Post bestPost = new Post("Bye there people.");
        postDao.add(testPost);
        postDao.add(bestPost);
        postDao.clearAllPosts();
        List<Post> allPosts =postDao.getAll();
        assertEquals(0, allPosts.size());
    }
}
