package nl.lakedigital.djfc.commons.xml;

public class OpvragenOpdrachtenStatusResponse {
    public enum Status {
        BEZIG, KLAAR;
    }

    private Status status;

    public OpvragenOpdrachtenStatusResponse() {
    }

    public OpvragenOpdrachtenStatusResponse(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
