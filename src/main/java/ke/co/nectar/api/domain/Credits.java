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
public class Credits implements Extract<Credits> {

    private Double credits;
    private List<Purchase> purchases;
    private List<Consumption> consumptions;

    public Credits() {}

    public Credits(Double credits,
                   List<Purchase> purchases,
                   List<Consumption> consumptions) {
        setCredits(credits);
        setPurchases(purchases);
        setConsumptions(consumptions);
    }

    public Double getCredits() {
        return credits;
    }

    public void setCredits(Double credits) {
        this.credits = credits;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public List<Consumption> getConsumptions() {
        return consumptions;
    }

    public void setConsumptions(List<Consumption> consumptions) {
        this.consumptions = consumptions;
    }

    public class Purchase {
        private String ref;

        @JsonProperty("user_ref")
        private String userRef;
        private Double value;
        private Double units;
        private String currency;

        @JsonProperty("purchase_date")
        private Instant purchaseDate;

        public Purchase(String ref, String userRef, Double value,
                        Double units, String currency, Instant purchaseDate) {
            setRef(ref);
            setUserRef(userRef);
            setValue(value);
            setUnits(units);
            setCurrency(currency);
            setPurchaseDate(purchaseDate);
        }

        public String getRef() {
            return ref;
        }

        public void setRef(String ref) {
            this.ref = ref;
        }

        public String getUserRef() {
            return userRef;
        }

        public void setUserRef(String userRef) {
            this.userRef = userRef;
        }

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }

        public Double getUnits() {
            return units;
        }

        public void setUnits(Double units) {
            this.units = units;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public Instant getPurchaseDate() {
            return purchaseDate;
        }

        public void setPurchaseDate(Instant purchaseDate) {
            this.purchaseDate = purchaseDate;
        }
    }

    public class Consumption {
        private String ref;
        private Double units;

        @JsonProperty("consumption_date")
        private Instant consumptionDate;

        @JsonProperty("user_ref")
        private String userRef;

        @JsonProperty("token_ref")
        private String tokenRef;

        public Consumption(String ref, Double units, Instant consumptionDate,
                           String userRef, String tokenRef) {
            setRef(ref);
            setUnits(units);
            setConsumptionDate(consumptionDate);
            setUserRef(userRef);
            setTokenRef(tokenRef);
        }

        public String getRef() {
            return ref;
        }

        public void setRef(String ref) {
            this.ref = ref;
        }

        public Double getUnits() {
            return units;
        }

        public void setUnits(Double units) {
            this.units = units;
        }

        public Instant getConsumptionDate() {
            return consumptionDate;
        }

        public void setConsumptionDate(Instant consumptionDate) {
            this.consumptionDate = consumptionDate;
        }

        public String getUserRef() {
            return userRef;
        }

        public void setUserRef(String userRef) {
            this.userRef = userRef;
        }

        public String getTokenRef() {
            return tokenRef;
        }

        public void setTokenRef(String tokenRef) {
            this.tokenRef = tokenRef;
        }
    }

    @Override
    public Credits extractFrom(ApiResponse response) {
        LinkedHashMap credits = (LinkedHashMap) response.getData().get("credits");
        return new Credits((Double) credits.get("credits"),
                extractPurchases(credits),
                extractConsumption(credits));
    }

    private List<Purchase> extractPurchases(LinkedHashMap credits) {
        List<Purchase> extractedPurchases = new ArrayList<>();
        if (credits.containsKey("purchase")) {
            List<LinkedHashMap> purchases = (List<LinkedHashMap>) credits.get("purchase");
            purchases.forEach(purchase -> {
                extractedPurchases.add(new Purchase(
                        (String) purchase.get("ref"),
                        (String) purchase.get("user_ref"),
                        (Double) purchase.get("value"),
                        (Double) purchase.get("units"),
                        (String) purchase.get("currency"),
                        Instant.parse((String) purchase.get("purchase_date"))
                ));
            });
        }
        return extractedPurchases;
    }

    private List<Consumption> extractConsumption(LinkedHashMap credits) {
        List<Consumption> extractedConsumptions = new ArrayList<>();
        if (credits.containsKey("consumption")) {
            List<LinkedHashMap> consumptions = (List<LinkedHashMap>) credits.get("consumption");
            consumptions.forEach(consumption -> {
                extractedConsumptions.add(new Consumption(
                        (String) consumption.get("ref"),
                        (Double) consumption.get("units"),
                        Instant.parse((String) consumption.get("consumption_date")),
                        (String) consumption.get("user_ref"),
                        (String) consumption.get("token_ref")
                ));
            });
        }
        return extractedConsumptions;
    }
}
