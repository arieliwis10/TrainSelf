package com.duoc.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        // Rutas públicas: login/registro no requieren token
        return path.equals("/") || path.startsWith("/auth/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Token no proporcionado\"}");
            return;
        }

        String token = authHeader.substring(7);
        Claims claims;

        // Solo la VALIDACIÓN del JWT va dentro de este try. Si el token
        // es válido pero algo falla más adelante (ej. el microservicio
        // downstream no responde), esa excepción ya no debe reportarse
        // como "token inválido" — se deja propagar tal cual es.
        try {
            claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            System.out.println("ERROR JWT: " + e.getClass().getName() + " - " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Token invalido o expirado\"}");
            return;
        }

        Number idNumber = claims.get("id", Number.class);
        Long userId = idNumber.longValue();
        String rol = claims.get("rol", String.class);
        String correo = claims.getSubject();

        // Adjuntamos la identidad del usuario a la request para que
        // el GatewayController la reenvíe como headers a los microservicios
        request.setAttribute("userId", userId);
        request.setAttribute("userRol", rol);
        request.setAttribute("userCorreo", correo);

        var authentication = new UsernamePasswordAuthenticationToken(
                correo, null, List.of(new SimpleGrantedAuthority("ROLE_" + rol))
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Fuera del try del JWT: si el GatewayController falla (ej. el
        // microservicio downstream no responde), el error real se propaga
        // sin ser tapado por el catch de validación de token.
        filterChain.doFilter(request, response);
    }
}