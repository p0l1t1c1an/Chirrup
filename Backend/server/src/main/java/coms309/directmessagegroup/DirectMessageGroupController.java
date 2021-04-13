package coms309.directmessagegroup;

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

@Api(value = "DirectMessageController", description = "REST APIs for directmessage entity")
@RequestMapping("/api/groups")
@RestController
public class DirectMessageGroupController {
    @Autowired
    DirectMessageGroupService groupService;

    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(DirectMessageGroupController.class);

    @ApiOperation(value = "lists all groups", response = Iterable.class, tags = "getAllGroups")
    @GetMapping
    private List<DirectMessageGroup> getAllGroups() {
        logger.info("got all groups");
        return groupService.getAllGroups();
    }

    @ApiOperation(value = "gets a group by id", response = DirectMessageGroup.class, tags = "getGroup")
    @GetMapping("/{groupId}")
    private DirectMessageGroup getGroup(@PathVariable("groupId") int id) {
        DirectMessageGroup group = groupService.getGroupById(id);
        if(group != null) {
            logger.info("got group by id");
            return groupService.getGroupById(id);
        }

        return null;
    }

    @ApiOperation(value = "delete a group by id", response = String.class, tags = "deleteGroup")
    @DeleteMapping("/{groupId}")
    private String deleteGroup(@PathVariable("groupId") int id) {
        logger.info("deleting group");
        DirectMessageGroup group = groupService.getGroupById(id);
        if(group != null) {
            Set<User> usersInGroup = group.getMembers();

            for (User user : usersInGroup) {
                user.removeFromGroup(group);
                userService.saveOrUpdate(user);
            }

            group.dismissMembers();
            groupService.delete(id);
            return "deleted group: " + id;
        }

        return "group not found";

    }

    @ApiOperation(value = "creates a new group", response = DirectMessageGroup.class, tags = "saveGroup")
    @PostMapping
    private DirectMessageGroup saveGroup(@RequestBody DirectMessageGroup group) {
        logger.info("created a new group");
        groupService.saveOrUpdate(group);
        return group;
    }

    @ApiOperation(value = "adds a member to a group", response = String.class, tags = "addMemberToGroup")
    @PatchMapping("/{groupId}/add/{userId}")
    private String addMemberToGroup(@PathVariable("groupId") int id, @PathVariable("userId") int userId) {
        DirectMessageGroup group = groupService.getGroupById(id);
        User user = userService.getUserById(userId);

        if(group == null) {
            return "Group not found";
        }
        
        if(user == null) {
            return "User not found";
        }

        group.addMemberToGroup(user);
        user.addGroup(group);
        groupService.saveOrUpdate(group);
        return "User " + user.getId() + " added to group " + group.getName();
    }

    @ApiOperation(value = "removes a member from a group", response = String.class, tags = "removeMemberFromGroup")
    @PatchMapping("/{groupId}/remove/{userId}")
    private String removeMemberFromGroup(@PathVariable("groupId") int id, @PathVariable("userId") int userId) {
        DirectMessageGroup group = groupService.getGroupById(id);
        User user = userService.getUserById(userId);

        if(group == null) {
            return "Group not found";
        }
        
        if(user == null) {
            return "User not found";
        }

        group.removeMemberFromGroup(user);
        groupService.saveOrUpdate(group);
        return "User " + user.getId() + " removed from group " + group.getName();
    }
}