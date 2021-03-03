package settings;  
import java.util.ArrayList;  
import java.util.List;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  

@Service  
public class SettingsService {
   
    @Autowired
    SettingsRepository<Settings> settingsRepository;

    @Autowired  
    SettingsRepository<StandardSettings> standardRepository; 

    @Autowired  
    SettingsRepository<ChildSettings> childRepository; 

    @Autowired  
    SettingsRepository<ParentSettings> parentRepository; 

    public List<Settings> getAllSettings() {  
        List<Settings> settings = new ArrayList<Settings>();  
        settingsRepository.findAll().forEach(s -> settings.add(s));  
        return settings;  
    }  

    public List<StandardSettings> getAllStandard() {  
        List<StandardSettings> standard = new ArrayList<StandardSettings>();  
        standardRepository.findAll().forEach(s -> standard.add(s));  
        return standard;  
    } 

    public List<ChildSettings> getAllChildren() {  
        List<ChildSettings> children = new ArrayList<ChildSettings>();  
        childRepository.findAll().forEach(c -> children.add(c));  
        return children;  
    } 

    public List<ParentSettings> getAllParents() {  
        List<ParentSettings> parents = new ArrayList<ParentSettings>();  
        parentRepository.findAll().forEach(p -> parents.add(p));  
        return parents;  
    } 

    public Settings getSettingsById(int id) {  
        return settingsRepository.findById(id).get();  
    }  

    public void saveOrUpdate(Settings s) {  
        settingsRepository.save(s);  
    }  

    public void delete(int id){  
        settingsRepository.deleteById(id);  
    }
}

