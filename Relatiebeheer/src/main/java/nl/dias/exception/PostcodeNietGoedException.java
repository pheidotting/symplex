package nl.dias.exception;

public class PostcodeNietGoedException extends Exception {
    private static final long serialVersionUID = 1322640773874011911L;

    private String postcode;

    public PostcodeNietGoedException(String postcode) {
        this.postcode = postcode;
    }

    @Override
    public String getMessage() {
        return "De ingevoerde postcode '" + postcode + "' is niet correct";
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
