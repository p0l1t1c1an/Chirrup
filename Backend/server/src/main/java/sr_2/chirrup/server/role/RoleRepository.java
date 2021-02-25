package sr_2.chirrup.server.role;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class RoleRepository {
    private static List<Role> DB = new ArrayList<>();

    public void insertRole(Role role) {
        DB.add(new Role(role.getRole()));
    }

    public List<Role> getRoles() {
        return DB;
    }
}