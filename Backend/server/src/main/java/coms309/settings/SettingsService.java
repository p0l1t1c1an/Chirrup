package coms309.settings;  

import java.util.ArrayList;  
import java.util.List;  

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  


@Service  
public class SettingsService {
  
    @Autowired  
    StandardRepository standardRepository; 

    @Autowired  
    ChildRepository childRepository; 

    @Autowired  
    ParentRepository parentRepository; 

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


    public StandardSettings getStandardById(int id) {  
        return standardRepository.findById(id).get();  
    }  
    
    public ChildSettings getChildById(int id) {  
        return childRepository.findById(id).get();  
    }  
    
    public ParentSettings getParentById(int id) {  
        return parentRepository.findById(id).get();  
    }  


    public void saveStandard(StandardSettings s) {  
        standardRepository.save(s);  
    }  
    
    public void saveChild(ChildSettings s) {  
        childRepository.save(s);  
    }  

    public void saveParent(ParentSettings s) {  
        parentRepository.save(s);  
    }  
    

    public void deleteStandard(int id){  
        standardRepository.deleteById(id);  
    }

    public void deleteChild(int id){  
        childRepository.deleteById(id);  
    }

    public void deleteParent(int id){  
        parentRepository.deleteById(id);  
    }

    public ParentSettings standardToParent(int id){
        StandardSettings s = standardRepository.findById(id).get();
        ParentSettings p = new ParentSettings();
        ((StandardSettings) p).updateSettings(s);
        standardRepository.deleteById(id);
        parentRepository.save(p);
        p.getUser().setSettings(p);
        return p;
    }

    public StandardSettings parentToStandard(int id){
        ParentSettings p = parentRepository.findById(id).get();
        StandardSettings s = new StandardSettings();
        s.updateSettings(p);
        parentRepository.deleteById(id);
        standardRepository.save(s);
        s.getUser().setSettings(s);
        return s;
    }

    public StandardSettings childToStandard(int id){
        ChildSettings c = childRepository.findById(id).get();
        StandardSettings s = new StandardSettings();
        s.updateSettings(c);
        childRepository.deleteById(id);
        standardRepository.save(s);
        s.getUser().setSettings(s);
        return s;
    }
}

