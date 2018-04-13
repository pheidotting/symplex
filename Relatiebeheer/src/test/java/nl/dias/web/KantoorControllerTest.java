package nl.dias.web;
//package nl.dias.web;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import nl.dias.domein.Kantoor;
//import nl.dias.domein.KantoorJson;
//import nl.dias.domein.Medewerker;
//import nl.dias.domein.Opmerking;
//import nl.dias.domein.Sessie;
//import nl.dias.exception.BsnNietGoedException;
//import nl.dias.exception.IbanNietGoedException;
//import nl.dias.exception.PostcodeNietGoedException;
//import nl.dias.exception.TelefoonnummerNietGoedException;
//import nl.dias.service.KantoorService;
//
//import org.easymock.EasyMock;
//import org.junit.Before;
//import org.junit.Test;
//import org.powermock.api.easymock.PowerMock;
//
//import com.google.gson.Gson;
//
//public class KantoorControllerTest {
//	private KantoorController controller;
//	private KantoorService kantoorService;
//	// private GebruikerService gebruikerService;
//	private HttpServletRequest request;
//
//	@Before
//	public void setUp() throws Exception {
//		controller = new KantoorController();
//
//		kantoorService = EasyMock.createMock(KantoorService.class);
//		controller.setKantoorService(kantoorService);
//
//		// gebruikerService = EasyMock.createMock(GebruikerService.class);
//		// controller.setGebruikerService(gebruikerService);
//
//		request = EasyMock.createMock(HttpServletRequest.class);
//	}
//
//	@Test
//	public void aanmelden() throws ServletException, IOException, PostcodeNietGoedException, TelefoonnummerNietGoedException, BsnNietGoedException, IbanNietGoedException {
//		Medewerker medewerker = new Medewerker();
//		medewerker.setIdentificatie("aa@bb.cc");
//		medewerker.setVoornaam("Aa");
//		medewerker.setHashWachtwoord("wachtwoord");
//		controller.setNieuwWachtwoord("wachtwoord");
//
//		Kantoor kantoor = new Kantoor();
//		kantoor.setNaam("Bb");
//		kantoor.getMedewerkers().add(medewerker);
//		kantoor.setEmailadres("aa@bb.cc");
//		medewerker.setKantoor(kantoor);
//
//		Opmerking opmerking = new Opmerking();
//		opmerking.setOpmerking("Heeft ons gevonden via : presentatie");
//		opmerking.setKantoor(kantoor);
//
//		kantoor.getOpmerkingen().add(opmerking);
//
//		// gebruikerService.opslaan(medewerker);
//		// EasyMock.expectLastCall();
//		EasyMock.expect(request.getRemoteAddr()).andReturn("1234");
//		HttpSession session = EasyMock.createMock(HttpSession.class);
//
//		EasyMock.expect(request.getSession()).andReturn(session).times(1);
//		EasyMock.expect(session.getId()).andReturn("12");
//		EasyMock.expect(request.getHeader("user-agent")).andReturn("agent");
//
//		Sessie sessie = new Sessie();
//		sessie.setIpadres("1234");
//		sessie.setSessie("12");
//		sessie.setBrowser("agent");
//
//		// medewerker.getSessies().add(sessie);
//		// sessie.setGebruiker(medewerker);
//
//		kantoorService.opslaanKantoor(kantoor);
//		EasyMock.expectLastCall().andDelegateTo(new OpslaanKantoor());
//
//		// sessie.setAttribute("ingelogdeGebruiker", 58);
//		// EasyMock.expectLastCall();
//
//		EasyMock.replay(request, kantoorService, session);
//
//		controller.aanmelden("aa@bb.cc", "presentatie", request);
//
//		EasyMock.verify(request, kantoorService, session);
//	}
//
//	private class OpslaanKantoor extends KantoorService {
//		@Override
//		public void opslaan(Kantoor kantoor) throws PostcodeNietGoedException, TelefoonnummerNietGoedException, BsnNietGoedException, IbanNietGoedException {
//			kantoor.setId(58L);
//			kantoor.getMedewerkers().get(0).setId(46L);
//		}
//	}
//
//	@Test
//	public void opslaan() throws Exception {
//		controller = PowerMock.createPartialMock(KantoorController.class, "getIngelogdeGebruiker");
//		controller.setKantoorService(kantoorService);
//
//		String strKantoor = "{\"id\":0,\"naam\":\"Bbbb\",\"adresStraat\":\"STRAAT\",\"adresPlaats\":\"PLAATS\",\"adresHuisnummer\":2,\"adresPostcode\":\"1111AA\",\"adresToevoeging\":\"TOEVOEGING\",\"factuurAdresHuisnummer\":3,\"factuurAdresPlaats\":\"PLAATS_FACTUUR\",\"factuurAdresPostcode\":\"2222BB\",\"factuurAdresStraat\":\"STRAAT_FACTUUR\",\"factuurAdresToevoeging\":\"TOEVOEGING_FACTUUR\",\"kvk\":233334343,\"btwNummer\":\"BTW_NUMMER\",\"datumOprichtingString\":\"10-09-2013\",\"rechtsvorm\":\"VOF\",\"soortKantoor\":\"HYP\",\"emailadres\":\"EMAILADRES\"}";
//
//		Medewerker gebruiker = new Medewerker();
//		gebruiker.setId(46L);
//		PowerMock.expectPrivate(controller, "getIngelogdeGebruiker", request).andReturn(gebruiker);
//
//		kantoorService.opslaan(kantoor());
//		EasyMock.expectLastCall();
//
//		EasyMock.replay(request, kantoorService);
//		PowerMock.replayAll();
//
//		controller.opslaan(strKantoor, request);
//
//		EasyMock.verify(request, kantoorService);
//		PowerMock.verifyAll();
//	}
//
//	@Test
//	public void opslaanMetId() throws Exception {
//		controller = PowerMock.createPartialMock(KantoorController.class, "getIngelogdeGebruiker");
//		controller.setKantoorService(kantoorService);
//
//		String strKantoor = "{\"id\":2,\"naam\":\"Bbbb\",\"adresStraat\":\"STRAAT\",\"adresPlaats\":\"PLAATST\",\"adresHuisnummer\":2,\"adresPostcode\":\"1111AA\",\"adresToevoeging\":\"TOEVOEGING\",\"factuurAdresHuisnummer\":3,\"factuurAdresPlaats\":\"PLAATS_FACTUUR\",\"factuurAdresPostcode\":\"2222BB\",\"factuurAdresStraat\":\"STRAAT_FACTUUR\",\"factuurAdresToevoeging\":\"TOEVOEGING_FACTUUR\",\"kvk\":233334343,\"btwNummer\":\"BTW_NUMMER\",\"datumOprichtingString\":\"10-09-2013\",\"rechtsvorm\":\"VOF\",\"soortKantoor\":\"HYP\",\"emailadres\":\"EMAILADRES\"}";
//
//		Medewerker gebruiker = new Medewerker();
//		gebruiker.setId(46L);
//		PowerMock.expectPrivate(controller, "getIngelogdeGebruiker", request).andReturn(gebruiker);
//
//		Kantoor kantoor = kantoor();
//		kantoor.setId(2L);
//
//		EasyMock.expect(kantoorService.lees(2L)).andReturn(kantoor);
//
//		kantoor.getAdres().setPlaats("PLAATST");
//		kantoorService.opslaan(kantoor);
//		EasyMock.expectLastCall();
//
//		EasyMock.replay(request, kantoorService);
//		PowerMock.replayAll();
//
//		controller.opslaan(strKantoor, request);
//
//		EasyMock.verify(request, kantoorService);
//		PowerMock.verifyAll();
//	}
//
//	private Kantoor kantoor() {
//		String s = "{\"id\":0,\"naam\":\"Bbbb\",\"adresStraat\":\"STRAAT\",\"adresPlaats\":\"PLAATS\",\"adresHuisnummer\":2,\"adresPostcode\":\"1111AA\",\"adresToevoeging\":\"TOEVOEGING\",\"factuurAdresHuisnummer\":3,\"factuurAdresPlaats\":\"PLAATS_FACTUUR\",\"factuurAdresPostcode\":\"2222BB\",\"factuurAdresStraat\":\"STRAAT_FACTUUR\",\"factuurAdresToevoeging\":\"TOEVOEGING_FACTUUR\",\"kvk\":233334343,\"btwNummer\":\"BTW_NUMMER\",\"datumOprichtingString\":\"10-09-2013\",\"rechtsvorm\":\"VOF\",\"soortKantoor\":\"HYP\",\"emailadres\":\"EMAILADRES\"}";
//
//		Gson gson = new Gson();
//
//		KantoorJson kantoorJson = gson.fromJson(s, KantoorJson.class);
//
//		Kantoor kantoor = kantoorJson.clone(new Kantoor());
//
//		return kantoor;
//
//	}
// }
