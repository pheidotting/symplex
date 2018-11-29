package nl.dias.web.filter.trackandtraceid;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import nl.dias.domein.Gebruiker;
import nl.dias.domein.Medewerker;
import nl.dias.domein.Relatie;
import nl.dias.repository.GebruikerRepository;
import nl.dias.service.trackandtraceid.InkomendRequestService;
import nl.lakedigital.loginsystem.exception.NietGevondenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.ws.rs.core.HttpHeaders;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

@Component
public class TrackAndTraceFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrackAndTraceFilter.class);

    private InkomendRequestService inkomendRequestService;
    private GebruikerRepository gebruikerRepository;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
        this.inkomendRequestService = ctx.getBean(InkomendRequestService.class);
        this.gebruikerRepository = ctx.getBean(GebruikerRepository.class);
    }


    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String json = null;

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        MultiReadHttpServletRequest multiReadHttpServletRequest = new MultiReadHttpServletRequest(httpServletRequest);
        String url = getFullURL(httpServletRequest);

        Gebruiker ingelogdeGebruiker = getIngelogdeGebruiker(httpServletRequest);
        Long id = getIngelogdeGebruiker(httpServletRequest) == null ? null : getIngelogdeGebruiker(httpServletRequest).getId();

        if (!url.endsWith("/log4j/log4javascript")) {

            if ("POST".equalsIgnoreCase(httpServletRequest.getMethod()) && !url.endsWith("bijlage/uploadBijlage")) {
                json = getJson(multiReadHttpServletRequest.getReader());

                //Wachtwoord filteren uit inloggen request, niet zo netjes om dit plain text op te slaan
                if (url.endsWith("inloggen")) {
                    int i = json.indexOf("wachtwoord\":") + 13;
                    int j = json.indexOf('\"', i);

                    json = json.substring(0, i) + "XXXX" + json.substring(j);
                }
            }

            inkomendRequestService.opslaan(id, json, httpServletRequest, url);
        }

        HeaderMapRequestWrapper requestWrapper = new HeaderMapRequestWrapper(multiReadHttpServletRequest);
        String trackAndTraceId = UUID.randomUUID().toString();
        requestWrapper.addHeader("trackAndTraceId", trackAndTraceId);

        MDC.remove("ingelogdeGebruiker");
        MDC.remove("ingelogdeGebruikerOpgemaakt");
        MDC.remove("url");
        MDC.remove("trackAndTraceId");

        if (ingelogdeGebruiker != null) {
            MDC.put("ingelogdeGebruiker", id + "");
            MDC.put("ingelogdeGebruikerOpgemaakt", maakOp(ingelogdeGebruiker));
        }
        MDC.put("trackAndTraceId", trackAndTraceId);
        MDC.put("url", stripToken(url));

        filterChain.doFilter(requestWrapper, servletResponse);
    }

    private String stripToken(String urlMetToken) {
        int pos = urlMetToken.indexOf("&token=");
        if (pos > 0) {
            return urlMetToken.substring(0, pos);
        } else {
            return urlMetToken;
        }
    }

    @Override
    public void destroy() {

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

    private String getJson(BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append('\n');
        }
        reader.close();
        return sb.toString();
    }

    private String maakOp(Gebruiker gebruiker) {
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append(gebruiker.getVoornaam());
        stringBuffer.append(" ");
        if (gebruiker.getTussenvoegsel() != null && !"".equals(gebruiker.getTussenvoegsel())) {
            stringBuffer.append(gebruiker.getTussenvoegsel());
            stringBuffer.append(" ");
        }
        stringBuffer.append(gebruiker.getAchternaam());
        stringBuffer.append(" (");
        stringBuffer.append(gebruiker.getId());
        stringBuffer.append(")");

        if (gebruiker instanceof Medewerker) {
            stringBuffer.append(", ");
            stringBuffer.append(((Medewerker) gebruiker).getKantoor().getNaam());
            stringBuffer.append(" (");
            stringBuffer.append(((Medewerker) gebruiker).getKantoor().getId());
            stringBuffer.append(")");
        }

        return stringBuffer.toString();
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
                return gebruikerRepository.zoekOpIdentificatie(decodedJWT.getSubject());
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

    public class HeaderMapRequestWrapper extends HttpServletRequestWrapper {
        private Map<String, String> headerMap = new HashMap<>();

        /**
         * construct a wrapper for this request
         *
         * @param request
         */
        public HeaderMapRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        /**
         * add a header with given name and value
         *
         * @param name
         * @param value
         */
        public void addHeader(String name, String value) {
            headerMap.put(name, value);
        }

        @Override
        public String getHeader(String name) {
            String headerValue = super.getHeader(name);
            if (headerMap.containsKey(name)) {
                headerValue = headerMap.get(name);
            }
            return headerValue;
        }

        /**
         * get the Header names
         */
        @Override
        public Enumeration<String> getHeaderNames() {
            List<String> names = Collections.list(super.getHeaderNames());
            for (String name : headerMap.keySet()) {
                names.add(name);
            }
            return Collections.enumeration(names);
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            List<String> values = Collections.list(super.getHeaders(name));
            if (headerMap.containsKey(name)) {
                values.add(headerMap.get(name));
            }
            return Collections.enumeration(values);
        }

    }

}