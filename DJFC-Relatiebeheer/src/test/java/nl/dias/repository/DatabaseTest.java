package nl.dias.repository;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-unittest.xml")
public abstract class DatabaseTest {
    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    public void init() {
        entityManager = entityManager.getEntityManagerFactory().createEntityManager();
    }

    @Before
    public void cleanUp() {
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from Aangifte").executeUpdate();
        entityManager.createQuery("delete from Adres").executeUpdate();
        entityManager.createQuery("delete from Bedrijf").executeUpdate();
        entityManager.createQuery("delete from Bijlage").executeUpdate();
        entityManager.createQuery("delete from Gebruiker").executeUpdate();
        entityManager.createQuery("delete from Hypotheek").executeUpdate();
        entityManager.createQuery("delete from Kantoor").executeUpdate();
        entityManager.createQuery("delete from JaarCijfers").executeUpdate();
        entityManager.createQuery("delete from Opmerking").executeUpdate();
        entityManager.createQuery("delete from RekeningNummer").executeUpdate();
        entityManager.createQuery("delete from RisicoAnalyse").executeUpdate();
        entityManager.createQuery("delete from Schade").executeUpdate();
        entityManager.createQuery("delete from Sessie").executeUpdate();
        entityManager.createQuery("delete from Polis").executeUpdate();
        entityManager.createQuery("delete from Telefoonnummer").executeUpdate();
        entityManager.getTransaction().commit();
    }
}
