package nl.lakedigital.djfc.messaging.reciever;

import com.codahale.metrics.Timer;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import nl.lakedigital.as.messaging.request.communicatie.NieuweVersieRequest;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.reflection.ReflectionToStringBuilder;
import nl.lakedigital.djfc.service.CommunicatieProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class NieuweVersieRequestReciever extends AbstractReciever<NieuweVersieRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NieuweVersieRequestReciever.class);

    @Inject
    private MetricsService metricsService;
    @Inject
    private CommunicatieProductService communicatieProductService;

    public NieuweVersieRequestReciever() {
        super(NieuweVersieRequest.class, LOGGER);
    }

    @Override
    @Transactional
    public void verwerkMessage(NieuweVersieRequest nieuweVersieRequest) {
        LOGGER.info("Verwerken {}", ReflectionToStringBuilder.toString(nieuweVersieRequest));

        Timer.Context timer = metricsService.addTimerMetric("verwerkMessage", NieuweVersieRequestReciever.class);

        Map<String, String> var = new HashMap<>();
        var.put("versie", nieuweVersieRequest.getVersie());
        var.put("releasenotes", nieuweVersieRequest.getReleasenotes());

        communicatieProductService.versturen(nieuweVersieRequest.getGeadresseerden(), null, var, CommunicatieProductService.TemplateNaam.NIEUWE_VERSIE);

        String token = new StringBuilder()//
                .append("x")//
                .append("o")//
                .append("x")//
                .append("b")//
                .append("-")//
                .append("3")//
                .append("4")//
                .append("5")//
                .append("5")//
                .append("3")//
                .append("2")//
                .append("0")//
                .append("4")//
                .append("1")//
                .append("3")//
                .append("1")//
                .append("2")//
                .append("-")//
                .append("d")//
                .append("A")//
                .append("N")//
                .append("n")//
                .append("T")//
                .append("3")//
                .append("5")//
                .append("r")//
                .append("u")//
                .append("I")//
                .append("G")//
                .append("s")//
                .append("W")//
                .append("H")//
                .append("b")//
                .append("P")//
                .append("2")//
                .append("1")//
                .append("k")//
                .append("k")//
                .append("A")//
                .append("L")//
                .append("d")//
                .append("W")//
                .toString();

        SlackSession session = SlackSessionFactory.createWebSocketSlackSession(token);
        try {
            session.connect();
        } catch (IOException e) {
            LOGGER.error("Kon geen verbinding maken met Slack {}", e);
        }
        SlackChannel channel = session.findChannelByName("symplex"); //make sure bot is a member of the channel.

        session.sendMessage(channel, "Nieuwe release van Symplex!:\n\n*Versie " + nieuweVersieRequest.getVersie() + "*\n\nDe wijzigingen zijn:\n" + nieuweVersieRequest.getReleasenotes());

        metricsService.stop(timer);
    }
}
