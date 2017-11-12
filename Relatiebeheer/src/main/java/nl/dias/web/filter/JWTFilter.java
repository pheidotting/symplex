package nl.dias.web.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import nl.dias.repository.GebruikerRepository;
import nl.dias.service.AuthorisatieService;
import nl.dias.service.GebruikerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


@Provider
public class JWTFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JWTFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // Get the HTTP Authorization header from the request
        String authorizationHeader = ((HttpServletRequest) request).getHeader(HttpHeaders.AUTHORIZATION);

        // Extract the token from the HTTP Authorization header
        if (authorizationHeader != null) {
            String token = null;
            try {
                token = authorizationHeader.substring("Bearer".length()).trim();
            } catch (StringIndexOutOfBoundsException e) {
                LOGGER.trace("Niks aan de hand", e);
            }

            if (token != null) {
                try {
                    LOGGER.debug("Evaluating token {}", token);
                    // Validate the token
                    try {
                        Algorithm algorithm = Algorithm.HMAC512("secret");
                        JWTVerifier verifier = JWT.require(algorithm).withIssuer(((HttpServletRequest) request).getContextPath()).build();
                        DecodedJWT jwt = verifier.verify(token);
                    } catch (UnsupportedEncodingException exception) {
                        LOGGER.error("UTF8 fout", exception);
                        //UTF-8 encoding not supported
                        ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    } catch (JWTVerificationException exception) {
                        LOGGER.error("", exception);
                        //Invalid signature/claims
                        ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    }


                    LOGGER.info("#### valid token : " + token);

                    ((HttpServletResponse) response).setHeader(HttpHeaders.AUTHORIZATION, authorizationHeader);

                    filterChain.doFilter(request, response);
                } catch (Exception e) {
                    LOGGER.error("#### invalid token : " + token);
                    LOGGER.trace("Oorspronkelijke fout : ", e);
                    ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
            } else {
                LOGGER.error("#### geen token");
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else {
            LOGGER.error("#### geen token");
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @Override
    public void destroy() {
        LOGGER.debug("destroy filter");
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        LOGGER.debug("init filter");
    }
}
