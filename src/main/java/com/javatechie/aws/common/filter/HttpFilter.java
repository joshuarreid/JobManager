package com.javatechie.aws.common.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class HttpFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(HttpFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String username = "";
        try {
            if (httpRequest.getHeader("username") != null)
            username = httpRequest.getHeader("username");
        } catch (Exception e){}

        logger.info("Incoming request: " + username + " " + httpRequest.getMethod() + " " + httpRequest.getRequestURI());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
