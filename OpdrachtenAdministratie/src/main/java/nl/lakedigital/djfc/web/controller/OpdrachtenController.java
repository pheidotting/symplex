package nl.lakedigital.djfc.web.controller;

import nl.lakedigital.djfc.commons.domain.uitgaand.UitgaandeOpdracht;
import nl.lakedigital.djfc.commons.xml.OpvragenOpdrachtenStatusResponse;
import nl.lakedigital.djfc.repository.UitgaandeOpdrachtRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/opdracht")
@Controller
public class OpdrachtenController {
    @Inject
    private UitgaandeOpdrachtRepository uitgaandeOpdrachtRepository;


    @RequestMapping(method = RequestMethod.GET, value = "/status/{trackandtraceid}")
    @ResponseBody
    public OpvragenOpdrachtenStatusResponse alleParticulierePolisSoorten(@PathVariable("trackandtraceid") String trackandtraceid) {
        List<UitgaandeOpdracht> uitgaandeOpdrachten = uitgaandeOpdrachtRepository.zoekObvTrackAndTrackeId(trackandtraceid).stream().filter(uitgaandeOpdracht -> uitgaandeOpdracht.getTijdstipAfgerond() == null).collect(Collectors.toList());

        return new OpvragenOpdrachtenStatusResponse(uitgaandeOpdrachten.isEmpty() ? OpvragenOpdrachtenStatusResponse.Status.KLAAR : OpvragenOpdrachtenStatusResponse.Status.BEZIG);
    }

}
