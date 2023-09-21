package sp3.com.swagger.docswagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import sp3.com.swagger.docswagger.filtros.FiltroDeTokensJWT;

@SpringBootApplication
public class TesteswaggerApplication {
    @Bean
    public FilterRegistrationBean<FiltroDeTokensJWT> filterJwt() {
        FilterRegistrationBean<FiltroDeTokensJWT> filterRB = new FilterRegistrationBean<FiltroDeTokensJWT>();
        filterRB.setFilter(new FiltroDeTokensJWT());
        filterRB.addUrlPatterns("/v1/auth/*");
        return filterRB;
    }
    public static void main(String[] args) {
        SpringApplication.run(TesteswaggerApplication.class, args);
    }

}
