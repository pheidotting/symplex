package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.domain.Telefoonnummer;
import nl.lakedigital.djfc.repository.AbstractRepository;
import nl.lakedigital.djfc.repository.TelefoonnummerRepository;
import org.easymock.Mock;
import org.easymock.TestSubject;

public class TelefoonnummerServiceTest extends AbstractServicetTest<Telefoonnummer> {
    @TestSubject
    private TelefoonnummerService telefoonnummerService = new TelefoonnummerService();
    @Mock
    private TelefoonnummerRepository telefoonnummerRepository;

    @Override
    public AbstractService getService() {
        return telefoonnummerService;
    }

    @Override
    public AbstractRepository getRepository() {
        return telefoonnummerRepository;
    }

    @Override
    public Telefoonnummer getMinimaalGevuldeEntiteit() {
        Telefoonnummer telefoonnummer = new Telefoonnummer();
        telefoonnummer.setTelefoonnummer("0612345678");

        return telefoonnummer;
    }

    @Override
    public Telefoonnummer getGevuldeEntiteit() {
        Telefoonnummer telefoonnummer = new Telefoonnummer();
        telefoonnummer.setTelefoonnummer("1");

        return telefoonnummer;
    }

    @Override
    public Telefoonnummer getGevuldeBestaandeEntiteit() {
        Telefoonnummer telefoonnummer = new Telefoonnummer();
        telefoonnummer.setTelefoonnummer("2");
        telefoonnummer.setId(2L);

        return telefoonnummer;
    }

    @Override
    public Telefoonnummer getTeVerwijderenEntiteit() {
        Telefoonnummer telefoonnummer = new Telefoonnummer();
        telefoonnummer.setTelefoonnummer("3");
        telefoonnummer.setId(3L);

        return telefoonnummer;
    }
}