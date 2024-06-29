package com.onrender.todolistjeed.todolistapi.Filter;

import com.onrender.todolistjeed.todolistapi.Models.UserModel;
import com.onrender.todolistjeed.todolistapi.Repositories.iUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private iUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "https://todolist-jeed.onrender.com");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        String path = request.getRequestURI();

        if ("/user".equals(path)
                || path.startsWith("/user/email-verify")
                || path.startsWith("/user/reset-pass")
                || path.startsWith("/user/task")) {
            filterChain.doFilter(request, response);
            return;
        }

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Basic ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization header missing or not Basic");
            return;
        }

        try {
            String base64Credentials = authorization.substring("Basic".length()).trim();
            byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(decodedBytes, StandardCharsets.UTF_8);
            String[] values = credentials.split(":", 2);
            String username = values[0];
            String password = values[1];

            UserModel user = this.userRepository.findByUsername(username);
            if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
               response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
               return;
            }

            request.setAttribute("authenticatedUser", user);
        } catch (Exception error) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Failed to authenticate");
        }

        filterChain.doFilter(request, response);
    }
}
