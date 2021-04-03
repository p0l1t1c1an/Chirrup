package coms309.settings;  

import java.util.ArrayList;  
import java.util.List;  

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;

import coms309.user.User;
import coms309.user.UserRepository;


@Service  
public class SettingsService {
  
    @Autowired  
    UserSettingsRepository userSettingsRepository; 

    @Autowired
    UserRepository userRepository;

    public List<UserSettings> getAllSettings() {  
        List<UserSettings> list = new ArrayList<UserSettings>();  
        userSettingsRepository.findAll().forEach(s -> list.add(s));  
        return list;  
    } 

    public UserSettings getSettingsById(int id) {  
        return userSettingsRepository.findById(id).get();  
    }  
    
    public void saveSettings(UserSettings s) {  
        userSettingsRepository.save(s);  
    }  

    public void deleteSettingsById(int id){  
        userSettingsRepository.deleteById(id);  
    }
 
     public List<Integer> getBlockedIds(int id){
        List<Integer> blocked = new ArrayList<Integer>();
        for(User b : userSettingsRepository.findById(id).get().getBlocked()) {
            blocked.add(b.getId()); 
        }

        return blocked;
    }

    public List<Integer> getParentIds(int id){
        List<Integer> parents = new ArrayList<Integer>();
        for(UserSettings p : userSettingsRepository.findById(id).get().getParents()) {
            parents.add(p.getId()); 
        }

        return parents;
    }

    public List<Integer> getChildrenIds(int id){
        List<Integer> children = new ArrayList<Integer>();
        for(UserSettings c : userSettingsRepository.findById(id).get().getChildren()) {
            children.add(c.getId()); 
        }

        return children;
    }

    public void addChildToParent(int id, int child) {
        UserSettings parentSettings = userSettingsRepository.findById(id).get();
        UserSettings childSettings = userSettingsRepository.findById(child).get();
        User childAccount = userRepository.findById(child).get();

        parentSettings.addChild(childSettings);
        childSettings.addParent(parentSettings);
        childAccount.setRole(User.CHILD);

        userRepository.save(childAccount);
        userSettingsRepository.save(childSettings);
        userSettingsRepository.save(parentSettings);
    }

    
    public void createChildOfParent(int id, User childAccount) {
        UserSettings parentSettings = userSettingsRepository.findById(id).get();
        User parentAccount = userRepository.findById(id).get();
         
        UserSettings childSettings = new UserSettings(childAccount);

        childAccount.setRole(User.CHILD);
        childAccount.setSettings(childSettings);
        userRepository.save(childAccount);

        parentAccount.setRole(User.PARENT);
        userRepository.save(parentAccount);

        parentSettings.addChild(childSettings);
        childSettings.addParent(parentSettings);
        
        userSettingsRepository.save(childSettings);
        userSettingsRepository.save(parentSettings);
    }


    public void removeChildFromParent(int id, int child) {
        UserSettings parentSettings = userSettingsRepository.findById(id).get();
        User parentAccount = userRepository.findById(id).get();
        
        UserSettings childSettings = userSettingsRepository.findById(child).get();
        User childAccount = userRepository.findById(child).get();

        parentSettings.removeChild(childSettings);
        childSettings.removeParent(parentSettings);
        
        if(childSettings.getParents().isEmpty())
            childAccount.setRole(User.STANDARD);

        if(parentSettings.getChildren().isEmpty())
            parentAccount.setRole(User.STANDARD);

        userRepository.save(childAccount);
        userRepository.save(parentAccount);
        
        userSettingsRepository.save(childSettings);
        userSettingsRepository.save(parentSettings);
    }

}

