package com.iesfranciscodelosrios.security.filters;

import com.iesfranciscodelosrios.model.dto.user.UserAuthenticationDTO;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iesfranciscodelosrios.service.UserDetailServiceImp;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class UserRegisterUuidFilter extends OncePerRequestFilter {

    private final UserDetailServiceImp userDetailsService;

    public UserRegisterUuidFilter(UserDetailServiceImp userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        UserAuthenticationDTO userAuthentication = null;
        String email;
        String uuid;

        try {
            userAuthentication = new ObjectMapper().readValue(request.getInputStream(), UserAuthenticationDTO.class);
            email = userAuthentication.getEmail();
            uuid = userAuthentication.getUuid();

        } catch (StreamReadException e) {
            throw new RuntimeException(e);

        } catch (DatabindException e) {
            throw new RuntimeException(e);

        } catch (IOException e) {
            throw new RuntimeException(e);

        }

        this.userDetailsService.registerUser(email, uuid);

        request.setAttribute("email", email);
        request.setAttribute("uuid", uuid);

        filterChain.doFilter(request, response);
    }
}
