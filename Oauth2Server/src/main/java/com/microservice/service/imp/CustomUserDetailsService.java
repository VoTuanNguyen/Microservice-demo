package com.microservice.service.imp;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails user = User.withUsername("user").password("password").passwordEncoder(this.passwordEncoder::encode)
				.roles("USER").build();

		UserDetails admin = User.withUsername("admin").password("password")
				.passwordEncoder(this.passwordEncoder::encode).roles("ADMIN").build();

		return Stream.of(user, admin).filter(u -> u.getUsername().equals(username)).findFirst()
				.orElseThrow(() -> new UsernameNotFoundException("couldn't find the username " + username + "!"));
	}
}