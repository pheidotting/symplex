package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.domain.RekeningNummer;
import nl.lakedigital.djfc.repository.AbstractRepository;
import nl.lakedigital.djfc.repository.RekeningNummerRepository;
import org.easymock.Mock;
import org.easymock.TestSubject;

public class RekeningNummerServiceTest extends AbstractServicetTest<RekeningNummer> {
    @TestSubject
    private RekeningNummerService rekeningNummerService = new RekeningNummerService();
    @Mock
    private RekeningNummerRepository rekeningNummerRepository;

    @Override
    public AbstractService getService() {
        return rekeningNummerService;
    }

    @Override
    public AbstractRepository getRepository() {
        return rekeningNummerRepository;
    }

    @Override
    public RekeningNummer getMinimaalGevuldeEntiteit() {
        RekeningNummer rekeningNummer = new RekeningNummer();
        rekeningNummer.setRekeningnummer("NL12");
        return rekeningNummer;
    }

    @Override
    public RekeningNummer getGevuldeEntiteit() {
        RekeningNummer rekeningNummer = new RekeningNummer();
        rekeningNummer.setRekeningnummer("4");

        return rekeningNummer;
    }

    @Override
    public RekeningNummer getGevuldeBestaandeEntiteit() {
        RekeningNummer rekeningNummer = new RekeningNummer();
        rekeningNummer.setRekeningnummer("5");
        rekeningNummer.setId(5L);

        return rekeningNummer;
    }

    @Override
    public RekeningNummer getTeVerwijderenEntiteit() {
        RekeningNummer rekeningNummer = new RekeningNummer();
        rekeningNummer.setRekeningnummer("6");
        rekeningNummer.setId(6L);

        return rekeningNummer;
    }
}