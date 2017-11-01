package nl.dias.web.mapper.dozer;

import nl.dias.domein.Relatie;
import org.dozer.DozerConverter;

public class RelatieDozerMapper extends DozerConverter<String, Relatie> {

    public RelatieDozerMapper() {
        super(String.class, Relatie.class);
    }

    @Override
    public String convertFrom(Relatie relatie, String arg1) {
        String id = null;
        if (relatie != null && relatie.getId() != null) {
            id = relatie.getId().toString();
        }
        return id;
    }

    @Override
    public Relatie convertTo(String arg0, Relatie arg1) {
        Relatie relatie = new Relatie();
        if (arg0 != null) {
            relatie.setId(Long.valueOf(arg0));
        }

        return relatie;
    }
}
