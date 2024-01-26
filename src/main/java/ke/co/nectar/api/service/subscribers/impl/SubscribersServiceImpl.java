package ke.co.nectar.api.service.subscribers.impl;

import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.domain.Subscriber;
import ke.co.nectar.api.service.exceptions.ApiResponseException;
import ke.co.nectar.api.service.subscribers.SubscribersService;
import ke.co.nectar.api.utils.RequestUtils;
import ke.co.nectar.api.utils.requests.Payload;
import ke.co.nectar.api.validation.authoritization.BasicAuthCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class SubscribersServiceImpl implements SubscribersService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RequestUtils requestUtils;

    @Autowired
    private Subscriber subscriber;

    @Value("${endpoints.subscribers.host}")
    private String subscribersEndpoint;

    @Value("${endpoints.subscribers.username}")
    private String subscribersBasicAuthUsername;

    @Value("${endpoints.subscribers.password}")
    private String subscribersBasicAuthPassword;

    @Override
    public Subscriber getSubscriber(String requestId,
                              String subscriberRef)
            throws ApiResponseException {
        ApiResponse response =  requestUtils.get(
                new BasicAuthCredentials(subscribersBasicAuthUsername,
                        subscribersBasicAuthPassword),
                String.format("%s?ref=%s&request_id=%s", subscribersEndpoint, subscriberRef, requestId));
        return subscriber.extractFrom(response);
    }

    @Override
    public String addSubscriber(String requestId,
                                  String userRef,
                                  Map<String, Object> params)
            throws ApiResponseException {
        ApiResponse response = requestUtils.post(
                new BasicAuthCredentials(subscribersBasicAuthUsername,
                        subscribersBasicAuthPassword),
                String.format("%s?request_id=%s&user_ref=%s",
                        subscribersEndpoint, requestId, userRef),
                        new Payload(params).toJson().toString());
        return (String) ((LinkedHashMap) response.getData().get("subscriber")).get("ref");
    }

    @Override
    public String updateSubscriber(String requestId,
                                     String subscriberRef,
                                     String userRef,
                                     Map<String, Object> params)
            throws ApiResponseException {
        ApiResponse response = requestUtils.put(
                new BasicAuthCredentials(subscribersBasicAuthUsername,
                        subscribersBasicAuthPassword),
                String.format("%s?request_id=%s&subscriber_ref=%s&user_ref=%s",
                        subscribersEndpoint, requestId, subscriberRef, userRef),
                new Payload(params).toJson().toString());
        return (String) ((LinkedHashMap) response.getData().get("subscriber")).get("ref");
    }

    public void activateSubscriber(String requestId,
                                           String userRef,
                                           String subscriberRef)
            throws ApiResponseException {
        requestUtils.put(
                new BasicAuthCredentials(subscribersBasicAuthUsername,
                        subscribersBasicAuthPassword),
                String.format("%s/%s?request_id=%s&user_ref=%s",
                        subscribersEndpoint, subscriberRef, requestId, userRef),
                null);
    }

    public void deactivateSubscriber(String requestId,
                                     String userRef,
                                     String subscriberRef)
            throws ApiResponseException {
        requestUtils.delete(
                new BasicAuthCredentials(subscribersBasicAuthUsername,
                        subscribersBasicAuthPassword),
                String.format("%s/%s?request_id=%s&user_ref=%s",
                        subscribersEndpoint, subscriberRef, requestId, userRef));
    }
}
