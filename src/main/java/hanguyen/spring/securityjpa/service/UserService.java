package hanguyen.spring.securityjpa.service;

import hanguyen.spring.securityjpa.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService {

    public User findByUserName(String userName);


}
