package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class PostTest {

    @Before
    public void setUp() throws Exception {
    }
    public Post setupNewPost(){
        return new Post("Day 1: Intro");
    }

    @After
    public void tearDown() throws Exception {

    }



    @Test
    public void NewPostObjectGetsCorrectlyCreated_true() throws Exception {
        Post post = new Post("Day 1: Intro");
        assertEquals(true, post instanceof Post);
    }

    @Test
    public void PostInstantiatesWithContent_true() throws Exception {
        Post post = new Post("Day 1: Intro");
        assertEquals("Day 1: Intro", post.getContent());

    }


    @Test
    public void getPublished_isFalseAfterInstantiation_false() throws Exception {
        Post myPost = new Post("Day 1: Intro");
        assertEquals(false, myPost.getPublished());
    }
    @Test
    public void getCreatedAt_instantiatesWithCurrentTime_today() throws Exception{
        Post myPost = setupNewPost(); //see below
        assertEquals(LocalDateTime.now().getDayOfWeek(), myPost.getCreatedAt().getDayOfWeek());
    }

}