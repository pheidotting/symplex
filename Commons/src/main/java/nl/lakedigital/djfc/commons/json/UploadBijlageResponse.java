package nl.lakedigital.djfc.commons.json;

public class UploadBijlageResponse {
    private JsonBijlage bijlage;
    private JsonGroepBijlages groepBijlages;

    public JsonBijlage getBijlage() {
        return bijlage;
    }

    public void setBijlage(JsonBijlage bijlage) {
        this.bijlage = bijlage;
    }

    public JsonGroepBijlages getGroepBijlages() {
        return groepBijlages;
    }

    public void setGroepBijlages(JsonGroepBijlages groepBijlages) {
        this.groepBijlages = groepBijlages;
    }
}
