package coms309.feed;

import coms309.user.User;
import coms309.user.UserService;

import coms309.post.Post;
import coms309.post.PostService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.query.criteria.internal.path.SetAttributeJoin;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  

@Service  
public class FeedService {
    
    @Autowired  
    UserService userService;
  
    @Autowired
    PostService postService;

    private int comparePosts(Post a, Post b, boolean mostLiked) {
        if(mostLiked) {
            int aLikes = a.getLikeIds().size();
            int bLikes = b.getLikeIds().size();
            
            if(aLikes < bLikes) {
                return -1;
            }
            else if(aLikes > bLikes) {
                return 1;
            }

            return 0;
        }
        else {
            return a.getDateCreated().compareTo(b.getDateCreated());
        }
    }

    // Simple Feed generation
    public List<Integer> feedById(int id, boolean mostLiked) {
        List<Post> feed = new ArrayList<Post>(); 

        for(User u : userService.getUserById(id).getFollowing()) {
            for(Post p : u.getPosts()) {
                    feed.add(p);
            }
        }

        // -1 to reverse order of sort
        // It makes it so we get Most Liked or Newest
        Collections.sort(feed, (a, b) -> -1*comparePosts(a, b, mostLiked));
        return feed.stream().map(x -> x.getId()).collect(Collectors.toList());
    }

    public List<Integer> feedAll(boolean mostLiked) {
        List<Post> feed = postService.getAllPost();
        Collections.sort(feed, (a, b) -> -1*comparePosts(a, b, mostLiked));
        return feed.stream().map(x -> x.getId()).collect(Collectors.toList());
    }
    
}
