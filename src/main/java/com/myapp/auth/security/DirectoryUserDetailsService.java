package com.myapp.auth.security;

import com.myapp.auth.domain.Person;
import com.myapp.auth.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectoryUserDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            final Person person = personRepository.findByEmailIgnoreCase(username);
            if(person!=null){
                PasswordEncoder encoder = PasswordEncoderFactories
                            .createDelegatingPasswordEncoder();
                String password = encoder.encode(person.getPassword());
                return User.withUsername(person.getEmail())
                    .accountLocked(!person.isEnabled())
                    .password(password)
                    .roles(person.getRole())
                    .build();
            }
        }catch (Exception ex){
            log.error("Error login ",ex);
        }
        throw new UsernameNotFoundException(username);
    }
}
