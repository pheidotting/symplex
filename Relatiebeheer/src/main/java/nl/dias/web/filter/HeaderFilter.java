//package nl.dias.web.filter;
//
//import nl.dias.domein.Gebruiker;
//import nl.dias.domein.Sessie;
//import nl.dias.repository.GebruikerRepository;
//import nl.lakedigital.loginsystem.exception.NietGevondenException;
//import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Date;
//
//@Component
//public class HeaderFilter extends OncePerRequestFilter {
//    private static final Logger LOGGER = LoggerFactory.getLogger(HeaderFilter.class);
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//        GebruikerRepository gebruikerRepository = (GebruikerRepository) applicationContext.getBean("gebruikerRepository");
//
//        LOGGER.debug("In HeaderFilter");
//        HttpServletRequest req = (HttpServletRequest) httpServletRequest;
//
//        String sessieHeader = req.getHeader("sessieCode");
//        Gebruiker gebruiker;
//        if (sessieHeader != null) {
//            LOGGER.debug("sessieHeader : {}", sessieHeader);
//            try {
//                LOGGER.debug("Gebruiker opzoeken");
//                gebruiker = gebruikerRepository.zoekOpSessieEnIpadres(sessieHeader, "0:0:0:0:0:0:0:1");
//
//                if (gebruiker == null) {
//                } else {
//                    LOGGER.debug("Gebruiker met id {} gevonden", gebruiker.getId());
//
//                    Sessie sessie = null;
//
//                    LOGGER.debug("Sessies");
//                    for (Sessie sessie1 : gebruiker.getSessies()) {
//                        if (sessie1.getSessie().equals(sessieHeader)) {
//                            sessie = sessie1;
//                        }
//                    }
//                    LOGGER.debug("/ Sessies");
//                    if (sessie != null) {
//                        LOGGER.debug(ReflectionToStringBuilder.toString(sessie));
//                        LOGGER.debug("Sessie weer opslaan met bijgewerkte datum");
//                        req.getSession().setAttribute("sessie", sessie.getSessie());
//                        sessie.setDatumLaatstGebruikt(new Date());
//                        gebruikerRepository.opslaan(sessie);
//
//                        LOGGER.debug("Gebruiker opgehaald : {}", gebruiker != null ? gebruiker.getId() : "0");
//                    } else {
//                        LOGGER.debug("Geen sessie gevonden");
//                    }
//                }
//            } catch (NietGevondenException nge) {
//                LOGGER.trace("Niet gevonden blijkbaar ", nge);
//            }
//        }
//        LOGGER.debug("Verder met het filter");
//        filterChain.doFilter(httpServletRequest, httpServletResponse);
//    }
//}
