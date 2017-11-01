package nl.dias.it;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class Stub {
    private String url;
    private String response;

    public Stub(String url, String response, boolean equal) {
        this.url = url;
        this.response = response;

        if (equal) {
            stubFor(get(urlPathEqualTo(url))//
                    .withHeader("Accept", equalTo("application/xml"))//
                    .willReturn(aResponse().withStatus(200)//
                            .withHeader("Content-Type", "application/xml")//
                            .withBody(response)));
        } else {
            stubFor(any(urlPathMatching(url))//
                    .withHeader("Accept", equalTo("application/xml"))//
                    .willReturn(aResponse().withStatus(200)//
                            .withHeader("Content-Type", "application/xml")//
                            .withBody(response)));
        }
    }

    public void verifyStub(String id) {
        verify(getRequestedFor(urlEqualTo(url.replace("([0-9]*)", id))));
    }
}
