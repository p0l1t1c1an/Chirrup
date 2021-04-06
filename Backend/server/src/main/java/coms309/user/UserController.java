package coms309.user;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import coms309.post.Post;
import coms309.settings.UserSettings;

//creating RestController  
@Api(value = "UserController", description = "Rest APIs for user entity")
@RequestMapping("/api")
@RestController
public class UserController {
    // autowired the StudentService class
    @Autowired
    UserService userService;
    Logger logger = LoggerFactory.getLogger(UserController.class);    

    //creating a get mapping to retrieve all the users in the db
    @ApiOperation(value = "get all users", response = Iterable.class, tags = "getUsers")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "not authorized!"), 
        @ApiResponse(code = 403, message = "forbidden!!!"),
        @ApiResponse(code = 404, message = "not found!!!") })
    @GetMapping("/user")
    private List<User> getAllUser() {
        logger.info("got users");
        return userService.getAllUser();
    }

    //creating a get mappping to get a user by an id
    @ApiOperation(value = "get user by id", response = User.class, tags = "getUser")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "not authorized!"), 
        @ApiResponse(code = 403, message = "forbidden!!!"),
        @ApiResponse(code = 404, message = "not found!!!") })
    @GetMapping("/user/{id}")
    private User getUser(@PathVariable("id") int id) {
        logger.info("got user");
        return userService.getUserById(id);
    }

    // creating a delete mapping to remove an existing user
    @ApiOperation(value = "delete user by id", response = String.class, tags = "deleteUser")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "not authorized!"), 
        @ApiResponse(code = 403, message = "forbidden!!!"),
        @ApiResponse(code = 404, message = "not found!!!") })
    @DeleteMapping("/user/{id}")
    private String deleteUser(@PathVariable("id") int id) {
        logger.info("deleted user");
        userService.delete(id);
        return "User deleted: " + id;
    }

    // // creating post mapping that edits an existing user
    // @PatchMapping("/user/{id}")
    // private String saveUser(@PathVariable("id") int id, @RequestBody User user) {
    //     logger.info("edited user");
    //     User found = userService.getUserById(id);
    //     found.updateInfo(user);
    //     userService.saveOrUpdate(found);
    //     return "User edited: " + found.getUsername();
    // }

    // creating post mapping that edits an existing user
    @ApiOperation(value = "edit user by id", response = String.class, tags = "editUser")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "not authorized!"), 
        @ApiResponse(code = 403, message = "forbidden!!!"),
        @ApiResponse(code = 404, message = "not found!!!") })
    @PutMapping("/user/{id}")
    private String editUser(@PathVariable("id") int id, @RequestBody User user) {
        logger.info("edited user");
        User found = userService.getUserById(id);
        found.updateInfo(user);
        userService.saveOrUpdate(found);
        return "User edited: " + found.getUsername();
    }

    // creating post mapping that partially edits an existing user
    @ApiOperation(value = "partially edit a user", response = String.class, tags = "editPartialUser")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "not authorized!"), 
        @ApiResponse(code = 403, message = "forbidden!!!"),
        @ApiResponse(code = 404, message = "not found!!!") })
    @PatchMapping("/user/{id}")
    private String editPartialUser(@PathVariable("id") int id, @RequestBody User user) {
        logger.info("edited user");
        User found = userService.getUserById(id);
        found.updatePartialInfo(user);
        userService.saveOrUpdate(found);
        return "User edited: " + found.getUsername();
    }

    // creating get mapping that returns who a user is following
    @ApiOperation(value = "get users following user by id", response = Iterable.class, tags = "getFollowing")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "not authorized!"), 
        @ApiResponse(code = 403, message = "forbidden!!!"),
        @ApiResponse(code = 404, message = "not found!!!") })
    @GetMapping("/user/{id}/following")
    private List<Integer> getFollowing(@PathVariable("id") int id) {
        logger.info("returned followed users");
        return userService.getFollowingById(id);
    }

    // creating get mapping that returns who a user is being followed by
    @ApiOperation(value = "get users followers by id", response = Iterable.class, tags = "getFollowers")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "not authorized!"), 
        @ApiResponse(code = 403, message = "forbidden!!!"),
        @ApiResponse(code = 404, message = "not found!!!") })
    @GetMapping("/user/{id}/followers")
    private List<Integer> getFollowers(@PathVariable("id") int id) {
        logger.info("returned followers");
        return userService.getFollowersById(id);
    }

    // creating post mapping that creates a new user
    @ApiOperation(value = "create a new user", response = User.class, tags = "saveUser")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "not authorized!"), 
        @ApiResponse(code = 403, message = "forbidden!!!"),
        @ApiResponse(code = 404, message = "not found!!!") })
    @PostMapping("/user")
    private User saveUser(@RequestBody User user) {
        // switch (user.getRole()) {
        //     case 2: 
        //         user.setSettings(new ChildSettings(user));
        //         break;
        //     case 3:
        //         user.setSettings(new ParentSettings(user));
        //         break; 
        //     default:
        //         user.setSettings(new StandardSettings(user));
        //         break;
        // }
        logger.info("created new user");
        user.setSettings(new UserSettings(user));
        userService.saveOrUpdate(user);
        return user;
    }

    //creating a get mapping for returning all posts in the db from a specific user
    @ApiOperation(value = "lists all posts by certain user", response = Iterable.class, tags = "getAllPostByUserId")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "not authorized!"), 
        @ApiResponse(code = 403, message = "forbidden!!!"),
        @ApiResponse(code = 404, message = "not found!!!") })
    @GetMapping("/user/{userId}/posts")
    private List<Integer> getAllPostByUserId(@PathVariable("userId") int userId) {
        User user = userService.getUserById(userId);
        logger.info("got all posts from user");
        return user.getPostsId();
    }

    // creating post mapping that follows a user
    @ApiOperation(value = "follow a user", response = String.class, tags = "followUser")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "not authorized!"), 
        @ApiResponse(code = 403, message = "forbidden!!!"),
        @ApiResponse(code = 404, message = "not found!!!") })
    @PostMapping("/user/{id}/following/{following}")
    private String followUser(@PathVariable("id") int id, @PathVariable("following") int follow) {
        if(id != follow) {
            User follower = userService.getUserById(id);
            User following = userService.getUserById(follow);
            follower.addFollowing(following);
            userService.saveOrUpdate(follower);
            logger.info("followed a user");
            return "User is now following: " + following.getUsername();
        }
        return "users cant follow themselves";
    }

    // creating delete mapping that unfollows a user
    @ApiOperation(value = "unfollow a user", response = String.class, tags = "unfollowUser")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "not authorized!"), 
        @ApiResponse(code = 403, message = "forbidden!!!"),
        @ApiResponse(code = 404, message = "not found!!!") })
    @DeleteMapping("/user/{id}/following/{unfollowing}")
    private String unfollowUser(@PathVariable("id") int id, @PathVariable("unfollowing") int unfollow) {
        User follower = userService.getUserById(id);
        User following = userService.getUserById(unfollow);
        follower.removeFollowing(following);
        following.removeFollower(follower);
        userService.saveOrUpdate(follower);
        userService.saveOrUpdate(following);
        logger.info("unfollowed a user");

        return "User is now unfollowing: " + following.getUsername();
    }

    @ApiOperation(value = "lists all dms by certain user", response = Iterable.class, tags = "getAllDirectMessagesByUserId")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "not authorized!"), 
        @ApiResponse(code = 403, message = "forbidden!!!"),
        @ApiResponse(code = 404, message = "not found!!!") })
    @GetMapping("/user/{userId}/directmessages")
    private List<Integer> getAllDirectMessagesByUserId(@PathVariable("userId") int userId) {
        User user = userService.getUserById(userId);
        logger.info("got all messages from user");
        return user.getMessagesId();
    }
}
