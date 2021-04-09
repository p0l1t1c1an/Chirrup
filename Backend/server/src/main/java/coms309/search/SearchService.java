package coms309.search;

import coms309.user.User;
import coms309.user.UserService;
import coms309.settings.SettingsService;

import java.util.ArrayList;  
import java.util.List;
import java.util.Set;
import java.util.Map;

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
               dist.apply(stored, searched) <= 3;
    }

    public List<User> searchUser(Map<String, String> params, boolean isExact) {
        // TODO: Check if map is not null and then if isExact is true
        //      Then, we run with either .equals or my compareSearch
        return null; // so it will compile
    }
    
    public List<User> searchUserOr(Map<String, List<String>> params, boolean isExact) {
      // TODO: Do the same thing as searchUser but muliple values can be sent for the
      //        same tag. Add all matching users into list.
        return null;
    }

}
