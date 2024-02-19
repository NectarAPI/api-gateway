package ke.co.nectar.api.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import ke.co.nectar.api.constants.StringConstants;
import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.domain.Payment;
import ke.co.nectar.api.service.payment.PaymentsService;
import ke.co.nectar.api.utils.UUidUtils;
import ke.co.nectar.api.validation.authoritization.APIAuthorizationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/v1")
public class PaymentsController  extends BaseController {

    @Autowired
    private PaymentsService paymentsService;

    @PreAuthorize("hasPermission(#request,'get_payments')")
    @GetMapping(value = "/payments",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse getPayments(HttpServletRequest request) {
        String requestId = UUidUtils.generateRef();
        try {
            List<Payment> paymentsData = paymentsService.getPayments(
                    requestId, APIAuthorizationManager.user.getRef());
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                                    StringConstants.SUCCESS_OBTAINED_PAYMENTS,
                                    requestId,
                                    generateData("data", paymentsData));
        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    requestId);
        }
    }

    @PreAuthorize("hasPermission(#request,'get_payment')")
    @GetMapping(value = "/payments/{payment_ref}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse getPayment(HttpServletRequest request,
                                  @NotNull @PathVariable(value = "payment_ref") String paymentRef) {
        String requestId = UUidUtils.generateRef();
        try {
            Payment paymentData = paymentsService.getPayment(
                    requestId, APIAuthorizationManager.user.getRef(), paymentRef);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                    StringConstants.SUCCESS_OBTAINED_PAYMENTS,
                    requestId,
                    generateData("data", paymentData));
        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    requestId);
        }
    }

    /**
     * This controller processes incoming payment requests and
     * triggers a payment request based on the specified
     * @Param paymentType, which must be supported in the payments
     * service.
     * @param request
     * @param paymentRef
     * @return
     */
    @PreAuthorize("hasPermission(#request,'schedule_payment')")
    @GetMapping(value = "/payments/{payment_type}/schedule",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse schedulePayment(HttpServletRequest request,
                                  @NotNull @PathVariable(value = "payment_ref") String paymentRef) {
        String requestId = UUidUtils.generateRef();
        try {
            Payment paymentData = paymentsService.schedulePayment(
                    requestId, APIAuthorizationManager.user.getRef(), paymentRef);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                    StringConstants.SUCCESS_OBTAINED_PAYMENTS,
                    requestId,
                    generateData("data", paymentData));
        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    requestId);
        }
    }

    /**
     * This function processes the result of a payment request. The specific
     * payment request must be handled in the payment service based on the
     * specified @Param paymentType.
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/payments/{payment_type}/result",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse processPaymentResult(HttpServletRequest request,
                                  @NotNull @RequestBody String response) {
        String requestId = UUidUtils.generateRef();
        try {
            Payment paymentData = paymentsService.processPaymentResult(
                    requestId, APIAuthorizationManager.user.getRef(), paymentRef);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                    StringConstants.SUCCESS_OBTAINED_PAYMENTS,
                    requestId,
                    generateData("data", paymentData));
        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    requestId);
        }
    }

    /**
     * This function processes a timeout response for a payment request. This handles
     * cases where customers take too long or do not enter valid payment authorization,
     * or where a payment gateway cancels a transaction for a specified reason. Handling
     * of this request will be based on the specified @Param paymentType.
     */
    @PostMapping(value = "/payments/{payment_type}/timeout")
    public ApiResponse processTimeout(HttpServletRequest httpServletRequest) {
        String requestId = UUidUtils.generateRef();
        try {
            Payment paymentData = paymentsService.processPaymentTimeout(
                    requestId, APIAuthorizationManager.user.getRef(), paymentRef);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                    StringConstants.SUCCESS_OBTAINED_PAYMENTS,
                    requestId,
                    generateData("data", paymentData));
        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    requestId);
        }
    }


    /**
     * This function triggers a check/validation on a payment request. Handling of this
     * request will be based on a specific @Param paymentType in the payments-service.
     */
    @PostMapping(value = "/payments/{payment_type}/validate")
    public ApiResponse validatePayment(HttpServletRequest httpServletRequest) {
        String requestId = UUidUtils.generateRef();
        try {
            Payment paymentData = paymentsService.validatePayment(
                    requestId, APIAuthorizationManager.user.getRef(), paymentRef);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                    StringConstants.SUCCESS_OBTAINED_PAYMENTS,
                    requestId,
                    generateData("data", paymentData));
        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    requestId);
        }
    }


    /**
     * This function processes the result if a check on a payment request. Handling of this
     * request will be based on a specific @Param paymentType in the payments-service.
     */
    @PostMapping(value = "/payments/{payment_type}/status")
    public ApiResponse processPaymentStatus(HttpServletRequest httpServletRequest) {
        String requestId = UUidUtils.generateRef();
        try {
            Payment paymentData = paymentsService.processPaymentValidation(
                    requestId, APIAuthorizationManager.user.getRef(), paymentRef);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                    StringConstants.SUCCESS_OBTAINED_PAYMENTS,
                    requestId,
                    generateData("data", paymentData));
        } catch (Exception e) {
            return new ApiResponse(StringConstants.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    requestId);
        }
    }


}
