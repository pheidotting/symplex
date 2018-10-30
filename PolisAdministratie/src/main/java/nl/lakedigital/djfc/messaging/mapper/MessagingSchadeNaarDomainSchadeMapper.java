package nl.lakedigital.djfc.messaging.mapper;

import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.domain.Schade;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.domain.Bedrag;
import nl.lakedigital.djfc.service.SchadeService;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public class MessagingSchadeNaarDomainSchadeMapper implements Function<Schade, nl.lakedigital.djfc.domain.Schade> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessagingSchadeNaarDomainSchadeMapper.class);

    private SchadeService schadeService;
    private IdentificatieClient identificatieClient;

    public MessagingSchadeNaarDomainSchadeMapper(SchadeService schadeService, IdentificatieClient identificatieClient) {
        this.schadeService = schadeService;
        this.identificatieClient = identificatieClient;
    }

    @Override
    public nl.lakedigital.djfc.domain.Schade apply(Schade schadeIn) {
        LOGGER.debug("Schade in : {}", ReflectionToStringBuilder.toString(schadeIn));
        nl.lakedigital.djfc.domain.Schade schade;

        if (schadeIn.getIdentificatie() == null || "".equals(schadeIn.getIdentificatie())) {
            schade = new nl.lakedigital.djfc.domain.Schade();
        } else {
            Identificatie identificatie = identificatieClient.zoekIdentificatieCode(schadeIn.getIdentificatie());

            LOGGER.debug(ReflectionToStringBuilder.toString(identificatie));

            schade = schadeService.lees(identificatie.getEntiteitId());
        }

        String patternDatum = "yyyy-MM-dd";
        String patternDatumTijd = "yyyy-MM-dd'T'HH:mm";

        LocalDate datumAfgehandeld = null;
        if (schadeIn.getDatumAfgehandeld() != null && !"".equals(schadeIn.getDatumAfgehandeld())) {
            datumAfgehandeld = LocalDate.parse(schadeIn.getDatumAfgehandeld(), DateTimeFormat.forPattern(patternDatum));
        }
        schade.setDatumAfgehandeld(datumAfgehandeld);

        LocalDateTime datumTijdMelding = null;
        if (schadeIn.getDatumMelding() != null && !"".equals(schadeIn.getDatumMelding())) {
            try {
                datumTijdMelding = LocalDateTime.parse(schadeIn.getDatumMelding(), DateTimeFormat.forPattern(patternDatumTijd));
            } catch (IllegalArgumentException e) {
                datumTijdMelding = LocalDateTime.parse(schadeIn.getDatumMelding(), DateTimeFormat.forPattern(patternDatum));
            }
        }
        schade.setDatumMelding(datumTijdMelding);

        LocalDateTime datumTijdSchade = null;
        if (schadeIn.getDatumSchade() != null && !"".equals(schadeIn.getDatumSchade())) {
            try {
                datumTijdSchade = LocalDateTime.parse(schadeIn.getDatumSchade(), DateTimeFormat.forPattern(patternDatumTijd));
            } catch (IllegalArgumentException e) {
                datumTijdSchade = LocalDateTime.parse(schadeIn.getDatumSchade(), DateTimeFormat.forPattern(patternDatum));
            }
        }
        schade.setDatumSchade(datumTijdSchade);
        if (schadeIn.getEigenRisico() != null) {
            schade.setEigenRisico(new Bedrag(schadeIn.getEigenRisico()));
        }
        schade.setLocatie(schadeIn.getLocatie());
        schade.setOmschrijving(schadeIn.getOmschrijving());
        schade.setSchadeNummerMaatschappij(schadeIn.getSchadeNummerMaatschappij());
        schade.setSchadeNummerTussenPersoon(schadeIn.getSchadeNummerTussenPersoon());
        schade.setIdentificatie(schadeIn.getIdentificatie());

        schadeService.opslaan(schade, schadeIn.getSoortSchade(), schadeIn.getPolis(), schadeIn.getStatusSchade());

        return schade;
    }
}
