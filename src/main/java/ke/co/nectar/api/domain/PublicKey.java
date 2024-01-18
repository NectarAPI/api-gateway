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
public class PublicKey implements Extract<PublicKey> {

    private String key;

    @JsonProperty("user_ref")
    private String userRef;
    private boolean activated;
    private String ref;

    @JsonProperty("created_at")
    private Instant createdAt;

    public PublicKey() {}

    public PublicKey(String key, String userRef, boolean activated,
                     String ref, Instant createdAt) {
        setKey(key);
        setUserRef(userRef);
        setActivated(activated);
        setRef(ref);
        setCreatedAt(createdAt);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserRef() {
        return userRef;
    }

    public void setUserRef(String userRef) {
        this.userRef = userRef;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public PublicKey extractFrom(ApiResponse response) {
        LinkedHashMap publicKey = (LinkedHashMap) response.getData().get("public_key");
        return new PublicKey((String) publicKey.get("key"),
                (String) publicKey.get("user_ref"),
                (Boolean) publicKey.get("activated"),
                (String) publicKey.get("ref"),
                Instant.parse((String) publicKey.get("created_at")));
    }

    public List<PublicKey> extractMultipleFrom(ApiResponse response) {
        List<LinkedHashMap> publicKeys = (List<LinkedHashMap>) response.getData().get("data");
        List<PublicKey> extractedPublicKeys = new ArrayList<>();
        for (LinkedHashMap publicKey : publicKeys) {
            extractedPublicKeys.add(new PublicKey((String) publicKey.get("key"),
                    (String) publicKey.get("user_ref"),
                    (Boolean) publicKey.get("activated"),
                    (String) publicKey.get("ref"),
                    Instant.parse((String) publicKey.get("created_at"))));
        }
        return extractedPublicKeys;
    }
}
