package nl.lakedigital.djfc.inloggen;

import javax.servlet.http.HttpServletRequest;

public final class Sessie {
    // Zodat Hibernate Envers deze uit kan lezen..
    private static Long ingelogdeGebruiker;
    private static String trackAndTraceId;

    private Sessie() {
    }

    public static Long getIngelogdeGebruiker() {
        return ingelogdeGebruiker;
    }

    public static void setIngelogdeGebruiker(Long ingelogdeGebruiker) {
        Sessie.ingelogdeGebruiker = ingelogdeGebruiker;
    }

    public static void setIngelogdeGebruiker(HttpServletRequest httpServletRequest) {
        String ingelogdeGebruiker = httpServletRequest.getHeader("ingelogdeGebruiker");
        if (ingelogdeGebruiker != null) {
            Sessie.ingelogdeGebruiker = Long.valueOf(ingelogdeGebruiker);
        }
    }

    public static String getTrackAndTraceId() {
        return trackAndTraceId;
    }

    public static void setTrackAndTraceId(String trackAndTraceId) {
        Sessie.trackAndTraceId = trackAndTraceId;
    }
}
