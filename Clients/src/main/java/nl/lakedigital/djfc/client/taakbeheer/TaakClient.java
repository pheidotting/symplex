package nl.lakedigital.djfc.client.taakbeheer;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import nl.lakedigital.djfc.commons.json.OpvragenAfgerondeTakenResponse;
import nl.lakedigital.djfc.commons.json.Taak;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class TaakClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaakClient.class);

    protected String basisUrl;

    private final String URL_AFGERONDE_TAKEN = "/rest/taak/alleAfgerondeTaken";

    public void setBasisUrl(String basisUrl) {
        this.basisUrl = basisUrl;
    }

    public TaakClient() {
    }

    public TaakClient(String basisUrl) {
        this.basisUrl = basisUrl;
    }

    public List<Taak> alleAfgerondeTaken(String soortEntiteit, Long entiteitId) throws IOException, JAXBException {
        String uri = basisUrl + URL_AFGERONDE_TAKEN + "/" + soortEntiteit + "/" + entiteitId;
        URL url = new URL(uri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/xml");

        InputStream xml = connection.getInputStream();

        XmlMapper mapper = new XmlMapper();
        OpvragenAfgerondeTakenResponse opvragenAfgerondeTakenResponse = mapper.readValue(xml, OpvragenAfgerondeTakenResponse.class);

        connection.disconnect();

        return opvragenAfgerondeTakenResponse.getTaken();
    }
}
