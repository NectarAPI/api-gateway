package ke.co.nectar.api.controllers.payments;

import java.util.Map;
import java.util.Optional;

public class PaymentRequest {

    private String type;

    private Double amount;

    private Optional<Map<String, String>> data;

    public PaymentRequest() {}

    public PaymentRequest(String type, Double amount,
                          Optional<Map<String, String>> data) {
        setType(type);
        setAmount(amount);
        setData(data);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Optional<Map<String, String>> getData() {
        return data;
    }

    public void setData(Optional<Map<String, String>> data) {
        this.data = data;
    }
}

