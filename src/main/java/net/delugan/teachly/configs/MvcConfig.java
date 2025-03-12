package net.delugan.teachly.configs;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.filter.UrlHandlerFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * Configuration class for Spring MVC.
 * Configures resource handlers, template resolvers, and URL handling.
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    /**
     * Configures resource handlers for static resources.
     * Ensures that requests to /static/** are handled correctly.
     *
     * @param registry The ResourceHandlerRegistry to configure
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/static/**")) {
            registry.addResourceHandler("/static/**").addResourceLocations("/static/");
        }
    }

    /**
     * Creates a template resolver for Thymeleaf templates.
     * Configures the template location, suffix, mode, and caching.
     *
     * @return The configured SpringResourceTemplateResolver
     */
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(true);
        return templateResolver;
    }

    /**
     * Creates a template engine for Thymeleaf templates.
     * Configures the template resolver and enables Spring EL compiler.
     *
     * @return The configured SpringTemplateEngine
     */
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    /**
     * Creates a filter registration bean for URL handling.
     * Configures a trailing slash handler for dashboard URLs.
     *
     * @return The configured FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean<OncePerRequestFilter> urlHandlerFilterRegistrationBean() {
        FilterRegistrationBean<OncePerRequestFilter> registrationBean = new FilterRegistrationBean<>();
        UrlHandlerFilter urlHandlerFilter = UrlHandlerFilter.trailingSlashHandler("/dashboard/**").
                redirect(HttpStatus.PERMANENT_REDIRECT).
                build();
        registrationBean.setFilter(urlHandlerFilter);

        return registrationBean;
    }
}
