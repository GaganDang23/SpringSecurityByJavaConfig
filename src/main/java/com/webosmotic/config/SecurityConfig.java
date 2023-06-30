package com.webosmotic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailservice() {

		UserDetails normalUser = User.withUsername("gagan").password(passwordEncoder().encode("password"))
				.roles("Normal").build();

		UserDetails publicUser = User.withUsername("gagan1").password(passwordEncoder().encode("password"))
				.roles("Public").build();

		UserDetails adminUser = User.withUsername("gagan2").password(passwordEncoder().encode("password"))
				.roles("Admin").build();

		InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager(normalUser, publicUser,
				adminUser);
		return userDetailsManager;

	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable().authorizeHttpRequests().requestMatchers().permitAll().anyRequest().authenticated()
				.and().formLogin();

		return httpSecurity.build();

	}

}
