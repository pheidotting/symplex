package nl.lakedigital.djfc.appender;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

public class KibanaAppender extends AppenderSkeleton {
    private String applicatie = "";
    private String token = "";
    private String interval = "1";
    private KibanaEventsBuffer kibanaEventsBuffer;

    public KibanaAppender() {
        super();
    }

    public KibanaAppender(boolean isActive) {
        super(isActive);
    }

    @Override
    protected void append(LoggingEvent event) {
        if (kibanaEventsBuffer == null) {
            ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
            kibanaEventsBuffer = (KibanaEventsBuffer) appContext.getBean("kibanaEventsBuffer");
        }

        assert this.layout != null : "Cannot log, there is no layout configured.";

        String output = this.layout.format(event);

        kibanaEventsBuffer.add(output, event, Integer.parseInt(interval), token, applicatie);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getApplicatie() {
        return applicatie;
    }

    public void setApplicatie(String applicatie) {
        this.applicatie = applicatie;
    }

    @Override
    public void close() {
        //Gebruiken we niet
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }
}
