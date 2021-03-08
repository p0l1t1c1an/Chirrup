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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coms309.profile.Profile;

//creating RestController  
@RequestMapping("/api")
@RestController
public class UserController {
    // autowired the StudentService class
    @Autowired
    UserService userService;
    Logger logger = LoggerFactory.getLogger(UserController.class);    

    //creating a get mapping to retrieve all the users in the db
    @GetMapping("/user")
    private List<User> getAllUser() {
        logger.info("got users");
        return userService.getAllUser();
    }

    //creating a get mappping to get a user by an id
    @GetMapping("/user/{id}")
    private User getUser(@PathVariable("id") int id) {
        logger.info("got user");
        return userService.getUserById(id);
    }

    // creating a delete mapping to remove an existing user
    @DeleteMapping("/user/{id}")
    private void deleteUser(@PathVariable("id") int id) {
        logger.info("deleted user");
        userService.delete(id);
    }

    // creating post mapping that edits an existing user
    @PatchMapping("/user/{id}")
    private int saveUser(@PathVariable("id") int id, @RequestBody User user) {
        logger.info("edited user");
        User found = userService.getUserById(id);
        found.updateInfo(user);
        userService.saveOrUpdate(found);
        return found.getId();
    }

    // creating post mapping that creates a new user
    @PostMapping("/user")
    private int saveUser(@RequestBody User user) {
        logger.info("created new user");
        user.setProfile(new Profile(user));
        userService.saveOrUpdate(user);
        return user.getId();
    }

    // creating get mapping that returns who a user is following
    @GetMapping("/user/following/{id}")
    private List<Integer> getFollowing(@PathVariable("id") int id) {
        logger.info("returned followed users");
        // User currentUser = userService.getUserById(id);
        // return currentUser.getFollowing();
        return userService.getFollowingById(id);
    }

    // creating post mapping that follows a user
    @PostMapping("/user/following/{id}/{following}")
    private int followUser(@PathVariable("id") int id, @PathVariable("following") int follow) {
        logger.info("followed user");
        User follower = userService.getUserById(id);
        User following = userService.getUserById(follow);
        follower.addFollowing(following);
        userService.saveOrUpdate(follower);
        return following.getId();
    }
}