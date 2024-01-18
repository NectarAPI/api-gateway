package ke.co.nectar.api.service.credentials.impl;

import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.domain.Credentials;
import ke.co.nectar.api.service.credentials.CredentialsService;
import ke.co.nectar.api.service.exceptions.ApiResponseException;
import ke.co.nectar.api.utils.RequestUtils;
import ke.co.nectar.api.validation.authoritization.BasicAuthCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CredentialsServiceImpl implements CredentialsService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RequestUtils requestUtils;

    @Autowired
    private Credentials credentials;

    @Value("${endpoints.credentials.host}")
    private String credentialsEndpoint;

    @Value("${endpoints.credentials.username}")
    private String credentialsBasicAuthUsername;

    @Value("${endpoints.credentials.password}")
    private String credentialsBasicAuthPassword;

    @Override
    public Credentials getCredentialsByRef(String requestId, String ref)
        throws ApiResponseException  {
        ApiResponse response = requestUtils.get(
                new BasicAuthCredentials(credentialsBasicAuthUsername,
                        credentialsBasicAuthPassword),
                String.format("%s?ref=%s&request_id=%s", credentialsEndpoint, ref, requestId));
        return credentials.extractFrom(response);
   }

   @Override
    public void activateCredentials(String requestId, String userRef, String ref)
        throws ApiResponseException {
        requestUtils.put(
                new BasicAuthCredentials(credentialsBasicAuthUsername,
                        credentialsBasicAuthPassword),
                String.format("%s?ref=%s&request_id=%s&user_ref=%s",
                        credentialsEndpoint, ref, requestId, userRef),
                null);
    }

    @Override
    public void deactivateCredentials(String requestId, String userRef, String ref)
        throws ApiResponseException {
        requestUtils.delete(
                new BasicAuthCredentials(credentialsBasicAuthUsername,
                        credentialsBasicAuthPassword),
                String.format("%s?ref=%s&request_id=%s&user_ref=%s",
                        credentialsEndpoint, ref, requestId, userRef));
    }
}
