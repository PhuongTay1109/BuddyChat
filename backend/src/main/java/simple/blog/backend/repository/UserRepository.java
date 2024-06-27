package simple.blog.backend.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import simple.blog.backend.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, Integer> {
    public User findByUsername(String username);
    public User findByUserId(Integer userId);
}
