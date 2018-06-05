package nl.dias.web.medewerker;

import nl.dias.messaging.sender.NieuweTaakRequestSender;
import nl.lakedigital.djfc.commons.json.Taak;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@RequestMapping("/taak")
@Controller
public class TaakController {
    @Inject
    private NieuweTaakRequestSender nieuweTaakRequestSender;

    @RequestMapping(method = RequestMethod.GET, value = "/opslaan")
    @ResponseBody
    public String opslaan(HttpServletRequest httpServletRequest) {
        Taak taak = new Taak();
        taak.setTitel("a");
        taak.setOmschrijving("b");
        taak.setEntiteitId(4L);
        taak.setSoortEntiteit("RELATIE");

        nieuweTaakRequestSender.send(taak);

        return "abc";
    }
}
