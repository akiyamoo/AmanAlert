package kg.iaau.amanalert.security.config;

import kg.iaau.amanalert.handler.AuthEntryPointJwt;
import kg.iaau.amanalert.security.details.UserDetailsServiceImpl;
import kg.iaau.amanalert.security.filter.CustomAuthenticationProvider;
import kg.iaau.amanalert.security.filter.TokenAuthenticationFilter;
import kg.iaau.amanalert.util.JwtUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class WebSecurityConfig {

    UserDetailsServiceImpl userDetailsService;

    AuthEntryPointJwt unauthorizedHandler;

    JwtUtils jwtUtils;

    private static final String[] PUBLIC_URLS = {
            "/api/auth/**", "/api/test/**", "swagger-ui/**", "/v3/**", "api/news/image/**"
    };

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, CustomAuthenticationProvider authenticationProvider) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager manager) throws Exception {
        http.csrf().disable().httpBasic().disable().cors().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeHttpRequests((requests) -> requests.requestMatchers(HttpMethod.POST, PUBLIC_URLS).permitAll()
                        .requestMatchers(HttpMethod.GET, PUBLIC_URLS).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterAt(new TokenAuthenticationFilter(manager, userDetailsService, jwtUtils), UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider());

        return http.build();
    }
}
