package nl.dias.domein;

import org.joda.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "LOG")
public class LogObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "LEVEL")
    private final String level;
    @Column(name = "MESSAGE")
    private final String message;
    @Column(name = "TIMESTAMP")
    private final LocalDateTime timestamp;
    @Column(name = "URL")
    private final String url;

    public LogObject(String level, String message, String timestamp, String url) {
        super();
        this.level = level;
        this.message = message;
        this.timestamp = converteerTimestampNaarLocalDate(timestamp);
        this.url = url;
    }

    public String getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {

        String seperator = " - ";

        return level + seperator + message + seperator + timestamp + seperator + url;
    }

    private LocalDateTime converteerTimestampNaarLocalDate(String timestamp) {
        return new LocalDateTime(Long.parseLong(timestamp));
    }

}
