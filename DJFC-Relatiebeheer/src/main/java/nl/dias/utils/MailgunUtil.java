//package nl.dias.utils;
//
//import javax.ws.rs.core.MediaType;
//
//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.ClientResponse;
//import com.sun.jersey.api.client.WebResource;
//import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
//import com.sun.jersey.multipart.FormDataMultiPart;
//
//public class MailgunUtil {
//
//    public static void verstuurMail(String onderwerp, String tekst, String aan) {
//        String omgeving = System.getProperty("omgeving");
//        if (omgeving == null || "".equals(omgeving)) {
//            return;
//        } else if ("PRD".equals(omgeving)) {
//            omgeving = "";
//        }
//
//        Client client = Client.create();
//        client.addFilter(new HTTPBasicAuthFilter("api", "key-3ax6xnjp29jd6fds4gc373sgvjxteol0"));
//        WebResource webResource = client.resource("https://api.mailgun.net/v2/samples.mailgun.org/" + "messages");
//        FormDataMultiPart form = new FormDataMultiPart();
//        form.field("from", "Excited User <me@samples.mailgun.org>");
//        form.field("to", aan);
//        // form.field("bcc", "bar@example.com");
//        // form.field("cc", "baz@example.com");
//        form.field("subject", onderwerp + omgeving);
//        form.field("text", tekst);
//        // String file_separator = System.getProperty("file.separator");
//        // File txtFile = new File("." + file_separator + "files" +
//        // file_separator + "test.txt");
//        // form.bodyPart(new FileDataBodyPart("attachment", txtFile,
//        // MediaType.TEXT_PLAIN_TYPE));
//        // File jpgFile = new File("." + file_separator + "files" +
//        // file_separator + "test.jpg");
//        // form.bodyPart(new FileDataBodyPart("attachment", jpgFile,
//        // MediaType.APPLICATION_OCTET_STREAM_TYPE));
//
//        webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).post(ClientResponse.class, form);
//    }
//}
