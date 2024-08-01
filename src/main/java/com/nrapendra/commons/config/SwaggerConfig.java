package com.nrapendra.commons.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI customOpenAPI() {

        return new OpenAPI().components(new Components()).info(new Info()
                .title("REST Education backend Api Documentation").version("1.0")
                .termsOfService("Education backend terms of service")
                .description(
                        "This is REST API documentation of the Spring Education backend. If authentication is enabled, when calling the APIs use admin/admin")
                .license(swaggerLicense()).contact(swaggerContact()));
    }

    private Contact swaggerContact() {
        Contact petclinicContact = new Contact();
        petclinicContact.setName("Nrapendra Trivedi");
        petclinicContact.setEmail("triajay259@gmail.com");
        petclinicContact.setUrl("https://github.com/Nrapendra786/TutorialApp");
        return petclinicContact;
    }

    private License swaggerLicense() {
        License petClinicLicense = new License();
        petClinicLicense.setName("Apache 2.0");
        petClinicLicense.setUrl("http://www.apache.org/licenses/LICENSE-2.0");
        petClinicLicense.setExtensions(Collections.emptyMap());
        return petClinicLicense;
    }

}
