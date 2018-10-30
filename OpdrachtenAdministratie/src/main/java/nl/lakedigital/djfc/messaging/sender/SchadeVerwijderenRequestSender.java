package nl.lakedigital.djfc.messaging.sender;

import nl.lakedigital.as.messaging.request.SchadeVerwijderenRequest;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.SoortOpdracht;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.jms.Destination;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Component
public class SchadeVerwijderenRequestSender extends AbstractSender<SchadeVerwijderenRequest> {
    @Inject
    private Destination responseDestination;

    public SchadeVerwijderenRequestSender() {
        this.jmsTemplates = new ArrayList<>();
        ActiveMQConnectionFactory amqConnectionFactory = new ActiveMQConnectionFactory();
        amqConnectionFactory.setBrokerURL("tcp://localhost:61616");
        amqConnectionFactory.setUserName("admin");
        amqConnectionFactory.setPassword("secret");

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(amqConnectionFactory);

        ActiveMQQueue verwijderEntiteitenRequestDestination = new ActiveMQQueue("oa.pa.schadeVerwijderenRequestQueue");
        JmsTemplate verwijderEntiteitenRequestTemplate = new JmsTemplate();
        verwijderEntiteitenRequestTemplate.setConnectionFactory(connectionFactory);
        verwijderEntiteitenRequestTemplate.setDefaultDestination(verwijderEntiteitenRequestDestination);
        this.jmsTemplates.add(verwijderEntiteitenRequestTemplate);

    }

    public SchadeVerwijderenRequestSender(final JmsTemplate jmsTemplate) {
        //        this.jmsTemplates.add(jmsTemplate);
        this.clazz = SchadeVerwijderenRequest.class;
        this.jmsTemplates = new ArrayList<>();
        ActiveMQConnectionFactory amqConnectionFactory = new ActiveMQConnectionFactory();
        amqConnectionFactory.setBrokerURL("tcp://localhost:61616");
        amqConnectionFactory.setUserName("admin");
        amqConnectionFactory.setPassword("secret");

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(amqConnectionFactory);

        ActiveMQQueue verwijderEntiteitenRequestDestination = new ActiveMQQueue("oa.pa.schadeVerwijderenRequestQueue");
        JmsTemplate verwijderEntiteitenRequestTemplate = new JmsTemplate();
        verwijderEntiteitenRequestTemplate.setConnectionFactory(connectionFactory);
        verwijderEntiteitenRequestTemplate.setDefaultDestination(verwijderEntiteitenRequestDestination);
        this.jmsTemplates.add(verwijderEntiteitenRequestTemplate);

    }

    @Override
    protected Destination getReplyTo() {
        return responseDestination;
    }

    @Override
    public List<SoortEntiteit> getSoortEntiteiten() {
        return newArrayList(SoortEntiteit.SCHADE);
    }

    @Override
    public SoortOpdracht getSoortOpdracht() {
        return SoortOpdracht.VERWIJDEREN;
    }
}
