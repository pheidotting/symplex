package nl.dias.web.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import nl.dias.domein.Gebruiker;
import nl.dias.repository.GebruikerRepository;
import nl.dias.service.LoginService;
import org.joda.time.LocalDateTime;
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
import java.util.Date;


@Provider
public class JWTFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JWTFilter.class);

    private GebruikerRepository gebruikerRepository;
    private LoginService loginService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String url = getFullURL((HttpServletRequest) request);

        if (!url.endsWith(".wav.a") && !url.endsWith(".mp3.a")) {
            String authorizationHeader;
            if (!url.contains("/bijlage/download")) {
                // Get the HTTP Authorization header from the request
                authorizationHeader = ((HttpServletRequest) request).getHeader(HttpHeaders.AUTHORIZATION);
            } else {
                //uit de url halen
                int pos = url.indexOf("&token=");
                authorizationHeader = "Bearer " + url.substring(pos).replace("&token=", "");
            }

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
                        LOGGER.trace("Evaluating token {}", token);
                        // Validate the token
                        try {
                            DecodedJWT decodedJWT = JWT.decode(token);

                            Gebruiker gebruiker = gebruikerRepository.zoekOpIdentificatie(decodedJWT.getSubject());

                            Algorithm algorithm = Algorithm.HMAC512(gebruiker.getSalt());
                            JWTVerifier verifier = JWT.require(algorithm).withIssuer(((HttpServletRequest) request).getRemoteUser()).build();
                            verifier.verify(token);

                            LocalDateTime expireTime = LocalDateTime.now().plusHours(1);
                            token = JWT.create().withSubject(gebruiker.getIdentificatie()).withIssuer(((HttpServletRequest) request).getContextPath()).withIssuedAt(new Date()).withExpiresAt(expireTime.toDate()).sign(algorithm);

                            loginService.nieuwToken(gebruiker.getId(), token);
                        } catch (UnsupportedEncodingException exception) {
                            LOGGER.trace("UTF8 fout", exception);
                            //UTF-8 encoding not supported
                            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
                        } catch (JWTVerificationException exception) {
                            LOGGER.trace("", exception);
                            //Invalid signature/claims
                            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
                        }

                        LOGGER.trace("valid token : " + token);

                        ((HttpServletResponse) response).setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
                    } catch (Exception e) {
                        LOGGER.trace("invalid token : " + token);
                        LOGGER.trace("Oorspronkelijke fout : {}", e);
                        ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    }
                } else {
                    ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
            } else {
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
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
        loginService = WebApplicationContextUtils.
                getRequiredWebApplicationContext(filterConfig.getServletContext()).
                getBean(LoginService.class);
    }


    private String getFullURL(HttpServletRequest request) {
        StringBuilder requestURL = new StringBuilder().append(request.getRequestURL().toString());
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }

}
