package coms309.postTests;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;

import org.springframework.test.context.junit4.SpringRunner;

import coms309.ServerApplication;
import coms309.post.Post;
import coms309.post.PostRepository;
import coms309.post.PostService;

import org.springframework.boot.test.context.SpringBootTest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class, 
                webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostServiceTest {
    @InjectMocks
    PostService service;

    @Mock
    PostRepository repo;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getPostByIdTest() {
        Post p = new Post(1, null, null, "content");
        when(repo.getPostById(1)).thenReturn(p);

        Post got = service.getPostById(1);
        assertEquals("content", got.getContent());
    }

    @Test
    public void getAllPostTest() {
        List<Post> posts = new ArrayList<Post>();
        posts.add(new Post(1, null, null, "content1"));
        posts.add(new Post(2, null, null, "content2"));
        posts.add(new Post(3, null, null, "content3"));

        posts.forEach(p -> service.saveOrUpdate(p));

        when(repo.findAll()).thenReturn(posts);

        List<Post> postList = service.getAllPost();

        assertEquals(3, postList.size());
    }

    @Test
    public void updatePostTest() {
        Post post = new Post(1, null, null, "content");

        service.saveOrUpdate(post);
        post.setContent("new content");
        when(repo.getPostById(1)).thenReturn(post);
        service.saveOrUpdate(post);

        Post got = service.getPostById(1);

        assertEquals("new content", got.getContent());
    }
}
