//package nl.lakedigital.djfc.mapper;
//
//import nl.lakedigital.djfc.commons.json.JsonVerzekeringsMaatschappij;
//import nl.lakedigital.djfc.domain.VerzekeringsMaatschappij;
//
//public class VerzekeringsMaatschappijNaarJsonVerzekeringsMaatschappijMapper extends AbstractMapper<VerzekeringsMaatschappij, JsonVerzekeringsMaatschappij> implements JsonMapper {
//    @Override
//    public JsonVerzekeringsMaatschappij map(VerzekeringsMaatschappij verzekeringsMaatschappij, Object parent, Object bestaandObject) {
//        JsonVerzekeringsMaatschappij jsonVerzekeringsMaatschappij = new JsonVerzekeringsMaatschappij();
//
//        jsonVerzekeringsMaatschappij.setId(verzekeringsMaatschappij.getId());
//        jsonVerzekeringsMaatschappij.setNaam(verzekeringsMaatschappij.getNaam());
//
//        return jsonVerzekeringsMaatschappij;
//    }
//
//    @Override
//    public boolean isVoorMij(Object object) {
//        return object instanceof VerzekeringsMaatschappij;
//    }
//}
