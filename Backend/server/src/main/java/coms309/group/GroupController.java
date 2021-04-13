package coms309.group;

import java.util.List;

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

@Api(value = "DirectMessageController", description = "REST APIs for directmessage entity")
@RequestMapping("/api/groups")
@RestController
public class GroupController {
    @Autowired
    GroupService groupService;

    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(GroupController.class);

    @ApiOperation(value = "lists all groups", response = Iterable.class, tags = "getAllGroups")
    @GetMapping
    private List<Group> getAllGroups() {
        logger.info("got all groups");
        return groupService.getAllGroups();
    }

    @ApiOperation(value = "gets a group by id", response = Group.class, tags = "getGroup")
    @GetMapping
    private Group getGroup(@PathVariable("groupId") int id) {
        logger.info("got group by id");
        return groupService.getGroupById(id);
    }

    @ApiOperation(value = "delete a group by id", response = String.class, tags = "deleteGroup")
    @DeleteMapping
    private String deleteGroup(@PathVariable("groupId") int id) {
        logger.info("deleting group");
        groupService.delete(id);
        return "deleted group: " + id;
    }

    @ApiOperation(value = "creates a new group", response = Group.class, tags = "saveGroup")
    @PostMapping
    private Group saveGroup(@RequestBody Group group) {
        logger.info("created a new group");
        groupService.saveOrUpdate(group);
        return group;
    }

    @ApiOperation(value = "adds a member to a group", response = String.class, tags = "addMemberToGroup")
    @PatchMapping("/{groupId}/add/{userId}")
    private String addMemberToGroup(@PathVariable("groupId") int id, @PathVariable("userId") int userId) {
        Group group = groupService.getGroupById(id);
        User user = userService.getUserById(userId);

        if(group == null) {
            return "Group not found";
        }
        
        if(user == null) {
            return "User not found";
        }

        group.addMemberToGroup(user);
        groupService.saveOrUpdate(group);
        return "User " + user.getId() + " added to group " + group.getGroup_name();
    }

    @ApiOperation(value = "removes a member from a group", response = String.class, tags = "removeMemberFromGroup")
    @PatchMapping("/{groupId}/remove/{userId}")
    private String removeMemberFromGroup(@PathVariable("groupId") int id, @PathVariable("userId") int userId) {
        Group group = groupService.getGroupById(id);
        User user = userService.getUserById(userId);

        if(group == null) {
            return "Group not found";
        }
        
        if(user == null) {
            return "User not found";
        }

        group.removeMemberFromGroup(user);
        groupService.saveOrUpdate(group);
        return "User " + user.getId() + " removed from group " + group.getGroup_name();
    }
}