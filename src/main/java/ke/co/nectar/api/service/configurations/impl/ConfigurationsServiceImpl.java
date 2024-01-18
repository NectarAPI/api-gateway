package ke.co.nectar.api.service.configurations.impl;

import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.domain.configuration.Configuration;
import ke.co.nectar.api.domain.configuration.STSConfiguration;
import ke.co.nectar.api.domain.configuration.exceptions.InvalidConfigurationTypeException;
import ke.co.nectar.api.service.configurations.ConfigurationsService;
import ke.co.nectar.api.service.exceptions.ApiResponseException;
import ke.co.nectar.api.utils.RequestUtils;
import ke.co.nectar.api.utils.requests.Payload;
import ke.co.nectar.api.validation.authoritization.BasicAuthCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ConfigurationsServiceImpl implements ConfigurationsService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RequestUtils requestUtils;

    @Autowired
    private Configuration configuration;

    @Value("${endpoints.configuration.host}")
    private String configurationsEndpoint;

    @Value("${endpoints.configuration.username}")
    private String configurationsBasicAuthUsername;

    @Value("${endpoints.configuration.password}")
    private String configurationsBasicAuthPassword;

    @Override
    public STSConfiguration getConfiguration(String requestId,
                                             String ref,
                                             boolean detailed)
        throws ApiResponseException, InvalidConfigurationTypeException {
        ApiResponse response = requestUtils.get(
                new BasicAuthCredentials(configurationsBasicAuthUsername,
                        configurationsBasicAuthPassword),
                String.format("%s?ref=%s&detailed=%b&request_id=%s",
                        configurationsEndpoint, ref, detailed, requestId));
        return configuration.extractFrom(response);
    }

    @Override
    public STSConfiguration createConfiguration(String requestId,
                                      String userRef,
                                      Map<String, Object> params)
        throws ApiResponseException, InvalidConfigurationTypeException {
        ApiResponse response = requestUtils.post(
                new BasicAuthCredentials(configurationsBasicAuthUsername,
                        configurationsBasicAuthPassword),
                String.format("%s?user_ref=%s&request_id=%s",
                        configurationsEndpoint, userRef, requestId),
                new Payload(params).toJson().toString());
        return configuration.extractFrom(response);
    }

    @Override
    public void activateConfiguration(String requestId, String userRef, String ref)
        throws ApiResponseException {
        requestUtils.put(
                new BasicAuthCredentials(configurationsBasicAuthUsername,
                        configurationsBasicAuthPassword),
                String.format("%s?config_ref=%s&request_id=%s&user_ref=%s",
                        configurationsEndpoint, ref, requestId, userRef),
                null);
    }

    @Override
    public void deactivateConfigurations(String requestId, String userRef, String ref)
        throws ApiResponseException {
        requestUtils.delete(
                new BasicAuthCredentials(configurationsBasicAuthUsername,
                        configurationsBasicAuthPassword),
                String.format("%s?config_ref=%s&request_id=%s&user_ref=%s",
                        configurationsEndpoint, ref, requestId, userRef));
    }
}
