package com.example.demo.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class JwtSecurtiyConfig extends WebSecurityConfigurerAdapter {
	@Autowired 
	private SecurityAuthenticationEntryPoint entryPoint;
	
	@Autowired
	private SecurityAuthenticationProvider authenticationProvider;
	
	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(Collections.singletonList(authenticationProvider));
		}
	
	@Bean
	public SecurityAuthenticationTokenFilter  authenticationTokenFilter() {
		SecurityAuthenticationTokenFilter filter =new SecurityAuthenticationTokenFilter();
		filter.setAuthenticationManager(authenticationManager());
		filter.setAuthenticationSuccessHandler(new SecuritySuccessHandler());
		return filter;
		}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/signup", "/login");
		System.out.println("in ignore block 2>>>>>>>>>>>>>");
		}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().disable();
		http.csrf().disable().logout().disable();
		
		http
		.authorizeRequests()
			.antMatchers("/signup", "/login","/token/decode").permitAll()
			.antMatchers("/api/user").hasAuthority("ADMIN")
			.antMatchers("/api/user/{id}","/api/pdf","/api/pdf/{id}").hasAnyAuthority("ADMIN","USER")
			.and()
			.authorizeRequests().anyRequest().authenticated()
		.and()
			.exceptionHandling().authenticationEntryPoint(entryPoint)
		.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http
        .addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		

		System.out.println("2>>>>>>>>>>>>>");
		}
	}
	
/*@Autowired
private CustomUserDetailsService customUserDetailsService;*/
/*@Bean
public UserDetailsService userDetailsService() {
	return new CustomUserDetailsService();
}*/

/*@Bean 
@Override
public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
}*/

/*@Bean
public JWTAuthenticationFilter authenticationTokenFilterBean() {
    return new JWTAuthenticationFilter();
}*/
//		http
//		.antMatcher("/api/**")
//			.authorizeRequests().anyRequest().hasRole("fdghfdgfdgdfgfdg")
//			//.antMatchers("/api/user").hasAuthority("ADMIN")
//			//.antMatchers("/api/user").hasRole("ADMIN")
//		.and()
//			.authorizeRequests()
//				.antMatchers("/api/signup", "/api/login").permitAll()
		/*.and()
			.authorizeRequests().anyRequest().authenticated()
		.and()
			.exceptionHandling().authenticationEntryPoint(entryPoint)
		.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//		
*/		
		
//		http
//		.headers().cacheControl();
		
	
	/*@Override
	//@Order(Ordered.HIGHEST_PRECEDENCE)
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
//		System.out.println(customUserDetailsService.loadUserByUsername("harshit@gmail.com"));
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordencoder());
		}*/
	
	/*@Bean(name="passwordEncoder")
	public PasswordEncoder passwordencoder(){
	    return new BCryptPasswordEncoder();
	}*/


