package com.vaadinblog.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService{
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
          if ("username".equals(username)) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
            User user = new User(username, "password", true, true, false, false, authorities);
            return user;
          } else {
            throw new UsernameNotFoundException("User unknown");
          }
        }
}


