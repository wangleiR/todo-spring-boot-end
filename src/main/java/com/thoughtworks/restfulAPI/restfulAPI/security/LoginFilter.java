package com.thoughtworks.restfulAPI.restfulAPI.security;
import com.thoughtworks.restfulAPI.restfulAPI.model.User;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class LoginFilter  extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null){
            if (header.equals("hehe")){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            User uu=new User(2l,"test","123");
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            uu,"", new ArrayList<>()
                    )
            );
        }
        filterChain.doFilter(request,response);
    }
}
