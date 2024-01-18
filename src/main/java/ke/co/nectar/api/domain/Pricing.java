package ke.co.nectar.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.domain.extractor.Extract;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.LinkedHashMap;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pricing implements Extract<Pricing> {

    @JsonProperty("meter_no")
    private Double credits;

    @JsonProperty("created_at")
    private Instant createdAt;

    @JsonProperty("updated_at")
    private Instant updatedAt;

    public Pricing() {}

    public Pricing(Double credits, Instant createdAt, Instant updatedAt) {
        setCredits(credits);
        setCreatedAt(createdAt);
        setUpdatedAt(updatedAt);
    }

    public Double getCredits() {
        return credits;
    }

    public void setCredits(Double credits) {
        this.credits = credits;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public Pricing extractFrom(ApiResponse response) {
        LinkedHashMap pricing = (LinkedHashMap) response.getData().get("pricing");
        return new Pricing((Double) pricing.get("credits"),
                        Instant.parse((String) pricing.get("created_at")),
                        Instant.parse((String) pricing.get("updated_at")));
    }

    @Override
    public String toString() {
        return String.format("Pricing { credits: %f, created_at: %s, updated_at:%s }",
                credits, createdAt.toString(), updatedAt.toString());
    }
}
