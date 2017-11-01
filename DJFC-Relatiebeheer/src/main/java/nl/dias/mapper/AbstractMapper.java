package nl.dias.mapper;


import nl.dias.domein.Bedrag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

public abstract class AbstractMapper<T, Z> {

    public Z map(T object) {
        return map(object, null, null);
    }

    public abstract Z map(T object, Object parent, Object bestaandObject);

    abstract boolean isVoorMij(Object object);

    public List<Z> mapAll(Set<T> objecten) {
        if (objecten != null) {
            List<Z> ret = new ArrayList<>();
            for (T obj : objecten) {
                ret.add(map(obj));
            }
            return ret;
        } else {
            return newArrayList();
        }

    }

    public List<Z> mapAll(List<T> objecten) {
        if (objecten != null) {
            List<Z> ret = new ArrayList<>();
            for (T obj : objecten) {
                ret.add(map(obj));
            }
            return ret;
        } else {
            return newArrayList();
        }

    }

    public Set<Z> mapAllNaarSet(List<T> objecten) {
        return mapAllNaarSet(objecten, null);
    }

    public Set<Z> mapAllNaarSet(List<T> objecten, Object parent) {
        if (objecten != null) {
            Set<Z> ret = new HashSet<>();
            for (T obj : objecten) {
                ret.add(map(obj, parent, null));
            }
            return ret;
        } else {
            return newHashSet();
        }
    }

    protected static String zetBedragOm(Bedrag bedrag) {
        String waarde;
        String[] x = bedrag.getBedrag().toString().split("\\.");
        if (x[1].length() == 1) {
            waarde = bedrag.getBedrag().toString() + "0";
        } else {
            waarde = bedrag.getBedrag().toString() + "";
        }
        return waarde;
    }

}