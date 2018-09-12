//package nl.lakedigital.djfc.predicates;
//
//import com.google.common.base.Predicate;
//import nl.lakedigital.djfc.domain.Polis;
//import nl.lakedigital.djfc.domain.SoortVerzekering;
//
//public class PolissenOpSoortPredicate implements Predicate<Polis> {
//    private SoortVerzekering soortVerzekering;
//
//    public PolissenOpSoortPredicate(SoortVerzekering soortVerzekering) {
//        this.soortVerzekering = soortVerzekering;
//    }
//
//    @Override
//    public boolean apply(Polis polis) {
//        return polis.getSoortVerzekering().equals(soortVerzekering);
//    }
//}
