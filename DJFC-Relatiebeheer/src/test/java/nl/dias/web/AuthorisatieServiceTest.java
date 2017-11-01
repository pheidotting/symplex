//package nl.dias.web;
//
//import nl.dias.domein.Medewerker;
//import nl.dias.domein.Relatie;
//import nl.dias.domein.Sessie;
//import nl.dias.service.AuthorisatieService;
//import nl.dias.service.GebruikerService;
//import nl.lakedigital.loginsystem.exception.NietGevondenException;
//import nl.lakedigital.loginsystem.exception.OnjuistWachtwoordException;
//import org.easymock.EasyMockRunner;
//import org.easymock.EasyMockSupport;
//import org.easymock.Mock;
//import org.easymock.TestSubject;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.UnsupportedEncodingException;
//import java.security.NoSuchAlgorithmException;
//import java.util.Date;
//
//import static org.easymock.EasyMock.expect;
//import static org.easymock.EasyMock.expectLastCall;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.fail;
//
//@RunWith(EasyMockRunner.class)
//public class AuthorisatieServiceTest extends EasyMockSupport {
//    @TestSubject
//    private AuthorisatieService service = new AuthorisatieService();
//
//    @Mock
//    private GebruikerService gebruikerService;
//
//    private HttpServletRequest request;
//    private HttpServletResponse response;
//    private HttpSession httpSession;
//    private String identificatie;
//    private String wachtwoord;
//    private boolean onthouden;
//
//    @Before
//    public void setUp() throws Exception {
//        request = createMock(HttpServletRequest.class);
//        response = createMock(HttpServletResponse.class);
//        httpSession = createMock(HttpSession.class);
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        verifyAll();
//    }
//
//    @Test
//    public void testInloggenNietGevonden() throws NietGevondenException {
//        identificatie = "identificatie";
//
//        expect(gebruikerService.zoek(identificatie)).andThrow(new NietGevondenException(""));
//
//        replayAll();
//
//        try {
//            service.inloggen(identificatie, wachtwoord, onthouden, request, response);
//            fail("exception verwacht");
//        } catch (NietGevondenException e) {
//            assertEquals(" werd niet gevonden.", e.getMessage());
//        } catch (OnjuistWachtwoordException e) {
//            fail("onjuiste exception");
//        }
//    }
//
//    @Test
//    public void testInloggenOnjuistWachtwoord() throws NietGevondenException, UnsupportedEncodingException, NoSuchAlgorithmException {
//        identificatie = "identificatie";
//        wachtwoord = "wachtwoord";
//
//        Medewerker medewerker = new Medewerker();
//        medewerker.setHashWachtwoord("anderwachtwoord");
//
//        expect(gebruikerService.zoek(identificatie)).andReturn(medewerker);
//
//        replayAll();
//
//        try {
//            service.inloggen(identificatie, wachtwoord, onthouden, request, response);
//            fail("exception verwacht");
//        } catch (NietGevondenException e) {
//            fail("onjuiste exception");
//        } catch (OnjuistWachtwoordException e) {
//            assertEquals("Het ingevoerde wachtwoord is onjuist", e.getMessage());
//        }
//    }
//
//    @Test
//    @Ignore
//    public void testInloggen() throws NietGevondenException, OnjuistWachtwoordException, UnsupportedEncodingException, NoSuchAlgorithmException {
//        identificatie = "identificatie";
//        wachtwoord = "wachtwoord";
//
//        Medewerker medewerker = new Medewerker();
//        medewerker.setIdentificatie(identificatie);
//        medewerker.setHashWachtwoord(wachtwoord);
//
//        expect(gebruikerService.zoek(identificatie)).andReturn(medewerker);
//        expect(request.getHeader("user-agent")).andReturn("agent");
//        expect(request.getRemoteAddr()).andReturn("addr");
//
//        Sessie sessie = new Sessie();
//        sessie.setBrowser("agent");
//        sessie.setIpadres("addr");
//        sessie.setDatumLaatstGebruikt(new Date());
//        sessie.setGebruiker(medewerker);
//        sessie.setSessie("nieuweCode");
//        gebruikerService.opslaan(sessie);
//        expectLastCall();
//
//        medewerker.getSessies().add(sessie);
//        gebruikerService.opslaan(medewerker);
//        expectLastCall();
//
//        expect(request.getSession()).andReturn(httpSession);
//        httpSession.setAttribute("sessie", "nieuweCode");
//        expectLastCall();
//
//        replayAll();
//
//        service.inloggen(identificatie, wachtwoord, onthouden, request, response);
//    }
//
//    @Test
//    @Ignore
//    public void testUitloggen() throws NietGevondenException {
//        expect(request.getSession()).andReturn(httpSession);
//        expect(httpSession.getAttribute("sessie")).andReturn("sessieId");
//        expect(request.getRemoteAddr()).andReturn("remoteAddr");
//
//        Sessie sessie = new Sessie();
//        sessie.setId(46L);
//        Relatie relatie = new Relatie();
//        relatie.getSessies().add(sessie);
//        expect(gebruikerService.zoekOpSessieEnIpAdres("sessieId", "remoteAddr")).andReturn(relatie);
//        expect(gebruikerService.zoekSessieOp("sessieId", "remoteAddr", relatie.getSessies())).andReturn(sessie);
//        gebruikerService.opslaan(relatie);
//        expectLastCall();
//
//        gebruikerService.verwijderVerlopenSessies(relatie);
//        expectLastCall();
//
//        Cookie cookie = createMock(Cookie.class);
//        Cookie[] cookies = {cookie};
//        expect(request.getCookies()).andReturn(cookies);
//
//        expect(cookie.getName()).andReturn(AuthorisatieService.COOKIE_DOMEIN_CODE);
//        expect(cookie.getValue()).andReturn("value");
//        cookie.setMaxAge(0);
//        expectLastCall();
//        expect(gebruikerService.zoekOpCookieCode("value")).andReturn(relatie);
//        gebruikerService.verwijder(sessie);
//        expectLastCall();
//
//        replayAll();
//
//        service.uitloggen(request);
//    }
//}
