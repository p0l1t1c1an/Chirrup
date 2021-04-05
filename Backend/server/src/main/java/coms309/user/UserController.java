package coms309.user;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

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

import coms309.settings.StandardSettings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import coms309.post.Post;
import coms309.settings.ChildSettings;
import coms309.settings.ParentSettings;

//creating RestController  
@Api(value = "UserController", description = "Rest APIs for user entity")
@RequestMapping("/api")
@RestController
public class UserController {
    // autowired the StudentService class
    @Autowired
    UserService userService;

    @Autowired
    HttpServletRequest httpServletRequest;

    Logger logger = LoggerFactory.getLogger(UserController.class);    

    private static String profilePicturePath = "/files/profilepictures/";

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
    private Set<User> getFollowing(@PathVariable("id") int id) {
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
    private Set<User> getFollowers(@PathVariable("id") int id) {
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
        user.setSettings(new StandardSettings(user));
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
    @PostMapping("/user/{id}/followers/{following}")
    private String followUser(@PathVariable("id") int id, @PathVariable("following") int follow) {
        User follower = userService.getUserById(id);
        User following = userService.getUserById(follow);
        follower.addFollowing(following);
        userService.saveOrUpdate(follower);
        logger.info("followed a user");
        return "User is now following: " + following.getUsername();
    }

    //create a mapping for uploading an image for a profile picture
    @ApiOperation(value = "upload profile picture", response = String.class, tags = "uploadProfilePicture")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "not authorized!"), 
        @ApiResponse(code = 403, message = "forbidden!!!"),
        @ApiResponse(code = 404, message = "not found!!!") })
    @PostMapping("/user/{userId}/profilePicture")
    private String uploadProfilePicture(@PathVariable("userId") int id, @RequestParam("profile_picture") MultipartFile pfp) throws IOException {
        User user = userService.getUserById(id);

        if(pfp.isEmpty()) {
            return "profile picture not given";
        }

        String path = "D:/Desktop/"; //httpServletRequest.getServletContext().getRealPath(profilePicturePath);

        if (!new File(path).exists()) {
            new File(path).mkdir();
        }

        String[] parts = pfp.getOriginalFilename().toString().split("\\.");
        String ext = "im";

        if(parts.length >= 2) {
            ext = parts[parts.length-1];
        }

        path += ("profile_pic_" +  id + "." + ext);

        if (path != null){        
            File currentPfp = new File(path);
            if(currentPfp.exists()) {
                currentPfp.delete();
            }
        }

        File toSave = new File(path);
        toSave.createNewFile();

        pfp.transferTo(toSave);
        user.setProfilePicturePath(path);
        userService.saveOrUpdate(user);
        return "profile picture changed";
    }

    @ApiOperation(value = "get profile picture", response = String.class, tags = "getProfilePicture")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "not authorized!"), 
        @ApiResponse(code = 403, message = "forbidden!!!"),
        @ApiResponse(code = 404, message = "not found!!!") })
    @GetMapping(path = "/user/{id}/profilePicture")
    ResponseEntity<Resource> getProfilePicture(@PathVariable int id) throws IOException {
        User user = userService.getUserById(id);

        // if laptop id not found it cannot have an invoice associated with it
        if(user == null) {
            return null;
        }

        // Check the file if it exists and load it into the memory
        File file = new File(user.getProfilePicturePath());
        if(!file.exists()) {
            return null;
        }

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