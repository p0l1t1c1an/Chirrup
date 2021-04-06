package coms309.feed;

import coms309.user.User;
import coms309.user.UserService;

import coms309.post.Post;

import coms309.settings.SettingsService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.hibernate.query.criteria.internal.path.SetAttributeJoin;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  

@Service  
public class FeedService {
    
    @Autowired  
    UserService userService;

    @Autowired
    SettingsService settingsService;

    // Simple Feed generation

    public List<Post> feedById(int id) {
        List<Post> feed = new ArrayList<Post>(); 
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);

        for(User u : userService.getUserById(id).getFollowing()) {
            for(Post p : u.getPosts()) {
               if(p.getDateCreated().isAfter(yesterday)) {
                    feed.add(p);
               }
            }
        }

        Collections.sort(feed, (a, b) -> a.getDateCreated().compareTo(b.getDateCreated()));
        return feed;
    }
    
}
