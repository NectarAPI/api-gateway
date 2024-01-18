package ke.co.nectar.api.service.notifications.impl;

import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.domain.Notification;
import ke.co.nectar.api.service.exceptions.ApiResponseException;
import ke.co.nectar.api.service.notifications.NotificationsService;
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
public class NotificationsServiceImpl
        implements NotificationsService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RequestUtils requestUtils;

    @Autowired
    private Notification notification;

    @Value("${endpoints.notifications.host}")
    private String notificationsEndpoint;

    @Value("${endpoints.notifications.username}")
    private String notificationsBasicAuthUsername;

    @Value("${endpoints.notifications.password}")
    private String notificationsBasicAuthPassword;

    @Override
    public List<Notification> getNotifications(String requestId, String userRef)
        throws ApiResponseException {
        ApiResponse response = requestUtils.get(
                new BasicAuthCredentials(notificationsBasicAuthUsername,
                        notificationsBasicAuthPassword),
                    String.format("%s?user_ref=%s&request_id=%s",
                            notificationsEndpoint, userRef,  requestId));
        return notification.extractMultipleFrom(response);
    }

    @Override
    public Notification getNotification(String requestId, String ref)
            throws ApiResponseException {
        ApiResponse response = requestUtils.get(
                new BasicAuthCredentials(notificationsBasicAuthUsername,
                        notificationsBasicAuthPassword),
                String.format("%s?ref=%s&request_id=%s",
                        notificationsEndpoint, ref, requestId));
        return notification.extractFrom(response);
    }

    @Override
    public void setNotificationReadStatus(String requestId,
                                          String userRef,
                                          String notificationRef,
                                          boolean status,
                                          long timestamp)
        throws ApiResponseException {
        Map<String, Object> params = new HashMap<>();
        params.put("user_ref", userRef);
        params.put("notification_ref", notificationRef);
        params.put("status", status);
        params.put("read_timestamp", timestamp);
        requestUtils.put(
                new BasicAuthCredentials(notificationsBasicAuthUsername,
                        notificationsBasicAuthPassword),
                String.format("%s?request_id=%s", notificationsEndpoint, requestId),
                String.format("[%s]", new Payload(params).toJson()));
    }
}
