package com.poly.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		System.out.println("vao filter truoc");
		// Cấu hình bảo mật
		http.csrf(csrf -> csrf.disable()).sessionManagement(ses -> ses.sessionFixation().none())
				.authorizeHttpRequests(auth ->auth.requestMatchers("/public/**").permitAll()
						.requestMatchers("/checkout/**").authenticated()
						.requestMatchers("/user/**").authenticated()
						.requestMatchers("/admin/**").hasAuthority("ADMIN")
						.anyRequest().authenticated())
			
				.formLogin(login -> login.loginPage("/public/login")
						.failureHandler(new CustomAuthenticationFailureHandler()).loginProcessingUrl("/logon")
						.usernameParameter("username").passwordParameter("password")
						.successHandler(new CustomAuthenticationSuccessHandler()) // Đảm bảo rằng bạn đã thêm dòng này
				)
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/public/login")
						.invalidateHttpSession(true).deleteCookies("JSESSIONID").clearAuthentication(true).permitAll())
				.rememberMe(rememberMe -> rememberMe.key("uniqueAndSecret") // Khóa bí mật cho remember-me
						.tokenValiditySeconds(7200) // Thời gian hiệu lực token (1 ngày)
						.rememberMeParameter("rememberMe")); // Tên tham số cho checkbox "Remember Me"

		return http.build();
	}

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
//		/public/**
		return (web) -> web.ignoring().requestMatchers("/uploads/**","/logoBrand.ico", "/favicon.ico");
	}
}