package coms309.feedTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.context.annotation.Bean;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito;

import coms309.user.User;
import coms309.user.UserService;

import coms309.post.Post;
import coms309.post.PostService;

import coms309.feed.FeedService;

import coms309.ServerApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class, 
                webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FeedServiceTest { 

    @InjectMocks
	FeedService feedService;

    @Mock
    PostService postService;
    
    @Mock
    UserService userService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

    @Test
    public void newestFeedAllTest() {
        User mock = Mockito.mock(User.class);

        List<Post> postsList = new ArrayList<Post>();
        postsList.add(new Post(1, mock, LocalDateTime.now(), null));
        postsList.add(new Post(2, mock, LocalDateTime.of(2000, 1, 1, 1, 25), null));
        postsList.add(new Post(3, mock, LocalDateTime.of(2000, 1, 1, 1, 10), null));
        postsList.add(new Post(4, mock, LocalDateTime.of(2000, 1, 1, 1, 20), null));
        postsList.add(new Post(5, mock, LocalDateTime.of(2000, 1, 1, 1, 15), null));
    
		when(postService.getAllPost()).thenReturn(postsList);

        List<Integer> feed = feedService.feedAll(false);
        
        assertEquals(5, feed.size());

        // Order for feed should be 1, 2, 4, 5, 3
        assertEquals(Integer.valueOf(1), feed.get(0));
        assertEquals(Integer.valueOf(2), feed.get(1));
        assertEquals(Integer.valueOf(4), feed.get(2));
        assertEquals(Integer.valueOf(5), feed.get(3));
        assertEquals(Integer.valueOf(3), feed.get(4)); 
    }

    @Test
    public void likedFeedAllTest() { 
        User mock1 = Mockito.mock(User.class);
        User mock2 = Mockito.mock(User.class);
        User mock3 = Mockito.mock(User.class);

        List<Post> postsList = new ArrayList<Post>();
        postsList.add(new Post(1, mock1, LocalDateTime.now(), null));
        postsList.add(new Post(2, mock1, LocalDateTime.of(2000, 1, 1, 1, 25), null));
        postsList.add(new Post(3, mock2, LocalDateTime.of(2000, 1, 1, 1, 10), null));
        postsList.add(new Post(4, mock2, LocalDateTime.of(2000, 1, 1, 1, 20), null));
        postsList.add(new Post(5, mock3, LocalDateTime.of(2000, 1, 1, 1, 15), null));
  
        // Post Id 1
        postsList.get(0).addLike(mock1);
        postsList.get(0).addLike(mock2);
        postsList.get(0).addLike(mock3);

        // Post Id 3
        postsList.get(2).addLike(mock1);
        postsList.get(2).addLike(mock2);

        // Post Id 4
        postsList.get(3).addLike(mock1);

		when(postService.getAllPost()).thenReturn(postsList);

        List<Integer> feed = feedService.feedAll(true);
        
        assertEquals(5, feed.size());

        // Order for feed should be 1, 3, 4, 2, 5
        assertEquals(Integer.valueOf(1), feed.get(0));
        assertEquals(Integer.valueOf(3), feed.get(1));
        assertEquals(Integer.valueOf(4), feed.get(2));
        assertEquals(Integer.valueOf(2), feed.get(3));
        assertEquals(Integer.valueOf(5), feed.get(4)); 
    }
    
    @Test
    public void userFeedTest() {
        User spy1 = Mockito.spy(User.class);
        User spy2 = Mockito.spy(User.class);

        spy1.getPosts().add(new Post(1, spy1, LocalDateTime.of(2000, 1, 1, 1, 10), null));
        spy1.getPosts().add(new Post(2, spy1, LocalDateTime.of(2000, 1, 1, 1, 25), null));
        spy1.getPosts().add(new Post(3, spy1, LocalDateTime.now(), null));
   
        spy2.addFollowing(spy1);

		when(userService.getUserById(2)).thenReturn(spy2);
         
        List<Integer> feed = feedService.feedById(2, false);
        
        assertEquals(3, feed.size());

        // Order for feed should be 3, 2, 1
        assertEquals(Integer.valueOf(3), feed.get(0));
        assertEquals(Integer.valueOf(2), feed.get(1)); 
        assertEquals(Integer.valueOf(1), feed.get(2));
    }

}
