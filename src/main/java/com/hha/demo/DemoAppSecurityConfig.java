package com.hha.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.hha.demo.security.CustomUserDetailService;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class DemoAppSecurityConfig {

	@Autowired
	private CustomUserDetailService userDetailService;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(c -> c.disable())
				.cors(c -> c.disable())
				.formLogin(form -> form.loginPage("/auth/login"))
				.logout(form -> form.logoutSuccessUrl("/auth/after-logout"))
				.authenticationProvider(authenticationProvider())
				.authorizeHttpRequests(request -> request
													.mvcMatchers("/auth/**","/logout").permitAll()
//													.mvcMatchers("/user/**").hasAuthority("USER")
													.anyRequest().authenticated()
						)
				.build();
	}
}
