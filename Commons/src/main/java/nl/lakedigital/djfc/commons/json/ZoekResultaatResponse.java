package nl.lakedigital.djfc.commons.json;

import java.util.ArrayList;
import java.util.List;

public class ZoekResultaatResponse {
    private List<BedrijfOfRelatie> bedrijfOfRelatieList;

    public List<BedrijfOfRelatie> getBedrijfOfRelatieList() {
        if (bedrijfOfRelatieList == null) {
            bedrijfOfRelatieList = new ArrayList<>();
        }
        return bedrijfOfRelatieList;
    }

    public void setBedrijfOfRelatieList(List<BedrijfOfRelatie> bedrijfOfRelatieList) {
        this.bedrijfOfRelatieList = bedrijfOfRelatieList;
    }
}
