package nl.dias.domein.features;

import org.togglz.core.Feature;
import org.togglz.core.annotation.Label;
import org.togglz.core.context.FeatureContext;

public enum MyFeatures implements Feature {

    @Label("Nieuwe Polis Administratie")
    NIEUWE_POLIS_ADMINISTRATIE,

    @Label("Dummy Feature voor adres ophalen via de API, AAN == niet naar de API, UIT == wel naar de API")
    ADRES_NIET_VIA_API,

    @Label("Koppeling met Todoist")
    TODOIST,

    @Label("Beheerpagina")
    BEHEERPAGINA,

    @Label("Opgenomen gesprekken en voicemails tonen")
    TELEFONIE;

    public boolean isActive() {
        return FeatureContext.getFeatureManager().isActive(this);
    }

}