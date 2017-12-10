package nl.symplex.test.builders;

import nl.lakedigital.djfc.domain.response.Hypotheek;
import nl.lakedigital.djfc.domain.response.Opmerking;

public class HypotheekBuilder {
    private Hypotheek hypotheek;

    public HypotheekBuilder() {
        this.hypotheek = new Hypotheek();
    }

    public HypotheekBuilder defaultHypotheek() {
        hypotheek.setHypotheekVorm(1L);
        hypotheek.setOmschrijving("omschrijving");
        hypotheek.setHypotheekBedrag("123456");
        hypotheek.setRente("2");
        //        private String marktWaarde;
        //        private String onderpand;
        //        private String koopsom;
        //        private String vrijeVerkoopWaarde;
        //        private String taxatieDatum;
        //        private String wozWaarde;
        //        private String waardeVoorVerbouwing;
        //        private String waardeNaVerbouwing;
        //        private String ingangsDatum;
        //        private String eindDatum;
        //        private Long duur;
        //        private String ingangsDatumRenteVastePeriode;
        //        private String eindDatumRenteVastePeriode;
        //        private Long duurRenteVastePeriode;
        //        private List<Opmerking> opmerkingen;
        //        private List<Bijlage> bijlages;
        //        private String leningNummer;
        //        private String bank;
        //        private Long gekoppeldeHypotheek;
        //        private String boxI;
        //        private String boxIII;
        //        private Long hypotheekPakket;
        //        private String identificatie;

        return this;
    }

    public HypotheekBuilder metRelatie(String identificatie) {
        hypotheek.setParentIdentificatie(identificatie);
        hypotheek.setSoortEntiteit("RELATIE");

        return this;
    }

    public HypotheekBuilder metOpmerking(Opmerking opmerking) {
        this.hypotheek.getOpmerkingen().add(opmerking);

        return this;
    }

    public Hypotheek build() {
        return hypotheek;
    }
}
