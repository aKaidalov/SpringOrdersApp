package config.security;

import config.security.handlers.ApiAccessDeniedHandler;
import config.security.handlers.ApiEntryPoint;
import config.security.handlers.ApiLogoutSuccessHandler;
import config.security.jwt.JwtAuthenticationFilter;
import config.security.jwt.JwtAuthorizationFilter;
import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@EnableWebSecurity
@EnableMethodSecurity
@PropertySource("classpath:/application.properties")
public class SecurityConfig {

    @Value("${jwt.signing.key}")
    private String jwtKey;

    private final MvcRequestMatcher.Builder mvc;

    public SecurityConfig(HandlerMappingIntrospector introspector) {
        this.mvc = new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.with(new FilterConfigurer(), withDefaults());

        http.formLogin(withDefaults());

        http.authorizeHttpRequests(conf -> conf
                .requestMatchers(mvcMatcher("/home")).permitAll()
                .requestMatchers(mvcMatcher("/admin/**")).hasRole("ADMIN")
                .requestMatchers(mvcMatcher("/**")).authenticated()
                .requestMatchers(antMatcher("/static/**")).permitAll());


//        http.logout(conf -> conf.logoutUrl("/api/logout"));

        http.csrf(AbstractHttpConfigurer::disable);

        http.exceptionHandling(conf -> conf
                .authenticationEntryPoint(new ApiEntryPoint())
                .accessDeniedHandler(new ApiAccessDeniedHandler()));

        http.logout(conf -> conf
                .logoutSuccessHandler(new ApiLogoutSuccessHandler())
                .logoutUrl("/api/logout"));

        return http.build();
    }

    public static class FilterConfigurer extends AbstractHttpConfigurer<FilterConfigurer, HttpSecurity> {

        @Override
        public void configure(HttpSecurity http) {
            AuthenticationManager manager = http.getSharedObject(AuthenticationManager.class);

//            // Use in 1-11 task
//            var loginFilter = new ApiAuthenticationFilter(
//                    manager, "/api/login");


            // Use in 12 task
            var authorizationFilter = new JwtAuthorizationFilter(jwtKey);

            http.addFilterBefore(authorizationFilter,
                    AuthorizationFilter.class);

            var loginFilter = new JwtAuthenticationFilter(
                    manager, "/api/login", jwtKey);

            http.addFilterBefore(loginFilter,
                    UsernamePasswordAuthenticationFilter.class);
        }
    }

    @Bean
    public UserDetailsService userDetailService() {
        UserDetails user = User.builder()
                .username("user")
                .password("$2a$10$CEjgArcc/SVv1UIVfLVwS.7KZQZ6TaQoh13cTr6GCTTIjhee8TPLO") // password: 123
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password("$2a$10$WYpToaEC47cO0MY0mwMqJ.Vs79talyCtFCU7dANjh1n9BYBpu..Ju") // password: 123
                .roles("USER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.debug(false);
    }

    private MvcRequestMatcher mvcMatcher(String pattern) {
        return mvc.pattern(pattern);
    }
}