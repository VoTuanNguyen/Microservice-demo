package com.microservice;

import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableResourceServer
@RestController
@EnableDiscoveryClient
public class ResourceServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResourceServerApplication.class, args);
	}
	Map<Integer, Account> accMap = new HashMap<>();

	@RequestMapping("/account")
//	@PreAuthorize("hasRole('ADMIN')")
	private Collection<Account> getAccountDetails() {
		if (ObjectUtils.isEmpty(accMap)) {
			accMap.put(123456, new Account(123456, "saving", "ramu", "SBI"));
			accMap.put(123457, new Account(123457, "saving", "sita", "ICICI"));
			accMap.put(123458, new Account(123458, "current", "ganesh", "HDFC"));
		}

		return accMap.values();
	}

	@Configuration
	@EnableWebSecurity
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
		}
	}

	@RequestMapping("/user")
	@PreAuthorize("@securityService.hasAnyRole('ADMIN')")
	Principal getUser(Principal user) {
		return user;
	}
}
