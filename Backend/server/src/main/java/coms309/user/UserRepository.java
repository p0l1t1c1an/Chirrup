package coms309.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private static List<User> DB = new ArrayList<>();

    public void insertUser(User user) {
        DB.add(new User(user));
    }

    public List<User> getUsers() {
        return DB;
    }
}