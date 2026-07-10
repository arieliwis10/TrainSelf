package com.duoc.gateway;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class GatewayController {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${services.auth.url}")
    private String authUrl;

    @Value("${services.usuarios.url}")
    private String usuariosUrl;

    @Value("${services.rutinas.url}")
    private String rutinasUrl;

    @Value("${services.admin.url}")
    private String adminUrl;

    @Value("${services.leaderboard.url}")
    private String leaderboardUrl;

    @RequestMapping("/auth/**")
    public ResponseEntity<String> proxyAuth(HttpServletRequest request) {
        return forward(request, authUrl);
    }

    @RequestMapping("/usuarios/**")
    public ResponseEntity<String> proxyUsuarios(HttpServletRequest request) {
        return forward(request, usuariosUrl);
    }

    @RequestMapping("/rutinas/**")
    public ResponseEntity<String> proxyRutinas(HttpServletRequest request) {
        return forward(request, rutinasUrl);
    }

    @RequestMapping("/admin/**")
    public ResponseEntity<String> proxyAdmin(HttpServletRequest request) {
        return forward(request, adminUrl);
    }

    @RequestMapping("/leaderboard/**")
    public ResponseEntity<String> proxyLeaderboard(HttpServletRequest request) {
        return forward(request, leaderboardUrl);
    }

    private ResponseEntity<String> forward(HttpServletRequest request, String targetBaseUrl) {
        String path = request.getRequestURI();
        String query = request.getQueryString();
        String targetUrl = targetBaseUrl + path + (query != null ? "?" + query : "");

        HttpHeaders headers = new HttpHeaders();
        var headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            headers.add(name, request.getHeader(name));
        }

        // Identidad extraída del JWT por el JwtAuthFilter
        Object userId = request.getAttribute("userId");
        Object userRol = request.getAttribute("userRol");
        if (userId != null) headers.add("X-User-Id", String.valueOf(userId));
        if (userRol != null) headers.add("X-User-Rol", String.valueOf(userRol));

        String body = null;
        try {
            body = request.getReader().lines().reduce("", (a, b) -> a + b);
        } catch (Exception ignored) {}

        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        HttpMethod method = HttpMethod.valueOf(request.getMethod());

        return restTemplate.exchange(targetUrl, method, entity, String.class);
    }
}