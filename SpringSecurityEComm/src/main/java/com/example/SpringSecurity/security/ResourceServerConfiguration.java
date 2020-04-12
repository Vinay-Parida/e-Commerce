package com.example.SpringSecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    AppUserDetailsService appUserDetailsService;

    public ResourceServerConfiguration(){
        super();
    }

    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(appUserDetailsService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authenticationProvider;
    }

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder authenticationManagerBuilder){
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").anonymous()
                .antMatchers("/register/customer").anonymous()
                .antMatchers("/register/seller").anonymous()
                .antMatchers("/registrationConfirm").anonymous()
                .antMatchers("/reactivateUser").anonymous()
                .antMatchers("/forgetPassword").anonymous()
                .antMatchers("/resetPassword").anonymous()
                .antMatchers("/admin/customers").hasAnyRole("ADMIN")
                .antMatchers("/admin/sellers").hasAnyRole("ADMIN")
                .antMatchers("/admin/activate/customer").hasAnyRole("ADMIN")
                .antMatchers("/admin/deactivate/customer").hasAnyRole("ADMIN")
                .antMatchers("/admin/activate/seller").hasAnyRole("ADMIN")
                .antMatchers("/admin/deactivate/seller").hasAnyRole("ADMIN")
                .antMatchers("/admin/home").hasAnyRole("ADMIN")
                .antMatchers("/user/home").hasAnyRole("USER")
                .antMatchers("/doLogout").hasAnyRole("ADMIN", "USER")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .csrf()
                .disable();
    }
}
