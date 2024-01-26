package ke.co.nectar.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.domain.configuration.exceptions.InvalidConfigurationTypeException;
import ke.co.nectar.api.domain.extractor.Extract;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class MeterType implements Extract<MeterType> {

    private String name;

    private String ref;

    @JsonProperty("created_at")
    private Instant createdAt;

    @JsonProperty("updated_at")
    private Instant updatedAt;

    public MeterType() {}

    public MeterType(String name, String ref, Instant createdAt, Instant updatedAt) {
        setName(name);
        setRef(ref);
        setCreatedAt(createdAt);
        setUpdatedAt(updatedAt);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public MeterType extractFrom(ApiResponse response) {
        LinkedHashMap meterType  = (LinkedHashMap) response.getData().get("meter_type");
        return new MeterType((String) meterType.get("name"),
                (String) meterType.get("ref"),
                Instant.parse((String) meterType.get("created_at")),
                Instant.parse((String) meterType.get("updated_at")));
    }

    public List<MeterType> extractMultipleFrom(ApiResponse response) {
        List<LinkedHashMap> meterTypes = (List<LinkedHashMap>) response.getData().get("data");
        List<MeterType> extractedMeterTypes = new ArrayList<>();
        for (LinkedHashMap meterType : meterTypes) {
            extractedMeterTypes.add(new MeterType((String) meterType.get("name"),
                    (String) meterType.get("ref"),
                    Instant.parse((String) meterType.get("created_at")),
                    Instant.parse((String) meterType.get("created_at"))));
        }
        return extractedMeterTypes;
     }
}
