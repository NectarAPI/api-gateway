package ke.co.nectar.api.service.subscribers;

import ke.co.nectar.api.domain.Subscriber;
import ke.co.nectar.api.service.exceptions.ApiResponseException;

import java.util.Map;

public interface SubscribersService {

    Subscriber getSubscriber(String requestId,
                             String subscriberRef)
        throws ApiResponseException;

    String addSubscriber(String requestId,
                         String userRef,
                         Map<String, Object> params)
        throws ApiResponseException;

    String updateSubscriber(String requestId,
                          String subscriberRef,
                          String userRef,
                          Map<String, Object> params)
        throws ApiResponseException;

    void activateSubscriber(String requestId,
                            String userRef,
                            String subscriberRef)
        throws ApiResponseException;

    void deactivateSubscriber(String requestId,
                              String userRef,
                              String subscriberRef)
        throws ApiResponseException;
}
