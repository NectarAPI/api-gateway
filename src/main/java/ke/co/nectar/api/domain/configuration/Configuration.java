package ke.co.nectar.api.domain.configuration;

import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.domain.configuration.exceptions.InvalidConfigurationTypeException;
import ke.co.nectar.api.domain.extractor.Extract;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.LinkedHashMap;

@Component
public class Configuration implements Extract<STSConfiguration> {

    public enum ConfigType {
        NATIVE, PRISM_THRIFT
    }

    public Configuration() {}

    @Override
    public STSConfiguration extractFrom(ApiResponse response) throws InvalidConfigurationTypeException {
        LinkedHashMap config = (LinkedHashMap) response.getData().get("config");
        LinkedHashMap configDetails = ((LinkedHashMap) config.get("config"));
        ConfigType configType = ConfigType.valueOf((String) configDetails.get("config_type"));

        if (configType == ConfigType.NATIVE) {
            return new NativeConfiguration((String) configDetails.get ("name"),
                    (String) configDetails.get("user_ref"),
                    (Boolean) configDetails.get("activated"),
                    (String) configDetails.get("ref"),
                    Instant.parse((String) configDetails.get("created_at")),
                    config.containsKey("key_expiry_no") ? (String) config.get("key_expiry_no") : null,
                    config.containsKey("encryption_algorithm") ? (String) config.get("encryption_algorithm") : null,
                    config.containsKey("token_carrier_type") ? (String) config.get("token_carrier_type") : null,
                    config.containsKey("decoder_key_generation_algorithm") ? (String) config.get("decoder_key_generation_algorithm") : null,
                    config.containsKey("tariff_index") ? (String) config.get("tariff_index") : null,
                    config.containsKey("key_revision_no") ? (String) config.get("key_revision_no") : null,
                    config.containsKey("vending_key") ? (String) config.get("vending_key") : null,
                    config.containsKey("supply_group_code") ? (String) config.get("supply_group_code") : null,
                    config.containsKey("key_type") ? (String) config.get("key_type") : null,
                    config.containsKey("base_date") ? (String) config.get("base_date") : null,
                    config.containsKey("issuer_identification_no") ? (String) config.get("issuer_identification_no") : null);

        } else if (configType == ConfigType.PRISM_THRIFT) {
            return new PrismThriftConfiguration((String) configDetails.get ("name"),
                    (String) configDetails.get("user_ref"),
                    (Boolean) configDetails.get("activated"),
                    (String) configDetails.get("ref"),
                    Instant.parse((String) configDetails.get("created_at")),
                    config.containsKey("host") ? (String) config.get("host") : null,
                    config.containsKey("port") ? (String) config.get("port") : null,
                    config.containsKey("realm") ? (String) config.get("realm") : null,
                    config.containsKey("username") ? (String) config.get("username") : null,
                    config.containsKey("password") ? (String) config.get("password") : null,
                    config.containsKey("encryption_algorithm") ? (String) config.get("encryption_algorithm") : null,
                    config.containsKey("token_carrier_type") ? (String) config.get("token_carrier_type") : null,
                    config.containsKey("supply_group_code") ? (String) config.get("supply_group_code") : null,
                    config.containsKey("key_revision_no") ? (String) config.get("key_revision_no") : null,
                    config.containsKey("key_expiry_no") ? (String) config.get("key_expiry_no") : null,
                    config.containsKey("tariff_index") ? (String) config.get("tariff_index") : null);
        }
        throw new InvalidConfigurationTypeException(String.format("%s is not valid", configDetails.get("type")));
    }
}
