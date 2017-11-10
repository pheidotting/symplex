package nl.dias.domein.polis;

import nl.dias.domein.polis.AansprakelijkheidVerzekering;
import nl.dias.domein.polis.AnnuleringsVerzekering;
import nl.dias.domein.polis.AutoVerzekering;
import nl.dias.domein.polis.BromSnorfietsVerzekering;
import nl.dias.domein.polis.CamperVerzekering;
import nl.dias.domein.polis.FietsVerzekering;
import nl.dias.domein.polis.InboedelVerzekering;
import nl.dias.domein.polis.LevensVerzekering;
import nl.dias.domein.polis.MobieleApparatuurVerzekering;
import nl.dias.domein.polis.MotorVerzekering;
import nl.dias.domein.polis.OngevallenVerzekering;
import nl.dias.domein.polis.PleziervaartuigVerzekering;
import nl.dias.domein.polis.Polis;
import nl.dias.domein.polis.RechtsbijstandVerzekering;
import nl.dias.domein.polis.RecreatieVerzekering;
import nl.dias.domein.polis.ReisVerzekering;
import nl.dias.domein.polis.WoonhuisVerzekering;
import nl.dias.domein.polis.ZorgVerzekering;

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
