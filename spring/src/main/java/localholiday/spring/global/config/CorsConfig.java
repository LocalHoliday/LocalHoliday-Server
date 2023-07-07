package localholiday.spring.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        //addMapping : CORS를 적용할 URL 패턴 정의
        //allowedOrigins : 자원 공유를 허락한 Origin 지정
        //allowedMethods : 허용할 HTTP Method 지정
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*");

    }
}
