package com.example.FifthSpring.config;

import com.example.FifthSpring.model.Authority;
import com.example.FifthSpring.model.Member;
import com.example.FifthSpring.model.MemberDetails;
import com.example.FifthSpring.repository.AuthorityRepository;
import com.example.FifthSpring.repository.MemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http .authorizeHttpRequests(authorize->authorize
                        .requestMatchers("/",
                                "/article/list",
                                "/article/content",
                                "/article/add",
                                "/api/new-article",
                                "/youtube/**",
                                "/youtube/search/**",
                                "/api/youtube/**",
                                "/api/youtube/search/**",
                                "/map/**","/playlist/**"
                        ,"/swagger", "/swagger-ui.html", "/swagger-ui/**", "/api-docs", "/api-docs/**", "/v3/api-docs/**")
                        .permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()  // 정적 리소스
                        .requestMatchers("/member/**")
                        .hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/signup")
                        .permitAll()
                        .anyRequest().authenticated())
                .httpBasic(withDefaults())
                .formLogin(login->login.loginPage("/login").permitAll())
                .logout(logout->logout.logoutUrl("/logout").permitAll());
        return http.csrf(csrf->csrf.disable()).build();

    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    public UserDetailsService userDetailsServiceEmbeded(MemberRepository memberRepository) {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Member member = memberRepository.findByEmail(username).orElseThrow();
                return new UserDetails() {
                    @Override
                    public Collection<? extends GrantedAuthority> getAuthorities() {
                        return List.of();
                    }

                    @Override
                    public String getPassword() {
                        return member.getPassword();
                    }

                    @Override
                    public String getUsername() {
                        return member.getEmail();
                    }
                };
            }
        };
    }

    @Bean
    public UserDetailsService userDetailsService(MemberRepository memberRepository, AuthorityRepository authorityRepository) {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Member member = memberRepository.findByEmail(username).orElseThrow();
                List<Authority> authorities = authorityRepository.findByMember(member);
                return new MemberDetails(member, authorities);
            }
        };
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return new WebSecurityCustomizer() {
            @Override
            public void customize(WebSecurity web) {
                web.ignoring().requestMatchers(
                        "/css/**",
                        "/js/**",
                        "/image/**",
                        "/health/**",
                        "/actuator/**",
                        "/h2-console/**");
            }
        };
    }
}
