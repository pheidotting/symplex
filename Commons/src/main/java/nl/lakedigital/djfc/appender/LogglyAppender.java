package nl.lakedigital.djfc.appender;

import inloggen.SessieHolder;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.slf4j.MDC;
import org.springframework.context.ApplicationContext;

public class LogglyAppender extends AppenderSkeleton {
    private String tag = "";
    private String token = "";
    private String interval = "1";
    private LogglyEventsBuffer logglyEventsBuffer;

    public LogglyAppender() {
        super();
    }

    public LogglyAppender(boolean isActive) {
        super(isActive);
    }

    @Override
    protected void append(LoggingEvent event) {
        if (logglyEventsBuffer == null) {
            ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
            logglyEventsBuffer = (LogglyEventsBuffer) appContext.getBean("logglyEventsBuffer");
        }

        assert this.layout != null : "Cannot log, there is no layout configured.";

        String output = this.layout.format(event);

        Layout klasse = new PatternLayout("%c{1}");

        String tagOutput = tag + "," + klasse.format(event);
        String trackAndTraceId = MDC.get("trackAndTraceId");
        if (trackAndTraceId == null || "".equals(trackAndTraceId)) {
            if (SessieHolder.get().getTrackAndTraceId() != null && !"".equals(SessieHolder.get().getTrackAndTraceId())) {
                trackAndTraceId = SessieHolder.get().getTrackAndTraceId();
            }
        }
        if (trackAndTraceId != null && !"".equals(trackAndTraceId)) {
            tagOutput = tag + "," + SessieHolder.get().getTrackAndTraceId();
        }
        logglyEventsBuffer.add(output, event.getLevel(), token, tagOutput, Integer.parseInt(interval));
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    @Override
    public void close() {
        //Gebruiken we niet
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }
}
