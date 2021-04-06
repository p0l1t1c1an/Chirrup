package coms309.search;

import coms309.user.User;
import coms309.user.UserService;
import coms309.settings.SettingsService;

import java.util.ArrayList;  
import java.util.List;
import java.util.Set;

import org.hibernate.query.criteria.internal.path.SetAttributeJoin;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  

import org.apache.commons.text.similarity.LevenshteinDistance;

@Service  
public class SearchService {
    
    @Autowired  
    UserService userService;

    @Autowired
    SettingsService settingsService;

    private boolean compareSearch(String stored, String searched) {
        LevenshteinDistance dist = LevenshteinDistance.getDefaultInstance();
      
        if(stored == null || stored.equals("")) {
            return false;
        }

        if(searched == null || searched.equals("")) {
            return true;
        }

        // Does data user's string start with the searched strings
        // and are only 5 letters different
        return stored.startsWith(searched.substring(0, 1)) && 
               dist.apply(stored, searched) <= 5;
    }

    public List<User> searchUser(String username, String firstname, String lastname) {
        List<User> matching = new ArrayList<User>(); 
        
        for(User u : userService.getAllUser()) {
            if(compareSearch(u.getUsername(), username) &&
               compareSearch(u.getFirstname(), firstname) &&
               compareSearch(u.getLastname(), lastname))
            {
                matching.add(u);
            }
        }
        return matching;
    }
    
}
