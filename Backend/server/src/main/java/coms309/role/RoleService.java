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

    //gets all roles
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }  

    //getting a specific record  
    public Role getRoleById(int id) {  
        return roleRepository.getRoleById(id);  
    }  

    //save or update role
    public void saveOrUpdate(Role role)   
    {  
        roleRepository.save(role);  
    }

    //deleting a specific record  
    public void delete(int id)   
    {  
        roleRepository.deleteRoleById(id);  
    }
}
