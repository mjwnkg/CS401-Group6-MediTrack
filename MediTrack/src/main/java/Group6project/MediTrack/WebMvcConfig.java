package Group6project.MediTrack;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import Group6project.MediTrack.controller.AuthValidationController;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthValidationController authValidationController;

    public WebMvcConfig(AuthValidationController authValidationController) {
        this.authValidationController = authValidationController;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authValidationController)
                // Protect everything ...
                .addPathPatterns("/**")
                // ... except the auth pages and static resources
                .excludePathPatterns("/login", "/register", "/css/**", "/js/**", "/images/**");
    }
}
