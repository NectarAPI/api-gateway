package ke.co.nectar.api.controllers.payments;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import ke.co.nectar.api.constants.StringConstants;
import ke.co.nectar.api.controllers.BaseController;
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
                    requestId, paymentRef);
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
     * @Param paymentType. This payment type must be implemented
     * in the payments service.
     * @param request HttpServletRequest for incoming request
     * @param paymentRequest Payment request parameters
     * @return
     */
    @PreAuthorize("hasPermission(#request,'schedule_payment')")
    @GetMapping(value = "/payments/{payment_type}/schedule",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse schedulePayment(HttpServletRequest request,
                                       @NotNull @RequestBody PaymentRequest paymentRequest) {
        String requestId = UUidUtils.generateRef();
        try {
            String paymentRef = paymentsService.schedulePayment(
                    requestId, APIAuthorizationManager.user.getRef(), paymentRequest);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                    StringConstants.SUCCESS_OBTAINED_PAYMENTS,
                    requestId,
                    generateData("data", paymentRef));
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
     * @return ApiResponse
     */
    @PostMapping(value = "/payments/{payment_type}/result",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse processSchedulePaymentResult(HttpServletRequest request,
                                                    @NotNull @RequestBody String paymentResult) {
        String requestId = UUidUtils.generateRef();
        try {
            Payment paymentData = paymentsService.processPaymentValidation(requestId, paymentResult);
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
    public ApiResponse processPaymentTimeout(HttpServletRequest httpServletRequest,
                                             @NotNull @RequestBody String paymentRef) {
        String requestId = UUidUtils.generateRef();
        try {
            String transactionRef = paymentsService.processPaymentTimeout(
                    requestId, paymentRef);
            return new ApiResponse(StringConstants.SUCCESS_CODE,
                    StringConstants.SUCCESS_OBTAINED_PAYMENTS,
                    requestId,
                    generateData("transaction_ref", transactionRef));
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
    @PreAuthorize("hasPermission(#request,'validate_payment')")
    @PostMapping(value = "/payments/{payment_type}/validate")
    public ApiResponse validatePayment(HttpServletRequest httpServletRequest,
                                       @NotNull @RequestBody String paymentRef) {
        String requestId = UUidUtils.generateRef();
        try {
            String paymentData = paymentsService.validatePayment(
                    requestId, paymentRef);
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
    public ApiResponse processValidatePaymentStatus(HttpServletRequest httpServletRequest,
                                                    @NotNull @RequestBody String paymentRef) {
        String requestId = UUidUtils.generateRef();
        try {
            Payment paymentData = paymentsService.processPaymentValidation(
                    requestId, paymentRef);
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
