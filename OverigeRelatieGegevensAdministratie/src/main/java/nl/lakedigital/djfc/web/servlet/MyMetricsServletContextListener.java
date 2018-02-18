package nl.lakedigital.djfc.web.servlet;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlets.MetricsServlet;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;

public class MyMetricsServletContextListener extends MetricsServlet.ContextListener {

    private MetricRegistry metricRegistry;

    @Override
    protected MetricRegistry getMetricRegistry() {
        return metricRegistry;
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContextEvent.getServletContext());
        this.metricRegistry = ctx.getBean(MetricRegistry.class);
        super.contextInitialized(servletContextEvent);
    }
}