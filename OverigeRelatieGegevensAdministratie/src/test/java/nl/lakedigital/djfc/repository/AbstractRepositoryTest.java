package nl.lakedigital.djfc.repository;

import nl.lakedigital.djfc.domain.AbstracteEntiteitMetSoortEnId;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class AbstractRepositoryTest<T extends AbstracteEntiteitMetSoortEnId> {
    public abstract T maakEntiteit(String zoekWaarde);

    public abstract AbstractRepository getRepository();

    @Test
    public void zoeken() {
        T t1 = maakEntiteit("abc");
        T t2 = maakEntiteit("bcd");

        List<T> ts = newArrayList(t1, t2);

        getRepository().opslaan(ts);

        assertThat(getRepository().zoek("a").size(), is(1));
        assertEquals(getRepository().zoek("a").get(0), t1);
        assertThat(getRepository().zoek("b").size(), is(2));
        assertTrue(getRepository().zoek("b").contains(t1));
        assertTrue(getRepository().zoek("b").contains(t2));
        assertThat(getRepository().zoek("c").size(), is(2));
        assertTrue(getRepository().zoek("c").contains(t1));
        assertTrue(getRepository().zoek("c").contains(t2));
        assertThat(getRepository().zoek("d").size(), is(1));
        assertEquals(getRepository().zoek("d").get(0), t2);

        getRepository().verwijder(ts);
    }
}
