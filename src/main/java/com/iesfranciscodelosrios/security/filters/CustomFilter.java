package com.iesfranciscodelosrios.security.filters;

import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

public class CustomFilter extends org.springframework.web.filter.OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        Enumeration<String> headerName = request.getHeaderNames();

        System.out.println("----------- HEADERS -----------");
        while (headerName.hasMoreElements()) {
            String name = headerName.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + " : " + value);
        }


        filterChain.doFilter(request, response);
    }
}
