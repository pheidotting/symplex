package nl.dias.web.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Mapper<T, Z> {
    public abstract T mapVanJson(Z json);

    public abstract Z mapNaarJson(T object);

    public Set<T> mapAllVanJson(List<Z> jsons) {
        if (jsons != null) {
            Set<T> ret = new HashSet<>();
            for (Z json : jsons) {
                ret.add(mapVanJson(json));
            }
            return ret;
        } else {
            return null;
        }
    }

    public List<Z> mapAllNaarJson(Set<T> objecten) {
        if (objecten != null) {
            List<Z> ret = new ArrayList<>();
            for (T obj : objecten) {
                ret.add(mapNaarJson(obj));
            }
            return ret;
        } else {
            return null;
        }

    }

    public List<Z> mapAllNaarJson(List<T> objecten) {
        if (objecten != null) {
            List<Z> ret = new ArrayList<>();
            for (T obj : objecten) {
                ret.add(mapNaarJson(obj));
            }
            return ret;
        } else {
            return null;
        }

    }
}
