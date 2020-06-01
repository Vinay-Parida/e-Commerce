package com.example.springsecurity.security;

import com.example.springsecurity.filters.CustomerFilter;
import com.jfilter.EnableJsonFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
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
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@ComponentScan({"com.jfilter.components"})
@EnableJsonFilter
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
        final DaoAuthenticationProvider authenticationProvider = new CustomerFilter();
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

                .antMatchers("/uploadImage").anonymous()

                .antMatchers("/uploadImage").hasAnyRole("CUSTOMER", "SELLER")

                .antMatchers("/admin/**").hasAnyRole("ADMIN")

                .antMatchers("/customer/**").hasAnyRole("CUSTOMER")

                .antMatchers("/seller/**").hasAnyRole("SELLER")

//                .antMatchers("/admin/home").hasAnyRole("ADMIN")

                .antMatchers("/doLogout").hasAnyRole("ADMIN", "CUSTOMER", "SELLER")

                .antMatchers("/user/home").hasAnyRole("USER")

                .anyRequest()
                .permitAll()
                .and()


                //With this config other request are showing unauthorized
//                .formLogin()
//                .loginPage("/login").permitAll()
//                .and()
//                .requestMatchers().antMatchers("/login", "/oauth/authorize", "/oauth/confirm_access")
//                .and()


                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .csrf()
                .disable();
    }
}
