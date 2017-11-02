package nl.lakedigital.djfc.web.controller;

import nl.lakedigital.djfc.domain.TelefonieBestand;
import nl.lakedigital.djfc.service.InlezenTelefonieBestandenService;
import nl.lakedigital.djfc.service.TelefonieBestandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

@Controller
@RequestMapping("/telefonie")
public class TelefonieController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TelefonieController.class);

    @Inject
    private TelefonieBestandService telefonieBestandService;

    @RequestMapping(method = RequestMethod.GET, value = "/go")
    public void gogo() {
        new InlezenTelefonieBestandenService(telefonieBestandService);
    }

    @RequestMapping("/recordings")
    @ResponseBody
    public Map<String, List<String>> getRecordingsAndVoicemails(@RequestParam List<String> telefoonnummers) {
        LOGGER.debug("Ophalen gesprekken met telefoonnummers {}", telefoonnummers);

        Map<String, List<String>> ret = new HashMap<>();

        for (String telefoonnummer : telefoonnummers) {
            List<TelefonieBestand> telefonieBestands = newArrayList();
            LOGGER.debug("Ophalen met nummer {}", telefoonnummer);
            telefonieBestands.addAll(telefonieBestandService.alleTelefonieBestandenOpTelefoonnummer(telefoonnummer));

            List<String> bestanden = telefonieBestands.stream().map(telefonieBestand -> telefonieBestand.getBestandsnaam()).collect(Collectors.toList());

            ret.put(telefoonnummer, bestanden);
        }

        return ret;
    }
}
