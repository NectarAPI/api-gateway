package ke.co.nectar.api.domain.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class NativeConfiguration extends STSConfiguration {

    @JsonProperty("key_expiry_no")
    private String keyExpiryNo;

    @JsonProperty("encryption_algorithm")
    private String encryptionAlgorithm;

    @JsonProperty("token_carrier_type")
    private String tokenCarrierType;

    @JsonProperty("decoder_key_generation_algorithm")
    private String decoderKeyGenerationAlgorithm;

    @JsonProperty("tariff_index")
    private String tariffIndex;

    @JsonProperty("key_revision_no")
    private String keyRevisionNo;

    @JsonProperty("vending_key")
    private String vendingKey;

    @JsonProperty("supply_group_code")
    private String supplyGroupCode;

    @JsonProperty("key_type")
    private String keyType;

    @JsonProperty("base_date")
    private String baseDate;

    @JsonProperty("issuer_identification_no")
    private String issuerIdentificationNo;

    public NativeConfiguration() {}

    public NativeConfiguration(String name, String userRef, boolean activated,
                         String ref, Instant createdAt, String keyExpiryNo,
                         String encryptionAlgorithm, String tokenCarrierType,
                         String decoderKeyGenerationAlgorithm, String tariffIndex,
                         String keyRevisionNo, String vendingKey, String supplyGroupCode,
                         String keyType, String baseDate, String issuerIdentificationNo) {
        setConfigType(Configuration.ConfigType.NATIVE);
        setName(name);
        setUserRef(userRef);
        setActivated(activated);
        setRef(ref);
        setCreatedAt(createdAt);
        setKeyExpiryNo(keyExpiryNo);
        setEncryptionAlgorithm(encryptionAlgorithm);
        setTokenCarrierType(tokenCarrierType);
        setDecoderKeyGenerationAlgorithm(decoderKeyGenerationAlgorithm);
        setTariffIndex(tariffIndex);
        setKeyRevisionNo(keyRevisionNo);
        setVendingKey(vendingKey);
        setSupplyGroupCode(supplyGroupCode);
        setKeyType(keyType);
        setBaseDate(baseDate);
        setIssuerIdentificationNo(issuerIdentificationNo);
    }

    public String getKeyExpiryNo() {
        return keyExpiryNo;
    }

    public void setKeyExpiryNo(String keyExpiryNo) {
        this.keyExpiryNo = keyExpiryNo;
    }

    public String getEncryptionAlgorithm() {
        return encryptionAlgorithm;
    }

    public void setEncryptionAlgorithm(String encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
    }

    public String getTokenCarrierType() {
        return tokenCarrierType;
    }

    public void setTokenCarrierType(String tokenCarrierType) {
        this.tokenCarrierType = tokenCarrierType;
    }

    public String getDecoderKeyGenerationAlgorithm() {
        return decoderKeyGenerationAlgorithm;
    }

    public void setDecoderKeyGenerationAlgorithm(String decoderKeyGenerationAlgorithm) {
        this.decoderKeyGenerationAlgorithm = decoderKeyGenerationAlgorithm;
    }

    public String getTariffIndex() {
        return tariffIndex;
    }

    public void setTariffIndex(String tariffIndex) {
        this.tariffIndex = tariffIndex;
    }

    public String getKeyRevisionNo() {
        return keyRevisionNo;
    }

    public void setKeyRevisionNo(String keyRevisionNo) {
        this.keyRevisionNo = keyRevisionNo;
    }

    public String getVendingKey() {
        return vendingKey;
    }

    public void setVendingKey(String vendingKey) {
        this.vendingKey = vendingKey;
    }

    public String getSupplyGroupCode() {
        return supplyGroupCode;
    }

    public void setSupplyGroupCode(String supplyGroupCode) {
        this.supplyGroupCode = supplyGroupCode;
    }

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    public String getBaseDate() {
        return baseDate;
    }

    public void setBaseDate(String baseDate) {
        this.baseDate = baseDate;
    }

    public String getIssuerIdentificationNo() {
        return this.issuerIdentificationNo;
    }

    public void setIssuerIdentificationNo(String issuerIdentificationNo) {
        this.issuerIdentificationNo =  issuerIdentificationNo;
    }
}
