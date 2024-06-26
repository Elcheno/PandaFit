package com.iesfranciscodelosrios.security.filters;

import com.iesfranciscodelosrios.model.dto.user.UserAuthenticationDTO;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.service.UserDetailServiceImp;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class UserRegisterUuidFilter extends OncePerRequestFilter {

    private final UserDetailServiceImp userDetailsService;

    /**
     * Constructor for initializing the UserRegisterUuidFilter with a UserDetailServiceImp instance.
     *
     * @param userDetailsService The UserDetailServiceImp instance to be used.
     */
    public UserRegisterUuidFilter(UserDetailServiceImp userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Internal method to perform filtering logic for user registration with UUID.
     *
     * @param request      The HTTP request to be filtered.
     * @param response     The HTTP response after filtering.
     * @param filterChain The filter chain to which this filter belongs.
     * @throws ServletException If a servlet error occurs.
     * @throws IOException      If an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        System.out.println(request.getServletPath());
        if (request.getServletPath().equals("/login")) {
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

            UserEntity usuario = this.userDetailsService.findByEmail(email);

            request.setAttribute("email", email);
            request.setAttribute("uuid", uuid);
            request.setAttribute("id", usuario.getId());
            request.setAttribute("institutionId", usuario.getInstitution().getId());
        }

        filterChain.doFilter(request, response);
    }
}
