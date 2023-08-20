package com.effectivemobile.config;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SimpleCORSFilter implements Filter {
    private static final String HOUR_STRING = "3600";
    private static final String METHODS = "GET, POST, PUT, OPTIONS, DELETE";
    private static final String HEADERS = "Content-Type,Authorization,X-Requested-With,"
            + "Content-Length,Accept,Origin";

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse resp, final FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) resp;

        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, request.getHeader(HttpHeaders.ORIGIN));
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, METHODS);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, HOUR_STRING);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, HEADERS);

        chain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
