//package nl.lakedigital.djfc.predicates;
//
//import com.google.common.base.Predicate;
//import nl.lakedigital.djfc.domain.StatusPolis;
//
//public class StatusPolisBijStatusPredicate implements Predicate<StatusPolis> {
//    private String omschrijving;
//
//    public StatusPolisBijStatusPredicate(String omschrijving) {
//        this.omschrijving = omschrijving;
//    }
//
//    @Override
//    public boolean apply(StatusPolis statusPolis) {
//        if (omschrijving.equals(statusPolis.getOmschrijving())) {
//            return true;
//        }
//
//        return false;
//    }
//}
