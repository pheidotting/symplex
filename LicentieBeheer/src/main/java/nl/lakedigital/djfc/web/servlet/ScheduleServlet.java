package nl.lakedigital.djfc.web.servlet;

import nl.lakedigital.djfc.repository.IdentificatieRepository;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleServlet implements ServletContextListener {
    private ScheduledExecutorService scheduler;

    @Inject
    private IdentificatieRepository identificatieRepository;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContextEvent.getServletContext()).getAutowireCapableBeanFactory().autowireBean(this);

        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new CheckDatabaseConnectie(identificatieRepository), 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }

}