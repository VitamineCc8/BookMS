package com.wei.filter;

import javax.servlet.*;
import java.io.IOException;

public class CharacterEncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }


    //    防止乱码问题出现
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        servletRequest.setCharacterEncoding("UTF-8");
//        servletResponse.setCharacterEncoding("UTF-8");
//        filterChain.doFilter(servletRequest, servletResponse);
    }


    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
