package cr.brainstation.bsfinalproject.config;

import cr.brainstation.bsfinalproject.filters.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Spring Boot configuration. Creates the beans for the CORS and jwt filter.
 */
@Configuration
@ComponentScan("cr.brainstation.bsfinalproject")
public class Config {

    private AuthenticationFilter authenticationFilter;

    /**
     * CORS Filter for all kind of requests to any path of the API.
     * @return the filter registration bean
     */
    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }

    /**
     * JWT Filter for all kind of requests to the paths that require authentication in the API.
     * @return the filter registration bean
     */
    @Bean
    public FilterRegistrationBean<AuthenticationFilter> jwtFilter(){
        FilterRegistrationBean<AuthenticationFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(this.authenticationFilter);
        registrationBean.addUrlPatterns("/order");
        registrationBean.addUrlPatterns("/order/product");
        registrationBean.addUrlPatterns("/user");
        registrationBean.addUrlPatterns("/rating");
        registrationBean.addUrlPatterns("/my-rating/*");
        registrationBean.addUrlPatterns("/address/*");
        registrationBean.addUrlPatterns("/payment/*");
        registrationBean.setOrder(1);

        return registrationBean;
    }

    @Autowired
    public void setAuthenticationFilter(AuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }
}
