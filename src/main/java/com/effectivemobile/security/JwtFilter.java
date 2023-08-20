package com.effectivemobile.security;

import com.effectivemobile.service.security.UserSecurityService;
import liquibase.repackaged.org.apache.commons.lang3.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;

@Component
@Slf4j
@AllArgsConstructor
public class JwtFilter extends GenericFilterBean {

    public static final String AUTHORIZATION_KEY = "Authorization";

    private final JwtProvider jwtProvider;

    private final UserSecurityService userDetailService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = getTokenFromRequest((HttpServletRequest) servletRequest);
        if (token != null && jwtProvider.validateToken(token)) {
            String userLogin = jwtProvider.getLoginFromToken(token);
            UserDetails userDetails = userDetailService.loadUserByUsername(userLogin);
            if (userDetails != null) {
                SecurityContextHolder.getContext()
                        .setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION_KEY);
        if (hasText(token)) {
            return StringUtils.removeStart(token, "Bearer").trim();
        }
        return null;
    }
}
