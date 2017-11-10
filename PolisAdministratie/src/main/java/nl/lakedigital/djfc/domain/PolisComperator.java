package nl.lakedigital.djfc.domain;

import java.io.Serializable;
import java.util.Comparator;

public class PolisComperator implements Comparator<Polis>, Serializable {
    private static final long serialVersionUID = -8269884964794863370L;

    @Override
    public int compare(Polis o1, Polis o2) {
        if (o1.getClass().toString().equals(o2.getClass().toString())) {
            if (o1.getIngangsDatum().isAfter(o2.getIngangsDatum())) {
                return 1;
            } else if (o1.getIngangsDatum().isBefore(o2.getIngangsDatum())) {
                return -1;
            } else {
                return 0;
            }
        } else {
            return o1.getClass().toString().compareTo(o2.getClass().toString());
        }
    }

}
