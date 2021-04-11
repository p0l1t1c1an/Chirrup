package coms309.settings;


import coms309.user.User;
import coms309.user.UserService;

import java.util.List;
import java.util.Set;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @ApiOperation(value = "Get a list of all Settings", response= List.class, tags = "getAllSettings")
    @GetMapping("/settings")
    private List<UserSettings> getAllSettings() {
        return settingsService.getAllSettings();
    }
    
   
    @ApiOperation(value = "Get a specific Settings by id", response= UserSettings.class, tags = "getSettings")
    @GetMapping("/settings/{id}")
    private UserSettings getSettings(@PathVariable("id") int id) {
        return settingsService.getSettingsById(id);
    }

    @ApiOperation(value = "Delete a specific Settings by id", response = void.class, tags = "deleteSettings")
    @DeleteMapping("/settings/{id}")
    private void deleteSettings(@PathVariable("id") int id) {
        settingsService.deleteSettingsById(id);
    }

    @ApiOperation(value = "Edit a specific Settings by id", response = int.class, tags = "editSettings")
    @PutMapping("/settings/{id}")
    private int editSettings(@PathVariable("id") int id, @RequestBody UserSettings s) {
        UserSettings update = settingsService.getSettingsById(id);
        update.updateSettings(s);
        settingsService.saveSettings(update);
        return update.getId();
    }

    @ApiOperation(value = "Modify any amount of values of a specific Settings", response = void.class, tags = "settingsToggle")
    @PutMapping("/settings/{id}/toggle")
    private void settingsToggle(@PathVariable("id") int id, @RequestParam Optional<Boolean> darkMode, @RequestParam Optional<Integer> updateTime,
            @RequestParam Optional<Integer> textSize, @RequestParam Optional<Boolean> locked, @RequestParam Optional<Integer> timeLimit)
    {
        settingsService.modifySettings(id, darkMode, updateTime, textSize, locked, timeLimit);    
    }

    @ApiOperation(value = "Create a Settings", response = int.class, tags = "saveSettings")
    @PostMapping("/settings")
    private int saveSettings(@RequestBody UserSettings s) {
        settingsService.saveSettings(s);
        return s.getId();
    }
   
    @ApiOperation(value = "Get a list of the roles in whitelist", response = List.class, tags = "getRoleWhitelist")
    @GetMapping("/settings/child/{id}/whitelist")
    private List<Integer> getRoleWhitelist(@PathVariable("id") int id) {
        return settingsService.getSettingsById(id).getWhitelist();
    }
   
    @ApiOperation(value = "Add a role to the role Whitelist", response = void.class, tags = "addRole")
    @PutMapping("/settings/child/{id}/whitelist/{role}")
    private void addRole(@PathVariable("id") int id, @PathVariable("role") int role) {
        UserSettings c = settingsService.getSettingsById(id);
        c.addToWhitelist(role);
        settingsService.saveSettings(c);
    }

    @ApiOperation(value = "Remove a role from the Role Whitelist", response = void.class, tags = "removeRole")
    @DeleteMapping("/settings/child/{id}/whitelist/{role}")
    private void removeRole(@PathVariable("id") int id, @PathVariable("role") int role) {
        UserSettings c = settingsService.getSettingsById(id);
        c.removeFromWhitelist(role);
        settingsService.saveSettings(c);
    }

    @ApiOperation(value = "Get a list of children's ids", response = List.class, tags = "getParentsChildren")
    @GetMapping("/settings/parent/{id}/children")
    private List<Integer> getParentsChildren(@PathVariable("id") int id) {
        return settingsService.getChildrenIds(id);
    }

    @ApiOperation(value = "Get a list of parents' ids", response = List.class, tags = "getChildsParents")
    @GetMapping("/settings/child/{id}/parents")
    private List<Integer> getChildsParents(@PathVariable("id") int id) {
        return settingsService.getParentIds(id);
    }
    
    @ApiOperation(value = "Link a Child to a Parent", response = void.class, tags = "addChildToParent")
    @PutMapping("/settings/parent/{id}/child/{child}")
    private void addChildToParent(@PathVariable("id") int id, @PathVariable("child") int child) {
        settingsService.addChildToParent(id, child);    
    }

    
    @ApiOperation(value = "Create a child of linked to a Parent", response = void.class, tags = "createChildOfParent")
    @PostMapping("/settings/parent/{id}/child")
    private void createChildOfParent(@PathVariable("id") int id, @RequestBody User user) {
        settingsService.createChildOfParent(id, user);
    }


    @ApiOperation(value = "Unlink a Child from a Parent", response = void.class, tags = "removeChildFromParent")
    @DeleteMapping("/settings/parent/{id}/child/{child}")
    private void removeChildFromParent(@PathVariable("id") int id, @PathVariable("child") int child) {
        settingsService.removeChildFromParent(id, child);
    }

}
