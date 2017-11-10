//package nl.dias.web.filter;
//
//import static org.easymock.EasyMock.expect;
//import static org.easymock.EasyMock.expectLastCall;
//
//import java.io.IOException;
//import java.util.HashSet;
//import java.util.Set;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import nl.dias.domein.Gebruiker;
//import nl.dias.domein.Sessie;
//import nl.dias.repository.GebruikerRepository;
//import nl.dias.service.AuthorisatieService;
//import nl.dias.service.GebruikerService;
//import nl.lakedigital.loginsystem.exception.NietGevondenException;
//
//import org.easymock.EasyMockSupport;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//
//@Ignore
//public class AuthorisatieFilterTest extends EasyMockSupport {
//    private GebruikerRepository gebruikerRepository;
//    private GebruikerService gebruikerService;
//    private AuthorisatieFilter filter;
//    private ServletRequest request;
//    private ServletResponse response;
//    private FilterChain chain;
//
//    @Before
//    public void setUp() throws Exception {
//        filter = new AuthorisatieFilter();
//
//        gebruikerRepository = createMock(GebruikerRepository.class);
//        filter.setGebruikerRepository(gebruikerRepository);
//
//        gebruikerService = createMock(GebruikerService.class);
//        filter.setGebruikerService(gebruikerService);
//
//        request = createMock(HttpServletRequest.class);
//        response = createMock(HttpServletResponse.class);
//        chain = createMock(FilterChain.class);
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        verifyAll();
//    }
//
//    @Test
//    public void testDoFilter() throws IOException, ServletException, NietGevondenException {
//        StringBuffer sb = new StringBuffer();
//        sb.append("goeie url");
//
//        Cookie[] cookies = {};
//        expect(((HttpServletRequest) request).getCookies()).andReturn(cookies);
//        expect(((HttpServletRequest) request).getRequestURL()).andReturn(sb).times(2);
//        expect(((HttpServletRequest) request).getQueryString()).andReturn("aa").times(2);
//
//        HttpSession httpSession = createMock(HttpSession.class);
//        expect(((HttpServletRequest) request).getSession()).andReturn(httpSession);
//        expect(httpSession.getAttribute("sessie")).andReturn("sessie");
//        expect(((HttpServletRequest) request).getRemoteAddr()).andReturn("ipadres");
//
//        Gebruiker gebruiker = createMock(Gebruiker.class);
//        expect(gebruikerRepository.zoekOpSessieEnIpadres("sessie", "ipadres")).andReturn(gebruiker);
//        expect(gebruiker.getId()).andReturn(2L);
//
//        Sessie sessie = createNiceMock(Sessie.class);
//        Set<Sessie> sessies = new HashSet<Sessie>();
//        sessies.add(sessie);
//        expect(gebruiker.getSessies()).andReturn(sessies);
//        expect(gebruikerService.zoekSessieOp("sessie", "ipadres", sessies)).andReturn(sessie);
//
//        gebruikerRepository.opslaan(sessie);
//        expectLastCall();
//
//        chain.doFilter(request, response);
//        expectLastCall();
//
//        replayAll();
//
//        filter.doFilter(request, response, chain);
//    }
//
//    @Test
//    public void testDoFilterMet401() throws IOException, ServletException, NietGevondenException {
//        StringBuffer sb = new StringBuffer();
//        sb.append("goeie url");
//        expect(((HttpServletRequest) request).getRequestURL()).andReturn(sb).times(2);
//        expect(((HttpServletRequest) request).getQueryString()).andReturn("aa").times(2);
//
//        HttpSession httpSession = createMock(HttpSession.class);
//        expect(((HttpServletRequest) request).getSession()).andReturn(httpSession);
//        expect(httpSession.getAttribute("sessie")).andReturn(null);
//        expect(((HttpServletRequest) request).getRemoteAddr()).andReturn("ipadres");
//
//        ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
//        expectLastCall();
//
//        Cookie[] cookies = {};
//        expect(((HttpServletRequest) request).getCookies()).andReturn(cookies);
//
//        replayAll();
//
//        filter.doFilter(request, response, chain);
//    }
//
//    @Test
//    public void testDoFilterMetCookie() throws IOException, ServletException, NietGevondenException {
//        StringBuffer sb = new StringBuffer();
//        sb.append("goeie url");
//        expect(((HttpServletRequest) request).getRequestURL()).andReturn(sb).times(2);
//        expect(((HttpServletRequest) request).getQueryString()).andReturn("aa").times(2);
//
//        HttpSession httpSession = createMock(HttpSession.class);
//        expect(((HttpServletRequest) request).getSession()).andReturn(httpSession);
//
//        Cookie cookie = createMock(Cookie.class);
//        expect(cookie.getName()).andReturn(AuthorisatieService.COOKIE_DOMEIN_CODE);
//        expect(cookie.getValue()).andReturn("aabb").times(4);
//
//        Cookie[] cookies = { cookie };
//        expect(((HttpServletRequest) request).getCookies()).andReturn(cookies);
//
//        Gebruiker gebruiker = createMock(Gebruiker.class);
//        expect(gebruikerRepository.zoekOpCookieCode("aabb")).andReturn(gebruiker);
//
//        Sessie sessie = createMock(Sessie.class);
//        Set<Sessie> sessies = new HashSet<Sessie>();
//        sessies.add(sessie);
//        expect(gebruiker.getSessies()).andReturn(sessies);
//        expect(gebruikerService.zoekSessieOp("aabb", sessies)).andReturn(sessie);
//        expect(sessie.getSessie()).andReturn("sessie");
//        httpSession.setAttribute("sessie", "sessie");
//        expectLastCall();
//
//        chain.doFilter(request, response);
//        expectLastCall();
//
//        replayAll();
//
//        filter.doFilter(request, response, chain);
//    }
//
//    @Ignore
//    @Test
//    public void testDoFilterInloggen() throws IOException, ServletException, NietGevondenException {
//        StringBuffer sb = new StringBuffer();
//        sb.append("/rest/gebruiker/inloggen");
//        expect(((HttpServletRequest) request).getRequestURL()).andReturn(sb).times(2);
//        expect(((HttpServletRequest) request).getQueryString()).andReturn(null).times(2);
//
//        chain.doFilter(request, response);
//        expectLastCall();
//
//        replayAll();
//
//        filter.doFilter(request, response, chain);
//    }
//}
