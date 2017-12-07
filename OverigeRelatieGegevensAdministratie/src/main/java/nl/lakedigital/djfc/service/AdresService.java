package nl.lakedigital.djfc.service;

import nl.lakedigital.as.messaging.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.djfc.domain.Adres;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.messaging.sender.EntiteitenOpgeslagenRequestSender;
import nl.lakedigital.djfc.repository.AbstractRepository;
import nl.lakedigital.djfc.repository.AdresRepository;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdresService extends AbstractService<Adres> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdresService.class);

    @Inject
    private AdresRepository adresRepository;
    @Inject
    private EntiteitenOpgeslagenRequestSender entiteitenOpgeslagenRequestSender;

    public AdresService() {
        super(nl.lakedigital.as.messaging.domain.SoortEntiteit.ADRES);
    }

    @Override
    public AbstractRepository getRepository() {
        return adresRepository;
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    @Override
    public void opslaan(final List<Adres> adressen) {
        List<SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIds = new ArrayList<>();

        for (Adres adres : adressen) {
            SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId = new SoortEntiteitEnEntiteitId();
            soortEntiteitEnEntiteitId.setSoortEntiteit(nl.lakedigital.as.messaging.domain.SoortEntiteit.ADRES);
            soortEntiteitEnEntiteitId.setEntiteitId(adres.getId());

            LOGGER.debug("soortEntiteitEnEntiteitId {}", ReflectionToStringBuilder.toString(soortEntiteitEnEntiteitId));

            soortEntiteitEnEntiteitIds.add(soortEntiteitEnEntiteitId);
        }

        adresRepository.opslaan(adressen);

        entiteitenOpgeslagenRequestSender.send(soortEntiteitEnEntiteitIds);
    }

    @Override
    public Adres lees(Long id) {
        return adresRepository.lees(id);
    }

    public List<Adres> alles() {
        return adresRepository.alles();
    }

    public void verwijder(List<Adres> adressen) {
        adresRepository.verwijder(adressen);
    }

    public List<Adres> alleAdressenBijLijstMetEntiteiten(List<Long> ids, SoortEntiteit soortEntiteit) {
        return adresRepository.alleAdressenBijLijstMetEntiteiten(ids, soortEntiteit);
    }

    public List<Adres> zoekOpAdres(String zoekterm) {
        return adresRepository.zoekOpAdres(zoekterm);
    }

    public List<Adres> zoekOpPostcode(String zoekterm) {
        return adresRepository.zoekOpPostcode(zoekterm);
    }

    public List<Adres> zoekOpPlaats(String zoekterm) {
        return adresRepository.zoekOpPlaats(zoekterm);
    }

}
