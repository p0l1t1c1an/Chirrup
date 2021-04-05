package coms309.directmessage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coms309.user.User;
import coms309.user.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "DirectMessageController", description = "REST APIs for directmessage entity")
@RequestMapping("/api")
@RestController
public class DirectMessageController {
    @Autowired
    DirectMessageService dmService;

    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(DirectMessageController.class);

    //creating a get mapping for returning all dms in the db
    @ApiOperation(value = "lists all posts", response = Iterable.class, tags = "getAllDirectMessages")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "not authorized!"), 
        @ApiResponse(code = 403, message = "forbidden!!!"),
        @ApiResponse(code = 404, message = "not found!!!") })
    @GetMapping("/directmessages")
    private List<DirectMessage> getAllDirectMessage() {
        logger.info("got all dms");
        return dmService.getAllDirectMessages();
    }

    //creating a get mapping for returning a specific post
    @ApiOperation(value = "gets a dm by id", response = DirectMessage.class, tags = "getDirectMessage")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "not authorized!"), 
        @ApiResponse(code = 403, message = "forbidden!!!"),
        @ApiResponse(code = 404, message = "not found!!!") })
    @GetMapping("/directmessages/{id}")
    private DirectMessage getDirectMessage(@PathVariable("id") int id) {
        logger.info("got dm by id");
        return dmService.getDirectMessageById(id);
    }

    //creating a delete mapping for removing a post
    @ApiOperation(value = "deletes a dm by id", response = String.class, tags = "deleteDirectMessage")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "not authorized!"), 
        @ApiResponse(code = 403, message = "forbidden!!!"),
        @ApiResponse(code = 404, message = "not found!!!") })
    @DeleteMapping("/directmessages/{id}")
    private String deleteDirectMessage(@PathVariable("id") int id) {
        dmService.delete(id);
        logger.info("deleted dm by id");
        return "DM deleted: " + id; 
    }

    //creating post mapping the creates a new post
    @ApiOperation(value = "creates a new dm", response = String.class, tags = "saveDirectMessage")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "not authorized!"), 
        @ApiResponse(code = 403, message = "forbidden!!!"),
        @ApiResponse(code = 404, message = "not found!!!") })
    @PostMapping("/directmessages/{fromId}/{toId}")
    private String saveDirectMessage(@RequestBody DirectMessage dm, @PathVariable(value = "fromId") int fromId, @PathVariable(value = "toId") int toId) {
        User from = userService.getUserById(fromId);
        User to = userService.getUserById(toId);
        LocalDateTime now = LocalDateTime.now();
        
        dm.setDateSent(now);
        dm.setFrom(from);
        dm.setTo(to);

        dmService.saveOrUpdate(dm);
        
        logger.info("created a new dm");
        return "dm created: " + dm.getId();
    }
}