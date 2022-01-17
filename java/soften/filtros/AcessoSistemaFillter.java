package soften.filtros;

import com.github.adminfaces.starter.infra.security.LogonMB;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "acessoSistemaFilter", urlPatterns = {"/sistema/*"})
public class AcessoSistemaFilter implements Filter {

    @Inject
    LogonMB sessaoMB;

    @Override
    public void init(FilterConfig fc) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        try {

            if (sessaoMB == null || !sessaoMB.isLoggedIn()) {
                res.sendRedirect("/index.soften");
            } else {
                chain.doFilter(request, response);
            }
        } catch (IOException | ServletException e) { 

            res.sendRedirect("/index.soften");
        }

    }

    @Override
    public void destroy() {

    }

}
