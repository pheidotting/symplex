package nl.lakedigital.djfc.appender;

import com.google.common.util.concurrent.RateLimiter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.*;
import com.sun.jersey.api.client.filter.ClientFilter;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;
import org.slf4j.MDC;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class KibanaEventsBuffer {
    private List<KibanaEvent> events;
    private Timer timer;
    private boolean flushing = false;
    private RateLimiter rateLimiter = RateLimiter.create(1);

    public void add(String event, LoggingEvent loggingEvent, int interval, final String token, String applicatie, String omgeving) {
        if (events == null) {
            events = new ArrayList<>();
        }

        String ingelogdeGebruiker = MDC.get("ingelogdeGebruiker");
        Long ig = null;
        if (ingelogdeGebruiker != null && !"null".equals(ingelogdeGebruiker)) {
            ig = Long.valueOf(ingelogdeGebruiker);
        }

        events.add(new KibanaEvent(event, loggingEvent, ig, MDC.get("trackAndTraceId"), MDC.get("ingelogdeGebruikerOpgemaakt"), MDC.get("url"), applicatie, omgeving));

        if (!events.isEmpty() && events.size() >= 500 || loggingEvent.getLevel() == Level.ERROR) {
            flush(token);
        }
        if (timer == null && !flushing) {
            int delay = interval * 1000;
            int period = 5000;
            try {
                this.timer = new Timer();
            } catch (OutOfMemoryError outOfMemoryError) {
                throw new RuntimeException(outOfMemoryError.getMessage());
            }
            timer.scheduleAtFixedRate(new TimerTask() {

                public void run() {
                    flush(token);

                }
            }, delay, period);
            timer = null;
        }
    }

    public void flush(String token) {
        if (events != null && !events.isEmpty() && !flushing) {
            flushing = true;
            List<KibanaEvent> eventsToFlush = new ArrayList<>(events);
            events = null;

            rateLimiter.acquire();

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            StringBuffer verstuurObject = new StringBuffer();
            for (KibanaEvent event : eventsToFlush) {
                String s = gson.toJson(event).replace("\n", "");
                verstuurObject.append(s);
            }
            Client client = Client.create();

            client.addFilter(new ClientFilter() {
                @Override
                public ClientResponse handle(ClientRequest clientRequest) throws ClientHandlerException {
                    ClientResponse response = getNext().handle(clientRequest);

                    return response;
                }
            });

            WebResource webResource = client.resource("http://listener.logz.io:8070/?token=" + token);

            webResource.accept("application/json").type("application/json").post(ClientResponse.class, verstuurObject.toString());

            flushing = false;
        }
    }


    private class KibanaEvent {
        private String message;
        private String logLevel;
        private String timestamp;
        private String javaClass;
        private String lineNumber;
        private Long ingelogdeGebruiker;
        private String ingelogdeGebruikerOpgemaakt;
        private String trackAndTraceId;
        private String applicatie;
        private String omgeving;
        private String url;

        public KibanaEvent(String message, LoggingEvent event, Long ingelogdeGebruiker, String trackAndTraceId, String ingelogdeGebruikerOpgemaakt, String url, String applicatie, String omgeving) {
            this.message = message;
            if (event.getLevel() != null) {
                this.logLevel = event.getLevel().toString();
            }
            this.ingelogdeGebruiker = ingelogdeGebruiker;
            this.ingelogdeGebruikerOpgemaakt = ingelogdeGebruikerOpgemaakt;
            this.trackAndTraceId = trackAndTraceId;

            Layout timestampLayout = new PatternLayout("%d{ISO8601}");
            Layout javaClassLayout = new PatternLayout("%c{1}");
            Layout lineNumberLayout = new PatternLayout("%L");

            this.timestamp = timestampLayout.format(event);
            this.javaClass = javaClassLayout.format(event);
            this.lineNumber = lineNumberLayout.format(event);
            this.applicatie = applicatie;
            this.omgeving = omgeving;
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

        public String getJavaClass() {
            return javaClass;
        }

        public void setJavaClass(String javaClass) {
            this.javaClass = javaClass;
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
    }
}
