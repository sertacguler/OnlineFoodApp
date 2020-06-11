package com.bilgeadam.onlinefoodapp.jwt;

import com.bilgeadam.onlinefoodapp.domain.Customer;
import com.bilgeadam.onlinefoodapp.service.CustomerService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsCustomerService implements UserDetailsService {

    private final CustomerService customerService;

    public JwtUserDetailsCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> userFromDB = customerService.findByName(username);
        JwtUserDetails jwtUserDetails = null;
        if (userFromDB.isPresent()) {
            jwtUserDetails = new JwtUserDetails(userFromDB.get().getId(),userFromDB.get().getName(),userFromDB.get().getPassword(),"CUSTOMER");
        } else {
            throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username));
        }

        return jwtUserDetails;
    }
}



















