package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
//@EnableAspectJAutoProxy //( to make preauthorize work in controller)
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class JwtSecurtiyConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	@Autowired 
	private SecurityAuthenticationEntryPoint entryPoint;
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}
	
	@Bean 
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	}
	
	@Bean
    public JWTAuthenticationFilter authenticationTokenFilterBean() {
        return new JWTAuthenticationFilter();
    }
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/api/signup", "/api/login");
		System.out.println("in ignore block 2>>>>>>>>>>>>>");
		}

	@Override
	//@Order(1) 
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().disable();
		
		http.csrf().disable().logout().disable();
		
		http
		.antMatcher("/api/user")
			.authorizeRequests().anyRequest().hasRole("ADMIN")
		.and()
			.authorizeRequests()
		//.antMatchers("/api/user").hasAuthority("ADMIN")
		//.antMatchers("/api/user").hasRole("ADMIN")
				.antMatchers("/api/signup", "/api/login").permitAll()
		.and()
			.authorizeRequests().anyRequest().authenticated()
		.and()
			.exceptionHandling().authenticationEntryPoint(entryPoint)
		.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http
        .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
		
		http
		.headers().cacheControl();
		
		System.out.println("2>>>>>>>>>>>>>");
		}
	
	@Override
	//@Order(Ordered.HIGHEST_PRECEDENCE)
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordencoder());
		}
	
	@Bean(name="passwordEncoder")
	public PasswordEncoder passwordencoder(){
	    return new BCryptPasswordEncoder();
	}
	 
	/*
	 * @Autowired private SecurityAuthenticationProvider authenticationProvider;
	 * 
	 * @Autowired private SecurityAuthenticationEntryPoint entryPoint;
	 * 
	 * @Bean public SecurityAuthenticationTokenFilter
	 * authenticationTokenFilter() { SecurityAuthenticationTokenFilter filter =
	 * new SecurityAuthenticationTokenFilter();
	 * filter.setAuthenticationManager(authenticationManager());
	 * filter.setAuthenticationSuccessHandler(new SecuritySuccessHandler());
	 * return filter; }
	 */

	/*
	 * @Bean public AuthenticationManager authenticationManager() { return new
	 * ProviderManager(Collections.singletonList(authenticationProvider)); }
	 */

}
