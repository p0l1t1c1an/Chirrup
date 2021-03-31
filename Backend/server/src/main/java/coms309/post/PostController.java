package coms309.post;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coms309.user.User;
import coms309.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "PostController", description = "REST APIs for post entity")
@RequestMapping("/api")
@RestController
public class PostController {
    @Autowired
    PostService postService;
    @Autowired
    UserService userService;
    Logger logger = LoggerFactory.getLogger(PostController.class);    

    //creating a get mapping for returning all posts in the db
    @ApiOperation(value = "lists all posts", response = Iterable.class, tags = "getAllPost")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "not authorized!"), 
        @ApiResponse(code = 403, message = "forbidden!!!"),
        @ApiResponse(code = 404, message = "not found!!!") })
    @GetMapping("/posts")
    private List<Post> getAllPost() {
        logger.info("got all posts");
        return postService.getAllPost();
    }

    //creating a get mapping for returning a specific post
    @ApiOperation(value = "gets a post by id", response = Post.class, tags = "getPost")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "not authorized!"), 
        @ApiResponse(code = 403, message = "forbidden!!!"),
        @ApiResponse(code = 404, message = "not found!!!") })
    @GetMapping("/posts/{id}")
    private Post getPost(@PathVariable("id") int id) {
        logger.info("got post by id");
        return postService.getPostById(id);
    }

    //creating a delete mapping for removing a post
    @ApiOperation(value = "deletes a post by id", response = String.class, tags = "deletePost")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "not authorized!"), 
        @ApiResponse(code = 403, message = "forbidden!!!"),
        @ApiResponse(code = 404, message = "not found!!!") })
    @DeleteMapping("/posts/{id}")
    private String deletePost(@PathVariable("id") int id) {
        logger.info("deleted post by id");
        postService.delete(id);
        return "Post deleted: " + id;
    }

    //creating post mapping the creates a new post
    @ApiOperation(value = "creates a new post", response = String.class, tags = "savePost")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "not authorized!"), 
        @ApiResponse(code = 403, message = "forbidden!!!"),
        @ApiResponse(code = 404, message = "not found!!!") })
    @PostMapping("/posts/{userId}")
    private String savePost(@RequestBody Post post, @PathVariable(value = "userId") int id) {
        User creator = userService.getUserById(id);
        post.setCreator(creator);
        postService.saveOrUpdate(post);
        logger.info("created a new post");
        return "post created: " + post.getId();
    }

    //creating an update mapping that edits an existing post
    @ApiOperation(value = "updates a post by id", response = String.class, tags = "updatePost")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "not authorized!"), 
        @ApiResponse(code = 403, message = "forbidden!!!"),
        @ApiResponse(code = 404, message = "not found!!!") })
    @PatchMapping("/posts/{postId}")
    private String updatePost(@PathVariable("postId") int id, @RequestBody Post post) {
        Post current = postService.getPostById(id);
        current.updatePartialInfo(post);
        logger.info("updated post by id");
        postService.saveOrUpdate(current);
        return "post updated: " + current.getId();
    }
}
