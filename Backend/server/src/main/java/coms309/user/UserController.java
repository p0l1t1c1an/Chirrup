package coms309.user;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coms309.profile.Profile;
import coms309.settings.StandardSettings;

//creating RestController  
@RequestMapping("/api")
@RestController
public class UserController {
    // autowired the StudentService class
    @Autowired
    UserService userService;

    @GetMapping("/user")
    private List<User> getAllUser() {
        return userService.getAllUser();
    }

    @GetMapping("/user/{id}")
    private User getUser(@PathVariable("id") int id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/user/{id}")
    private void deleteUser(@PathVariable("id") int id) {
        userService.delete(id);
    }

    // creating post mapping that post the student details in the database
    @PostMapping("/user/{id}")
    private int saveUser(@PathVariable("id") int id, @RequestBody User user) {
        User found = userService.getUserById(id);
        found.updateInfo(user);
        userService.saveOrUpdate(found);
        return found.getId();
    }

    // creating post mapping that post the student detail in the database
    @PostMapping("/user")
    private int saveUser(@RequestBody User user) {
        user.setProfile(new Profile(user));
        user.setSettings(new StandardSettings(user));
        userService.saveOrUpdate(user);
        return user.getId();
    }

    // creating post mapping that follows a user
    @PostMapping("/user/{id}/followers/{following}")
    private int followUser(@PathVariable("id") int id, @PathVariable("following") int follow) {
        User follower = userService.getUserById(id);
        User following = userService.getUserById(follow);
        follower.addFollowing(following);
        userService.saveOrUpdate(follower);
        return following.getId();
    }
}
