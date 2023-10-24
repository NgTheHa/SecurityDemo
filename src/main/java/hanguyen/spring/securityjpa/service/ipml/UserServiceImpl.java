package hanguyen.spring.securityjpa.service.ipml;

import hanguyen.spring.securityjpa.entity.Role;
import hanguyen.spring.securityjpa.entity.User;
import hanguyen.spring.securityjpa.repository.RoleRepository;
import hanguyen.spring.securityjpa.repository.UserRepository;
import hanguyen.spring.securityjpa.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void insertUser(){
        User user1 = new User();
        user1.setUserName("ha");
        user1.setPasssword("$2a$12$vSk6fmL.csgSo8.Mydtjx.7dYzlH9exrrME2ZV/wQkZycBnDMGm7i");
        user1.setEnabled(true);

        Role role1 = new Role();
        role1.setName("ROLE_ADMIN");

        Collection<Role> roles = new ArrayList<>();
        roles.add(role1);

        user1.setRoles(roles);
        userRepository.save(user1);
    }


    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user==null){
            throw new UsernameNotFoundException("Invalid username or password!");
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPasssword(), rolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> rolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
