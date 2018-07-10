package nl.dias.service;

import nl.dias.domein.Kantoor;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.client.taak.TaakClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.Taak;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class OpenstaandeTaakService {

    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private BedrijfService bedrijfService;
    @Inject
    private PolisService polisService;
    @Inject
    private SchadeService schadeService;
    @Inject
    private HypotheekService hypotheekService;
    @Inject
    private TaakClient taakClient;
    @Inject
    private IdentificatieClient identificatieClient;

    public List<Taak> alleOpenstaandeTaken(Kantoor kantoor) {
        List<Taak> taken = new ArrayList<>();
        gebruikerService.alleRelaties(kantoor, true).stream().forEach(relatie -> {
            taken.addAll(taakClient.alles("RELATIE", relatie.getId()));

            polisService.allePolissenBijRelatie(relatie.getId()).stream().forEach(polis -> {
                taken.addAll(taakClient.alles("POLIS", polis.getId()));

                schadeService.alleSchadesBijPolis(polis.getId()).stream().forEach(schade -> taken.addAll(taakClient.alles("SCHADE", schade.getId())));

                hypotheekService.allesVanRelatie(relatie.getId()).stream().forEach(hypotheek -> taken.addAll(taakClient.alles("HYPOTHEEK", hypotheek.getId())));
            });

        });
        bedrijfService.alles().stream().forEach(bedrijf -> {
            taken.addAll(taakClient.alles("BEDRIJF", bedrijf.getId()));

            polisService.allePolissenBijBedrijf(bedrijf.getId()).stream().forEach(polis -> {
                taken.addAll(taakClient.alles("POLIS", polis.getId()));

                schadeService.alleSchadesBijPolis(polis.getId()).stream().forEach(schade -> taken.addAll(taakClient.alles("SCHADE", schade.getId())));
            });

        });

        taken.stream().forEach(taak -> {
            Identificatie identificatie = identificatieClient.zoekIdentificatie("TAAK", taak.getId());
            taak.setIdentificatie(identificatie.getIdentificatie());

            taak.setId(null);

            taak.getWijzigingTaaks().stream().forEach(wijzigingTaak -> {
                if (wijzigingTaak.getToegewezenAan() != null) {
                    Identificatie identificatieM = identificatieClient.zoekIdentificatie("MEDEWERKER", Long.valueOf(wijzigingTaak.getToegewezenAan()));
                    wijzigingTaak.setToegewezenAan(identificatieM.getIdentificatie());
                }

                Identificatie identificatieW = identificatieClient.zoekIdentificatie("WIJZIGINGTAAK", wijzigingTaak.getId());
                wijzigingTaak.setIdentificatie(identificatieW.getIdentificatie());

                wijzigingTaak.setId(null);

            });
        });

        return taken;
    }
}
