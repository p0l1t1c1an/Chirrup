package coms309.user;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer>  
{
    List<User> findAll();
    User getUserById(int id);
    User saveAndFlush(User entity);
}  
