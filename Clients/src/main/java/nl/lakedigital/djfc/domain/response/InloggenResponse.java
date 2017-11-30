package nl.lakedigital.djfc.domain.response;

public class InloggenResponse {
    private Long returnCode;
    private boolean moetWachtwoordUpdaten;

    public InloggenResponse(Long returnCode, boolean moetWachtwoordUpdaten) {
        this.returnCode = returnCode;
        this.moetWachtwoordUpdaten = moetWachtwoordUpdaten;
    }

    public Long getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(Long returnCode) {
        this.returnCode = returnCode;
    }

    public boolean isMoetWachtwoordUpdaten() {
        return moetWachtwoordUpdaten;
    }

    public void setMoetWachtwoordUpdaten(boolean moetWachtwoordUpdaten) {
        this.moetWachtwoordUpdaten = moetWachtwoordUpdaten;
    }
}
