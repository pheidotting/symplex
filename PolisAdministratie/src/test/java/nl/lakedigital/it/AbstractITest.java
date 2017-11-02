package nl.lakedigital.it;

import nl.lakedigital.as.messaging.domain.Polis;
import nl.lakedigital.as.messaging.request.PolisOpslaanRequest;
import nl.lakedigital.as.messaging.request.PolisVerwijderenRequest;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.messaging.sender.AbstractSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.BrowserCallback;
import org.springframework.jms.core.JmsTemplate;

import javax.inject.Inject;
import javax.jms.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class AbstractITest {
    private final static Logger LOGGER = LoggerFactory.getLogger(AbstractITest.class);

    @Inject
    protected JmsTemplate polisOpslaanRequestTemplate;
    @Inject
    protected JmsTemplate polisVerwijderenRequestTemplate;

    protected final String datumFormaat = "yyyy-MM-dd";

    protected void sendPolisOpslaanRequestMessage(Long ingegelogdeGebruiker, String trackAndTraceId, Polis... polissen) {
        AbstractSender sender = new AbstractSender<PolisOpslaanRequest, JsonPolis>() {
            @Override
            public PolisOpslaanRequest maakMessage(JsonPolis jsonPolis) {
                PolisOpslaanRequest polisOpslaanRequest = new PolisOpslaanRequest();
                polisOpslaanRequest.setIngelogdeGebruiker(ingegelogdeGebruiker);
                polisOpslaanRequest.setTrackAndTraceId(trackAndTraceId);

                for (Polis polis : polissen) {
                    polisOpslaanRequest.getPolissen().add(polis);
                }

                return polisOpslaanRequest;
            }
        };

        sender.setClazz(PolisOpslaanRequest.class);
        sender.setJmsTemplate(polisOpslaanRequestTemplate);
        sender.send(new JsonPolis());
    }

    protected void sendPolisVerwijderenRequestMessage(Long ingegelogdeGebruiker, String trackAndTraceId, Long... ids) {
        AbstractSender sender = new AbstractSender<PolisVerwijderenRequest, JsonPolis>() {
            @Override
            public PolisVerwijderenRequest maakMessage(JsonPolis jsonPolis) {
                PolisVerwijderenRequest polisVerwijderenRequest = new PolisVerwijderenRequest();
                polisVerwijderenRequest.setIngelogdeGebruiker(ingegelogdeGebruiker);
                polisVerwijderenRequest.setTrackAndTraceId(trackAndTraceId);

                for (Long id : ids) {
                    polisVerwijderenRequest.getIds().add(id);
                }

                return polisVerwijderenRequest;
            }
        };

        sender.setClazz(PolisOpslaanRequest.class);
        sender.setJmsTemplate(polisVerwijderenRequestTemplate);
        sender.send(new JsonPolis());
    }

    protected <T> List<T> getMessageFromTemplate(JmsTemplate jmsTemplate, Class<T> clazz) throws JAXBException, JMSException {
        //        Message m = jmsTemplate.receive();
        return jmsTemplate.browse(new BrowserCallback<List>() {
            @Override
            public List doInJms(Session session, QueueBrowser qb) throws JMSException {
                List<T> result = newArrayList();

                final Enumeration<Message> e = qb.getEnumeration();
                while (e.hasMoreElements()) {
                    final Message m = e.nextElement();
                    TextMessage message = (TextMessage) m;

                    T ontvangenObject;

                    try {
                        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
                        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                        ontvangenObject = (T) jaxbUnmarshaller.unmarshal(new StringReader(message.getText()));
                        result.add(ontvangenObject);

                        m.acknowledge();
                    } catch (JAXBException e1) {
                        e1.printStackTrace();
                    }
                }

                return result;
            }
        });

        //        TextMessage message = (TextMessage) m;
        //        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        //        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        //
        //        T ontvangenObject = (T) jaxbUnmarshaller.unmarshal(new StringReader(message.getText()));
        //
        //        m.acknowledge();
        //
        //        return ontvangenObject;
        //        return result;
    }
}
