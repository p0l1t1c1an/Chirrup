package coms309.role;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer>  
{  
    Role getRoleById(int id);
    List<Role> getAllRole();
    void deleteRoleById(int id);
    void saveOrUpdate(Role role);
}  