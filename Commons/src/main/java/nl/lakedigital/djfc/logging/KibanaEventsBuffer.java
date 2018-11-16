package nl.lakedigital.djfc.logging;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.*;
import com.sun.jersey.api.client.filter.ClientFilter;

public class KibanaEventsBuffer {
    private static String token = "bnZJezPfDeAFkfVDgKRXhSbaEAkYNvNG";

    public static void log(KibanaEvent kibanaEvent) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String s = gson.toJson(kibanaEvent).replace("\n", "");
        Client client = Client.create();

        client.addFilter(new ClientFilter() {
            @Override
            public ClientResponse handle(ClientRequest clientRequest) throws ClientHandlerException {
                ClientResponse response = getNext().handle(clientRequest);

                return response;
            }
        });

        WebResource webResource = client.resource("http://listener.logz.io:8070/?token=" + token);
        webResource.accept("application/json").type("application/json").post(ClientResponse.class, s);
    }
}
