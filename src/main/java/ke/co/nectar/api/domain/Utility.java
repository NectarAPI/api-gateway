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
public class Utility implements Extract<Utility> {

    private String name;

    private String ref;

    @JsonProperty("contact_phone_no")
    private String contactPhoneNo;

    @JsonProperty("unit_charge")
    private double unitCharge;

    private Boolean activated;

    @JsonProperty("config_ref")
    private String configRef;

    private List<Meter> meters;

    @JsonProperty("created_at")
    private Instant createdAt;

    @JsonProperty("updated_at")
    private Instant updatedAt;

    public Utility() {}

    public Utility(String name, String ref, String contactPhoneNo,
                   double unitCharge, boolean activated, String configRef,
                   List<Meter> meters, Instant createdAt, Instant updatedAt) {
        setName(name);
        setRef(ref);
        setContactPhoneNo(contactPhoneNo);
        setUnitCharge(unitCharge);
        setActivated(activated);
        setConfigRef(configRef);
        setMeters(meters);
        setCreatedAt(createdAt);
        setUpdatedAt(updatedAt);
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getRef() {
        return ref;
    }

    private void setRef(String ref) {
        this.ref = ref;
    }

    public String getContactPhoneNo() {
        return contactPhoneNo;
    }

    public void setContactPhoneNo(String contactPhoneNo) {
        this.contactPhoneNo = contactPhoneNo;
    }

    public double getUnitCharge() {
        return unitCharge;
    }

    public void setUnitCharge(double unitCharge) {
        this.unitCharge = unitCharge;
    }

    public boolean getActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getConfigRef() {
        return configRef;
    }

    public void setConfigRef(String configRef) {
        this.configRef = configRef;
    }

    public List<Meter> getMeters() {
        return meters;
    }

    public void setMeters(List<Meter> meters) {
        this.meters = meters;
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
    public String toString() {
        return String.format("Utility { name: %s, ref: %s, contact_phone_no: %s, " +
                        "unit_charge: %s, activated: %s:, config_ref: %s, " +
                        "meters: %s, updated_at: %b, created_at: %s }\n",
                name, ref, contactPhoneNo, unitCharge, activated,
                configRef, meters, updatedAt.toString(), createdAt.toString());
    }

    @Override
    public Utility extractFrom(ApiResponse response) {
        LinkedHashMap utility = (LinkedHashMap) response.getData().get("utility");
        return new Utility((String) utility.get("name"),
                (String) utility.get("ref"),
                (String) utility.get("contact_phone_no"),
                (Double) utility.get("unit_charge"),
                (Boolean) utility.get("activated"),
                (String) utility.get("config_ref"),
                getExtractedMeters(utility),
                Instant.parse((String) utility.get("created_at")),
                Instant.parse((String) utility.get("updated_at")));
    }

    public List<Utility> extractMultipleFrom(ApiResponse response) {
        List<LinkedHashMap> utilities = (List<LinkedHashMap>) response.getData().get("utilities");
        List<Utility> extractedUtilities = new ArrayList<>();
        for (LinkedHashMap utility : utilities) {
            extractedUtilities.add(new Utility((String) utility.get("name"),
                    (String) utility.get("ref"),
                    (String) utility.get("contact_phone_no"),
                    (Double) utility.get("unit_charge"),
                    (Boolean) utility.get("activated"),
                    (String) utility.get("config_ref"),
                    getExtractedMeters(utility),
                    Instant.parse((String) utility.get("created_at")),
                    Instant.parse((String) utility.get("updated_at"))));
        }
        return extractedUtilities;
    }

    private List<Meter> getExtractedMeters(LinkedHashMap utility) {
        List<Meter> extractedMeters  = new ArrayList<>();
        if (utility.containsKey("meters")) {
            extractedMeters =extractMeters((List<LinkedHashMap>) utility.get("meters"));
        }
        return extractedMeters;
    }
}
