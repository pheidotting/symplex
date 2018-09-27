package nl.lakedigital.djfc.web.controller;

import nl.lakedigital.djfc.commons.domain.uitgaand.UitgaandeOpdracht;
import nl.lakedigital.djfc.commons.xml.OpvragenOpdrachtenStatusResponse;
import nl.lakedigital.djfc.repository.UitgaandeOpdrachtRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.List;

@RequestMapping("/opdracht")
@Controller
public class OpdrachtenController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpdrachtenController.class);

    @Inject
    private UitgaandeOpdrachtRepository uitgaandeOpdrachtRepository;


    @RequestMapping(method = RequestMethod.GET, value = "/status/{trackandtraceid}")
    @ResponseBody
    public OpvragenOpdrachtenStatusResponse alleParticulierePolisSoorten(@PathVariable("trackandtraceid") String trackandtraceid) {
        List<UitgaandeOpdracht> uitgaandeOpdrachten = uitgaandeOpdrachtRepository.zoekObvTrackAndTrackeId(trackandtraceid);

        return new OpvragenOpdrachtenStatusResponse(uitgaandeOpdrachten.isEmpty() ? OpvragenOpdrachtenStatusResponse.Status.KLAAR : OpvragenOpdrachtenStatusResponse.Status.BEZIG);
    }

}
