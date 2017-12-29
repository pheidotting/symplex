package nl.dias.domein.polis;

import java.io.Serializable;
import java.util.Comparator;

public class PolisComperator implements Comparator<Polis>, Serializable {
    private static final long serialVersionUID = -8269884964794863370L;

    @Override
    public int compare(Polis o1, Polis o2) {
        return o1.getClass().toString().compareTo(o2.getClass().toString());
    }

}
