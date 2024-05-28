package com.iesfranciscodelosrios.security.filters;

import com.iesfranciscodelosrios.model.dto.user.UserAuthenticationDTO;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.security.jwt.JwtUtils;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iesfranciscodelosrios.service.UserDetailServiceImp;
import com.iesfranciscodelosrios.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    JwtUtils jwtUtils;

    /**
     * Constructor de JwtAuthenticationFilter.
     *
     * @param jwtUtils Instancia de JwtUtils.
     */
    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    /**
     * Attempt to authenticate the user.
     *
     * @param request  The received HTTP request.
     * @param response The HTTP response to be sent.
     * @return The resulting authentication.
     * @throws AuthenticationException If authentication fails.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        String email = request.getAttribute("email").toString();
        String uuid = request.getAttribute("uuid").toString();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, uuid);

        return getAuthenticationManager().authenticate(authenticationToken);
    }

    /**
     * Handling of successful authentication.
     *
     * @param request     The received HTTP request.
     * @param response    The HTTP response to be sent.
     * @param chain       The filter chain to which this filter belongs.
     * @param authResult  The authentication result.
     * @throws IOException      If an I/O error occurs.
     * @throws ServletException If a servlet error occurs.
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        User user = (User) authResult.getPrincipal();


        String token = jwtUtils.generateTokenAccess(user.getUsername());

        response.addHeader("Authorization", "Bearer ".concat(token));

        Map<String, Object> httpResponse = new HashMap<>();
        httpResponse.put("token", "Bearer ".concat(token));
        httpResponse.put("message", "Authentication succesfully");
        httpResponse.put("email", user.getUsername());
        httpResponse.put("roles", user.getAuthorities());
        httpResponse.put("id", request.getAttribute("id").toString());
        httpResponse.put("institutionId", request.getAttribute("institutionId").toString());

        response.getWriter().write(new ObjectMapper().writeValueAsString(httpResponse));
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().flush();
    }

    /**
     * Handling of unsuccessful authentication.
     *
     * @param request  The received HTTP request.
     * @param response The HTTP response to be sent.
     * @param failed   The failed authentication exception.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                "Authentication Failed");
    }

}
