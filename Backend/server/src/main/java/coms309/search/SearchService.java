package coms309.search;

import coms309.user.User;
import coms309.user.UserService;

import java.util.ArrayList;  
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Objects;

import org.hibernate.query.criteria.internal.path.SetAttributeJoin;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  

import org.springframework.util.MultiValueMap;

import org.apache.commons.text.similarity.LevenshteinDistance;

@Service  
public class SearchService {
    
    @Autowired  
    UserService userService;

    private static final double MIN_DIFF = 60.0;
   
    // Adding to this means that compareUser function must also be modified
    private static final String[] USER_TAGS = {"user", "first", "last", "role", "phone"};

    private double percentSimilar(String stored, String searched) {
        LevenshteinDistance dist = LevenshteinDistance.getDefaultInstance();
      
        if(stored == null || stored.equals("")) {
            return 0;
        }

        if(searched == null || searched.equals("")) {
            return 100;
        }

        // Percent Similar = # of characters that don't have to change / total chars 
        // Can be negative in case of searched > stored with many cahracters to change
        // Plus one is for small length words to be easier to find (bit of a hack)

        return 100.0 * (stored.length() + 1 - dist.apply(stored, searched)) / stored.length();
    }
    
    private boolean exactSearch(String stored, String searched) { 
        if(searched == null || searched.equals("")) {
            return true;
        }
        
        //System.out.println(stored +" : "+searched);

        return Objects.equals(stored, searched);
    }


    private int tryParseInt(String s, int defaultInt) {
        try {
            return Integer.parseInt(s); 
        } 
        catch (NumberFormatException e) { 
            return defaultInt;
        }
    }

    private boolean compareUser(User u, int tagPos, String param, boolean isExact) {
        boolean ret = false;
        
        switch(tagPos) {
            // Only names can use fuzzy search
            case 0:
                ret = isExact ? exactSearch(u.getUsername(), param) : percentSimilar(u.getUsername(), param) >= MIN_DIFF; 
                break;
            case 1:
                ret = isExact ? exactSearch(u.getFirstname(), param) : percentSimilar(u.getFirstname(), param) >= MIN_DIFF; 
                break;
            case 2:
                ret = isExact ? exactSearch(u.getLastname(), param) : percentSimilar(u.getLastname(), param) >= MIN_DIFF; 
                break;

            // The rest are always exact
            case 3:
                ret = u.getRole() == tryParseInt(param, u.getRole()); //If no role given, then it won't effect search
                break;
            case 4:
                ret = exactSearch(u.getTelephone(), param);
                break;
            default:
                ret = false;
                break;
        }

        //System.out.println(param + " : " + ret);
        return ret;

    }

    public List<User> searchUser(int id, Map<String, String> params, boolean isExact) {
        if(params != null && !params.isEmpty()) {
            List<User> users = userService.getAllUser();
           
            // Remove from search if user is blocking someone
            if(id > 0) {
                User searcher = userService.getUserById(id);
                users = users.stream()
                        .filter(x -> !searcher.getBlocking().contains(x))
                        .collect(Collectors.toList());
            }

            for(int i = 0; i < USER_TAGS.length; ++i) {
                final int iFinal = i;
                users = users.stream() // This is odd and I don't understand why I can't just pass i
                        .filter(x -> compareUser(x, iFinal, params.get(USER_TAGS[iFinal]), isExact))
                        .collect(Collectors.toList());
            }
            return users; 
        }

        return new ArrayList<User>();
    }
    
    public List<User> searchUserOr(int id, MultiValueMap<String, String> params, boolean isExact) {
         if(params != null && !params.isEmpty()) {
            List<User> users = userService.getAllUser();
            
            // Remove from search if user is blocking someone
            if(id > 0) {
                User searcher = userService.getUserById(id);
                users = users.stream()
                        .filter(x -> !searcher.getBlocking().contains(x))
                        .collect(Collectors.toList());
            }

            for(int i = 0; i < USER_TAGS.length; ++i) {
                final int iFinal = i;
                users = users.stream()
                        .filter(x -> {
                            boolean save = false;
                            if(params.get(USER_TAGS[iFinal]) != null) {
                                for(String param : params.get(USER_TAGS[iFinal])) {
                                    save = save || compareUser(x, iFinal, param, isExact); 
                                }
                                return save;
                            }
                            return true;
                        })
                        .collect(Collectors.toList());
            }
            return users; 
        }

        return new ArrayList<User>();
    }

}
