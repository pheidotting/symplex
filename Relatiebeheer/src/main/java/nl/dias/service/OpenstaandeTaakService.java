package nl.dias.service;

import nl.dias.domein.*;
import nl.dias.domein.polis.Polis;
import nl.lakedigital.djfc.client.taak.TaakClient;
import nl.lakedigital.djfc.commons.json.Taak;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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

    public List<Taak> alleOpenstaandeTaken(Kantoor kantoor) {
        List<Taak> taken = new ArrayList<>();
        gebruikerService.alleRelaties(kantoor, true).stream().forEach(new Consumer<Relatie>() {
            @Override
            public void accept(Relatie relatie) {
                taken.addAll(taakClient.alles("RELATIE", relatie.getId()));

                polisService.allePolissenBijRelatie(relatie.getId()).stream().forEach(new Consumer<Polis>() {
                    @Override
                    public void accept(Polis polis) {
                        taken.addAll(taakClient.alles("POLIS", polis.getId()));

                        schadeService.alleSchadesBijPolis(polis.getId()).stream().forEach(new Consumer<Schade>() {
                            @Override
                            public void accept(Schade schade) {
                                taken.addAll(taakClient.alles("SCHADE", schade.getId()));
                            }
                        });

                        hypotheekService.allesVanRelatie(relatie.getId()).stream().forEach(new Consumer<Hypotheek>() {
                            @Override
                            public void accept(Hypotheek hypotheek) {
                                taken.addAll(taakClient.alles("HYPOTHEEK", hypotheek.getId()));
                            }
                        });
                    }
                });

            }
        });
        bedrijfService.alles().stream().forEach(new Consumer<Bedrijf>() {
            @Override
            public void accept(Bedrijf bedrijf) {
                taken.addAll(taakClient.alles("BEDRIJF", bedrijf.getId()));

                polisService.allePolissenBijBedrijf(bedrijf.getId()).stream().forEach(new Consumer<Polis>() {
                    @Override
                    public void accept(Polis polis) {
                        taken.addAll(taakClient.alles("POLIS", polis.getId()));

                        schadeService.alleSchadesBijPolis(polis.getId()).stream().forEach(new Consumer<Schade>() {
                            @Override
                            public void accept(Schade schade) {
                                taken.addAll(taakClient.alles("SCHADE", schade.getId()));
                            }
                        });
                    }
                });

            }
        });

        return taken;
    }
}
