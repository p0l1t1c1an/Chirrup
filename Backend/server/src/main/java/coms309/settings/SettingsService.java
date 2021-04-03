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

}

