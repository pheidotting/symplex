package nl.lakedigital.djfc.web.servlet;

import nl.lakedigital.djfc.domain.Adres;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.service.AdresService;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;

@RunWith(EasyMockRunner.class)
public class VerwijderDuplicaatAdressenTest extends EasyMockSupport {

    @TestSubject
    private VerwijderDuplicaatAdressen verwijderDuplicaatAdressen = new VerwijderDuplicaatAdressen();

    @Mock
    private AdresService adresService;

    @Test
    public void testRun() throws Exception {
        List<Adres> adressen = newArrayList();
        Adres adres1 = maakAdres(1L, "straat", 3L, "", "1234AA", "plaats", Adres.SoortAdres.POSTADRES, SoortEntiteit.RELATIE, 4L);
        Adres adres2 = maakAdres(2L, "straat", 3L, null, "1234AA", "plaats", Adres.SoortAdres.POSTADRES, SoortEntiteit.RELATIE, 4L);

        adressen.add(adres1);
        adressen.add(adres2);

        expect(adresService.alles()).andReturn(adressen);

        Adres adresZonderLegeString = adres1;
        adresZonderLegeString.setToevoeging(null);

        adresService.opslaan(newArrayList(adresZonderLegeString, adres2));
        expectLastCall();

        adresService.verwijder(newArrayList(adres2));
        expectLastCall();

        replayAll();

        verwijderDuplicaatAdressen.run();

        verifyAll();
    }

    private Adres maakAdres(Long id, String straat, Long huisnummer, String toevoeging, String postcode, String plaats, Adres.SoortAdres soortAdres, SoortEntiteit soortEntiteit, Long entiteitId) {
        Adres adres = new Adres();

        adres.setId(id);
        adres.setStraat(straat);
        adres.setHuisnummer(huisnummer);
        adres.setToevoeging(toevoeging);
        adres.setPostcode(postcode);
        adres.setPlaats(plaats);
        adres.setSoortAdres(soortAdres);
        adres.setSoortEntiteit(soortEntiteit);
        adres.setEntiteitId(entiteitId);

        return adres;
    }
}