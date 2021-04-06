package coms309.post;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Integer>  {
    List<Post> findAll();

    Post getPostById(int id);
}
