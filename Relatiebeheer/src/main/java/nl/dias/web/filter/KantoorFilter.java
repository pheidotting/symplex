package nl.dias.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class KantoorFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(KantoorFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);

        //        StringWriter sw = new StringWriter(10000);
        //        sw.write(servletResponse.getOutputStream());

        LOGGER.debug("{}", ((HttpServletResponse) servletResponse).getOutputStream());
    }

    @Override
    public void destroy() {

    }
}
