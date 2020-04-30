package com.example.SpringSecurity.security;

import com.example.SpringSecurity.filters.CustomerFilter;
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
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
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

//                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/admin/customers").hasAnyRole("ADMIN")
                .antMatchers("/admin/sellers").hasAnyRole("ADMIN")
                .antMatchers("/admin/activate/customer").hasAnyRole("ADMIN")
                .antMatchers("/admin/deactivate/customer").hasAnyRole("ADMIN")
                .antMatchers("/admin/activate/seller").hasAnyRole("ADMIN")
                .antMatchers("/admin/deactivate/seller").hasAnyRole("ADMIN")
                .antMatchers("/admin/allCategory").hasAnyRole("ADMIN")
                .antMatchers("/admin/addMetadataField").hasAnyRole("ADMIN")
                .antMatchers("/admin/metadataFields").hasAnyRole("ADMIN")
                .antMatchers("/admin/category").hasAnyRole("ADMIN")
                .antMatchers("/admin/allCategory").hasAnyRole("ADMIN")
                .antMatchers("/admin/updateCategory").hasAnyRole("ADMIN")
                .antMatchers("/admin/addCategoryMetadataField").hasAnyRole("ADMIN")
                .antMatchers("/admin/updateCategoryMetadataField").hasAnyRole("ADMIN")
                .antMatchers("/admin/viewProduct").hasAnyRole("ADMIN")
                .antMatchers("/admin/viewAllProduct").hasAnyRole("ADMIN")
                .antMatchers("/admin/activateProduct").hasAnyRole("ADMIN")
                .antMatchers("/admin/deactivateProduct").hasAnyRole("ADMIN")

                .antMatchers("/uploadImage").hasAnyRole("CUSTOMER", "SELLER")

//                .antMatchers("/customer/**").hasAnyRole("CUSTOMER")
                .antMatchers("/customer/profile").hasAnyRole("CUSTOMER")
                .antMatchers("/customer/addAddress").hasAnyRole("CUSTOMER")
                .antMatchers("/customer/address").hasAnyRole("CUSTOMER")
                .antMatchers("/customer/updateProfile").hasAnyRole("CUSTOMER")
                .antMatchers("/customer/updatePassword").hasAnyRole("CUSTOMER")
                .antMatchers("/customer/deleteAddress").hasAnyRole("CUSTOMER")
                .antMatchers("/customer/updateAddress").hasAnyRole("CUSTOMER")
                .antMatchers("/customer/getAllCategories").hasAnyRole("CUSTOMER")
                .antMatchers("/customer/filterCategory").hasAnyRole("CUSTOMER")
                .antMatchers("/customer/viewProduct").hasAnyRole("CUSTOMER")
                .antMatchers("/customer/viewAllProduct").hasAnyRole("CUSTOMER")
                .antMatchers("/customer/viewSimilarProduct").hasAnyRole("CUSTOMER")

//                .antMatchers("/seller/**").hasAnyRole("SELLER")
                .antMatchers("/seller/profile").hasAnyRole("SELLER")
                .antMatchers("/seller/updatePassword").hasAnyRole("SELLER")
                .antMatchers("/seller/updateAddress").hasAnyRole("SELLER")
                .antMatchers("seller/updateProfile").hasAnyRole("SELLER")
                .antMatchers("/seller/getAllCategories").hasAnyRole("SELLER")
                .antMatchers("/seller/addProduct").hasAnyRole("SELLER")
                .antMatchers("/seller/addProductVariation").hasAnyRole("SELLER")
                .antMatchers("/seller/viewProduct").hasAnyRole("SELLER")
                .antMatchers("/seller/viewProductVariation").hasAnyRole("SELLER")
                .antMatchers("/seller/viewAllProduct").hasAnyRole("SELLER")
                .antMatchers("/seller/viewAllProductVariation").hasAnyRole("SELLER")
                .antMatchers("/seller/deleteProduct").hasAnyRole("SELLER")
                .antMatchers("/seller/updateProduct").hasAnyRole("SELLER")
                .antMatchers("/seller/updateProductVariation").hasAnyRole("SELLER")

//                .antMatchers("/admin/home").hasAnyRole("ADMIN")

                .antMatchers("/doLogout").hasAnyRole("ADMIN", "CUSTOMER", "SELLER")

                .antMatchers("/user/home").hasAnyRole("USER")

                .anyRequest()
                .permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .csrf()
                .disable();
    }
}
