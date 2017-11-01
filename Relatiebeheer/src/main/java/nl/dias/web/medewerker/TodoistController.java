package nl.dias.web.medewerker;

import nl.lakedigital.djfc.commons.json.JsonClientIdEnClientSecret;
import nl.lakedigital.djfc.commons.json.OphalenOauthTokenRequest;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.core.MediaType;

@Controller
@RequestMapping(value = "/todoist")
@Configuration
//@PropertySources({@PropertySource(value = "file:djfc.app.properties", ignoreResourceNotFound = true),
//        @PropertySource(value = "djfc.app.properties", ignoreResourceNotFound = true),
//        @PropertySource(value = "classpath:dev/djfc.app.properties", ignoreResourceNotFound = true)})
public class TodoistController {
    private final static Logger LOGGER = LoggerFactory.getLogger(TodoistController.class);

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigIn() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    //    @Value("${todoist.prefix}")
    private String todoistPrefix;
    //    @Value("${todoist.client.id}")
    private String todoistClientId;
    //    @Value("${todoist.client.secret}")
    private String todoistClientSecret;

    @RequestMapping(method = RequestMethod.POST, value = "/oauthToken", produces = MediaType.TEXT_PLAIN)
    @ResponseBody
    public String oauthToken(@RequestBody OphalenOauthTokenRequest requestIn) throws OAuthSystemException, OAuthProblemException {
        LOGGER.debug("{}", ReflectionToStringBuilder.toString(requestIn));

        String url = "https://todoist.com/oauth/access_token";

        OAuthClientRequest request = OAuthClientRequest.tokenLocation(url).setClientId(requestIn.getClient_id()).setRedirectURI(requestIn.getRedirect_uri()).setClientSecret(requestIn.getClient_secret()).setGrantType(GrantType.AUTHORIZATION_CODE).setCode(requestIn.getCode()).buildQueryMessage();

        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        OAuthJSONAccessTokenResponse oAuthResponse = oAuthClient.accessToken(request);

        return oAuthResponse.getAccessToken();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getPrefix", produces = MediaType.TEXT_PLAIN)
    @ResponseBody
    public String getPrefix() {
        return todoistPrefix;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getClientIdEnClientSecret", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public JsonClientIdEnClientSecret getClientIdEnClientSecret() {
        JsonClientIdEnClientSecret clientIdEnClientSecret = new JsonClientIdEnClientSecret();

        clientIdEnClientSecret.setClientId(todoistClientId);
        clientIdEnClientSecret.setClientSecret(todoistClientSecret);

        return clientIdEnClientSecret;
    }

}
