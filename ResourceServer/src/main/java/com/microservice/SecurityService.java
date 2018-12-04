package com.microservice;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SecurityService {

	public boolean hasRole(String role) {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
		
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(authority);
	}
	
	public boolean hasAnyRole(String ...roles) {
		String anyAuthorities = StringUtils.arrayToDelimitedString(roles, ",ROLE_");
		String[] lstRole = ("ROLE_" + anyAuthorities).split(",");
		
		SimpleGrantedAuthority authority;
		
		for(String role : lstRole) {
			authority = new SimpleGrantedAuthority(role);
			if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(authority))
				return true;
		}
		
		return false;
	}
}