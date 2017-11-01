package nl.dias.service.trackandtraceid;

import nl.dias.domein.trackandtraceid.InkomendRequest;
import nl.dias.repository.trackandtraceid.InkomendRequestRepository;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Service
public class InkomendRequestService {
    @Inject
    private InkomendRequestRepository inkomendRequestRepository;

    public void opslaan(Long ingelogdeGebruiker, String json, HttpServletRequest httpServletRequest, String url) {
        InkomendRequest inkomendRequest = new InkomendRequest();
        inkomendRequest.setJson(json);
        inkomendRequest.setHttpRequest(ReflectionToStringBuilder.toString(httpServletRequest, ToStringStyle.SHORT_PREFIX_STYLE));
        inkomendRequest.setIngelogdeGebruiker(ingelogdeGebruiker);
        inkomendRequest.setTrackAndTraceId(getTrackAndTraceId(httpServletRequest));
        inkomendRequest.setUrl(url);

        inkomendRequestRepository.opslaan(inkomendRequest);
    }

    private String getTrackAndTraceId(HttpServletRequest httpServletRequest) {
        String tati = httpServletRequest.getHeader("trackAndTraceId");

        return tati;
    }

    public void setInkomendRequestRepository(InkomendRequestRepository inkomendRequestRepository) {
        this.inkomendRequestRepository = inkomendRequestRepository;
    }
}
