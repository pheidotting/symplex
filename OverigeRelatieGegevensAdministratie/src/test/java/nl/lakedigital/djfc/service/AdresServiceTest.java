package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.domain.Adres;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.repository.AbstractRepository;
import nl.lakedigital.djfc.repository.AdresRepository;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.expect;

public class AdresServiceTest extends AbstractServicetTest<Adres> {
    @TestSubject
    private AdresService adresService = new AdresService();
    @Mock
    private AdresRepository adresRepository;

    @Override
    public AbstractService getService() {
        return adresService;
    }

    @Override
    public AbstractRepository getRepository() {
        return adresRepository;
    }

    @Override
    public Adres getMinimaalGevuldeEntiteit() {
        Adres adres = new Adres();
        adres.setPostcode("1234AA");
        adres.setHuisnummer(2L);
        return adres;
    }

    @Override
    public Adres getGevuldeEntiteit() {
        Adres adres = new Adres();
        adres.setPostcode("1234AA");
        adres.setHuisnummer(2L);

        return adres;
    }

    @Override
    public Adres getGevuldeBestaandeEntiteit() {
        Adres bestaand = new Adres();
        bestaand.setId(1L);
        bestaand.setPostcode("1234AA");
        bestaand.setHuisnummer(2L);

        return bestaand;
    }

    @Override
    public Adres getTeVerwijderenEntiteit() {
        Adres teVerwijderen = new Adres();
        teVerwijderen.setId(2L);
        teVerwijderen.setPostcode("2345BB");
        teVerwijderen.setHuisnummer(2L);

        return teVerwijderen;
    }

    @Override
    public void testOpslaan() throws Exception {
        super.testOpslaan();
    }

    @Override
    public void testOpslaanLijst() throws Exception {
        super.testOpslaanLijst();
    }

    @Test
    public void testLees() {
        Adres adres = new Adres();
        Long id = 5L;

        expect(getRepository().lees(id)).andReturn(adres);

        replayAll();

        getService().lees(id);

        verifyAll();
    }

    @Test
    public void alleAdressenBijLijstMetEntiteiten() {
        List<Long> lijst = new ArrayList<>();
        List<Adres> ret = new ArrayList<>();
        SoortEntiteit soortEntiteit = SoortEntiteit.RELATIE;

        expect(adresRepository.alleAdressenBijLijstMetEntiteiten(lijst, soortEntiteit)).andReturn(ret);

        replayAll();

        adresService.alleAdressenBijLijstMetEntiteiten(lijst, soortEntiteit);

        verifyAll();
    }
}