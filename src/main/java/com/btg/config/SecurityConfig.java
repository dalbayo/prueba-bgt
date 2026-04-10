package com.btg.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		/*http
	        .cors(cors -> cors.configurationSource(request -> {
	            var config = new org.springframework.web.cors.CorsConfiguration();
	            config.setAllowedOrigins(java.util.List.of("http://localhost:4000")); // El puerto de tu frontend
	            config.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	            config.setAllowedHeaders(java.util.List.of("*"));
	            config.setAllowCredentials(true);
	            return config;
	        }))
	        .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll() // <-- CLAVE: Permite OPTIONS globalmente
	            .requestMatchers("/api/auth/**").permitAll()
	            .anyRequest().authenticated()
	        );*/
//				http
//		        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//		        .csrf(csrf -> csrf.disable())
//		        .authorizeHttpRequests(auth -> auth
//		            .anyRequest().permitAll()
//		        );
//		    return http.build();
		    http
	        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
	        .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth
	            // 1. Permitir acceso pÃºblico a AuthController
	            .requestMatchers("/api/auth/**").permitAll()
	            
	            // 2. Permitir acceso pÃºblico a TestController
	            .requestMatchers("/test/**").permitAll()
	            
	            // 3. Exigir autenticaciÃ³n para CUALQUIER otra ruta
	            .anyRequest().authenticated()
	        );
	    
	    return http.build();
	}
	     

	 
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
	    
	    // ESTA ES LA DIFERENCIA: Usamos "Patterns" en lugar de "Origins"
	    configuration.setAllowedOriginPatterns(Arrays.asList("*")); 
	    
	    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
	    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
	    configuration.setAllowCredentials(true); 
	    
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}
	

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}