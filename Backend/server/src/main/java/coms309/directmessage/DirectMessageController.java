package coms309.directmessage;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coms309.directmessagegroup.DirectMessageGroup;
import coms309.directmessagegroup.DirectMessageGroupService;
import coms309.user.User;
import coms309.user.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "DirectMessageController", description = "REST APIs for directmessage entity")
@RequestMapping("/api/directmessages")
@RestController
public class DirectMessageController {
    @Autowired
    DirectMessageService dmService;

    @Autowired
    UserService userService;

    @Autowired
    DirectMessageGroupService groupService;

    Logger logger = LoggerFactory.getLogger(DirectMessageController.class);

    //creating a get mapping for returning all dms in the db
    @ApiOperation(value = "lists all posts", response = Iterable.class, tags = "getAllDirectMessages")

    @GetMapping
    private List<DirectMessage> getAllDirectMessage() {
        logger.info("got all dms");
        return dmService.getAllDirectMessages();
    }

    //creating a get mapping for returning a specific post
    @ApiOperation(value = "gets a dm by id", response = DirectMessage.class, tags = "getDirectMessage")
    @GetMapping("/{id}")
    private DirectMessage getDirectMessage(@PathVariable("id") int id) {
        logger.info("got dm by id");
        return dmService.getDirectMessageById(id);
    }

    //creating a delete mapping for removing a post
    @ApiOperation(value = "deletes a dm by id", response = String.class, tags = "deleteDirectMessage")
    @DeleteMapping("/{id}")
    private String deleteDirectMessage(@PathVariable("id") int id) {
        dmService.delete(id);
        logger.info("deleted dm by id");
        return "DM deleted: " + id; 
    }

    //creating post mapping the creates a new post
    @ApiOperation(value = "creates a new dm", response = String.class, tags = "saveDirectMessage")
    @PostMapping("/{fromId}/{toId}")
    private String saveDirectMessage(@RequestBody DirectMessage dm, @PathVariable(value = "fromId") int fromId, @PathVariable(value = "toId") int toId) {
        User from = userService.getUserById(fromId);
        DirectMessageGroup to = groupService.getGroupById(toId);
        LocalDateTime now = LocalDateTime.now();
        dm.setDateSent(now);
        dm.setFrom(from);
        dm.setTo(to);
        dmService.saveOrUpdate(dm);
        logger.info("created a new dm");
        return "dm created: " + dm.getId();
    }
}