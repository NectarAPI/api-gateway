package ke.co.nectar.api.service.tokens.impl;

import ke.co.nectar.api.constants.StringConstants;
import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.domain.Credits;
import ke.co.nectar.api.domain.Pricing;
import ke.co.nectar.api.domain.Token;
import ke.co.nectar.api.domain.configuration.Configuration;
import ke.co.nectar.api.domain.configuration.NativeConfiguration;
import ke.co.nectar.api.domain.configuration.PrismThriftConfiguration;
import ke.co.nectar.api.domain.configuration.STSConfiguration;
import ke.co.nectar.api.domain.configuration.exceptions.InvalidConfigurationTypeException;
import ke.co.nectar.api.service.configurations.ConfigurationsService;
import ke.co.nectar.api.service.consumption.ConsumptionService;
import ke.co.nectar.api.service.credits.CreditsService;
import ke.co.nectar.api.service.exceptions.ApiResponseException;
import ke.co.nectar.api.service.pricing.PricingService;
import ke.co.nectar.api.service.tokens.TokensService;
import ke.co.nectar.api.service.tokens.exceptions.InsufficientCreditsException;
import ke.co.nectar.api.service.tokens.exceptions.InvalidResponseException;
import ke.co.nectar.api.service.tokens.exceptions.MismatchedConfigUserRefException;
import ke.co.nectar.api.service.tokens.exceptions.MissingConfigRefException;
import ke.co.nectar.api.utils.RequestUtils;
import ke.co.nectar.api.utils.requests.Payload;
import ke.co.nectar.api.validation.authoritization.BasicAuthCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TokenServiceImpl implements TokensService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RequestUtils requestUtils;

    @Autowired
    private Token token;

    @Autowired
    private CreditsService creditsService;

    @Autowired
    private PricingService pricingService;

    @Autowired
    private ConsumptionService consumptionService;

    @Autowired
    private ConfigurationsService configurationsService;

    @Value("${endpoints.token.host}")
    private String tokenEndpoint;

    @Value("${endpoints.token.username}")
    private String tokenBasicAuthUsername;

    @Value("${endpoints.token.password}")
    private String tokenBasicAuthPassword;

    @Override
    public List<Token> generateToken(String requestId,
                                    String userRef,
                                    Map<String, Object> params)
            throws Exception {
        if (params.containsKey("config_ref")) {
            String configRef = (String) params.get("config_ref");
            STSConfiguration configuration = configurationsService
                                                    .getConfiguration(requestId,
                                                            configRef,
                                                            true);
            if (configuration.getUserRef().equals(userRef)) {
                Credits credits = creditsService.getCredits(requestId, userRef);
                Pricing pricing = pricingService.getPricing(requestId);

                if (credits.getCredits() >= pricing.getCredits()) {
                    List<Token> generatedTokens = requestToken(requestId, userRef,
                            concatParams(params, configuration));
                    for (Token token: generatedTokens) {
                        postConsumption(requestId, userRef,
                                token.getRef(),
                                pricing.getCredits());
                    }
                    return generatedTokens;
                }
                throw new InsufficientCreditsException(
                        StringConstants.INSUFFICIENT_CREDITS
                );
            }
            throw new MismatchedConfigUserRefException(
                    String.format("Config %s is not assigned to %s",
                            configRef, userRef)
            );
        }
        throw new MissingConfigRefException();
    }

    @Override
    public Token getTokenWithRef(String requestId, String ref)
            throws ApiResponseException {
        ApiResponse response = requestUtils.get(
                new BasicAuthCredentials(tokenBasicAuthUsername,
                        tokenBasicAuthPassword),
                String.format("%s?ref=%s&request_id=%s", tokenEndpoint, ref, requestId));
        return token.extractFrom(response);
    }

    @Override
    public Token getTokenWithTokenNo(String tokenNo) {
        return null;
    }

    @Override
    public Map<String, Object> decodeToken(String requestID, String userRef,
                             String tokenNo, Map<String, Object> params)
        throws ApiResponseException, InvalidConfigurationTypeException,
            MissingConfigRefException, MismatchedConfigUserRefException,
            InvalidResponseException {
        if (params.containsKey("config_ref")) {
            String configRef = (String) params.get("config_ref");
            STSConfiguration configuration = configurationsService
                    .getConfiguration(requestID, configRef, true);

            if (configuration.getUserRef().equals(userRef)) {
                ApiResponse response = requestUtils.post(
                        new BasicAuthCredentials(tokenBasicAuthUsername,
                                tokenBasicAuthPassword),
                        String.format("%s/%s?user_ref=%s&request_id=%s", tokenEndpoint, tokenNo, userRef, requestID),
                        new Payload(concatParams(params, configuration)).toJson().toString());
                if (response.getStatus().getCode() == 200) {
                    return (Map<String, Object>) response.getData().get("token_details");
                }
                throw new InvalidResponseException(response.getStatus().getMessage());
            }
            throw new MismatchedConfigUserRefException(
                    String.format("Config %s is not assigned to %s",
                            configRef, userRef)
            );
        }
        throw new MissingConfigRefException();
    }

    private List<Token> requestToken(String requestId,
                                     String userRef,
                                     Map<String, Object> params)
        throws ApiResponseException {
        ApiResponse response = requestUtils.post(
                new BasicAuthCredentials(tokenBasicAuthUsername,
                        tokenBasicAuthPassword),
                String.format("%s?user_ref=%s&request_id=%s",
                        tokenEndpoint, userRef, requestId),
                new Payload(params).toJson().toString());
        return token.extractMultipleFrom(response);
    }

    private void postConsumption(String requestId, String userRef,
                                 String tokenRef, Double units)
        throws ApiResponseException {
        Map<String, Object> params = new HashMap<>();
        params.put("consumption_date", Instant.now());
        params.put("units", units);
        params.put("token_ref", tokenRef);
        consumptionService.postConsumption(requestId, userRef, params);
    }

    private Map<String, Object> concatParams(Map<String,Object> params,
                                              STSConfiguration configuration) {
        Map<String,Object> concatParams = new HashMap<>();

        if (configuration.getConfigType() == Configuration.ConfigType.NATIVE) {
            concatParams.put("key_type", ((NativeConfiguration) configuration).getKeyType());
            concatParams.put("supply_group_code", ((NativeConfiguration) configuration).getSupplyGroupCode());
            concatParams.put("tariff_index", ((NativeConfiguration) configuration).getTariffIndex());
            concatParams.put("key_revision_no", ((NativeConfiguration) configuration).getKeyRevisionNo());
            concatParams.put("issuer_identification_no", ((NativeConfiguration) configuration).getIssuerIdentificationNo());
            concatParams.put("base_date", ((NativeConfiguration) configuration).getBaseDate());
            concatParams.put("key_expiry_no", ((NativeConfiguration) configuration).getKeyExpiryNo());
            concatParams.put("encryption_algorithm", ((NativeConfiguration) configuration).getEncryptionAlgorithm());
            concatParams.put("decoder_key_generation_algorithm", ((NativeConfiguration) configuration).getDecoderKeyGenerationAlgorithm());
            concatParams.put("vending_key", ((NativeConfiguration) configuration).getVendingKey());

        } else if (configuration.getConfigType() == Configuration.ConfigType.PRISM_THRIFT) {
            concatParams.put("type", "prism-thrift");
            concatParams.put("host", ((PrismThriftConfiguration) configuration).getHost());
            concatParams.put("port", ((PrismThriftConfiguration) configuration).getPort());
            concatParams.put("realm", ((PrismThriftConfiguration) configuration).getRealm());
            concatParams.put("username", ((PrismThriftConfiguration) configuration).getUsername());
            concatParams.put("password", ((PrismThriftConfiguration) configuration).getPassword());
            concatParams.put("encryption_algorithm", ((PrismThriftConfiguration) configuration).getEncryptionAlgorithm());
            concatParams.put("token_carrier_type", ((PrismThriftConfiguration) configuration).getTokenCarrierType());
            concatParams.put("supply_group_code", ((PrismThriftConfiguration) configuration).getSupplyGroupCode());
            concatParams.put("key_revision_no", ((PrismThriftConfiguration) configuration).getKeyRevisionNo());
            concatParams.put("key_expiry_no", ((PrismThriftConfiguration) configuration).getKeyExpiryNo());
            concatParams.put("tariff_index", ((PrismThriftConfiguration) configuration).getTariffIndex());
        }

        concatParams.putAll(params);
        return concatParams;
    }
}
