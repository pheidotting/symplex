package nl.dias.web.medewerker;

import nl.dias.messaging.sender.OpslaanTaakRequestSender;
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
    private OpslaanTaakRequestSender nieuweTaakRequestSender;

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

    @RequestMapping(method = RequestMethod.GET, value = "/opslan")
    @ResponseBody
    public String opslan(HttpServletRequest httpServletRequest) {
        Taak taak = new Taak();
        taak.setIdentificatie("fa15f345-5feb-4fd4-9c0b-dcf48682c6de");
        taak.setTitel("a");
        taak.setOmschrijving("b");
        taak.setEntiteitId(4L);
        taak.setSoortEntiteit("RELATIE");
        taak.setToegewezenAan(3L);
        taak.setStatus("AFGEROND");

        nieuweTaakRequestSender.send(taak);

        return "abc";
    }
}
