package nl.dias.web.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import nl.dias.domein.Gebruiker;
import nl.dias.domein.Medewerker;
import nl.dias.domein.Relatie;
import nl.dias.service.GebruikerService;
import nl.lakedigital.loginsystem.exception.NietGevondenException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.ws.rs.core.HttpHeaders;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class KantoorFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(KantoorFilter.class);

    private FilterConfig filterConfig = null;
    private GebruikerService gebruikerService;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        CharResponseWrapper wrappedResponse = new CharResponseWrapper((HttpServletResponse) response);

        chain.doFilter(request, wrappedResponse);
        byte[] bytes = wrappedResponse.getByteArray();

        String responseString = new String(bytes);

        if (getFullURL((HttpServletRequest) request).contains("/relatie/lees/")) {
            nl.lakedigital.djfc.domain.response.Relatie relatie = (nl.lakedigital.djfc.domain.response.Relatie) mapVanJson(responseString, nl.lakedigital.djfc.domain.response.Relatie.class);

            Medewerker ingelogdeGebruiker = (Medewerker) getIngelogdeGebruiker((HttpServletRequest) request);

            if (relatie == null || relatie.getKantoor() != ingelogdeGebruiker.getKantoor().getId()) {
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
                responseString = "";
            }
        } else if (getFullURL((HttpServletRequest) request).contains("/bedrijf/lees/")) {
            nl.lakedigital.djfc.domain.response.Bedrijf bedrijf = (nl.lakedigital.djfc.domain.response.Bedrijf) mapVanJson(responseString, nl.lakedigital.djfc.domain.response.Bedrijf.class);

            Medewerker ingelogdeGebruiker = (Medewerker) getIngelogdeGebruiker((HttpServletRequest) request);

            if (bedrijf != null || bedrijf.getKantoor() != ingelogdeGebruiker.getKantoor().getId()) {
                    ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    responseString = "";
            }
        }

        response.getOutputStream().write(responseString.getBytes());
    }

    private Object mapVanJson(String jsonString, Class clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            LOGGER.error("Foutmelding in mapVanJson {}", e);
        }
        return null;
    }

    protected Gebruiker getIngelogdeGebruiker(HttpServletRequest httpServletRequest) {
        String authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        // Extract the token from the HTTP Authorization header
        String token = null;
        if (authorizationHeader != null) {
            try {
                token = authorizationHeader.substring("Bearer".length()).trim();
            } catch (StringIndexOutOfBoundsException e) {
                LOGGER.trace("Niks aan de hand", e);
            }
        }
        if (token != null) {
            DecodedJWT decodedJWT = JWT.decode(token);

            try {
                return gebruikerService.zoekOpIdentificatie(decodedJWT.getSubject());
            } catch (NietGevondenException nge) {
                LOGGER.error("Net gevonden : {}", decodedJWT.getSubject());
                LOGGER.trace("Bijbehorende error {}", nge);
                Gebruiker gebruiker = new Relatie();
                gebruiker.setId(0L);
                return gebruiker;
            }
        }
        return null;
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

    private static class ByteArrayServletStream extends ServletOutputStream {
        ByteArrayOutputStream baos;

        ByteArrayServletStream(ByteArrayOutputStream baos) {
            this.baos = baos;
        }

        public void write(int param) throws IOException {
            baos.write(param);
        }
    }

    private static class ByteArrayPrintWriter {

        private ByteArrayOutputStream baos = new ByteArrayOutputStream();

        private PrintWriter pw = new PrintWriter(baos);

        private ServletOutputStream sos = new ByteArrayServletStream(baos);

        public PrintWriter getWriter() {
            return pw;
        }

        public ServletOutputStream getStream() {
            return sos;
        }

        byte[] toByteArray() {
            return baos.toByteArray();
        }
    }

    public class CharResponseWrapper extends HttpServletResponseWrapper {
        private ByteArrayPrintWriter output;
        private boolean usingWriter;

        public CharResponseWrapper(HttpServletResponse response) {
            super(response);
            usingWriter = false;
            output = new ByteArrayPrintWriter();
        }

        public byte[] getByteArray() {
            return output.toByteArray();
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            // will error out, if in use
            if (usingWriter) {
                super.getOutputStream();
            }
            usingWriter = true;
            return output.getStream();
        }

        @Override
        public PrintWriter getWriter() throws IOException {
            // will error out, if in use
            if (usingWriter) {
                super.getWriter();
            }
            usingWriter = true;
            return output.getWriter();
        }

        public String toString() {
            return output.toString();
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
        this.gebruikerService = ctx.getBean(GebruikerService.class);
    }

    public void destroy() {
        filterConfig = null;
    }
}
