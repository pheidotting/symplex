package nl.lakedigital.djfc.mapper;

import nl.lakedigital.as.messaging.AbstractMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public abstract class AbstractMapper<T> {
    private final static Logger LOGGER = LoggerFactory.getLogger(AbstractMapper.class);
    private Class clazz;

    public AbstractMapper(Class clazz) {
        this.clazz = clazz;
    }

    protected void setMDC(AbstractMessage abstractMessage) {
        abstractMessage.setTrackAndTraceId(MDC.get("trackAndTraceId"));
        abstractMessage.setIngelogdeGebruiker(MDC.get("ingelogdeGebruiker") == null ? null : Long.valueOf(MDC.get("ingelogdeGebruiker")));
        abstractMessage.setIngelogdeGebruikerOpgemaakt(MDC.get("ingelogdeGebruikerOpgemaakt"));
        abstractMessage.setUrl(MDC.get("url"));
    }

    protected String marshall(T request) {
        StringWriter sw = new StringWriter();

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(request, sw);
        } catch (JAXBException e) {
            LOGGER.error("Error : ", e);
        }
        return sw.toString();
    }
}
