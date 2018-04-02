package nl.lakedigital.djfc.web.controller;

import nl.lakedigital.djfc.commons.json.JsonCommunicatieProduct;
import nl.lakedigital.djfc.domain.CommunicatieProduct;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.mapper.Mapper;
import nl.lakedigital.djfc.service.CommunicatieProductService;
import nl.lakedigital.djfc.service.ontvangen.LeesEnVerwerkEmailService;
import nl.lakedigital.djfc.service.verzenden.VerzendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/communicatieproduct")
public class CommunicatieProductController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommunicatieProductController.class);

    @Inject
    private CommunicatieProductService communicatieProductService;
    @Inject
    private Mapper mapper;
    @Inject
    private LeesEnVerwerkEmailService leesEnVerwerkEmailService;
    @Inject
    private VerzendService verzendService;

    @RequestMapping(method = RequestMethod.GET, value = "/alles/{soortentiteit}/{parentid}")
    @ResponseBody
    public List<JsonCommunicatieProduct> alles(@PathVariable("soortentiteit") String soortentiteit, @PathVariable("parentid") Long parentid) {
        List<JsonCommunicatieProduct> result = new ArrayList<>();

        for (CommunicatieProduct communicatieProduct : communicatieProductService.lijst(SoortEntiteit.valueOf(soortentiteit), parentid)) {
            result.add(mapper.map(communicatieProduct, JsonCommunicatieProduct.class));
        }

        return result;
    }

    //    @RequestMapping(method = RequestMethod.POST, value = "/nieuw")
    //    @ResponseBody
    //    public Long nieuwCommunicatieProduct(@RequestBody OpslaanCommunicatieProduct opslaanCommunicatieProduct) {
    //        LOGGER.debug("Opslaan {}", ReflectionToStringBuilder.toString(opslaanCommunicatieProduct, ToStringStyle.SHORT_PREFIX_STYLE));
    //
    //        CommunicatieProductService.SoortCommunicatieProduct soort = null;
    //        if (opslaanCommunicatieProduct.getSoortCommunicatieProduct() != null) {
    //            soort = CommunicatieProductService.SoortCommunicatieProduct.valueOf(opslaanCommunicatieProduct.getSoortCommunicatieProduct().name());
    //        }
    //
    //        return communicatieProductService.maakCommunicatieProduct(opslaanCommunicatieProduct.getId(), //
    //                soort,//
    //                opslaanCommunicatieProduct.getParentid(), opslaanCommunicatieProduct.getTekst(), opslaanCommunicatieProduct.getOnderwerp(), null, opslaanCommunicatieProduct.getMedewerker());
    //    }

    @RequestMapping(method = RequestMethod.POST, value = "/versturen/{id}")
    @ResponseBody
    public void versturen(@PathVariable Long id) {
        LOGGER.debug("Klaarzetten om te versturen, CommunicatieProduct met id {}", id);

        communicatieProductService.verstuur(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/markeerAlsGelezen/{id}")
    @ResponseBody
    public void markeerAlsGelezen(@PathVariable Long id) {
        communicatieProductService.markeerAlsGelezen(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/leesmails")
    @ResponseBody
    public void leesMails() {
        leesEnVerwerkEmailService.leesEnVerwerkEmails();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/verstuur")
    @ResponseBody
    public void verstuur() {
        verzendService.verzend();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/markeerOmTeVerzenden/{id}")
    @ResponseBody
    public void markeerOmTeVerzenden(@PathVariable("id") Long id) {
        communicatieProductService.markeerOmTeVerzenden(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lees/{id}")
    @ResponseBody
    public JsonCommunicatieProduct lees(@PathVariable("id") Long id) {
        return mapper.map(communicatieProductService.lees(id), JsonCommunicatieProduct.class);
    }

}
