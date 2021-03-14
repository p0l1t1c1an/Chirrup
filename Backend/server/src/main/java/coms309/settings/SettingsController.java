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

//creating RestController  
@RequestMapping("/api")
@RestController
public class SettingsController {
    @Autowired
    SettingsService settingsService;

    @GetMapping("/settings")
    private List<StandardSettings> getAllStandard() {
        return settingsService.getAllStandard();
    }
    
    @GetMapping("/settings/child")
    private List<ChildSettings> getAllChildren() {
        return settingsService.getAllChildren();
    }
    
    @GetMapping("/settings/parent")
    private List<ParentSettings> getAllParents() {
        return settingsService.getAllParents();
    }
    
    @GetMapping("/settings/{id}")
    private StandardSettings getStandard(@PathVariable("id") int id) {
        return settingsService.getStandardById(id);
    }

    @GetMapping("/settings/child/{id}")
    private ChildSettings getChild(@PathVariable("id") int id) {
        return settingsService.getChildById(id);
    }

    @GetMapping("/settings/parent/{id}")
    private ParentSettings getParent(@PathVariable("id") int id) {
        return settingsService.getParentById(id);
    }

    @DeleteMapping("/settings/{id}")
    private void deleteStandard(@PathVariable("id") int id) {
        settingsService.deleteStandard(id);
    }

    @DeleteMapping("/settings/child/{id}")
    private void deleteChild(@PathVariable("id") int id) {
        settingsService.deleteChild(id);
    }

    @DeleteMapping("/settings/parent/{id}")
    private void deleteParent(@PathVariable("id") int id) {
        settingsService.deleteParent(id);
    }

    @PutMapping("/settings/{id}")
    private int editStandard(@PathVariable("id") int id, @RequestBody StandardSettings s) {
        StandardSettings update = settingsService.getStandardById(id);
        update.updateSettings(s);
        settingsService.saveStandard(update);
        return update.getId();
    }
 
    @PutMapping("/settings/child/{id}")
    private int editChild(@PathVariable("id") int id, @RequestBody ChildSettings s) {
        ChildSettings update = settingsService.getChildById(id);
        update.updateSettings(s);
        settingsService.saveChild(update);
        return update.getId();
    }

    @PutMapping("/settings/parent/{id}")
    private int editParent(@PathVariable("id") int id, @RequestBody ParentSettings s) {
        ParentSettings update = settingsService.getParentById(id);
        update.updateSettings(s);
        settingsService.saveParent(update);
        return update.getId();
    }

    @PostMapping("/settings")
    private int saveStandard(@RequestBody StandardSettings s) {
        settingsService.saveStandard(s);
        return s.getId();
    }

    
    @PostMapping("/settings/child")
    private int saveChild(@RequestBody ChildSettings s) {
        settingsService.saveChild(s);
        return s.getId();
    }    

    @PostMapping("/settings/parent")
    private int saveParent(@RequestBody ParentSettings s) {
        settingsService.saveParent(s);
        return s.getId();
    }
}
