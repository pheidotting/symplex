package nl.lakedigital.djfc.web.servlet;

import nl.lakedigital.djfc.service.TelefonieBestandService;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InlezenTelefonieBestandenServlet implements ServletContextListener {
    @Inject
    private TelefonieBestandService telefonieBestandService;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContextEvent.getServletContext()).getAutowireCapableBeanFactory().autowireBean(this);

        //        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        //        scheduler.scheduleAtFixedRate(new InlezenTelefonieBestandenService(telefonieBestandService), 0, 5, TimeUnit.MINUTES);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //Moet overriden worden van de compiler, maar van sonar mag er geen leeg blok zijn, dus maar een comment
    }
}

