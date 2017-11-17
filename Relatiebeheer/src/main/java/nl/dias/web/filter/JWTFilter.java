package nl.dias.web.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import nl.dias.domein.Gebruiker;
import nl.dias.repository.GebruikerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

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

    private GebruikerRepository gebruikerRepository;

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
                        DecodedJWT decodedJWT = JWT.decode(token);

                        Gebruiker gebruiker = gebruikerRepository.zoekOpIdentificatie(decodedJWT.getSubject());

                        Algorithm algorithm = Algorithm.HMAC512(gebruiker.getSalt());
                        JWTVerifier verifier = JWT.require(algorithm).withIssuer(((HttpServletRequest) request).getContextPath()).build();
                        verifier.verify(token);
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
                } catch (Exception e) {
                    LOGGER.error("#### invalid token : " + token);
                    LOGGER.trace("Oorspronkelijke fout : {}", e);
                    ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
            } else {
                LOGGER.debug("#### geen token 1");
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else {
            LOGGER.debug("#### geen token 2");
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        LOGGER.debug("destroy filter");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.debug("init filter");
        gebruikerRepository = WebApplicationContextUtils.
                getRequiredWebApplicationContext(filterConfig.getServletContext()).
                getBean(GebruikerRepository.class);
    }
}
