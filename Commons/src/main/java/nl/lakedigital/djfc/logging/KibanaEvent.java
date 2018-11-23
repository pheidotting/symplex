package nl.lakedigital.djfc.logging;

import org.apache.log4j.Layout;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

import java.io.Serializable;

public class KibanaEvent implements Serializable {
    private String message;
    private String logLevel;
    private String timestamp;
    private String filename;
    private String lineNumber;
    private Long ingelogdeGebruiker;
    private String ingelogdeGebruikerOpgemaakt;
    private String trackAndTraceId;
    private String applicatie;
    private String omgeving;
    private String url;
    private int hash;

    public KibanaEvent(int hash, String message, LoggingEvent event, Long ingelogdeGebruiker, String trackAndTraceId, String ingelogdeGebruikerOpgemaakt, String url, String applicatie, String omgeving) {
        this.hash = hash;
        this.message = message;
        this.ingelogdeGebruiker = ingelogdeGebruiker;
        this.ingelogdeGebruikerOpgemaakt = ingelogdeGebruikerOpgemaakt;
        this.trackAndTraceId = trackAndTraceId;

        //        Layout timestampLayout = new PatternLayout("%d{ISO8601}");
        Layout timestampLayout = new PatternLayout("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        //        Layout javaClassLayout = new PatternLayout("%c{1}");
        Layout lineNumberLayout = new PatternLayout("%L");

        if (event != null) {
            if (event.getLevel() != null) {
                this.logLevel = event.getLevel().toString();
            }
            this.timestamp = timestampLayout.format(event);
            this.filename = event.getLoggerName();//javaClassLayout.format(event);
            this.lineNumber = lineNumberLayout.format(event);
        }
        this.applicatie = applicatie;
        this.omgeving = omgeving;
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Long getIngelogdeGebruiker() {
        return ingelogdeGebruiker;
    }

    public void setIngelogdeGebruiker(Long ingelogdeGebruiker) {
        this.ingelogdeGebruiker = ingelogdeGebruiker;
    }

    public String getIngelogdeGebruikerOpgemaakt() {
        return ingelogdeGebruikerOpgemaakt;
    }

    public void setIngelogdeGebruikerOpgemaakt(String ingelogdeGebruikerOpgemaakt) {
        this.ingelogdeGebruikerOpgemaakt = ingelogdeGebruikerOpgemaakt;
    }

    public String getTrackAndTraceId() {
        return trackAndTraceId;
    }

    public void setTrackAndTraceId(String trackAndTraceId) {
        this.trackAndTraceId = trackAndTraceId;
    }

    public String getApplicatie() {
        return applicatie;
    }

    public void setApplicatie(String applicatie) {
        this.applicatie = applicatie;
    }

    public String getOmgeving() {
        return omgeving;
    }

    public void setOmgeving(String omgeving) {
        this.omgeving = omgeving;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getHash() {
        return hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }
}