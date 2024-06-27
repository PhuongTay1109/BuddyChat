package com.buddy.chat.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.buddy.chat.model.Role;

@Repository
public interface RoleRepository extends MongoRepository<Role, Integer> {
    public Role findByAuthority(String authority);
    
} 
