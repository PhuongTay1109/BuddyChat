package simple.blog.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import simple.blog.backend.model.Role;

@Repository
public interface RoleRepository extends MongoRepository<Role, Integer> {
    public Role findByAuthority(String authority);
    
} 
