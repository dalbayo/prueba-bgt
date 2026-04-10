package com.btg.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.btg.dto.LoginRequestDto;
import com.btg.dto.TokenResponseDto;
import com.btg.security.jwt.JwtService;
import com.btg.security.jwt.UserDetailsImpl;
import com.btg.service.IUsuarioService; 

@RestController
@RequestMapping("/api/auth")
// @CrossOrigin(origins = "http://localhost:4000")
public class AuthController {
	 
	@Autowired
	private  IUsuarioService usuarioService;

	@Autowired
    private AuthenticationManager authenticationManager;
	@Autowired
    private JwtService jwtService;
	@Autowired
    private UserDetailsService userDetailsService;
	
	@Autowired
    private PasswordEncoder passwordEncoder; // Inyecta el bean de BCrypt
	
    // AquÃƒÂ­ inyectarÃƒÂ­as tu repositorio para guardar el refresh token en SQL Server
    // private final RefreshTokenRepository refreshTokenRepository; 

   

    // 1. LOGIN: Genera ambos tokens por primera vez
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginRequestDto request) {
        // Valida contra SQL Server

        String claveCifrada = passwordEncoder.encode(request.password());
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );
       // LoginRequestDto loginProcesado = new LoginRequestDto(request.username(), claveCifrada);
        
        UserDetails user = userDetailsService.loadUserByUsername(request.username());
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken();
        
        // IMPORTANTE: AquÃƒÂ­ debes persistir el refreshToken en SQL Server asociado al usuario
        // refreshTokenService.save(user.getId(), refreshToken);

        return ResponseEntity.ok(new TokenResponseDto(accessToken, refreshToken));
    }

    // 2. REFRESH: Genera un nuevo Access Token (Cada 10 min)
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDto> refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        // Usamos .map directamente sobre el Optional, sin .get()
        return usuarioService.findByToken(refreshToken)
            .map(usuario -> {
                // 1. Validar expiraciÃƒÂ³n usando Instant (coherente con tu entidad)
            	// Ajustado para LocalDateTime (coherente con el esquema de BD y la entidad)
            	// elimine las siguientes lineas 
//            	if (usuario.getExpiryDate() != null && 
//            	    usuario.getExpiryDate().isBefore(java.time.LocalDateTime.now())) {
//            	    
//            	    throw new RuntimeException("El token de actualizaciÃƒÂ³n ha expirado");
//            	}
                // 2. Generar nuevo Access Token
                UserDetails user = new UserDetails() {
					
					@Override
					public String getUsername() {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public String getPassword() {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public Collection<? extends GrantedAuthority> getAuthorities() {
						// TODO Auto-generated method stub
						return null;
					}
				};

                String newAccessToken = jwtService.generateAccessToken(new UserDetailsImpl(usuario)); 

                // 3. Rotar Refresh Token
                String newRefreshToken = jwtService.generateRefreshToken();
//                usuario.setToken(refreshToken);  // elimine esta linea
                // Definir nueva expiraciÃƒÂ³n (7 dÃƒÂ­as en formato Instant) 
             // Ejemplo de actualizaciÃƒÂ³n a 7 dÃƒÂ­as
//                usuario.setExpiryDate(java.time.LocalDateTime.now().plusDays(7)); // elimine esta linea
                
                usuarioService.save(usuario); // Recomendado usar el servicio en lugar del repositorio

                return ResponseEntity.ok(new TokenResponseDto(newAccessToken, newRefreshToken));
            })
            .orElseThrow(() -> new RuntimeException("Token de actualizaciÃƒÂ³n no encontrado"));
    }
    
}
