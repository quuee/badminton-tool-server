package cn.badminton.tool.config;

import cn.badminton.tool.support.auth.*;
import cn.badminton.tool.tools.WXLoginUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity()
public class SecurityConfiguration {

    @Autowired
    private JWTAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private MyAuthExceptionEntryPoint myAuthExceptionEntryPoint;

    @Autowired
    private WXAppletAuthenticationProvider wxAppletAuthenticationProvider;

    @Autowired
    private WXAuthenticationSuccessHandler wxAuthenticationSuccessHandler;

    @Autowired
    private WXAuthenticationFailureHandler wxAuthenticationFailureHandler;

    @Autowired
    private ObjectMapper jsonMapper;

    @Autowired
    private WXLoginUtil wxUtil;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring()
//                // Spring Security should completely ignore URLs starting with /resources/
//                .requestMatchers("/static/**", "/badminton-images/**");
        return (web -> web.ignoring().requestMatchers("/swagger/**","/swagger-ui.html","/webjars/**","/v3/**","/swagger-resources/**","/doc.html","/static/**"));


    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable).cors(AbstractHttpConfigurer::disable);
        httpSecurity.sessionManagement((sessionManagement) -> {
            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });
        httpSecurity.authorizeHttpRequests((authorizeRequests) -> {
            authorizeRequests.anyRequest().authenticated();

        }).exceptionHandling((exceptionHandling) -> {
            exceptionHandling.authenticationEntryPoint(myAuthExceptionEntryPoint);
        });

        httpSecurity.addFilterAt(wxAppletAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationTokenFilter, WXAppletAuthenticationFilter.class);
//        httpSecurity.authenticationManager(wxAppletAuthenticationManager);

        return httpSecurity.build();
    }

    @Bean
    public WXAppletAuthenticationFilter wxAppletAuthenticationFilter() {
        WXAppletAuthenticationFilter wxAppletAuthenticationFilter = new WXAppletAuthenticationFilter();
        wxAppletAuthenticationFilter.setJsonMapper(jsonMapper);
        wxAppletAuthenticationFilter.setWxLoginUtil(wxUtil);
        wxAppletAuthenticationFilter.setAuthenticationManager(providerManager());
        wxAppletAuthenticationFilter.setAuthenticationSuccessHandler(wxAuthenticationSuccessHandler);
        wxAppletAuthenticationFilter.setAuthenticationFailureHandler(wxAuthenticationFailureHandler);
        return wxAppletAuthenticationFilter;
    }

    @Bean
    protected ProviderManager providerManager() { return new ProviderManager(wxAppletAuthenticationProvider); }




}
