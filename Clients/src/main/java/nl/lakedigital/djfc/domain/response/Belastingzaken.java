package nl.lakedigital.djfc.domain.response;

import java.util.ArrayList;
import java.util.List;

public class Belastingzaken {
    private Contracten contracten;
    private List<Jaarrekening> jaarrekeningen;
    private List<IB> ibs;
    private List<Btw> btws;
    private List<Loonbelasting> loonbelastingen;
    private List<Overig> overigen;

    public Contracten getContracten() {
        return contracten;
    }

    public void setContracten(Contracten contracten) {
        this.contracten = contracten;
    }

    public List<Jaarrekening> getJaarrekeningen() {
        if (jaarrekeningen == null) {
            jaarrekeningen = new ArrayList<>();
        }
        return jaarrekeningen;
    }

    public void setJaarrekeningen(List<Jaarrekening> jaarrekeningen) {
        this.jaarrekeningen = jaarrekeningen;
    }

    public List<IB> getIbs() {
        if (ibs == null) {
            ibs = new ArrayList<>();
        }
        return ibs;
    }

    public void setIbs(List<IB> ibs) {
        this.ibs = ibs;
    }

    public List<Btw> getBtws() {
        if (btws == null) {
            btws = new ArrayList<>();
        }
        return btws;
    }

    public void setBtws(List<Btw> btws) {
        this.btws = btws;
    }

    public List<Loonbelasting> getLoonbelastingen() {
        if (loonbelastingen == null) {
            loonbelastingen = new ArrayList<>();
        }
        return loonbelastingen;
    }

    public void setLoonbelastingen(List<Loonbelasting> loonbelastingen) {
        this.loonbelastingen = loonbelastingen;
    }

    public List<Overig> getOverigen() {
        if (overigen == null) {
            overigen = new ArrayList<>();
        }
        return overigen;
    }

    public void setOverigen(List<Overig> overigen) {
        this.overigen = overigen;
    }
}
