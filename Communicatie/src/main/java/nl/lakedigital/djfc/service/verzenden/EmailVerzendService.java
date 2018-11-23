package nl.lakedigital.djfc.service.verzenden;

import com.google.common.util.concurrent.RateLimiter;
import nl.lakedigital.djfc.client.oga.BijlageClient;
import nl.lakedigital.djfc.commons.domain.CommunicatieProduct;
import nl.lakedigital.djfc.commons.domain.UitgaandeEmail;
import nl.lakedigital.djfc.commons.json.JsonBijlage;
import nl.lakedigital.djfc.repository.CommunicatieProductRepository;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Service
@PropertySource(value = "file:app.properties", ignoreResourceNotFound = true)
@PropertySource(value = "file:comm.app.properties", ignoreResourceNotFound = true)
public class EmailVerzendService implements AbstractVerzendService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailVerzendService.class);

    @Value("${mailHost}")
    private String mailHost;// = "localhost";
    @Value("${smtpPort}")
    private String smtpPort;// = 2345;

    @Inject
    private CommunicatieProductRepository communicatieProductRepository;
    //    @Inject
    private BijlageClient bijlageClient;
    private RateLimiter rateLimiter;

    @PostConstruct
    public void init() {
        rateLimiter = RateLimiter.create(1);
    }

    @Override
    public boolean isVoorMij(CommunicatieProduct communicatieProduct) {
        return communicatieProduct instanceof UitgaandeEmail;
    }

    public void verzend(CommunicatieProduct communicatieProduct) {
        try {
            LOGGER.debug("Verbinding maken met {}", mailHost);
            LOGGER.debug("smtp port {}", smtpPort);

            Properties properties = new Properties();
            properties.put("mail.smtp.host", mailHost);
            properties.put("mail.smtp.port", smtpPort);

                        Session emailSession = Session.getDefaultInstance(properties, null);

            UitgaandeEmail uitgaandeEmail = (UitgaandeEmail) communicatieProduct;

            StringBuilder afzender = new StringBuilder();
            afzender.append(uitgaandeEmail.getNaamVerzender());
            afzender.append("<");
            afzender.append(uitgaandeEmail.getEmailVerzender());
            afzender.append(">");

            Message msg = new MimeMessage(emailSession);

            // -- Set the FROM and TO fields --
            msg.setFrom(new InternetAddress(afzender.toString()));

            StringBuilder ontvanger = new StringBuilder();
            ontvanger.append(uitgaandeEmail.getNaamOntvanger());
            ontvanger.append(" <");
            ontvanger.append(uitgaandeEmail.getEmailOntvanger());
            ontvanger.append(">");

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(ontvanger.toString(), false));
            msg.setSubject(uitgaandeEmail.getOnderwerp());
            msg.setSentDate(new Date());

            BodyPart messageBodyPart = new MimeBodyPart();

            //evt bijlages bijzoeken
            List<JsonBijlage> bijlages = new ArrayList<>();//bijlageClient.lijst("COMMUNICATIEPRODUCT", uitgaandeEmail.getId());

            // Create a multipar message
            Multipart multipart = new MimeMultipart();
            //                 Now set the actual message
            messageBodyPart.setContent(uitgaandeEmail.getTekst() + "\n", "text/html");
            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            for (JsonBijlage bijlage : bijlages) {
                // Part two is attachment
                messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(bijlageClient.getUploadPad() + File.separator + bijlage.getS3Identificatie());
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(bijlage.getBestandsNaam());
                multipart.addBodyPart(messageBodyPart);
            }

            // Send the complete message parts
            msg.setContent(multipart);

            Transport transport = emailSession.getTransport("smtp");
            transport.connect(mailHost, Integer.valueOf(smtpPort), null, null);
            //Zeker weten dat de mail niet al verstuurd is door een andere Thread
            if (communicatieProductRepository.lees(uitgaandeEmail.getId()).getDatumTijdVerzending() == null) {
                rateLimiter.acquire();
                transport.sendMessage(msg, msg.getAllRecipients());
            }
            transport.close();

            uitgaandeEmail.setDatumTijdVerzending(LocalDateTime.now());
            uitgaandeEmail.setOnverzondenIndicatie(null);
        } catch (NoSuchProviderException e) {
            LOGGER.error("{}", e);
        } catch (MessagingException e) {
            LOGGER.error("{}", e);
        }

    }
}
