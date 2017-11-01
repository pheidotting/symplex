package nl.lakedigital.djfc.commons.json;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class JsonGroepBijlages {
    private Long id;
    private String naam;
    private List<JsonBijlage> bijlages;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public List<JsonBijlage> getBijlages() {
        if (bijlages == null) {
            bijlages = newArrayList();
        }
        return bijlages;
    }

    public void setBijlages(List<JsonBijlage> bijlages) {
        this.bijlages = bijlages;
    }
}
