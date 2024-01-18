package ke.co.nectar.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.domain.extractor.Extract;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class Notification implements Extract<Notification> {

    private String ref;
    private String subject;
    private String text;
    private String type;

    @JsonProperty("user_ref")
    private String userRef;
    private String affected;
    private boolean read;

    @JsonProperty("read_date")
    private Instant readDate;

    @JsonProperty("created_date")
    private Instant createdDate;

    public Notification() {}

    public Notification(String ref, String subject, String text, String type,
                        String userRef, String affected, Boolean read,
                        Instant readDate, Instant createdDate) {
        setRef(ref);
        setSubject(subject);
        setText(text);
        setType(type);
        setUserRef(userRef);
        setAffected(affected);
        setRead(read);
        setReadDate(readDate);
        setCreatedDate(createdDate);
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserRef() {
        return userRef;
    }

    public void setUserRef(String userRef) {
        this.userRef = userRef;
    }

    public String getAffected() {
        return affected;
    }

    public void setAffected(String affected) {
        this.affected = affected;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public Instant getReadDate() {
        return readDate;
    }

    public void setReadDate(Instant readDate) {
        this.readDate = readDate;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public Notification extractFrom(ApiResponse response) {
        LinkedHashMap notification = (LinkedHashMap) response.getData().get("notification");
        return new Notification((String) notification.get("ref"),
                (String) notification.get("subject"),
                (String) notification.get("text"),
                (String) notification.get("type"),
                (String) notification.get("user_ref"),
                (String) notification.get("affected"),
                (Boolean) notification.get("read"),
                Instant.parse((String) notification.get("read_at")),
                Instant.parse((String) notification.get("created_at")));
    }

    public List<Notification> extractMultipleFrom(ApiResponse response) {
        List<LinkedHashMap> notifications = (List<LinkedHashMap>) response.getData().get("notifications");
        List<Notification> extractedNotifications = new ArrayList<>();
        notifications.forEach(notification -> {
            extractedNotifications.add(new Notification((String) notification.get("ref"),
                    (String) notification.get("subject"),
                    (String) notification.get("text"),
                    (String) notification.get("type"),
                    (String) notification.get("user_ref"),
                    (String) notification.get("affected"),
                    (Boolean) notification.get("read"),
                    notification.get("read_at") == null ?
                            null : Instant.parse((String) notification.get("read_at")),
                    Instant.parse((String) notification.get("created_at"))));
        });
        return extractedNotifications;
    }
}
