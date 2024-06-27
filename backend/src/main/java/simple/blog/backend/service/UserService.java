package simple.blog.backend.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import simple.blog.backend.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    public List<User> findAllUsers();
    public User findUserByUserId(Integer userId);

    @Override
    default UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
    }



}
