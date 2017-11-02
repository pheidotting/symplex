package nl.lakedigital.djfc.service.envers;


public class RevisionListener implements org.hibernate.envers.RevisionListener {
    @Override
    public void newRevision(Object revisionEntity) {
        RevEntity revEntity = (RevEntity) revisionEntity;

        revEntity.setUserid(SessieHolder.get().getIngelogdeGebruiker());
        revEntity.setTrackAndTraceId(SessieHolder.get().getTrackAndTraceId());
    }

}
