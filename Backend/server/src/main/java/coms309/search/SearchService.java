package coms309.search;

import coms309.user.User;
import coms309.user.UserService;
import coms309.user.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
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

    @Autowired
    UserRepository userRepository;

    private static final double MIN_DIFF = 60.0;
   
    // Adding to this means that compareUser function must also be modified
    private static final String[] USER_TAGS = {"user", "first", "last", "role", "phone"};

    private double percentSimilar(String stored, String searched, boolean isOr) {
        LevenshteinDistance dist = LevenshteinDistance.getDefaultInstance();
      
        if(stored == null || stored.equals("")) {
            return 0;
        }

        if(searched == null || searched.equals("")) {
            return isOr ? 0 : 100;
        }

        // Percent Similar = # of characters that don't have to change / total chars 
        // Can be negative in case of searched > stored with many cahracters to change
        // Plus one is for small length words to be easier to find (bit of a hack)

        return 100.0 * (stored.length() + 1 - dist.apply(stored, searched)) / stored.length();
    }
    
    private boolean exactSearch(String stored, String searched, boolean isOr) { 
        if(searched == null || searched.equals("")) {
            return !isOr;
        }

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

    private boolean compareUser(User u, int tagPos, String param, boolean isExact, boolean isOr) {
        switch(tagPos) {
            // Only names can use fuzzy search
            case 0:
                return isExact ? 
                    exactSearch(u.getUsername(),  param, isOr) : 
                    percentSimilar(u.getUsername(),  param, isOr) >= MIN_DIFF; 
            case 1:
                return isExact ? 
                    exactSearch(u.getFirstname(), param, isOr) : 
                    percentSimilar(u.getFirstname(), param, isOr) >= MIN_DIFF; 
            case 2:
                return isExact ? 
                    exactSearch(u.getLastname(),  param, isOr) : 
                    percentSimilar(u.getLastname(),  param, isOr) >= MIN_DIFF; 

            // The rest are always exact
            case 3:
                return u.getRole() == tryParseInt(param, isOr ? -1 : u.getRole()); //If no role given, then it won't effect search
            case 4:
                return exactSearch(u.getTelephone(), param, isOr);
            default:
                return !isOr;
        }
    }

    public List<Integer> searchUser(int id, Map<String, String> params, boolean isExact) {
        if(params != null && !params.isEmpty()) {
            List<User> users = userService.getAllUser();
           
            // Remove from search if user is blocking someone
            if(id > 0 && userRepository.existsById(id)) {
                User searcher = userService.getUserById(id);
                users = users.stream()
                        .filter(x -> !searcher.getBlocking().contains(x))
                        .collect(Collectors.toList());
            }

            for(int i = 0; i < USER_TAGS.length; ++i) {
                final int iFinal = i;
                users = users.stream() // This is odd and I don't understand why I can't just pass i
                        .filter(x -> compareUser(x, iFinal, params.get(USER_TAGS[iFinal]), isExact, false))
                        .collect(Collectors.toList());
            }
            return users.stream().map(x -> x.getId()).collect(Collectors.toList()); 
        }

        return new ArrayList<Integer>();
    }
    
    public Set<Integer> searchUserOr(int id, Map<String, String> params, boolean isExact) {
         if(params != null && !params.isEmpty()) {
            List<User> users = userService.getAllUser();
            Set<User> matched = new HashSet<User>();

            // Remove from search if user is blocking someone
            if(id > 0 && userRepository.existsById(id)) {
                User searcher = userService.getUserById(id);
                users = users.stream()
                        .filter(x -> !searcher.getBlocking().contains(x))
                        .collect(Collectors.toList());
            }

            for(int i = 0; i < USER_TAGS.length; ++i) {
                final int iFinal = i;
                matched.addAll(users.stream()
                        .filter(x -> compareUser(x, iFinal, params.get(USER_TAGS[iFinal]), isExact, true))
                        .collect(Collectors.toSet()));
            }
            return matched.stream().map(x -> x.getId()).collect(Collectors.toSet()); 
        }

        return new HashSet<Integer>();
    }

}
