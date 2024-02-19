package ke.co.nectar.api.service.payment.impl;

import ke.co.nectar.api.controllers.response.ApiResponse;
import ke.co.nectar.api.service.exceptions.ApiResponseException;
import ke.co.nectar.api.service.payment.PaymentsService;
import ke.co.nectar.api.utils.RequestUtils;
import ke.co.nectar.api.validation.authoritization.BasicAuthCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class PaymentsServiceImpl implements PaymentsService {

    @Autowired
    private RequestUtils requestUtils;

    @Value("${endpoints.payments.callback-url}")
    private String paymentsCallbackUrl;

    @Value("${endpoints.payments.timeout-url}")
    private String paymentsQueueTimeoutUrl;

    @Value("${endpoints.payments.check-payments-url}")
    private String checkPaymentsUrl;

    @Value("${endpoints.payments.schedule-payments-url}")
    private String schedulePaymentsUrl;

    @Value("${endpoints.payments.validate-payments-url}")
    private String validatePaymentUrl;

    @Value("${endpoints.payments.validate-payments-result-url}")
    private String processPaymentValidation;

    @Value("${endpoints.payments.username}")
    private String beesBasicAuthUsername;

    @Value("${endpoints.payments.password}")
    private String beesBasicAuthPassword;

    @Override
    public String forwardPaymentResult(String requestId, String forward)
            throws ApiResponseException {
        ApiResponse response = requestUtils.post(
                new BasicAuthCredentials(beesBasicAuthUsername,
                        beesBasicAuthPassword),
                String.format("%s?request_id=%s",
                        paymentsCallbackUrl, requestId),
                forward);
        assert response != null;
        if (response.getStatus().getCode() == 200) {
            return response.getStatus().getRequestId();
        }
        throw new ApiResponseException(response.getStatus().getMessage());
    }
}
