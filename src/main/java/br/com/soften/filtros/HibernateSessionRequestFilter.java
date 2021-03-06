/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.soften.filtros;

import br.com.soften.dao.AbstractDao;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * 
 *
 * @author Renata Rafa
 */
@WebFilter(urlPatterns = {"/*"}, asyncSupported = true)
public class HibernateSessionRequestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        try {
            chain.doFilter(request, response);
        } finally { 
            AbstractDao.getEmClose();
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

}
