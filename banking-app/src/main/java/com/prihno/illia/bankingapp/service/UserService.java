package com.prihno.illia.bankingapp.service;

import com.prihno.illia.bankingapp.entity.user.User;
import com.prihno.illia.bankingapp.entity.user.details.UserDetails;
import com.prihno.illia.bankingapp.repository.UserDetailsRepository;
import com.prihno.illia.bankingapp.repository.UserRepository;
import com.prihno.illia.bankingapp.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public ApiResponse signUp(User user) {
        ApiResponse response = new ApiResponse(true, "success response");
        return response;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User findByEmail(String userEmail) throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        if (!optionalUser.isPresent()) {
            throw new Exception("user not present");
        }
        User user = optionalUser.get();
        return user;
    }

    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void saveDetails(UserDetails userDetails) {
        userDetailsRepository.save(userDetails);
    }

    public boolean getUsersFromDB(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            return true;
        }
        return false;
    }
}
