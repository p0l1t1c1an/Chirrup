package coms309.role;  
import java.util.ArrayList;  
import java.util.List;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  

@Service  
public class RoleService   
{  
@Autowired  
RoleRepository roleRepository;  
    public List<Role> getAllRole() {  
        List<Role> roles = new ArrayList<Role>();  
        roleRepository.findAll().forEach(role -> roles.add(role));  
        return roles;  
    }  
    //getting a specific record  
    public Role getRoleById(int id) {  
        return roleRepository.findById(id).get();  
    }  

    public void saveOrUpdate(Role role)   
    {  
        roleRepository.save(role);  
    }  
    //deleting a specific record  
    public void delete(int id)   
    {  
        roleRepository.deleteById(id);  
    }
}