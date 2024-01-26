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
public class Subscriber implements Extract<Subscriber> {

    private String name;

    @JsonProperty("phone_no")
    private String phoneNo;

    private String ref;

    private boolean activated;

    @JsonProperty("created_at")
    private Instant createdAt;

    @JsonProperty("updated_at")
    private Instant updatedAt;

    public Subscriber() {}

    public Subscriber(String name, String phoneNo, String ref,
                      boolean activated, Instant createdAt, Instant updatedAt) {
        setName(name);
        setPhoneNo(phoneNo);
        setRef(ref);
        setActivated(activated);
        setCreatedAt(createdAt);
        setUpdatedAt(updatedAt);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public Subscriber extractFrom(ApiResponse response) {
        LinkedHashMap subscriber = (LinkedHashMap) response.getData().get("subscriber");
        return new Subscriber((String) subscriber.get("name"),
                (String) subscriber.get("phone_no"),
                (String) subscriber.get("ref"),
                (Boolean) subscriber.get("activated"),
                Instant.parse((String) subscriber.get("created_at")),
                Instant.parse((String) subscriber.get("updated_at")));
    }

    public List<Subscriber> extractMultipleFrom(ApiResponse response) {
        List<LinkedHashMap> subscribers = (List<LinkedHashMap>) response.getData().get("subscribers");
        List<Subscriber> extractedSubscribers = new ArrayList<>();
        subscribers.forEach(subscriber -> {
            extractedSubscribers.add(new Subscriber((String) subscriber.get("name"),
                    (String) subscriber.get("phone_no"),
                    (String) subscriber.get("ref"),
                    (Boolean) subscriber.get("activated"),
                    Instant.parse((String) subscriber.get("created_at")),
                    subscriber.get("updated-at") == null ?
                            null : Instant.parse((String) subscriber.get("updated_at"))));
        });
        return extractedSubscribers;
    }


}
