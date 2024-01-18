package ke.co.nectar.api.service.credits.impl;

import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.domain.Credits;
import ke.co.nectar.api.service.credits.CreditsService;
import ke.co.nectar.api.service.exceptions.ApiResponseException;
import ke.co.nectar.api.utils.RequestUtils;
import ke.co.nectar.api.validation.authoritization.BasicAuthCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CreditsServiceImpl implements CreditsService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RequestUtils requestUtils;

    @Autowired
    private Credits credits;

    @Value("${endpoints.credits.host}")
    private String creditsEndpoint;

    @Value("${endpoints.credits.username}")
    private String creditsBasicAuthUsername;

    @Value("${endpoints.credits.password}")
    private String creditsBasicAuthPassword;

    @Override
    public Credits getCredits(String requestId, String userRef)
        throws ApiResponseException {
        ApiResponse response = requestUtils.get(
                new BasicAuthCredentials(creditsBasicAuthUsername,
                        creditsBasicAuthPassword),
                String.format("%s?user_ref=%s&all=false&detailed=false&request_id=%s",
                        creditsEndpoint, userRef, requestId));
        return credits.extractFrom(response);
    }

    @Override
    public Credits getCreditsAndConsumption(String requestId, String userRef)
        throws ApiResponseException {
        ApiResponse response = requestUtils.get(
                new BasicAuthCredentials(creditsBasicAuthUsername,
                        creditsBasicAuthPassword),
                String.format("%s?user_ref=%s&all=true&detailed=true&request_id=%s",
                        creditsEndpoint, userRef, requestId));
        return credits.extractFrom(response);
    }
}
