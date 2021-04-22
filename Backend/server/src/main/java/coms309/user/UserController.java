package coms309.user;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import coms309.settings.UserSettings;

//creating RestController  
@Api(value = "UserController", description = "Rest APIs for user entity")
@RequestMapping("/api")
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    HttpServletRequest httpServletRequest;

    Logger logger = LoggerFactory.getLogger(UserController.class);    

    private static String profilePicturePath = "D:/Desktop/"; //"/files/profilepictures/";
    private static String defaultPic = "default_picture.png";

    //creating a get mapping to retrieve all the users in the db
    @ApiOperation(value = "get all users", response = Iterable.class, tags = "getUsers")
    @GetMapping("/user")
    private List<User> getAllUser() {
        logger.info("got users");
        return userService.getAllUser();
    }

    //creating a get mappping to get a user by an id
    @ApiOperation(value = "get user by id", response = User.class, tags = "getUser")
    @GetMapping("/user/{id}")
    private User getUser(@PathVariable("id") int id) {
        logger.info("got user");
        return userService.getUserById(id);
    }

    // creating a delete mapping to remove an existing user
    @ApiOperation(value = "delete user by id", response = String.class, tags = "deleteUser")
    @DeleteMapping("/user/{id}")
    private String deleteUser(@PathVariable("id") int id) {
        logger.info("deleted user");
        userService.delete(id);
        return "User deleted: " + id;
    }

    // creating post mapping that edits an existing user
    @ApiOperation(value = "edit user by id", response = String.class, tags = "editUser")
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
    @GetMapping("/user/{id}/following")
    private List<Integer> getFollowing(@PathVariable("id") int id) {
        logger.info("returned followed users");
        return userService.getFollowingById(id);
    }

    // creating get mapping that returns who a user is being followed by
    @ApiOperation(value = "get users followers by id", response = Iterable.class, tags = "getFollowers")
    @GetMapping("/user/{id}/followers")
    private List<Integer> getFollowers(@PathVariable("id") int id) {
        logger.info("returned followers");
        return userService.getFollowersById(id);
    }

    // creating post mapping that creates a new user
    @ApiOperation(value = "create a new user", response = User.class, tags = "saveUser")
    @PostMapping("/user")
    private User saveUser(@RequestBody User user) {
        logger.info("created new user");
        user.setSettings(new UserSettings(user));
        userService.saveOrUpdate(user);
        return user;
    }

    //creating a get mapping for returning all posts in the db from a specific user
    @ApiOperation(value = "lists all posts by certain user", response = Iterable.class, tags = "getAllPostByUserId")
    @GetMapping("/user/{userId}/posts")
    private List<Integer> getAllPostByUserId(@PathVariable("userId") int userId) {
        User user = userService.getUserById(userId);
        logger.info("got all posts from user");
        return user.getPostsId();
    }

    // creating post mapping that follows a user
    @ApiOperation(value = "follow a user", response = String.class, tags = "followUser")
    @PostMapping("/user/{id}/following/{following}")
    private String followUser(@PathVariable("id") int id, @PathVariable("following") int follow) {
        if(id != follow) {
            User follower = userService.getUserById(id);
            User following = userService.getUserById(follow); 
            if(!follower.getBlocking().contains(following)) {
                follower.addFollowing(following);
                userService.saveOrUpdate(follower);
                logger.info("followed a user");
                return "User is now following: " + following.getUsername();
            }
            return "User can't follow someone they're blocking";
        }
        return "users cant follow themselves";
    }

    // creating delete mapping that unfollows a user
    @ApiOperation(value = "unfollow a user", response = String.class, tags = "unfollowUser")
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

    // creating get mapping that returns who a user is blocking
    @ApiOperation(value = "get users blocking user by id", response = Iterable.class, tags = "getBlocking")
    @GetMapping("/user/{id}/blocking")
    private List<Integer> getBlocking(@PathVariable("id") int id) {
        logger.info("returned blocked users");
        return userService.getBlockingById(id);
    }

    // creating get mapping that returns who a user is being blocked by
    @ApiOperation(value = "get users blockers by id", response = Iterable.class, tags = "getBlockers")
    @GetMapping("/user/{id}/blockers")
    private List<Integer> getBlockers(@PathVariable("id") int id) {
        logger.info("returned blockers");
        return userService.getBlockersById(id);
    }
 
    // creating post mapping that blocks a user
    @ApiOperation(value = "block a user", response = String.class, tags = "blockUser")
    @PostMapping("/user/{id}/blocking/{blocking}")
    private String blockUser(@PathVariable("id") int id, @PathVariable("blocking") int block) {
        if(id != block) {
            User blocker = userService.getUserById(id);
            User blocking = userService.getUserById(block);
            blocker.addBlocking(blocking);
            blocker.removeFollowing(blocking);
            blocking.removeFollower(blocker);
            userService.saveOrUpdate(blocker);
            userService.saveOrUpdate(blocking);
            logger.info("blocked a user");
            return "User is now blocking: " + blocking.getUsername();
        }
        return "users cant block themselves";
    }

    // creating delete mapping that unblocks a user
    @ApiOperation(value = "unblock a user", response = String.class, tags = "unblockUser")
    @DeleteMapping("/user/{id}/blocking/{unblocking}")
    private String unblockUser(@PathVariable("id") int id, @PathVariable("unblocking") int unblock) {
        User blocker = userService.getUserById(id);
        User blocking = userService.getUserById(unblock);
        blocker.removeBlocking(blocking);
        blocking.removeBlocker(blocker);
        userService.saveOrUpdate(blocker);
        userService.saveOrUpdate(blocking);
        logger.info("unblocked a user");

        return "User is now unblocking: " + blocking.getUsername();
    }

    // @ApiOperation(value = "lists all dms by certain user", response = Iterable.class, tags = "getAllDirectMessagesByUserId")
    // @GetMapping("/user/{userId}/directmessages")
    // private List<Integer> getAllDirectMessagesByUserId(@PathVariable("userId") int userId) {
    //     User user = userService.getUserById(userId);
    //     logger.info("got all messages from user");
    //     return user.getMessagesId();
    // }

    //create a mapping for uploading an image for a profile picture
    @ApiOperation(value = "upload profile picture", response = String.class, tags = "uploadProfilePicture")
    @PostMapping("/user/{userId}/profilePicture")
    private String uploadProfilePicture(@PathVariable("userId") int id, @RequestParam("profile_picture") MultipartFile pfp) throws IOException {
        User user = userService.getUserById(id);

        if(pfp.isEmpty()) {
            return "profile picture not given";
        }

        String[] parts = pfp.getOriginalFilename().toString().split("\\.");
        String ext = "im";

        if(parts.length >= 2) {
            ext = parts[parts.length-1];
        }

        String serverFilePath = profilePicturePath;

        serverFilePath += ("profile_pic_" +  id + "." + ext);

        if (serverFilePath != null){        
            File currentPfp = new File(serverFilePath);
            if(currentPfp.exists()) {
                currentPfp.delete();
            }
        }

        logger.info("saving profile picture at: " + serverFilePath);

        File toSave = new File(serverFilePath);
        toSave.createNewFile();

        pfp.transferTo(toSave);
        user.setProfilePicturePath(serverFilePath);
        userService.saveOrUpdate(user);
        return "profile picture changed";
    }

    @ApiOperation(value = "get profile picture", response = String.class, tags = "getProfilePicture")
    @GetMapping(path = "/user/{id}/profilePicture")
    ResponseEntity<Resource> getProfilePicture(@PathVariable int id) throws IOException {
        User user = userService.getUserById(id);

        // if laptop id not found it cannot have an invoice associated with it
        if(user == null) {
            return null;
        }


        if(user.getProfilePicturePath() == null) {
            File file = new File(profilePicturePath + defaultPic);
            String[] splitPath = (profilePicturePath + defaultPic).split("/");
            String fileName = splitPath[splitPath.length-1];
            
            // add headers to state that a file is being downloaded
            HttpHeaders header = new HttpHeaders();
            header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileName);
            header.add("Cache-Control", "no-cache, no-store, must-revalidate");
            header.add("Pragma", "no-cache");
            header.add("Expires", "0");

            // convert the file into bytearrayresource format to send to the front end with the file
            Path path = Paths.get(file.getAbsolutePath());
            ByteArrayResource data = new ByteArrayResource(Files.readAllBytes(path));

            // send the response entity back to the front end with the 
            return ResponseEntity.ok()
                    .headers(header)
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(data);
        }

        File file = new File(user.getProfilePicturePath());

        String[] splitPath = user.getProfilePicturePath().split("/");
        String fileName = splitPath[splitPath.length-1];
        
        // add headers to state that a file is being downloaded
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileName);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        // convert the file into bytearrayresource format to send to the front end with the file
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource data = new ByteArrayResource(Files.readAllBytes(path));

        // send the response entity back to the front end with the 
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(data);
    }

}

