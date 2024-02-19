package ke.co.nectar.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public class Credits {

    private String ref;

    private Instant purchaseDate;

    private String userRef;

    private Double value;

    private String currency;

    private Double units;

    private Payment payment;

    public Credits() {}

    public Credits(String ref, Instant purchaseDate, String userRef,
                   Double value, String currency, Double units,
                   Payment payment) {
        setRef(ref);
        setPurchaseDate(purchaseDate);
        setUserRef(userRef);
        setValue(value);
        setCurrency(currency);
        setUnits(units);
        setPayment(payment);
    }

    @Override
    public String toString() {
        return String.format("ref: %s", ref);
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Instant getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Instant purchaseDate) {
        this.purchaseDate = purchaseDate;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getUnits() {
        return units;
    }

    public void setUnits(Double units) {
        this.units = units;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
