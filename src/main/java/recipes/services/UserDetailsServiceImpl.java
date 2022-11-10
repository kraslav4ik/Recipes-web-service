package recipes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.entities.User;
import recipes.repositories.UserRepository;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        List<User> userList = this.userRepository.findByEmail(email);
        System.out.println(userList);

        if (userList.isEmpty()) {
            System.out.println("I DIDNT FIND ANY USERS, THROW AN EXCEPTION TO DAOPROVIDER");
            throw new UsernameNotFoundException(email + " not found");
        }
        return new UserDetailsImpl(userList.get(0));
    }

    public void save(User user) {
        if (!this.userRepository.existsByEmail(user.getEmail())) {
            this.userRepository.save(user);
            return;
        }
        System.out.println("I THINK THAT USER ALREADY EXISTS");
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this email already exists");

    }
}
