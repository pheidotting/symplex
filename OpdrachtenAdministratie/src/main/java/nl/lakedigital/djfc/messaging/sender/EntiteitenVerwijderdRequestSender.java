package nl.lakedigital.djfc.messaging.sender;

import nl.lakedigital.as.messaging.request.VerwijderEntiteitenRequest;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.SoortOpdracht;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.jms.Destination;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Component
public class EntiteitenVerwijderdRequestSender extends AbstractSender<VerwijderEntiteitenRequest> {
    private final static Logger LOGGER = LoggerFactory.getLogger(EntiteitenVerwijderdRequestSender.class);
    @Inject
    private Destination responseDestination;

    public EntiteitenVerwijderdRequestSender() {
        super();

        ActiveMQConnectionFactory amqConnectionFactory = new ActiveMQConnectionFactory();
        amqConnectionFactory.setBrokerURL("tcp://localhost:61616");
        amqConnectionFactory.setUserName("admin");
        amqConnectionFactory.setPassword("secret");

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(amqConnectionFactory);

        ActiveMQQueue verwijderEntiteitenRequestDestination = new ActiveMQQueue("id.verwijderEntiteitenRequestQueue");
        JmsTemplate verwijderEntiteitenRequestTemplate = new JmsTemplate();
        verwijderEntiteitenRequestTemplate.setConnectionFactory(connectionFactory);
        verwijderEntiteitenRequestTemplate.setDefaultDestination(verwijderEntiteitenRequestDestination);
        this.jmsTemplates.add(verwijderEntiteitenRequestTemplate);
    }

    public EntiteitenVerwijderdRequestSender(final JmsTemplate jmsTemplate) {
        super();

        this.jmsTemplates.add(jmsTemplate);
        this.clazz = VerwijderEntiteitenRequest.class;

        ActiveMQConnectionFactory amqConnectionFactory = new ActiveMQConnectionFactory();
        amqConnectionFactory.setBrokerURL("tcp://localhost:61616");
        amqConnectionFactory.setUserName("admin");
        amqConnectionFactory.setPassword("secret");

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(amqConnectionFactory);

        ActiveMQQueue verwijderEntiteitenRequestDestination = new ActiveMQQueue("id.verwijderEntiteitenRequestQueue");
        JmsTemplate verwijderEntiteitenRequestTemplate = new JmsTemplate();
        verwijderEntiteitenRequestTemplate.setConnectionFactory(connectionFactory);
        verwijderEntiteitenRequestTemplate.setDefaultDestination(verwijderEntiteitenRequestDestination);
        this.jmsTemplates.add(verwijderEntiteitenRequestTemplate);
    }

    @Override
    public List<SoortEntiteit> getSoortEntiteiten() {
        return newArrayList(SoortEntiteit.ENTITEITVERWIJDERD);
    }

    @Override
    protected Destination getReplyTo() {
        return responseDestination;
    }

    @Override
    public SoortOpdracht getSoortOpdracht() {
        return SoortOpdracht.VERWIJDEREN;
    }
}
