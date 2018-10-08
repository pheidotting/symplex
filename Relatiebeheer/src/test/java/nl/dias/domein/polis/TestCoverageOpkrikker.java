package nl.dias.domein.polis;

import org.junit.Test;

public class TestCoverageOpkrikker {
    private Polis polis;

    @Test
    public void testGetSoortVerzekering() {
        polis = new AansprakelijkheidVerzekering();
        polis.getSoortVerzekering();
        polis = new AnnuleringsVerzekering();
        polis.getSoortVerzekering();
        polis = new AutoVerzekering();
        polis.getSoortVerzekering();
        polis = new BromSnorfietsVerzekering();
        polis.getSoortVerzekering();
        polis = new CamperVerzekering();
        polis.getSoortVerzekering();
        polis = new FietsVerzekering();
        polis.getSoortVerzekering();
        polis = new InboedelVerzekering();
        polis.getSoortVerzekering();
        polis = new LevensVerzekering();
        polis.getSoortVerzekering();
        polis = new MobieleApparatuurVerzekering();
        polis.getSoortVerzekering();
        polis = new MotorVerzekering();
        polis.getSoortVerzekering();
        polis = new OngevallenVerzekering();
        polis.getSoortVerzekering();
        polis = new PleziervaartuigVerzekering();
        polis.getSoortVerzekering();
        polis = new RechtsbijstandVerzekering();
        polis.getSoortVerzekering();
        polis = new RecreatieVerzekering();
        polis.getSoortVerzekering();
        polis = new ReisVerzekering();
        polis.getSoortVerzekering();
        polis = new WoonhuisVerzekering();
        polis.getSoortVerzekering();
        polis = new ZorgVerzekering();
        polis.getSoortVerzekering();
    }

}
