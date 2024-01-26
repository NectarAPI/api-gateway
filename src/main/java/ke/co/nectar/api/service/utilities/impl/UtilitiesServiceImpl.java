package ke.co.nectar.api.service.utilities.impl;

import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.domain.Meter;
import ke.co.nectar.api.domain.Utility;
import ke.co.nectar.api.service.exceptions.ApiResponseException;
import ke.co.nectar.api.service.utilities.UtilitiesService;
import ke.co.nectar.api.utils.RequestUtils;
import ke.co.nectar.api.utils.requests.Payload;
import ke.co.nectar.api.validation.authoritization.BasicAuthCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class UtilitiesServiceImpl implements UtilitiesService  {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RequestUtils requestUtils;

    @Autowired
    private Utility utility;

    @Autowired
    private Meter meter;

    @Value("${endpoints.utilities.host}")
    private String utilitiesEndpoint;

    @Value("${endpoints.utilities.username}")
    private String utilitiesBasicAuthUsername;

    @Value("${endpoints.utilities.password}")
    private String utilitiesBasicAuthPassword;

    @Override
    public Utility getUtility(String requestId,
                              String utilityRef)
            throws ApiResponseException {
        ApiResponse response = requestUtils.get(
                new BasicAuthCredentials(utilitiesBasicAuthUsername,
                        utilitiesBasicAuthPassword),
                String.format("%s?ref=%s&request_id=%s", utilitiesEndpoint, utilityRef, requestId));
        return utility.extractFrom(response);
    }

    @Override
    public List<Meter> getUtilityMeters(String requestId,
                                 String utilityRef)
            throws ApiResponseException {
        ApiResponse response = requestUtils.get(
                new BasicAuthCredentials(utilitiesBasicAuthUsername,
                        utilitiesBasicAuthPassword),
                String.format("%s/%s/meter?request_id=%s", utilitiesEndpoint, utilityRef, requestId));
        return meter.extractMultipleFrom(response);
    }

    @Override
    public String createUtility(String requestId,
                                String userRef,
                                Map<String, Object> params)
            throws ApiResponseException {
        ApiResponse response = requestUtils.post(
                new BasicAuthCredentials(utilitiesBasicAuthUsername,
                        utilitiesBasicAuthPassword),
                String.format("%s?request_id=%s&user_ref=%s",
                        utilitiesEndpoint, requestId, userRef),
                new Payload(params).toJson().toString());
        return (String) ((LinkedHashMap) response.getData().get("utility")).get("ref");
    }

    @Override
    public String updateUtility(String requestId,
                       String utilityRef,
                       String userRef,
                       Map<String, Object> params)
            throws ApiResponseException {
        ApiResponse response = requestUtils.put(
                new BasicAuthCredentials(utilitiesBasicAuthUsername,
                        utilitiesBasicAuthPassword),
                String.format("%s?request_id=%s&user_ref=%s&utility_ref=%s",
                        utilitiesEndpoint, requestId, userRef, utilityRef),
                new Payload(params).toJson().toString());
        return (String) ((LinkedHashMap) response.getData().get("utility")).get("ref");
    }

    @Override
    public void activateUtility(String requestId,
                                String userRef,
                                String utilityRef)
            throws ApiResponseException {
        requestUtils.put(
                new BasicAuthCredentials(utilitiesBasicAuthUsername,
                        utilitiesBasicAuthPassword),
                String.format("%s/%s?user_ref=%s&request_id=%s",
                        utilitiesEndpoint, utilityRef, userRef, requestId),
                null);
    }

    @Override
    public void deactivateUtility(String requestId,
                              String userRef,
                              String utilityRef)
            throws ApiResponseException {
        requestUtils.delete(
                new BasicAuthCredentials(utilitiesBasicAuthUsername,
                        utilitiesBasicAuthPassword),
                String.format("%s/%s?user_ref=%s&request_id=%s",
                        utilitiesEndpoint, utilityRef, userRef, requestId));
    }
}
