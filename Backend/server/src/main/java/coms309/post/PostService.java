package coms309.post;

import java.util.ArrayList;  
import java.util.List;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;

@Service
public class PostService{  
    @Autowired  
    PostRepository postRepository;  
    public List<Post> getAllPost() {  
        List<Post> posts = new ArrayList<Post>();  
        postRepository.findAll().forEach(post -> posts.add(post));  
        return posts;
    }
    
    //getting a specific record  
    public Post getPostById(int id) {  
        return postRepository.findById(id).get();  
    }  

    public void saveOrUpdate(Post post)   
    {  
        postRepository.save(post);  
    }

    //deleting a specific record  
    public void delete(int id)   
    {  
        postRepository.deleteById(id);  
    }
}