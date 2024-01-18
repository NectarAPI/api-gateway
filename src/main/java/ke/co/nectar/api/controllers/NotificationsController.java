package ke.co.nectar.api.controllers;

import ke.co.nectar.api.constants.StringConstants;
import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.domain.Notification;
import ke.co.nectar.api.service.notifications.NotificationsService;
import ke.co.nectar.api.utils.UUidUtils;
import ke.co.nectar.api.validation.authoritization.APIAuthorizationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/v1")
public class NotificationsController extends BaseController {

    @Autowired
    private NotificationsService notificationsService;

    @PreAuthorize("hasPermission(#request,'get_notifications')")
    @GetMapping(path = "/notifications",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse getNotifications(HttpServletRequest request) {
        String requestId = UUidUtils.generateRef();
        try {
            List<Notification> notifications = notificationsService.getNotifications(
                    requestId,
                    APIAuthorizationManager.user.getRef());
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                                    StringConstants.SUCCESS_OBTAINED_TOKEN_DETAILS,
                                    requestId,
                                    generateData("data", notifications));
        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    requestId);
        }
    }

    @PreAuthorize("hasPermission(#request,'set_notifications_read_status')")
    @PutMapping(path = "/notifications",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse setNotificationReadStatus(HttpServletRequest request,
                                                 @RequestBody Map<String, Object> params) {
        String requestId = UUidUtils.generateRef();
        try {
            String notificationRef = (String) params.get("notification_ref");
            boolean status = (Boolean) params.get("status");
            long timestamp = (Long) params.get("timestamp");
            notificationsService.setNotificationReadStatus(requestId,
                                                            APIAuthorizationManager.user.getRef(),
                                                            notificationRef,
                                                            status,
                                                            timestamp);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                                    StringConstants.SUCCESS_SET_NOTIFICATION_READ_STATUS,
                                    requestId);
        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    requestId);
        }
    }
}
