package com.hha.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.hha.demo.jwt.JwtAuthenticationEntryPoint;
import com.hha.demo.jwt.JwtAuthenticationFilter;
import com.hha.demo.security.CustomUserDetailService;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class DemoAppSecurityConfig {

	@Autowired
	private CustomUserDetailService userDetailService;
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
//	@Bean
//	DaoAuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//		authenticationProvider.setUserDetailsService(userDetailService);
//		authenticationProvider.setPasswordEncoder(passwordEncoder());
//		return authenticationProvider;
//	}
	
	@Bean
	AuthenticationManager getAuthenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		java.security.Security.addProvider(
		         new org.bouncycastle.jce.provider.BouncyCastleProvider()
		);
		
		return http
				.csrf(c -> c.disable())
				.cors(c -> {
		            CorsConfigurationSource source = res -> {
		                CorsConfiguration config = new CorsConfiguration();
		                config.addAllowedOrigin("*");
		                config.addAllowedHeader("*");
		                config.addAllowedMethod("*");
		                config.addExposedHeader("Authorization");
		                return config;
		            };
		            c.configurationSource(source);
		        })
				.formLogin(form -> form.loginPage("/auth/login"))
				.logout(form -> form.logoutSuccessUrl("/auth/after-logout"))
				.authorizeHttpRequests(request -> request
													.mvcMatchers("/auth/**","/logout", "/api/auth/**").permitAll()
													.mvcMatchers("/users/bootstrap/**").permitAll()
													.anyRequest().authenticated()
						)
				.addFilterBefore(jwtAuthenticationFilter, ExceptionTranslationFilter.class)
				.exceptionHandling(handler -> handler.authenticationEntryPoint(jwtAuthenticationEntryPoint))
				.build();
	}
}
