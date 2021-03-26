package coms309.user;  
import java.util.ArrayList;  
import java.util.List;
import java.util.Set;

import org.hibernate.query.criteria.internal.path.SetAttributeJoin;
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

    public Set<User> getFollowingById(int id) {
        User current = this.getUserById(id);
        return current.getFollowing();
    }

    public Set<User> getFollowersById(int id) {
        User current = this.getUserById(id);
        return current.getFollowers();
    }
}