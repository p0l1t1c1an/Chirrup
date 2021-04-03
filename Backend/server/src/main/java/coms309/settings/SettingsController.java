package coms309.settings;


import coms309.user.User;
import coms309.user.UserService;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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


//creating RestController  
@Api(value = "SettingsController", description="RESTful API for CRUDL operations on Settings")
@RequestMapping("/api")
@RestController
public class SettingsController {
    @Autowired
    SettingsService settingsService;

    @Autowired
    UserService userService;

    @ApiOperation(value = "Get a list of Settings, Child, and Parent Settings", response= List.class, tags = "getAllSettings")
    @GetMapping("/settings")
    private List<UserSettings> getAllSettings() {
        return settingsService.getAllSettings();
    }
    
   
    @ApiOperation(value = "Get a specific Settings, Child, or Parent Settings by id", response= UserSettings.class, tags = "getSettings")
    @GetMapping("/settings/{id}")
    private UserSettings getSettings(@PathVariable("id") int id) {
        return settingsService.getSettingsById(id);
    }

    @ApiOperation(value = "Delete a specific Settings, Child, or Parent Settings by id", response = void.class, tags = "deleteSettings")
    @DeleteMapping("/settings/{id}")
    private void deleteSettings(@PathVariable("id") int id) {
        settingsService.deleteSettingsById(id);
    }

    @ApiOperation(value = "Edit a specific Settings, Child, or Parent Settings by id", response = int.class, tags = "editSettings")
    @PutMapping("/settings/{id}")
    private int editSettings(@PathVariable("id") int id, @RequestBody UserSettings s) {
        UserSettings update = settingsService.getSettingsById(id);
        update.updateSettings(s);
        settingsService.saveSettings(update);
        return update.getId();
    }
 
    @ApiOperation(value = "Save a Settings Settings", response = int.class, tags = "saveSettings")
    @PostMapping("/settings")
    private int saveSettings(@RequestBody UserSettings s) {
        settingsService.saveSettings(s);
        return s.getId();
    }
 
    @GetMapping("/settings/{id}/blocked/ids")
    private List<Integer> getBlockedUserIds(@PathVariable("id") int id) {
        return settingsService.getBlockedIds(id);
    }

    @GetMapping("/settings/{id}/blocked")
    private Set<User> getBlockedUsers(@PathVariable("id") int id) {
        return settingsService.getSettingsById(id).getBlocked();
    }

    
    @PutMapping("/settings/{id}/block/{blocked}")
    private void blockUser(@PathVariable("id") int id, @PathVariable("blocked") int blocked) {
        UserSettings s =  settingsService.getSettingsById(id);
        s.addBlocked(userService.getUserById(blocked));
        settingsService.saveSettings(s);
    }


    @DeleteMapping("/settings/{id}/unblock/{unblocked}")
    private void unblockUser(@PathVariable("id") int id, @PathVariable("unblocked") int unblocked) {
        UserSettings s = settingsService.getSettingsById(id);
        s.removeBlocked(userService.getUserById(unblocked));
        settingsService.saveSettings(s);
    }

 
    @GetMapping("/settings/child/{id}/whitelist")
    private List<Integer> getIntegerWhitelist(@PathVariable("id") int id) {
        return settingsService.getSettingsById(id).getWhitelist();
    }

    
    @PutMapping("/settings/child/{id}/whitelist/{role}")
    private void addInteger(@PathVariable("id") int id, @PathVariable("role") int role) {
        UserSettings c = settingsService.getSettingsById(id);
        c.addToWhitelist(role);
        settingsService.saveSettings(c);
    }


    @DeleteMapping("/settings/child/{id}/whitelist/{role}")
    private void removeInteger(@PathVariable("id") int id, @PathVariable("role") int role) {
        UserSettings c = settingsService.getSettingsById(id);
        c.removeFromWhitelist(role);
        settingsService.saveSettings(c);
    }

    @GetMapping("/settings/parent/{id}/children")
    private List<Integer> getParentsChildren(@PathVariable("id") int id) {
        return settingsService.getChildrenIds(id);
    }

    @GetMapping("/settings/child/{id}/parents")
    private List<Integer> getChildsParents(@PathVariable("id") int id) {
        return settingsService.getParentIds(id);
    }
    
    @PutMapping("/settings/parent/{id}/child/{child}")
    private void addChildToParent(@PathVariable("id") int id, @PathVariable("child") int child) {
        settingsService.addChildToParent(id, child);    
    }

    
    @PostMapping("/settings/parent/{id}/child")
    private void createChildOfParent(@PathVariable("id") int id, @RequestBody User user) {
        settingsService.createChildOfParent(id, user);
    }


    @DeleteMapping("/settings/parent/{id}/child/{child}")
    private void removeChildFromParent(@PathVariable("id") int id, @PathVariable("child") int child) {
        settingsService.removeChildFromParent(id, child);
    }

}
