package coms309.post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
        Post post = postService.getPostById(id);

        post.dismissComments();
        if(post.getParent() == null) {
            postService.saveOrUpdate(post);
            postService.delete(id);
        } else {
            Post parent = post.getParent();
            parent.removeComment(post);
            postService.saveOrUpdate(parent);

            post.removeParent();
            postService.saveOrUpdate(post);
            postService.delete(id);
        }

        logger.info("deleted post by id");
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

        LocalDateTime now = LocalDateTime.now();

        post.setDateCreated(now);

        post.setCreator(creator);
        postService.saveOrUpdate(post);
        logger.info("created a new post");
        return "post created: " + post.getId();
    }

    //creating post mapping the creates a new comment
    @ApiOperation(value = "creates a new comment", response = String.class, tags = "saveComment")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "not authorized!"), 
        @ApiResponse(code = 403, message = "forbidden!!!"),
        @ApiResponse(code = 404, message = "not found!!!") })
    @PostMapping("/posts/{userId}/{postId}")
    private String saveComment(@RequestBody Post comment, @PathVariable(value = "userId") int id, @PathVariable(value = "postId") int postId) {
        User creator = userService.getUserById(id);
        Post parentPost = postService.getPostById(postId);

        LocalDateTime now = LocalDateTime.now();
        comment.setDateCreated(now);

        comment.setCreator(creator);
        comment.setParent(parentPost);

        parentPost.addComment(comment);
        postService.saveOrUpdate(comment);
        postService.saveOrUpdate(parentPost);

        return "comment created: " + comment.getId();
    }

    //creating post mapping the creates a new like
    @ApiOperation(value = "creates a new like", response = String.class, tags = "saveLike")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "not authorized!"), 
        @ApiResponse(code = 403, message = "forbidden!!!"),
        @ApiResponse(code = 404, message = "not found!!!") })
    @PostMapping("/posts/like/{userId}/{postId}")
    private String saveLike(@PathVariable(value = "userId") int id, @PathVariable(value = "postId") int postId) {
        User currentUser = userService.getUserById(id);
        Post post = postService.getPostById(postId);
        post.addLike(currentUser);
        logger.info("created a new like");
        postService.saveOrUpdate(post);
        return "like created on post: " + post.getId();
    }

    //creating a delete mapping for removing a post
    @ApiOperation(value = "deletes a like by id", response = String.class, tags = "deleteLike")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "not authorized!"), 
        @ApiResponse(code = 403, message = "forbidden!!!"),
        @ApiResponse(code = 404, message = "not found!!!") })
    @DeleteMapping("/posts/like/{userId}/{postId}")
    private String deleteLike(@PathVariable(value = "userId") int id, @PathVariable(value = "postId") int postId) {
        User currentUser = userService.getUserById(id);
        Post post = postService.getPostById(postId);
        post.removeLike(currentUser);
        logger.info("removed a like");
        postService.saveOrUpdate(post);
        return "Like deleted on post: " + post.getId();
    }

    //creating an update mapping that edits an existing post/comment
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

        LocalDateTime now = LocalDateTime.now();
        post.setDateCreated(now);

        postService.saveOrUpdate(current);
        return "post updated: " + current.getId();
    }

    //creating a get mapping for returning all posts in the db from a specific user
    @ApiOperation(value = "lists all posts by certain user", response = Iterable.class, tags = "getAllPostByUserId")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "not authorized!"), 
        @ApiResponse(code = 403, message = "forbidden!!!"),
        @ApiResponse(code = 404, message = "not found!!!") })
    @GetMapping("/user/{userId}/posts")
    private Set<Post> getAllPostByUserId(@PathVariable("userId") int userId) {
        User user = userService.getUserById(userId);
        logger.info("got all posts from user");
        return user.getPosts();
    }

}
