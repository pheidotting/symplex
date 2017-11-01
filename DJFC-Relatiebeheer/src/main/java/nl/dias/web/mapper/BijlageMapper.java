//package nl.dias.web.mapper;
//
//import nl.dias.domein.Bijlage;
//import nl.dias.web.SoortEntiteit;
//import nl.lakedigital.djfc.commons.json.JsonBijlage;
//import org.apache.commons.lang.builder.ReflectionToStringBuilder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//@Component
//public class BijlageMapper extends Mapper<Bijlage, JsonBijlage> {
//    private static final Logger LOGGER = LoggerFactory.getLogger(BijlageMapper.class);
//
//    @Override
//    public Bijlage mapVanJson(JsonBijlage json) {
//        LOGGER.debug("{}", json);
//
//        Bijlage bijlage = new Bijlage();
//        if (json.getId() != null) {
//            bijlage.setId(Long.valueOf(json.getId()));
//        }
//        bijlage.setOmschrijving(json.getOmschrijvingOfBestandsNaam());
//        bijlage.setSoortBijlage(SoortEntiteit.valueOf(json.getSoortBijlage().toUpperCase()));
//
//        LOGGER.debug("{}", bijlage);
//        return bijlage;
//    }
//
//    @Override
//    public JsonBijlage mapNaarJson(Bijlage bijlage) {
//
//        JsonBijlage json = new JsonBijlage();
//        json.setId(bijlage.getId() == null ? null : bijlage.getId().toString());
//        if (bijlage.getOmschrijving() != null) {
//            json.setOmschrijvingOfBestandsNaam(bijlage.getOmschrijving());
//        } else {
//            json.setOmschrijvingOfBestandsNaam(bijlage.getBestandsNaam());
//        }
//        json.setDatumUpload(bijlage.getUploadMoment().toString("dd-MM-yyyy HH:mm"));
//        json.setBestandsNaam(bijlage.getBestandsNaam());
//        json.setParentId(bijlage.getEntiteitId());
//        json.setSoortBijlage(bijlage.getSoortBijlage().toString());
//
//        LOGGER.debug("In  : " + ReflectionToStringBuilder.toString(bijlage));
//        LOGGER.debug("Uit : " + ReflectionToStringBuilder.toString(json));
//
//        return json;
//    }
//}
