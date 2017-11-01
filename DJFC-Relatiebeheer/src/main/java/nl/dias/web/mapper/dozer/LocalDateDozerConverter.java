package nl.dias.web.mapper.dozer;

import org.dozer.DozerConverter;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalDateDozerConverter extends DozerConverter<LocalDate, String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocalDateDozerConverter.class);

    public LocalDateDozerConverter() {
        super(LocalDate.class, String.class);
    }

    @Override
    public LocalDate convertFrom(String arg0, LocalDate arg1) {
        String patternDatum = "dd-MM-yyyy";

        if (arg0 != null && !"".equals(arg0)) {
            return LocalDate.parse(arg0, DateTimeFormat.forPattern(patternDatum));
        } else {
            return null;
        }
    }

    @Override
    public String convertTo(LocalDate datumTijdIn, String arg1) {
        if (datumTijdIn == null) {
            return null;
        }
        String geconverteerd = datumTijdIn.toString("dd-MM-yyyy");

        LOGGER.debug("Converteren " + datumTijdIn + " naar " + geconverteerd);

        return geconverteerd;
    }

}
