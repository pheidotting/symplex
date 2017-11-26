package nl.dias.service.envers;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "REVINFO")
@RevisionEntity(RevisionListener.class)
public class RevEntity extends DefaultRevisionEntity {
    private static final long serialVersionUID = -1169834849124037964L;

    private Long userid;
    private String trackAndTraceId = "";

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getTrackAndTraceId() {
        return trackAndTraceId;
    }

    public void setTrackAndTraceId(String trackAndTraceId) {
        this.trackAndTraceId = trackAndTraceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RevEntity)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        RevEntity revEntity = (RevEntity) o;
        return Objects.equals(getUserid(), revEntity.getUserid()) && Objects.equals(getTrackAndTraceId(), revEntity.getTrackAndTraceId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUserid(), getTrackAndTraceId());
    }
}
