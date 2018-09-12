//package nl.lakedigital.djfc.web.controller;
//
//import nl.lakedigital.djfc.commons.json.JsonVerzekeringsMaatschappij;
//import nl.lakedigital.djfc.commons.json.OpvragenJsonVerzekeringsMaatschappijenResponse;
//import nl.lakedigital.djfc.domain.VerzekeringsMaatschappij;
//import nl.lakedigital.djfc.mapper.Mapper;
//import nl.lakedigital.djfc.service.VerzekeringsMaatschappijService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.inject.Inject;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RequestMapping("/verzekeringsmaatschappij")
//@Controller
//public class VerzekeringsMaatschappijController {
//    private static final Logger LOGGER = LoggerFactory.getLogger(VerzekeringsMaatschappijController.class);
//
//    @Inject
//    private VerzekeringsMaatschappijService maatschappijService;
//    @Inject
//    private Mapper mapper;
//
//    @RequestMapping(method = RequestMethod.GET, value = "/lijstVerzekeringsMaatschappijen")
//    @ResponseBody
//    public OpvragenJsonVerzekeringsMaatschappijenResponse lijstVerzekeringsMaatschappijen() {
//        LOGGER.debug("ophalen lijst met VerzekeringsMaatschappijen");
//
//        List<VerzekeringsMaatschappij> lijst = maatschappijService.alles();
//
//        LOGGER.debug("Gevonden, " + lijst.size() + " VerzekeringsMaatschappijen");
//
//        List<JsonVerzekeringsMaatschappij> result = lijst.stream().map(verzekeringsMaatschappij -> {
//
//            JsonVerzekeringsMaatschappij jsonVerzekeringsMaatschappij = new JsonVerzekeringsMaatschappij();
//
//            jsonVerzekeringsMaatschappij.setId(verzekeringsMaatschappij.getId());
//            jsonVerzekeringsMaatschappij.setNaam(verzekeringsMaatschappij.getNaam());
//
//            return jsonVerzekeringsMaatschappij;
//        }).collect(Collectors.toList());
//
//        OpvragenJsonVerzekeringsMaatschappijenResponse opvragenJsonVerzekeringsMaatschappijenResponse = new OpvragenJsonVerzekeringsMaatschappijenResponse();
//        opvragenJsonVerzekeringsMaatschappijenResponse.setJsonVerzekeringsMaatschappijs(result);
//
//        return opvragenJsonVerzekeringsMaatschappijenResponse;
//    }
//}
