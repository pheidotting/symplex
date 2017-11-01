package nl.dias.service;

import nl.dias.domein.Bedrijf;
import nl.dias.domein.RisicoAnalyse;
import nl.dias.repository.RisicoAnalyseRepository;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class RisicoAnalyseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RisicoAnalyseService.class);

    @Inject
    private RisicoAnalyseRepository risicoAnalyseRepository;
    @Inject
    private BedrijfService bedrijfService;

    public RisicoAnalyse lees(Long id) {
        return risicoAnalyseRepository.lees(id);
    }

    public RisicoAnalyse leesBijBedrijf(Long bedrijfsId) {
        Bedrijf bedrijf = bedrijfService.lees(bedrijfsId);

        RisicoAnalyse risicoAnalyse = risicoAnalyseRepository.leesBijBedrijf(bedrijf);

        LOGGER.debug(ReflectionToStringBuilder.toString(risicoAnalyse, ToStringStyle.SHORT_PREFIX_STYLE));

        return risicoAnalyse;
    }
}
