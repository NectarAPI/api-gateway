package ke.co.nectar.api.service.notifications;

import ke.co.nectar.api.domain.Notification;
import ke.co.nectar.api.service.exceptions.ApiResponseException;

import java.util.List;

public interface NotificationsService {

    List<Notification> getNotifications(String requestId, String userRef)
                throws ApiResponseException;

    Notification getNotification(String requestId, String userRef)
            throws ApiResponseException;

    void setNotificationReadStatus(String requestId,
                                   String userRef,
                                   String notificationRef,
                                   boolean status,
                                   long timestamp)
            throws ApiResponseException;
}
