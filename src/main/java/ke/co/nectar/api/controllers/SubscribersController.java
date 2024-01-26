package ke.co.nectar.api.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import ke.co.nectar.api.constants.StringConstants;
import ke.co.nectar.api.controllers.exceptions.MissingSubscriberRefException;
import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.domain.Subscriber;
import ke.co.nectar.api.service.subscribers.SubscribersService;
import ke.co.nectar.api.utils.UUidUtils;
import ke.co.nectar.api.validation.authoritization.APIAuthorizationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Validated
@RequestMapping("/v1")
public class SubscribersController extends BaseController {

    @Autowired
    private SubscribersService subscriberService;

    @PreAuthorize("hasPermission(#request,'get_subscriber')")
    @GetMapping(path = "/subscribers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse getSubscriber(HttpServletRequest request,
                                     @RequestParam("ref") String subscriberRef) {

        String requestId = UUidUtils.generateRef();
        try {
            if (!subscriberRef.isBlank()) {
                Subscriber subscriberDetails = subscriberService.getSubscriber(requestId, subscriberRef);
                return new ApiResponse(StringConstants.SUCCESS_CODE,
                        StringConstants.SUCCESS_OBTAINED_SUBSCRIBER_DETAILS,
                        requestId,
                        generateData("data", subscriberDetails));
            }
            throw new MissingSubscriberRefException("Missing subscriber ref");

        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                                    e.getMessage(),
                                    requestId);
        }
    }

    @PreAuthorize("hasPermission(#request,'add_subscriber')")
    @PostMapping(path = "/subscribers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse addSubscriber(HttpServletRequest request,
                                     @RequestBody Map<String, Object> params) {
        String requestId = UUidUtils.generateRef();
        try {
            String createdSubscriberRef = subscriberService.addSubscriber(requestId,
                    APIAuthorizationManager.user.getRef(),
                    params);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                    StringConstants.SUCCESS_CREATED_SUBSCRIBER,
                    requestId,
                    generateData("ref", createdSubscriberRef));
        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                                    e.getMessage(),
                                    requestId);
        }
    }

    @PreAuthorize("hasPermission(#request,'update_subscriber')")
    @PutMapping(path = "/subscribers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse updateSubscriber(HttpServletRequest request,
                                        @NotNull @RequestParam("subscriber_ref") String subscriberRef,
                                        @RequestBody Map<String, Object> params) {
        String requestId = UUidUtils.generateRef();
        try {
            String updatedSubscriberRef = subscriberService.updateSubscriber(requestId,
                                                subscriberRef,
                                                APIAuthorizationManager.user.getRef(),
                                                params);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                                    StringConstants.SUCCESS_UPDATED_SUBSCRIBER,
                                    requestId,
                                    generateData("ref", updatedSubscriberRef));
        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                                    e.getMessage(),
                                    requestId);
        }
    }

    @PreAuthorize("hasPermission(#request,'activate_subscriber')")
    @PutMapping(path = "/subscribers/{subscriber_ref}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse activateSubscriber(HttpServletRequest request,
                                          @NotNull @PathVariable(value = "subscriber_ref") String subscriberRef) {
        String requestId = UUidUtils.generateRef();
        String userRef = APIAuthorizationManager.user.getRef();
        try {
            subscriberService.activateSubscriber(requestId, userRef, subscriberRef);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                                    StringConstants.SUCCESS_ACTIVATED_SUBSCRIBER,
                                    requestId);
        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                                    e.getMessage(),
                                    requestId);
        }
    }

    @PreAuthorize("hasPermission(#request,'deactivate_subscriber')")
    @DeleteMapping(path = "/subscribers/{subscriber_ref}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse deactivateSubscriber(HttpServletRequest request,
                                            @NotNull @PathVariable(value = "subscriber_ref") String subscriberRef) {
        String requestId = UUidUtils.generateRef();
        String userRef = APIAuthorizationManager.user.getRef();
        try {
            subscriberService.deactivateSubscriber(requestId, userRef, subscriberRef);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                                    StringConstants.SUCCESS_DEACTIVATED_SUBSCRIBER,
                                    requestId);
        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                                    e.getMessage(),
                                    requestId);
        }
    }
}
