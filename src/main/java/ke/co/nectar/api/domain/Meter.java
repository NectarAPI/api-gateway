package ke.co.nectar.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.domain.extractor.Extract;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class Meter implements Extract<Meter> {

    private String ref;

    private BigDecimal no;

    private Boolean activated;

    @JsonProperty("meter_type")
    private MeterType meterType;

    private Subscriber subscriber;

    @JsonProperty("created_at")
    private Instant createdAt;

    @JsonProperty("updated_at")
    private Instant updatedAt;

    public Meter() {}

    public Meter(String ref, BigDecimal no, Boolean activated,
                 MeterType meterType, Subscriber subscriber,
                 Instant createdAt, Instant updatedAt) {
        setRef(ref);
        setNo(no);
        setActivated(activated);
        setMeterType(meterType);
        setSubscriber(subscriber);
        setCreatedAt(createdAt);
        setUpdatedAt(updatedAt);
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public BigDecimal getNo() {
        return no;
    }

    public void setNo(BigDecimal no) {
        this.no = no;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public MeterType getMeterType() {
        return meterType;
    }

    public void setMeterType(MeterType meterType) {
        this.meterType = meterType;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
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
    public Meter extractFrom(ApiResponse response) {
        return extractMeter((LinkedHashMap) response.getData().get("meter"));
    }

    public List<Meter> extractMultipleFrom(ApiResponse response) {
        return extractMeters((List<LinkedHashMap>) response.getData().get("meters"));
    }

}
