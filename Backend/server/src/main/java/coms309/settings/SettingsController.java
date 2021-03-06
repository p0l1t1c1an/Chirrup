package coms309.settings;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/settings/{id}")
    private Settings getStandard(@PathVariable("id") int id) {
        return settingsService.getStandardById(id);
    }

    @DeleteMapping("/settings/{id}")
    private void deleteSettings(@PathVariable("id") int id) {
        settingsService.deleteStandard(id);
    }

    @PostMapping("/settings/{id}")
    private int saveSettings(@PathVariable("id") int id, @RequestBody StandardSettings s) {
        StandardSettings update = settingsService.getStandardById(id);
        update.setDarkMode(s.getDarkMode());
        update.setTextSize(s.getTextSize());
        update.setUpdateTime(s.getUpdateTime());
        settingsService.saveStandard(update);
        return update.getId();
    }

    @PostMapping("/settings")
    private int saveSettings(@RequestBody StandardSettings s) {
        settingsService.saveStandard(s);
        return s.getId();
    }
}

