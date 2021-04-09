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

    public List<Integer> getFollowingById(int id) {
        User current = this.getUserById(id);
        return current.getFollowingId();
    }

    public List<Integer> getFollowersById(int id) {
        User current = this.getUserById(id);
        return current.getFollowersId();
    }

    public List<Integer> getBlockingById(int id) {
        User current = this.getUserById(id);
        return current.getBlockingId();
    }

    public List<Integer> getBlockersById(int id) {
        User current = this.getUserById(id);
        return current.getBlockersId();
    }
}
