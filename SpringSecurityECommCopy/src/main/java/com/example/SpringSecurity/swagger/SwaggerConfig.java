package com.example.SpringSecurity.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

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


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(DEFAULT)
                .tags(new Tag(ACTIVATION_TAG, "Customer can activate and reactivate his account"))
                .tags(new Tag(ADMIN_TAG, "Admin can activate and deactivate all user and can "));
    }
}
