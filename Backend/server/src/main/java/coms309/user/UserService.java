package coms309.user;  
import java.util.ArrayList;  
import java.util.List;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  

@Service  
public class UserService {
    
@Autowired  
UserRepository userRepository;  
    public List<User> getAllUser() {  
        List<User> users = new ArrayList<User>();  
        userRepository.findAll().forEach(user -> users.add(user));  
        return users;  
    }  

    public User getUserById(int id) {  
        return userRepository.findById(id).get();  
    }  

    public void saveOrUpdate(User user)   
    {  
        userRepository.save(user);  
    }  

    public void delete(int id)   
    {  
        userRepository.deleteById(id);  
    }

    public List<Integer> getFollowingById(int id) {
        List<Integer> followingIds = new ArrayList<Integer>();
        User current = this.getUserById(id);
        for (User user : userRepository.findAll()) {
            if(current.getFollowing().contains(user)) {
                followingIds.add(user.getId());
            }
        }
        return followingIds;
    }

    public List<Integer> getFollowersById(int id) {
        List<Integer> followerIds = new ArrayList<Integer>();
        User current = this.getUserById(id);
        for (User user : userRepository.findAll()) {
            if(current.getFollowers().contains(user)) {
                followerIds.add(user.getId());
            }
        }
        return followerIds;
    }
}