package coms309.settings;

import java.util.List;
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

    @ApiOperation(value = "Get a list of Standard, Child, and Parent Settings", response= List.class, tags = "getAllStandard")
    @GetMapping("/settings")
    private List<StandardSettings> getAllStandard() {
        return settingsService.getAllStandard();
    }
    
    @ApiOperation(value = "Get a list of Child Settings", response= List.class, tags = "getAllChildren")
    @GetMapping("/settings/child")
    private List<ChildSettings> getAllChildren() {
        return settingsService.getAllChildren();
    }
    
    @ApiOperation(value = "Get a list of Parent Settings", response= List.class, tags = "getAllParents")
    @GetMapping("/settings/parent")
    private List<ParentSettings> getAllParents() {
        return settingsService.getAllParents();
    }
    
    @ApiOperation(value = "Get a specific Standard, Child, or Parent Settings by id", response= StandardSettings.class, tags = "getStandard")
    @GetMapping("/settings/{id}")
    private StandardSettings getStandard(@PathVariable("id") int id) {
        return settingsService.getStandardById(id);
    }

    @ApiOperation(value = "Get a specific Child Settings by id", response= ChildSettings.class, tags = "getChild")
    @GetMapping("/settings/child/{id}")
    private ChildSettings getChild(@PathVariable("id") int id) {
        return settingsService.getChildById(id);
    }

    @ApiOperation(value = "Get a specific Parent Settings by id", response= ParentSettings.class, tags = "getParent")
    @GetMapping("/settings/parent/{id}")
    private ParentSettings getParent(@PathVariable("id") int id) {
        return settingsService.getParentById(id);
    }

    @ApiOperation(value = "Delete a specific Standard, Child, or Parent Settings by id", response = void.class, tags = "deleteStandard")
    @DeleteMapping("/settings/{id}")
    private void deleteStandard(@PathVariable("id") int id) {
        settingsService.deleteStandard(id);
    }

    @ApiOperation(value = "Delete a specific Child Settings by id", response = void.class, tags = "deleteChild")
    @DeleteMapping("/settings/child/{id}")
    private void deleteChild(@PathVariable("id") int id) {
        settingsService.deleteChild(id);
    }

    @ApiOperation(value = "Delete a specific Parent Settings by id", response = void.class, tags = "deleteParent")
    @DeleteMapping("/settings/parent/{id}")
    private void deleteParent(@PathVariable("id") int id) {
        settingsService.deleteParent(id);
    }


    @ApiOperation(value = "Edit a specific Standard, Child, or Parent Settings by id", response = int.class, tags = "editStandard")
    @PutMapping("/settings/{id}")
    private int editStandard(@PathVariable("id") int id, @RequestBody StandardSettings s) {
        StandardSettings update = settingsService.getStandardById(id);
        update.updateSettings(s);
        settingsService.saveStandard(update);
        return update.getId();
    }
 
    @ApiOperation(value = "Edit a specific Child Settings by id", response = int.class, tags = "editChild")
    @PutMapping("/settings/child/{id}")
    private int editChild(@PathVariable("id") int id, @RequestBody ChildSettings s) {
        ChildSettings update = settingsService.getChildById(id);
        update.updateSettings(s);
        settingsService.saveChild(update);
        return update.getId();
    }

    @ApiOperation(value = "Edit a specific Parent Settings by id", response = int.class, tags = "parentStandard")
    @PutMapping("/settings/parent/{id}")
    private int editParent(@PathVariable("id") int id, @RequestBody ParentSettings s) {
        ParentSettings update = settingsService.getParentById(id);
        update.updateSettings(s);
        settingsService.saveParent(update);
        return update.getId();
    }

    @ApiOperation(value = "Save a Standard Settings", response = int.class, tags = "saveStandard")
    @PostMapping("/settings")
    private int saveStandard(@RequestBody StandardSettings s) {
        settingsService.saveStandard(s);
        return s.getId();
    }
 
    @ApiOperation(value = "Save a Child Settings", response = int.class, tags = "saveChild")
    @PostMapping("/settings/child")
    private int saveChild(@RequestBody ChildSettings s) {
        settingsService.saveChild(s);
        return s.getId();
    }    

    @ApiOperation(value = "Save a Parent Settings", response = int.class, tags = "saveParent")
    @PostMapping("/settings/parent")
    private int saveParent(@RequestBody ParentSettings s) {
        settingsService.saveParent(s);
        return s.getId();
    }
}
