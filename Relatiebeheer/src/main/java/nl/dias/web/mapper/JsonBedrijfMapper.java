package nl.dias.web.mapper;

import nl.dias.domein.Bedrijf;
import nl.dias.web.mapper.dozer.BedrijfDozerMapper;
import org.springframework.stereotype.Component;

@Component
public class JsonBedrijfMapper extends Mapper<Bedrijf, nl.lakedigital.djfc.domain.response.Bedrijf> {

    @Override
    public Bedrijf mapVanJson(nl.lakedigital.djfc.domain.response.Bedrijf json) {
        //        List<String> myMappingFiles = new ArrayList<>();
        //        myMappingFiles.add("dozer/bedrijf.xml");
        //
        //        DozerBeanMapper mapper = new DozerBeanMapper(myMappingFiles);
        //
        return new BedrijfDozerMapper().convertFrom(json, null);

    }

    @Override
    public nl.lakedigital.djfc.domain.response.Bedrijf mapNaarJson(Bedrijf object) {
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
        nl.lakedigital.djfc.domain.response.Bedrijf jsonBedrijf = new BedrijfDozerMapper().convertTo(object, null);
        //        jsonBedrijf.setOpmerkingen(opmerkingMapper.mapAllNaarJson(object.getOpmerkingen()));
        return jsonBedrijf;
    }
}