package ke.co.nectar.api.domain.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public abstract class STSConfiguration {

    private String name;

    @JsonProperty("user_ref")
    private String userRef;

    private boolean activated;

    private String ref;

    @JsonProperty("created_at")
    private Instant createdAt;

    @JsonProperty("configuration_type")
    private Configuration.ConfigType configType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Configuration.ConfigType getConfigType() {
        return configType;
    }

    public void setConfigType(Configuration.ConfigType configType) {
        this.configType = configType;
    }

    @Override
    public String toString() {
        return String.format("Configuration { user_ref: %s, activated: %b, " +
                        "ref: %s, created_at: %s }",
                userRef, activated, ref, createdAt);
    }
}
