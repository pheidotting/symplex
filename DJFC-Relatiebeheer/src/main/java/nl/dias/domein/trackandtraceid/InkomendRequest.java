package nl.dias.domein.trackandtraceid;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;


@Table(name = "INKOMENDREQUEST")
@Entity
public class InkomendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "TIJDSTIP")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime tijdstip;
    @Column(name = "HTTPREQUEST")
    private String httpRequest;
    @Column(name = "JSON")
    private String json;
    @Column(name = "URL")
    private String url;
    @Column(name = "TRACKANDTRACEID")
    private String trackAndTraceId;
    @Column(name = "INGELOGDEGEBRUIKER")
    private Long ingelogdeGebruiker;

    public InkomendRequest() {
        tijdstip = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTijdstip() {
        return tijdstip;
    }

    public void setTijdstip(LocalDateTime tijdstip) {
        this.tijdstip = tijdstip;
    }

    public String getHttpRequest() {
        return httpRequest;
    }

    public void setHttpRequest(String httpRequest) {
        this.httpRequest = httpRequest;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTrackAndTraceId() {
        return trackAndTraceId;
    }

    public void setTrackAndTraceId(String trackAndTraceId) {
        this.trackAndTraceId = trackAndTraceId;
    }

    public Long getIngelogdeGebruiker() {
        return ingelogdeGebruiker;
    }

    public void setIngelogdeGebruiker(Long ingelogdeGebruiker) {
        this.ingelogdeGebruiker = ingelogdeGebruiker;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof InkomendRequest)) {
            return false;
        }

        InkomendRequest that = (InkomendRequest) o;

        return new EqualsBuilder().append(getId(), that.getId()).append(getTijdstip(), that.getTijdstip()).append(getHttpRequest(), that.getHttpRequest()).append(getJson(), that.getJson()).append(getUrl(), that.getUrl()).append(getTrackAndTraceId(), that.getTrackAndTraceId()).append(getIngelogdeGebruiker(), that.getIngelogdeGebruiker()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).append(getTijdstip()).append(getHttpRequest()).append(getJson()).append(getUrl()).append(getTrackAndTraceId()).append(getIngelogdeGebruiker()).toHashCode();
    }
}
