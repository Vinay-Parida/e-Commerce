package com.example.springsecurity.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final Contact DEFAULT_CONTACT = new Contact("Vinay Parida", "https://github.com/Vinay-Parida", "vinay.parida@tothenew.com");
    public static final ApiInfo DEFAULT = new ApiInfo("E Commerce Application",
            "Spring Boot Application for E Commerce Application",
            "1.0", "urn:tos", DEFAULT_CONTACT,
            "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList<VendorExtension>());

    public static final String ACTIVATION_TAG = "Activation Controller";
    public static final String ADMIN_TAG = "Admin Controller";
    public static final String CUSTOMER_TAG = "Customer Controller";
    public static final String SELLER_TAG = "Seller Controller";
    public static final String FORGET_PASSWORD_TAG = "Forgot Password Controller";
    public static final String RESISTER_TAG = "Registration Controller";


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(DEFAULT)
                .tags(new Tag(ACTIVATION_TAG, "Customer can activate and reactivate his account"))
                .tags(new Tag(ADMIN_TAG, "Admin can perform following activities: \n" +
                        "• Activate and deactivate all user and can activate and deactivate user \n" +
                        "• Activate and deactivate product added by seller and can see all products\n" +
                        "• Add category and view a and all category \n" +
                        "• Add category metadata field and metadata field"))
                .tags(new Tag(CUSTOMER_TAG, "Customer can perform following activities: \n" +
                        "• View and update his profile \n" +
                        "• Add, view update and delete his address \n" +
                        "• Update his password \n" +
                        "• Get list of categories and filter out category \n" +
                        "• View a single and all products and also a customer can view similar products"))
                .tags(new Tag(SELLER_TAG, "Seller can perform following activities \n" +
                        "• View and update profile\n" +
                        "• Update Address and Password \n" +
                        "• Get a list of all categories\n" +
                        "• Add, update, delete and view a Product\n" +
                        "• Add, update and view product Variation"))
                .tags(new Tag(FORGET_PASSWORD_TAG, "A user can Forget Password and reset it via token"))
                .tags(new Tag(RESISTER_TAG, "A User can register himself as a seller or a customer"));
    }
    @Bean
    public Docket api(ServletContext servletContext) {
        return new Docket(DocumentationType.SWAGGER_2)
               .securitySchemes(Arrays.asList(apiKey()))
                .securityContexts(Collections.singletonList(securityContext()));
    }
    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.regex("/.*")).build();
    }

    private List<SecurityReference> defaultAuth() {
        final AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
        return Collections.singletonList(new SecurityReference("Bearer", authorizationScopes));
    }

    private ApiKey apiKey() {
        return new ApiKey("Bearer", "Authorization", "header");
    }
}
