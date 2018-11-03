package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.repository.UitgaandeOpdrachtRepository;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EasyMockRunner.class)
public class VerstuurUitgaandeOpdrachtenServiceTest extends EasyMockSupport {
    @TestSubject
    private VerstuurUitgaandeOpdrachtenService verstuurUitgaandeOpdrachtenService = new VerstuurUitgaandeOpdrachtenService();

    @Mock
    private UitgaandeOpdrachtRepository uitgaandeOpdrachtRepository;

    @Test
    public void test() {

    }

}