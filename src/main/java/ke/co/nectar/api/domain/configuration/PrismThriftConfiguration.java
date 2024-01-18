package ke.co.nectar.api.domain.configuration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrismThriftConfiguration extends STSConfiguration {

    @JsonProperty("host")
    private String host;

    @JsonProperty("port")
    private String port;

    @JsonProperty("realm")
    private String realm;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("encryption_algorithm")
    private String encryptionAlgorithm;

    @JsonProperty("token_carrier_type")
    private String tokenCarrierType;

    @JsonProperty("supply_group_code")
    private String supplyGroupCode;

    @JsonProperty("key_revision_no")
    private String keyRevisionNo;

    @JsonProperty("key_expiry_no")
    private String keyExpiryNo;

    @JsonProperty("tariff_index")
    private String tariffIndex;

    public PrismThriftConfiguration() {}

    public PrismThriftConfiguration(String name, String userRef, boolean activated,
                                    String ref, Instant createdAt, String host, String port,
                                    String realm, String username, String password,
                                    String encryptionAlgorithm, String tokenCarrierType,
                                    String supplyGroupCode, String keyRevisionNo,
                                    String keyExpiryNo, String tariffIndex) {
        setConfigType(Configuration.ConfigType.PRISM_THRIFT);
        setName(name);
        setUserRef(userRef);
        setActivated(activated);
        setRef(ref);
        setCreatedAt(createdAt);
        setHost(host);
        setPort(port);
        setRealm(realm);
        setUsername(username);
        setPassword(password);
        setEncryptionAlgorithm(encryptionAlgorithm);
        setTokenCarrierType(tokenCarrierType);
        setSupplyGroupCode(supplyGroupCode);
        setKeyRevisionNo(keyRevisionNo);
        setKeyExpiryNo(keyExpiryNo);
        setTariffIndex(tariffIndex);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getSupplyGroupCode() {
        return supplyGroupCode;
    }

    public void setSupplyGroupCode(String supplyGroupCode) {
        this.supplyGroupCode = supplyGroupCode;
    }

    public String getKeyRevisionNo() {
        return keyRevisionNo;
    }

    public void setKeyRevisionNo(String keyRevisionNo) {
        this.keyRevisionNo = keyRevisionNo;
    }

    public String getKeyExpiryNo() {
        return keyExpiryNo;
    }

    public void setKeyExpiryNo(String keyExpiryNo) {
        this.keyExpiryNo = keyExpiryNo;
    }

    public String getTariffIndex() {
        return tariffIndex;
    }

    public void setTariffIndex(String tariffIndex) {
        this.tariffIndex = tariffIndex;
    }
}
