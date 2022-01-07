package pl.bgraczyk.githublister.config;

import java.util.function.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import pl.bgraczyk.githublister.config.ApplicationProperties.Developer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.builders.PathSelectors.regex;

@RestController
@Profile("swagger")
public class OpenApiConfiguration {

    private final ApplicationProperties applicationProperties;

    @Autowired
    public OpenApiConfiguration(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @GetMapping(value = "/docs")
    public RedirectView docs() {
        return new RedirectView("/swagger-ui/index.html");
    }

    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(swaggerApiInfo())
            .select()
            .paths(paths())
            .build();
    }

    private ApiInfo swaggerApiInfo() {
        return new ApiInfoBuilder()
            .title(applicationProperties.getTitle())
            .description(applicationProperties.getDescription())
            .version(applicationProperties.getVersion())
            .contact(developerContact())
            .build();
    }

    private Contact developerContact() {
        Developer developer = applicationProperties.getDeveloper();
        return new Contact(developer.getName(), developer.getUrl(), developer.getEmail());
    }

    private Predicate<String> paths() {
        return regex("/api.*");
    }
}
