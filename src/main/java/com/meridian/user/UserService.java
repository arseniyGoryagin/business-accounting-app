package com.meridian.user;


import com.meridian.exceptions.UserAlreadyRegisteredException;
import com.meridian.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;


    /**
     * Создание нового пользователя
     *
     * @return созданный пользователь
     */
    public User createNewUser(User user){

        if(userRepository.existsByUsername(user.getUsername())){
            throw new UserAlreadyRegisteredException("Such user already exists");
        }
        return userRepository.save(user);
    }


    /**
     * Возвращает пользователя по username
     *
     * @return пользователь
     */
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }




    /**
     * Возвращает пользователя по email
     *
     * @return пользователь
     */
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return getUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No such user"));
            }
        };
    }


    /**
     * Поменять поля пользователя
     *
     * @return пользователь
     */
    public User saveUser(User user) {
        return userRepository.save(user);
    }


    /**
     * Возвращает текущего авторизованного пользователя
     *
     * @return пользователь
     */
    public User getCurrentUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }



}
