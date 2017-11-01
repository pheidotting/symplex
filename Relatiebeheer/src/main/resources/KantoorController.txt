package nl.dias.web.medewerker;
//package nl.dias.web;
//
//import java.io.IOException;
//import java.math.BigInteger;
//import java.security.SecureRandom;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.Cookie;
//import javax.ws.rs.core.MediaType;
//
//import nl.dias.domein.Kantoor;
//import nl.dias.domein.KantoorJson;
//import nl.dias.domein.Medewerker;
//import nl.dias.domein.Opmerking;
//import nl.dias.domein.RekeningNummer;
//import nl.dias.domein.Sessie;
//import nl.dias.exception.BsnNietGoedException;
//import nl.dias.exception.EmailAdresFoutiefException;
//import nl.dias.exception.IbanNietGoedException;
//import nl.dias.exception.NietIngelogdException;
//import nl.dias.exception.PostcodeNietGoedException;
//import nl.dias.exception.TelefoonnummerNietGoedException;
//import nl.dias.service.GebruikerService;
//import nl.dias.utils.MailgunUtil;
//import nl.dias.utils.Utils;
//import nl.dias.utils.Validatie;
//
//import org.apache.log4j.Logger;
//
//import com.google.gson.Gson;
//import com.sun.jersey.api.core.InjectParam;
//
//@Path("/kantoor")
//public class KantoorController {// extends AbstractController {
//    private final Logger logger = Logger.getLogger(this.getClass());
//
//    private String cookieCode;
//    private Cookie cookie;
//
//    @InjectParam
//    private KantoorService kantoorService;
//    @InjectParam
//    private GebruikerService gebruikerService;
//    private final SecureRandom random = new SecureRandom();
//    private String nieuwWachtwoord;
//
//    public String creerWachtwoord() {
//        if (nieuwWachtwoord == null) {
//            return new BigInteger(50, random).toString(32);
//        } else {
//            return nieuwWachtwoord;
//        }
//    }
//
//    @GET
//    @Path("/aanmelden")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String aanmelden(@QueryParam("identificatie") String identificatie, @QueryParam("waar") String waar) {// ,
//                                                                                                                 // @Context
//                                                                                                                 // HttpServletRequest
//                                                                                                                 // request)
//                                                                                                                 // {
//        String messages = null;
//
//        try {
//            String wachtwoord = creerWachtwoord();
//            SeleniumController.setWachtwoord(wachtwoord);
//
//            Validatie.validateEmail(identificatie);
//
//            logger.debug("Proberen een account te creeren met e-mail : " + identificatie);
//
//            String[] delen = identificatie.split("@");
//            String[] delen2 = delen[1].split("\\.");
//
//            Medewerker medewerker = new Medewerker();
//            medewerker.setVoornaam(Utils.beginHoofdletter(delen[0]));
//            medewerker.setIdentificatie(identificatie);
//            medewerker.setHashWachtwoord(wachtwoord);
//
//            Kantoor kantoor = new Kantoor();
//            kantoor.setNaam(Utils.beginHoofdletter(delen2[0]));
//            kantoor.setEmailadres(identificatie);
//            // kantoorService.opslaanKantoor(kantoor);
//
//            kantoor.getMedewerkers().add(medewerker);
//            medewerker.setKantoor(kantoor);
//
//            Opmerking opmerking = new Opmerking();
//            opmerking.setOpmerking("Heeft ons gevonden via : " + waar);
//            opmerking.setKantoor(kantoor);
//
//            kantoor.getOpmerkingen().add(opmerking);
//
//            logger.debug("opslaan nieuw Kantoor, naam " + kantoor.getNaam());
//            // gebruikerService.opslaan(medewerker);
//            // kantoorService.opslaanKantoor(kantoor);
//
//            Sessie sessie = new Sessie();
//            sessie.setIpadres(request.getRemoteAddr());
//            sessie.setSessie(request.getSession().getId());
//            sessie.setBrowser(request.getHeader("user-agent"));
//            //
//            // sessie.setCookieCode(getCookieCode());
//            // // response.addCookie(getCookie());
//            //
//            // medewerker.getSessies().add(sessie);
//            // sessie.setGebruiker(medewerker);
//            //
//
//            kantoorService.opslaanKantoor(kantoor);
//            // medewerker.setKantoor(kantoor);
//            // gebruikerService.opslaan(medewerker);
//
//            // De ingelogde medewerker op de httpSession zetten
//            // request.getSession().setAttribute("ingelogdeGebruiker",
//            // kantoor.getMedewerkers().get(0).getId());
//
//            messages = gson.toJson(new Returnen(kantoor.getNaam(), medewerker.getVoornaam(), kantoor.getId().toString(), medewerker.getId().toString()));
//
//            MailgunUtil.verstuurMail("onderwerrup", wachtwoord + "\n\n" + identificatie, "patrick@lakedigital.nl");
//        } catch (EmailAdresFoutiefException | PostcodeNietGoedException | TelefoonnummerNietGoedException | BsnNietGoedException | IbanNietGoedException e) {
//            messages = gson.toJson(e.getMessage());
//        }
//
//        return messages;
//    }
//
//    private class Returnen {
//        @SuppressWarnings("unused")
//        private final String kantoornaam;
//        @SuppressWarnings("unused")
//        private final String voornaam;
//        @SuppressWarnings("unused")
//        private final String idKantoor;
//        @SuppressWarnings("unused")
//        private final String idMedewerker;
//
//        public Returnen(String kantoornaam, String voornaam, String idKantoor, String idMedewerker) {
//            this.kantoornaam = kantoornaam;
//            this.voornaam = voornaam;
//            this.idKantoor = idKantoor;
//            this.idMedewerker = idMedewerker;
//        }
//    }
//
//    @GET
//    @Path("/lees")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String leesKantoor(@Context HttpServletRequest request) {
//        Gson gson = new Gson();
//        String messages = null;
//        try {
//            Medewerker medewerker = (Medewerker) getIngelogdeGebruiker(request);
//            logger.debug("ingelogde medewerker: " + medewerker.getId() + " - " + medewerker.getIdentificatie());
//
//            if (medewerker.getKantoor() == null) {
//                logger.debug("medewerker opnieuw ophalen");
//                medewerker = (Medewerker) getGebruikerService().lees(medewerker.getId());
//            }
//            logger.debug("Kantoor opnieuw ophalen voor Id : " + medewerker.getKantoor().getId());
//            Kantoor kantoor = kantoorService.lees(medewerker.getKantoor().getId());
//
//            kantoor.setMedewerkers(null);
//            kantoor.setOpmerkingen(null);
//            kantoor.setRelaties(null);
//            for (RekeningNummer rekeningNummer : kantoor.getRekeningnummers()) {
//                rekeningNummer.setKantoor(null);
//            }
//
//            messages = gson.toJson(new KantoorJson(kantoor));
//        } catch (NietIngelogdException e) {
//            messages = gson.toJson(e.getMessage());
//        }
//        return messages;
//    }
//
//    @GET
//    @Path("/opslaan")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String opslaan(@QueryParam("kantoor") String strKantoor, @Context HttpServletRequest request) throws ServletException, IOException {
//        Gson gson = new Gson();
//
//        // TODO afvangen java.lang.NumberFormatException
//        KantoorJson kantoorJson = null;
//        String melding = "";
//        try {
//            kantoorJson = gson.fromJson(strKantoor, KantoorJson.class);
//        } catch (Exception e) {
//            melding = e.getMessage();
//            e.printStackTrace();
//        }
//
//        if (melding.equals("")) {
//            // uit de database halen, zodat dit object gemanaged is en dus
//            // gewijzigd kan worden.
//            // als id = 0, dan een nieuw object aanmaken
//            Kantoor kantoor = null;
//            if (kantoorJson.getId() == 0L) {
//                kantoor = kantoorJson.clone(kantoor);
//            } else {
//                kantoor = kantoorService.lees(kantoorJson.getId());
//                kantoor = kantoorJson.clone(kantoor);
//            }
//
//            try {
//                getIngelogdeGebruiker(request);
//            } catch (NietIngelogdException e1) {
//                melding = e1.getMessage();
//            }
//
//            if (melding.equals("")) {
//                try {
//                    kantoorService.opslaanKantoor(kantoor);
//                } catch (PostcodeNietGoedException | TelefoonnummerNietGoedException | BsnNietGoedException | IbanNietGoedException e) {
//                    melding = e.getMessage();
//                }
//            }
//        }
//
//        return gson.toJson(melding);
//    }
//
//    @GET
//    @Path("/verwijder")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String verwijder(@QueryParam("id") String id, @Context HttpServletRequest request) {
//        Gson gson = new Gson();
//        String messages = null;
//
//        try {
//            checkIngelogd(request);
//
//            Kantoor kantoor = kantoorService.lees(Long.parseLong(id));
//            kantoorService.verwijder(kantoor);
//
//        } catch (NietIngelogdException e) {
//            messages = e.getMessage();
//        }
//
//        return gson.toJson(messages);
//    }
//
//    public void setKantoorService(KantoorService kantoorService) {
//        this.kantoorService = kantoorService;
//    }
//
//    public void setNieuwWachtwoord(String nieuwWachtwoord) {
//        this.nieuwWachtwoord = nieuwWachtwoord;
//    }
//
//    public String getCookieCode() {
//        if (cookieCode == null || cookieCode.equals("")) {
//            cookieCode = new BigInteger(50, random).toString(64);
//        }
//        return cookieCode;
//    }
//
//    public void setCookieCode(String cookieCode) {
//        this.cookieCode = cookieCode;
//    }
//
//    public Cookie getCookie() {
//        if (cookie == null) {
//            cookie = new Cookie("inloggen", getCookieCode());
//            cookie.setDomain("dias");
//        }
//        return cookie;
//    }
//
//    public void setCookie(Cookie cookie) {
//        this.cookie = cookie;
//    }
// }
