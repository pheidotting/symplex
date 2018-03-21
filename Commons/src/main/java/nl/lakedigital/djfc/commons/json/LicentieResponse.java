package nl.lakedigital.djfc.commons.json;

import java.util.ArrayList;
import java.util.List;

public class LicentieResponse {
    List<Licentie> licenties;

    public List<Licentie> getLicenties() {
        if (licenties == null) {
            licenties = new ArrayList<>();
        }
        return licenties;
    }

    public void addLicentie(Licentie licentie) {
        getLicenties().add(licentie);
    }

    public void setLicenties(List<Licentie> licenties) {
        this.licenties = licenties;
    }
}
