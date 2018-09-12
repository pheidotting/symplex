package nl.lakedigital.djfc.messaging.mapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.io.StringWriter;

public abstract class AbstractMapper<T> {
    private Class clazz;

    public AbstractMapper(Class clazz) {
        this.clazz = clazz;
    }

    protected String marshall(T request) {
        StringWriter sw = new StringWriter();

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(request, sw);
        } catch (PropertyException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return sw.toString();
    }
}
