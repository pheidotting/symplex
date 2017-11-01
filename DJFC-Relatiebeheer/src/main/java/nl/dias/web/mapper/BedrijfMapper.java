package nl.dias.web.mapper;

import nl.dias.domein.Bedrijf;
import nl.dias.web.mapper.dozer.AdresDozerMapper;
import nl.lakedigital.djfc.commons.json.JsonBedrijf;
import org.springframework.stereotype.Component;

@Component
public class BedrijfMapper extends Mapper<Bedrijf, JsonBedrijf> {

    @Override
    public Bedrijf mapVanJson(JsonBedrijf json) {
        //        List<String> myMappingFiles = new ArrayList<>();
        //        myMappingFiles.add("dozer/bedrijf.xml");
        //
        //        DozerBeanMapper mapper = new DozerBeanMapper(myMappingFiles);
        //
        return new AdresDozerMapper().convertFrom(json, null);

    }

    @Override
    public JsonBedrijf mapNaarJson(Bedrijf object) {
        //        DozerBeanMapper mapper = new DozerBeanMapper();
        //        mapper.addMapping(new BeanMappingBuilder() {
        //            @Override
        //            protected void configure() {
        //                mapping(JsonAangifte.class, Aangifte.class).fields("datumAfgerond", "datumAfgerond", FieldsMappingOptions.customConverter(LocalDateDozerConverter.class))
        //                        .fields("uitstelTot", "uitstelTot", FieldsMappingOptions.customConverter(LocalDateDozerConverter.class))
        //                        .fields("relatie", "relatie", FieldsMappingOptions.customConverter(RelatieDozerMapper.class));
        //            }
        //        });
        //
        //        return mapper.map(object, JsonBedrijf.class);
        JsonBedrijf jsonBedrijf = new AdresDozerMapper().convertTo(object, null);
        //        jsonBedrijf.setOpmerkingen(opmerkingMapper.mapAllNaarJson(object.getOpmerkingen()));
        return jsonBedrijf;
    }
}