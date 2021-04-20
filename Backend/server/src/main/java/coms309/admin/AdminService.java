package coms309.admin;

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
public class AdminService {
    
    @Autowired
    PostService postService;

    private int mostReported(Post a, Post b) {
        int aReports = a.getReports().size();
        int bReports = b.getReports().size();
        
        if(aReports < bReports) {
            return -1;
        }
        else if(aReports > bReports) {
            return 1;
        }

        return 0;
    }

    // Simple Admin generation
    public List<Integer> adminFeed() {
        List<Post> posts = postService.getAllPost();

        posts.removeIf(p -> p.getReports().isEmpty());
        Collections.sort(posts, (Post a, Post b) -> -1*mostReported(a, b));
        
        return posts.stream().map(x -> x.getId()).collect(Collectors.toList());
    }

    public void solveEditPost(int id, Post newPost) {
        Post p = postService.getPostById(id);

        p.getReports().clear();
        p.updatePartialInfo(newPost);

        postService.saveOrUpdate(p);
    }

    public void solveIgnorePost(int id) {
        Post p = postService.getPostById(id);
        p.getReports().clear();
        postService.saveOrUpdate(p);
    }

    public void solveDeletePost(int id) {
        postService.delete(id);
    }

}
