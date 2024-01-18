package ke.co.nectar.api.service.publickeys.impl;

import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.domain.PublicKey;
import ke.co.nectar.api.service.exceptions.ApiResponseException;
import ke.co.nectar.api.service.publickeys.PublicKeysService;
import ke.co.nectar.api.utils.RequestUtils;
import ke.co.nectar.api.utils.requests.Payload;
import ke.co.nectar.api.validation.authoritization.BasicAuthCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PublicKeysServiceImpl implements PublicKeysService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RequestUtils requestUtils;

    @Autowired
    private PublicKey publicKey;

    @Value("${endpoints.public_keys.host}")
    private String publicKeysEndpoint;

    @Value("${endpoints.public_keys.username}")
    private String publicKeysBasicAuthUsername;

    @Value("${endpoints.public_keys.password}")
    private String publicKeysBasicAuthPassword;

    @Override
    public List<PublicKey> getPublicKeys(String requestId,
                                         String userRef,
                                         boolean activated)
        throws ApiResponseException {
        ApiResponse response = requestUtils.get(
                new BasicAuthCredentials(publicKeysBasicAuthUsername,
                        publicKeysBasicAuthPassword),
                String.format("%s?user_ref=%s&activated=%b&request_id=%s",
                        publicKeysEndpoint, userRef, activated, requestId));
        return publicKey.extractMultipleFrom(response);
    }

    @Override
    public PublicKey createPublicKey(String requestId,
                                     String userRef,
                                     String name,
                                     String key,
                                     boolean activated)
        throws ApiResponseException {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("public_key", key);
        params.put("activated", activated);
        ApiResponse response = requestUtils.post(
                new BasicAuthCredentials(publicKeysBasicAuthUsername,
                        publicKeysBasicAuthPassword),
                String.format("%s?&request_id=%s&user_ref=%s",
                        publicKeysEndpoint, requestId, userRef),
                new Payload(params).toJson().toString());
        return publicKey.extractFrom(response);
    }

    @Override
    public void activatePublicKey(String requestId, String userRef, String ref)
        throws ApiResponseException {
        ApiResponse response = requestUtils.put(
                new BasicAuthCredentials(publicKeysBasicAuthUsername,
                        publicKeysBasicAuthPassword),
                String.format("%s?ref=%s&request_id=%s&user_ref=%s",
                        publicKeysEndpoint, ref, requestId, userRef),
                null);
    }

    @Override
    public void deactivatePublicKey(String requestId, String userRef, String ref)
        throws ApiResponseException {
        ApiResponse response = requestUtils.delete(
                new BasicAuthCredentials(publicKeysBasicAuthUsername,
                        publicKeysBasicAuthPassword),
                String.format("%s?ref=%s&request_id=%s&user_ref=%s",
                        publicKeysEndpoint, ref, requestId, userRef));
    }
}
