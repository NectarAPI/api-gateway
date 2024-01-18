package ke.co.nectar.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.domain.extractor.ExtractMultiple;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class Token implements ExtractMultiple<Token> {

    private String ref;

    @JsonProperty("token_no")
    private String tokenNo;

    @JsonProperty("user_ref")
    private String userRef;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("meter_no")
    private String meterNo;

    @JsonProperty("created_at")
    private Instant createdAt;

    public Token() {}

    public Token(String ref, String tokenNo, String userRef,
                 String tokenType, String meterNo, Instant createdAt) {
        setRef(ref);
        setTokenNo(tokenNo);
        setUserRef(userRef);
        setTokenType(tokenType);
        setMeterNo(meterNo);
        setCreatedAt(createdAt);
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getTokenNo() {
        return tokenNo;
    }

    public void setTokenNo(String tokenNo) {
        this.tokenNo = tokenNo;
    }

    public String getUserRef() {
        return userRef;
    }

    public void setUserRef(String userRef) {
        this.userRef = userRef;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public List<Token> extractMultipleFrom(ApiResponse response) {
        List<Token> tokens = new ArrayList<>();
        for (LinkedHashMap token :
                (ArrayList<LinkedHashMap>) response.getData().get("tokens")) {
            tokens.add(new Token((String) token.get("ref"),
                    (String) token.get("token_no"),
                    (String) token.get("user_ref"),
                    (String) token.get("token_type"),
                    (String) token.get("meter_no"),
                    Instant.parse((String) token.get("created_at"))));
        }
        return tokens;
    }

    @Override
    public Token extractFrom(ApiResponse response) {
        LinkedHashMap token = (LinkedHashMap) response.getData().get("token");
        return new Token((String) token.get("ref"),
                (String) token.get("token_no"),
                (String) token.get("user_ref"),
                (String) token.get("token_type"),
                (String) token.get("meter_no"),
                Instant.parse((String) token.get("created_at")));
    }

    @Override
    public String toString() {
        return String.format("Token { ref: %s, token_no: %s, user_ref: %s," +
                        "token_type: %s, meter_no: %s, created_at: %s }",
                ref, tokenNo, userRef, tokenType, meterNo, createdAt.toString());
    }
}
